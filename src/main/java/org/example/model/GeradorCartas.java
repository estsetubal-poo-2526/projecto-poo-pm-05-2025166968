package org.example.model;

public class GeradorCartas {
    public static Baralho criarBaralhoTeste(){
        Baralho baralho = new Baralho();

        baralho.adicionarCarta(new CartaCriatura("Charmander", 50, 30, 10, Elemento.Fogo, Raridade.Comum));
        baralho.adicionarCarta(new CartaCriatura("Squirtle", 60, 20, 20, Elemento.Agua, Raridade.Comum));
        baralho.adicionarCarta(new CartaCriatura("Bulbasaur", 55, 25, 15, Elemento.Erva, Raridade.Comum));
        baralho.adicionarCarta(new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum));
        baralho.adicionarCarta(new CartaCriatura("Pidgey", 40, 25, 10, Elemento.Voador, Raridade.Comum));
        baralho.adicionarCarta(new CartaCriatura("Seel", 55, 20, 20, Elemento.Gelo, Raridade.Comum));
        baralho.adicionarCarta(new CartaCriatura("Eevee", 50, 25, 15, Elemento.Normal, Raridade.Incomum));
        baralho.adicionarCarta(new CartaCriatura("Charizard", 90, 60, 20, Elemento.Fogo, Raridade.Lendario));
        baralho.adicionarCarta(new CartaCriatura("Blastoise", 100, 50, 40, Elemento.Agua, Raridade.Lendario));
        baralho.adicionarCarta(new CartaCriatura("Venusaur", 95, 55, 35, Elemento.Erva, Raridade.Lendario));
        baralho.adicionarCarta(new CartaCriatura("Raichu", 65, 55, 10, Elemento.Eletrico, Raridade.Raro));
        baralho.adicionarCarta(new CartaCriatura("Articuno", 85, 45, 40, Elemento.Gelo, Raridade.Epico));
        baralho.adicionarCarta(new CartaCriatura("Zapdos", 80, 65, 20, Elemento.Eletrico, Raridade.Epico));
        baralho.adicionarCarta(new CartaCriatura("Pidgeot", 70, 50, 20, Elemento.Voador, Raridade.Raro));
        baralho.adicionarCarta(new CartaCriatura("Snorlax", 120, 30, 50, Elemento.Normal, Raridade.Epico));

        return baralho;
    }
}
