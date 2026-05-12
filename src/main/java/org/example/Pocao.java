package org.example;

public class Pocao extends CartaEspecial{
    private int cura;

    public Pocao(String nome, Raridade raridade, int cura){
        super(nome, 0, 0, 0, Elemento.Normal, raridade);
        this.cura = cura;
    }

    public int getCura(){
        return cura;
    }

    @Override
    public void aplicarEfeito(Carta alvo){
        if (alvo instanceof  CartaEspecial){
            CartaCriatura criatura = (CartaCriatura) alvo;
            int novoHp = Math.min(criatura.getHp() + cura, criatura.getMaxHp());
            criatura.setHp(novoHp);
        }
    }

    @Override
    public String toString(){
        return super.toString() + " | Cura: " + cura;
    }
}
