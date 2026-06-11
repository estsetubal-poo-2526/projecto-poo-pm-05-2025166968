package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.model.Perfil;
import org.example.view.MenuPrincipal;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Perfil.carregar();
        stage.setTitle("Card Arena");
        stage.setMaximized(true);
        MenuPrincipal menu = new MenuPrincipal(stage);
        menu.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}