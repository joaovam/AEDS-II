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

typedef struct No {
	  int nivel;
      NJogador elemento;
	struct No *esq, *dir;
} No;


//cabecalhos No
No* novoNo(PJogador );
void setNivel(No*);
No* NovoNo2(int, No*, No*, int);
int getNivel(No*);


//cabecalhos AVL
bool pesquisarRec(char[], No*);
void caminharCentralRec(No*);
void caminharPreRec(No*);
void caminharPosRec(No*);
void inserirRec(PJogador, No**);
void removerRec(char[], No**);
void antecessor(No**, No**);
void removerRecSucessor(char[], No**);
void sucessor(No**, No**);

void start();
bool pesquisar(char[]);
void caminharCentral();
void caminharPre();
void caminharPos();
void inserir(PJogador);
void remover(PJogador);
void removerSucessor(PJogador);
No* balancear(No*);
No* rotacionarDir(No*);
No* rotacionarEsq(No*);

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
bool isFim(char*);
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
    start();
    scanf(" %[^\n]", entrada);

	//carga inicial na estrutura
    while (!isFim(entrada))
    {
        pos = atoi(entrada);
        inserir(jogadores[pos]);
        scanf(" %[^\n]", entrada);
    }
    
    bool existe;
    
    inicio = clock();
    
    scanf(" %[^\n]", entrada);
	while(!isFim(entrada))
    {
        existe = pesquisar(entrada);
        printf((existe)?"SIM\n":"NAO\n");
        
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


/***

	"Classe" No
**/

No* novoNo(PJogador elemento) {
   No* novo = (No*) malloc(sizeof(No));
   novo->elemento = clone(elemento);
   novo->esq = NULL;
   novo->dir = NULL;
   novo->nivel = 0;
   return novo;
}

No* NovoNo2(PJogador elemento, No* esq, No* dir, int nivel) {
	No* novo = (No*) malloc(sizeof(No));
	novo->elemento = clone(elemento);
	novo->esq = esq;
	novo->dir = dir;
    novo->nivel = nivel;
    return novo;
}


	/**
	 * Cálculo do número de níveis a partir de um vértice
	 */
void setNivel(No* no) {

	no->nivel = (getNivel(no->esq) > getNivel(no->dir))? 1 + getNivel(no->esq) : getNivel(no->dir) + 1;
   }

	/**
	 * Retorna o número de níveis a partir de um vértice 
	 * @param no nó que se deseja o nível.
	 */
int getNivel(No* no) {
	
   return (no == NULL) ? 0 : no->nivel;
   }

/***

	Classe AVL

***/

/*
 * Variavel global
 */
No* raiz;

/**
 * Criar arvore binaria.
 */
void start() {
   raiz = NULL;
}

/**
 * Metodo publico iterativo para pesquisar elemento.
 * @param x Elemento que sera procurado.
 * @return <code>true</code> se o elemento existir,
 * <code>false</code> em caso contrario.
 */
bool pesquisar(char x[]) {

	printf("%s raiz ",x);
   return pesquisarRec(x, raiz);
}

/**
 * Metodo privado recursivo para pesquisar elemento.
 * @param x Elemento que sera procurado.
 * @param i No em analise.
 * @return <code>true</code> se o elemento existir,
 * <code>false</code> em caso contrario.
 */
bool pesquisarRec(char x[], No* i) {
   bool resp;
   if (i == NULL) {
      resp = false;
	  comp++;
   } else if (strcmp(x,i->elemento.nome)==0) {
      resp = true;

   } else if (strcmp(x,i->elemento.nome) < 0) {
   	  comp++;
   	  printf("esq ");
      resp = pesquisarRec(x, i->esq);

   } else {
   	  comp++;
   	  printf("dir ");
      resp = pesquisarRec(x, i->dir);
   }
   return resp;
}

/**
 * Metodo publico iterativo para exibir elementos.
 */
void caminharCentral() {
   printf("[ ");
   caminharCentralRec(raiz);
   printf("]\n");
}

/**
 * Metodo privado recursivo para exibir elementos.
 * @param i No em analise.
 */
void caminharCentralRec(No* i) {
   if (i != NULL) {
      caminharCentralRec(i->esq);
      printf("%s ", i->elemento.nome);
      caminharCentralRec(i->dir);
   }
}

/**
 * Metodo publico iterativo para exibir elementos.
 */
void caminharPre() {
   printf("[ ");
   caminharPreRec(raiz);
   printf("]\n");
}

/**
 * Metodo privado recursivo para exibir elementos.
 * @param i No em analise.
 */
void caminharPreRec(No* i) {
   if (i != NULL) {
      printf("%s ", i->elemento.nome);
      caminharPreRec(i->esq);
      caminharPreRec(i->dir);
   }
}

/**
 * Metodo publico iterativo para exibir elementos.
 */
void caminharPos() {
   printf("[ ");
   caminharPosRec(raiz);
   printf("]\n");
}

/**
 * Metodo privado recursivo para exibir elementos.
 * @param i No em analise.
 */
void caminharPosRec(No* i) {
   if (i != NULL) {
      caminharPosRec(i->esq);
      caminharPosRec(i->dir);
      printf("%s ", i->elemento.nome);
   }
}

void inserir(PJogador x) {
   inserirRec(x, &raiz);
}

/**
 * Metodo privado recursivo para inserir elemento.
 * @param x Elemento a ser inserido.
 * @param i No** endereco do ponteiro No
 */
void inserirRec(PJogador x, No** i) {
   if (*i == NULL) {
      *i = novoNo(x);

   } else if (strcmp(x->nome , (*i)->elemento.nome) < 0) {
      inserirRec(x, &((*i)->esq));

   } else if (strcmp(x->nome , (*i)->elemento.nome) > 0) {
      inserirRec(x, &((*i)->dir));

   } else {
      errx(1, "Erro ao inserir!");
   }
   
   *i = balancear(*i);
}

/**
 * Metodo publico iterativo para remover elemento.
 * @param x Elemento a ser removido.
 */
void remover(char x[]) {
   removerRec(x, &raiz);
}

/**
 * Metodo privado recursivo para remover elemento.
 * @param x Elemento a ser removido.
 * @param i No** endereco do ponteiro No
 */
void removerRec(char x[], No** i) {
   if (*i == NULL) {
      errx(1, "Erro ao remover!");

   } else if (strcmp(x , (*i)->elemento.nome) < 0) {
      removerRec(x, &((*i)->esq));

   } else if (strcmp(x , (*i)->elemento.nome) > 0) {
      removerRec(x, &((*i)->dir));

   } else if ((*i)->dir == NULL) {
      No* del = *i;
      *i = (*i)->esq;
      free(del);

   } else if ((*i)->esq == NULL) {
      No* del = *i;
      *i = (*i)->dir;
      free(del);

   } else {
      antecessor(i, &((*i)->esq));
   }
   
   *i =balancear(*i);
}

/**
 * Metodo para trocar no removido pelo antecessor.
 * @param i No** endereco do ponteiro No que contem o elemento removido.
 * @param j No** endereco do ponteiro No da subarvore esquerda.
 */
void antecessor(No** i, No** j) {
   if ((*j)->dir != NULL) {
      antecessor(i, &((*j)->dir));

   } else {
      No* del = *j;
      (*i)->elemento = clone(&(*j)->elemento);
      (*j) = (*j)->esq;
      free(del);
   }
}


/**
 * Metodo publico iterativo para remover elemento.
 * @param x Elemento a ser removido.
 */
void removerSucessor(char x[]) {
   removerRecSucessor(x, &raiz);
}

/**
 * Metodo privado recursivo para remover elemento.
 * @param x Elemento a ser removido.
 * @param i No** endereco do ponteiro No
 */
void removerRecSucessor(char x[], No** i) {
   if (*i == NULL) {
      errx(1, "Erro ao remover!");

   } else if (strcmp(x , (*i)->elemento.nome) < 0) {
      removerRec(x, &((*i)->esq));

   } else if (strcmp(x , (*i)->elemento.nome) > 0) {
      removerRec(x, &((*i)->dir));

   } else if ((*i)->dir == NULL) {
      No* del = *i;
      *i = (*i)->esq;
      free(del);

   } else if ((*i)->esq == NULL) {
      No* del = *i;
      *i = (*i)->dir;
      free(del);

   } else {
      sucessor(i, &((*i)->dir));
   }
}

/**
 * Metodo para trocar no removido pelo sucessor.
 * @param i No** endereco do ponteiro No que contem o elemento removido.
 * @param j No** endereco do ponteiro No da subarvore esquerda.
 */
void sucessor(No** i, No** j) {
   if ((*j)->esq != NULL) {
      sucessor(i, &((*j)->esq));

   } else {
      No* del = *j;
      (*i)->elemento = clone(&(*j)->elemento);
      (*j) = (*j)->dir;
      free(del);
   }
}


No* balancear(No* no){
      if(no != NULL){
      
      		//printf("testeDir = %d",getNivel(no->dir));
      		//printf("testeEsq = %d",getNivel(no->esq));
      		
         	int fator =getNivel(no->dir) - getNivel(no->esq);
         	//printf("FATOR==%d\n\n",fator);
		 
		     //Se balanceada
		     if (abs(fator) <= 1){
		        setNivel(no);

		     //Se desbalanceada para a direita
		     }else if (fator == 2){

		        int fatorFilhoDir = getNivel(no->dir->dir) - getNivel(no->dir->esq);

		        //Se o filho a direita tambem estiver desbalanceado
		        if (fatorFilhoDir == -1) {
		           no->dir = rotacionarDir(no->dir);
		        }
		        no = rotacionarEsq(no);

		     //Se desbalanceada para a esquerda
		     }else if (fator == -2){

		        int fatorFilhoEsq = getNivel(no->esq->dir) - getNivel(no->esq->esq);

		        //Se o filho a esquerda tambem estiver desbalanceado
		        if (fatorFilhoEsq == 1) {
		           no->esq = rotacionarEsq(no->esq);
		        }
		        no = rotacionarDir(no);

		     }else{
		        errx(1,"Erro fator de balanceamento ( %d ) invalido!",fator ); 
		     }
      
		
		}
   return no;   
}
   
No* rotacionarDir(No* no) {

      
      No* noEsq = no->esq;
      No* noEsqDir = noEsq->dir;

      noEsq->dir = no;
      no->esq = noEsqDir;

      setNivel(no);
      setNivel(noEsq);

      return noEsq;
   }

No* rotacionarEsq(No* no) {
      
      No *noDir = no->dir;
      No *noDirEsq = noDir->esq;

      noDir->esq = no;
      no->dir = noDirEsq;

      setNivel(no);
      setNivel(noDir);
      return noDir;
}




/**
 * Metodo publico iterativo para inserir elemento.
 * @param x Elemento a ser inserido.






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


