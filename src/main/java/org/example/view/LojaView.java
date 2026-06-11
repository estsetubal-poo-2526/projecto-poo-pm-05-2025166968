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

        // Packs
        VBox packBasico = criarPack("Pack Básico", "6 cartas aleatórias",
                "60% Comum  •  28% Incomum\n9% Raro  •  2.5% Épico  •  0.5% Lendário",
                "50", "#e74c3c", 50);

        VBox packRaro = criarPack("Pack Raro", "6 cartas aleatórias",
                "24% Comum  •  29% Incomum\n37% Raro  •  8% Épico  •  2% Lendário",
                "100", "#2980b9", 100);

        VBox packEpico = criarPack("Pack Épico", "6 cartas aleatórias",
                "18% Comum  •  23% Incomum\n33% Raro  •  19% Épico  •  7% Lendário",
                "200", "#8e44ad", 200);

        HBox packs = new HBox(30);
        packs.setAlignment(Pos.CENTER);
        packs.getChildren().addAll(packBasico, packRaro, packEpico);

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

    private VBox criarPack(String nome, String descricao, String odds, String preco, String cor, int custo) {
        Text txtNome = new Text(nome);
        txtNome.setStyle("-fx-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        Text txtDescricao = new Text(descricao);
        txtDescricao.setStyle("-fx-fill: rgba(255,255,255,0.8); -fx-font-size: 13px;");

        // placeholder imagem pack
        Button imgPack = new Button("[Pack]");
        imgPack.setPrefSize(120, 160);
        imgPack.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: rgba(255,255,255,0.4); " +
                "-fx-background-radius: 10; -fx-border-color: rgba(255,255,255,0.2); -fx-border-radius: 10;");
        imgPack.setMouseTransparent(true);

        Text txtOdds = new Text(odds);
        txtOdds.setStyle("-fx-fill: rgba(255,255,255,0.65); -fx-font-size: 11px;");

        Button btnComprar = new Button("Comprar  💰 " + preco);
        btnComprar.getStyleClass().add("btn-primario");
        btnComprar.setPrefWidth(200);
        btnComprar.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= custo) {
                Perfil.getInstancia().removerMoedas(custo);
                List<Carta> cartas = new ArrayList<>(GeradorCartas.abrirPack());
                for (Carta carta : cartas) Perfil.getInstancia().adicionarCarta(carta);
                EcraAberturaPack ecra = new EcraAberturaPack(stage, cartas);
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
        box.setPadding(new Insets(25));
        box.setPrefSize(280, 380);
        box.setStyle("-fx-background-color: " + cor + "; -fx-background-radius: 18; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 20, 0, 0, 6);");
        box.getChildren().addAll(txtNome, imgPack, txtDescricao, txtOdds, btnComprar);

        return box;
    }
}