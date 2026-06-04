package org.example.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CampoTest {
    private Campo campo;
    private CartaCriatura carta;

    @BeforeEach
    void setup(){
        campo = new Campo();
        carta = new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum);
    }

    @Test
    void testColocarCartaNoTurno1(){
        boolean resultado = campo.colocarCriatura(carta, 0);
        assertTrue(resultado);
        assertEquals(carta, campo.getEspacosCriatura()[0]);
    }

    @Test
    void testNaoPodeColocarForaDoEspacoDesbloqueado(){
        boolean resultado = campo.colocarCriatura(carta, 1);
        assertFalse(resultado);
    }

    @Test
    void testRemoverCarta(){
        campo.avancarTurno();
        CartaCriatura carta2 = new CartaCriatura("Squirtle", 60, 20, 20, Elemento.Agua, Raridade.Comum);
        assertTrue(campo.colocarCriatura(carta, 0));
        assertTrue(campo.colocarCriatura(carta2, 1));
    }

    @Test
    void testEspacosDesbloqueadosProgridem(){
        campo.avancarTurno();
        CartaCriatura carta2 = new CartaCriatura("Squirtle", 60, 20, 20, Elemento.Agua, Raridade.Comum);
        assertTrue(campo.colocarCriatura(carta, 0));
        assertTrue(campo.colocarCriatura(carta2, 1));
    }

    @Test
    void testTemCriaturas(){
        assertFalse(campo.temCriaturas());
        campo.colocarCriatura(carta, 0);
        assertTrue(campo.temCriaturas());
    }
}
