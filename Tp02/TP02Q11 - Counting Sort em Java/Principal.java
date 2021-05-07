import java.util.*;

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

        MyIO.println("[" + getId() + " ## " + getNome() + " ## " +getAltura() + " ## " + getPeso() + " ## " + getAnoNascimento() + " ## " + getUniversidade() + " ## " + getCidadeNascimento() + " ## "+ getEstadoNascimento() +"]");
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
    public void  clone(Jogador resultado){
        resultado.setId(getId());
        resultado.setNome(getNome());
        resultado.setAltura(getAltura());
        resultado.setPeso(getPeso());
        resultado.setUniversidade(getUniversidade());
        resultado.setAnoNascimento(getAnoNascimento());
        resultado.setCidadeNascimento(getCidadeNascimento());
        resultado.setEstadoNascimento(getEstadoNascimento());

        

    }      
    
    /**
    Classe Principal: Coordena as informações e a classe acima
    
    **/  

    }
    public class Principal{
	   protected static Jogador[] jogadores;
	   protected static Jogador[] novoVetor;
	   protected static int comp;

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
        
        public static void criaLog(long tempo){


			Arq.openWrite("698159_countingsort.txt");
			Arq.println("698159\t" + tempo +"ms" + "\t" + comp);
			Arq.close();


		}
        
        public static void SelecionaNovoVetor(int id, Jogador Novo){
        
        	jogadores[id].clone(Novo);
        
        
        }
        public static boolean isFim(String palavra){

            return palavra.length() == 3 && palavra.charAt(0)=='F' && palavra.charAt(1)=='I' && palavra.charAt(2)=='M';
        }
        
        /**
        ordena subArray
        @param numero de posicoes no array
        **/
        
      	public static void countingsort(int n) {
     		 //Array para contar o numero de ocorrencias de cada elemento
     		 int[] count = new int[getMaior(n) + 1];
     		 Jogador[] ordenado = new Jogador[n];

     		 //Inicializar cada posicao do array de contagem 
	 		 for (int i = 0; i < count.length; count[i] = 0, i++);
	
     		 //Agora, o count[i] contem o numero de elemento iguais a i
     		 for (int i = 0; i < n; count[novoVetor[i].getAltura()]++, i++);

     		 //Agora, o count[i] contem o numero de elemento menores ou iguais a i
     		 for(int i = 1; i < count.length; count[i] += count[i-1], i++);
     		 
     		 //inicializando posições do array ordenado
     		 for(int i = 0;i<n;i++)
     		 	ordenado[i] = new Jogador();
     		 
     		 //Ordenando
     		 for(int i = n-1; i >= 0; novoVetor[i].clone(ordenado[count[novoVetor[i].getAltura()]-1]), count[novoVetor[i].getAltura()]--, i--);

     		 //Copiando para o array original
     		 for(int i = 0; i < n;ordenado[i].clone(novoVetor[i]), i++);
     		 
     		 //fazendo adequacoes para nomes//utilizado insercao por o array ja estar quase ordenado
     		 insercao(n);
     		 
   		}
   		
   		public static void insercao(int n) {
        	Jogador tmp = new Jogador();
			for (int i = 1; i < n; i++) {
				novoVetor[i].clone(tmp);
	         	int j = i - 1;

	         while (comparaJogadores(j,tmp)){
	         
	            novoVetor[j].clone(novoVetor[j + 1]);
	            j--;
	         }
	         tmp.clone(novoVetor[j+1]);
	      }
		}
		
   		
   		public static boolean comparaJogadores(int j,Jogador tmp){
        
        	
        	return (j >= 0)&&(novoVetor[j].getAltura()==tmp.getAltura()&&novoVetor[j].getNome().compareTo(tmp.getNome())>0);
        
        }
   		
   		
   		


		/**
	 	* Retorna o maior elemento do array.
    	* @return maior elemento
	 	*/
		public static int getMaior(int n) {
	   		int maior = novoVetor[0].getAltura();

			for (int i = 0; i < n; i++) {
    	    	if(maior < novoVetor[i].getAltura()){
    	        	maior = novoVetor[i].getAltura();
    	     	}
			}
		    return maior;	
		}
        
   		
   		
   		public static long now(){


			return new Date().getTime();

		}

        


        public static void main(String[] Args){
            int tam = 3922;

            jogadores = new Jogador[tam];//vetor original
            novoVetor = new Jogador[tam];//vetor subsequente
            
            comp = 0;//numero de comparacoes


            leArquivoInicial(jogadores);
            String palavra = MyIO.readString();
            int posicao = 0;
            int n = 0;

            while(!isFim(palavra)){
                posicao = Integer.parseInt(palavra);
                
                novoVetor[n] = new Jogador();
                SelecionaNovoVetor(posicao,novoVetor[n++]);
                
                palavra = MyIO.readString();
            }
            long inicio = now();
            countingsort(n);
			long fim = now();
			long tempo = (fim - inicio);
			criaLog(tempo);
			for(int i = 0;i<n;i++){
			
				novoVetor[i].imprimir();
			
			}



        }






    }





















    






