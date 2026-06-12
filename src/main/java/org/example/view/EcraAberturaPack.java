package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Carta;
import org.example.model.CartaEspecial;
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

    public void mostrar() {
        mostrarCarta(cartaAtual);
    }

    private void mostrarCarta(int index) {
        // Contador
        Text txtContador = new Text("Carta " + (index + 1) + " / " + cartas.size());
        txtContador.setStyle("-fx-fill: rgba(255,255,255,0.5); -fx-font-size: 16px;");

        // Título
        Text txtTitulo = new Text("✨ Abertura de Pack ✨");
        txtTitulo.setStyle("-fx-fill: #f5c842; -fx-font-size: 32px; -fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(245,200,66,0.6), 15, 0, 0, 0);");

        // Carta atual
        Carta carta = cartas.get(index);
        String info;
        String raridade = carta.getRaridade().toString();

        if (carta instanceof CartaEspecial) {
            info = carta.getNome() + "\n\n[ESPECIAL]\n\n" + raridade;
        } else {
            info = carta.getNome() + "\n\n" + carta.getElemento() +
                    "\nHP: " + carta.getHp() +
                    "\nATK: " + carta.getAtk() +
                    "\nDEF: " + carta.getDef() +
                    "\n\n" + raridade;
        }

        Button btnCarta = new Button(info);
        btnCarta.setPrefSize(220, 300);
        btnCarta.setMouseTransparent(true);

        // cor da carta baseada na raridade
        String corRaridade = switch (carta.getRaridade()) {
            case Comum -> "#aab7b8";
            case Incomum -> "#27ae60";
            case Raro -> "#2980b9";
            case Epico -> "#8e44ad";
            case Lendario -> "#f5c842";
        };
        btnCarta.setStyle("-fx-background-color: " + corRaridade + "; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 15; " +
                "-fx-effect: dropshadow(gaussian, " + corRaridade + ", 25, 0, 0, 0);");

        // Botão avançar
        Button btnAvancar;
        if (index < cartas.size() - 1) {
            btnAvancar = new Button("Próxima Carta →");
            btnAvancar.getStyleClass().add("btn-primario");
            btnAvancar.setOnAction(e -> mostrarCarta(index + 1));
        } else {
            btnAvancar = new Button("🏆 Ver Coleção");
            btnAvancar.getStyleClass().add("btn-primario");
            btnAvancar.setOnAction(e -> {
                if (lobby != null) {
                    lobby.mostrar();
                } else {
                    new Lobby(stage).mostrar();
                }
            });
        }
        btnAvancar.setPrefWidth(220);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(txtTitulo, txtContador, btnCarta, btnAvancar);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}