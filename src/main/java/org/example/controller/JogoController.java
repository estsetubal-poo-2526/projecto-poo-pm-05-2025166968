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

    public void usarCartaEspecial(int indexJogador, int indexEspecial){
        CartaEspecial especial = jogo.getJogador(indexJogador).getCampo().getEspacosEspecial()[indexEspecial];
        if (especial == null){
            return;
        }

        CartaCriatura[] criaturas = getJogo().getJogador(indexJogador).getCampo().getEspacosCriatura();
        CartaCriatura alvo = null;
        for (CartaCriatura c : criaturas){
            if (c != null){
                if (alvo == null || c.getHp() < alvo.getHp()){
                    alvo = c;
                }
            }
        }

        if (alvo != null){
            especial.aplicarEfeito(alvo);
            jogo.getJogador(indexJogador).getCampo().removerEspecial(indexEspecial);
        }
    }

    public void passarTurno(){
        jogo.proximoTurno();
        if (jogo.isJogoTerminado()){
            terminarJogo();
            return;
        }
        turnoPC();
        if (jogo.isJogoTerminado()){
            terminarJogo();
            return;
        }
        SalaJogo salaJogo = new SalaJogo(stage,this);
        salaJogo.mostrar();
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

        if (jogadorGanhou){
            Perfil.getInstancia().adicionarMoedas(40);
        }else{
            Perfil.getInstancia().adicionarMoedas(10);
        }

        EcraFimJogo ecraFim = new EcraFimJogo(stage, vencedor.getNome(), jogadorGanhou);
        ecraFim.mostrar();
    }

    public Jogo getJogo(){
        return jogo;
    }
}
