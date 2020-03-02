/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainApp;

/**
 *
 * @author Yefry Montero
 */

import Controlador.ControladorVentanasMontero;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class VentanaMontero extends Application{
    public static String MenuPrincipalID = "MenuPrincipal";
    public static String MenuPrincipalFile = "/Vista/MenuPrincipal.fxml";

    public static String VentaID = "Venta";
    public static String VentaFile = "/Vista/Venta.fxml";
    
    public static String CompraID = "Compra";
    public static String CompraFile = "/Vista/Compra.fxml";  

    public static String ImprimirID = "Imprimir";
    public static String ImprimirFile = "/Vista/imprimir.fxml";    

    @Override
    public void start(Stage primaryStage){
    	ControladorVentanasMontero mainContainer = new ControladorVentanasMontero();
    	mainContainer.cargaPantalla(VentanaMontero.MenuPrincipalID,VentanaMontero.MenuPrincipalFile);
    	mainContainer.cargaPantalla(VentanaMontero.VentaID,VentanaMontero.VentaFile);
    	mainContainer.cargaPantalla(VentanaMontero.CompraID,VentanaMontero.CompraFile);
    	mainContainer.cargaPantalla(VentanaMontero.ImprimirID,VentanaMontero.ImprimirFile);

    	mainContainer.setPantalla(VentanaMontero.MenuPrincipalID);
    	Group root = new Group();
    	root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        Screen sc = Screen.getPrimary();
        primaryStage.setScene(scene);;
        primaryStage.show();
    }

}
