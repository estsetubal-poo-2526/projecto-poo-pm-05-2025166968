package org.example.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
    public Lobby(Stage stage){
        this.stage = stage;
    }

    public void mostrar(){
        Tab abaColecao = new Tab("Coleção"); // COLEÇÃO
        abaColecao.setClosable(false);

        FlowPane colecaoLayout = new FlowPane(10, 10);
        colecaoLayout.setPadding(new Insets(10));

        List<CartaCriatura> colecao = Perfil.getInstancia().getColecao();
        for (CartaCriatura carta : colecao){
            Button btnCarta = new Button(carta.getNome() + "\n" + carta.getElemento() + "\nHP:" + carta.getHp() + "\nATK:" + carta.getAtk() + "\nDEF:" + carta.getDef() + "\n" + carta.getRaridade());
            btnCarta.setPrefSize(120, 160);
            colecaoLayout.getChildren().add(btnCarta);
        }

        ScrollPane scrollColecao = new ScrollPane(colecaoLayout);
        scrollColecao.setFitToWidth(true);
        abaColecao.setContent(scrollColecao);

        Tab abaBaralhos = new Tab("Baralhos");
        abaBaralhos.setClosable(false);
        abaBaralhos.setContent(new Text("Gestão de Baralhos"));

        Tab abaLoja = new Tab("Loja"); // LOJA
        abaLoja.setClosable(false);

        Text txtMoedas = new Text("Moedas: " + Perfil.getInstancia().getMoedas());
        txtMoedas.setFont(Font.font(20));

        Button btnPackBasico = new Button("Pack Básico\n6 cartas\n-50 moedas");
        btnPackBasico.setPrefSize(150,100);
        btnPackBasico.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 50){
                Perfil.getInstancia().removerMoedas(50);
                List<CartaCriatura> cartas = GeradorCartas.abrirPack();
                Perfil.getInstancia().getColecao().addAll(cartas);
                mostrar();
            }
        });

        Button btnPackRaro = new Button("Pack Raro\n6 cartas\n-100 moedas");
        btnPackRaro.setPrefSize(150,100);
        btnPackRaro.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 100){
                Perfil.getInstancia().removerMoedas(100);
                List<CartaCriatura> cartas = GeradorCartas.abrirPack();
                Perfil.getInstancia().getColecao().addAll(cartas);
                mostrar();
            }
        });

        Button btnPackEpico = new Button("Pack Epico\n6 cartas\n-200 moedas");
        btnPackEpico.setPrefSize(150,100);
        btnPackEpico.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 200){
                Perfil.getInstancia().removerMoedas(200);
                List<CartaCriatura> cartas = GeradorCartas.abrirPack();
                Perfil.getInstancia().getColecao().addAll(cartas);
                mostrar();
            }
        });

        HBox packs = new HBox(20);
        packs.setAlignment(Pos.CENTER);
        packs.getChildren().addAll(btnPackBasico, btnPackRaro, btnPackEpico);

        VBox lojaLayout = new VBox(20);
        lojaLayout.setAlignment(Pos.CENTER);
        lojaLayout.getChildren().addAll(txtMoedas, packs);
        abaLoja.setContent(lojaLayout);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(abaColecao, abaBaralhos, abaLoja);

        javafx.scene.control.Button btnJogar = new javafx.scene.control.Button("Iniciar Jogo");
        btnJogar.setOnAction(e -> {
            JogoController controller = new JogoController(stage);
            controller.iniciarJogo();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(tabPane, btnJogar);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}
