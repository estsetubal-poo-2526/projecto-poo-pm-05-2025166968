# Card Arena 🃏

Jogo de cartas estratégico por turnos desenvolvido em Java com JavaFX.

## Autores
- Tiago Vieira — Nº 2025166968
- Tomás Silvestre — Nº 2025153010

**Unidade Curricular:** Programação Orientada a Objetos 2025/26  
**Instituição:** Escola Superior de Tecnologia de Setúbal — IPS

---

## Descrição
Card Arena é um jogo de cartas turn-based para 1 jogador contra o PC. O jogador constrói baralhos com criaturas de diferentes elementos e enfrenta o adversário numa arena digital. O objetivo é eliminar todas as cartas do adversário através de combates táticos baseados em atributos e vantagens elementais.

---

## Requisitos
- Java 21
- JavaFX 21
- Maven

---

## Como executar

### 1. Clonar o repositório
```bash
git clone https://github.com/estsetubal-poo-2526/projecto-poo-pm-05-2025166968.git
cd projecto-poo-pm-05-2025166968
```

### 2. Configurar JavaFX
Descarrega o JavaFX 21 SDK em https://gluonhq.com/products/javafx/ e extrai para uma pasta local.

### 3. Executar no IntelliJ
- Abre o projeto no IntelliJ IDEA
- Vai a **Run → Edit Configurations**
- VM options: --module-path "C:\Users\Tiago\Documents\facul\POOproj\JDK\openjfx-21.0.11_windows-x64_bin-sdk\javafx-sdk-21.0.11\lib" --add-modules javafx.controls,javafx.fxml
- Corre a classe `Main.java`

---

## Funcionalidades
- 🃏 Sistema de cartas com 7 elementos (Fogo, Água, Erva, Elétrico, Gelo, Voador, Normal)
- ⚔️ Sistema de batalha com vantagens/desvantagens elementais
- 🛡️ Modo de ataque e defesa para as criaturas
- 💊 Cartas especiais (Poções e Treinadores)
- 🏪 Loja com 3 tipos de packs
- 📦 Sistema de baralhos personalizados (máximo 4 baralhos)
- 💰 Sistema de moedas ganhas por vitórias
- 💾 Persistência de dados entre sessões

---

## Estrutura do Projeto
src/

├── main/

│   ├── java/org/example/

│   │   ├── controller/    # JogoController

│   │   ├── model/         # Lógica do jogo

│   │   └── view/          # Interface JavaFX

│   └── resources/

│       ├── images/        # Imagens das cartas

│       └── styles/        # CSS

└── test/

└── java/org/example/model/  # Testes unitários JUnit 5

---

## Testes
Para correr os testes unitários:
```bash
mvn test
```