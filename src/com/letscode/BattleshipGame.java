package com.letscode;

import com.letscode.enums.GameStatus;
import com.letscode.enums.Player;
import com.letscode.service.GameAction;
import com.letscode.utils.BattleshipBoard;

import java.util.Scanner;

public class BattleshipGame {

    public static final int AMOUNT_OF_BOARD_COORDINATES = 5;    // tabuleiro completo = 10
    public static final int BOARD_SIZE = AMOUNT_OF_BOARD_COORDINATES * 2 + 3;
    public static final int AMOUNT_OF_SHIPS = 3;    // quantidade desejada = 10
    public static final char[] LINE_IDENTIFIERS = {'A','B','C','D','E','F','G','H','I','J'};
    public static final char[] COLUMN_IDENTIFIERS = {'0','1','2','3','4','5','6','7','8','9'};

    public static void main(String[] args) {

        // Criação dos tabuleiros
        BattleshipBoard personPlayer = new BattleshipBoard();
        BattleshipBoard machinePlayer = new BattleshipBoard();

        personPlayer.showBoard(Player.HUMANO);

        // Determinação das coordenadas dos navios
        char[][] personShips = GameAction.readShipsCoordinates();
        char[][] machineShips = GameAction.createRandomShipsCoordinates();

        // Posicionamento dos navios nos tabluleiros
        personPlayer.positionShips(personShips);
        machinePlayer.positionShips(machineShips);

        personPlayer.showBoard(Player.HUMANO);
        machinePlayer.showBoard(Player.MAQUINA);    // Não exibir

        int personAmountOfWholeShips = AMOUNT_OF_SHIPS;
        int machineAmountOfWholeShips = AMOUNT_OF_SHIPS;

        // Jogando

        GameStatus status = GameStatus.JOGANDO;
        int round = 0;

        do {
            char bombLineCoordinate = ' ';
            char bombColumnCoordinate = ' ';
            char[][] opponentGameBoard = new char[0][0];
            Player currentPlayer = Player.NULO;
            String bombingResult;
            int amountOfOpponentShips = 0;

            if (round % 2 == 0) {                                                // rodada jogador

                Scanner readCoordinates = new Scanner(System.in);

                System.out.print("\nDigite a LINHA onde deseja bombardear: ");
                bombLineCoordinate = readCoordinates.next().toUpperCase().charAt(0);

                System.out.print("Digite a COLUNA onde deseja bombardear: ");
                bombColumnCoordinate = readCoordinates.next().charAt(0);

                currentPlayer = Player.HUMANO;
                opponentGameBoard = machinePlayer.gameBoard;
                amountOfOpponentShips = machineAmountOfWholeShips;

            }

            if (round % 2 != 0) {                                                // rodada computador
                int randomLine = (int) (Math.random() * AMOUNT_OF_BOARD_COORDINATES);
                int randomColumn = (int) (Math.random() * AMOUNT_OF_BOARD_COORDINATES);

                bombLineCoordinate = LINE_IDENTIFIERS[randomLine];
                bombColumnCoordinate = COLUMN_IDENTIFIERS[randomColumn];
                currentPlayer = Player.MAQUINA;
                opponentGameBoard = personPlayer.gameBoard;
                amountOfOpponentShips = personAmountOfWholeShips;
            }

            bombingResult = GameAction.bombOpponent(opponentGameBoard, currentPlayer, bombLineCoordinate, bombColumnCoordinate);

            switch (bombingResult) {
                case "accurate":
                    amountOfOpponentShips--;
                    round++;
                    break;
                case "missed":
                    round++;
                    break;
                case "repeated":
                    System.out.printf("Você ja bombardeou esse local, escolha outro");
                    break;
            }

            if(currentPlayer == Player.HUMANO){
                machineAmountOfWholeShips = amountOfOpponentShips;
                GameAction.reultadoDoBombardeio(currentPlayer,bombingResult);
                machinePlayer.showBoardWithoutShips(Player.MAQUINA);    // Não exibir
            }else{
                personAmountOfWholeShips = amountOfOpponentShips;
                GameAction.reultadoDoBombardeio(currentPlayer,bombingResult);
                personPlayer.showBoard(Player.HUMANO);
            }

            GameStatus roundResult = GameAction.updateGameStatus(amountOfOpponentShips, currentPlayer, status);
            status = roundResult;


        } while(status == GameStatus.JOGANDO);

    }


}
