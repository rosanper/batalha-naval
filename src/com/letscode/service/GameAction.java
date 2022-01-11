package com.letscode.service;

import com.letscode.BattleshipGame;
import com.letscode.enums.GameStatus;
import com.letscode.enums.Player;

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

}
