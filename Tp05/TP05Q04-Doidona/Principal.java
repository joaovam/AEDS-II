import java.util.*;

public class Principal{
	   protected static Jogador[] jogadores;
	   public static Doidona doidona;
	   public static int comp = 0;


        /**
         *@param vetor vazio

         metodo le o arquivo e preenche o vetor
         * */
        public static void leArquivoInicial(Jogador[] jogadores){

            Arq.openRead("/tmp/players.csv");
            String dados = "";
            int i = 0;

            Arq.readLine();//remove linha de cabecalho
            


            while(i<3922){
                dados = Arq.readLine();
                jogadores[i] = new Jogador();
                jogadores[i].ler(dados);

                i++;
            }
        }
        
        
        
        public static void SelecionaNovoVetor(int id, Jogador Novo){
        
        	Novo = jogadores[id].clone();
        
        
        }
        public static boolean isFim(String palavra){

            return palavra.length() == 3 && palavra.charAt(0)=='F' && palavra.charAt(1)=='I' && palavra.charAt(2)=='M';
        }
        
        
        
   		
   		
   		public static long now(){


			return new Date().getTime();

		}
		public static void criaLog(long tempo){


			Arq.openWrite("698159_Doidona.txt");
			Arq.println("698159\t" + tempo +"ms" + "\t" + comp);
			Arq.close();


		}
		
		
		
		
        


        public static void main(String[] Args)throws Exception{
            int tam = 3922;

            jogadores = new Jogador[tam];//vetor original
			doidona = new Doidona();
            

            leArquivoInicial(jogadores);
            comp = 0;
            int posicao = 0;
            
            String palavra = MyIO.readString();

            while(!isFim(palavra)){
                posicao = Integer.parseInt(palavra);
                
                doidona.inserir(jogadores[posicao]);
                
                palavra = MyIO.readString();
                     
            }


            long inicio = now();
            
            palavra = MyIO.readLine();
            boolean resp = false;

			while(!isFim(palavra)){
			
				resp = doidona.pesquisar(palavra);
				MyIO.println((resp)?"SIM":"NAO");
				palavra = MyIO.readLine();
			}
			//doidona.mostrar();
			
            long fim = now();
            long tempo = fim-inicio;
            
            criaLog(tempo);
            
        }
    }
    
class Doidona{
	
	Hash T1;
	HashRe T2;
	Lista lista;
	ArvoreBinaria av;
	
	
	public Doidona(){
		
		T1 = new Hash();
		T2 = new HashRe();
		lista = new Lista();
		av = new ArvoreBinaria();
		
	
	}
	
	public int reserva(int x){
		return x % 3;
	
	}
	
	public boolean inserir(Jogador x)throws Exception{
		//MyIO.println("================Inserindo: " + x.getNome()+" ===================");
		boolean resp = false;
		if(T1.inserir(x)){
		//	MyIO.println("T1");
			resp = true;
		
		}else{
			switch(reserva(x.getAltura())){
			
				case 0:
					T2.inserir(x);
		//			MyIO.println("T2");
					break;
				case 1:
					lista.inserirFim(x);
		//			MyIO.println("LISTA");
					break;
				case 2:
					av.inserir(x);
		//			MyIO.println("AV");
					break;
			}                   
		}
		
		return resp;
		
	}
	
	public boolean pesquisar(String x){
		boolean resp = true;
		MyIO.print(x + " ");
	
		if(T1.pesquisar(x)){
		}else if(T2.pesquisar(x)){
		}else if(lista.pesquisar(x)){
		}else if(av.pesquisar(x)){
		}else{
		
			resp = false;

		}
	
		return resp;
	}
	
	public void mostrar(){
	
		MyIO.println("-------------------T1---------------------");
		T1.mostrar();
		MyIO.println("-------------------T2---------------------");
		T2.mostrar();
		MyIO.println("-------------------LISTA------------------");
		lista.mostrar();
		MyIO.println("-------------------AV---------------------");
		av.caminharCentral();
	
	
	}

}
    
    
    
     /**
    	Classe Hash adapatada para jogadores
    **/

class Hash {
   Jogador tabela[];
   int m1, m2, m, reserva;

   public Hash (){
      this(11, 0);
   }

   public Hash (int m1, int m2){
      this.m1 = m1;
      this.m2 =  m2;
      this.m = m1 + m2;
      this.tabela = new Jogador [this.m];
      for(int i = 0; i < m; i++){
         tabela[i] = null;
      }
      reserva  = 0;
   }

   public int h(int elemento){
      return elemento % m1;
   }

   public boolean inserir (Jogador elemento){
      boolean resp = false;

		//MyIO.println("==============Jogador: "+elemento.getNome());

      if(elemento != null){

         int pos = h(elemento.getAltura());
        // mostrar();
         //MyIO.println(pos);

         if(tabela[pos] == null){
            tabela[pos] = elemento.clone();
            resp = true;

         } else if (reserva < m2){
            tabela[m1 + reserva] = elemento.clone();
            reserva++;
            resp = true;
         }else{
         
         	//MyIO.println("ENTREI AQUI");
         
         	resp = false;
         }
      }

      return resp;
   }


   public boolean pesquisar (int elemento){
      boolean resp = false;

      int pos = h(elemento);

      if(tabela[pos].getAltura() == elemento){
         resp = true;

      } else {
         for(int i = 0; i < reserva; i++){
            if(tabela[m1 + i].getAltura() == elemento){
               resp = true;
               i = reserva;
            }
         }
      }
      return resp;
   }
   
   public boolean pesquisar(String elemento){
   	
   		boolean resp = false;
   
   		Principal.comp++;
   		for(int i = 0;i< m;i++){
   		
   			if(tabela[i] != null && tabela[i].getNome().compareTo(elemento)==0){
   			
   				resp = true;
   				i = m;
   			}
   			
   			Principal.comp++;
   		}
   
   		return resp;
   }

   boolean remover (int elemento){
      boolean resp = false;

      //...

      return resp;
   }
   void mostrar(){
   
   		for(int i = 0; i < m;i++){
   		
   			if(tabela[i]!=null){
   				MyIO.print("Pos = " + i + "  ");
   				tabela[i].imprimir();
   			}
   		
   		}
   }
}


    
    /**
    	Classe Hash com rehash adapatada para jogadores
    **/

class HashRe {
   Jogador tabela[];
   int m;
   

   public HashRe (){
      this(9);
   }

   public HashRe (int m){
      this.m = m;
      this.tabela = new Jogador [this.m];
      for(int i = 0; i < m; i++){
         tabela[i] = null;
      }
   }

   public int h(int elemento){
      return elemento % m;
   }

   public int reh(int elemento){
      return ++elemento % m;
   }

   public boolean inserir (Jogador elemento){
      boolean resp = false;

      if(elemento != null){

         int pos = h(elemento.getAltura());

         if(tabela[pos] == null){
            tabela[pos] = elemento.clone();
            resp = true;

         } else{

            pos = reh(elemento.getAltura());

            if(tabela[pos] == null){
               tabela[pos] = elemento.clone();
               resp = true;
            }
         }
      }

      return resp;
   }

   public boolean pesquisar (int elemento){
      boolean resp = false;

      int pos = h(elemento);

      if(tabela[pos].getAltura() == elemento){
         resp = true;

      } else {
         pos = reh(elemento);

         if(tabela[pos].getAltura() == elemento){
            resp = true;
         }
      }
      return resp;
   }
   
   public boolean pesquisar(String elemento){
   
   		boolean resp = false;
   
   		for(int i = 0;i < m;i++){
   			Principal.comp++;
   			if(tabela[i]!=null && tabela[i].getNome().compareTo(elemento) == 0){
   				resp = true;
   				i = m;
   			
   			}
   				
   		}
   		return resp;
   
   }

   boolean remover (int elemento){
      boolean resp = false;

      //...

      return resp;
   }
   
   void mostrar(){
   
   		for(int i = 0 ; i < m ; i++ ){
   		
   			if(tabela[i]!=null)
   				tabela[i].imprimir();
   		
   		}
   
   }
}

/**
 * No da arvore binaria
 * @author Max do Val Machado
 */
class No {
	public Jogador elemento; // Conteudo do no.
	public No esq, dir;  // Filhos da esq e dir.

	/**
	 * Construtor da classe.
	 * @param elemento Conteudo do no.
	 */
	public No(Jogador elemento) {
		this(elemento, null, null);
	}

	/**
	 * Construtor da classe.
	 * @param elemento Conteudo do no.
	 * @param esq No da esquerda.
	 * @param dir No da direita.
	 */
	public No(Jogador elemento, No esq, No dir) {
		this.elemento = elemento.clone();
		this.esq = esq;
		this.dir = dir;
	}
}



/**
 * Arvore binaria de pesquisa
 * @author Max do Val Machado
 */
class ArvoreBinaria {
	private No raiz; // Raiz da arvore.

	/**
	 * Construtor da classe.
	 */
	public ArvoreBinaria() {
		raiz = null;
	}

	/**
	 * Metodo publico iterativo para pesquisar elemento.
	 * @param x Elemento que sera procurado.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	public boolean pesquisar(String x) {

		
		MyIO.print("raiz ");
		
		
		return pesquisar(x, raiz);

	}

	/**
	 * Metodo privado recursivo para pesquisar elemento.
	 * @param x Elemento que sera procurado.
	 * @param i No em analise.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	private boolean pesquisar(String x, No i) {
      boolean resp;
      	Principal.comp++;
		if (i == null) {
         resp = false;

      } else if (x.compareTo(i.elemento.getNome()) == 0) {
         resp = true;

      } else if (x.compareTo(i.elemento.getNome()) < 0) {
      	 Principal.comp++;
	      MyIO.print("esq ");
         resp = pesquisar(x, i.esq);

      } else {
      	MyIO.print("dir ");
         resp = pesquisar(x, i.dir);
      }
      return resp;
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public void caminharCentral() {
		System.out.print("[ ");
		caminharCentral(raiz);
		System.out.println("]");
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param i No em analise.
	 */
	private void caminharCentral(No i) {
		if (i != null) {
			caminharCentral(i.esq); // Elementos da esquerda.
			i.elemento.imprimir(); // Conteudo do no.
			caminharCentral(i.dir); // Elementos da direita.
		}
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public void caminharPre() {
		System.out.print("[ ");
		caminharPre(raiz);
		System.out.println("]");
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param i No em analise.
	 */
	private void caminharPre(No i) {
		if (i != null) {
			i.elemento.imprimir(); // Conteudo do no.
			caminharPre(i.esq); // Elementos da esquerda.
			caminharPre(i.dir); // Elementos da direita.
		}
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public void caminharPos() {
		System.out.print("[ ");
		caminharPos(raiz);
		System.out.println("]");
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param i No em analise.
	 */
	private void caminharPos(No i) {
		if (i != null) {
			caminharPos(i.esq); // Elementos da esquerda.
			caminharPos(i.dir); // Elementos da direita.
			i.elemento.imprimir(); // Conteudo do no.
		}
	}


	/**
	 * Metodo publico iterativo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir(Jogador x) throws Exception {
		
		raiz = inserir(x, raiz);
	}

	/**
	 * Metodo privado recursivo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @param i No em analise.
	 * @return No em analise, alterado ou nao.
	 * @throws Exception Se o elemento existir.
	 */
	private No inserir(Jogador x, No i) throws Exception {
		if (i == null) {
         i = new No(x);

      } else if (x.getNome().compareTo(i.elemento.getNome()) < 0) {
         i.esq = inserir(x, i.esq);

      } else if (x.getNome().compareTo(i.elemento.getNome()) > 0) {
         i.dir = inserir(x, i.dir);

      }//else{    		
      		
         //throw new Exception("Erro ao inserir!");
      //}

		return i;
	}

	/**
	 * Metodo publico para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserirPai(Jogador x) throws Exception {
      if(raiz == null){
         raiz = new No(x);
      } else if(x.getNome().compareTo(raiz.elemento.getNome()) < 0){
		   inserirPai(x, raiz.esq, raiz);
      } else if(x.getNome().compareTo(raiz.elemento.getNome()) > 0){
		   inserirPai(x, raiz.dir, raiz);
      } else {
         throw new Exception("Erro ao inserirPai!");
      }
	}

	/**
	 * Metodo privado recursivo para inserirPai elemento.
	 * @param x Elemento a ser inserido.
	 * @param i No em analise.
	 * @param pai No superior ao em analise.
	 * @throws Exception Se o elemento existir.
	 */
	private void inserirPai(Jogador x, No i, No pai) throws Exception {
		if (i == null) {
         if(x.getNome().compareTo(i.elemento.getNome()) < 0){
            pai.esq = new No(x);
         } else {
            pai.dir = new No(x);
         }
      } else if (x.getNome().compareTo(i.elemento.getNome()) < 0) {
         inserirPai(x, i.esq, i);
      } else if (x.getNome().compareTo(i.elemento.getNome()) > 0) {
         inserirPai(x, i.dir, i);
      } else {
         throw new Exception("Erro ao inserirPai!");
      }
	}


	/**
	 * Metodo publico iterativo para remover elemento.
	 * @param x Elemento a ser removido.
	 * @throws Exception Se nao encontrar elemento.
	 */
	public void remover(Jogador x) throws Exception {
		raiz = remover(x, raiz);
	}

	/**
	 * Metodo privado recursivo para remover elemento.
	 * @param x Elemento a ser removido.
	 * @param i No em analise.
	 * @return No em analise, alterado ou nao.
	 * @throws Exception Se nao encontrar elemento.
	 */
	private No remover(Jogador x, No i) throws Exception {

		if (i == null) {
         throw new Exception("Erro ao remover!");

      } else if (x.getNome().compareTo(i.elemento.getNome()) < 0) {
         i.esq = remover(x, i.esq);

      } else if (x.getNome().compareTo(i.elemento.getNome()) > 0) {
         i.dir = remover(x, i.dir);

      // Sem no a direita.
      } else if (i.dir == null) {
         i = i.esq;

      // Sem no a esquerda.
      } else if (i.esq == null) {
         i = i.dir;

      // No a esquerda e no a direita.
      } else {
         i.esq = antecessor(i, i.esq);
		}

		return i;
	}

	/**
	 * Metodo para trocar no removido pelo antecessor.
	 * @param i No que teve o elemento removido.
	 * @param j No da subarvore esquerda.
	 * @return No em analise, alterado ou nao.
	 */
	private No antecessor(No i, No j) {

      // Existe no a direita.
		if (j.dir != null) {
         // Caminha para direita.
			j.dir = antecessor(i, j.dir);

      // Encontrou o maximo da subarvore esquerda.
		} else {
			i.elemento = j.elemento.clone(); // Substitui i por j.
			j = j.esq; // Substitui j por j.ESQ.
		}
		return j;
	}

	/**
	 * Metodo publico iterativo para remover elemento.
	 * @param x Elemento a ser removido.
	 * @throws Exception Se nao encontrar elemento.
	 */
	public void remover2(Jogador x) throws Exception {
      if (raiz == null) {
         throw new Exception("Erro ao remover2!");
      } else if(x.getNome().compareTo(raiz.elemento.getNome()) < 0){
         remover2(x, raiz.esq, raiz);
      } else if (x.getNome().compareTo(raiz.elemento.getNome())>0){
         remover2(x, raiz.dir, raiz);
      } else if (raiz.dir == null) {
         raiz = raiz.esq;
      } else if (raiz.esq == null) {
         raiz = raiz.dir;
      } else {
         raiz.esq = antecessor(raiz, raiz.esq);
      }
   }

	/**
	 * Metodo privado recursivo para remover elemento.
	 * @param x Elemento a ser removido.
	 * @param i No em analise.
	 * @param pai do No em analise.
	 * @throws Exception Se nao encontrar elemento.
	 */
	private void remover2(Jogador x, No i, No pai) throws Exception {
		if (i == null) {
         throw new Exception("Erro ao remover2!");
      } else if (x.getNome().compareTo(i.elemento.getNome()) < 0) {
         remover2(x, i.esq, i);
      } else if (x.getNome().compareTo(i.elemento.getNome()) > 0) {
         remover2(x, i.dir, i);
      } else if (i.dir == null) {
         pai = i.esq;
      } else if (i.esq == null) {
         pai = i.dir;
      } else {
         i.esq = antecessor(i, i.esq);
		}
	}

   public Jogador getRaiz() throws Exception {
      return raiz.elemento;
   }

   public static boolean igual (ArvoreBinaria a1, ArvoreBinaria a2){
      return igual(a1.raiz, a2.raiz);
   }

   private static boolean igual (No i1, No i2){
      boolean resp;
      if(i1 != null && i2 != null){
         resp = (i1.elemento.getId() == i2.elemento.getId()) && igual(i1.esq, i2.esq) && igual(i1.dir, i2.dir);
      } else if(i1 == null && i2 == null){
         resp = true;
      } else {
         resp = false; 
      }
      return resp;
   }

   public int soma(){
      return soma(raiz);
   }

   public int soma(No i){
      int resp = 0;
      if(i != null){
         resp = i.elemento.getId() + soma(i.esq) + soma(i.dir);
      }
      return resp;
   }

   public int quantidadePares(){
      return quantidadePares(raiz);
   }

   public int quantidadePares(No i){
      int resp = 0;
      if(i != null){
         resp = ((i.elemento.getId() % 2 == 0) ? 1 : 0) + quantidadePares(i.esq) + quantidadePares(i.dir);
      }
      return resp;
   }

   public boolean hasDiv11(){
      return hasDiv11(raiz);
   }

   public boolean hasDiv11(No i){
      boolean resp = false;
      if(i != null){
         resp = (i.elemento.getId() % 11 == 0) || hasDiv11(i.esq) || hasDiv11(i.dir);
      }
      return resp;
   }
}

/**
 * Celula (pilha, lista e fila dinamica)
 * @author Max do Val Machado
 * @version 2 01/2015
 */
class Celula {
	public Jogador elemento; // Elemento inserido na celula.
	public Celula prox; // Aponta a celula prox.


	/**
	 * Construtor da classe.
	 */

	/**
	 * Construtor da classe.
	 * @param elemento int inserido na celula.
	 */
	 
	 public Celula(){

	 	Jogador elemento = new Jogador();
	 	
	 	
	 	
	 
	 }

	public Celula(Jogador x){
	
		 this.prox = null;
		 elemento = x.clone();	
	}
}

/**
 * Lista dinamica
 * @author Max do Val Machado
 * @version 2 01/2015
 */
class Lista {
	private Celula primeiro;
	private Celula ultimo;


	/**
	 * Construtor da classe que cria uma lista sem elementos (somente no cabeca).
	 */
	public Lista() {
		primeiro = new Celula();
		ultimo = primeiro;
	}


	/**
	 * Insere um elemento na primeira posicao da lista.
    * @param x int elemento a ser inserido.
	 */
	public void inserirInicio(Jogador x) {
		Celula tmp = new Celula(x);
      tmp.prox = primeiro.prox;
		primeiro.prox = tmp;
		if (primeiro == ultimo) {                 
			ultimo = tmp;
		}
      tmp = null;
	}


	/**
	 * Insere um elemento na ultima posicao da lista.
    * @param x int elemento a ser inserido.
	 */
	public void inserirFim(Jogador x) {
		ultimo.prox = new Celula(x);
		ultimo = ultimo.prox;
	}


	/**
	 * Remove um elemento da primeira posicao da lista.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se a lista nao contiver elementos.
	 */
	public Jogador removerInicio() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		}

      Celula tmp = primeiro;
		primeiro = primeiro.prox;
		Jogador resp = primeiro.elemento.clone();
      tmp.prox = null;
      tmp = null;
		return resp;
	}


	/**
	 * Remove um elemento da ultima posicao da lista.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se a lista nao contiver elementos.
	 */
	public Jogador removerFim() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		} 

		// Caminhar ate a penultima celula:
      Celula i;
      for(i = primeiro; i.prox != ultimo; i = i.prox);

      Jogador resp = ultimo.elemento.clone(); 
      ultimo = i; 
      i = ultimo.prox = null;
      
		return resp;
	}


	/**
    * Insere um elemento em uma posicao especifica considerando que o 
    * primeiro elemento valido esta na posicao 0.
    * @param x int elemento a ser inserido.
	 * @param pos int posicao da insercao.
	 * @throws Exception Se <code>posicao</code> invalida.
	 */
   public void inserir(Jogador x, int pos) throws Exception {

      int tamanho = tamanho();

      if(pos < 0 || pos > tamanho){
			throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tamanho + ") invalida!");
      } else if (pos == 0){
         inserirInicio(x);
      } else if (pos == tamanho){
         inserirFim(x);
      } else {
		   // Caminhar ate a posicao anterior a insercao
         Celula i = primeiro;
         for(int j = 0; j < pos; j++, i = i.prox);
		
         Celula tmp = new Celula(x);
         tmp.prox = i.prox;
         i.prox = tmp;
         tmp = i = null;
      }
   }


	/**
    * Remove um elemento de uma posicao especifica da lista
    * considerando que o primeiro elemento valido esta na posicao 0.
	 * @param posicao Meio da remocao.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se <code>posicao</code> invalida.
	 */
	public Jogador remover(int pos) throws Exception {
      Jogador resp;
      int tamanho = tamanho();

		if (primeiro == ultimo){
			throw new Exception("Erro ao remover (vazia)!");

      } else if(pos < 0 || pos >= tamanho){
			throw new Exception("Erro ao remover (posicao " + pos + " / " + tamanho + " invalida!");
      } else if (pos == 0){
         resp = removerInicio();
      } else if (pos == tamanho - 1){
         resp = removerFim();
      } else {
		   // Caminhar ate a posicao anterior a insercao
         Celula i = primeiro;
         for(int j = 0; j < pos; j++, i = i.prox);
		
         Celula tmp = i.prox;
         resp = tmp.elemento.clone();
         i.prox = tmp.prox;
         tmp.prox = null;
         i = tmp = null;
      }

		return resp;
	}

	/**
	 * Mostra os elementos da lista separados por espacos.
	 */
	public void mostrar() {

		int j = 0;
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			MyIO.print("["+j+"] ");
			i.elemento.imprimir();
			j++;
		}

	}

	/**
	 * Procura um elemento e retorna se ele existe.
	 * @param x Elemento a pesquisar.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	public boolean pesquisar(Jogador x) {
		boolean resp = false;
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
         if(i.elemento.getId() == x.getId()){
            resp = true;
            i = ultimo;
         }
		}
		return resp;
	}
	
	public boolean pesquisar(String x){
	
		boolean resp = false;
		for(Celula i = primeiro.prox ; i != null;i = i.prox){
			Principal.comp++;
			if(i.elemento.getNome().compareTo(x)==0){
				
				resp = true;
				i = ultimo;
			}
		
		}
		
		return resp;
	}

	/**
	 * Calcula e retorna o tamanho, em numero de elementos, da lista.
	 * @return resp int tamanho
	 */
   public int tamanho() {
      int tamanho = 0; 
      for(Celula i = primeiro; i != ultimo; i = i.prox, tamanho++);
      return tamanho;
   }
}


/**
Classe Jogador com metodos proprios

**/
    
class Jogador{

    private int id,
                altura,
                peso,
                anoNascimento;
    private String nome,
                   universidade,
                   cidadeNascimento,
                   estadoNascimento;

    public Jogador(){

    }
    public Jogador(int id, String nome, int altura,int peso,String universidade,int anoNascimento,String cidadeNascimento,String estadoNascimento){

        setId(id);
        setNome(nome);
        setAltura(altura);
        setPeso(peso);
        setUniversidade(universidade);
        setAnoNascimento(anoNascimento);
        setCidadeNascimento(cidadeNascimento);
        setEstadoNascimento(estadoNascimento);
    }
    public int getId(){
        return id;

    }
    public void setId(int id){
        if(id >= 0)
            this.id = id;
    }
    public String getNome(){
        return nome;

    }
    public void setNome(String nome){
        nome = validaDado(nome);

        this.nome = nome;
    }
    public int getAltura(){

        return altura;

    }
    public void setAltura(int altura){

        this.altura = altura;
    }
    public int getPeso(){

        return peso;

    }
    public void setPeso(int peso){

        this.peso = peso;


    }
    public String getUniversidade(){
        return universidade;
        
    }
    public void setUniversidade(String universidade){
        universidade = validaDado(universidade);
        this.universidade = universidade;
    }
    public int getAnoNascimento(){
        return anoNascimento;

    }
    public void setAnoNascimento(int anoNascimento){
        this.anoNascimento = anoNascimento;

    }
    public String getCidadeNascimento(){
        return cidadeNascimento;
        
    }
    public void setCidadeNascimento(String cidadeNascimento){
        cidadeNascimento = validaDado(cidadeNascimento);
        this.cidadeNascimento = cidadeNascimento;    

    }
    public String getEstadoNascimento(){
        return estadoNascimento;
        
    }
    public void setEstadoNascimento(String estadoNascimento){
        estadoNascimento = validaDado(estadoNascimento);
    
        this.estadoNascimento = estadoNascimento;
    }
    public String validaDado(String dado){

        if(dado.isEmpty())
            dado = "nao informado";

        return dado;

    }
    /**
     *Imprime a instancia
     * */

    public void imprimir(){

        MyIO.println("["+getId()+" ## " + getNome() + " ## " +getAltura() + " ## " + getPeso() + " ## " + getAnoNascimento() + " ## " + getUniversidade() + " ## " + getCidadeNascimento() + " ## "+ getEstadoNascimento() +"]");
    }

    public void ler(String jogador){

        String[] valores = jogador.split(",",8);

        setId(valores[0]!="" ?Integer.parseInt(valores[0]): -1);
        setNome(valores[1]);//.replaceAll("\\*",""));
        setAltura(Integer.parseInt(valores[2]));
        setPeso(Integer.parseInt(valores[3]));
        setUniversidade(valores[4]);
        setAnoNascimento(Integer.parseInt(valores[5]));
        setCidadeNascimento(valores[6]);
        setEstadoNascimento(valores[7]);

    }
    public Jogador clone() {
        Jogador novo = new Jogador();
        novo.id = this.id;
        novo.nome = this.nome;
        novo.altura = this.altura;
        novo.peso = this.peso;
        novo.anoNascimento = this.anoNascimento;
        novo.universidade = this.universidade;
        novo.cidadeNascimento = this.cidadeNascimento;
        novo.estadoNascimento = this.estadoNascimento;
        return novo;
    }
    
    

}
