package org.example.model;

public class Perfil {
    private static Perfil instancia;
    private String nome;
    private int moedas;

    private Perfil(){
        this.nome = "Jogador";
        this.moedas = 0;
    }

    public static Perfil getInstancia(){
        if (instancia == null){
            instancia = new Perfil();
        }
        return instancia;
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
}
