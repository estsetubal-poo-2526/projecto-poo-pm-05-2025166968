package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.*;
import java.util.List;
import java.util.Map;

public class BaralhosView {
    private Stage stage;
    private Lobby lobby;

    public BaralhosView(Stage stage, Lobby lobby) {
        this.stage = stage;
        this.lobby = lobby;
    }

    private ImageView getImagem(String nome, int width, int height) {
        try {
            String caminho = "/images/" + nome.toLowerCase() + ".png";
            Image img = new Image(getClass().getResourceAsStream(caminho));
            ImageView iv = new ImageView(img);
            iv.setFitWidth(width);
            iv.setFitHeight(height);
            iv.setPreserveRatio(true);
            return iv;
        } catch (Exception e) {
            return null;
        }
    }

    private ImageView getImagemBaralho() {
        try {
            Image img = new Image(getClass().getResourceAsStream("/images/baralho.png"));
            ImageView iv = new ImageView(img);
            iv.setFitWidth(80);
            iv.setFitHeight(80);
            iv.setPreserveRatio(true);
            return iv;
        } catch (Exception e) {
            return null;
        }
    }

    private String getCorElemento(Carta carta) {
        if (carta instanceof CartaEspecial) return "#8e44ad";
        return switch (carta.getElemento()) {
            case Fogo -> "#c0392b";
            case Agua -> "#2980b9";
            case Erva -> "#27ae60";
            case Eletrico -> "#d4a017";
            case Gelo -> "#85c1e9";
            case Voador -> "#5d6d7e";
            default -> "#717d7e";
        };
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

            HBox cardRow = new HBox(8);
            cardRow.setAlignment(Pos.CENTER_LEFT);
            cardRow.setPadding(new Insets(4));
            cardRow.setStyle("-fx-background-color: " + getCorElemento(carta) + "; " +
                    "-fx-background-radius: 8; -fx-cursor: hand;");
            cardRow.setPrefWidth(200);

            ImageView iv = getImagem(carta.getNome(), 30, 30);
            if (iv != null) cardRow.getChildren().add(iv);

            Text txtNome = new Text(carta.getNome() + " [" + carta.getRaridade() + "]");
            txtNome.setStyle("-fx-fill: white; -fx-font-size: 11px; -fx-font-weight: bold;");
            cardRow.getChildren().add(txtNome);

            cardRow.setOnMouseClicked(e -> {
                Perfil.getInstancia().removerDoBaralho(index);
                mostrar();
            });
            baralhoLayout.getChildren().add(cardRow);
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
                alert.setContentText("Já tens 4 baralhos guardados!");
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

        // === CENTRO - Coleção ===
        FlowPane colecaoLayout = new FlowPane(10, 10);
        colecaoLayout.setPadding(new Insets(10));

        for (Map.Entry<String, Carta> entry : Perfil.getInstancia().getColecao().entrySet()) {
            Carta carta = entry.getValue();
            int qtd = Perfil.getInstancia().getQuantidades().get(carta.getNome());

            VBox cardBox = new VBox(3);
            cardBox.setAlignment(Pos.CENTER);
            cardBox.setPrefSize(90, 120);
            cardBox.setPadding(new Insets(4));
            cardBox.setStyle("-fx-background-color: " + getCorElemento(carta) +
                    "; -fx-background-radius: 10; -fx-cursor: hand;");

            ImageView iv = getImagem(carta.getNome(), 50, 50);
            if (iv != null) {
                cardBox.getChildren().add(iv);
            } else {
                Text emoji = new Text(carta instanceof CartaEspecial ? "🧪" : "❓");
                emoji.setStyle("-fx-font-size: 20px;");
                cardBox.getChildren().add(emoji);
            }

            Text txtNome = new Text(carta.getNome());
            txtNome.setStyle("-fx-fill: white; -fx-font-size: 9px; -fx-font-weight: bold;");
            Text txtQtd = new Text("x" + qtd);
            txtQtd.setStyle("-fx-fill: #f5c842; -fx-font-size: 10px; -fx-font-weight: bold;");

            cardBox.getChildren().addAll(txtNome, txtQtd);
            cardBox.setOnMouseClicked(e -> {
                boolean adicionado = Perfil.getInstancia().adicionarAoBaralho(carta);
                if (adicionado) {
                    mostrar();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Não podes adicionar mais esta carta!");
                    alert.showAndWait();
                }
            });
            colecaoLayout.getChildren().add(cardBox);
        }

        ScrollPane scrollCentro = new ScrollPane(colecaoLayout);
        scrollCentro.getStyleClass().add("scroll-pane");
        HBox.setHgrow(scrollCentro, Priority.ALWAYS);

        // === DIREITA - Baralhos guardados ===
        Text txtSalvos = new Text("Baralhos Guardados");
        txtSalvos.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        FlowPane baralhosSalvosLayout = new FlowPane(10, 10);
        baralhosSalvosLayout.setPadding(new Insets(10));

        for (Map.Entry<String, List<Carta>> entry : Perfil.getInstancia().getBaralhosSalvos().entrySet()) {
            String nomeBaralho = entry.getKey();
            List<Carta> cartas = entry.getValue();
            boolean selecionado = nomeBaralho.equals(Perfil.getInstancia().getBaralhoSelecionado());

            VBox cardBaralho = new VBox(8);
            cardBaralho.setAlignment(Pos.CENTER);
            cardBaralho.setPrefSize(130, 170);
            cardBaralho.setPadding(new Insets(10));
            cardBaralho.setStyle(selecionado ?
                    "-fx-background-color: #f5c842; -fx-background-radius: 12; -fx-cursor: hand; " +
                            "-fx-effect: dropshadow(gaussian, rgba(245,200,66,0.6), 15, 0, 0, 0);" :
                    "-fx-background-color: #2a1a4e; -fx-background-radius: 12; -fx-cursor: hand; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 3);");

            // imagem do baralho
            ImageView ivBaralho = getImagemBaralho();

            Text txtNome = new Text(nomeBaralho);
            txtNome.setStyle(selecionado ?
                    "-fx-fill: #1a0a2e; -fx-font-weight: bold; -fx-font-size: 12px;" :
                    "-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");

            Text txtQtd = new Text(cartas.size() + " cartas");
            txtQtd.setStyle(selecionado ?
                    "-fx-fill: #1a0a2e; -fx-font-size: 11px;" :
                    "-fx-fill: rgba(255,255,255,0.7); -fx-font-size: 11px;");

            if (ivBaralho != null) {
                cardBaralho.getChildren().addAll(ivBaralho, txtNome, txtQtd);
            } else {
                Button imgBaralhoBtn = new Button("[Baralho]");
                imgBaralhoBtn.setPrefSize(80, 80);
                imgBaralhoBtn.getStyleClass().add("placeholder");
                imgBaralhoBtn.setMouseTransparent(true);
                cardBaralho.getChildren().addAll(imgBaralhoBtn, txtNome, txtQtd);
            }

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
}