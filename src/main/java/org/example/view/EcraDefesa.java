package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.JogoController;
import org.example.model.*;

public class EcraDefesa {

    private Stage stage;
    private JogoController controller;
    private CartaCriatura atacante;
    private int danoBase;

    public EcraDefesa(Stage stage, JogoController controller, CartaCriatura atacante, int danoBase) {
        this.stage = stage;
        this.controller = controller;
        this.atacante = atacante;
        this.danoBase = danoBase;
    }

    public void mostrar() {
        Text txtTitulo = new Text("⚠ Estás a ser atacado!");
        txtTitulo.setStyle("-fx-fill: #e74c3c; -fx-font-size: 28px; -fx-font-weight: bold;");

        // Carta atacante do PC
        Text txtAtacante = new Text("Carta atacante:");
        txtAtacante.setStyle("-fx-fill: rgba(255,255,255,0.7); -fx-font-size: 14px;");

        Button btnAtacante = new Button(atacante.getNome() +
                "\nHP:" + atacante.getHp() +
                "\nATK:" + atacante.getAtk() +
                "\nDEF:" + atacante.getDef());
        btnAtacante.setPrefSize(130, 170);
        btnAtacante.setMouseTransparent(true);
        btnAtacante.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 12;");

        Text txtDano = new Text("Dano base: " + danoBase);
        txtDano.setStyle("-fx-fill: #f5c842; -fx-font-size: 16px; -fx-font-weight: bold;");

        VBox ladoEsquerdo = new VBox(10);
        ladoEsquerdo.setAlignment(Pos.CENTER);
        ladoEsquerdo.getChildren().addAll(txtAtacante, btnAtacante, txtDano);

        // Cartas em defesa do jogador
        Text txtDefesa = new Text("Escolhe uma carta para defender:");
        txtDefesa.setStyle("-fx-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        HBox cartasDefesa = new HBox(20);
        cartasDefesa.setAlignment(Pos.CENTER);
        cartasDefesa.setPadding(new Insets(10));

        CartaCriatura[] espacos = controller.getJogo().getJogador(0).getCampo().getEspacosCriatura();
        boolean temDefensor = false;

        for (int i = 0; i < 5; i++) {
            if (espacos[i] != null && espacos[i].getPosicao() == Posicao.Defesa) {
                temDefensor = true;
                final int indexDefensor = i;
                CartaCriatura defensor = espacos[i];

                int danoReal = Math.max(0, danoBase - defensor.getDef());

                Button btnDefensor = new Button(defensor.getNome() +
                        "\nHP:" + defensor.getHp() +
                        "\nDEF:" + defensor.getDef() +
                        "\nDano real: " + danoReal);
                btnDefensor.setPrefSize(190, 150); // largura e altura trocadas
                btnDefensor.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;");
                btnDefensor.setOnAction(e -> {
                    defensor.receberDano(danoBase);
                    if (!defensor.estaViva()) {
                        controller.getJogo().getJogador(0).getCampo().removerCriatura(indexDefensor);
                    }
                    controller.getJogo().verificarFimJogo();
                    if (controller.getJogo().isJogoTerminado()) {
                        controller.terminarJogo();
                    } else {
                        continuarTurno();
                    }
                });
                cartasDefesa.getChildren().add(btnDefensor);
            }
        }

        ScrollPane scrollDefesa = new ScrollPane(cartasDefesa);
        scrollDefesa.setFitToHeight(true);
        scrollDefesa.getStyleClass().add("scroll-pane");
        scrollDefesa.setMaxWidth(700);

        // Botão não defender
        Button btnNaoDefender = new Button("Não defender\n(recebe dano direto)");
        btnNaoDefender.setStyle("-fx-background-color: #7f8c8d; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 10 20 10 20; -fx-cursor: hand;");
        btnNaoDefender.setOnAction(e -> {
            CartaCriatura alvo = null;
            int indexAlvo = -1;
            for (int i = 0; i < 5; i++) {
                if (espacos[i] != null && espacos[i].getPosicao() == Posicao.Ataque) {
                    if (alvo == null || espacos[i].getHp() < alvo.getHp()) {
                        alvo = espacos[i];
                        indexAlvo = i;
                    }
                }
            }
            if (alvo != null) {
                alvo.receberDano(danoBase);
                if (!alvo.estaViva()) {
                    controller.getJogo().getJogador(0).getCampo().removerCriatura(indexAlvo);
                }
            }
            controller.getJogo().verificarFimJogo();
            if (controller.getJogo().isJogoTerminado()) {
                controller.terminarJogo();
            } else {
                continuarTurno();
            }
        });

        VBox ladoDireito = new VBox(15);
        ladoDireito.setAlignment(Pos.CENTER);
        if (temDefensor) {
            ladoDireito.getChildren().addAll(txtDefesa, scrollDefesa, btnNaoDefender);
        } else {
            Text txtSemDefesa = new Text("Não tens cartas em defesa!\nO ataque vai direto às tuas criaturas.");
            txtSemDefesa.setStyle("-fx-fill: #e74c3c; -fx-font-size: 14px; -fx-text-alignment: center;");
            Button btnOk = new Button("OK");
            btnOk.getStyleClass().add("btn-primario");
            btnOk.setOnAction(e -> btnNaoDefender.fire());
            ladoDireito.getChildren().addAll(txtSemDefesa, btnOk);
        }

        HBox conteudo = new HBox(40);
        conteudo.setAlignment(Pos.CENTER);
        conteudo.getChildren().addAll(ladoEsquerdo, ladoDireito);

        VBox layout = new VBox(25);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(txtTitulo, conteudo);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void continuarTurno() {
        controller.continuarAposTurnoPC();
    }
}