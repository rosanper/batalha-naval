package com.letscode.service;

import com.letscode.BattleshipGame;
import com.letscode.enums.GameStatus;
import com.letscode.enums.Player;
import com.letscode.utils.BattleshipBoard;

import java.util.Arrays;
import java.util.Scanner;

public class GameAction {

    public static void playGame(BattleshipBoard humanPlayer, BattleshipBoard machinePlayer){

        System.out.println("\nO jogo vai começar:");

        GameStatus status = GameStatus.JOGANDO;
        Player currentPlayer = Player.HUMANO;
        int humanAmountOfWholeShips = BattleshipGame.AMOUNT_OF_SHIPS;
        int machineAmountOfWholeShips = BattleshipGame.AMOUNT_OF_SHIPS;

        do {
            if (currentPlayer == Player.HUMANO) {
                humanPlayer.showBoard(Player.HUMANO);
//                machinePlayer.showBoardWithoutShips(Player.MAQUINA);
                machinePlayer.showBoard(Player.MAQUINA); // Para fins de teste
            }

            // Determinação das variáveis da rodada
            int amountOfOpponentShips = setAmountOfOpponentShips(currentPlayer, humanAmountOfWholeShips, machineAmountOfWholeShips);
            char[][] opponentGameBoard = setOpponentGameBoard(currentPlayer, humanPlayer.gameBoard, machinePlayer.gameBoard);
            char[] bombCoordinates;

            // Bombardeio
            String bombingResult = "";
            do {
                bombCoordinates = setBombCoordinates(currentPlayer);
                bombingResult = bombOpponent(currentPlayer, bombCoordinates, opponentGameBoard);
            } while (bombingResult == "repeated");

            // Atualização das variáveis da rodada
            amountOfOpponentShips = updateBombingResult(currentPlayer, bombingResult, amountOfOpponentShips);

            if (currentPlayer == Player.HUMANO) machineAmountOfWholeShips = amountOfOpponentShips;
            if (currentPlayer == Player.MAQUINA) humanAmountOfWholeShips = amountOfOpponentShips;

            status = updateGameStatus(amountOfOpponentShips, currentPlayer, status);
            currentPlayer = updateCurrentPlayer(currentPlayer);

            // Última rodada
            if(status == GameStatus.JOGO_FINALIZADO){
                machinePlayer.showBoardWithoutShips(Player.MAQUINA);
                humanPlayer.showBoard(Player.HUMANO);
            }

        } while(status == GameStatus.JOGANDO);

    }

    private static char[] setBombCoordinates(Player player) {
        char[] coordinates = new char[2];

        if (player == Player.HUMANO) {
            Scanner readCoordinates = new Scanner(System.in);

            do {
                System.out.print("\nDigite a LINHA onde deseja bombardear: ");
                coordinates[0] = readCoordinates.next().toUpperCase().charAt(0);

                if (Arrays.binarySearch(BattleshipGame.LINE_IDENTIFIERS, coordinates[0]) < 0) {
                    System.out.println("** ATENÇÃO: Esta linha não existe, tente novamente **");
                }
            } while (Arrays.binarySearch(BattleshipGame.LINE_IDENTIFIERS, coordinates[0]) < 0);

            do {
                System.out.print("Digite a COLUNA onde deseja bombardear: ");
                coordinates[1] = readCoordinates.next().charAt(0);

                if (Arrays.binarySearch(BattleshipGame.COLUMN_IDENTIFIERS, coordinates[1]) < 0) {
                    System.out.println("** ATENÇÃO: Esta coluna não existe, tente novamente **\n");
                }
            } while (Arrays.binarySearch(BattleshipGame.COLUMN_IDENTIFIERS, coordinates[1]) < 0);
        }

        if (player == Player.MAQUINA) {
            int randomLine = (int) (Math.random() * BattleshipGame.AMOUNT_OF_BOARD_COORDINATES);
            int randomColumn = (int) (Math.random() * BattleshipGame.AMOUNT_OF_BOARD_COORDINATES);

            coordinates[0] = BattleshipGame.LINE_IDENTIFIERS[randomLine];
            coordinates[1] = BattleshipGame.COLUMN_IDENTIFIERS[randomColumn];
        }

        return coordinates;
    }

    private static char[][] setOpponentGameBoard(Player player, char[][] humanGameBoard, char[][] machineGameBoard) {
        char[][] opponentGameBoard = new char[0][0];

        if (player == Player.HUMANO) opponentGameBoard = machineGameBoard;
        if (player == Player.MAQUINA) opponentGameBoard = humanGameBoard;

        return opponentGameBoard;
    }

    private static int setAmountOfOpponentShips(Player player, int humanAmountOfWholeShips, int machineAmountOfWholeShips) {
        int amountOfOpponentShips = 0;

        if (player == Player.HUMANO) amountOfOpponentShips = machineAmountOfWholeShips;
        if (player == Player.MAQUINA) amountOfOpponentShips = humanAmountOfWholeShips;

        return amountOfOpponentShips;
    }

    private static String bombOpponent(Player player, char[] bombCoordinates, char[][] opponentGameBoard) {
        String bombingResult = "";
        char lineCoordinate = bombCoordinates[0];
        char columnCoordinate = bombCoordinates[1];

        for (int i = 0; i < opponentGameBoard.length; i++) {
            if (opponentGameBoard[i][1] == lineCoordinate) {
                for (int j = 0; j < opponentGameBoard[i].length; j++) {
                    if (opponentGameBoard[1][j] == columnCoordinate) {
                        if (opponentGameBoard[i][j] == 'N') {
                            opponentGameBoard[i][j] = '*';
                            bombingResult = "accurate";

                        } else if (opponentGameBoard[i][j] == ' ') {
                            opponentGameBoard[i][j] = '-';
                            bombingResult = "missed";

                        } else {
                            bombingResult = "repeated";
                            if(player == Player.HUMANO) {
                                System.out.println("\n** ATENÇÃO: Você já bombardeou este local, escolha outro **");
                            }
                        }
                    }
                }
            }
        }
        return bombingResult;
    }

    private static int updateBombingResult(Player player, String bombingResult, int amountOfOpponentShips) {
        switch (bombingResult) {
            case "accurate":
                amountOfOpponentShips--;
                System.out.printf("\nRodada %s: ",player);
                System.out.println("Um navio inimigo foi atingido!");
                break;
            case "missed":
                System.out.printf("\nRodada %s: ",player);
                System.out.println("O tiro atingiu a água!");
                break;
            case "repeated":
                break;
        }

        return amountOfOpponentShips;
    }

    private static GameStatus updateGameStatus(int remainingShips, Player player, GameStatus gameStatus) {
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

    private static Player updateCurrentPlayer(Player player) {
        Player currentPlayer = player;

        if (player == Player.HUMANO) currentPlayer = Player.MAQUINA;
        if (player == Player.MAQUINA) currentPlayer = Player.HUMANO;

        return currentPlayer;
    }

}
