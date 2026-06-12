package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TreinadorTest {

    @Test
    void testBuffAtk() {
        CartaCriatura carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        Treinador treinador = new Treinador("treinador", Raridade.Comum, 10, 0);
        treinador.aplicarEfeito(carta);
        assertEquals(50, carta.getAtk());
    }

    @Test
    void testBuffDef() {
        CartaCriatura carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        Treinador treinador = new Treinador("treinador", Raridade.Comum, 0, 10);
        treinador.aplicarEfeito(carta);
        assertEquals(15, carta.getDef());
    }
}