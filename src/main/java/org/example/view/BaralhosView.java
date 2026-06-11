package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Carta;
import org.example.model.Perfil;
import java.util.Map;

public class BaralhosView {
    private Stage stage;
    private Lobby lobby;

    public BaralhosView(Stage stage, Lobby lobby) {
        this.stage = stage;
        this.lobby = lobby;
    }

    public void mostrar() {
        Text txtTitulo = new Text("Baralhos");
        txtTitulo.setFont(Font.font(28));

        Text txtBaralho = new Text("Baralho (" + Perfil.getInstancia().getBaralhoAtual().size() + "/15)");
        txtBaralho.setFont(Font.font(16));

        // lado esquerdo - baralho atual
        VBox baralhoLayout = new VBox(5);
        baralhoLayout.setPadding(new Insets(10));
        baralhoLayout.getChildren().add(txtBaralho);

        for (int i = 0; i < Perfil.getInstancia().getBaralhoAtual().size(); i++) {
            Carta carta = Perfil.getInstancia().getBaralhoAtual().get(i);
            final int index = i;
            Button btnCarta = new Button(carta.getNome() + " [" + carta.getRaridade() + "]");
            btnCarta.setPrefWidth(200);
            btnCarta.setOnAction(e -> {
                Perfil.getInstancia().removerDoBaralho(index);
                mostrar();
            });
            baralhoLayout.getChildren().add(btnCarta);
        }

        ScrollPane scrollBaralho = new ScrollPane(baralhoLayout);
        scrollBaralho.setPrefWidth(250);

        // lado direito - coleção para adicionar
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
                    mostrar();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Não podes adicionar mais esta carta ao baralho!");
                    alert.showAndWait();
                }
            });
            colecaoParaBaralho.getChildren().add(btnCarta);
        }

        ScrollPane scrollColecao = new ScrollPane(colecaoParaBaralho);
        HBox conteudo = new HBox(10);
        conteudo.getChildren().addAll(scrollBaralho, scrollColecao);

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.setOnAction(e -> lobby.mostrar());

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(txtTitulo, conteudo, btnVoltar);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}