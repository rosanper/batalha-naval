package com.letscode;

import java.util.Arrays;
import java.util.Scanner;

public class batalhaNaval {

    public static void main(String[] args) {

        char[] lineIdentifiers = {'A','B','C','D','E','F','G','H','I','J'};
        char[] columnIdentifiers = {'0','1','2','3','4','5','6','7','8','9'};

        char[][] personsBoard = createGameBoard(lineIdentifiers, columnIdentifiers);
        char[][] machinesBoard = createGameBoard(lineIdentifiers, columnIdentifiers);

        exibirTabuleiro(personsBoard, "Jogador");

        // Posicionando navios do jogador

        char[][] naviosJogador = lerPosicoesNavios();
        int numeroNaviosJogador = posicionarNavios(personsBoard, naviosJogador);

        exibirTabuleiro(personsBoard, "Jogador");

        // Posicionando navios do computador

        char[][] naviosComputador = criarPosicoesAleatorias(lineIdentifiers, columnIdentifiers);
        int numeroNaviosComputador = posicionarNavios(machinesBoard, naviosComputador);

        exibirTabuleiro(machinesBoard, "Computador");

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

                int resultadoBombardeioJogador = bombardear(machinesBoard,"Jogador",
                        posicaoLinha,posicaoColuna);
                switch (resultadoBombardeioJogador){
                    case 1:
                        numeroNaviosComputador--;
                        rodada++;
                        break;
                    case 2:
                        rodada++;
                        break;
                    case 3:
                        System.out.printf("Você ja bombardeou esse local, escolha outro");
                        break;
                }

                exibirTabuleiro(machinesBoard, "Computador");

                String resultadoRodada = finalizarJogo(numeroNaviosComputador,"Jogador",status);
                status = resultadoRodada;
            }

            if(rodada%2 != 0){   // rodada computador
                int numeroLinha = (int) (Math.random()*4);
                int numeroColuna = (int) (Math.random()*4);

                char posicaoLinha = columnIdentifiers[numeroLinha];
                char posicaoColuna = lineIdentifiers[numeroColuna];

                int resultadoBombardeioComputador = bombardear(personsBoard,"Computador",
                        posicaoLinha,posicaoColuna);
                switch (resultadoBombardeioComputador){
                    case 1:
                        numeroNaviosJogador--;
                        rodada++;
                        break;
                    case 2:
                        rodada++;
                        break;
                    case 3:
                        System.out.printf("Você ja bombardeou esse local, escolha outro");
                        break;
                }

                exibirTabuleiro(personsBoard, "Jogador");

                String resultadoRodada = finalizarJogo(numeroNaviosJogador,"Computador",status);
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

    // fim das funcoes criadas

    private static int posicionarNavios(char[][] tabuleiro, char[][] posicaoNavios) {
        int numeroNavios = 0;

        while(numeroNavios < posicaoNavios.length){

            char posicaoLinha = posicaoNavios[numeroNavios][0];
            char posicaoColuna = posicaoNavios[numeroNavios][1];

            for (int i = 0; i < tabuleiro.length; i++){
                if(tabuleiro[i][1] == posicaoLinha){
                    for (int j = 0; j < tabuleiro[i].length; j++){
                        if(tabuleiro[1][j] == posicaoColuna){
                            if (tabuleiro[i][j] ==' '){
                                tabuleiro[i][j] = 'N';
                                numeroNavios++;
                            }
                        }
                    }
                }
            }
        }

        return numeroNavios;
    }

    private static void exibirTabuleiro(char[][] tabuleiro, String player) {
        System.out.printf("\nTabuleiro %s", player);
        for(int i = 0; i < tabuleiro.length; i++){
            System.out.printf("\n");
            for (int j = 0; j < tabuleiro[i].length; j++){
                System.out.print(tabuleiro[i][j]);
            }
        }
    }

    private static char[][] criarPosicoesAleatorias(char[] lineIdentifiers, char[] columnIdentifiers) {
        char[][] naviosComputador = new char[3][2];
        int numeroLinha;
        int numeroColuna;

        for (int i = 0; i < naviosComputador.length; i++) {
            numeroLinha = (int) (Math.random()*4);
            naviosComputador[i][0] = columnIdentifiers[numeroLinha];

            numeroColuna = (int) (Math.random()*4);
            naviosComputador[i][1] = lineIdentifiers[numeroColuna];
        }

        return naviosComputador;
    }

    private static char[][] lerPosicoesNavios() {
        char[][] naviosJogador = new char[3][2];

        Scanner lerPosicoes = new Scanner(System.in);

        for (int i = 0; i < naviosJogador.length; i++) {
            System.out.print("\nDigite a LINHA onde deseja colocar o navio: ");
            naviosJogador[i][0] = lerPosicoes.next().charAt(0);

            System.out.print("Digite a COLUNA onde deseja colocar o navio: ");
            naviosJogador[i][1] = lerPosicoes.next().toUpperCase().charAt(0);
        }

        return naviosJogador;

        // Fazer validacao se ja existe navio na posicao escolhida
    }

    private static char[][] createGameBoard(char[] lineIdentifiers, char[] columnIdentifiers) {
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
