package com.letscode.service;

import com.letscode.BattleshipGame;
import com.letscode.enums.GameStatus;
import com.letscode.enums.Player;
import com.letscode.utils.BattleshipBoard;

import java.util.Scanner;

public class GameAction {

    public static char[] setBombingCoordinates(int round) {
        char[] coordinates = new char[2];

        if (round % 2 == 0) {
            Scanner readCoordinates = new Scanner(System.in);

            System.out.print("\nDigite a LINHA onde deseja bombardear: ");
            coordinates[0] = readCoordinates.next().toUpperCase().charAt(0);

            System.out.print("Digite a COLUNA onde deseja bombardear: ");
            coordinates[1] = readCoordinates.next().charAt(0);
        }

        if (round % 2 != 0) {
            int randomLine = (int) (Math.random() * BattleshipGame.AMOUNT_OF_BOARD_COORDINATES);
            int randomColumn = (int) (Math.random() * BattleshipGame.AMOUNT_OF_BOARD_COORDINATES);

            coordinates[0] = BattleshipGame.LINE_IDENTIFIERS[randomLine];
            coordinates[1] = BattleshipGame.COLUMN_IDENTIFIERS[randomColumn];
        }

        return coordinates;
    }

    public static String bombOpponent(char[][] gameBoard, Player player, char[] bombCoordinates) {
        String bombingResult = "";
        char lineCoordinate = bombCoordinates[0];
        char columnCoordinate = bombCoordinates[1];

        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i][1] == lineCoordinate) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                    if (gameBoard[1][j] == columnCoordinate) {
                        if (gameBoard[i][j] == 'N') {
                            gameBoard[i][j] = '*';
                            bombingResult = "accurate";

                        } else if (gameBoard[i][j] == ' ') {
                            gameBoard[i][j] = '-';
                            bombingResult = "missed";

                        } else {
                            if(player == Player.HUMANO) {
                                bombingResult = "repeated";
                            }
                        }
                    }
                }
            }
        }
        return bombingResult;
    }

    public static void bombingResultMessage(Player player, String bombingResult) {
        switch (bombingResult) {
            case "accurate":
                System.out.printf("\nRodada %s: ",player);
                System.out.println("Um navio inimigo foi atingido!");
                break;
            case "missed":
                System.out.printf("\nRodada %s: ",player);
                System.out.println("O tiro atingiu a água!");
                break;
            case "repeated":
                System.out.println("\n** ATENÇÃO: Você já bombardeou esse local, escolha outro **");
                break;
        }
    }

    public static GameStatus updateGameStatus(int remainingShips, Player player, GameStatus gameStatus) {
        if (remainingShips == 0) {
            switch (player) {
                case HUMANO:
                    System.out.println("Parabéns, você venceu!!!");
                    break;
                case MAQUINA:
                    System.out.println("Visshh, você foi derrotado!");
                    break;
                default:
                    break;
            }
            gameStatus = GameStatus.JOGO_FINALIZADO;
        }
        return gameStatus;
    }

    public static void playGame(BattleshipBoard personPlayer, BattleshipBoard machinePlayer){
        int personAmountOfWholeShips = BattleshipGame.AMOUNT_OF_SHIPS;
        int machineAmountOfWholeShips = BattleshipGame.AMOUNT_OF_SHIPS;


        GameStatus status = GameStatus.JOGANDO;
        int round = 0;

        System.out.println("\nO jogo vai começar:");

        do {
            char[] bombCoordinates = new char[2];
            char[][] opponentGameBoard = new char[0][0];
            Player currentPlayer = Player.HUMANO;
            String bombingResult;
            int amountOfOpponentShips = 0;

            if (round % 2 == 0) {   // rodada jogador
                personPlayer.showBoard(Player.HUMANO);
                machinePlayer.showBoardWithoutShips(Player.MAQUINA);

                bombCoordinates = GameAction.setBombingCoordinates(round);

                currentPlayer = Player.HUMANO;
                opponentGameBoard = machinePlayer.gameBoard;
                amountOfOpponentShips = machineAmountOfWholeShips;
            }

            if (round % 2 != 0) {   // rodada computador
                bombCoordinates = GameAction.setBombingCoordinates(round);

                currentPlayer = Player.MAQUINA;
                opponentGameBoard = personPlayer.gameBoard;
                amountOfOpponentShips = personAmountOfWholeShips;
            }

            bombingResult = GameAction.bombOpponent(opponentGameBoard, currentPlayer, bombCoordinates);

            switch (bombingResult) {
                case "accurate":
                    amountOfOpponentShips--;
                    round++;
                    break;
                case "missed":
                    round++;
                    break;
                case "repeated":
                    break;
            }

            if (currentPlayer == Player.HUMANO) {
                machineAmountOfWholeShips = amountOfOpponentShips;
                GameAction.bombingResultMessage(currentPlayer,bombingResult);
            } else {
                personAmountOfWholeShips = amountOfOpponentShips;
                GameAction.bombingResultMessage(currentPlayer,bombingResult);
            }

            GameStatus roundResult = GameAction.updateGameStatus(amountOfOpponentShips, currentPlayer, status);
            status = roundResult;

            if(status == GameStatus.JOGO_FINALIZADO){
                machinePlayer.showBoardWithoutShips(Player.MAQUINA);
                personPlayer.showBoard(Player.HUMANO);
            }

        } while(status == GameStatus.JOGANDO);

    }
}
