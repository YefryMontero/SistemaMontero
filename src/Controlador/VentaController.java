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
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Yefry Montero
 */
public class VentaController implements Initializable, ControladorVentanas {

    /**
     * Initializes the controller class.
     */
    Validaciones validacion = new Validaciones();
    private Connection conexion;
    ControladorVentanasMontero controlador;
    @FXML
    private GridPane gridVenta;

    @FXML
    private DatePicker dateFecha;

    @FXML
    private Button btnAñadir;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnAtras;

    @FXML
    private TextField[][] tfventa;
    private int numlinea = 2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            tfventa = new TextField[7][100];
            gridVenta.add(tfventa[4][0] = new TextField("0"), 4, 0);
            gridVenta.add(tfventa[0][numlinea] = new TextField(), 0, 2);
            gridVenta.add(tfventa[1][numlinea] = new TextField(), 1, 2);
            gridVenta.add(tfventa[2][numlinea] = new TextField("0"), 2, 2);
            gridVenta.add(tfventa[3][numlinea] = new TextField("0"), 3, 2);
            gridVenta.add(tfventa[4][numlinea] = new TextField("0"), 4, 2);
            gridVenta.add(tfventa[5][numlinea] = new TextField(), 5, 2);
            vrTotal();

        

    }

    @FXML
    void add(ActionEvent event) {
        
        if (numlinea == 10) {
            JOptionPane.showMessageDialog(null, "Ya alcanzo el limite");
        } else {
            numlinea++;
            gridVenta.add(tfventa[0][numlinea] = new TextField(), 0, numlinea);
            gridVenta.add(tfventa[1][numlinea] = new TextField(), 1, numlinea);
            gridVenta.add(tfventa[2][numlinea] = new TextField("0"), 2, numlinea);
            gridVenta.add(tfventa[3][numlinea] = new TextField("0"), 3, numlinea);
            gridVenta.add(tfventa[4][numlinea] = new TextField("0"), 4, numlinea);
            gridVenta.add(tfventa[5][numlinea] = new TextField(), 5, numlinea);
            vrTotal();

        }
    }

    public void vrTotal() {

        tfventa[3][numlinea].focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {System.out.println(numlinea);
                int vrtotal = 0;
                for (int i = 2; i <= numlinea; i++) {
                    vrtotal += Integer.parseInt(tfventa[3][i].getText())
                            + Integer.parseInt(tfventa[4][i].getText());
                }
                tfventa[4][0].setText(
                        String.valueOf(vrtotal));
            }
        });
        tfventa[4][numlinea].focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {System.out.println(numlinea);
                int vrtotal = 0;
                for (int i = 2; i <= numlinea; i++) {
                    vrtotal += Integer.parseInt(tfventa[3][i].getText())
                            + Integer.parseInt(tfventa[4][i].getText());
                }
                tfventa[4][0].setText(
                        String.valueOf(vrtotal));
            }
        });

    }

    @FXML
    public void Guardar() {

        try {
            conexion = DBConnection.connect();
            String sql = "INSERT INTO VENTA "
                    + " ( Fecha, ValorVenta) "
                    + " VALUES ( ?, ?)";
            PreparedStatement estado = conexion.prepareStatement(sql);
//            estado.setInt(1, n);
            estado.setDate(1, Date.valueOf(dateFecha.getValue()));
            estado.setInt(2, Integer.parseInt(tfventa[4][0].getText()));

            estado.executeUpdate();

            estado.close();
            System.out.println(codVenta());
            for (int j = 2; j <= numlinea; j++) {
                guardarlineaventa(j, codVenta());
            }

        } catch (SQLException e) {
            System.out.println("Error " + e);
        }

    }

    @FXML
    public void Atras() {
        controlador.setPantalla(VentanaMontero.MenuPrincipalID);
    }

    public void guardarlineaventa(int j, String codVenta) {
        double vraso = Integer.parseInt(tfventa[3][j].getText()) * 0.01;
        double vrdes = Integer.parseInt(tfventa[3][j].getText()) * 0.015;
        try {
            conexion = DBConnection.connect();

            String sql = "INSERT INTO LINEA_VENTA (CodigoVenta , IdComprador, NumFactura, ValorFactura, ValorCancelado, FletesyViaticos,ValorAsofrucol, ValorDescuento,kilosTomate)"
                    + "VALUES (? ,?,?,?,?,?,?,?,?)";
            PreparedStatement estado = conexion.prepareStatement(sql);
            estado.setInt(1, Integer.parseInt(codVenta));
            estado.setString(2, tfventa[0][j].getText());
            estado.setInt(3, Integer.parseInt(tfventa[1][j].getText()));
            estado.setInt(4, Integer.parseInt(tfventa[2][j].getText()));
            estado.setInt(5, Integer.parseInt(tfventa[3][j].getText()));
            estado.setInt(6, Integer.parseInt(tfventa[4][j].getText()));
            estado.setInt(7, (int) vraso);
            estado.setInt(8, (int) vrdes);
            estado.setInt(9, Integer.parseInt(tfventa[5][j].getText()));
            estado.executeUpdate();

            estado.close();
            System.out.println("Controlador.VentaController.guardarlineaventa()");
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    public String codVenta() {
        String codVenta = "";
        try {
            conexion = DBConnection.connect();

            String sql = "Select codigoVenta from Venta Order By CodigoVenta ASC";
            ResultSet rs = conexion.createStatement().executeQuery(sql);
            while (rs.next()) {
                codVenta = rs.getString("codigoVenta");
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
