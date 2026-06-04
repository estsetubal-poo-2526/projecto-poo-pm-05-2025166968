package org.example.controller;
import org.example.model.*;
import org.example.view.EcraFimJogo;
import org.example.view.SalaJogo;
import org.example.view.Lobby;
import javafx.stage.Stage;
import org.example.model.CartaCriatura;
import org.example.model.Elemento;

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
        jogadaPC();
        SalaJogo salaJogo = new SalaJogo(stage, this);
        salaJogo.mostrar();
    }

    public void passarTurno(){
        jogo.proximoTurno();
        turnoPC();
        if (jogo.isJogoTerminado()){
            terminarJogo();
        }
    }

    public void turnoPC(){
        CartaCriatura[] campoPC = jogo.getJogador(1).getCampo().getEspacosCriatura();
        CartaCriatura[] campoJogador = jogo.getJogador(0).getCampo().getEspacosCriatura();

        jogadaPC();

        CartaCriatura melhorAtacante = null;
        int indexMelhor = -1;
        for (int i = 0; i < 5; i++){
            if (campoPC[i] != null && campoPC[i].podeAtacar(jogo.getJogador(1).getCampo().getTurnoAtual())){
                if (melhorAtacante == null || campoPC[i].getAtk() > melhorAtacante.getAtk()){
                    melhorAtacante = campoPC[i];
                    indexMelhor = i;
                }
            }
        }

        if (melhorAtacante != null){
            for (int j = 0; j < 5; j++){
                if (campoJogador[j] != null){
                    double fator = calcularFatorElemento(melhorAtacante.getElemento(), campoJogador[j].getElemento());
                    int dano = (int)(melhorAtacante.getAtk() * fator);
                    campoJogador[j].receberDano(dano);

                    if (!campoJogador[j].estaViva()){
                        jogo.getJogador(0).getCampo().removerCriatura(j);
                        campoJogador[j] = null;
                    }
                    break;
                }
            }
        }
        jogo.verificarFimJogo();
    }

    public void atacar(int indexAtacante, int indexAlvo){
        CartaCriatura atacante = jogo.getJogador(0).getCampo().getEspacosCriatura()[indexAtacante];
        CartaCriatura alvo = jogo.getJogador(1).getCampo().getEspacosCriatura()[indexAlvo];

        System.out.println("Atacante: " + (atacante != null ? atacante.getNome() : "null"));
        System.out.println("Alvo: " + (alvo != null ? alvo.getNome() : "null"));

        if (atacante == null || alvo == null){
            System.out.println("Atacante ou alvo é null!");
            return;
        }

        System.out.println("Pode atacar: " + atacante.podeAtacar(jogo.getJogador(0).getCampo().getTurnoAtual()));
        System.out.println("Turno entrada atacante: " + atacante.getTurnoEntrada());
        System.out.println("Turno atual: " + jogo.getJogador(0).getCampo().getTurnoAtual());

        if (!atacante.podeAtacar(jogo.getJogador(0).getCampo().getTurnoAtual())){
            System.out.println("Não pode atacar!");
            return;
        }

        double fator = calcularFatorElemento(atacante.getElemento(), alvo.getElemento());
        int dano = (int)(atacante.getAtk() * fator);
        System.out.println("Dano: " + dano);
        System.out.println("HP antes: " + alvo.getHp());
        alvo.receberDano(dano);
        System.out.println("Hp depois: " + alvo.getHp());

        if (!alvo.estaViva()){
            jogo.getJogador(1).getCampo().removerCriatura(indexAlvo);
        }

        jogo.verificarFimJogo();
    }

    public void jogadaPC(){
        Jogador pc = jogo.getJogador(1);
        Campo campoPC = pc.getCampo();

        for (int i = 0; i < 5; i++){
            if (campoPC.getEspacosCriatura()[i] == null && !pc.getMao().isEmpty()){
                pc.jogarCriatura(0, i);

                if (campoPC.getEspacosCriatura()[i] != null){
                    campoPC.getEspacosCriatura()[i].setTurnoEntrada(0);
                }
            }
        }
    }

    private double calcularFatorElemento(Elemento atacante, Elemento alvo){
        if (temVantagem(atacante, alvo)){
            return 1.5;
        }
        if (temVantagem(alvo, atacante)){
            return 0.7;
        }
        return 1.0;
    }

    private boolean temVantagem(Elemento atacante, Elemento alvo){
        return switch(atacante){
            case Fogo -> alvo == Elemento.Erva || alvo == Elemento.Gelo;
            case Agua -> alvo == Elemento.Fogo;
            case Erva -> alvo == Elemento.Agua;
            case Eletrico -> alvo == Elemento.Agua || alvo == Elemento.Voador;
            case Voador -> alvo == Elemento.Erva;
            case Gelo -> alvo == Elemento.Erva || alvo == Elemento.Voador;
            case Normal -> false;
        };
    }

    public void terminarJogo(){
        Jogador vencedor = jogo.getVencedor();
        boolean jogadorGanhou = vencedor == jogo.getJogador(0);
        EcraFimJogo ecraFim = new EcraFimJogo(stage, vencedor.getNome(), jogadorGanhou);
        ecraFim.mostrar();
    }

    public Jogo getJogo(){
        return jogo;
    }
}
