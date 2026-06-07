package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Perfil {
    private static Perfil instancia;
    private String nome;
    private int moedas;
    private List<CartaCriatura> colecao;
    private List<CartaCriatura> baralhoAtual;

    private Perfil(){
        this.nome = "Jogador";
        this.moedas = 300;
        this.colecao = new ArrayList<>();
        this.baralhoAtual = new ArrayList<>();
        adicionarCartasStarter();
    }

    public static Perfil getInstancia(){
        if (instancia == null){
            instancia = new Perfil();
        }
        return instancia;
    }

    private void adicionarCartasStarter(){
        colecao.addAll(GeradorCartas.criarBaralhoTeste().getCartas());
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

    public int getMoedas(){
        return moedas;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public List<CartaCriatura> getColecao(){
        return colecao;
    }

    public void adicionarCarta(CartaCriatura carta){
        colecao.add(carta);
    }

    public List<CartaCriatura> getBaralhoAtual(){
        return baralhoAtual;
    }

    public boolean adicionarAoBaralho(CartaCriatura carta){
        if (baralhoAtual.size() >= 15){
            return false;
        }

        long count = baralhoAtual.stream().filter(c -> c.getNome().equals(carta.getNome())).count();

        if (count >= 4){
            return false;
        }

        baralhoAtual.add(carta);
        return true;
    }

    public void removerDoBaralho(int index){
        if (index >= 0 && index < baralhoAtual.size()){
            baralhoAtual.remove(index);
        }
    }
}
