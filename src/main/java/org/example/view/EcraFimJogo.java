package org.example.view;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Perfil;

public class EcraFimJogo {
    private Stage stage;
    private String nomeVencedor;
    private boolean jogadorGanhou;

    public EcraFimJogo(Stage stage, String nomeVencedor, boolean jogadorGanhou){
        this.stage = stage;
        this.nomeVencedor = nomeVencedor;
        this.jogadorGanhou = jogadorGanhou;
    }

    public void mostrar(){
        Text txtResultado = new Text(jogadorGanhou ? "VITÓRIA" : "DERROTA");
        txtResultado.setFont(Font.font(64));

        Text txtVencedor = new Text("Vencedor: " + nomeVencedor);
        txtVencedor.setFont(Font.font(24));

        int moedasGanhas = jogadorGanhou ? 40 : 10;
        Text txtMoedas = new Text("+" + moedasGanhas + " moedas | Total: " + Perfil.getInstancia().getMoedas() + " moedas");
        txtMoedas.setFont(Font.font(20));

        Button btnVoltar = new Button("Voltar ao Menu");
        btnVoltar.setPrefWidth(200);
        btnVoltar.setOnAction(e -> {
            Lobby lobby = new Lobby(stage);
            lobby.mostrar();
        });

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(txtResultado, txtVencedor, txtMoedas, btnVoltar);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}
