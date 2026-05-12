package org.example;

public abstract class Carta {
    protected String nome;
    protected int hp;
    protected int maxHp;
    protected int atk;
    protected int def;
    protected Elemento elemento;
    protected Raridade raridade;

    public Carta(String nome, int hp, int atk, int def, Elemento elemento, Raridade raridade){
        this.nome = nome;
        this.hp = hp;
        this.maxHp = hp;
        this.atk = atk;
        this.def = def;
        this.elemento = elemento;
        this.raridade = raridade;
    }

    public String getNome(){
        return nome;
    }

    public int getHp(){
        return hp;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public int getAtk(){
        return atk;
    }

    public int getDef(){
        return def;
    }

    public Elemento getElemento(){
        return elemento;
    }

    public Raridade getRaridade(){
        return raridade;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public void setAtk(int atk){
        this.atk = atk;
    }

    public void setDef(int def){
        this.def = def;
    }

    public abstract void aplicarEfeito(Carta alvo);

    @Override
    public String toString(){
        return nome + "[" + elemento + "] HP: " + hp + "ATK: " + atk + "DEF: " + def;
    }
}
