package com.letscode;

import java.util.Arrays;
import java.util.Scanner;

public class batalhaNaval {

    public static void main(String[] args) {

        // criar o tabuleiroJogador

        char[][] tabuleiroJogador = new char[10][10];

        for(char[] linha: tabuleiroJogador){
            Arrays.fill(linha,' ');
        }

        for(int i = 0; i< tabuleiroJogador.length;i++){
            for (int j = 0; j< tabuleiroJogador[i].length;j = j + 2){
                tabuleiroJogador[i][j] = '|';
            }
        }

        for(int i = 0; i< tabuleiroJogador.length;i= i+2){
            for (int j = 0; j< tabuleiroJogador[i].length;j++){
                tabuleiroJogador[i][j] = '-';
            }
        }


        char[] letras = {'A','B','C','D','E','F'};
        char[] numeros = {'1','2','3','4','5','6'};

        int indiceArray = 0;
        for (int i = 3; i< tabuleiroJogador.length;i = i + 2){
            tabuleiroJogador[i][1] = numeros[indiceArray];
            indiceArray++;
        }

        indiceArray = 0;
        for (int j = 3; j< tabuleiroJogador[1].length;j = j + 2){
            tabuleiroJogador[1][j] = letras[indiceArray];
            indiceArray++;
        }

        System.out.printf("Tabuleiro Jogador");
        for(int i = 0; i< tabuleiroJogador.length;i++){
            System.out.printf("\n");
            for (int j = 0; j< tabuleiroJogador[i].length;j++){
                System.out.print(tabuleiroJogador[i][j]);
            }
        }

        // Posicionando Navio jogador

        Scanner lerPosicoes = new Scanner(System.in);
        int numeroNavios = 0;

        while(numeroNavios <3){


            System.out.print("\nDigite a linha onde deseja colocar o navio: ");
            char posicaoLinha = lerPosicoes.next().charAt(0);


            System.out.print("Digite a coluna onde deseja colocar o navio: ");
            char posicaoColuna = lerPosicoes.next().toUpperCase().charAt(0);

            for (int i = 0; i< tabuleiroJogador.length;i++){
                if(tabuleiroJogador[i][1] == posicaoLinha){
                    for (int j = 0; j< tabuleiroJogador[i].length;j++){
                        if(tabuleiroJogador[1][j] == posicaoColuna){
                            if (tabuleiroJogador[i][j]==' '){
                                tabuleiroJogador[i][j] = 'N';
                                numeroNavios++;
                            }else{
                                System.out.printf("Já existe um navio nesse lugar, escolha outro");
                            }
                        }
                    }
                }
            }

            System.out.printf("Tabuleiro Jogador");
            for(int i = 0; i< tabuleiroJogador.length;i++){
                System.out.printf("\n");
                for (int j = 0; j< tabuleiroJogador[i].length;j++){
                    System.out.print(tabuleiroJogador[i][j]);
                }
            }



            //OBS: validar as entradas e assegurar que não existe navio posicionado no local
        }

        // Criar tabuleiro computador

        char[][] tabuleiroComputador = new char[10][10];

        for(char[] linha: tabuleiroComputador){
            Arrays.fill(linha,' ');
        }

        for(int i = 0; i< tabuleiroComputador.length;i++){
            for (int j = 0; j< tabuleiroComputador[i].length;j = j + 2){
                tabuleiroComputador[i][j] = '|';
            }
        }

        for(int i = 0; i< tabuleiroComputador.length;i= i+2){
            for (int j = 0; j< tabuleiroComputador[i].length;j++){
                tabuleiroComputador[i][j] = '-';
            }
        }

        indiceArray = 0;
        for (int i = 3; i< tabuleiroComputador.length;i = i + 2){
            tabuleiroComputador[i][1] = numeros[indiceArray];
            indiceArray++;
        }

        indiceArray = 0;
        for (int j = 3; j< tabuleiroComputador[1].length;j = j + 2){
            tabuleiroComputador[1][j] = letras[indiceArray];
            indiceArray++;
        }


        // Posicionar navio computador

        int numeroNaviosComputador = 0;

        while(numeroNaviosComputador<3){
            int numeroLinha = (int) (Math.random()*4);
            int numeroColuna = (int) (Math.random()*4);

            char posicaoLinha = numeros[numeroLinha];
            char posicaoColuna = letras[numeroColuna];

            for (int i = 0; i< tabuleiroComputador.length;i++){
                if(tabuleiroComputador[i][1] == posicaoLinha){
                    for (int j = 0; j< tabuleiroComputador[i].length;j++){
                        if(tabuleiroComputador[1][j] == posicaoColuna){
                            if (tabuleiroComputador[i][j]==' '){
                                tabuleiroComputador[i][j] = 'N';
                                numeroNaviosComputador++;
                            }
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

        // jogando

        String status = "jogando";     // criar um enum para isso

        while(status == "jogando"){
            int rodada = 0;

            if(rodada%2 == 0){                             // rodada jogador

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
                                    numeroNavios--;
                                    rodada++;
                                }else if(tabuleiroJogador[i][j]==' '){
                                    tabuleiroJogador[i][j] = '-';
                                    rodada++;
                                }
                            }
                        }
                    }
                }

                System.out.printf("Tabuleiro Jogador");
                for(int i = 0; i< tabuleiroJogador.length;i++){
                    System.out.printf("\n");
                    for (int j = 0; j< tabuleiroJogador[i].length;j++){
                        System.out.print(tabuleiroJogador[i][j]);
                    }
                }

                if (numeroNavios == 0){                            //finalizando o jogo
                    System.out.printf("Visshh, você foi derrotado!");
                    status = "jogo finalizado";
                }

            }
        }
    }
}
