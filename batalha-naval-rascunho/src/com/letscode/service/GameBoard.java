package com.letscode.service;

import java.util.Arrays;

public class GameBoard {

    public static char[][] createGameBoard(char[] lineIdentifiers, char[] columnIdentifiers) {
        final int BOARD_SIZE = 11; // tabuleiro completo = 23
        int columnIdentifierIndex = 0;
        int lineIdentifierIndex = 0;
        char[][] gameBoard = new char[BOARD_SIZE][BOARD_SIZE];

        for(char[] line : gameBoard) {
            if (Arrays.asList(gameBoard).indexOf(line) % 2 == 0) {  // preenchimento das divisorias
                Arrays.fill(line,'-');
            } else if (Arrays.asList(gameBoard).indexOf(line) == 1) {   // preenchimento da linha de cabeçalho
                for (int i = 0; i < line.length; i++) {
                    if (i == 1) {
                        line[i] = ' ';
                    } else {
                        line[i] = (i % 2 == 0) ? '|' : columnIdentifiers[columnIdentifierIndex++];
                    }
                }
            } else {    // preenchimento das linhas do campo de jogo
                for (int i = 0; i < line.length; i++) {
                    if (i == 1) {
                        line[i] = lineIdentifiers[lineIdentifierIndex];
                        lineIdentifierIndex++;
                    } else {
                        line[i] = i % 2 == 0 ? '|' : ' ';
                    }
                }
            }
        }

        return gameBoard;
    }

}
