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
bool isFim(char *str);

//cabecalhos "classe" lista
void start();
void inserirInicio(PJogador x);
void inserirFim(PJogador x);
void inserir(PJogador x, int pos);
NJogador removerInicio();
NJogador removerFim();
NJogador remover(int pos);
void mostrar();

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

    while (!isFim(entrada))
    {
        pos = atoi(entrada);
        inserirFim(jogadores[pos]);
        scanf(" %[^\n]", entrada);
    }
    int operacoes = 0;
    scanf(" %i", &operacoes);
    for (int i = 0; i < operacoes; i++)
    {
        //fflush(stdin);
        scanf(" %[^\n]", entrada);
        descobreOperacao(entrada, jogadores);
    }
    mostrar();
    return 0;
}

void descobreOperacao(char entrada[], PJogador jogadores[])
{
    char *divisao[3];
    divisao[0] = NULL;
    divisao[1] = NULL;
    divisao[2] = NULL;
    divisao[0] = strsep(&entrada, " ");
    divisao[1] = strsep(&entrada, " ");
    divisao[2] = strsep(&entrada, " ");
    NJogador retorno;

    int posicao = 0;
    if (divisao[1] != NULL)
    {
        posicao = atoi(divisao[1]);
    }

    if (strcmp(divisao[0], "II") == 0)
    {
        inserirInicio(jogadores[posicao]);
    }
    else if (strcmp(divisao[0], "I*") == 0)
    {
        inserir(jogadores[atoi(divisao[2])], posicao);
    }
    else if (strcmp(divisao[0], "IF") == 0)
    {
        inserirFim(jogadores[posicao]);
    }
    else if (strcmp(divisao[0], "RI") == 0)
    {
        retorno = removerInicio();
        printf("(R) %s\n", retorno.nome);
    }
    else if (strcmp(divisao[0], "R*") == 0)
    {
        retorno = remover(atoi(divisao[1]));
        printf("(R) %s\n", retorno.nome);
    }
    else if (strcmp(divisao[0], "RF") == 0)
    {
        retorno = removerFim();
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
/"Classe Lista"
**/

#define MAXTAM 3922

NJogador array[MAXTAM]; // Elementos da pilha
int n;                  // Quantidade de array.

/**
 * Inicializacoes
 */
void start()
{
    n = 0;
}

/**
 * Insere um elemento na primeira posicao da lista e move os demais
 * elementos para o fim da 
 * @param x int elemento a ser inserido.
 */
void inserirInicio(PJogador x)
{
    int i;

    //validar insercao
    if (n >= MAXTAM)
    {
        printf("Erro ao inserir!");
        exit(1);
    }

    //levar elementos para o fim do array
    for (i = n; i > 0; i--)
    {
        array[i] = clone(&array[i - 1]);
    }

    array[0] = clone(x);
    n++;
}

/**
 * Insere um elemento na ultima posicao da 
 * @param x int elemento a ser inserido.
 */
void inserirFim(PJogador x)
{

    //validar insercao
    if (n >= MAXTAM)
    {
        printf("Erro ao inserir!");
        exit(1);
    }

    array[n] = clone(x);
    n++;
}

/**
 * Insere um elemento em uma posicao especifica e move os demais
 * elementos para o fim da 
 * @param x int elemento a ser inserido.
 * @param pos Posicao de insercao.
 */
void inserir(PJogador x, int pos)
{
    int i;

    //validar insercao
    if (n >= MAXTAM || pos < 0 || pos > n)
    {
        printf("Erro ao inserir!");
        exit(1);
    }

    //levar elementos para o fim do array
    for (i = n; i > pos; i--)
    {
        array[i] = clone(&array[i - 1]);
    }

    array[pos] = clone(x);
    n++;
}

/**
 * Remove um elemento da primeira posicao da lista e movimenta 
 * os demais elementos para o inicio da mesma.
 * @return resp int elemento a ser removido.
 */
NJogador removerInicio()
{
    int i;
    NJogador resp;

    //validar remocao
    if (n == 0)
    {
        printf("Erro ao remover!");
        exit(1);
    }

    resp = clone(&array[0]);
    n--;

    for (i = 0; i < n; i++)
    {
        array[i] = clone(&array[i + 1]);
    }

    return resp;
}

/**
 * Remove um elemento da ultima posicao da 
 * @return resp int elemento a ser removido.
 */
NJogador removerFim()
{

    //validar remocao
    if (n == 0)
    {
        printf("Erro ao remover!");
        exit(1);
    }

    return array[--n];
}

/**
 * Remove um elemento de uma posicao especifica da lista e 
 * movimenta os demais elementos para o inicio da mesma.
 * @param pos Posicao de remocao.
 * @return resp int elemento a ser removido.
 */
NJogador remover(int pos)
{
    int i;
    NJogador resp;

    //validar remocao
    if (n == 0 || pos < 0 || pos >= n)
    {
        printf("Erro ao remover!");
        exit(1);
    }

    resp = clone(&array[pos]);
    n--;

    for (i = pos; i < n; i++)
    {
        array[i] = clone(&array[i + 1]);
    }

    return resp;
}

/**
 * Mostra os array separados por espacos.
 */
void mostrar()
{
    int i;



    for (i = 0; i < n; i++)
    {
        printf("[%d] ", i);
        imprimir(&array[i]);
    }
}

/**
 * Procura um array e retorna se ele existe.
 * @param x int elemento a ser pesquisado.
 * @return <code>true</code> se o array existir,
 * <code>false</code> em caso contrario.
 */
bool pesquisar(PJogador x)
{
    bool retorno = false;
    int i;

    for (i = 0; i < n && retorno == false; i++)
    {
        retorno = (array[i].id == x->id);
    }
    return retorno;
}
