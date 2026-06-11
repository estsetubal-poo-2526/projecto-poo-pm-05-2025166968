package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Baralho implements Serializable {

    private List<Carta> cartas;
    private Random random;

    public Baralho() {
        this.cartas = new ArrayList<>();
        this.random = new Random();
    }

    public void adicionarCarta(Carta carta) {
        cartas.add(carta);
    }

    public Carta sacarCarta() {
        if (cartas.isEmpty()) return null;
        int index = random.nextInt(cartas.size());
        return cartas.remove(index);
    }

    public boolean estaVazio() {
        return cartas.isEmpty();
    }

    public int getTamanho() {
        return cartas.size();
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    @Override
    public String toString() {
        return "Baralho com " + cartas.size() + " cartas";
    }
}