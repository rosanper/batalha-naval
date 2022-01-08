package com.letscode;

import com.letscode.service.GameAction;
import com.letscode.service.GameBoard;
import utils.BattleshipBoard;

import java.util.Scanner;

public class batalhaNaval {

    public static final int AMOUNT_OF_BOARD_COORDINATES = 5;    // tabuleiro completo = 10
    public static final int BOARD_SIZE = AMOUNT_OF_BOARD_COORDINATES * 2 + 3;
    public static final int AMOUNT_OF_SHIPS = 3;    // quantidade desejada = 10

    public static void main(String[] args) {


        char[] lineIdentifiers = {'A','B','C','D','E','F','G','H','I','J'};
        char[] columnIdentifiers = {'0','1','2','3','4','5','6','7','8','9'};

        // Criação dos tabuleiros
        char[][] personBoard = GameBoard.createGameBoard(lineIdentifiers, columnIdentifiers);
        char[][] machineBoard = GameBoard.createGameBoard(lineIdentifiers, columnIdentifiers);

        GameBoard.showBoard(personBoard, "Jogador");

        // Determinação das coordenadas dos navios
        char[][] personShips = GameAction.readShipsCoordinates(lineIdentifiers, columnIdentifiers);
        char[][] machineShips = GameAction.createRandomShipsCoordinates(lineIdentifiers, columnIdentifiers);

        BattleshipBoard personPlayer = new BattleshipBoard(personShips);
        BattleshipBoard machinePlayer = new BattleshipBoard(machineShips);

        personPlayer.showBoard("Humano");
        machinePlayer.showBoard("Máquina");


        // Posicionamento dos navios nos tabluleiros
        int personAmountOfWholeShips = GameAction.positionShips(personBoard, personShips);
        int machineAmountOfWholeShips = GameAction.positionShips(machineBoard, machineShips);

        GameBoard.showBoard(personBoard, "Jogador");
        GameBoard.showBoard(machineBoard, "Computador");

        // Jogando

        String status = "jogando";     // criar um enum para isso

        while(status == "jogando"){
            int rodada = 0;

            if(rodada%2 == 0){                             // rodada jogador

                Scanner lerPosicoes = new Scanner(System.in);

                System.out.print("\nDigite a LINHA onde deseja bombardear: ");
                char posicaoLinha = lerPosicoes.next().charAt(0);

                System.out.print("Digite a COLUNA onde deseja bombardear: ");
                char posicaoColuna = lerPosicoes.next().toUpperCase().charAt(0);

                int resultadoBombardeioJogador = bombardear(machineBoard,"Jogador",
                        posicaoLinha,posicaoColuna);
                switch (resultadoBombardeioJogador){
                    case 1:
                        machineAmountOfWholeShips--;
                        rodada++;
                        break;
                    case 2:
                        rodada++;
                        break;
                    case 3:
                        System.out.printf("Você ja bombardeou esse local, escolha outro");
                        break;
                }

                GameBoard.showBoard(machineBoard, "Computador");

                String resultadoRodada = finalizarJogo(machineAmountOfWholeShips,"Jogador",status);
                status = resultadoRodada;
            }

            if(rodada%2 != 0){   // rodada computador
                int numeroLinha = (int) (Math.random()*4);
                int numeroColuna = (int) (Math.random()*4);

                char posicaoLinha = columnIdentifiers[numeroLinha];
                char posicaoColuna = lineIdentifiers[numeroColuna];

                int resultadoBombardeioComputador = bombardear(personBoard,"Computador",
                        posicaoLinha,posicaoColuna);
                switch (resultadoBombardeioComputador){
                    case 1:
                        personAmountOfWholeShips--;
                        rodada++;
                        break;
                    case 2:
                        rodada++;
                        break;
                    case 3:
                        System.out.printf("Você ja bombardeou esse local, escolha outro");
                        break;
                }

                GameBoard.showBoard(personBoard, "Jogador");

                String resultadoRodada = finalizarJogo(personAmountOfWholeShips,"Computador",status);
                status = resultadoRodada;
            }
        }

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

    private static int bombardear(char[][] tabuleiro, String player,
                                 char posicaoLinha, char posicaoColuna){
        int result = 0;
        for (int i = 0; i < tabuleiro.length; i++){
            if (tabuleiro[i][1] == posicaoLinha){
                for (int j = 0; j < tabuleiro[i].length; j++){
                    if (tabuleiro[1][j] == posicaoColuna){
                        if (tabuleiro[i][j] == 'N') {
                            tabuleiro[i][j] = '*';
                            result = 1;

                        } else if (tabuleiro[i][j] == ' '){
                            tabuleiro[i][j] = '-';
                            result = 2;

                        } else {
                            if(player == "Jogador"){
                                result = 3;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private static String finalizarJogo(int numeroNavios, String player, String status){
        String resultado=status;

        if(numeroNavios == 0 && player == "Jogador"){
            System.out.printf("Parabéns, você venceu!!!");
            resultado = "jogo finalizado";
        }

        if(numeroNavios == 0 && player == "Computador"){
            System.out.printf("Visshh, você foi derrotado!");
            resultado = "jogo finalizado";
        }

        return resultado;
    }
}
