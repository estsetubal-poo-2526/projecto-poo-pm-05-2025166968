package org.example.view;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.JogoController;
import org.example.model.Carta;
import java.util.List;

import java.util.List;

public class SalaJogo {
    private Stage stage;
    private JogoController controller;

    public SalaJogo(Stage stage, JogoController controller){
        this.stage = stage;
        this.controller = controller;
    }

    public void mostrar(){
        Text txtAdversario = new Text("Campo do Adversário");
        txtAdversario.setFont(Font.font(18));
        HBox campoAdversario = new HBox(10);
        campoAdversario.setAlignment(Pos.CENTER);
        for (int i = 0; i < 5; i++){
            Button espaco = new Button("[ ]");
            espaco.setPrefSize(100, 140);
            campoAdversario.getChildren().add(espaco);
        }

        Text txtJogador = new Text("O Teu Campo");
        txtJogador.setFont(Font.font(18));
        HBox campoJogador = new HBox(10);
        campoJogador.setAlignment(Pos.CENTER);
        for (int i = 0; i < 5; i++){
            Button espaco = new Button("[ ]");
            espaco.setPrefSize(100, 140);
            campoJogador.getChildren().add(espaco);
        }

        Text txtMao = new Text("Mão");
        txtMao.setFont(Font.font(14));
        HBox mao = new HBox(10);
        mao.setAlignment(Pos.CENTER);
        List<Carta> cartasMao = controller.getJogo().getJogador(0).getMao();
        for (Carta carta : cartasMao){
            Button btnCarta = new Button(carta.getNome() + "\nHP:" + carta.getHp() + "\nATK:" + carta.getAtk() + "\nDEF:" + carta.getDef());
            btnCarta.setPrefSize(100, 140);
            mao.getChildren().add(btnCarta);
        }

        Button btnPassarTurno = new Button("Passar Turno");
        Button btnDesistir = new Button("Desistir");
        btnDesistir.setOnAction(e -> {
            Lobby lobby = new Lobby(stage);
            lobby.mostrar();
        });

        HBox botoes = new HBox(20);
        botoes.setAlignment(Pos.CENTER);
        botoes.getChildren().addAll(btnPassarTurno, btnDesistir);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(txtAdversario, campoAdversario, txtJogador, campoJogador, txtMao, mao, botoes);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}
