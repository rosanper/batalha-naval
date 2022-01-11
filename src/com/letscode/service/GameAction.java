package com.letscode.service;

import com.letscode.enums.GameStatus;
import com.letscode.enums.Player;

public class GameAction {

    public static String bombOpponent(char[][] gameBoard, Player player, char lineCoordinate, char columnCoordinate) {
        String bombingResult = "";

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
