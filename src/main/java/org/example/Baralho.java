package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Baralho {
    private List<CartaCriatura> cartas;
    private Random random;

    public Baralho(){
        this.cartas = new ArrayList<>();
        this.random = new Random();
    }

    public void adicionarCarta(CartaCriatura carta){
        cartas.add(carta);
    }

    public CartaCriatura sacarCarta(){
        if (cartas.isEmpty()) return null;
        int index = random.nextInt(cartas.size());
        return cartas.remove(index);
    }

    public boolean estaVazio(){
        return cartas.isEmpty();
    }

    public int getTamanho(){
        return cartas.size();
    }

    @Override
    public String toString(){
        return "Baralho com " + cartas.size() + " cartas";
    }
}
