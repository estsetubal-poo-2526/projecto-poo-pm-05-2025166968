package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.JogoController;
import org.example.model.CartaCriatura;
import org.example.model.CartaEspecial;
import org.example.model.Pocao;
import org.example.model.Treinador;

public class EcraUsarEspecial {

    private Stage stage;
    private JogoController controller;
    private int indexEspecial;
    private CartaEspecial carta;

    public EcraUsarEspecial(Stage stage, JogoController controller, int indexEspecial, CartaEspecial carta) {
        this.stage = stage;
        this.controller = controller;
        this.indexEspecial = indexEspecial;
        this.carta = carta;
    }

    public void mostrar() {
        Text txtTitulo = new Text(carta.getNome());
        txtTitulo.getStyleClass().add("titulo");

        // Descrição do efeito
        String descricao;
        if (carta instanceof Pocao) {
            descricao = "Cura " + ((Pocao) carta).getCura() + " HP a uma criatura aliada.";
        } else if (carta instanceof Treinador) {
            Treinador t = (Treinador) carta;
            descricao = "Aplica +" + t.getBuffAtk() + " ATK e +" + t.getBuffDef() + " DEF a uma criatura aliada.";
        } else {
            descricao = "Carta especial.";
        }

        Text txtDescricao = new Text(descricao);
        txtDescricao.setStyle("-fx-fill: rgba(255,255,255,0.8); -fx-font-size: 16px;");

        Text txtPergunta = new Text("Em qual criatura queres usar?");
        txtPergunta.setStyle("-fx-fill: white; -fx-font-size: 14px;");

        // Botões para cada criatura em campo
        HBox criaturas = new HBox(10);
        criaturas.setAlignment(Pos.CENTER);
        CartaCriatura[] espacos = controller.getJogo().getJogador(0).getCampo().getEspacosCriatura();

        boolean temCriatura = false;
        for (int i = 0; i < 5; i++) {
            if (espacos[i] != null) {
                temCriatura = true;
                final int indexCriatura = i;
                CartaCriatura criatura = espacos[i];
                Button btnCriatura = new Button(criatura.getNome() +
                        "\nHP:" + criatura.getHp() + "/" + criatura.getMaxHp() +
                        "\nATK:" + criatura.getAtk() +
                        "\nDEF:" + criatura.getDef());
                btnCriatura.setPrefSize(120, 150);
                btnCriatura.getStyleClass().add("card-" + criatura.getElemento().toString().toLowerCase());
                btnCriatura.setOnAction(e -> {
                    // aplica efeito na criatura escolhida
                    carta.aplicarEfeito(criatura);
                    // remove carta especial do campo
                    controller.getJogo().getJogador(0).getCampo().removerEspecial(indexEspecial);
                    // volta à sala de jogo
                    SalaJogo salaJogo = new SalaJogo(stage, controller);
                    salaJogo.mostrar();
                });
                criaturas.getChildren().add(btnCriatura);
            }
        }

        if (!temCriatura) {
            Text txtSemCriaturas = new Text("Não tens criaturas em campo!");
            txtSemCriaturas.setStyle("-fx-fill: #e74c3c; -fx-font-size: 14px;");
            criaturas.getChildren().add(txtSemCriaturas);
        }

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.getStyleClass().add("btn-voltar");
        btnCancelar.setOnAction(e -> {
            SalaJogo salaJogo = new SalaJogo(stage, controller);
            salaJogo.mostrar();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(txtTitulo, txtDescricao, txtPergunta, criaturas, btnCancelar);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}