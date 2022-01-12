package com.letscode.service;

import com.letscode.BattleshipGame;

import java.util.Arrays;
import java.util.Scanner;

public class ShipsPlacement {

    public static char[][] readCoordinates() {
        int createdShips = 0;
        char[][] shipsCoordinates = new char[BattleshipGame.AMOUNT_OF_SHIPS][2];

        Scanner readCoordinates = new Scanner(System.in);

        do {
            // usuario adiciona linha para navio - com validaçao
            do {
                System.out.printf("\nDigite a LINHA onde deseja colocar o navio #%d: ", createdShips + 1);
                shipsCoordinates[createdShips][0] = readCoordinates.next().toUpperCase().charAt(0);

                if (Arrays.binarySearch(BattleshipGame.LINE_IDENTIFIERS, shipsCoordinates[createdShips][0]) < 0) {
                    System.out.println("** ATENÇÃO: Esta linha não existe, tente novamente **");
                }
            } while (Arrays.binarySearch(BattleshipGame.LINE_IDENTIFIERS, shipsCoordinates[createdShips][0]) < 0);

            // usuario adiciona coluna para navio - com validaçao
            do {
                System.out.printf("Digite a COLUNA onde deseja colocar o navio #%d: ", createdShips + 1);
                shipsCoordinates[createdShips][1] = readCoordinates.next().charAt(0);

                if (Arrays.binarySearch(BattleshipGame.COLUMN_IDENTIFIERS, shipsCoordinates[createdShips][1]) < 0) {
                    System.out.println("** ATENÇÃO: Esta coluna não existe, tente novamente **\n");
                }
            } while (Arrays.binarySearch(BattleshipGame.COLUMN_IDENTIFIERS, shipsCoordinates[createdShips][1]) < 0);

            createdShips = validateCreatedShips(createdShips, shipsCoordinates);

        } while (createdShips < BattleshipGame.AMOUNT_OF_SHIPS);

        return shipsCoordinates;
    }

    public static char[][] createRandomCoordinates() {
        int createdShips = 0;
        int lineCoordinate;
        int columnCoordinate;
        char[][] randomShipsCoordinates = new char[BattleshipGame.AMOUNT_OF_SHIPS][2];

        do {
            for (int i = 0; i < randomShipsCoordinates.length; i++) {
                lineCoordinate = (int) (Math.random() * BattleshipGame.AMOUNT_OF_BOARD_COORDINATES);
                randomShipsCoordinates[i][0] = BattleshipGame.LINE_IDENTIFIERS[lineCoordinate];

                columnCoordinate = (int) (Math.random() * BattleshipGame.AMOUNT_OF_BOARD_COORDINATES);
                randomShipsCoordinates[i][1] = BattleshipGame.COLUMN_IDENTIFIERS[columnCoordinate];
            }

            createdShips = validateCreatedShips(createdShips, randomShipsCoordinates);

        } while (createdShips < BattleshipGame.AMOUNT_OF_SHIPS);

        return randomShipsCoordinates;
    }

    private static int validateCreatedShips(int createdShips, char[][] shipsCoordinates) {
        if (createdShips == 0) {
            createdShips++;
        } else {
            boolean shipWasPositioned = false;

            for (int i = 0; i < createdShips; i++) {
                if (shipsCoordinates[createdShips][0] == shipsCoordinates[i][0] && shipsCoordinates[createdShips][1] == shipsCoordinates[i][1]) {
                    shipWasPositioned = false;
                    System.out.println("** ATENÇÃO: Já existe um navio nestas coordenadas, favor escolher novamente **");
                    break;
                }
                shipWasPositioned = true;
            }

            if (shipWasPositioned) createdShips++;
        }
        return createdShips;
    }

}
