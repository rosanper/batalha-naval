package com.letscode;

import com.letscode.enums.Player;
import com.letscode.service.GameAction;
import com.letscode.service.ShipsPlacement;
import com.letscode.utils.BattleshipBoard;

public class BattleshipGame {

    public static final int AMOUNT_OF_BOARD_COORDINATES = 5;    // tabuleiro completo = 10
    public static final int BOARD_SIZE = AMOUNT_OF_BOARD_COORDINATES * 2 + 3;
    public static final int AMOUNT_OF_SHIPS = 3;    // quantidade desejada = 10
    public static final char[] LINE_IDENTIFIERS = {'A','B','C','D','E','F','G','H','I','J'};
    public static final char[] COLUMN_IDENTIFIERS = {'0','1','2','3','4','5','6','7','8','9'};

    public static void main(String[] args) {

        // Criação dos tabuleiros
        BattleshipBoard humanPlayer = new BattleshipBoard();
        BattleshipBoard machinePlayer = new BattleshipBoard();

        humanPlayer.showBoard(Player.HUMANO);

        // Determinação das coordenadas dos navios
        char[][] humanShips = ShipsPlacement.readCoordinates();
        char[][] machineShips = ShipsPlacement.createRandomCoordinates();

        // Posicionamento dos navios nos tabluleiros
        humanPlayer.positionShips(humanShips);
        machinePlayer.positionShips(machineShips);

        humanPlayer.showBoard(Player.HUMANO);

        GameAction.playGame(humanPlayer, machinePlayer);
    }


}
