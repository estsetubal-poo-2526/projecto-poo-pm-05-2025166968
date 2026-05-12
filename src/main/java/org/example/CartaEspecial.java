package org.example;

public abstract class CartaEspecial extends Carta{
    public CartaEspecial(String nome, int hp, int atk, int def, Elemento elemento, Raridade raridade){
        super(nome, hp, atk, def, elemento, raridade);
    }

    @Override
    public abstract void aplicarEfeito(Carta alvo);

    @Override
    public String toString(){
        return "[Especial] " + super.toString();
    }
}
