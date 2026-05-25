package org.example.controller;
import org.example.model.*;
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

    public void atacar(int indexAtacante, int indexAlvo){
        CartaCriatura atacante = jogo.getJogador(0).getCampo().getEspacosCriatura()[indexAtacante];
        CartaCriatura alvo = jogo.getJogador(1).getCampo().getEspacosCriatura()[indexAlvo];

        if (atacante == null || alvo == null){
            return;
        }

        if (!atacante.podeAtacar(jogo.getJogador(0).getCampo().getTurnoAtual())){
            return;
        }

        double fator = calcularFatorElemento(atacante.getElemento(), alvo.getElemento());
        int dano = (int)(atacante.getAtk() * fator);
        alvo.receberDano(dano);

        if (!alvo.estaViva()){
            jogo.getJogador(1).getCampo().removerCriatura(indexAlvo);
        }

        verificarFimJogo();
    }

    private double calcularFatorElemento(Elemento atacante, Elemento alvo){
        if (temVantagem(atacante, alvo)){
            return 1.5;
        }
        if (temVamtagem(alvo, atacante)){
            return 0.7;
        }
        return 1.0;
    }

    private boolean temVantagem(Elemento atacante, Elemento alvo){
        return switch(atacante){
            case Fogo -> alvo == Elemento.Erva || alvo == Elemento.Gelo;
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
