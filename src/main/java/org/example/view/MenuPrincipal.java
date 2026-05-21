package org.example.view;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuPrincipal {
    private Stage stage;
    public MenuPrincipal(Stage stage){
        this.stage = stage;
    }

    public void mostrar(){
        Text titulo = new Text("Card Arena");
        titulo.setFont(Font.font(48));

        Button btnNovoJogo = new Button("Novo Jogo");
        Button btnSair = new Button("Sair");

        btnNovoJogo.setPrefWidth(200);
        btnSair.setPrefWidth(200);

        btnNovoJogo.setOnAction(e -> {
            Lobby lobby = new Lobby(stage);
            lobby.mostrar();
        });

        btnSair.setOnAction(e -> {
            stage.close();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titulo, btnNovoJogo, btnSair);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}
