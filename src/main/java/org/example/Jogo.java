package org.example;

public class Jogo {
    private Jogador[] jogadores;
    private int turnoAtual;
    private int jogadorAtivo;
    private boolean jogoTerminado;
    private Jogador vencedor;

    public Jogo(String nomeJogador1, String nomeJogador2){
        this.jogadores = new Jogador[2];
        this.jogadores[0] = new Jogador(nomeJogador1);
        this.jogadores[1] = new Jogador(nomeJogador2);
        this.turnoAtual = 1;
        this.jogadorAtivo = 0;
        this.jogoTerminado = false;
        this.vencedor = null;
    }

    public void iniciarJogo(){
        for (int i = 0; i < 3; i++){
            jogadores[0].sacarCarta();
            jogadores[1].sacarCarta();
        }
    }

    public void proximoTurno(){
        jogadorAtivo = (jogadorAtivo + 1) % 2;
        if (jogadorAtivo == 0){
            turnoAtual++;
        }
        jogadores[jogadorAtivo].getCampo().avancarTurno();

        jogadores[jogadorAtivo].sacarCarta();

        verificarFimJogo();
    }

    public void verificarFimJogo(){
        for (Jogador j : jogadores){
            if (j.semCartas()){
                jogoTerminado = true;
                vencedor = j == jogadores[0] ? jogadores[1] : jogadores[0];
            }
        }
    }

    public Jogador getJogadorAtivo(){
        return jogadores[jogadorAtivo];
    }

    public Jogador getJogador(int index){
        return jogadores[index];
    }

    public int getTurnoAtual(int index){
        return turnoAtual;
    }

    public boolean isJogoTerminado(){
        return jogoTerminado;
    }

    public Jogador getVencedor(){
        return vencedor;
    }

    @Override
    public String toString(){
        return "Turno " + turnoAtual + " | Jogador ativo: " + getJogadorAtivo().getNome();
    }
}
