package org.example.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

    public SalaJogo(Stage stage, JogoController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void mostrar() {

        // === CAMPO ADVERSÁRIO ===
        Text txtAdversario = new Text("Campo do Adversário");
        txtAdversario.setStyle("-fx-fill: rgba(255,255,255,0.7); -fx-font-size: 14px;");

        HBox criaturaAdversario = new HBox(8);
        criaturaAdversario.setAlignment(Pos.CENTER);
        CartaCriatura[] espacosPC = controller.getJogo().getJogador(1).getCampo().getEspacosCriatura();
        for (int i = 0; i < 5; i++) {
            final int indexAlvo = i;
            if (espacosPC[i] != null) {
                Button espaco = new Button(espacosPC[i].getNome() +
                        "\nHP:" + espacosPC[i].getHp() +
                        "\nATK:" + espacosPC[i].getAtk() +
                        "\nDEF:" + espacosPC[i].getDef());
                espaco.setPrefSize(100, 130);
                espaco.getStyleClass().add(getCardStyle(espacosPC[i]));
                espaco.setOnAction(e -> {
                    if (cartaSelecionada != -1 && !jaAtacou) {
                        boolean atacou = controller.atacar(cartaSelecionada, indexAlvo);
                        if (atacou) {
                            cartaSelecionada = -1;
                            jaAtacou = true;
                        } else {
                            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                                    javafx.scene.control.Alert.AlertType.WARNING);
                            alert.setTitle("Aviso");
                            alert.setHeaderText(null);
                            alert.setContentText("Não podes atacar com uma carta em modo de defesa!");
                            alert.showAndWait();
                        }
                        atualizar();
                    }
                });
                criaturaAdversario.getChildren().add(espaco);
            } else {
                Button espaco = new Button("[ ]");
                espaco.setPrefSize(100, 130);
                espaco.getStyleClass().add("espaco-carta");
                criaturaAdversario.getChildren().add(espaco);
            }
        }

        // Espaços especiais adversário
        VBox especiaisAdversario = new VBox(8);
        especiaisAdversario.setAlignment(Pos.CENTER);
        CartaEspecial[] especiaisPC = controller.getJogo().getJogador(1).getCampo().getEspacosEspecial();
        for (int i = 0; i < 2; i++) {
            if (especiaisPC[i] != null) {
                Button espaco = new Button(especiaisPC[i].getNome());
                espaco.setPrefSize(80, 60);
                espaco.getStyleClass().add("card-especial");
                especiaisAdversario.getChildren().add(espaco);
            } else {
                Button espaco = new Button("[ESP]");
                espaco.setPrefSize(80, 60);
                espaco.getStyleClass().add("espaco-carta");
                especiaisAdversario.getChildren().add(espaco);
            }
        }

        HBox campoAdversario = new HBox(10);
        campoAdversario.setAlignment(Pos.CENTER);
        campoAdversario.getStyleClass().add("campo-adversario");
        campoAdversario.getChildren().addAll(criaturaAdversario, especiaisAdversario);

        // === CAMPO JOGADOR ===
        Text txtJogador = new Text("O Teu Campo");
        txtJogador.setStyle("-fx-fill: rgba(255,255,255,0.7); -fx-font-size: 14px;");

        HBox criaturaJogador = new HBox(8);
        criaturaJogador.setAlignment(Pos.CENTER);
        CartaCriatura[] espacos = controller.getJogo().getJogador(0).getCampo().getEspacosCriatura();
        for (int i = 0; i < 5; i++) {
            final int indexCarta = i;
            if (espacos[i] != null) {
                String posicao = espacos[i].getPosicao() == Posicao.Ataque ? "⚔ ATK" : "🛡 DEF";
                Button espaco = new Button(espacos[i].getNome() + "\n" + posicao +
                        "\nHP:" + espacos[i].getHp() +
                        "\nATK:" + espacos[i].getAtk() +
                        "\nDEF:" + espacos[i].getDef());
                espaco.setPrefSize(100, 130);
                if (cartaSelecionada == i) {
                    espaco.getStyleClass().add("carta-selecionada");
                } else {
                    espaco.getStyleClass().add(getCardStyle(espacos[i]));
                }
                espaco.setOnAction(e -> {
                    cartaSelecionada = indexCarta;
                    atualizar();
                });
                espaco.setOnContextMenuRequested(e -> {
                    espacos[indexCarta].mudarPosicao();
                    atualizar();
                });
                criaturaJogador.getChildren().add(espaco);
            } else {
                Button espaco = new Button("[ ]");
                espaco.setPrefSize(100, 130);
                espaco.getStyleClass().add("espaco-carta");
                criaturaJogador.getChildren().add(espaco);
            }
        }

        // Espaços especiais jogador
        VBox especiaisJogador = new VBox(8);
        especiaisJogador.setAlignment(Pos.CENTER);
        CartaEspecial[] especiaisJog = controller.getJogo().getJogador(0).getCampo().getEspacosEspecial();
        for (int i = 0; i < 2; i++) {
            final int indexEspecial = i;
            if (especiaisJog[i] != null) {
                Button espaco = new Button(especiaisJog[i].getNome());
                espaco.setPrefSize(80, 60);
                espaco.getStyleClass().add("card-especial");
                espaco.setOnAction(e -> {
                    controller.usarCartaEspecial(0, indexEspecial);
                    atualizar();
                });
                especiaisJogador.getChildren().add(espaco);
            } else {
                Button espaco = new Button("[ESP]");
                espaco.setPrefSize(80, 60);
                espaco.getStyleClass().add("espaco-carta");
                especiaisJogador.getChildren().add(espaco);
            }
        }

        HBox campoJogador = new HBox(10);
        campoJogador.setAlignment(Pos.CENTER);
        campoJogador.getStyleClass().add("campo-jogador");
        campoJogador.getChildren().addAll(criaturaJogador, especiaisJogador);

        // === MÃO DO JOGADOR ===
        Text txtMao = new Text("Mão");
        txtMao.setStyle("-fx-fill: rgba(255,255,255,0.7); -fx-font-size: 14px;");

        HBox mao = new HBox(8);
        mao.setAlignment(Pos.CENTER);
        List<Carta> cartasMao = controller.getJogo().getJogador(0).getMao();
        for (int i = 0; i < cartasMao.size(); i++) {
            Carta carta = cartasMao.get(i);
            final int indexMao = i;
            Button btnCarta;
            if (carta instanceof CartaEspecial) {
                btnCarta = new Button(carta.getNome() + "\n[ESPECIAL]\n" + carta.getRaridade());
                btnCarta.getStyleClass().add("card-especial");
            } else {
                btnCarta = new Button(carta.getNome() + "\nHP:" + carta.getHp() +
                        "\nATK:" + carta.getAtk() + "\nDEF:" + carta.getDef());
                btnCarta.getStyleClass().add(getCardStyle(carta));
            }
            btnCarta.setPrefSize(100, 130);
            btnCarta.setOnAction(e -> {
                Campo campo = controller.getJogo().getJogador(0).getCampo();
                if (carta instanceof CartaEspecial) {
                    for (int j = 0; j < 2; j++) {
                        if (campo.getEspacosEspecial()[j] == null) {
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

        // === BOTÕES ===
        Button btnPassarTurno = new Button("Passar Turno");
        btnPassarTurno.getStyleClass().add("btn-passar-turno");
        btnPassarTurno.setOnAction(e -> {
            jaAtacou = false;
            controller.passarTurno();
        });

        Button btnDesistir = new Button("Desistir");
        btnDesistir.getStyleClass().add("btn-desistir");
        btnDesistir.setOnAction(e -> {
            Lobby lobby = new Lobby(stage);
            lobby.mostrar();
        });

        HBox botoes = new HBox(20);
        botoes.setAlignment(Pos.CENTER);
        botoes.getChildren().addAll(btnPassarTurno, btnDesistir);

        // === LAYOUT PRINCIPAL ===
        VBox layout = new VBox(12);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                txtAdversario, campoAdversario,
                txtJogador, campoJogador,
                txtMao, mao,
                botoes
        );

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

    public void atualizar() {
        mostrar();
    }
}