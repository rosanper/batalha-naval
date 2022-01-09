package com.letscode;

import com.letscode.service.GameAction;
import com.letscode.utils.BattleshipBoard;

import java.util.Scanner;

public class BattleshipGame {

    public static final int AMOUNT_OF_BOARD_COORDINATES = 5;    // tabuleiro completo = 10
    public static final int BOARD_SIZE = AMOUNT_OF_BOARD_COORDINATES * 2 + 3;
    public static final int AMOUNT_OF_SHIPS = 3;    // quantidade desejada = 10

    public static void main(String[] args) {

        char[] lineIdentifiers = {'A','B','C','D','E','F','G','H','I','J'}; // ver possibilidade de excluir
        char[] columnIdentifiers = {'0','1','2','3','4','5','6','7','8','9'}; // ver possibilidade de excluir

        // Criação dos tabuleiros
        BattleshipBoard personPlayer = new BattleshipBoard();
        BattleshipBoard machinePlayer = new BattleshipBoard();

        personPlayer.showBoard("Humano");

        // Determinação das coordenadas dos navios
        char[][] personShips = GameAction.readShipsCoordinates(lineIdentifiers, columnIdentifiers);
        char[][] machineShips = GameAction.createRandomShipsCoordinates(lineIdentifiers, columnIdentifiers);

        // Posicionamento dos navios nos tabluleiros
        personPlayer.positionShips(personShips);
        machinePlayer.positionShips(machineShips);

        personPlayer.showBoard("Humano");
        machinePlayer.showBoard("Máquina");

        int personAmountOfWholeShips = AMOUNT_OF_SHIPS;
        int machineAmountOfWholeShips = AMOUNT_OF_SHIPS;

        // Jogando

        String status = "jogando";     // criar um enum para isso
        int round = 0;

        do {

            if (round % 2 == 0) {                             // rodada jogador

                Scanner readCoordinates = new Scanner(System.in);

                System.out.print("\nDigite a LINHA onde deseja bombardear: ");
                char lineCoordinate = readCoordinates.next().toUpperCase().charAt(0);

                System.out.print("Digite a COLUNA onde deseja bombardear: ");
                char columnCoordinate = readCoordinates.next().charAt(0);

                String personBombingResult = GameAction.bombOpponent(machinePlayer.gameBoard,"Jogador", lineCoordinate, columnCoordinate);

                switch (personBombingResult) {
                    case "accurate":
                        machineAmountOfWholeShips--;
                        round++;
                        break;
                    case "missed":
                        round++;
                        break;
                    case "repeated":
                        System.out.printf("Você já bombardeou este local, escolha outro");
                        break;
                }

                machinePlayer.showBoard("Máquina");

                String roundResult = GameAction.updateGameStatus(machineAmountOfWholeShips,"Jogador", status);
                status = roundResult;
            }

            if (round % 2 != 0) {   // rodada computador
                int numeroLinha = (int) (Math.random()*AMOUNT_OF_BOARD_COORDINATES);
                int numeroColuna = (int) (Math.random()*AMOUNT_OF_BOARD_COORDINATES);

                char lineCoordinate = lineIdentifiers[numeroLinha];
                char columnCoordinate = columnIdentifiers[numeroColuna];

                String machineBombingResult = GameAction.bombOpponent(personPlayer.gameBoard,"Computador", lineCoordinate,columnCoordinate);

                switch (machineBombingResult) {
                    case "accurate":
                        personAmountOfWholeShips--;
                        round++;
                        break;
                    case "missed":
                        round++;
                        break;
                    case "repeated":
                        System.out.printf("Você ja bombardeou esse local, escolha outro");
                        break;
                }

                personPlayer.showBoard("Humano");

                String roundResult = GameAction.updateGameStatus(personAmountOfWholeShips,"Computador", status);
                status = roundResult;
            }
        } while(status == "jogando");

    }

    // funçoes criadas

    private static boolean hasCharacter(char[] array, char charactere){   //utilizar para validar as entradas (ainda nao utilizei)
        boolean result = false;
        for(int i=0;i<array.length;i++){
            if(array[i] == charactere){
                result = true;
                return result;
            }
        }
        return result;
    }

    private static int gerarNumeroAleatório(char[] array){      // caso queira usar para subsituir as partes onde tem que gerar um numero aleatorio
        int num = (int) (Math.random()*array.length);
        return num;
    }

}
