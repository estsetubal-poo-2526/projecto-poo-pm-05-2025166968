package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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

    public void mostrar() {
        Text txtTitulo = new Text("Coleção");
        txtTitulo.getStyleClass().add("titulo");

        FlowPane colecaoLayout = new FlowPane(15, 15);
        colecaoLayout.setPadding(new Insets(10));

        // ordena por elemento e depois por raridade
        List<Carta> cartasOrdenadas = new ArrayList<>(Perfil.getInstancia().getColecao().values());
        cartasOrdenadas.sort((a, b) -> {
            // especiais primeiro
            if (a instanceof CartaEspecial && !(b instanceof CartaEspecial)) return -1;
            if (!(a instanceof CartaEspecial) && b instanceof CartaEspecial) return 1;
            // ordena por elemento
            int elemComp = a.getElemento().toString().compareTo(b.getElemento().toString());
            if (elemComp != 0) return elemComp;
            // ordena por raridade
            return getRaridadeOrdem(a.getRaridade()) - getRaridadeOrdem(b.getRaridade());
        });

        Elemento elemAtual = null;
        for (Carta carta : cartasOrdenadas) {
            // cabeçalho de elemento
            if (!(carta instanceof CartaEspecial) && !carta.getElemento().equals(elemAtual)) {
                elemAtual = carta.getElemento();
                Text txtElem = new Text("── " + elemAtual + " ──");
                txtElem.setStyle("-fx-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
                colecaoLayout.getChildren().add(txtElem);
            }

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
            btnCarta.setPrefSize(130, 170);
            btnCarta.getStyleClass().add(getCardStyle(carta));
            colecaoLayout.getChildren().add(btnCarta);
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

    private int getRaridadeOrdem(Raridade r) {
        return switch (r) {
            case Comum -> 0;
            case Incomum -> 1;
            case Raro -> 2;
            case Epico -> 3;
            case Lendario -> 4;
        };
    }

    private String getCardStyle(Carta carta) {
        if (carta instanceof CartaEspecial) return "card-especial";
        return switch (carta.getElemento()) {
            case Fogo -> "card-fogo";
            case Agua -> "card-agua";
            case Erva -> "card-erva";
            case Eletrico -> "card-eletrico";
            case Gelo -> "card-gelo";
            case Voador -> "card-voador";
            default -> "card-normal";
        };
    }
}