package org.example;

public class Treinador extends CartaEspecial{
    private int buffAtk;
    private int buffDef;

    public Treinador(String nome, Raridade raridade, int buffAtk, int buffDef){
        super(nome, 0, 0, 0, Elemento.Normal, raridade);
        this.buffAtk = buffAtk;
        this.buffDef = buffDef;
    }

    public int getBuffAtk(){
        return buffAtk;
    }

    public int getBuffDef(){
        return buffDef;
    }

    @Override
    public void aplicarEfeito(Carta alvo){
        if (alvo instanceof CartaCriatura){
            CartaCriatura criatura = (CartaCriatura) alvo;
            criatura.setAtk(criatura.getAtk() + buffAtk);
            criatura.setDef(criatura.getDef() + buffDef);
        }
    }

    @Override
    public String toString(){
        return super.toString() + " | Buff ATK: " + buffAtk + "DEF: " + buffDef;
    }
}
