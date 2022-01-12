# Atividades Let's Code

Atividade do curso Santander Coders | Web Full-Stack da Let's Code.

## Batalha Naval

Trabalho final do modulo 6 - Java, realizado pela dupla:
1) [Cássia Martinelli](htps://github.com/cmartinellicm");
2) [Rodrigo Santana](https://github.com/rosanper).

### Requisitos da atividade:

Implementar um jogo de batalha naval em Java, com as seguintes especificações:
- Terá apenas navios do tipo submarino (1 quadrado cada);
- Cada jogador deverá posicionar em sua grelha dez submarinos;
- O programa aceitará apenas um jogador, pois o oponente será o computador;
- Para não precisarmos utilizar cores no terminal vamos usar as marcações:
    -  Navio posicionado N (ene maiúsculo);
    -  Tiro certeiro * (asterisco);
    -  Tiro na água – (traço).
- Em cada turno, a situação atual do tabuleiro do jogador deverá ser impressa na tela;
- Ao final do jogo, devemos indicar o vencedor e imprimir os dois tabuleiros.

### Organização do código:

O código está organizado em Camadas (Enums, Service e Utils) e o método main está presente na classe BattleshipGame.

### Modo de jogar:

Primeiramente o jogador irá escolher as posições onde deseja posicionar seus navios. Após todos os navios posicionados,
as rodadas começarão e o jogador vai escolher a posição onde deseja bombardear o tabuleiro adversário.
Este processo vai se repetir até que todos os navios do jogador ou do computador sejam destruídos.