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
import java.util.List;

public class DetalheBaralhoView {
    private Stage stage;
    private Lobby lobby;
    private String nomeBaralho;
    private List<Carta> cartas;

    public DetalheBaralhoView(Stage stage, Lobby lobby, String nomeBaralho, List<Carta> cartas) {
        this.stage = stage;
        this.lobby = lobby;
        this.nomeBaralho = nomeBaralho;
        this.cartas = cartas;
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
        Text txtTitulo = new Text(nomeBaralho);
        txtTitulo.getStyleClass().add("titulo");

        Text txtTotal = new Text(cartas.size() + " cartas");
        txtTotal.getStyleClass().add("texto-normal");

        FlowPane cartasLayout = new FlowPane(12, 12);
        cartasLayout.setPadding(new Insets(10));

        for (Carta carta : cartas) {
            VBox cardBox = new VBox(4);
            cardBox.setAlignment(Pos.CENTER);
            cardBox.setPrefSize(110, 160);
            cardBox.setPadding(new Insets(6));
            cardBox.setStyle("-fx-background-color: " + getCorElemento(carta) +
                    "; -fx-background-radius: 12; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);");

            ImageView iv = getImagem(carta.getNome(), 65, 65);
            if (iv != null) {
                cardBox.getChildren().add(iv);
            } else {
                Text emoji = new Text(carta instanceof CartaEspecial ? "🧪" : "❓");
                emoji.setStyle("-fx-font-size: 28px;");
                cardBox.getChildren().add(emoji);
            }

            Text txtNome = new Text(carta.getNome());
            txtNome.setStyle("-fx-fill: white; -fx-font-size: 10px; -fx-font-weight: bold;");

            Text txtRar = new Text(carta.getRaridade().toString());
            txtRar.setStyle("-fx-fill: rgba(255,255,255,0.8); -fx-font-size: 9px;");

            cardBox.getChildren().addAll(txtNome, txtRar);
            cartasLayout.getChildren().add(cardBox);
        }

        ScrollPane scroll = new ScrollPane(cartasLayout);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("scroll-pane");
        scroll.setMaxHeight(450);

        Button btnUsar = new Button("✅ Usar este Baralho");
        btnUsar.getStyleClass().add("btn-primario");
        btnUsar.setPrefWidth(250);
        btnUsar.setOnAction(e -> {
            Perfil.getInstancia().selecionarBaralho(nomeBaralho);
            lobby.mostrar();
        });

        Button btnEliminar = new Button("🗑 Eliminar Baralho");
        btnEliminar.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 10 25 10 25; -fx-cursor: hand;");
        btnEliminar.setPrefWidth(250);
        btnEliminar.setOnAction(e -> {
            Perfil.getInstancia().getBaralhosSalvos().remove(nomeBaralho);
            if (nomeBaralho.equals(Perfil.getInstancia().getBaralhoSelecionado())) {
                Perfil.getInstancia().setBaralhoSelecionado(null);
            }
            new BaralhosView(stage, lobby).mostrar();
        });

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.getStyleClass().add("btn-voltar");
        btnVoltar.setOnAction(e -> new BaralhosView(stage, lobby).mostrar());

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(txtTitulo, txtTotal, scroll, btnUsar, btnEliminar, btnVoltar);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}