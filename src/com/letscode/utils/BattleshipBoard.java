package com.letscode.utils;

import com.letscode.BattleshipGame;
import com.letscode.enums.Player;

import java.util.Arrays;

public class BattleshipBoard {
    public char[][] gameBoard;

    public BattleshipBoard() {
        this.gameBoard = this.createGameBoard();
    }

    public char[][] createGameBoard() {
        char[][] board = new char[BattleshipGame.BOARD_SIZE][BattleshipGame.BOARD_SIZE];
        int columnIdentifierIndex = 0;
        int lineIdentifierIndex = 0;

        for(char[] line : board) {
            if (Arrays.asList(board).indexOf(line) % 2 == 0) {  // preenchimento das divisorias
                Arrays.fill(line,'-');
            } else if (Arrays.asList(board).indexOf(line) == 1) {   // preenchimento da linha de cabeçalho
                for (int i = 0; i < line.length; i++) {
                    if (i == 1) {
                        line[i] = ' ';
                    } else {
                        line[i] = (i % 2 == 0) ? '|' : BattleshipGame.COLUMN_IDENTIFIERS[columnIdentifierIndex++];
                    }
                }
            } else {    // preenchimento das linhas do campo de jogo
                for (int i = 0; i < line.length; i++) {
                    if (i == 1) {
                        line[i] = BattleshipGame.LINE_IDENTIFIERS[lineIdentifierIndex];
                        lineIdentifierIndex++;
                    } else {
                        line[i] = i % 2 == 0 ? '|' : ' ';
                    }
                }
            }
        }
        return board;
    }

    public int positionShips(char[][] shipsCoordinates) {
        int amountOfPositionedShips = 0;

        for (char[] shipPosition : shipsCoordinates ) {
            char lineCoordinate = shipPosition[0];
            char columnCoordinate = shipPosition[1];

            for (int i = 3; i < this.gameBoard.length; i += 2) {
                if(this.gameBoard[i][1] == lineCoordinate) {
                    for (int j = 3; j < this.gameBoard[i].length; j += 2) {
                        if(this.gameBoard[1][j] == columnCoordinate) {
                            if (this.gameBoard[i][j] == ' ') {
                                this.gameBoard[i][j] = 'N';
                                amountOfPositionedShips++;
                            }
                        }
                    }
                }
            }
        }

        return amountOfPositionedShips;
    }

    public void showBoard(Player player) {
        String currentPlayer = "";
        if(player == Player.HUMANO){
            currentPlayer = "Humano";
        }else if(player == Player.MAQUINA){
            currentPlayer = "Máquina";
        }

        System.out.printf("\nTabuleiro %s", currentPlayer);

        for(int i = 0; i < this.gameBoard.length; i++){
            System.out.printf("\n");
            for (int j = 0; j < this.gameBoard[i].length; j++){
                System.out.print(this.gameBoard[i][j]);
            }
        }
        System.out.printf("\n");
    }
}
