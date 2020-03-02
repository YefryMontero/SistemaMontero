/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.BD.DBConnection;
import MainApp.VentanaMontero;
import Util.Validaciones;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Yefry Montero
 */
public class CompraController implements Initializable, ControladorVentanas {

    Validaciones validacion = new Validaciones();
    private Connection conexion;
    ControladorVentanasMontero controlador;
    @FXML
    private GridPane gridCompra;

    @FXML
    private DatePicker dateFecha;

    @FXML
    private Button btnAñadir;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnAtras;

    @FXML
    private TextField[][] tfCompra;
    private int numLineas = 2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        {
            tfCompra = new TextField[7][11];
            gridCompra.add(tfCompra[4][0] = new TextField("0"), 4, 0);
            gridCompra.add(tfCompra[0][numLineas] = new TextField(), 0, 2);
            gridCompra.add(tfCompra[1][numLineas] = new TextField(), 1, 2);
            gridCompra.add(tfCompra[2][numLineas] = new TextField("0"), 2, 2);
            gridCompra.add(tfCompra[3][numLineas] = new TextField("0"), 3, 2);
            gridCompra.add(tfCompra[4][numLineas] = new TextField("0"), 4, 2);
            gridCompra.add(tfCompra[5][numLineas] = new TextField(), 5, 2);
            vrTotal();
        }
    }

    public void limpiar(int lineas) {
        for (int i = 2; i <= lineas; i++) {
            for (int j = 0; j <= 5; j++) {
                gridCompra.getChildren().remove(tfCompra[j][i]);
            }

        }
        numLineas = 2;
        inicio();

    }

    public void inicio() {

        tfCompra = new TextField[7][11];
        gridCompra.add(tfCompra[4][0] = new TextField("0"), 4, 0);
        gridCompra.add(tfCompra[0][numLineas] = new TextField(), 0, 2);
        gridCompra.add(tfCompra[1][numLineas] = new TextField(), 1, 2);
        gridCompra.add(tfCompra[2][numLineas] = new TextField("0"), 2, 2);
        gridCompra.add(tfCompra[3][numLineas] = new TextField("0"), 3, 2);
        gridCompra.add(tfCompra[4][numLineas] = new TextField("0"), 4, 2);
        gridCompra.add(tfCompra[5][numLineas] = new TextField(), 5, 2);
        vrTotal();
    }

    @FXML
    void add(ActionEvent event) {

        if (!validacion.validarVacios(tfCompra[0][numLineas].getText(), "Proveedor")) {
            return;
        }
        if (!validacion.validarVacios(tfCompra[1][numLineas].getText(), "Producto")) {
            return;
        }
        if (!validacion.validarVacios(tfCompra[2][numLineas].getText(), "Cantidad")) {
            return;
        }
        if (!validacion.validarVacios(tfCompra[3][numLineas].getText(), "Peso Unitario")) {
            return;
        }
        if (!validacion.validarVacios(tfCompra[4][numLineas].getText(), "Valor Producto")) {
            return;
        }
        if (!validacion.validarVacios(tfCompra[5][numLineas].getText(), "Valor Total")) {
            return;
        }

        if (numLineas == 10) {
            JOptionPane.showMessageDialog(null, "Ya alcanzo el limite");
        } else {
            numLineas++;
            gridCompra.add(tfCompra[0][numLineas] = new TextField(), 0, numLineas);
            gridCompra.add(tfCompra[1][numLineas] = new TextField(), 1, numLineas);
            gridCompra.add(tfCompra[2][numLineas] = new TextField("0"), 2, numLineas);
            gridCompra.add(tfCompra[3][numLineas] = new TextField("0"), 3, numLineas);
            gridCompra.add(tfCompra[4][numLineas] = new TextField("0"), 4, numLineas);
            gridCompra.add(tfCompra[5][numLineas] = new TextField(), 5, numLineas);
            vrTotal();
        }
    }

    public void vrTotal() {

        tfCompra[2][numLineas].focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                System.out.print(numLineas);
                int vrproducto = 0;
                int vrtotal = 0;
                for (int i = 2; i <= numLineas; i++) {
                    vrtotal += Integer.parseInt(tfCompra[2][i].getText())
                            * Integer.parseInt(tfCompra[3][i].getText())
                            * Integer.parseInt(tfCompra[4][i].getText());
                    vrproducto = Integer.parseInt(tfCompra[2][i].getText())
                            * Integer.parseInt(tfCompra[3][i].getText())
                            * Integer.parseInt(tfCompra[4][i].getText());
                }
                tfCompra[4][0].setText(
                        String.valueOf(vrtotal));
                tfCompra[5][numLineas].setText(
                        String.valueOf(vrproducto));
            }
        });
        tfCompra[3][numLineas].focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                System.out.print(numLineas);
                int vrproducto = 0;
                int vrtotal = 0;
                for (int i = 2; i <= numLineas; i++) {
                    vrtotal += Integer.parseInt(tfCompra[2][i].getText())
                            * Integer.parseInt(tfCompra[3][i].getText())
                            * Integer.parseInt(tfCompra[4][i].getText());
                    vrproducto = Integer.parseInt(tfCompra[2][i].getText())
                            * Integer.parseInt(tfCompra[3][i].getText())
                            * Integer.parseInt(tfCompra[4][i].getText());
                }
                tfCompra[4][0].setText(
                        String.valueOf(vrtotal));
                tfCompra[5][numLineas].setText(
                        String.valueOf(vrproducto));
            }
        });
        tfCompra[4][numLineas].focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                System.out.print(numLineas);
                int vrproducto = 0;
                int vrtotal = 0;
                for (int i = 2; i <= numLineas; i++) {
                    vrtotal += Integer.parseInt(tfCompra[2][i].getText())
                            * Integer.parseInt(tfCompra[3][i].getText())
                            * Integer.parseInt(tfCompra[4][i].getText());
                    vrproducto = Integer.parseInt(tfCompra[2][i].getText())
                            * Integer.parseInt(tfCompra[3][i].getText())
                            * Integer.parseInt(tfCompra[4][i].getText());
                }
                tfCompra[4][0].setText(
                        String.valueOf(vrtotal));
                tfCompra[5][numLineas].setText(
                        String.valueOf(vrproducto));
            }
        });

    }

    @FXML
    public void Guardar() {
        
        if (!validacion.validarVacios(tfCompra[0][numLineas].getText(), "Proveedor")) {
            return;
        }
        if (!validacion.validarVacios(tfCompra[1][numLineas].getText(), "Producto")) {
            return;
        }
        if (!validacion.validarVacios(tfCompra[2][numLineas].getText(), "Cantidad")) {
            return;
        }
        if (!validacion.validarVacios(tfCompra[3][numLineas].getText(), "Peso Unitario")) {
            return;
        }
        if (!validacion.validarVacios(tfCompra[4][numLineas].getText(), "Valor Producto")) {
            return;
        }
        if (!validacion.validarVacios(tfCompra[5][numLineas].getText(), "Valor Total")) {
            return;
        }
        if (!validacion.soloLetras(tfCompra[0][numLineas].getText())) {
            return;
        }
        if (!validacion.soloLetras(tfCompra[1][numLineas].getText())) {
            return;
        }
        if (!validacion.soloNumeros(tfCompra[2][numLineas].getText())) {
            return;
        }
        if (!validacion.soloNumeros(tfCompra[3][numLineas].getText())) {
            return;
        }
        if (!validacion.soloNumeros(tfCompra[4][numLineas].getText())) {
            return;
        }
        if (!validacion.soloNumeros(tfCompra[5][numLineas].getText())) {
            return;
        }

        try {
            conexion = DBConnection.connect();
            String sql = "INSERT INTO Compra "
                    + " ( Fecha, ValorCompra) "
                    + " VALUES ( ?, ?)";
            PreparedStatement estado = conexion.prepareStatement(sql);
//            estado.setInt(1, n);
            estado.setDate(1, Date.valueOf(dateFecha.getValue()));
            estado.setInt(2, Integer.parseInt(tfCompra[4][0].getText()));

            estado.executeUpdate();

            estado.close();
            System.out.println(codCompra());
            for (int j = 2; j <= numLineas; j++) {
                guardarlineaventa(j, codCompra());
            }

        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        JOptionPane.showMessageDialog(null, "La Compra se ha guardado con exito");
        limpiar(numLineas);
    }

    @FXML
    public void Atras() {
        controlador.setPantalla(VentanaMontero.MenuPrincipalID);
    }

    public void guardarlineaventa(int j, String codVenta) {
        double canKG = Integer.parseInt(tfCompra[2][j].getText()) * Integer.parseInt(tfCompra[3][j].getText());
        try {
            conexion = DBConnection.connect();

            String sql = "INSERT INTO LINEA_COMPRA (CodigoCompra , IdComprador, Producto, CantCanatillas, pesoUni, CantKilos,VarKilo , VarCompra)"
                    + "VALUES (? ,?,?,?,?,?,?,?)";
            PreparedStatement estado = conexion.prepareStatement(sql);
            estado.setInt(1, Integer.parseInt(codVenta));
            estado.setString(2, tfCompra[0][j].getText());
            estado.setString(3, tfCompra[1][j].getText());
            estado.setInt(4, Integer.parseInt(tfCompra[2][j].getText()));
            estado.setInt(5, Integer.parseInt(tfCompra[3][j].getText()));
            estado.setInt(6, (int) canKG);
            estado.setInt(7, Integer.parseInt(tfCompra[4][j].getText()));
            estado.setInt(8, Integer.parseInt(tfCompra[5][j].getText()));
            estado.executeUpdate();

            estado.close();
            System.out.println("Se ha guardado con exito");
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    public String codCompra() {
        String codVenta = "";
        try {
            conexion = DBConnection.connect();

            String sql = "Select codigocompra from Compra Order By CodigoCompra ASC";
            ResultSet rs = conexion.createStatement().executeQuery(sql);
            while (rs.next()) {
                codVenta = rs.getString("codigoCompra");
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        return codVenta;
    }

    @Override
    public void setPantallaPadre(ControladorVentanasMontero ventana) {
        controlador = ventana;
    }
}
