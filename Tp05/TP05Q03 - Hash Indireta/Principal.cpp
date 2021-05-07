#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <stdbool.h>
#include <err.h>
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

typedef struct No
{
    int nivel;
    NJogador elemento;
    struct No *esq, *dir;
} No;

//TIPO CELULA ===================================================================
typedef struct Celula
{
    NJogador elemento;   // Elemento inserido na celula.
    struct Celula *prox; // Aponta a celula prox.
} Celula;

//LISTA PROPRIAMENTE DITA =======================================================
typedef struct Lista
{

    Celula *primeiro;
    Celula *ultimo;

} Lista;

//cabecalhos No
No *novoNo(PJogador);

void setNivel(No *);
No *NovoNo2(int, No *, No *, int);
int getNivel(No *);

//cabecalhos Hash
void startHash();
void startHash2(int);
int h(int);
void inserirHash(PJogador);
bool pesquisarHash(char[]);

//cabecalhos AVL
bool pesquisarRec(char[], No *);
void caminharCentralRec(No *);
void caminharPreRec(No *);
void caminharPosRec(No *);
void inserirRec(PJogador, No **);
void removerRec(char[], No **);
void antecessor(No **, No **);
void removerRecSucessor(char[], No **);
void sucessor(No **, No **);

void start(Lista *);
bool pesquisar(char[], Lista *);
void caminharCentral(Lista *);
void caminharPre(Lista *);
void caminharPos(Lista *);
void inserir(PJogador, Lista *);
void remover(PJogador, Lista *);
void removerSucessor(PJogador, Lista *);
No *balancear(No *, Lista *);
No *rotacionarDir(No *, Lista *);
No *rotacionarEsq(No *, Lista *);

//cabecalhos "classe" Jogador
void construtor1(PJogador jogador);
char *trataAsterisco(char *str);
void ler(PJogador jogador, char dados[]);
void imprimir(PJogador jogador);
void construtor2(PJogador jogador, char dados[]);
void leArquivoInicial(PJogador jogador[]);
NJogador clone(PJogador jogador);
//fim cabecalhos Jogador

//cabecalhos "classe main"
bool isFim(char *);
void criaLog(double);

/**
	Variavel Numero de comparacoes
**/
int comp;

int main()
{

    PJogador jogadores[3922]; //array principal
    comp = 0;

    clock_t inicio, fim; //calcular tempo

    int pos = 0;
    char entrada[100];

    leArquivoInicial(jogadores);
    startHash();
    scanf(" %[^\n]", entrada);

    //carga inicial na estrutura
    while (!isFim(entrada))
    {
        pos = atoi(entrada);
        inserirHash(jogadores[pos]);
        scanf(" %[^\n]", entrada);
    }

    bool existe;

    inicio = clock();

    scanf(" %[^\n]", entrada);
    while (!isFim(entrada))
    {
        existe = pesquisarHash(entrada);
        printf((existe) ? "SIM\n" : "NAO\n");

        scanf(" %[^\n]", entrada);
    }
    fim = clock();

    double total = (fim - inicio) / (double)CLOCKS_PER_SEC;
    criaLog(total);
    return 0;
}

void criaLog(double tempo)
{

    FILE *fp = fopen("698159_avl.txt", "w");
    fprintf(fp, "698159 \t %.4fS \t %i ", tempo, comp);

    fclose(fp);
}

/**
 * Lista simples dinamica
 * @author Max do Val Machado
 * @version 2 01/2015
 **/

Celula *novaCelula(PJogador elemento)
{
    Celula *nova = (Celula *)malloc(sizeof(Celula));
    nova->elemento = clone(elemento);
    nova->prox = NULL;
    return nova;
}

/**
 * Cria uma lista sem elementos (somente no cabeca).
 */
void start(Lista *x)
{
    NJogador jogador;
    construtor1(&jogador);
    x->primeiro = novaCelula(&jogador);
    x->ultimo = x->primeiro;
}

/**
 * Insere um elemento na primeira posicao da lista.
 * @param x int elemento a ser inserido.
 */
void inserirInicio(PJogador x, Lista *lista)
{
    Celula *tmp = novaCelula(x);
    tmp->prox = lista->primeiro->prox;
    lista->primeiro->prox = tmp;
    if (lista->primeiro == lista->ultimo)
    {
        lista->ultimo = tmp;
    }
    tmp = NULL;
}

/**
 * Insere um elemento na ultima posicao da lista.
 * @param x int elemento a ser inserido.
 */
void inserirFim(PJogador x, Lista *lista)
{
    lista->ultimo->prox = novaCelula(x);
    lista->ultimo = lista->ultimo->prox;
}

/**
 * Remove um elemento da primeira posicao da lista.
 * @return resp int elemento a ser removido.
 * @throws Exception Se a lista nao contiver elementos.
 */
NJogador removerInicio(Lista *lista)
{
    if (lista->primeiro == lista->ultimo)
    {
        errx(1, "Erro ao remover!");
    }

    Celula *tmp = lista->primeiro;
    lista->primeiro = lista->primeiro->prox;
    NJogador resp = clone(&lista->primeiro->elemento);
    tmp->prox = NULL;
    free(tmp);
    tmp = NULL;
    return resp;
}

/**
 * Remove um elemento da ultima posicao da lista.
 * @return resp int elemento a ser removido.
 */
NJogador removerFim(Lista *lista)
{
    if (lista->primeiro == lista->ultimo)
    {
        errx(1, "Erro ao remover!");
    }

    // Caminhar ate a penultima celula:
    Celula *i;
    for (i = lista->primeiro; i->prox != lista->ultimo; i = i->prox)
        ;

    NJogador resp = clone(&lista->ultimo->elemento);
    lista->ultimo = i;
    free(lista->ultimo->prox);
    i = lista->ultimo->prox = NULL;

    return resp;
}

/**
 * Calcula e retorna o tamanho, em numero de elementos, da lista.
 * @return resp int tamanho
 */
int tamanho(Lista *lista)
{
    int tamanho = 0;
    Celula *i;
    for (i = lista->primeiro; i != lista->ultimo; i = i->prox, tamanho++)
        ;
    return tamanho;
}

/**
 * Insere um elemento em uma posicao especifica considerando que o 
 * primeiro elemento valido esta na posicao 0.
 * @param x int elemento a ser inserido.
 * @param pos int posicao da insercao.
 * @throws Exception Se <code>posicao</code> invalida.
 */
void inserir(PJogador x, int pos, Lista *lista)
{

    int tam = tamanho(lista);

    if (pos < 0 || pos > tam)
    {
        errx(1, "Erro ao inserir posicao (%d/ tamanho = %d) invalida!", pos, tam);
    }
    else if (pos == 0)
    {
        inserirInicio(x, lista);
    }
    else if (pos == tam)
    {
        inserirFim(x, lista);
    }
    else
    {
        // Caminhar ate a posicao anterior a insercao
        int j;
        Celula *i = lista->primeiro;
        for (j = 0; j < pos; j++, i = i->prox)
            ;

        Celula *tmp = novaCelula(x);
        tmp->prox = i->prox;
        i->prox = tmp;
        tmp = i = NULL;
    }
}

/**
 * Remove um elemento de uma posicao especifica da lista
 * considerando que o primeiro elemento valido esta na posicao 0.
 * @param posicao Meio da remocao.
 * @return resp int elemento a ser removido.
 * @throws Exception Se <code>posicao</code> invalida.
 */
NJogador remover(int pos, Lista *lista)
{
    NJogador resp;
    int tam = tamanho(lista);

    if (lista->primeiro == lista->ultimo)
    {
        errx(1, "Erro ao remover (vazia)!");
    }
    else if (pos < 0 || pos >= tam)
    {
        errx(1, "Erro ao remover posicao (%d/ tamanho = %d) invalida!", pos, tam);
    }
    else if (pos == 0)
    {
        resp = removerInicio(lista);
    }
    else if (pos == tam - 1)
    {
        resp = removerFim(lista);
    }
    else
    {
        // Caminhar ate a posicao anterior a insercao
        Celula *i = lista->primeiro;
        int j;
        for (j = 0; j < pos; j++, i = i->prox)
            ;

        Celula *tmp = i->prox;
        resp = clone(&tmp->elemento);
        i->prox = tmp->prox;
        tmp->prox = NULL;
        free(tmp);
        i = tmp = NULL;
    }
    return resp;
}

/**
 * Mostra os elementos da lista separados por espacos.
 */
void mostrar(Lista *lista)
{
    printf("[ ");
    Celula *i;
    for (i = lista->primeiro->prox; i != NULL; i = i->prox)
    {
        imprimir(&i->elemento);
    }
    printf("] \n");
}

/**
 * Procura um elemento e retorna se ele existe.
 * @param x Elemento a pesquisar.
 * @return <code>true</code> se o elemento existir,
 * <code>false</code> em caso contrario.
 */
bool pesquisar(char x[], Lista *lista)
{
    bool retorno = false;
    Celula *i;

    for (i = lista->primeiro->prox; i != NULL; i = i->prox)
    {
        if (strcmp(i->elemento.nome, x) == 0)
        {
            retorno = true;
            i = lista->ultimo;
        }
    }
    return retorno;
}
/****
	Tabela Hash


*******/
/**
	variaveis globais Hash
***/

int m;
Lista tabela[25];

void startHash()
{

    startHash2(25);
}

void startHash2(int x)
{

    m = x;
    for (int i = 0; i < m; i++)
        start(&tabela[i]);
}

int h(int x)
{

    return x % m;
}

void inserirHash(PJogador x)
{

    int pos = h(x->altura);

    inserirFim(x, &tabela[pos]);
}

bool pesquisarHash(char elemento[])
{
	printf("%s ",elemento);
    bool resp;
    for (int i = 0; i < m; i++)
    {

        resp = pesquisar(elemento, &tabela[i]);
        if (resp)
            i = m;
    }

    return resp;
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
