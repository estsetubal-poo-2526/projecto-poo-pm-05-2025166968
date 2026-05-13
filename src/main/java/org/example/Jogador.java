package org.example;
import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private Baralho baralho;
    private List<Carta> mao;
    private Campo campo;
    private int moedas;

    public Jogador(String nome){
        this.nome = nome;
        this.baralho = new Baralho();
        this.mao = new ArrayList<>();
        this.campo = new Campo();
        this.moedas = 0;
    }

    public void sacarCarta(){
        if (!baralho.estaVazio()){
            mao.add(baralho.sacarCarta());
        }
    }

    public boolean jogarCriatura(int indexMao, int indexCampo){
        if (indexMao < 0 || indexMao >= mao.size()){
            return false;
        }
        Carta carta = mao.get(indexMao);

        if (!(carta instanceof CartaCriatura)){
            return false;
        }
        CartaCriatura criatura = (CartaCriatura) carta;

        if (campo.colocarCriatura(criatura, indexCampo)){
            mao.remove(indexMao);
            return true;
        }
        return false;
    }

    public boolean jogarEspecial(int indexMao, int indexCampo){
        if (indexMao < 0 || indexMao >= mao.size()){
            return false;
        }
        Carta carta = mao.get(indexMao);

        if (!(carta instanceof CartaEspecial)){
            return false;
        }
        CartaEspecial especial = (CartaEspecial) carta;

        if (campo.colocarEspecial(especial, indexCampo)){
            mao.remove(indexMao);
            return true;
        }
        return false;
    }

    public boolean semCartas(){
        return baralho.estaVazio() && mao.isEmpty() && !campo.temCriaturas();
    }

    public void adicionarMoedas(int quantidade){
        moedas += quantidade;
    }

    public void removerMoedas(int quantidade){
        moedas -= quantidade;
    }

    public String getNome(){
        return nome;
    }

    public Baralho getBaralho() {
        return baralho;
    }

    public List<Carta> getMao() {
        return mao;
    }

    public Campo getCampo() {
        return campo;
    }

    public int getMoedas() {
        return moedas;
    }

    @Override
    public String toString(){
        return nome + " | Mâo: " + mao.size() + " cartas | Moedas: " + moedas;
    }
}
