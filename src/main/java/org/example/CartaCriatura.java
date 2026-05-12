package org.example;

public class CartaCriatura extends Carta{
    private Posicao posicao;
    private int turnoEntrada;

    public CartaCriatura(String nome, int hp, int atk, int def, Elemento elemento, Raridade raridade){
        super(nome, hp, atk, def, elemento, raridade);
        this.posicao = Posicao.Ataque; //Carta Começa sempre em modo de ataque
        this.turnoEntrada = -1; // Significa que ainda não está em campo
    }

    public Posicao getPosicao(){
        return posicao;
    }

    public int getTurnoEntrada(){
        return turnoEntrada;
    }

    public void setTurnoEntrada(int turno){
        this.turnoEntrada = turno ;
    }

    public void mudarPosicao(){
        if (posicao == Posicao.Ataque) {
            posicao = Posicao.Defesa;
        } else {
            posicao = Posicao.Ataque;
        }
    }

    public boolean podeAtacar(int turnoAtual) {
        return posicao == Posicao.Ataque && turnoEntrada != turnoAtual;
    }

    public boolean estaViva(){
        return hp > 0;
    }

    public void receberDano(int dano){
        if (posicao == Posicao. Defesa){
            int danoReal = Math.max(0, dano - def);
            hp = Math.max(0, hp - danoReal);
        } else {
            hp = Math.max(0, hp - dano);
        }
    }

    @Override
    public void aplicarEfeito(Carta alvo){
    }

    @Override
    public String toString(){
        return super.toString() + " | Posição: " + posicao;
    }
}
