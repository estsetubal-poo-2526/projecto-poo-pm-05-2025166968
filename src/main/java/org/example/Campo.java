package org.example;

public class Campo {
    private CartaCriatura[] espacosCriatura;
    private CartaEspecial[] espacosEspecial;
    private int turnoAtual;

    public Campo(){
        this.espacosCriatura = new CartaCriatura[5];
        this.espacosEspecial = new CartaEspecial[2];
        this.turnoAtual = 1;
    }

    private int espacosDesbloqueados(){
        if (turnoAtual == 1){
            return 1;
        }
        if (turnoAtual == 2){
            return 3;
        }else {
            return 5;
        }
    }

    private int espacosEspeciaisDesbloqueados(){
        return turnoAtual >= 2 ? 2 : 0;
    }

    public void avancarTurno(){
        turnoAtual++;
    }

    public boolean colocarCriatura(CartaCriatura carta, int index){
        if (index < 0 || index >= espacosDesbloqueados()){
            return false;
        }
        if (espacosCriatura[index] != null){
            return false;
        }
        carta.setTurnoEntrada(turnoAtual);
        espacosCriatura[index] = carta;
        return true;
    }

    public boolean colocarEspecial(CartaEspecial carta, int index) {
        if (index < 0 || index >= espacosEspeciaisDesbloqueados()){
            return false;
        }
        if (espacosEspecial[index] != null){
            return false;
        }
        espacosEspecial[index] = carta;
        return true;
    }

    public void removerCriatura(int index){
        if (index >= 0 && index < 5) espacosCriatura[index] = null;
    }

    public void removerEspecial(int index){
        if (index >= 0 && index < 2) espacosEspecial[index] = null;
    }

    public CartaCriatura[] getEspeacosCriatura(){
        return espacosCriatura;
    }

    public CartaEspecial[] getEspacosEspecial(){
        return espacosEspecial;
    }

    public int getTurnoAtual(){
        return turnoAtual;
    }

    public boolean temCriaturas(){
        for (CartaCriatura c : espacosCriatura){
            if (c != null){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return "Campo - Turno " + turnoAtual + " | Espaços desbloqueados: " + espacosDesbloqueados();
    }
}
