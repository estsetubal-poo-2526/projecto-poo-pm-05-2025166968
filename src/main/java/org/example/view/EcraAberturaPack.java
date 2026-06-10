package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Carta;
import org.example.model.CartaEspecial;
import java.util.List;

public class EcraAberturaPack {

    private Stage stage;
    private List<Carta> cartas;
    private int cartaAtual = 0;

    public EcraAberturaPack(Stage stage, List<Carta> cartas) {
        this.stage = stage;
        this.cartas = cartas;
    }

    public void mostrar() {
        mostrarCarta(cartaAtual);
    }

    private void mostrarCarta(int index) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        Text txtTitulo = new Text("Abertura de Pack");
        txtTitulo.setFont(Font.font(32));

        Text txtContador = new Text("Carta " + (index + 1) + " de " + cartas.size());
        txtContador.setFont(Font.font(18));

        Carta carta = cartas.get(index);
        String info;
        if (carta instanceof CartaEspecial) {
            info = carta.getNome() + "\n[ESPECIAL]\n" + carta.getRaridade();
        } else {
            info = carta.getNome() + "\n" + carta.getElemento() +
                    "\nHP:" + carta.getHp() +
                    "\nATK:" + carta.getAtk() +
                    "\nDEF:" + carta.getDef() +
                    "\n" + carta.getRaridade();
        }

        Button btnCarta = new Button(info);
        btnCarta.setPrefSize(180, 250);
        btnCarta.setDisable(false);
        btnCarta.setMouseTransparent(true);

        Button btnAvancar;
        if (index < cartas.size() - 1) {
            btnAvancar = new Button("Próxima Carta →");
            btnAvancar.setOnAction(e -> mostrarCarta(index + 1));
        } else {
            btnAvancar = new Button("Ir para o Lobby");
            btnAvancar.setOnAction(e -> {
                Lobby lobby = new Lobby(stage);
                lobby.mostrar();
            });
        }
        btnAvancar.setPrefWidth(200);

        layout.getChildren().addAll(txtTitulo, txtContador, btnCarta, btnAvancar);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}