package org.example.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.JogoController;
import org.example.model.*;
import java.util.List;

public class SalaJogo {
    private Stage stage;
    private JogoController controller;
    private int cartaSelecionada = -1;
    private boolean jaAtacou = false;

    public SalaJogo(Stage stage, JogoController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    private ImageView getImagem(String nome, int width, int height) {
        try {
            String caminho = "/images/" + nome.toLowerCase() + ".png";
            Image img = new Image(getClass().getResourceAsStream(caminho));
            ImageView iv = new ImageView(img);
            iv.setFitWidth(width);
            iv.setFitHeight(height);
            iv.setPreserveRatio(true);
            return iv;
        } catch (Exception e) {
            return null;
        }
    }

    private String getCorElemento(Elemento e) {
        return switch (e) {
            case Fogo -> "#c0392b";
            case Agua -> "#2980b9";
            case Erva -> "#27ae60";
            case Eletrico -> "#d4a017";
            case Gelo -> "#85c1e9";
            case Voador -> "#5d6d7e";
            case Normal -> "#717d7e";
        };
    }

    private VBox criarCartaCriatura(CartaCriatura carta, Runnable onClick, Runnable onRightClick, boolean selecionada) {
        VBox cardBox = new VBox(3);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPrefSize(100, 140);
        cardBox.setPadding(new Insets(4));

        String cor = selecionada ? "#f5c842" : getCorElemento(carta.getElemento());
        cardBox.setStyle("-fx-background-color: " + cor + "; -fx-background-radius: 10; -fx-cursor: hand;");

        ImageView iv = getImagem(carta.getNome(), 60, 60);
        if (iv != null) cardBox.getChildren().add(iv);

        String posicaoTexto = carta.getPosicao() == Posicao.Ataque ? "⚔" : "🛡";
        Text txtNome = new Text(carta.getNome());
        txtNome.setStyle("-fx-fill: white; -fx-font-size: 10px; -fx-font-weight: bold;");
        Text txtPosicao = new Text(posicaoTexto + " HP:" + carta.getHp());
        txtPosicao.setStyle("-fx-fill: white; -fx-font-size: 9px;");
        Text txtStats = new Text("ATK:" + carta.getAtk() + " DEF:" + carta.getDef());
        txtStats.setStyle("-fx-fill: rgba(255,255,255,0.8); -fx-font-size: 9px;");

        cardBox.getChildren().addAll(txtNome, txtPosicao, txtStats);

        if (carta.getPosicao() == Posicao.Defesa) cardBox.setRotate(90);

        if (onClick != null) {
            cardBox.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) onClick.run();
                else if (e.getButton() == MouseButton.SECONDARY && onRightClick != null) onRightClick.run();
            });
        }

        return cardBox;
    }

    private VBox criarEspacoVazio() {
        VBox espaco = new VBox();
        espaco.setPrefSize(100, 140);
        espaco.setStyle("-fx-background-color: rgba(255,255,255,0.05); -fx-background-radius: 10; " +
                "-fx-border-color: rgba(255,255,255,0.15); -fx-border-radius: 10; -fx-border-style: dashed;");
        return espaco;
    }

    public void mostrar() {

        // === CAMPO ADVERSÁRIO ===
        Text txtAdversario = new Text("Campo do Adversário");
        txtAdversario.setStyle("-fx-fill: rgba(255,255,255,0.7); -fx-font-size: 14px;");

        HBox criaturaAdversario = new HBox(8);
        criaturaAdversario.setAlignment(Pos.CENTER);
        CartaCriatura[] espacosPC = controller.getJogo().getJogador(1).getCampo().getEspacosCriatura();
        for (int i = 0; i < 5; i++) {
            final int indexAlvo = i;
            if (espacosPC[i] != null) {
                VBox card = criarCartaCriatura(espacosPC[i], () -> {
                    if (cartaSelecionada != -1 && !jaAtacou) {
                        boolean atacou = controller.atacar(cartaSelecionada, indexAlvo);
                        if (atacou) {
                            cartaSelecionada = -1;
                            jaAtacou = true;
                        } else {
                            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                                    javafx.scene.control.Alert.AlertType.WARNING);
                            alert.setTitle("Aviso");
                            alert.setHeaderText(null);
                            alert.setContentText("Não podes atacar com uma carta em modo de defesa!");
                            alert.showAndWait();
                        }
                        atualizar();
                    }
                }, null, false);
                criaturaAdversario.getChildren().add(card);
            } else {
                criaturaAdversario.getChildren().add(criarEspacoVazio());
            }
        }

        // Espaços especiais adversário
        VBox especiaisAdversario = new VBox(8);
        especiaisAdversario.setAlignment(Pos.CENTER);
        CartaEspecial[] especiaisPC = controller.getJogo().getJogador(1).getCampo().getEspacosEspecial();
        for (int i = 0; i < 2; i++) {
            if (especiaisPC[i] != null) {
                Button espaco = new Button(especiaisPC[i].getNome());
                espaco.setPrefSize(80, 60);
                espaco.getStyleClass().add("card-especial");
                especiaisAdversario.getChildren().add(espaco);
            } else {
                Button espaco = new Button("[ESP]");
                espaco.setPrefSize(80, 60);
                espaco.getStyleClass().add("espaco-carta");
                especiaisAdversario.getChildren().add(espaco);
            }
        }

        HBox campoAdversario = new HBox(10);
        campoAdversario.setAlignment(Pos.CENTER);
        campoAdversario.getStyleClass().add("campo-adversario");
        campoAdversario.getChildren().addAll(criaturaAdversario, especiaisAdversario);

        // === CAMPO JOGADOR ===
        Text txtJogador = new Text("O Teu Campo");
        txtJogador.setStyle("-fx-fill: rgba(255,255,255,0.7); -fx-font-size: 14px;");

        HBox criaturaJogador = new HBox(8);
        criaturaJogador.setAlignment(Pos.CENTER);
        CartaCriatura[] espacos = controller.getJogo().getJogador(0).getCampo().getEspacosCriatura();
        for (int i = 0; i < 5; i++) {
            final int indexCarta = i;
            if (espacos[i] != null) {
                VBox card = criarCartaCriatura(espacos[i], () -> {
                    cartaSelecionada = indexCarta;
                    atualizar();
                }, () -> {
                    espacos[indexCarta].mudarPosicao();
                    atualizar();
                }, cartaSelecionada == i);
                criaturaJogador.getChildren().add(card);
            } else {
                criaturaJogador.getChildren().add(criarEspacoVazio());
            }
        }

        // Espaços especiais jogador
        VBox especiaisJogador = new VBox(8);
        especiaisJogador.setAlignment(Pos.CENTER);
        CartaEspecial[] especiaisJog = controller.getJogo().getJogador(0).getCampo().getEspacosEspecial();
        for (int i = 0; i < 2; i++) {
            final int indexEspecial = i;
            if (especiaisJog[i] != null) {
                Button espaco = new Button(especiaisJog[i].getNome());
                espaco.setPrefSize(80, 60);
                espaco.getStyleClass().add("card-especial");
                espaco.setOnAction(e -> new EcraUsarEspecial(stage, controller, indexEspecial, especiaisJog[indexEspecial]).mostrar());
                especiaisJogador.getChildren().add(espaco);
            } else {
                Button espaco = new Button("[ESP]");
                espaco.setPrefSize(80, 60);
                espaco.getStyleClass().add("espaco-carta");
                especiaisJogador.getChildren().add(espaco);
            }
        }

        HBox campoJogador = new HBox(10);
        campoJogador.setAlignment(Pos.CENTER);
        campoJogador.getStyleClass().add("campo-jogador");
        campoJogador.getChildren().addAll(criaturaJogador, especiaisJogador);

        // === MÃO DO JOGADOR ===
        Text txtMao = new Text("Mão");
        txtMao.setStyle("-fx-fill: rgba(255,255,255,0.7); -fx-font-size: 14px;");

        HBox mao = new HBox(8);
        mao.setAlignment(Pos.CENTER);
        List<Carta> cartasMao = controller.getJogo().getJogador(0).getMao();
        for (int i = 0; i < cartasMao.size(); i++) {
            Carta carta = cartasMao.get(i);
            final int indexMao = i;

            VBox cardBox = new VBox(3);
            cardBox.setAlignment(Pos.CENTER);
            cardBox.setPrefSize(100, 140);
            cardBox.setPadding(new Insets(4));

            if (carta instanceof CartaEspecial) {
                cardBox.setStyle("-fx-background-color: #8e44ad; -fx-background-radius: 10; -fx-cursor: hand;");
                ImageView iv = getImagem(carta.getNome(), 60, 60);
                if (iv != null) {
                    cardBox.getChildren().add(iv);
                } else {
                    Text emoji = new Text(carta instanceof Pocao ? "🧪" : "👤");
                    emoji.setStyle("-fx-font-size: 30px;");
                    cardBox.getChildren().add(emoji);
                }
                Text txtNome = new Text(carta.getNome());
                txtNome.setStyle("-fx-fill: white; -fx-font-size: 10px; -fx-font-weight: bold;");
                Text txtTipo = new Text("[ESPECIAL]");
                txtTipo.setStyle("-fx-fill: rgba(255,255,255,0.8); -fx-font-size: 9px;");
                cardBox.getChildren().addAll(txtNome, txtTipo);
            } else {
                CartaCriatura criatura = (CartaCriatura) carta;
                cardBox.setStyle("-fx-background-color: " + getCorElemento(criatura.getElemento()) +
                        "; -fx-background-radius: 10; -fx-cursor: hand;");
                ImageView iv = getImagem(carta.getNome(), 60, 60);
                if (iv != null) cardBox.getChildren().add(iv);
                Text txtNome = new Text(carta.getNome());
                txtNome.setStyle("-fx-fill: white; -fx-font-size: 10px; -fx-font-weight: bold;");
                Text txtStats = new Text("HP:" + carta.getHp() + " ATK:" + criatura.getAtk());
                txtStats.setStyle("-fx-fill: rgba(255,255,255,0.8); -fx-font-size: 9px;");
                cardBox.getChildren().addAll(txtNome, txtStats);
            }

            cardBox.setOnMouseClicked(e -> {
                Campo campo = controller.getJogo().getJogador(0).getCampo();
                if (carta instanceof CartaEspecial) {
                    for (int j = 0; j < 2; j++) {
                        if (campo.getEspacosEspecial()[j] == null) {
                            controller.getJogo().getJogador(0).jogarEspecial(indexMao, j);
                            atualizar();
                            break;
                        }
                    }
                } else {
                    for (int j = 0; j < 5; j++) {
                        if (campo.getEspacosCriatura()[j] == null) {
                            controller.getJogo().getJogador(0).jogarCriatura(indexMao, j);
                            atualizar();
                            break;
                        }
                    }
                }
            });
            mao.getChildren().add(cardBox);
        }

        // === BOTÕES ===
        Button btnPassarTurno = new Button("Passar Turno");
        btnPassarTurno.getStyleClass().add("btn-passar-turno");
        btnPassarTurno.setOnAction(e -> {
            jaAtacou = false;
            controller.passarTurno();
        });

        Button btnDesistir = new Button("Desistir");
        btnDesistir.getStyleClass().add("btn-desistir");
        btnDesistir.setOnAction(e -> {
            Perfil.guardar();
            new Lobby(stage).mostrar();
        });

        HBox botoes = new HBox(20);
        botoes.setAlignment(Pos.CENTER);
        botoes.getChildren().addAll(btnPassarTurno, btnDesistir);

        // === BARALHOS À DIREITA ===
        int cartasBaralhoJogador = controller.getJogo().getJogador(0).getBaralho().getTamanho();
        int cartasBaralhoPC = controller.getJogo().getJogador(1).getBaralho().getTamanho();

        Button btnBaralhoPC = new Button("🃏\nPC\n" + cartasBaralhoPC);
        btnBaralhoPC.setPrefSize(70, 100);
        btnBaralhoPC.getStyleClass().add("espaco-carta");
        btnBaralhoPC.setMouseTransparent(true);

        Button btnBaralhoJogador = new Button("🃏\nJogador\n" + cartasBaralhoJogador);
        btnBaralhoJogador.setPrefSize(70, 100);
        btnBaralhoJogador.getStyleClass().add("espaco-carta");
        btnBaralhoJogador.setMouseTransparent(true);

        VBox baralhosLateral = new VBox(20);
        baralhosLateral.setAlignment(Pos.CENTER);
        baralhosLateral.setPadding(new Insets(10));
        baralhosLateral.getChildren().addAll(btnBaralhoPC, btnBaralhoJogador);

        // === CENTRO ===
        VBox centro = new VBox(12);
        centro.setAlignment(Pos.CENTER);
        centro.getChildren().addAll(txtAdversario, campoAdversario, txtJogador, campoJogador, txtMao, mao, botoes);
        HBox.setHgrow(centro, Priority.ALWAYS);

        // === LAYOUT PRINCIPAL ===
        HBox layoutPrincipal = new HBox(10);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(15));
        layoutPrincipal.getChildren().addAll(centro, baralhosLateral);

        Scene scene = new Scene(layoutPrincipal, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void atualizar() { mostrar(); }
}