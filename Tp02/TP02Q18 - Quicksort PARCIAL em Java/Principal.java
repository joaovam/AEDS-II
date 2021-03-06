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
    Classe Principal: Coordena as informa????es e a classe acima
    
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


			Arq.openWrite("698159_quicksortParcial.txt");
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
        
      	public static void quicksortParcial(int esq, int dir,int k) {
        	int i = esq, j = dir;
        	Jogador pivo = new Jogador();
        	novoVetor[(dir+esq)/2].clone(pivo);
        	while (i <= j) {
            	while ((novoVetor[i].getEstadoNascimento().compareTo(pivo.getEstadoNascimento())<0) || (novoVetor[i].getEstadoNascimento().compareTo(pivo.getEstadoNascimento())==0 && novoVetor[i].getNome().compareTo(pivo.getNome())<0))i++;
            	
            	while ((novoVetor[j].getEstadoNascimento().compareTo(pivo.getEstadoNascimento())>0) || (novoVetor[j].getEstadoNascimento().compareTo(pivo.getEstadoNascimento())==0 && novoVetor[j].getNome().compareTo(pivo.getNome())>0)) j--;
            	if (i <= j) {
                	swapJogador(i, j);
                	i++;
                	j--;
            	}
        }
        if (esq < j)  quicksortParcial(esq, j,k);
        if (i < k && i < dir)  quicksortParcial(i, dir,k);
    }
   		
   		public static void swapJogador(int menor,int i){
   		
   			Jogador tmp = new Jogador();
   			
   			novoVetor[i].clone(tmp);
   			novoVetor[menor].clone(novoVetor[i]);
   			tmp.clone(novoVetor[menor]);
   		
   		
   		
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
            int k = 10;
            quicksortParcial(0,n-1,k);
			long fim = now();
			long tempo = (fim - inicio);
			criaLog(tempo);
			for(int i = 0;i < k;i++){
			
				novoVetor[i].imprimir();
			
			}



        }






    }





















    






