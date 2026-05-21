package org.example.view;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Lobby {
    private Stage stage;
    public Lobby(Stage stage){
        this.stage = stage;
    }

    public void mostrar(){
        Tab abaCoelcao = new Tab("Coleção");
        abaCoelcao.setClosable(false);
        abaCoelcao.setContent(new Text("Coleção de Cartas"));

        Tab abaBaralhos = new Tab("Baralhos");
        abaBaralhos.setClosable(false);
        abaBaralhos.setContent(new Text("Gestão de Baralhos"));

        Tab abaLoja = new Tab("Loja");
        abaLoja.setClosable(false);
        abaLoja.setContent(new Text("Loja de Packs"));

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(abaCoelcao, abaBaralhos, abaLoja);

        javafx.scene.control.Button btnJogar = new javafx.scene.control.Button("Iniciar Jogo");
        btnJogar.setOnAction(e -> {
            SalaJogo salaJogo = new SalaJogo(stage);
            salaJogo.mostrar();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(tabPane, btnJogar);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}
