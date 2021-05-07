#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <err.h>

//Jogador cabecalhos

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

void construtor1(PJogador jogador);
char *trataAsterisco(char *str);
void ler(PJogador jogador, char dados[]);
void imprimir(PJogador jogador);
void construtor2(PJogador jogador, char dados[]);
void leArquivoInicial(PJogador jogador[]);
NJogador clone(PJogador jogador);
void criaSubArray(PJogador jogadores[], NJogador subArray[], int *n);

//TIPO CELULA ===================================================================
typedef struct CelulaDupla
{
    NJogador elemento;        // Elemento inserido na celula.
    struct CelulaDupla *prox; // Aponta a celula prox.
    struct CelulaDupla *ant;  // Aponta a celula anterior.
} CelulaDupla;

CelulaDupla *novaCelulaDupla(PJogador elemento)
{
    CelulaDupla *nova = (CelulaDupla *)malloc(sizeof(CelulaDupla));
    nova->elemento = clone(elemento);
    nova->ant = nova->prox = NULL;
    return nova;
}

//lista cabecalhos
void start();
void inserirInicio(int x);
void inserirFim(PJogador x);
NJogador removerFim();
NJogador removerInicio();
int tamanho();
void inserir(int x, int pos);
void mostrar();
void mostrarInverso();
bool pesquisar(int x);

//Quicksort cabecalhos
void criaLog(double tempo, int comp);
void swapJogador(PJogador jogador1, PJogador jogador2);
void quicksortPrep(int *comp);
void quicksort(CelulaDupla *prim, CelulaDupla *ult, int *comp);
bool isFim(char *str);

int main()
{

    PJogador jogadores[3922]; //array principal

    int n = 0; //numero de posicoes do array secundario
    int comp = 0;

    clock_t inicio, fim; //calcular tempo

    int pos = 0;
    char entrada[100];

    leArquivoInicial(jogadores);
    scanf(" %[^\n]", entrada);
    start();
    while (!isFim(entrada))
    {

        pos = atoi(entrada);
        inserirFim(jogadores[pos]);

        scanf(" %[^\n]", entrada);
    }
    
    inicio = clock();

    quicksortPrep(&comp);

    mostrar();

    

    fim = clock();
    double total = (fim - inicio) / (double)CLOCKS_PER_SEC;
    criaLog(total, comp);

    return 0;
}

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
/*
	cria um array secundario com os jogadores indicados
*/
void criaSubArray(PJogador jogadores[], NJogador subArray[], int *n)
{
    char entrada[100];
    scanf(" %[^\n]", entrada);
    int pos;

    while (!isFim(entrada))
    {

        pos = atoi(entrada);
        subArray[*n] = clone(jogadores[pos]);

        scanf(" %[^\n]", entrada);
        *n = *n + 1;
    }
}

//LISTA PROPRIAMENTE DITA =======================================================
CelulaDupla *primeiro;
CelulaDupla *ultimo;

/**
 * Cria uma lista dupla sem elementos (somente no cabeca).
 */
void start()
{
    NJogador jogador;
    construtor1(&jogador);
    primeiro = novaCelulaDupla(&jogador);
    ultimo = primeiro;
}

/**
 * Insere um elemento na primeira posicao da lista.
 * @param x int elemento a ser inserido.
 */
void inserirInicio(PJogador x)
{
    CelulaDupla *tmp = novaCelulaDupla(x);

    tmp->ant = primeiro;
    tmp->prox = primeiro->prox;
    primeiro->prox = tmp;
    if (primeiro == ultimo)
    {
        ultimo = tmp;
    }
    else
    {
        tmp->prox->ant = tmp;
    }
    tmp = NULL;
}

/**
 * Insere um elemento na ultima posicao da lista.
 * @param x int elemento a ser inserido.
 */
void inserirFim(PJogador x)
{
    ultimo->prox = novaCelulaDupla(x);
    ultimo->prox->ant = ultimo;
    ultimo = ultimo->prox;
}

/**
 * Remove um elemento da primeira posicao da lista.
 * @return resp int elemento a ser removido.
 */
NJogador removerInicio()
{
    if (primeiro == ultimo)
    {
        errx(1, "Erro ao remover (vazia)!");
    }

    CelulaDupla *tmp = primeiro;
    primeiro = primeiro->prox;
    NJogador resp = clone(&primeiro->elemento);
    tmp->prox = primeiro->ant = NULL;
    free(tmp);
    tmp = NULL;
    return resp;
}

/**
 * Remove um elemento da ultima posicao da lista.
 * @return resp int elemento a ser removido.
 */
NJogador removerFim()
{
    if (primeiro == ultimo)
    {
        errx(1, "Erro ao remover (vazia)!");
    }
    NJogador resp = clone(&ultimo->elemento);
    ultimo = ultimo->ant;
    ultimo->prox->ant = NULL;
    free(ultimo->prox);
    ultimo->prox = NULL;
    return resp;
}

/**
 *  Calcula e retorna o tamanho, em numero de elementos, da lista.
 *  @return resp int tamanho
 */
int tamanho()
{
    int tamanho = 0;
    CelulaDupla *i;
    for (i = primeiro; i != ultimo; i = i->prox, tamanho++)
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
void inserir(PJogador x, int pos)
{

    int tam = tamanho();

    if (pos < 0 || pos > tam)
    {
        errx(1, "Erro ao remover (posicao %d/%d invalida!", pos, tam);
    }
    else if (pos == 0)
    {
        inserirInicio(x);
    }
    else if (pos == tam)
    {
        inserirFim(x);
    }
    else
    {
        // Caminhar ate a posicao anterior a insercao
        CelulaDupla *i = primeiro;
        int j;
        for (j = 0; j < pos; j++, i = i->prox)
            ;

        CelulaDupla *tmp = novaCelulaDupla(x);
        tmp->ant = i;
        tmp->prox = i->prox;
        tmp->ant->prox = tmp->prox->ant = tmp;
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
NJogador remover(int pos)
{
    NJogador resp;
    int tam = tamanho();

    if (primeiro == ultimo)
    {
        errx(1, "Erro ao remover (vazia)!");
    }
    else if (pos < 0 || pos >= tam)
    {
        errx(1, "Erro ao remover (posicao %d/%d invalida!", pos, tam);
    }
    else if (pos == 0)
    {
        resp = removerInicio();
    }
    else if (pos == tam - 1)
    {
        resp = removerFim();
    }
    else
    {
        // Caminhar ate a posicao anterior a insercao
        CelulaDupla *i = primeiro->prox;
        int j;
        for (j = 0; j < pos; j++, i = i->prox)
            ;

        i->ant->prox = i->prox;
        i->prox->ant = i->ant;
        resp = clone(&i->elemento);
        i->prox = i->ant = NULL;
        free(i);
        i = NULL;
    }

    return resp;
}

/**
 * Mostra os elementos da lista separados por espacos.
 */
void mostrar()
{
    CelulaDupla *i;
    int j = 0;
    for (i = primeiro->prox; i != NULL; i = i->prox, j++)
    {
        //  printf("[%d] ", j);
        imprimir(&i->elemento);
    }
}

/**
 * Mostra os elementos da lista de forma invertida 
 * e separados por espacos.
 */
void mostrarInverso()
{

    CelulaDupla *i;
    int j = 0;
    for (i = ultimo; i != primeiro; i = i->ant, j++)
    {
        printf("[%i] ", j);
        imprimir(&i->elemento);
    }
}

/**
 * Procura um elemento e retorna se ele existe.
 * @param x Elemento a pesquisar.
 * @return <code>true</code> se o elemento existir,
 * <code>false</code> em caso contrario.
 */
bool pesquisar(PJogador x)
{
    bool resp = false;
    CelulaDupla *i;

    for (i = primeiro->prox; i != NULL; i = i->prox)
    {
        if (i->elemento.nome == x->nome)
        {
            resp = true;
            i = ultimo;
        }
    }
    return resp;
}

void swapJogador(CelulaDupla *i, CelulaDupla *j)
{

    NJogador tmp;
    //printf("----------------------Antes-------------------\n");
    tmp = clone(&i->elemento);
    i->elemento = clone(&j->elemento);
    j->elemento = clone(&tmp);
    //printf("----------------------Depois-------------------\n");
}

void quicksortPrep(int *comp)
{

    quicksort(primeiro->prox, ultimo, comp);
}

int calculaDistancia(CelulaDupla *fim)
{

    int resp = 0;
    for (CelulaDupla *tmp = primeiro; tmp != fim; tmp = tmp->prox)
    {
        resp++;
    }
    return resp;
}
void quicksort(CelulaDupla *esq, CelulaDupla *dir, int *comp)
{
    CelulaDupla *i = esq, *j = dir;

    NJogador pivo;
    CelulaDupla *tmp;
    int cont = 0;
    int end = 1;

    for (tmp = esq; tmp->prox != dir; tmp = tmp->prox, end++)
        ;

    for (tmp = esq; cont < ( end / 2); cont++)
        tmp = tmp->prox;

    pivo = clone(&tmp->elemento);

    while (calculaDistancia(i) <= calculaDistancia(j))
    {
        *comp = *comp + 1;

        while ((strcmp(i->elemento.estadoNascimento, pivo.estadoNascimento) < 0) || (strcmp(i->elemento.estadoNascimento, pivo.estadoNascimento) == 0 && strcmp(i->elemento.nome, pivo.nome) < 0))
        {
            i = i->prox;
            *comp = *comp + 1;
        }
        *comp = *comp + 1;
        while ((strcmp(j->elemento.estadoNascimento, pivo.estadoNascimento) > 0) || (strcmp(j->elemento.estadoNascimento, pivo.estadoNascimento) == 0 && strcmp(j->elemento.nome, pivo.nome) > 0))
        {
            j = j->ant;
            *comp = *comp + 1;
        }
        if (calculaDistancia(i) <= calculaDistancia(j))
        {
            swapJogador(i, j);

            i = i->prox;
            j = j->ant;
        }
    }
    
    if (calculaDistancia(esq) < calculaDistancia(j))
        quicksort(esq, j, comp);

    if (calculaDistancia(i) < calculaDistancia(dir))
        quicksort(i, dir, comp);
}

void criaLog(double tempo, int comp)
{

    FILE *fp = fopen("698159_quicksort2.txt", "w");
    fprintf(fp, "698159 \t %.4fS \t %i ", tempo, comp);

    fclose(fp);
}
