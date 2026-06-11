package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Carta;
import org.example.model.CartaEspecial;
import org.example.model.Elemento;
import org.example.model.Perfil;
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

    public void mostrar() {
        Text txtTitulo = new Text(nomeBaralho);
        txtTitulo.getStyleClass().add("titulo");

        Text txtTotal = new Text(cartas.size() + " cartas");
        txtTotal.getStyleClass().add("texto-normal");

        FlowPane cartasLayout = new FlowPane(10, 10);
        cartasLayout.setPadding(new Insets(10));

        for (Carta carta : cartas) {
            String info;
            if (carta instanceof CartaEspecial) {
                info = carta.getNome() + "\n[ESPECIAL]\n" + carta.getRaridade();
            } else {
                info = carta.getNome() + "\nHP:" + carta.getHp() +
                        "\nATK:" + carta.getAtk() +
                        "\nDEF:" + carta.getDef() +
                        "\n" + carta.getRaridade();
            }
            Button btnCarta = new Button(info);
            btnCarta.setPrefSize(120, 150);
            btnCarta.getStyleClass().add(getCardStyle(carta));
            cartasLayout.getChildren().add(btnCarta);
        }

        javafx.scene.control.ScrollPane scroll = new javafx.scene.control.ScrollPane(cartasLayout);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("scroll-pane");

        Button btnUsar = new Button("✅ Usar este Baralho");
        btnUsar.getStyleClass().add("btn-primario");
        btnUsar.setPrefWidth(250);
        btnUsar.setOnAction(e -> {
            Perfil.getInstancia().selecionarBaralho(nomeBaralho);
            lobby.mostrar();
        });

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.getStyleClass().add("btn-voltar");
        btnVoltar.setOnAction(e -> new BaralhosView(stage, lobby).mostrar());

        Button btnEliminar = new Button("🗑 Eliminar Baralho");
        btnEliminar.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 10 25 10 25; -fx-cursor: hand;");
        btnEliminar.setPrefWidth(250);
        btnEliminar.setOnAction(e -> {
            Perfil.getInstancia().getBaralhosSalvos().remove(nomeBaralho);
            if (nomeBaralho.equals(Perfil.getInstancia().getBaralhoSelecionado())) {
                Perfil.getInstancia().setBaralhoSelecionado(null);
            }
            new BaralhosView(stage, lobby).mostrar();
        });

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));
        scroll.setMaxHeight(450);
        layout.getChildren().addAll(txtTitulo, txtTotal, scroll, btnUsar, btnEliminar, btnVoltar);

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