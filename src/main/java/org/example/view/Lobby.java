package org.example.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.example.controller.JogoController;
import org.example.model.CartaCriatura;
import org.example.model.GeradorCartas;
import org.example.model.Perfil;
import java.util.List;

public class Lobby {
    private Stage stage;
    private int abaAtiva = 0;
    public Lobby(Stage stage){
        this.stage = stage;
    }

    public void mostrar(){
        Text txtTitulo = new Text("Card Arena");
        txtTitulo.setFont(Font.font(32));

        Text txtMoedasHeader = new Text("Moedas: " + Perfil.getInstancia().getMoedas());
        txtMoedasHeader.setFont(Font.font(16));

        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.getChildren().addAll(txtTitulo, txtMoedasHeader);

        Tab abaColecao = new Tab("Coleção"); // COLEÇÃO
        abaColecao.setClosable(false);
        FlowPane colecaoLayout = new FlowPane(10, 10);
        colecaoLayout.setPadding(new Insets(10));

        for (CartaCriatura carta : Perfil.getInstancia().getColecao()){
            Button btnCarta = new Button(carta.getNome() + "\n" + carta.getElemento() + "\nHP:" + carta.getHp() + "\nATK:" + carta.getAtk() + "\nDEF:" + carta.getDef() + "\n" + carta.getRaridade());
            btnCarta.setPrefSize(120, 160);
            colecaoLayout.getChildren().add(btnCarta);
        }

        ScrollPane scrollColecao = new ScrollPane(colecaoLayout);
        scrollColecao.setFitToWidth(true);
        abaColecao.setContent(scrollColecao);

        Tab abaBaralhos = new Tab("Baralhos"); //Baralhos
        abaBaralhos.setClosable(false);
        VBox baralhoLayout = new VBox(5);
        baralhoLayout.setPadding(new Insets(10));
        Text txtBaralho = new Text("Baralho (" + Perfil.getInstancia().getBaralhoAtual().size() + "/15)");
        txtBaralho.setFont(Font.font(16));
        baralhoLayout.getChildren().add(txtBaralho);

        for (int i = 0; i < Perfil.getInstancia().getBaralhoAtual().size(); i++){
            CartaCriatura carta = Perfil.getInstancia().getBaralhoAtual().get(i);
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

        for (CartaCriatura carta : Perfil.getInstancia().getColecao()){
            Button btnCarta = new Button(carta.getNome() + "\n" + carta.getElemento() + "\n" + carta.getRaridade());
            btnCarta.setPrefSize(100, 80);
            btnCarta.setOnAction(e -> {
                boolean adicionado = Perfil.getInstancia().adicionarAoBaralho(carta);
                if (adicionado){
                    abaAtiva = 1;
                    mostrar();
                } else{
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

        Tab abaLoja = new Tab("Loja"); // LOJA
        abaLoja.setClosable(false);

        Button btnPackBasico = new Button("Pack Básico\n6 cartas\n-50 moedas");
        btnPackBasico.setPrefSize(150,100);
        btnPackBasico.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 50){
                Perfil.getInstancia().removerMoedas(50);
                Perfil.getInstancia().getColecao().addAll(GeradorCartas.abrirPack());
                abaAtiva = 2;
                mostrar();
            }
        });

        Button btnPackRaro = new Button("Pack Raro\n6 cartas\n-100 moedas");
        btnPackRaro.setPrefSize(150,100);
        btnPackRaro.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 100){
                Perfil.getInstancia().removerMoedas(100);
                Perfil.getInstancia().getColecao().addAll(GeradorCartas.abrirPack());
                abaAtiva = 2;
                mostrar();
            }
        });

        Button btnPackEpico = new Button("Pack Epico\n6 cartas\n-200 moedas");
        btnPackEpico.setPrefSize(150,100);
        btnPackEpico.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 200){
                Perfil.getInstancia().removerMoedas(200);
                Perfil.getInstancia().getColecao().addAll(GeradorCartas.abrirPack());
                abaAtiva = 2;
                mostrar();
            }
        });

        HBox packs = new HBox(20);
        packs.setAlignment(Pos.CENTER);
        packs.getChildren().addAll(btnPackBasico, btnPackRaro, btnPackEpico);
        VBox lojaLayout = new VBox(20);
        lojaLayout.setAlignment(Pos.CENTER);
        lojaLayout.setPadding(new Insets(20));
        lojaLayout.getChildren().addAll(packs);
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
        layout.getChildren().addAll(tabPane, btnJogar);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}
