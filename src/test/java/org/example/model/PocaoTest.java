package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PocaoTest {

    @Test
    void testCuraHP() {
        CartaCriatura carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        carta.receberDano(20);
        assertEquals(25, carta.getHp());

        Pocao pocao = new Pocao("pocaoPequena", Raridade.Comum, 20);
        pocao.aplicarEfeito(carta);
        assertEquals(45, carta.getHp());
    }

    @Test
    void testCuraNaoUltrapassaMaxHP() {
        CartaCriatura carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        carta.receberDano(5);
        assertEquals(40, carta.getHp());

        Pocao pocao = new Pocao("pocaoPequena", Raridade.Comum, 20);
        pocao.aplicarEfeito(carta);
        assertEquals(45, carta.getHp()); // não passa do máximo
    }
}