package com.letscode;

import com.letscode.service.GameBoard;

import java.util.Scanner;

public class batalhaNaval {

    public static void main(String[] args) {

        char[] lineIdentifiers = {'A','B','C','D','E','F','G','H','I','J'};
        char[] columnIdentifiers = {'0','1','2','3','4','5','6','7','8','9'};

        char[][] personBoard = GameBoard.createGameBoard(lineIdentifiers, columnIdentifiers);
        char[][] machineBoard = GameBoard.createGameBoard(lineIdentifiers, columnIdentifiers);

        GameBoard.showBoard(personBoard, "Jogador");

        // Posicionando navios do jogador

        char[][] naviosJogador = lerPosicoesNavios();
        int numeroNaviosJogador = posicionarNavios(personBoard, naviosJogador);

        GameBoard.showBoard(personBoard, "Jogador");

        // Posicionando navios do computador

        char[][] naviosComputador = criarPosicoesAleatorias(lineIdentifiers, columnIdentifiers);
        int numeroNaviosComputador = posicionarNavios(machineBoard, naviosComputador);

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

                GameBoard.showBoard(machineBoard, "Computador");

                String resultadoRodada = finalizarJogo(numeroNaviosComputador,"Jogador",status);
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

                GameBoard.showBoard(personBoard, "Jogador");

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

}
