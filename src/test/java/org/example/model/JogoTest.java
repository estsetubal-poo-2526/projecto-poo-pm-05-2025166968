package org.example.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JogoTest {
    private Jogo jogo;

    @BeforeEach
    void setup() {
        jogo = new Jogo("Jogador", "PC");
        jogo.getJogador(0).setBaralho(criarBaralhoTeste());
        jogo.getJogador(1).setBaralho(criarBaralhoTeste());
    }

    private Baralho criarBaralhoTeste(){
        Baralho baralho = new Baralho();
        baralho.adicionarCarta(new CartaCriatura("Pikachu", 45, 40, 5, Elemento.Eletrico, Raridade.Incomum));
        baralho.adicionarCarta(new CartaCriatura("Squirtle", 60, 20, 20, Elemento.Agua, Raridade.Comum));
        baralho.adicionarCarta(new CartaCriatura("Charmander", 50, 30, 10, Elemento.Fogo, Raridade.Comum));
        return baralho;
    }

    @Test
    void testIniciarJogo(){
        jogo.iniciarJogo();
        assertEquals(3, jogo.getJogador(0).getMao().size());
        assertEquals(3, jogo.getJogador(1).getMao().size());
    }

    @Test
    void testJogoNaoTerminadoNoInicio(){
        jogo.iniciarJogo();
        assertFalse(jogo.isJogoTerminado());
    }

    @Test
    void testVencedorNuloNoInicio(){
        jogo.iniciarJogo();
        assertNull(jogo.getVencedor());
    }

    @Test
    void testProximoTurno(){
        jogo.iniciarJogo();
        int turnoInicial = jogo.getTurnoAtual();
        jogo.proximoTurno();
        jogo.proximoTurno();
        assertTrue(jogo.getTurnoAtual() > turnoInicial);
    }

    @Test
    void testJogoTerminaQuandoSemCartas(){
        jogo.iniciarJogo();
        jogo.getJogador(0).getMao().clear();
        jogo.verificarFimJogo();
        assertTrue(jogo.isJogoTerminado());
    }
}
