#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

typedef struct
{

    int id,
        altura,
        peso,
        anoNascimento;

    char *nome,
        *universidade,
        *cidadeNascimento,
        *estadoNascimento;

} jogador;

typedef jogador *PJogador;
typedef jogador NJogador;

void construtor1(PJogador jogador)
{

    jogador->id = 0;
    jogador->altura = 0;
    jogador->peso = 0;
    jogador->anoNascimento = 0;

    jogador->nome = (char *)malloc(sizeof(char) * 100);
    jogador->universidade = (char *)malloc(sizeof(char) * 100);
    jogador->cidadeNascimento = (char *)malloc(sizeof(char) * 100);
    jogador->estadoNascimento = (char *)malloc(sizeof(char) * 100);
}

/**
	retira possiveis chiados nas strings
**/
char *trataAsterisco(char *str)
{
	
    if(strstr(str,"*")!=NULL){
	
	for(int i = 0;i<strlen(str);i++){
		if(str[i]=='*'){
			str[i] = '\0';
		}
			
	}

	}
    

    return str;
}
/**
	divide a entrada inteira em partes menores e as le para o jogador
**/

void ler(PJogador jogador, char dados[])
{

    char *array[8];

    char *emBranco = (char *)malloc(sizeof(char *) * 100);
    strcpy(emBranco, "nao informado");

    array[0] = strsep(&dados, ",");

    for (int i = 1; i < 8; i++)
    {
        array[i] = strsep(&dados, ",");
    }
    
    array[1] = trataAsterisco(array[1]);

    jogador->id = atoi(array[0]);
    jogador->altura = atoi(array[2]);
    jogador->peso = atoi(array[3]);
    jogador->anoNascimento = atoi(array[5]);

    if (strcmp(array[1], "\0") == 0)
        jogador->nome = emBranco;
    else
        jogador->nome = array[1];

    if (strcmp(array[4], "\0") == 0)
        jogador->universidade = emBranco;
    else
        jogador->universidade = array[4];

    if (strcmp(array[6], "\0") == 0)
    {
        jogador->cidadeNascimento = emBranco;
    }
    else
    {
        jogador->cidadeNascimento = array[6];
    }

    if (strcmp(array[7], "\n") == 0)
        jogador->estadoNascimento = emBranco;
    else
        jogador->estadoNascimento = strsep(&array[7], "\n");
}

/**
	Imprime instancia de Jogador selecionado nos moldes escolhidos
**/

void imprimir(PJogador jogador)
{

    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n", jogador->id, jogador->nome, jogador->altura, jogador->peso, jogador->anoNascimento, jogador->universidade, jogador->cidadeNascimento, jogador->estadoNascimento);
}

void construtor2(PJogador jogador, char dados[])
{
    jogador->id = 0;
    jogador->altura = 0;
    jogador->peso = 0;
    jogador->anoNascimento = 0;

    jogador->nome = (char *)malloc(sizeof(char) * 100);
    jogador->universidade = (char *)malloc(sizeof(char) * 100);
    jogador->cidadeNascimento = (char *)malloc(sizeof(char) * 100);
    jogador->estadoNascimento = (char *)malloc(sizeof(char) * 100);
    ler(jogador, dados);
}

/**
	Clona instancia passada para nova posicao
**/

void clone(PJogador origem, PJogador resultado)
{
    construtor1(resultado);
    resultado->id = origem->id;
    strcpy(resultado->nome, origem->nome);
    resultado->altura = origem->altura;
    resultado->peso = origem->peso;
    strcpy(resultado->universidade, origem->universidade);
    resultado->anoNascimento = origem->anoNascimento;
    strcpy(resultado->cidadeNascimento, origem->cidadeNascimento);
    strcpy(resultado->estadoNascimento, origem->estadoNascimento);
}

/**

	le todos os integrantes do arquivo e os instancia como jogador
**/

void leArquivoInicial(PJogador jogador[])
{
    FILE *Arq = fopen("/tmp/players.csv", "r");

    char *dados = (char *)malloc(sizeof(char) * 200);
    int i = 0;
    fgets(dados, 200, Arq);

    while (i < 3922)
    {
        dados = (char *)malloc(sizeof(char) * 200);
        fgets(dados, 200, Arq);


        jogador[i] = (PJogador)malloc(sizeof(NJogador));
        construtor2(jogador[i], dados);

        i++;
    }
    fclose(Arq);
}

bool isFim(char *str)
{

    return strlen(str) == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M';
}
/*
	cria um array secundario com os jogadores indicados
*/
void criaSubArray(PJogador jogadores[], PJogador subArray[], int *n)
{
    char entrada[100];
    scanf(" %[^\n]", entrada);
    int pos;

    while (!isFim(entrada))
    {

        pos = atoi(entrada);
        subArray[*n] = (PJogador)malloc(sizeof(NJogador));
        clone(jogadores[pos], subArray[*n]);

        scanf(" %[^\n]", entrada);
        *n = *n + 1;
    }
}

/**
	pesquisa binaria para nome do jogador
	
**/


bool pBinaria(char nome[], int n, PJogador array[],int *comp)
{

    bool resp = false;
    int dir = n - 1;
    int esq = 0;
    int meio;

    while (esq <= dir)
    {
        meio = (esq + dir) / 2;
 
        if (strcmp(nome, array[meio]->nome) == 0)
        {
        	*comp = *comp+1;

            resp = true;
            esq = n;
        }
        else if (strcmp(nome, array[meio]->nome) > 0)
        {
        	*comp = *comp+2;
            esq = meio + 1;
        }
        else
        {
        	*comp = *comp+2;
            dir = meio - 1;
        }
    }
    return resp;
}

/**
	ordena para pesquisa binaria
*/

void insercao(PJogador *array, int n)
{
    PJogador tmp;
    for (int i = 1; i < n; i++)
    {
        tmp = (PJogador)malloc(sizeof(NJogador));

        clone(array[i], tmp);
 
        int j = i - 1;

        while ((j >= 0) && (strcmp(array[j]->nome, tmp->nome) > 0))
        {
 
            clone(array[j], array[j + 1]);
            j--;
        }
        clone(tmp, array[j + 1]);
        free(tmp);
    }
}
void criaLog(double tempo,int comp){

	FILE *fp = fopen("698159_binaria.txt","w");
	fprintf(fp,"698159 \t %.4fS \t %i ",tempo,comp);
	
	fclose(fp);
}

int main()
{

    PJogador jogadores[3922];//array principal
    PJogador subArray[3922];//array criado secundariamente

    int n = 0;//numero de posicoes do array secundario
    int comp = 0;
    
    clock_t inicio, fim;//calcular tempo
    
    int pos = 0;
    char *resp = (char *)malloc(sizeof(char) * 3);
    char *entrada = (char *)malloc(sizeof(char) * 100);

    leArquivoInicial(jogadores);

    criaSubArray(jogadores, subArray, &n);

    insercao(subArray, n);
    

    scanf(" %[^\n]", entrada);
	
	inicio = clock();
    while (!isFim(entrada))
    {
        entrada = trataAsterisco(entrada);

        (pBinaria(entrada, n, subArray,&comp)) ? strcpy(resp, "SIM") : strcpy(resp, "NAO");
        printf("%s\n", resp);
        scanf(" %[^\n]", entrada);
    }
    fim = clock();
    double total = (fim - inicio)/(double)CLOCKS_PER_SEC;
    criaLog(total,comp);

    return 0;
}
