package com.letscode.service;

import com.letscode.batalhaNaval;

import java.util.Arrays;
import java.util.Scanner;

public class GameAction {

    public static char[][] readShipsCoordinates(char[] lineIdentifiers, char[] columnIdentifiers) {
        int createdShips = 0;
        char[][] shipsCoordinates = new char[batalhaNaval.AMOUNT_OF_SHIPS][2];

        Scanner readCoordinates = new Scanner(System.in);

        do {
            // usuario adiciona linha para navio - com validaçao
            do {
                System.out.printf("\nDigite a LINHA onde deseja colocar o navio #%d: ", createdShips + 1);
                shipsCoordinates[createdShips][0] = readCoordinates.next().toUpperCase().charAt(0);

                if (Arrays.binarySearch(lineIdentifiers, shipsCoordinates[createdShips][0]) < 0) {
                    System.out.println("** ATENÇÃO: Esta linha não existe, tente novamente **");
                }
            } while (Arrays.binarySearch(lineIdentifiers, shipsCoordinates[createdShips][0]) < 0);

            // usuario adiciona coluna para navio - com validaçao
            do {
                System.out.printf("Digite a COLUNA onde deseja colocar o navio #%d: ", createdShips + 1);
                shipsCoordinates[createdShips][1] = readCoordinates.next().charAt(0);

                if (Arrays.binarySearch(columnIdentifiers, shipsCoordinates[createdShips][1]) < 0) {
                    System.out.println("** ATENÇÃO: Esta coluna não existe, tente novamente **\n");
                }
            } while (Arrays.binarySearch(columnIdentifiers, shipsCoordinates[createdShips][1]) < 0);

            createdShips = validateCreatedShips(createdShips, shipsCoordinates);

        } while (createdShips < batalhaNaval.AMOUNT_OF_SHIPS);

        return shipsCoordinates;
    }

    public static char[][] createRandomShipsCoordinates(char[] lineIdentifiers, char[] columnIdentifiers) {
        int createdShips = 0;
        int lineCoordinate;
        int columnCoordinate;
        char[][] randomShipsCoordinates = new char[batalhaNaval.AMOUNT_OF_SHIPS][2];

        do {
            for (int i = 0; i < randomShipsCoordinates.length; i++) {
                lineCoordinate = (int) (Math.random() * batalhaNaval.AMOUNT_OF_BOARD_COORDINATES);
                randomShipsCoordinates[i][0] = lineIdentifiers[lineCoordinate];

                columnCoordinate = (int) (Math.random() * batalhaNaval.AMOUNT_OF_BOARD_COORDINATES);
                randomShipsCoordinates[i][1] = columnIdentifiers[columnCoordinate];
            }

            createdShips = validateCreatedShips(createdShips, randomShipsCoordinates);

        } while (createdShips < batalhaNaval.AMOUNT_OF_SHIPS);

        return randomShipsCoordinates;
    }

    private static int validateCreatedShips(int createdShips, char[][] randomShipsCoordinates) {
        if (createdShips == 0) {
            createdShips++;
        } else {
            boolean shipWasPositioned = false;

            for (int i = 0; i < createdShips; i++) {
                if (randomShipsCoordinates[createdShips][0] == randomShipsCoordinates[i][0] && randomShipsCoordinates[createdShips][1] == randomShipsCoordinates[i][1]) {
                    System.out.println("Já existe um navio nessas coordenadas, favor escolher novamente.");
                    break;
                }
                shipWasPositioned = true;
            }

            if (shipWasPositioned) createdShips++;
        }
        return createdShips;
    }


//    public static int positionShips(char[][] gameBoard, char[][] shipsCoordinates) {
//        int amountOfPositionedShips = 0;
//
//        for (char[] shipPosition : shipsCoordinates ) {
//            char lineCoordinate = shipPosition[0];
//            char columnCoordinate = shipPosition[1];
//
//            for (int i = 3; i < gameBoard.length; i += 2) {
//                if(gameBoard[i][1] == lineCoordinate) {
//                    for (int j = 3; j < gameBoard[i].length; j += 2) {
//                        if(gameBoard[1][j] == columnCoordinate) {
//                            if (gameBoard[i][j] == ' ') {
//                                gameBoard[i][j] = 'N';
//                                amountOfPositionedShips++;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        return amountOfPositionedShips;
//    }

}
