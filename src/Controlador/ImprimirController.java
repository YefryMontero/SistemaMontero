/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.BD.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Yefry Montero
 */
public class ImprimirController implements Initializable, ControladorVentanas {

    /**
     * Initializes the controller class.
     */
    @FXML
    private DatePicker datein;

    @FXML
    private Label tVenta;

    @FXML
    private Label tCompra;

    @FXML
    private Label ganancia;

    @FXML
    private Label comision;

    @FXML
    private DatePicker datefin;

    @FXML
    private TableView tablaVenta;

    @FXML
    private TableView tablaCompra;
    ControladorVentanasMontero controlador;
    @FXML
    private TableColumn colVenta, colCompra;
    private Connection conexion;

    ObservableList<ObservableList> Venta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        datefin.setOnAction(e -> System.out.println("FECHA " + datein.getValue()));
        if (datein.getValue() == null || datefin.getValue() == null) {
            System.out.println("Esta Vacio");
        } else {
            System.out.println("NO Esta Vacio");
        }
        datein.setOnAction(e -> System.out.println("FECHA " + datein.getValue()));
        if (datein.getValue() == null || datefin.getValue() == null) {
            System.out.println("Esta Vacio");
        } else {
        }
        crearTablaCompra();
        crearTablaVenta();
    }

    @Override
    public void setPantallaPadre(ControladorVentanasMontero ventana) {
        controlador = ventana;
    }

    @FXML
    void Buscar(ActionEvent event) {
        LocalDate ini = datein.getValue();
        LocalDate fin = datefin.getValue();
        if (ini == null || fin == null) {
            System.out.println("Esta Vacio");
        } else {
            System.out.println("NO Esta Vacio");
        }
        cargarDatosTablaVenta(ini, fin);
        cargarDatosTablaCompra(ini, fin);
        mostrarResultados(ini, fin);
    }

    public void mostrarResultados(LocalDate ini, LocalDate fin) {
        long varTotalCompra = 0;
        long varTotalVenta = 0;
        long varComision = 0;
        long varGanancia = 0;
        try {
            conexion = DBConnection.connect();
            String sql = "select valorcompra From compra\n"
                    + "where fecha >= '" + ini + "' and  fecha <= '" + fin + "'";
            //ResultSet
            ResultSet rs = conexion.createStatement().executeQuery(sql);
            while (rs.next()) {
                varTotalCompra += Float.valueOf(rs.getString("valorcompra"));
            }
            sql = "select valorventa From venta\n"
                    + "where fecha >= '" + ini + "' and  fecha <= '" + fin + "'";
            rs = conexion.createStatement().executeQuery(sql);
            while (rs.next()) {
                varTotalVenta += Float.valueOf(rs.getString("valorventa"));
            }

            sql = "select kilostomate From venta\n"
                    + "inner join linea_venta on venta.codigoventa = linea_venta.codigoventa\n"
                    + "where fecha >= '" + ini + "' and  fecha <= '" + fin + "'";
            rs = conexion.createStatement().executeQuery(sql);
            while (rs.next()) {
                varComision += Float.valueOf(rs.getString("kilostomate"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ImprimirController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tCompra.setText(String.valueOf(varTotalCompra));
        tVenta.setText(String.valueOf(varTotalVenta));
        ganancia.setText(String.valueOf(varTotalVenta - varTotalCompra));
        comision.setText(String.valueOf(varComision * 50));
    }

    public void crearTablaVenta() {
        try {
            conexion = DBConnection.connect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String sql = "select fecha,idComprador, numFactura,ValorFactura,valorcancelado,fletesyviaticos,valorasofrucol,valordescuento,kilostomate From Venta\n"
                    + "INNER JOIN LINEA_VENTA ON Venta.CodigoVenta = linea_venta.CodigoVenta\n";
            //ResultSet
            ResultSet rs = conexion.createStatement().executeQuery(sql);
            // Títulos de las columnas
            String[] titulos = {
                "Fecha",
                "idComprador",
                "numFactura",
                "Var Factura",
                "Var Cancelador",
                "fletesyviaticos",
                "Var Asofrucol",
                "Var Descuento",
                "Tomate KG"
            };
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                colVenta = new TableColumn(titulos[i]);
                colVenta.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> parametro) {
                        return new SimpleStringProperty((String) parametro.getValue().get(j));
                    }
                });
                tablaVenta.getColumns().addAll(colVenta);
                // Asignamos un tamaño a ls columnnas
                colVenta.setMinWidth(100);
                System.out.println("Column [" + i + "] ");
                // Centrar los datos de la tabla
                colVenta.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                    @Override
                    public TableCell<String, String> call(TableColumn<String, String> p) {
                        TableCell cell = new TableCell() {
                            @Override
                            protected void updateItem(Object t, boolean bln) {
                                if (t != null) {
                                    super.updateItem(t, bln);
                                    System.out.println(t);
                                    setText(t.toString());
                                    setAlignment(Pos.CENTER); //Setting the Alignment
                                }
                            }
                        };
                        return cell;
                    }
                });
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    public void crearTablaCompra() {
        try {
            conexion = DBConnection.connect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String sql = "select fecha,idComprador,producto,cantcanatillas,pesouni,cantkilos,varkilo,varcompra From compra\n"
                    + "INNER JOIN linea_Compra on compra.codigocompra = linea_Compra.codigocompra";
            ResultSet rs;

            rs = conexion.createStatement().executeQuery(sql);

            String[] titulos = {
                "Fecha",
                "idComprador",
                "producto",
                "cantcanatillas",
                "pesouni",
                "cantkilos",
                "varkilo",
                "varcompra"
            };
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                colCompra = new TableColumn(titulos[i]);
                colCompra.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> parametro) {
                        return new SimpleStringProperty((String) parametro.getValue().get(j));
                    }
                });
                tablaCompra.getColumns().addAll(colCompra);
                // Asignamos un tamaño a ls columnnas
                colCompra.setMinWidth(100);
                System.out.println("Column [" + i + "] ");
                // Centrar los datos de la tabla
                colCompra.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                    @Override
                    public TableCell<String, String> call(TableColumn<String, String> p) {
                        TableCell cell = new TableCell() {
                            @Override
                            protected void updateItem(Object t, boolean bln) {
                                if (t != null) {
                                    super.updateItem(t, bln);
                                    System.out.println(t);
                                    setText(t.toString());
                                    setAlignment(Pos.CENTER); //Setting the Alignment
                                }
                            }
                        };
                        return cell;
                    }
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImprimirController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarDatosTablaCompra(LocalDate ini, LocalDate fin) {
        ObservableList<ObservableList> Compra = FXCollections.observableArrayList();

        try {
            conexion = DBConnection.connect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String sql = "select fecha,idComprador,producto,cantcanatillas,pesouni,cantkilos,varkilo,varcompra From compra\n"
                    + "INNER JOIN linea_Compra on compra.codigocompra = linea_Compra.codigocompra\n"
                    + "where fecha >= '" + ini + "' and  fecha <= '" + fin + "'";
            //ResultSet
            ResultSet rs = conexion.createStatement().executeQuery(sql);
            // Títulos de las columnas

            /**
             * ******************************
             * Cargamos de la base de datos * ******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                Compra.addAll(row);
            }
            //FINALLY ADDED TO TableView
            tablaCompra.setItems(Compra);
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        tablaCompra.refresh();
    }

    public void cargarDatosTablaVenta(LocalDate ini, LocalDate fin) {
        Venta = FXCollections.observableArrayList();

        try {
            conexion = DBConnection.connect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String sql = "select fecha,idComprador, numFactura,ValorFactura,valorcancelado,fletesyviaticos,valorasofrucol,valordescuento,kilostomate From Venta\n"
                    + "INNER JOIN LINEA_VENTA ON Venta.CodigoVenta = linea_venta.CodigoVenta\n"
                    + "where fecha >= '" + ini + "' and  fecha <= '" + fin + "'";
            //ResultSet
            ResultSet rs = conexion.createStatement().executeQuery(sql);
            // Títulos de las columnas
            String[] titulos = {
                "Fecha",
                "idComprador",
                "numFactura",
                "Var Factura",
                "Var Cancelador",
                "fletesyviaticos",
                "Var Asofrucol",
                "Var Descuento",
                "Tomate KG"
            };
           
            /**
             * ******************************
             * Cargamos de la base de datos * ******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                Venta.addAll(row);
            }
            //FINALLY ADDED TO TableView
            tablaVenta.setItems(Venta);
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        tablaVenta.refresh();
    }
}
