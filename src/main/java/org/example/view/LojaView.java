package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Carta;
import org.example.model.GeradorCartas;
import org.example.model.Perfil;
import java.util.ArrayList;
import java.util.List;

public class LojaView {
    private Stage stage;
    private Lobby lobby;

    public LojaView(Stage stage, Lobby lobby) {
        this.stage = stage;
        this.lobby = lobby;
    }

    public void mostrar() {
        Text txtTitulo = new Text("Loja");
        txtTitulo.getStyleClass().add("titulo");

        Text txtMoedas = new Text("💰 " + Perfil.getInstancia().getMoedas() + " moedas");
        txtMoedas.getStyleClass().add("texto-moedas");

        // Pack Básico
        VBox packBasicoBox = criarPack("Pack Básico", "6 cartas", "50 moedas",
                "60% Comum\n28% Incomum\n9% Raro\n2.5% Épico\n0.5% Lendário",
                "#e74c3c", 50);

        // Pack Raro
        VBox packRaroBox = criarPack("Pack Raro", "6 cartas", "100 moedas",
                "24% Comum\n29% Incomum\n37% Raro\n8% Épico\n2% Lendário",
                "#2980b9", 100);

        // Pack Épico
        VBox packEpicoBox = criarPack("Pack Épico", "6 cartas", "200 moedas",
                "18% Comum\n23% Incomum\n33% Raro\n19% Épico\n7% Lendário",
                "#8e44ad", 200);

        HBox packs = new HBox(30);
        packs.setAlignment(Pos.CENTER);
        packs.getChildren().addAll(packBasicoBox, packRaroBox, packEpicoBox);

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.getStyleClass().add("btn-voltar");
        btnVoltar.setOnAction(e -> lobby.mostrar());

        VBox layout = new VBox(25);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(txtTitulo, txtMoedas, packs, btnVoltar);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private VBox criarPack(String nome, String cartas, String preco, String odds, String cor, int custo) {
        Text txtNome = new Text(nome);
        txtNome.setStyle("-fx-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Text txtCartas = new Text(cartas);
        txtCartas.setStyle("-fx-fill: rgba(255,255,255,0.8); -fx-font-size: 13px;");

        Text txtOdds = new Text(odds);
        txtOdds.setStyle("-fx-fill: rgba(255,255,255,0.7); -fx-font-size: 11px;");

        Button btnComprar = new Button("Comprar - " + preco);
        btnComprar.getStyleClass().add("btn-primario");
        btnComprar.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= custo) {
                Perfil.getInstancia().removerMoedas(custo);
                List<Carta> cartasObtidas = new ArrayList<>(GeradorCartas.abrirPack());
                for (Carta carta : cartasObtidas) Perfil.getInstancia().adicionarCarta(carta);
                EcraAberturaPack ecra = new EcraAberturaPack(stage, cartasObtidas);
                ecra.setLobby(lobby);
                ecra.mostrar();
            } else {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.WARNING);
                alert.setTitle("Sem moedas");
                alert.setHeaderText(null);
                alert.setContentText("Não tens moedas suficientes!");
                alert.showAndWait();
            }
        });

        VBox box = new VBox(12);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setPrefSize(270, 300);
        box.setStyle("-fx-background-color: " + cor + "; -fx-background-radius: 15; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5);");
        box.getChildren().addAll(txtNome, txtCartas, txtOdds, btnComprar);

        return box;
    }
}