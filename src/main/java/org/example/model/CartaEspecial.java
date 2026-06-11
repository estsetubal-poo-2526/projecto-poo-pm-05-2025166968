package org.example.model;

public abstract class CartaEspecial extends Carta  implements java.io.Serializable{
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
