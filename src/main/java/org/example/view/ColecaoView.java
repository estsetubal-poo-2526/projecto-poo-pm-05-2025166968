package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Carta;
import org.example.model.CartaEspecial;
import org.example.model.Perfil;
import java.util.Map;

public class ColecaoView {
    private Stage stage;
    private Lobby lobby;

    public ColecaoView(Stage stage, Lobby lobby) {
        this.stage = stage;
        this.lobby = lobby;
    }

    public void mostrar() {
        Text txtTitulo = new Text("Coleção");
        txtTitulo.setFont(Font.font(28));

        FlowPane colecaoLayout = new FlowPane(10, 10);
        colecaoLayout.setPadding(new Insets(10));

        for (Map.Entry<String, Carta> entry : Perfil.getInstancia().getColecao().entrySet()) {
            Carta carta = entry.getValue();
            int qtd = Perfil.getInstancia().getQuantidades().get(carta.getNome());
            String info;
            if (carta instanceof CartaEspecial) {
                info = carta.getNome() + "\n[ESPECIAL]\n" + carta.getRaridade() + "\nx" + qtd;
            } else {
                info = carta.getNome() + "\n" + carta.getElemento() +
                        "\nHP:" + carta.getHp() + "\nATK:" + carta.getAtk() +
                        "\nDEF:" + carta.getDef() + "\n" + carta.getRaridade() + "\nx" + qtd;
            }
            Button btnCarta = new Button(info);
            btnCarta.setPrefSize(120, 160);
            colecaoLayout.getChildren().add(btnCarta);
        }

        ScrollPane scroll = new ScrollPane(colecaoLayout);
        scroll.setFitToWidth(true);

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.setOnAction(e -> lobby.mostrar());

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(txtTitulo, scroll, btnVoltar);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}