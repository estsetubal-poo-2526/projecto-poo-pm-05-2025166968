package org.example.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.JogoController;
import org.example.model.Carta;
import org.example.model.CartaEspecial;
import org.example.model.GeradorCartas;
import org.example.model.Perfil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lobby {
    private Stage stage;
    private int abaAtiva = 0;

    public Lobby(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        Text txtTitulo = new Text("Card Arena");
        txtTitulo.setFont(Font.font(32));

        Text txtMoedasHeader = new Text("Moedas: " + Perfil.getInstancia().getMoedas());
        txtMoedasHeader.setFont(Font.font(16));

        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.getChildren().addAll(txtTitulo, txtMoedasHeader);

        Tab abaColecao = new Tab("Coleção");
        abaColecao.setClosable(false);
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

        ScrollPane scrollColecao = new ScrollPane(colecaoLayout);
        scrollColecao.setFitToWidth(true);
        abaColecao.setContent(scrollColecao);

        Tab abaBaralhos = new Tab("Baralhos");
        abaBaralhos.setClosable(false);
        VBox baralhoLayout = new VBox(5);
        baralhoLayout.setPadding(new Insets(10));
        Text txtBaralho = new Text("Baralho (" + Perfil.getInstancia().getBaralhoAtual().size() + "/15)");
        txtBaralho.setFont(Font.font(16));
        baralhoLayout.getChildren().add(txtBaralho);

        for (int i = 0; i < Perfil.getInstancia().getBaralhoAtual().size(); i++) {
            Carta carta = Perfil.getInstancia().getBaralhoAtual().get(i);
            final int index = i;
            Button btnCarta = new Button(carta.getNome() + " [" + carta.getRaridade() + "]");
            btnCarta.setPrefWidth(200);
            btnCarta.setOnAction(e -> {
                Perfil.getInstancia().removerDoBaralho(index);
                abaAtiva = 1;
                mostrar();
            });
            baralhoLayout.getChildren().add(btnCarta);
        }

        ScrollPane scrollBaralho = new ScrollPane(baralhoLayout);
        scrollBaralho.setPrefWidth(250);
        FlowPane colecaoParaBaralho = new FlowPane(5, 5);
        colecaoParaBaralho.setPadding(new Insets(10));

        for (Map.Entry<String, Carta> entry : Perfil.getInstancia().getColecao().entrySet()) {
            Carta carta = entry.getValue();
            int qtd = Perfil.getInstancia().getQuantidades().get(carta.getNome());
            Button btnCarta = new Button(carta.getNome() + "\n" + carta.getRaridade() + "\nx" + qtd);
            btnCarta.setPrefSize(100, 80);
            btnCarta.setOnAction(e -> {
                boolean adicionado = Perfil.getInstancia().adicionarAoBaralho(carta);
                if (adicionado) {
                    abaAtiva = 1;
                    mostrar();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Erro. Só podes meter 4 cartas do mesmo tipo em 1 baralho ou o baralho já está cheio!");
                    alert.showAndWait();
                }
            });
            colecaoParaBaralho.getChildren().add(btnCarta);
        }

        ScrollPane scrollColecaoBaralho = new ScrollPane(colecaoParaBaralho);
        HBox baralhoCompleto = new HBox(10);
        baralhoCompleto.getChildren().addAll(scrollBaralho, scrollColecaoBaralho);
        abaBaralhos.setContent(baralhoCompleto);

        Tab abaLoja = new Tab("Loja");
        abaLoja.setClosable(false);
        Button btnPackBasico = new Button("Pack Básico\n6 cartas\n-50 moedas");
        btnPackBasico.setPrefSize(150, 100);
        btnPackBasico.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 50) {
                Perfil.getInstancia().removerMoedas(50);
                List<Carta> cartas = new ArrayList<>(GeradorCartas.abrirPack());
                for (Carta carta : cartas) Perfil.getInstancia().adicionarCarta(carta);
                EcraAberturaPack ecra = new EcraAberturaPack(stage, cartas);
                ecra.mostrar();
            }
        });
        Button btnPackRaro = new Button("Pack Raro\n6 cartas\n-100 moedas");
        btnPackRaro.setPrefSize(150, 100);
        btnPackRaro.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 100) {
                Perfil.getInstancia().removerMoedas(100);
                List<Carta> cartas = new ArrayList<>(GeradorCartas.abrirPack());
                for (Carta carta : cartas) Perfil.getInstancia().adicionarCarta(carta);
                EcraAberturaPack ecra = new EcraAberturaPack(stage, cartas);
                ecra.mostrar();
            }
        });
        Button btnPackEpico = new Button("Pack Épico\n6 cartas\n-200 moedas");
        btnPackEpico.setPrefSize(150, 100);
        btnPackEpico.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 200) {
                Perfil.getInstancia().removerMoedas(200);
                List<Carta> cartas = new ArrayList<>(GeradorCartas.abrirPack());
                for (Carta carta : cartas) Perfil.getInstancia().adicionarCarta(carta);
                EcraAberturaPack ecra = new EcraAberturaPack(stage, cartas);
                ecra.mostrar();
            }
        });
        HBox packs = new HBox(20);
        packs.setAlignment(Pos.CENTER);
        packs.getChildren().addAll(btnPackBasico, btnPackRaro, btnPackEpico);
        VBox lojaLayout = new VBox(20);
        lojaLayout.setAlignment(Pos.CENTER);
        lojaLayout.setPadding(new Insets(20));
        lojaLayout.getChildren().add(packs);
        abaLoja.setContent(lojaLayout);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(abaColecao, abaBaralhos, abaLoja);
        tabPane.getSelectionModel().select(abaAtiva);
        tabPane.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            abaAtiva = newVal.intValue();
        });

        Button btnJogar = new Button("Iniciar Jogo");
        btnJogar.setOnAction(e -> {
            JogoController controller = new JogoController(stage);
            controller.iniciarJogo();
        });

        VBox layout = new VBox(0);
        layout.getChildren().addAll(header, tabPane, btnJogar);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}