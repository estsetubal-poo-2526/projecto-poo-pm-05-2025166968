package org.example.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
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

        String nomeBaralhoAtual = Perfil.getInstancia().getBaralhoSelecionado() != null ?
                Perfil.getInstancia().getBaralhoSelecionado() : "Sem baralho";

        Button btnImgBaralho = new Button(nomeBaralhoAtual);
        btnImgBaralho.setPrefSize(200, 260);
        btnImgBaralho.getStyleClass().add("placeholder");
        btnImgBaralho.setMouseTransparent(true);

        VBox baralhoContainer = new VBox(10);
        baralhoContainer.setAlignment(Pos.TOP_LEFT);
        VBox.setMargin(btnImgBaralho, new Insets(60, 0, 0, 0));
        baralhoContainer.getChildren().add(btnImgBaralho);

        VBox topoEsquerdo = new VBox(10);
        topoEsquerdo.setAlignment(Pos.TOP_LEFT);
        topoEsquerdo.setPadding(new Insets(20, 20, 20, 20));
        topoEsquerdo.getChildren().addAll(txtNome, baralhoContainer);

        // === CANTO SUPERIOR DIREITO ===
        Text txtMoedas = new Text("💰 " + Perfil.getInstancia().getMoedas() + " moedas");
        txtMoedas.getStyleClass().add("texto-moedas");

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
        btnIniciarJogo.getStyleClass().add("btn-primario");
        btnIniciarJogo.setPrefWidth(220);
        btnIniciarJogo.setOnAction(e -> {
            if (Perfil.getInstancia().getBaralhoSelecionado() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Sem baralho");
                alert.setHeaderText(null);
                alert.setContentText("Tens de selecionar um baralho antes de iniciar o jogo!\nVai a Baralhos e clica em 'Usar este Baralho'.");
                alert.showAndWait();
                return;
            }
            JogoController controller = new JogoController(stage);
            controller.iniciarJogo();
        });

        Button btnSair = new Button("Sair");
        btnSair.getStyleClass().add("btn-secundario");
        btnSair.setPrefWidth(220);
        btnSair.setOnAction(e -> {
            Perfil.guardar();
            stage.close();
        });

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
        btnColecao.getStyleClass().add("btn-nav-colecao");
        btnColecao.setPrefSize(380, 90);
        btnColecao.setOnAction(e -> new ColecaoView(stage, this).mostrar());

        Button btnBaralhos = new Button("Baralhos");
        btnBaralhos.getStyleClass().add("btn-nav-baralhos");
        btnBaralhos.setPrefSize(380, 90);
        btnBaralhos.setOnAction(e -> new BaralhosView(stage, this).mostrar());

        Button btnLoja = new Button("Loja");
        btnLoja.getStyleClass().add("btn-nav-loja");
        btnLoja.setPrefSize(380, 90);
        btnLoja.setOnAction(e -> new LojaView(stage, this).mostrar());

        HBox inferior = new HBox(20);
        inferior.setAlignment(Pos.BOTTOM_CENTER);
        inferior.setPadding(new Insets(0, 20, 20, 20));
        inferior.getChildren().addAll(btnColecao, btnBaralhos, btnLoja);

        // === LAYOUT PRINCIPAL ===
        VBox layout = new VBox(0);
        VBox.setVgrow(topo, Priority.ALWAYS);
        layout.getChildren().addAll(topo, inferior);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}