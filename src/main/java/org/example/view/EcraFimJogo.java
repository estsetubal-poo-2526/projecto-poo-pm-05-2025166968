package org.example.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Perfil;

public class EcraFimJogo {
    private Stage stage;
    private String nomeVencedor;
    private boolean jogadorGanhou;

    public EcraFimJogo(Stage stage, String nomeVencedor, boolean jogadorGanhou) {
        this.stage = stage;
        this.nomeVencedor = nomeVencedor;
        this.jogadorGanhou = jogadorGanhou;
    }

    public void mostrar() {
        Text txtResultado = new Text(jogadorGanhou ? "VITÓRIA!" : "DERROTA!");
        txtResultado.setStyle(jogadorGanhou ?
                "-fx-fill: #f5c842; -fx-font-size: 72px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(245,200,66,0.8), 20, 0, 0, 0);" :
                "-fx-fill: #e74c3c; -fx-font-size: 72px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(231,76,60,0.8), 20, 0, 0, 0);");

        Text txtVencedor = new Text("Vencedor: " + nomeVencedor);
        txtVencedor.setStyle("-fx-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        int moedasGanhas = jogadorGanhou ? 40 : 10;
        Text txtMoedas = new Text("+" + moedasGanhas + " moedas  |  Total: " + Perfil.getInstancia().getMoedas() + " 💰");
        txtMoedas.setStyle("-fx-fill: #f5c842; -fx-font-size: 20px;");

        Button btnVoltar = new Button("Voltar ao Lobby");
        btnVoltar.getStyleClass().add("btn-primario");
        btnVoltar.setPrefWidth(250);
        btnVoltar.setOnAction(e -> {
            Perfil.guardar();
            Lobby lobby = new Lobby(stage);
            lobby.mostrar();
        });

        Text txtTrofeu = new Text(jogadorGanhou ? "🏆" : "💀");
        txtTrofeu.setStyle("-fx-font-size: 80px;");

        VBox layout = new VBox(25);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(txtTrofeu, txtResultado, txtVencedor, txtMoedas, btnVoltar);

        Scene scene = new Scene(layout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}