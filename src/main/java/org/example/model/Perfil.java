package org.example.model;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Perfil {
    private static Perfil instancia;
    private String nome;
    private int moedas;
    private Map<String, Carta> colecao;
    private Map<String, Integer> quantidades;
    private List<Carta> baralhoAtual;

    private Perfil(){
        this.nome = "Jogador";
        this.moedas = 200;
        this.colecao = new LinkedHashMap<>();
        this.quantidades = new LinkedHashMap<>();
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
        for (CartaCriatura carta : GeradorCartas.criarBaralhoTeste().getCartas()){
            adicionarCarta(carta);
        }
        adicionarCarta(new Pocao("Poção Pequena", Raridade.Comum, 20));
        adicionarCarta(new Pocao("Poção Média", Raridade.Incomum, 40));
        adicionarCarta(new Treinador("Treinador Básico", Raridade.Comum, 10, 0));
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

    public Map<String, Carta> getColecao(){
        return colecao;
    }

    public void adicionarCarta(Carta carta){
        colecao.put(carta.getNome(), carta);
        quantidades.put(carta.getNome(), quantidades.getOrDefault(carta.getNome(), 0) + 1);
    }

    public Map<String, Integer> getQuantidades(){
        return quantidades;
    }

    public List<Carta> getBaralhoAtual(){
        return baralhoAtual;
    }

    public boolean adicionarAoBaralho(Carta carta){
        if (baralhoAtual.size() >= 15){
            return false;
        }

        long count = baralhoAtual.stream().filter(c -> c.getNome().equals(carta.getNome())).count();

        if (count >= 4){
            return false;
        }

        int qtd = quantidades.getOrDefault(carta.getNome(), 0);

        if (qtd <= 0){
            return false;
        }

        if (carta instanceof CartaEspecial){
            long especiais = baralhoAtual.stream().filter(c -> c instanceof CartaEspecial).count();
            if (especiais >= 5){
                return false;
            }
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
