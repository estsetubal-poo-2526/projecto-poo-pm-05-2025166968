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

public class SalaJogo {
    private Stage stage;

    public SalaJogo(Stage stage){
        this.stage = stage;
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
        for (int i = 0; i < 3; i++){
            Button carta = new Button("Carta " + (i + 1));
            carta.setPrefSize(100, 140);
            mao.getChildren().add(carta);
        }

        Button btnPassarTurno = new Button("Passar Truno");
        Button btnDesistir = new Button("Desistitir");
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
