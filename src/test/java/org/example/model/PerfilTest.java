package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PerfilTest {

    @BeforeEach
    void setup() {
        // reset do perfil para cada teste
        Perfil.resetInstancia();
    }

    @Test
    void testAdicionarCarta() {
        CartaCriatura carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        Perfil.getInstancia().adicionarCarta(carta);
        assertTrue(Perfil.getInstancia().getColecao().containsKey("Pikachu"));
    }

    @Test
    void testQuantidadeAumenta() {
        CartaCriatura carta = new CartaCriatura("TesteCarta", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        Perfil.getInstancia().adicionarCarta(carta);
        Perfil.getInstancia().adicionarCarta(carta);
        assertEquals(2, Perfil.getInstancia().getQuantidades().get("TesteCarta"));
    }

    @Test
    void testAdicionarAoBaralho() {
        CartaCriatura carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        Perfil.getInstancia().adicionarCarta(carta);
        assertTrue(Perfil.getInstancia().adicionarAoBaralho(carta));
        assertEquals(1, Perfil.getInstancia().getBaralhoAtual().size());
    }

    @Test
    void testLimiteBaralho() {
        CartaCriatura carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        for (int i = 0; i < 15; i++) {
            Perfil.getInstancia().adicionarCarta(carta);
            Perfil.getInstancia().adicionarAoBaralho(carta);
        }
        // 16ª carta não deve ser adicionada
        assertFalse(Perfil.getInstancia().adicionarAoBaralho(carta));
    }

    @Test
    void testSalvarBaralho() {
        CartaCriatura carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
        for (int i = 0; i < 15; i++) {
            Perfil.getInstancia().adicionarCarta(carta);
            Perfil.getInstancia().adicionarAoBaralho(carta);
        }
        Perfil.getInstancia().salvarBaralho("MeuBaralho");
        assertTrue(Perfil.getInstancia().getBaralhosSalvos().containsKey("MeuBaralho"));
    }
}