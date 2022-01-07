package com.letscode.service;

import com.letscode.batalhaNaval;

import java.util.Arrays;
import java.util.Scanner;

public class GameAction {

    public static char[][] readShipsCoordinates(char[] lineIdentifiers, char[] columnIdentifiers) {
        int positionedShips = 0;
        char[][] shipsCoordinates = new char[batalhaNaval.AMOUNT_OF_SHIPS][2];

        Scanner readCoordinates = new Scanner(System.in);

        do {
            do {
                System.out.printf("\nDigite a LINHA onde deseja colocar o navio #%d: ", positionedShips + 1);
                shipsCoordinates[positionedShips][0] = readCoordinates.next().toUpperCase().charAt(0);

                if (Arrays.binarySearch(lineIdentifiers, shipsCoordinates[positionedShips][0]) < 0) {
                    System.out.println("** ATENÇÃO: Esta linha não existe, tente novamente **");
                }
            } while (Arrays.binarySearch(lineIdentifiers, shipsCoordinates[positionedShips][0]) < 0);

            do {
                System.out.printf("Digite a COLUNA onde deseja colocar o navio #%d: ", positionedShips + 1);
                shipsCoordinates[positionedShips][1] = readCoordinates.next().charAt(0);

                if (Arrays.binarySearch(columnIdentifiers, shipsCoordinates[positionedShips][1]) < 0) {
                    System.out.println("** ATENÇÃO: Esta coluna não existe, tente novamente **\n");
                }
            } while (Arrays.binarySearch(columnIdentifiers, shipsCoordinates[positionedShips][1]) < 0);

            positionedShips++;
//            if (positionedShips == 0) {
//                positionedShips++;
//            } else {
//                // Fazer validacao se ja existe navio na posicao escolhida
//                for (int i = 0; i < positionedShips; i++) {
//                    if (shipsCoordinates[positionedShips][0] == shipsCoordinates[i][0] && shipsCoordinates[positionedShips][1] == shipsCoordinates[i][1]) {
//                        System.out.println("Já existe um navio nessas coordenadas, favor escolher novamente.");
//                        break;
//                    }
//                    if (i == positionedShips - 1) {
//                        positionedShips++;
//                    }
//                }
//
//            }
        } while (positionedShips < batalhaNaval.AMOUNT_OF_SHIPS);

        return shipsCoordinates;
    }

    public static int positionShips(char[][] gameBoard, char[][] shipsCoordinates) {
        int amountOfPositionedShips = 0;

        for (char[] shipPosition : shipsCoordinates ) {
            char lineCoordinate = shipPosition[0];
            char columnCoordinate = shipPosition[1];

            for (int i = 3; i < gameBoard.length; i += 2) {
                if(gameBoard[i][1] == lineCoordinate) {
                    for (int j = 3; j < gameBoard[i].length; j += 2) {
                        if(gameBoard[1][j] == columnCoordinate) {
                            if (gameBoard[i][j] == ' ') {
                                gameBoard[i][j] = 'N';
                                amountOfPositionedShips++;
                            }
                        }
                    }
                }
            }
        }

        return amountOfPositionedShips;
    }

}
