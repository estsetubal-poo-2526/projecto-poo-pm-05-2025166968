package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Carta;
import org.example.model.CartaEspecial;
import org.example.model.Elemento;
import org.example.model.Perfil;
import java.util.List;
import java.util.Map;

public class BaralhosView {
    private Stage stage;
    private Lobby lobby;

    public BaralhosView(Stage stage, Lobby lobby) {
        this.stage = stage;
        this.lobby = lobby;
    }

    public void mostrar() {
        Text txtTitulo = new Text("Baralhos");
        txtTitulo.getStyleClass().add("titulo");

        // === ESQUERDA - Baralho atual ===
        Text txtBaralho = new Text("Baralho (" + Perfil.getInstancia().getBaralhoAtual().size() + "/15)");
        txtBaralho.getStyleClass().add("texto-normal");

        VBox baralhoLayout = new VBox(5);
        baralhoLayout.setPadding(new Insets(10));
        baralhoLayout.getChildren().add(txtBaralho);

        for (int i = 0; i < Perfil.getInstancia().getBaralhoAtual().size(); i++) {
            Carta carta = Perfil.getInstancia().getBaralhoAtual().get(i);
            final int index = i;
            Button btnCarta = new Button(carta.getNome() + " [" + carta.getRaridade() + "]");
            btnCarta.setPrefWidth(200);
            btnCarta.getStyleClass().add(getCardStyle(carta));
            btnCarta.setOnAction(e -> {
                Perfil.getInstancia().removerDoBaralho(index);
                mostrar();
            });
            baralhoLayout.getChildren().add(btnCarta);
        }

        TextField txtNomeBaralho = new TextField();
        txtNomeBaralho.setPromptText("Nome do baralho...");
        txtNomeBaralho.setPrefWidth(200);

        Button btnGuardar = new Button("💾 Guardar");
        btnGuardar.getStyleClass().add("btn-primario");
        btnGuardar.setPrefWidth(200);
        btnGuardar.setOnAction(e -> {
            String nome = txtNomeBaralho.getText().trim();
            if (nome.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Escreve um nome para o baralho!");
                alert.showAndWait();
            } else if (Perfil.getInstancia().getBaralhoAtual().size() < 15) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("O baralho tem de ter 15 cartas!");
                alert.showAndWait();
            } else if (Perfil.getInstancia().getBaralhosSalvos().size() >= 4) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Já tens 4 baralhos guardados! Apaga um para criar outro.");
                alert.showAndWait();
            } else {
                Perfil.getInstancia().salvarBaralho(nome);
                mostrar();
            }
        });

        baralhoLayout.getChildren().addAll(txtNomeBaralho, btnGuardar);

        ScrollPane scrollEsquerdo = new ScrollPane(baralhoLayout);
        scrollEsquerdo.setPrefWidth(230);
        scrollEsquerdo.getStyleClass().add("scroll-pane");

        // === CENTRO - Coleção para selecionar ===
        FlowPane colecaoLayout = new FlowPane(10, 10);
        colecaoLayout.setPadding(new Insets(10));

        for (Map.Entry<String, Carta> entry : Perfil.getInstancia().getColecao().entrySet()) {
            Carta carta = entry.getValue();
            int qtd = Perfil.getInstancia().getQuantidades().get(carta.getNome());
            Button btnCarta = new Button(carta.getNome() + "\n" + carta.getRaridade() + "\nx" + qtd);
            btnCarta.setPrefSize(110, 80);
            btnCarta.getStyleClass().add(getCardStyle(carta));
            btnCarta.setOnAction(e -> {
                boolean adicionado = Perfil.getInstancia().adicionarAoBaralho(carta);
                if (adicionado) {
                    mostrar();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Não podes adicionar mais esta carta ao baralho!");
                    alert.showAndWait();
                }
            });
            colecaoLayout.getChildren().add(btnCarta);
        }

        ScrollPane scrollCentro = new ScrollPane(colecaoLayout);
        scrollCentro.getStyleClass().add("scroll-pane");
        HBox.setHgrow(scrollCentro, Priority.ALWAYS);

        // === DIREITA - Baralhos guardados ===
        Text txtSalvos = new Text("Baralhos Guardados");
        txtSalvos.getStyleClass().add("texto-normal");
        txtSalvos.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        FlowPane baralhosSalvosLayout = new FlowPane(10, 10);
        baralhosSalvosLayout.setPadding(new Insets(10));

        for (Map.Entry<String, List<Carta>> entry : Perfil.getInstancia().getBaralhosSalvos().entrySet()) {
            String nomeBaralho = entry.getKey();
            List<Carta> cartas = entry.getValue();
            boolean selecionado = nomeBaralho.equals(Perfil.getInstancia().getBaralhoSelecionado());

            VBox cardBaralho = new VBox(8);
            cardBaralho.setAlignment(Pos.CENTER);
            cardBaralho.setPrefSize(130, 160);
            cardBaralho.setPadding(new Insets(10));
            cardBaralho.setStyle(selecionado ?
                    "-fx-background-color: #f5c842; -fx-background-radius: 12; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(245,200,66,0.6), 15, 0, 0, 0);" :
                    "-fx-background-color: #2a1a4e; -fx-background-radius: 12; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 3);");

            Button imgBaralho = new Button("[Baralho]");
            imgBaralho.setPrefSize(80, 80);
            imgBaralho.getStyleClass().add("placeholder");
            imgBaralho.setMouseTransparent(true);

            Text txtNome = new Text(nomeBaralho);
            txtNome.setStyle(selecionado ?
                    "-fx-fill: #1a0a2e; -fx-font-weight: bold; -fx-font-size: 12px;" :
                    "-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");

            Text txtQtd = new Text(cartas.size() + " cartas");
            txtQtd.setStyle(selecionado ?
                    "-fx-fill: #1a0a2e; -fx-font-size: 11px;" :
                    "-fx-fill: rgba(255,255,255,0.7); -fx-font-size: 11px;");

            cardBaralho.getChildren().addAll(imgBaralho, txtNome, txtQtd);
            cardBaralho.setOnMouseClicked(e ->
                    new DetalheBaralhoView(stage, lobby, nomeBaralho, cartas).mostrar());

            baralhosSalvosLayout.getChildren().add(cardBaralho);
        }

        ScrollPane scrollDireito = new ScrollPane(baralhosSalvosLayout);
        scrollDireito.setPrefWidth(230);
        scrollDireito.getStyleClass().add("scroll-pane");

        VBox ladoDireito = new VBox(10);
        ladoDireito.setPadding(new Insets(10));
        VBox.setVgrow(scrollDireito, Priority.ALWAYS);
        ladoDireito.getChildren().addAll(txtSalvos, scrollDireito);

        // === LAYOUT 3 COLUNAS ===
        HBox conteudo = new HBox(10);
        conteudo.getChildren().addAll(scrollEsquerdo, scrollCentro, ladoDireito);

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.getStyleClass().add("btn-voltar");
        btnVoltar.setOnAction(e -> lobby.mostrar());

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));
        VBox.setVgrow(conteudo, Priority.ALWAYS);
        layout.getChildren().addAll(txtTitulo, conteudo, btnVoltar);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private String getCardStyle(Carta carta) {
        if (carta instanceof CartaEspecial) return "card-especial";
        if (carta.getElemento() == Elemento.Fogo) return "card-fogo";
        if (carta.getElemento() == Elemento.Agua) return "card-agua";
        if (carta.getElemento() == Elemento.Erva) return "card-erva";
        if (carta.getElemento() == Elemento.Eletrico) return "card-eletrico";
        if (carta.getElemento() == Elemento.Gelo) return "card-gelo";
        if (carta.getElemento() == Elemento.Voador) return "card-voador";
        return "card-normal";
    }
}