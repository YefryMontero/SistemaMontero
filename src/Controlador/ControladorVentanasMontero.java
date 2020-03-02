/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author Yefry Montero
 */
import Controlador.ControladorVentanas;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class ControladorVentanasMontero extends StackPane {

    private HashMap<String, Node> pantallas = new HashMap<>();

    public ControladorVentanasMontero() {
        super();
    }

    public void addPantalla(String nombre, Node pantalla) {
        pantallas.put(nombre, pantalla);
    }

    public Node getPantalla(String nombre) {
        return pantallas.get(nombre);
    }

    public boolean cargaPantalla(String nombre, String fxml) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent loadScreen = (Parent) myLoader.load();
            ControladorVentanas myControladorventanas = ((ControladorVentanas) myLoader.getController());
            myControladorventanas.setPantallaPadre(this);
            addPantalla(nombre, loadScreen);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean setPantalla(final String nombre) {
        Node screenToRemove;
        if (pantallas.get(nombre) != null) {   //Pantalla cargada
            if (!getChildren().isEmpty()) {    //Si aqui hay mas de una pantalla
                getChildren().add(0, pantallas.get(nombre));     //añade la pantalla
                screenToRemove = getChildren().get(1);
                getChildren().remove(1);                    // remueve la pantalla mostrada
            } else {
                getChildren().add(pantallas.get(nombre));       // si no se ha cargado nada solo muestra
            }
            return true;
        } else {
            System.out.println("La pantalla no ah sido bien cargarda!!! \n");
            return false;
        }
    }
}
