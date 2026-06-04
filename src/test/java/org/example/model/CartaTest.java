package org.example.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartaTest {
    @Test
    void testCriarCarta(){
        CartaCriatura carta = new CartaCriatura("Pikachu", 45,40,5,Elemento.Eletrico, Raridade.Incomum);
        assertEquals("Pikachu", carta.getNome());
        assertEquals(45, carta.getHp());
        assertEquals(40, carta.getAtk());
        assertEquals(5, carta.getDef());
        assertEquals(Elemento.Eletrico, carta.getElemento());
        assertEquals(Raridade.Incomum, carta.getRaridade());
    }

    @Test
    void testReceberDanoEmAtaque(){
        CartaCriatura carta = new CartaCriatura("Pikachu", 45,40,5, Elemento.Eletrico, Raridade.Incomum);
        carta.mudarPosicao();
        carta.receberDano(20);
        assertEquals(30, carta.getHp());
    }

    @Test
    void testReceberDanoEmDefesa(){
        CartaCriatura carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        carta.mudarPosicao();
        carta.receberDano(20);
        assertEquals(30, carta.getHp());
    }

    @Test
    void testCartaMorre(){
        CartaCriatura carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        carta.receberDano(100);
        assertEquals(0, carta.getHp());
        assertFalse(carta.estaViva());
    }

    @Test
    void testHpNaoFicaNegativo(){
        CartaCriatura carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        carta.receberDano(999);
        assertEquals(0, carta.getHp());
    }
}
