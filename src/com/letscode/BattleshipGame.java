package com.letscode;

import com.letscode.enums.GameStatus;
import com.letscode.service.GameAction;
import com.letscode.utils.BattleshipBoard;

import java.util.Scanner;

public class BattleshipGame {

    public static final int AMOUNT_OF_BOARD_COORDINATES = 5;    // tabuleiro completo = 10
    public static final int BOARD_SIZE = AMOUNT_OF_BOARD_COORDINATES * 2 + 3;
    public static final int AMOUNT_OF_SHIPS = 3;    // quantidade desejada = 10

    public static void main(String[] args) {

        char[] lineIdentifiers = {'A','B','C','D','E','F','G','H','I','J'}; // ver possibilidade de excluir
        char[] columnIdentifiers = {'0','1','2','3','4','5','6','7','8','9'}; // ver possibilidade de excluir

        // Criação dos tabuleiros
        BattleshipBoard personPlayer = new BattleshipBoard();
        BattleshipBoard machinePlayer = new BattleshipBoard();

        personPlayer.showBoard("Humano");

        // Determinação das coordenadas dos navios
        char[][] personShips = GameAction.readShipsCoordinates(lineIdentifiers, columnIdentifiers);
        char[][] machineShips = GameAction.createRandomShipsCoordinates(lineIdentifiers, columnIdentifiers);

        // Posicionamento dos navios nos tabluleiros
        personPlayer.positionShips(personShips);
        machinePlayer.positionShips(machineShips);

        personPlayer.showBoard("Humano");
        machinePlayer.showBoard("Máquina");

        int personAmountOfWholeShips = AMOUNT_OF_SHIPS;
        int machineAmountOfWholeShips = AMOUNT_OF_SHIPS;

        // Jogando

        GameStatus status = GameStatus.JOGANDO;     // criar um enum para isso
        int round = 0;

        do {
            char lineCoordinate = ' ';
            char columnCoordinate = ' ';
            char[][] gameBoard = new char[0][0];
            String player = "";
            int amountOfShips = 0;
            String bombingResult;

            if (round % 2 == 0) {                                                // rodada jogador

                Scanner readCoordinates = new Scanner(System.in);

                System.out.print("\nDigite a LINHA onde deseja bombardear: ");
                lineCoordinate = readCoordinates.next().toUpperCase().charAt(0);

                System.out.print("Digite a COLUNA onde deseja bombardear: ");
                columnCoordinate = readCoordinates.next().charAt(0);

                player = "Jogador";
                gameBoard = machinePlayer.gameBoard;
                amountOfShips = machineAmountOfWholeShips;

            }

            if (round % 2 != 0) {                                                // rodada computador
                int numeroLinha = (int) (Math.random()*AMOUNT_OF_BOARD_COORDINATES);
                int numeroColuna = (int) (Math.random()*AMOUNT_OF_BOARD_COORDINATES);

                lineCoordinate = lineIdentifiers[numeroLinha];
                columnCoordinate = columnIdentifiers[numeroColuna];
                player = "Computador";
                gameBoard = personPlayer.gameBoard;
                amountOfShips = personAmountOfWholeShips;
            }

            bombingResult = GameAction.bombOpponent(gameBoard,player,lineCoordinate,columnCoordinate);

            switch (bombingResult) {
                case "accurate":
                    amountOfShips--;
                    round++;
                    break;
                case "missed":
                    round++;
                    break;
                case "repeated":
                    System.out.printf("Você ja bombardeou esse local, escolha outro");
                    break;
            }

            if(player == "Jogador"){
                machineAmountOfWholeShips = amountOfShips;
                machinePlayer.showBoard("Máquina");
            }else{
                personAmountOfWholeShips = amountOfShips;
                personPlayer.showBoard("Humano");
            }

            GameStatus roundResult = GameAction.updateGameStatus(amountOfShips,player,status);
            status = roundResult;


        } while(status == GameStatus.JOGANDO);

    }


}
