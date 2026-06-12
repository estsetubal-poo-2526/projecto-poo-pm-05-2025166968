package org.example.model;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Perfil implements java.io.Serializable{
    private static Perfil instancia;
    private String nome;
    private String baralhoSelecionado;
    private int moedas;
    private Map<String, Carta> colecao;
    private Map<String, Integer> quantidades;
    private Map<String, List<Carta>> baralhosSalvos;
    private List<Carta> baralhoAtual;

    private Perfil(){
        this.nome = "Jogador";
        this.moedas = 200;
        this.colecao = new LinkedHashMap<>();
        this.quantidades = new LinkedHashMap<>();
        this.baralhoAtual = new ArrayList<>();
        this.baralhosSalvos = new LinkedHashMap<>();
        this.baralhoSelecionado = null;
        adicionarCartasStarter();
    }

    public void salvarBaralho(String nome){
        if (baralhoAtual.isEmpty()){
            return;
        }
        baralhosSalvos.put(nome, new ArrayList<>(baralhoAtual));
        baralhoAtual.clear();
    }

    public void selecionarBaralho(String nome){
        if (baralhosSalvos.containsKey(nome)){
            baralhoSelecionado = nome;
            baralhoAtual = new ArrayList<>(baralhosSalvos.get(nome));
        }
    }

    public Map<String, List<Carta>> getBaralhosSalvos(){
        return baralhosSalvos;
    }

    public String getBaralhoSelecionado(){
        return baralhoSelecionado;
    }

    public static Perfil getInstancia(){
        if (instancia == null){
            instancia = new Perfil();
        }
        return instancia;
    }

    private void adicionarCartasStarter(){
        for (Carta carta : GeradorCartas.criarBaralhoTeste().getCartas()){
            adicionarCarta(carta);
        }
        adicionarCarta(new Pocao("pocaoPequena", Raridade.Comum, 20));
        adicionarCarta(new Pocao("pocaoMedia", Raridade.Incomum, 40));
        adicionarCarta(new Treinador("treinador", Raridade.Comum, 10, 0));
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

    public void setBaralhoSelecionado(String nome){
        this.baralhoSelecionado = nome;
    }

    public static final String FICHEIRO = "perfil.dat";

    public static void guardar() {
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(
                new java.io.FileOutputStream(FICHEIRO))) {
            oos.writeObject(instancia);
            System.out.println("Perfil guardado em: " + new java.io.File(FICHEIRO).getAbsolutePath());
        } catch (java.io.IOException e) {
            System.out.println("Erro ao guardar: " + e.getMessage());
        }
    }

    public static void carregar(){
        java.io.File f = new java.io.File(FICHEIRO);
        if (!f.exists()){
            return;
        }
        try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(
                new java.io.FileInputStream(FICHEIRO))) {
            instancia = (Perfil) ois.readObject();
            System.out.println("Perfil carregado!");
        } catch (Exception e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
        }
    }
}
