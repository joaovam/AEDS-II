#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <err.h>

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

} Jogador;

typedef Jogador *PJogador;
typedef Jogador NJogador;

//cabecalhos "classe" Jogador
void construtor1(PJogador jogador);
char *trataAsterisco(char *str);
void ler(PJogador jogador, char dados[]);
void imprimir(PJogador jogador);
void construtor2(PJogador jogador, char dados[]);
void leArquivoInicial(PJogador jogador[]);
NJogador clone(PJogador jogador);
//fim cabecalhos Jogador


//TIPO CELULA ===================================================================
typedef struct Celula {
	NJogador elemento;        // Elemento inserido na celula.
	struct Celula* prox; // Aponta a celula prox.
} Celula;

Celula* novaCelula(PJogador elemento) {
   Celula* nova = (Celula*) malloc(sizeof(Celula));
   nova->elemento = clone(elemento);
   nova->prox = NULL;
   return nova;
}



bool isFim(char *str);

//cabecalhos "classe" pilha
void start();
void inserir(PJogador x);
NJogador remover();
void mostrar();
int mostrarPilha(Celula* ,int );

//cabecalhos "classe main"
void descobreOperacao(char entrada[], PJogador jogadores[]);

//variaveis

int main()
{

    PJogador jogadores[3922]; //array principal

    clock_t inicio, fim; //calcular tempo

    int pos = 0;
    char entrada[100];

    leArquivoInicial(jogadores);
    scanf(" %[^\n]", entrada);

	//carga inicial na estrutura
    while (!isFim(entrada))
    {
        pos = atoi(entrada);
        inserir(jogadores[pos]);
        scanf(" %[^\n]", entrada);
    }
    
    //operacoes realizadas na estrutura
    int operacoes = 0;
    scanf(" %i", &operacoes);
    for (int i = 0; i < operacoes; i++)
    {
        scanf(" %[^\n]", entrada);
        descobreOperacao(entrada, jogadores);
    }
    mostrar();
    return 0;
}

void descobreOperacao(char entrada[], PJogador jogadores[])
{
    char *divisao[2];
    divisao[0] = NULL;
    divisao[1] = NULL;
    divisao[0] = strsep(&entrada, " ");
    divisao[1] = strsep(&entrada, " ");
    NJogador retorno;

    int posicao = 0;
    if (divisao[1] != NULL)
    {
        posicao = atoi(divisao[1]);
    }

    if (strcmp(divisao[0], "I") == 0)
    {
        inserir(jogadores[posicao]);
    
    }else if (strcmp(divisao[0], "R") == 0)
    {
        retorno = remover();
        printf("(R) %s\n", retorno.nome);
        
    }
    else
    {

        printf("Erro ao selecionar Operacao\n");
    }
}
/**
/"Classe" Jogador
**/

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
	retira possiveis chiados nas strings
**/
char *trataAsterisco(char *str)
{

    if (strstr(str, "*") != NULL)
    {

        for (int i = 0; i < strlen(str); i++)
        {
            if (str[i] == '*')
            {
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

    //array[1] = trataAsterisco(array[1]);

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

    printf("## %s ## %d ## %d ## %d ## %s ## %s ## %s ##\n", jogador->nome, jogador->altura, jogador->peso, jogador->anoNascimento, jogador->universidade, jogador->cidadeNascimento, jogador->estadoNascimento);
}

/**
	Clona instancia passada para nova posicao
**/

NJogador clone(PJogador jogador)
{
    NJogador retorno;
    construtor1(&retorno);
    retorno.id = jogador->id;
    strcpy(retorno.nome, jogador->nome);
    retorno.altura = jogador->altura;
    retorno.peso = jogador->peso;
    retorno.anoNascimento = jogador->anoNascimento;
    strcpy(retorno.universidade, jogador->universidade);
    strcpy(retorno.cidadeNascimento, jogador->cidadeNascimento);
    strcpy(retorno.estadoNascimento, jogador->estadoNascimento);

    return retorno;
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

/**
/"Classe" Pilha
**/

/**
 * Pilha dinamica
 * @author Max do Val Machado
 * @version 2 01/2015
 */




//PILHA PROPRIAMENTE DITA =======================================================
Celula* topo; // Sem celula cabeca.


/**
 * Cria uma fila sem elementos.
 */
void start () {
   topo = NULL;
}


/**
 * Insere elemento na pilha (politica FILO).
 * @param x int elemento a inserir.
 */
void inserir(PJogador x) {
   Celula* tmp = novaCelula(x);
   tmp->prox = topo;
   topo = tmp;
   tmp = NULL;
}

/**
 * Remove elemento da pilha (politica FILO).
 * @return Elemento removido.
 */
NJogador remover() {
   if (topo == NULL) {
      errx(1, "Erro ao remover!");
   }

   NJogador resp = clone(&topo->elemento);
   Celula* tmp = topo;
   topo = topo->prox;
   tmp->prox = NULL;
   free(tmp);
   tmp = NULL;
   return resp;
}


/**
 * Mostra os elementos separados por espacos, comecando do topo.
 */
 void mostrar(){
 
 	mostrarPilha(topo,0);
 }
int mostrarPilha(Celula* i,int j) {
   

   if(i!=NULL) {
   	  j = mostrarPilha(i->prox,j);
   	  
      printf("[%d] ", j);
      j++;
      imprimir(&i->elemento);
   }
   return j;


}

