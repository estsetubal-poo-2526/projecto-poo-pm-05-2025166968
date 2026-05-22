package org.example.controller;
import org.example.model.GeradorCartas;
import org.example.model.Jogo;
import org.example.model.Jogador;
import org.example.view.SalaJogo;
import org.example.view.Lobby;
import javafx.stage.Stage;

public class JogoController {
    private Jogo jogo;
    private Stage stage;

    public JogoController(Stage stage){
        this.stage = stage;
        this.jogo = new Jogo("Jogador", "PC");
        jogo.getJogador(0).setBaralho(GeradorCartas.criarBaralhoTeste());
        jogo.getJogador(1).setBaralho(GeradorCartas.criarBaralhoTeste());
    }

    public void iniciarJogo(){
        jogo.iniciarJogo();
        SalaJogo salaJogo = new SalaJogo(stage, this);
        salaJogo.mostrar();
    }

    public void passarTurno(){
        jogo.proximoTurno();
        if (jogo.isJogoTerminado()){
            terminarJogo();
        }
    }

    public void terminarJogo(){
        Jogador vencedor = jogo.getVencedor();
        System.out.println("Vencedor: " + vencedor.getNome());
        Lobby lobby = new Lobby(stage);
        lobby.mostrar();
    }

    public Jogo getJogo(){
        return jogo;
    }
}
