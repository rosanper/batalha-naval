package com.letscode;

import com.letscode.service.GameAction;
import com.letscode.service.GameBoard;
import utils.BattleshipBoard;

import java.util.Scanner;

public class batalhaNaval {

    public static final int AMOUNT_OF_BOARD_COORDINATES = 5;    // tabuleiro completo = 10
    public static final int BOARD_SIZE = AMOUNT_OF_BOARD_COORDINATES * 2 + 3;
    public static final int AMOUNT_OF_SHIPS = 3;    // quantidade desejada = 10

    public static void main(String[] args) {

        // Criação dos tabuleiros
        char[] lineIdentifiers = {'A','B','C','D','E','F','G','H','I','J'};
        char[] columnIdentifiers = {'0','1','2','3','4','5','6','7','8','9'};

        char[][] personBoard = GameBoard.createGameBoard(lineIdentifiers, columnIdentifiers);
        char[][] machineBoard = GameBoard.createGameBoard(lineIdentifiers, columnIdentifiers);
        // novo
        BattleshipBoard personPlayer = new BattleshipBoard();
        BattleshipBoard machinePlayer = new BattleshipBoard();

//        GameBoard.showBoard(personBoard, "Jogador");
        personPlayer.showBoard("Humano");

        // Determinação das coordenadas dos navios
        char[][] personShips = GameAction.readShipsCoordinates(lineIdentifiers, columnIdentifiers);
        char[][] machineShips = GameAction.createRandomShipsCoordinates(lineIdentifiers, columnIdentifiers);

        // Posicionamento dos navios nos tabluleiros
//        int personAmountOfWholeShips = GameAction.positionShips(personBoard, personShips);
//        int machineAmountOfWholeShips = GameAction.positionShips(machineBoard, machineShips);

//        GameBoard.showBoard(personBoard, "Jogador");
//        GameBoard.showBoard(machineBoard, "Computador");
        // novo
        personPlayer.positionShips(personShips);
        machinePlayer.positionShips(machineShips);

        personPlayer.showBoard("Humano");
        machinePlayer.showBoard("Máquina");

        int personAmountOfWholeShips = AMOUNT_OF_SHIPS;
        int machineAmountOfWholeShips = AMOUNT_OF_SHIPS;

        // Jogando

        String status = "jogando";     // criar um enum para isso

        do {
            int round = 0;

            if (round % 2 == 0) {                             // rodada jogador

                Scanner readCoordinates = new Scanner(System.in);

                System.out.print("\nDigite a LINHA onde deseja bombardear: ");
                char lineCoordinate = readCoordinates.next().toUpperCase().charAt(0);

                System.out.print("Digite a COLUNA onde deseja bombardear: ");
                char columnCoordinate = readCoordinates.next().charAt(0);

                String personBombingResult = GameAction.bombOpponent(machinePlayer.gameBoard,"Jogador", lineCoordinate, columnCoordinate);

                switch (personBombingResult) {
                    case "accurate":
                        machineAmountOfWholeShips--;
                        round++;
                        break;
                    case "missed":
                        round++;
                        break;
                    case "repeated":
                        System.out.printf("Você já bombardeou este local, escolha outro");
                        break;
                }

//                GameBoard.showBoard(machineBoard, "Computador");
                machinePlayer.showBoard("Máquina");

                String roundResult = finalizarJogo(machineAmountOfWholeShips,"Jogador", status);
                status = roundResult;
            }

            if (round % 2 != 0) {   // rodada computador
                int numeroLinha = (int) (Math.random()*4);
                int numeroColuna = (int) (Math.random()*4);

                char lineCoordinate = columnIdentifiers[numeroLinha];
                char columnCoordinate = lineIdentifiers[numeroColuna];

                String machineBombingResult = GameAction.bombOpponent(personPlayer.gameBoard,"Computador", lineCoordinate,columnCoordinate);

                switch (machineBombingResult) {
                    case "accurate":
                        personAmountOfWholeShips--;
                        round++;
                        break;
                    case "missed":
                        round++;
                        break;
                    case "repeated":
                        System.out.printf("Você ja bombardeou esse local, escolha outro");
                        break;
                }

//                GameBoard.showBoard(personBoard, "Jogador");
                personPlayer.showBoard("Humano");

                String roundResult = finalizarJogo(personAmountOfWholeShips,"Computador", status);
                status = roundResult;
            }
        } while(status == "jogando");

    }

    // funçoes criadas

    private static boolean hasCharacter(char[] array, char charactere){   //utilizar para validar as entradas (ainda nao utilizei)
        boolean result = false;
        for(int i=0;i<array.length;i++){
            if(array[i] == charactere){
                result = true;
                return result;
            }
        }
        return result;
    }

    private static int gerarNumeroAleatório(char[] array){      // caso queira usar para subsituir as partes onde tem que gerar um numero aleatorio
        int num = (int) (Math.random()*array.length);
        return num;
    }



    private static String finalizarJogo(int numeroNavios, String player, String status){
        String resultado=status;

        if(numeroNavios == 0 && player == "Jogador"){
            System.out.printf("Parabéns, você venceu!!!");
            resultado = "jogo finalizado";
        }

        if(numeroNavios == 0 && player == "Computador"){
            System.out.printf("Visshh, você foi derrotado!");
            resultado = "jogo finalizado";
        }

        return resultado;
    }
}
