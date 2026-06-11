package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Carta;
import org.example.model.GeradorCartas;
import org.example.model.Perfil;
import java.util.ArrayList;
import java.util.List;

public class LojaView {
    private Stage stage;
    private Lobby lobby;

    public LojaView(Stage stage, Lobby lobby) {
        this.stage = stage;
        this.lobby = lobby;
    }

    public void mostrar() {
        Text txtTitulo = new Text("Loja");
        txtTitulo.setFont(Font.font(28));

        Text txtMoedas = new Text("Moedas: " + Perfil.getInstancia().getMoedas());
        txtMoedas.setFont(Font.font(20));

        Button btnPackBasico = new Button("Pack Básico\n6 cartas\n-50 moedas");
        btnPackBasico.setPrefSize(150, 100);
        btnPackBasico.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 50) {
                Perfil.getInstancia().removerMoedas(50);
                List<Carta> cartas = new ArrayList<>(GeradorCartas.abrirPack());
                for (Carta carta : cartas) Perfil.getInstancia().adicionarCarta(carta);
                EcraAberturaPack ecra = new EcraAberturaPack(stage, cartas);
                ecra.setLobby(lobby);
                ecra.mostrar();
            }
        });

        Button btnPackRaro = new Button("Pack Raro\n6 cartas\n-100 moedas");
        btnPackRaro.setPrefSize(150, 100);
        btnPackRaro.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 100) {
                Perfil.getInstancia().removerMoedas(100);
                List<Carta> cartas = new ArrayList<>(GeradorCartas.abrirPack());
                for (Carta carta : cartas) Perfil.getInstancia().adicionarCarta(carta);
                EcraAberturaPack ecra = new EcraAberturaPack(stage, cartas);
                ecra.setLobby(lobby);
                ecra.mostrar();
            }
        });

        Button btnPackEpico = new Button("Pack Épico\n6 cartas\n-200 moedas");
        btnPackEpico.setPrefSize(150, 100);
        btnPackEpico.setOnAction(e -> {
            if (Perfil.getInstancia().getMoedas() >= 200) {
                Perfil.getInstancia().removerMoedas(200);
                List<Carta> cartas = new ArrayList<>(GeradorCartas.abrirPack());
                for (Carta carta : cartas) Perfil.getInstancia().adicionarCarta(carta);
                EcraAberturaPack ecra = new EcraAberturaPack(stage, cartas);
                ecra.setLobby(lobby);
                ecra.mostrar();
            }
        });

        HBox packs = new HBox(20);
        packs.setAlignment(Pos.CENTER);
        packs.getChildren().addAll(btnPackBasico, btnPackRaro, btnPackEpico);

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.setOnAction(e -> lobby.mostrar());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(txtTitulo, txtMoedas, packs, btnVoltar);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}