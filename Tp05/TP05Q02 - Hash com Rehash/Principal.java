import java.util.*;

public class Principal{
	   protected static Jogador[] jogadores;
	   public static Hash hash;
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


			Arq.openWrite("698159_hashRehash.txt");
			Arq.println("698159\t" + tempo +"ms" + "\t" + comp);
			Arq.close();


		}
		
		
		
		
        


        public static void main(String[] Args)throws Exception{
            int tam = 3922;

            jogadores = new Jogador[tam];//vetor original
			hash = new Hash();
            

            leArquivoInicial(jogadores);
            comp = 0;
            int posicao = 0;
            
            String palavra = MyIO.readString();

            while(!isFim(palavra)){
                posicao = Integer.parseInt(palavra);
                
                hash.inserir(jogadores[posicao]);
                
                palavra = MyIO.readString();
                     
            }


            long inicio = now();
           // hash.mostrar();
            
            palavra = MyIO.readLine();
            boolean resp = false;

			while(!isFim(palavra)){
			
				resp = hash.pesquisar(palavra);
				MyIO.println((resp)?"SIM":"NAO");
				palavra = MyIO.readLine();
			}
			
            long fim = now();
            long tempo = fim-inicio;
            
            criaLog(tempo);
            
        }
    }
    
    /**
    	Classe Hash com rehash adapatada para jogadores
    **/

class Hash {
   Jogador tabela[];
   int m;
   

   public Hash (){
      this(21);
   }

   public Hash (int m){
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
      
      //MyIO.println("a ser inserido-----------");
      //elemento.imprimir();
      

      if(elemento != null){

         int pos = h(elemento.getAltura());

         if(tabela[pos] == null){
            tabela[pos] = elemento.clone();
            //MyIO.println("inseri por hash:");
            //elemento.imprimir();
            
            resp = true;

         } else{

            pos = reh(elemento.getAltura());

            if(tabela[pos] == null){
               // MyIO.println("inseri por Rehash:");
            	//elemento.imprimir();
               tabela[pos] = elemento.clone();
               resp = true;
            }
         }
      }
      

      return resp;
   }
   
   public void mostrar(){
      
      	MyIO.println("Mostrar---------------------------------");
      	for(int i = 0;i < m;i++){
      		if(tabela[i]!=null){
      			MyIO.print(i);
      			tabela[i].imprimir();
      			
      		}
      		
      	}
      	MyIO.println("Mostrar---------------------------------");
      	
      
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
   		MyIO.print(elemento + " ");
   		boolean resp = false;
		Principal.comp++;   		
   		for(int i = 0 ;i < m ; i++ ){
   		
   			if(tabela[i]!=null && tabela[i].getNome().compareTo(elemento)==0){
   			
   				resp = true;i = m;
   			
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
