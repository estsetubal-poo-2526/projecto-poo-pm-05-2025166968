package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.*;
import java.util.*;

public class ColecaoView {
    private Stage stage;
    private Lobby lobby;

    public ColecaoView(Stage stage, Lobby lobby) {
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

    public void mostrar() {
        Text txtTitulo = new Text("Coleção");
        txtTitulo.getStyleClass().add("titulo");

        FlowPane colecaoLayout = new FlowPane(15, 15);
        colecaoLayout.setPadding(new Insets(10));

        List<Carta> cartasOrdenadas = new ArrayList<>(Perfil.getInstancia().getColecao().values());
        cartasOrdenadas.sort((a, b) -> {
            if (a instanceof CartaEspecial && !(b instanceof CartaEspecial)) return -1;
            if (!(a instanceof CartaEspecial) && b instanceof CartaEspecial) return 1;
            int elemComp = a.getElemento().toString().compareTo(b.getElemento().toString());
            if (elemComp != 0) return elemComp;
            return getRaridadeOrdem(a.getRaridade()) - getRaridadeOrdem(b.getRaridade());
        });

        for (Carta carta : cartasOrdenadas) {
            int qtd = Perfil.getInstancia().getQuantidades().get(carta.getNome());

            VBox cardBox = new VBox(4);
            cardBox.setAlignment(Pos.CENTER);
            cardBox.setPrefSize(120, 180);
            cardBox.setPadding(new Insets(6));

            String cor = getCorElemento(carta);
            cardBox.setStyle("-fx-background-color: " + cor + "; -fx-background-radius: 12; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);");

            ImageView iv = getImagem(carta.getNome(), 70, 70);
            if (iv != null) {
                cardBox.getChildren().add(iv);
            } else {
                Text emoji = new Text(carta instanceof CartaEspecial ? "🧪" : "❓");
                emoji.setStyle("-fx-font-size: 30px;");
                cardBox.getChildren().add(emoji);
            }

            Text txtNome = new Text(carta.getNome());
            txtNome.setStyle("-fx-fill: white; -fx-font-size: 10px; -fx-font-weight: bold;");

            Text txtRar = new Text(carta.getRaridade().toString());
            txtRar.setStyle("-fx-fill: rgba(255,255,255,0.8); -fx-font-size: 9px;");

            Text txtQtd = new Text("x" + qtd);
            txtQtd.setStyle("-fx-fill: #f5c842; -fx-font-size: 11px; -fx-font-weight: bold;");

            cardBox.getChildren().addAll(txtNome, txtRar, txtQtd);
            colecaoLayout.getChildren().add(cardBox);
        }

        ScrollPane scroll = new ScrollPane(colecaoLayout);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("scroll-pane");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.getStyleClass().add("btn-voltar");
        btnVoltar.setOnAction(e -> lobby.mostrar());

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(txtTitulo, scroll, btnVoltar);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
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

    private int getRaridadeOrdem(Raridade r) {
        return switch (r) {
            case Comum -> 0;
            case Incomum -> 1;
            case Raro -> 2;
            case Epico -> 3;
            case Lendario -> 4;
        };
    }
}