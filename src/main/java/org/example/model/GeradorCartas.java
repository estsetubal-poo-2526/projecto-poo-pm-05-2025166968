package org.example.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorCartas {
    private static final List<CartaCriatura> todasAsCartas = new ArrayList<>();
    private static final Random random = new Random();

    static{
        todasAsCartas.add(new CartaCriatura("Charmander", 50, 30, 10, Elemento.Fogo, Raridade.Comum));
        todasAsCartas.add(new CartaCriatura("Squirtle", 60, 20, 20, Elemento.Agua, Raridade.Comum));
        todasAsCartas.add(new CartaCriatura("Bulbasaur", 55, 25, 15, Elemento.Erva, Raridade.Comum));
        todasAsCartas.add(new CartaCriatura("Pidgey", 40, 25, 10, Elemento.Voador, Raridade.Comum));
        todasAsCartas.add(new CartaCriatura("Seel", 55, 20, 20, Elemento.Gelo, Raridade.Comum));
        todasAsCartas.add(new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum));
        todasAsCartas.add(new CartaCriatura("Eevee", 50, 25, 15, Elemento.Normal, Raridade.Incomum));
        todasAsCartas.add(new CartaCriatura("Growlithe", 55, 35, 10, Elemento.Fogo, Raridade.Incomum));
        todasAsCartas.add(new CartaCriatura("Poliwag", 50, 30, 15, Elemento.Agua, Raridade.Incomum));
        todasAsCartas.add(new CartaCriatura("Raichu", 65, 55, 10, Elemento.Eletrico, Raridade.Raro));
        todasAsCartas.add(new CartaCriatura("Pidgeot", 70, 50, 20, Elemento.Voador, Raridade.Raro));
        todasAsCartas.add(new CartaCriatura("Arcanine", 80, 55, 20, Elemento.Fogo, Raridade.Raro));
        todasAsCartas.add(new CartaCriatura("Dewgong", 75, 40, 35, Elemento.Gelo, Raridade.Raro));
        todasAsCartas.add(new CartaCriatura("Articuno", 85, 45, 40, Elemento.Gelo, Raridade.Epico));
        todasAsCartas.add(new CartaCriatura("Zapdos", 80, 65, 20, Elemento.Eletrico, Raridade.Epico));
        todasAsCartas.add(new CartaCriatura("Snorlax", 120, 30, 50, Elemento.Normal, Raridade.Epico));
        todasAsCartas.add(new CartaCriatura("Charizard", 90, 60, 20, Elemento.Fogo, Raridade.Lendario));
        todasAsCartas.add(new CartaCriatura("Blastoise", 100, 50, 40, Elemento.Agua, Raridade.Lendario));
        todasAsCartas.add(new CartaCriatura("Venusaur", 95, 55, 35, Elemento.Erva, Raridade.Lendario));
    }

    public static Baralho criarBaralhoTeste(){
        Baralho baralho = new Baralho();
        for (CartaCriatura carta : todasAsCartas){
            baralho.adicionarCarta(new CartaCriatura(carta.getNome(), carta.getHp(), carta.atk, carta.getDef(), carta.getElemento(), carta.getRaridade()));
        }
        return baralho;
    }

    public static List<CartaCriatura> abrirPack(){
        List<CartaCriatura> pack = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            Raridade raridade = sortearRaridade();
            CartaCriatura carta = cartaAleatoriaDaRaridade(raridade);
            if (carta != null){
                pack.add(new CartaCriatura(carta.getNome(), carta.getHp(), carta.atk, carta.getDef(), carta.getElemento(), carta.getRaridade()));
            }
        }
        return pack;
    }

    private static Raridade sortearRaridade(){
        int roll = random.nextInt(100);

        if (roll < 60){
            return Raridade.Comum;
        }

        if (roll < 88){
            return Raridade.Incomum;
        }

        if (roll < 97){
            return Raridade.Raro;
        }

        if (roll < 99){
            return Raridade.Epico;
        }

        return Raridade.Lendario;
    }

    private static CartaCriatura cartaAleatoriaDaRaridade(Raridade raridade){
        List<CartaCriatura> filtradas = new ArrayList<>();
        for (CartaCriatura c : todasAsCartas){
            if (c.getRaridade() == raridade){
                filtradas.add(c);
            }
        }
        if (filtradas.isEmpty()){
            return todasAsCartas.get(random.nextInt(todasAsCartas.size()));
        }
        return filtradas.get(random.nextInt(filtradas.size()));
    }
}
