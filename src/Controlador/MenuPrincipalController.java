/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import MainApp.VentanaMontero;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Yefry Montero
 */
public class MenuPrincipalController implements Initializable, ControladorVentanas {
ControladorVentanasMontero controlador; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    void mostrarCompras(ActionEvent event) {
          controlador.setPantalla(VentanaMontero.CompraID);
    }

    @FXML
    void mostrarVentas(ActionEvent event) throws SQLException {
    controlador.setPantalla(VentanaMontero.VentaID);
    }
      @FXML
     void mostrarImprimir(ActionEvent event){
         controlador.setPantalla(VentanaMontero.ImprimirID);
     }

    @Override
    public void setPantallaPadre(ControladorVentanasMontero ventana) {
       controlador = ventana;
    }
    
}
