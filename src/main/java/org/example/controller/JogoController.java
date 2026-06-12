package org.example.controller;
import org.example.model.*;
import org.example.view.EcraDefesa;
import org.example.view.EcraFimJogo;
import org.example.view.SalaJogo;
import org.example.view.Lobby;
import javafx.stage.Stage;

public class JogoController {
    private Jogo jogo;
    private Stage stage;

    public JogoController(Stage stage) {
        this.stage = stage;
        this.jogo = new Jogo("Jogador", "PC");
        jogo.getJogador(0).setBaralho(GeradorCartas.criarBaralhoTeste());
        jogo.getJogador(1).setBaralho(GeradorCartas.criarBaralhoPC());
    }

    public void iniciarJogo() {
        jogo.iniciarJogo();
        jogadaPC();
        SalaJogo salaJogo = new SalaJogo(stage, this);
        salaJogo.mostrar();
    }

    public void usarCartaEspecial(int indexJogador, int indexEspecial) {
        CartaEspecial especial = jogo.getJogador(indexJogador).getCampo().getEspacosEspecial()[indexEspecial];
        if (especial == null) return;

        CartaCriatura[] criaturas = jogo.getJogador(indexJogador).getCampo().getEspacosCriatura();
        CartaCriatura alvo = null;
        for (CartaCriatura c : criaturas) {
            if (c != null) {
                if (alvo == null || c.getHp() < alvo.getHp()) {
                    alvo = c;
                }
            }
        }

        if (alvo != null) {
            especial.aplicarEfeito(alvo);
            jogo.getJogador(indexJogador).getCampo().removerEspecial(indexEspecial);
        }
    }

    public void passarTurno() {
        if (jogo.isJogoTerminado()) {
            terminarJogo();
            return;
        }

        jogo.proximoTurno();

        if (jogo.isJogoTerminado()) {
            terminarJogo();
            return;
        }

        jogo.getJogador(1).sacarCarta();

        jogo.verificarFimJogo();
        if (jogo.isJogoTerminado()) {
            terminarJogo();
            return;
        }

        turnoPC();
    }

    public void turnoPC() {
        CartaCriatura[] campoPC = jogo.getJogador(1).getCampo().getEspacosCriatura();
        CartaCriatura[] campoJogador = jogo.getJogador(0).getCampo().getEspacosCriatura();

        jogadaPC();

        CartaCriatura melhorAtacante = null;
        for (int i = 0; i < 5; i++) {
            if (campoPC[i] != null && campoPC[i].podeAtacar(jogo.getJogador(1).getCampo().getTurnoAtual())) {
                if (melhorAtacante == null || campoPC[i].getAtk() > melhorAtacante.getAtk()) {
                    melhorAtacante = campoPC[i];
                }
            }
        }

        if (melhorAtacante != null) {
            CartaCriatura melhorAlvo = null;
            int indexMelhorAlvo = -1;
            int melhorDanoReal = -1;

            for (int j = 0; j < 5; j++) {
                if (campoJogador[j] != null) {
                    double fator = calcularFatorElemento(melhorAtacante.getElemento(), campoJogador[j].getElemento());
                    int danoBase = (int)(melhorAtacante.getAtk() * fator);
                    int danoReal;
                    if (campoJogador[j].getPosicao() == Posicao.Defesa) {
                        danoReal = Math.max(0, danoBase - campoJogador[j].getDef());
                    } else {
                        danoReal = danoBase;
                    }
                    boolean vaaMatar = danoReal >= campoJogador[j].getHp();
                    boolean melhorVaaMatar = melhorAlvo != null && melhorDanoReal >= melhorAlvo.getHp();
                    if (melhorAlvo == null ||
                            (vaaMatar && !melhorVaaMatar) ||
                            (vaaMatar == melhorVaaMatar && danoReal > melhorDanoReal)) {
                        melhorAlvo = campoJogador[j];
                        indexMelhorAlvo = j;
                        melhorDanoReal = danoReal;
                    }
                }
            }

            if (melhorAlvo != null) {
                double fator = calcularFatorElemento(melhorAtacante.getElemento(), melhorAlvo.getElemento());
                int danoBase = (int)(melhorAtacante.getAtk() * fator);

                boolean temDefensor = false;
                CartaCriatura[] campoJog = jogo.getJogador(0).getCampo().getEspacosCriatura();
                for (CartaCriatura c : campoJog) {
                    if (c != null && c.getPosicao() == Posicao.Defesa) {
                        temDefensor = true;
                        break;
                    }
                }

                if (temDefensor) {
                    EcraDefesa ecraDefesa = new EcraDefesa(stage, this, melhorAtacante, danoBase);
                    ecraDefesa.mostrar();
                    return;
                } else {
                    melhorAlvo.receberDano(danoBase);
                    if (!melhorAlvo.estaViva()) {
                        jogo.getJogador(0).getCampo().removerCriatura(indexMelhorAlvo);
                        campoJogador[indexMelhorAlvo] = null;
                    }
                }
            }
        }

        jogo.verificarFimJogo();
        if (jogo.isJogoTerminado()) {
            terminarJogo();
            return;
        }

        jogo.getJogador(0).sacarCarta();
        SalaJogo salaJogo = new SalaJogo(stage, this);
        salaJogo.mostrar();
    }

    public boolean atacar(int indexAtacante, int indexAlvo) {
        if (jogo.isJogoTerminado()) return false;

        CartaCriatura atacante = jogo.getJogador(0).getCampo().getEspacosCriatura()[indexAtacante];
        CartaCriatura alvo = jogo.getJogador(1).getCampo().getEspacosCriatura()[indexAlvo];

        if (atacante == null || alvo == null) return false;
        if (!atacante.podeAtacar(jogo.getJogador(0).getCampo().getTurnoAtual())) return false;

        double fator = calcularFatorElemento(atacante.getElemento(), alvo.getElemento());
        int dano = (int)(atacante.getAtk() * fator);
        alvo.receberDano(dano);

        if (!alvo.estaViva()) {
            jogo.getJogador(1).getCampo().removerCriatura(indexAlvo);
        }

        jogo.verificarFimJogo();
        return true;
    }

    public void jogadaPC() {
        Jogador pc = jogo.getJogador(1);
        Campo campoPC = pc.getCampo();

        for (int i = 0; i < 5; i++) {
            if (campoPC.getEspacosCriatura()[i] == null && !pc.getMao().isEmpty()) {
                for (int j = 0; j < pc.getMao().size(); j++) {
                    if (pc.getMao().get(j) instanceof CartaCriatura) {
                        pc.jogarCriatura(j, i);
                        if (campoPC.getEspacosCriatura()[i] != null) {
                            campoPC.getEspacosCriatura()[i].setTurnoEntrada(0);
                        }
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            if (campoPC.getEspacosEspecial()[i] == null && !pc.getMao().isEmpty()) {
                for (int j = 0; j < pc.getMao().size(); j++) {
                    if (pc.getMao().get(j) instanceof CartaEspecial) {
                        pc.jogarEspecial(j, i);
                        break;
                    }
                }
            }
        }
    }

    public void continuarAposTurnoPC() {
        jogo.verificarFimJogo();
        if (jogo.isJogoTerminado()) {
            terminarJogo();
            return;
        }
        jogo.getJogador(0).sacarCarta();
        SalaJogo salaJogo = new SalaJogo(stage, this);
        salaJogo.mostrar();
    }

    private double calcularFatorElemento(Elemento atacante, Elemento alvo) {
        if (temVantagem(atacante, alvo)) return 1.5;
        if (temVantagem(alvo, atacante)) return 0.7;
        return 1.0;
    }

    private boolean temVantagem(Elemento atacante, Elemento alvo) {
        return switch (atacante) {
            case Fogo -> alvo == Elemento.Erva || alvo == Elemento.Gelo;
            case Agua -> alvo == Elemento.Fogo;
            case Erva -> alvo == Elemento.Agua;
            case Eletrico -> alvo == Elemento.Agua || alvo == Elemento.Voador;
            case Voador -> alvo == Elemento.Erva;
            case Gelo -> alvo == Elemento.Erva || alvo == Elemento.Voador;
            case Normal -> false;
        };
    }

    public void terminarJogo() {
        Jogador vencedor = jogo.getVencedor();
        boolean jogadorGanhou = vencedor == jogo.getJogador(0);

        if (jogadorGanhou) {
            Perfil.getInstancia().adicionarMoedas(40);
        } else {
            Perfil.getInstancia().adicionarMoedas(10);
        }

        EcraFimJogo ecraFim = new EcraFimJogo(stage, vencedor.getNome(), jogadorGanhou);
        ecraFim.mostrar();
    }

    public Jogo getJogo() { return jogo; }
    public Stage getStage() { return stage; }
}