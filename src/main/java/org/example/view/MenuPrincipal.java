package org.example.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Perfil;

public class MenuPrincipal {

    private Stage stage;

    public MenuPrincipal(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        Text txtTitulo = new Text("CARD ARENA");
        txtTitulo.setStyle("-fx-fill: #f5c842; -fx-font-size: 80px; -fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(245,200,66,0.8), 30, 0, 0, 0);");

        Text txtSubtitulo = new Text("O Jogo de Cartas Definitivo");
        txtSubtitulo.setStyle("-fx-fill: rgba(255,255,255,0.7); -fx-font-size: 20px;");

        Button btnNovoJogo = new Button("⚔ Novo Jogo");
        btnNovoJogo.getStyleClass().add("btn-primario");
        btnNovoJogo.setPrefWidth(280);
        btnNovoJogo.setPrefHeight(55);
        btnNovoJogo.setOnAction(e -> {
            Lobby lobby = new Lobby(stage);
            lobby.mostrar();
        });

        Button btnSair = new Button("Sair");
        btnSair.getStyleClass().add("btn-secundario");
        btnSair.setPrefWidth(280);
        btnSair.setPrefHeight(45);
        btnSair.setOnAction(e -> {
            Perfil.guardar();
            stage.close();
        });

        Text txtVersao = new Text("v1.0 — POO 2025/26");
        txtVersao.setStyle("-fx-fill: rgba(255,255,255,0.3); -fx-font-size: 12px;");

        VBox layout = new VBox(25);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(txtTitulo, txtSubtitulo, btnNovoJogo, btnSair, txtVersao);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}