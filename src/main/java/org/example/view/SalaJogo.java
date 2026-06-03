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
import org.example.model.Campo;
import org.example.model.CartaCriatura;

import java.util.List;

public class SalaJogo {
    private Stage stage;
    private JogoController controller;
    private int cartaSelecionada = -1;
    private boolean jaAtacou = false;

    public SalaJogo(Stage stage, JogoController controller){
        this.stage = stage;
        this.controller = controller;
    }

    public void mostrar(){
        Text txtAdversario = new Text("Campo do Adversário");
        txtAdversario.setFont(Font.font(18));
        HBox campoAdversario = new HBox(10); // CAMPO ADVERSARIO
        campoAdversario.setAlignment(Pos.CENTER);
        CartaCriatura[] espacosPC = controller.getJogo().getJogador(1).getCampo().getEspacosCriatura();
        for (int i = 0; i < 5; i++){
            final int indexAlvo = i;
            if (espacosPC[i] != null){
                Button espaco = new Button(espacosPC[i].getNome() + "\nHP:" + espacosPC[i].getHp() + "\nATK:" + espacosPC[i].getAtk() + "\nDEF:" + espacosPC[i].getDef());
                espaco.setPrefSize(100, 140);
                espaco.setOnAction(e -> {
                    if (cartaSelecionada != -1 && !jaAtacou){
                        controller.atacar(cartaSelecionada, indexAlvo);
                        cartaSelecionada = -1;
                        jaAtacou = true;
                        atualizar();
                    }
                });
                campoAdversario.getChildren().add(espaco);
            } else {
                Button espaco = new Button("[ ]");
                espaco.setPrefSize(100, 140);
                campoAdversario.getChildren().add(espaco);
            }
        }

        Text txtJogador = new Text("O Teu Campo");
        txtJogador.setFont(Font.font(18));
        HBox campoJogador = new HBox(10); // CAMPO JOGADOR
        campoJogador.setAlignment(Pos.CENTER);
        CartaCriatura[] espacos = controller.getJogo().getJogador(0).getCampo().getEspacosCriatura();
        for (int i = 0; i < 5; i++){
            final int indexCarta = i;
            if (espacos[i] != null){
                Button espaco = new Button(espacos[i].getNome() + "\nHP:" + espacos[i].getHp() + "\nATK:" + espacos[i].getAtk() + "\nDEF:" + espacos[i].getDef());
                espaco.setPrefSize(100, 140);
                espaco.setOnAction(e -> {
                    cartaSelecionada = indexCarta;
                    atualizar();
                });
                if (cartaSelecionada == i){
                    espaco.setStyle("-fx-background-color: yellow;");
                }
                campoJogador.getChildren().add(espaco);
            } else {
                Button espaco = new Button("[ ]");
                espaco.setPrefSize(100, 140);
                campoJogador.getChildren().add(espaco);
            }
        }

        Text txtMao = new Text("Mão");
        txtMao.setFont(Font.font(14));
        HBox mao = new HBox(10);
        mao.setAlignment(Pos.CENTER);
        List<Carta> cartasMao = controller.getJogo().getJogador(0).getMao();
        for (int i = 0; i < cartasMao.size(); i++){
            Carta carta = cartasMao.get(i);
            final int indexMao = i;
            Button btnCarta = new Button(carta.getNome() + "\nHP:" + carta.getHp() + "\nATK:" + carta.getAtk() + "\nDEF:" + carta.getDef());

            btnCarta.setPrefSize(100, 140);
            btnCarta.setOnAction(e ->{
                Campo campo = controller.getJogo().getJogador(0).getCampo();
                for (int j = 0; j < 5; j++){
                    if (campo.getEspacosCriatura()[j] == null){
                        controller.getJogo().getJogador(0).jogarCriatura(indexMao, j);
                        atualizar();
                        break;
                    }
                }
            });
            mao.getChildren().add(btnCarta);
        }

        Button btnPassarTurno = new Button("Passar Turno");
        btnPassarTurno.setOnAction(e -> {
            jaAtacou = false;
            controller.passarTurno();
            atualizar();
        });
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

    public void atualizar(){
        mostrar();
    }
}
