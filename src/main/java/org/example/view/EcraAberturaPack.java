package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Carta;
import org.example.model.CartaEspecial;
import org.example.model.Pocao;
import java.util.List;

public class EcraAberturaPack {

    private Stage stage;
    private List<Carta> cartas;
    private int cartaAtual = 0;
    private Lobby lobby;

    public EcraAberturaPack(Stage stage, List<Carta> cartas) {
        this.stage = stage;
        this.cartas = cartas;
    }

    public void setLobby(Lobby lobby) {
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
        mostrarCarta(cartaAtual);
    }

    private void mostrarCarta(int index) {
        Text txtContador = new Text("Carta " + (index + 1) + " / " + cartas.size());
        txtContador.setStyle("-fx-fill: rgba(255,255,255,0.5); -fx-font-size: 16px;");

        Text txtTitulo = new Text("✨ Abertura de Pack ✨");
        txtTitulo.setStyle("-fx-fill: #f5c842; -fx-font-size: 32px; -fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(245,200,66,0.6), 15, 0, 0, 0);");

        Carta carta = cartas.get(index);

        // cor baseada na raridade
        String corRaridade = switch (carta.getRaridade()) {
            case Comum -> "#aab7b8";
            case Incomum -> "#27ae60";
            case Raro -> "#2980b9";
            case Epico -> "#8e44ad";
            case Lendario -> "#f5c842";
        };

        // card com imagem
        VBox cardBox = new VBox(8);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPrefSize(220, 300);
        cardBox.setPadding(new Insets(15));
        cardBox.setStyle("-fx-background-color: " + corRaridade + "; -fx-background-radius: 15; " +
                "-fx-effect: dropshadow(gaussian, " + corRaridade + ", 25, 0, 0, 0);");

        ImageView iv = getImagem(carta.getNome(), 130, 130);
        if (iv != null) {
            cardBox.getChildren().add(iv);
        } else {
            Text emoji = new Text(carta instanceof Pocao ? "🧪" : carta instanceof CartaEspecial ? "👤" : "❓");
            emoji.setStyle("-fx-font-size: 60px;");
            cardBox.getChildren().add(emoji);
        }

        Text txtNome = new Text(carta.getNome());
        txtNome.setStyle("-fx-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        Text txtRaridade = new Text("⭐ " + carta.getRaridade());
        txtRaridade.setStyle("-fx-fill: white; -fx-font-size: 13px;");

        String info;
        if (carta instanceof CartaEspecial) {
            info = "[ESPECIAL]";
        } else {
            info = "HP:" + carta.getHp() + "  ATK:" + carta.getAtk() + "  DEF:" + carta.getDef();
        }
        Text txtStats = new Text(info);
        txtStats.setStyle("-fx-fill: rgba(255,255,255,0.85); -fx-font-size: 12px;");

        cardBox.getChildren().addAll(txtNome, txtRaridade, txtStats);

        Button btnAvancar;
        if (index < cartas.size() - 1) {
            btnAvancar = new Button("Próxima Carta →");
            btnAvancar.getStyleClass().add("btn-primario");
            btnAvancar.setOnAction(e -> mostrarCarta(index + 1));
        } else {
            btnAvancar = new Button("🏆 Ver Coleção");
            btnAvancar.getStyleClass().add("btn-primario");
            btnAvancar.setOnAction(e -> {
                if (lobby != null) lobby.mostrar();
                else new Lobby(stage).mostrar();
            });
        }
        btnAvancar.setPrefWidth(220);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(txtTitulo, txtContador, cardBox, btnAvancar);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}