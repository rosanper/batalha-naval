package com.letscode;

import java.util.Arrays;
import java.util.Scanner;

public class batalhaNaval {

    public static void main(String[] args) {

        char[] letras = {'A','B','C','D','E','F'};
        char[] numeros = {'1','2','3','4','5','6'};

        char[][] tabuleiroJogador = criarTabuleiro(letras, numeros);
        char[][] tabuleiroComputador = criarTabuleiro(letras, numeros);

        exibirTabuleiro(tabuleiroJogador, "Jogador");

        // Posicionando Navio jogador

        char[][] naviosJogador = lerPosicoesNavios();

        int numeroNaviosJogador = posicionarNavios(tabuleiroJogador, naviosJogador);

        exibirTabuleiro(tabuleiroJogador, "Jogador");

        // Posicionar navio computador

        char[][] naviosComputador = criarPosicoesAleatorias(letras, numeros);

        int numeroNaviosComputador = posicionarNavios(tabuleiroComputador, naviosComputador);

        exibirTabuleiro(tabuleiroComputador, "Computador");

        // jogando

        String status = "jogando";     // criar um enum para isso

        while(status == "jogando"){
            int rodada = 0;

            if(rodada%2 == 0){                             // rodada jogador

                Scanner lerPosicoes = new Scanner(System.in);

                System.out.print("\nDigite a linha onde deseja bombardear: ");
                char posicaoLinha = lerPosicoes.next().charAt(0);


                System.out.print("Digite a coluna onde deseja bombardear: ");
                char posicaoColuna = lerPosicoes.next().toUpperCase().charAt(0);

                for (int i = 0; i< tabuleiroComputador.length;i++){
                    if(tabuleiroComputador[i][1] == posicaoLinha){
                        for (int j = 0; j< tabuleiroComputador[i].length;j++){
                            if(tabuleiroComputador[1][j] == posicaoColuna){
                                if (tabuleiroComputador[i][j]=='N'){
                                    tabuleiroComputador[i][j] = '*';
                                    numeroNaviosComputador--;
                                    rodada++;
                                }else if(tabuleiroComputador[i][j]==' '){
                                    tabuleiroComputador[i][j] = '-';
                                    rodada++;
                                }else{
                                    System.out.printf("você ja bombardeou esse local, escolha outro");
                                }
                            }
                        }
                    }
                }

                System.out.printf("\nTabuleiro Computador");
                for(int i = 0; i< tabuleiroComputador.length;i++){
                    System.out.printf("\n");
                    for (int j = 0; j< tabuleiroComputador[i].length;j++){
                        System.out.print(tabuleiroComputador[i][j]);
                    }
                }

                if (numeroNaviosComputador == 0){                  //finalizando o jogo
                    System.out.printf("Parabens, você venceu!!!");
                    status = "jogo finalizado";
                }


            }
            if(rodada%2 != 0){   // rodada computador
                int numeroLinha = (int) (Math.random()*4);
                int numeroColuna = (int) (Math.random()*4);

                char posicaoLinha = numeros[numeroLinha];
                char posicaoColuna = letras[numeroColuna];

                for (int i = 0; i< tabuleiroJogador.length;i++){
                    if(tabuleiroJogador[i][1] == posicaoLinha){
                        for (int j = 0; j< tabuleiroJogador[i].length;j++){
                            if(tabuleiroJogador[1][j] == posicaoColuna){
                                if (tabuleiroJogador[i][j]=='N'){
                                    tabuleiroJogador[i][j] = '*';
                                    numeroNaviosJogador--;
                                    rodada++;
                                }else if(tabuleiroJogador[i][j]==' '){
                                    tabuleiroJogador[i][j] = '-';
                                    rodada++;
                                }
                            }
                        }
                    }
                }

                exibirTabuleiro(tabuleiroJogador, "Jogador");

                if (numeroNaviosJogador == 0){                            //finalizando o jogo
                    System.out.printf("Visshh, você foi derrotado!");
                    status = "jogo finalizado";
                }

            }
        }
    }

    private static int posicionarNavios(char[][] tabuleiro, char[][] posicaoNavios) {
        int numeroNavios = 0;

        while(numeroNavios < posicaoNavios.length){

            char posicaoLinha = posicaoNavios[numeroNavios][0];
            char posicaoColuna = posicaoNavios[numeroNavios][1];

            for (int i = 0; i< tabuleiro.length;i++){
                if(tabuleiro[i][1] == posicaoLinha){
                    for (int j = 0; j< tabuleiro[i].length;j++){
                        if(tabuleiro[1][j] == posicaoColuna){
                            if (tabuleiro[i][j]==' '){
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

    private static char[][] criarPosicoesAleatorias(char[] letras, char[] numeros) {
        char[][] naviosComputador = new char[3][2];
        int numeroLinha;
        int numeroColuna;

        for (int i = 0; i < naviosComputador.length; i++) {
            numeroLinha = (int) (Math.random()*4);
            naviosComputador[i][0] = numeros[numeroLinha];

            numeroColuna = (int) (Math.random()*4);
            naviosComputador[i][1] = letras[numeroColuna];
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

    private static char[][] criarTabuleiro(char[] letras, char[] numeros) {
        int indiceArray = 0;
        char[][] tabuleiro = new char[10][10];

        for(char[] linha : tabuleiro){
            Arrays.fill(linha,' ');
        }

        for(int i = 0; i < tabuleiro.length; i++){
            for (int j = 0; j < tabuleiro[i].length; j = j + 2){
                tabuleiro[i][j] = '|';
            }
        }

        for(int i = 0; i < tabuleiro.length; i = i+2){
            for (int j = 0; j < tabuleiro[i].length; j++){
                tabuleiro[i][j] = '-';
            }
        }

        for (int i = 3; i < tabuleiro.length; i = i + 2){
            tabuleiro[i][1] = numeros[indiceArray];
            indiceArray++;
        }

        indiceArray = 0;
        for (int j = 3; j< tabuleiro[1].length;j = j + 2){
            tabuleiro[1][j] = letras[indiceArray];
            indiceArray++;
        }
        return tabuleiro;
    }
}
