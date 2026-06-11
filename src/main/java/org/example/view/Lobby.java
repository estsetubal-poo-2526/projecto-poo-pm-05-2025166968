package org.example.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.JogoController;
import org.example.model.Perfil;

public class Lobby {
    private Stage stage;

    public Lobby(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {

        // === CANTO SUPERIOR ESQUERDO ===
        Text txtNome = new Text(Perfil.getInstancia().getNome());
        txtNome.getStyleClass().add("texto-normal");

        Button btnImgBaralho = new Button("[Baralho]");
        btnImgBaralho.setPrefSize(200, 200);
        btnImgBaralho.getStyleClass().add("placeholder");
        btnImgBaralho.setMouseTransparent(true);

        VBox topoEsquerdo = new VBox(10);
        topoEsquerdo.setAlignment(Pos.TOP_LEFT);
        topoEsquerdo.setPadding(new Insets(20, 20, 20, 20));

        VBox baralhoContainer = new VBox(10);
        baralhoContainer.setAlignment(Pos.TOP_LEFT);
        VBox.setMargin(btnImgBaralho, new Insets(100, 100, 100, 100)); // desce o baralho
        baralhoContainer.getChildren().add(btnImgBaralho);
        topoEsquerdo.getChildren().addAll(txtNome, btnImgBaralho);

        // === CANTO SUPERIOR DIREITO ===
        Text txtMoedas = new Text("💰 " + Perfil.getInstancia().getMoedas() + " moedas");
        txtMoedas.getStyleClass().add("moedas");

        VBox topoDireito = new VBox(10);
        topoDireito.setAlignment(Pos.TOP_RIGHT);
        topoDireito.setPadding(new Insets(20));
        topoDireito.getChildren().add(txtMoedas);

        // === CENTRO ===
        Button btnImgTreinador = new Button("[Treinador]");
        btnImgTreinador.setPrefSize(150, 200);
        btnImgTreinador.getStyleClass().add("placeholder");
        btnImgTreinador.setMouseTransparent(true);

        Button btnIniciarJogo = new Button("⚔ Iniciar Jogo");
        btnIniciarJogo.getStyleClass().add("btn-iniciar");
        btnIniciarJogo.setPrefWidth(200);
        btnIniciarJogo.setOnAction(e -> {
            JogoController controller = new JogoController(stage);
            controller.iniciarJogo();
        });

        Button btnSair = new Button("Sair");
        btnSair.getStyleClass().add("btn-sair");
        btnSair.setPrefWidth(200);
        btnSair.setOnAction(e -> stage.close());

        VBox centro = new VBox(15);
        centro.setAlignment(Pos.CENTER);
        centro.getChildren().addAll(btnImgTreinador, btnIniciarJogo, btnSair);

        // === TOPO ===
        BorderPane topo = new BorderPane();
        topo.setLeft(topoEsquerdo);
        topo.setCenter(centro);
        topo.setRight(topoDireito);
        topo.setPrefHeight(400);

        // === PARTE INFERIOR ===
        Button btnColecao = new Button("Coleção");
        btnColecao.getStyleClass().add("btn-colecao");
        btnColecao.setPrefSize(426, 100);
        btnColecao.setStyle("");
        btnColecao.setOnAction(e -> new ColecaoView(stage, this).mostrar());

        Button btnBaralhos = new Button("Baralhos");
        btnBaralhos.getStyleClass().add("btn-baralhos");
        btnBaralhos.setPrefSize(426, 100);
        btnBaralhos.setStyle("");
        btnBaralhos.setOnAction(e -> new BaralhosView(stage, this).mostrar());

        Button btnLoja = new Button("Loja");
        btnLoja.getStyleClass().add("btn-loja");
        btnLoja.setPrefSize(428, 100);
        btnLoja.setStyle("");
        btnLoja.setOnAction(e -> new LojaView(stage, this).mostrar());

        HBox inferior = new HBox(15);
        inferior.getStyleClass().add("area-inferior");
        inferior.setAlignment(Pos.BOTTOM_CENTER);
        inferior.getChildren().addAll(btnColecao, btnBaralhos, btnLoja);

        // === LAYOUT PRINCIPAL ===
        VBox layout = new VBox(0);
        VBox.setVgrow(topo, Priority.ALWAYS);
        layout.getChildren().addAll(topo, inferior);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/lobby.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}