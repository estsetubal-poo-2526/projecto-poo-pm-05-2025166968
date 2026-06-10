package org.example.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.JogoController;
import org.example.model.*;
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

        HBox criaturaAdversario = new HBox(10); // CAMPO ADVERSARIO
        criaturaAdversario.setAlignment(Pos.CENTER);
        CartaCriatura[] espacosPC = controller.getJogo().getJogador(1).getCampo().getEspacosCriatura();
        for (int i = 0; i < 5; i++){
            final int indexAlvo = i;
            if (espacosPC[i] != null){
                Button espaco = new Button(espacosPC[i].getNome() + "\nHP:" + espacosPC[i].getHp() + "\nATK:" + espacosPC[i].getAtk() + "\nDEF:" + espacosPC[i].getDef());
                espaco.setPrefSize(100, 140);
                espaco.setOnAction(e -> {
                    if (cartaSelecionada != -1 && !jaAtacou){
                        boolean atacou = controller.atacar(cartaSelecionada, indexAlvo);
                        if (atacou) {
                            cartaSelecionada = -1;
                            jaAtacou = true;
                        }else{
                            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Aviso");
                            alert.setHeaderText(null);
                            alert.setContentText("Não podes atacar com uma carta em modo de defesa!");
                            alert.showAndWait();
                        }
                        atualizar();
                    }
                });
                criaturaAdversario.getChildren().add(espaco);
            }else{
                Button espaco = new Button("[ ]");
                espaco.setPrefSize(100, 140);
                criaturaAdversario.getChildren().add(espaco);
            }
        }

        VBox especiaisAdversario = new VBox(10);
        especiaisAdversario.setAlignment(Pos.CENTER);
        CartaEspecial[] especiaisPC = controller.getJogo().getJogador(1).getCampo().getEspacosEspecial();
        for (int i = 0; i < 2; i++){
            if (especiaisPC[i] != null) {
                Button espaco = new Button(especiaisPC[i].getNome());
                espaco.setPrefSize(80, 60);
                especiaisAdversario.getChildren().add(espaco);
            }else{
                Button espaco = new Button("[ESP]");
                espaco.setPrefSize(80, 60);
                especiaisAdversario.getChildren().add(espaco);
            }
        }

        HBox campoAdversario = new HBox(10);
        campoAdversario.setAlignment(Pos.CENTER);
        campoAdversario.getChildren().addAll(criaturaAdversario, especiaisAdversario);

        Text txtJogador = new Text("O Teu Campo");
        txtJogador.setFont(Font.font(18));

        HBox criaturaJogador = new HBox(10); // CAMPO JOGADOR
        criaturaJogador.setAlignment(Pos.CENTER);
        CartaCriatura[] espacos = controller.getJogo().getJogador(0).getCampo().getEspacosCriatura();
        for (int i = 0; i < 5; i++){
            final int indexCarta = i;
            if (espacos[i] != null){
                String posicao = espacos[i].getPosicao() == Posicao.Ataque ? "[ATK]" : "[DEF]";
                Button espaco = new Button(espacos[i].getNome() + "\n" + posicao + "\nHP:" + espacos[i].getHp() + "\nATK:" + espacos[i].getAtk() + "\nDEF:" + espacos[i].getDef());
                espaco.setPrefSize(100, 140);
                espaco.setOnAction(e -> {
                    cartaSelecionada = indexCarta;
                    atualizar();
                });

                espaco.setOnContextMenuRequested(e -> {
                    espacos[indexCarta].mudarPosicao();
                    atualizar();
                });

                if (cartaSelecionada == i){
                    espaco.setStyle("-fx-background-color: yellow;");
                }

                criaturaJogador.getChildren().add(espaco);

            }else{
                Button espaco = new Button("[ ]");
                espaco.setPrefSize(100, 140);
                criaturaJogador.getChildren().add(espaco);
            }
        }

        VBox especiaisJogador = new VBox(10);
        especiaisJogador.setAlignment(Pos.CENTER);
        CartaEspecial[] especiaisJog = controller.getJogo().getJogador(0).getCampo().getEspacosEspecial();
        for (int i = 0; i < 2; i++){
            final int indexEspecial = i;
            if (especiaisJog[i] != null){
                Button espaco = new Button(especiaisJog[i].getNome());
                espaco.setPrefSize(80,60);
                espaco.setOnAction(e -> {
                    controller.usarCartaEspecial(0, indexEspecial);
                    atualizar();
                });
                especiaisJogador.getChildren().add(espaco);
            }else{
                Button espaco = new Button("[ESP]");
                espaco.setPrefSize(80,60);
                especiaisJogador.getChildren().add(espaco);
            }
        }

        HBox campoJogador = new HBox(10);
        campoJogador.setAlignment(Pos.CENTER);
        campoJogador.getChildren().addAll(criaturaJogador, especiaisJogador);

        Text txtMao = new Text("Mão");
        txtMao.setFont(Font.font(14));
        HBox mao = new HBox(10);
        mao.setAlignment(Pos.CENTER);
        List<Carta> cartasMao = controller.getJogo().getJogador(0).getMao();
        for (int i = 0; i < cartasMao.size(); i++) {
            Carta carta = cartasMao.get(i);
            final int indexMao = i;
            Button btnCarta;
            if (carta instanceof CartaEspecial) {
                btnCarta = new Button(carta.getNome() + "\n[ESPECIAL]" + carta.getRaridade());
            } else {
                btnCarta = new Button(carta.getNome() + "\nHP:" + carta.getHp() + "\nATK:" + carta.getAtk() + "\nDEF:" + carta.getDef());
            }
            btnCarta.setPrefSize(100, 140);
            btnCarta.setOnAction(e -> {
                Campo campo = controller.getJogo().getJogador(0).getCampo();
                if (carta instanceof CartaEspecial) {
                    for (int j = 0; j < 2; j++) {
                        if (campo.getEspacosCriatura()[j] == null) {
                            controller.getJogo().getJogador(0).jogarEspecial(indexMao, j);
                            atualizar();
                            break;
                        }
                    }
                } else {
                    for (int j = 0; j < 5; j++) {
                        if (campo.getEspacosCriatura()[j] == null) {
                            controller.getJogo().getJogador(0).jogarCriatura(indexMao, j);
                            atualizar();
                            break;
                        }
                    }
                }
            });
            mao.getChildren().add(btnCarta);
        }

        Button btnPassarTurno = new Button("Passar Turno");
        btnPassarTurno.setOnAction(e -> {
            jaAtacou = false;
            controller.passarTurno();
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
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(txtAdversario, campoAdversario, txtJogador, campoJogador, txtMao, mao, botoes);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public void atualizar(){
        mostrar();
    }
}
