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

        // Posicionando navios do jogador

        char[][] naviosJogador = lerPosicoesNavios();
        int numeroNaviosJogador = posicionarNavios(tabuleiroJogador, naviosJogador);

        exibirTabuleiro(tabuleiroJogador, "Jogador");

        // Posicionando navios do computador

        char[][] naviosComputador = criarPosicoesAleatorias(letras, numeros);
        int numeroNaviosComputador = posicionarNavios(tabuleiroComputador, naviosComputador);

        exibirTabuleiro(tabuleiroComputador, "Computador");

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

                int resultadoBombardeioJogador = bombardear(tabuleiroComputador,"Jogador",
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

                exibirTabuleiro(tabuleiroComputador, "Computador");

                String resultadoRodada = finalizarJogo(numeroNaviosComputador,"Jogador",status);
                status = resultadoRodada;
            }

            if(rodada%2 != 0){   // rodada computador
                int numeroLinha = (int) (Math.random()*4);
                int numeroColuna = (int) (Math.random()*4);

                char posicaoLinha = numeros[numeroLinha];
                char posicaoColuna = letras[numeroColuna];

                int resultadoBombardeioComputador = bombardear(tabuleiroJogador,"Computador",
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

                exibirTabuleiro(tabuleiroJogador, "Jogador");

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
