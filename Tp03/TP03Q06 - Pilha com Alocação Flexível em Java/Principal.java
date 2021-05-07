import java.util.*;


    public class Principal{
	   protected static Jogador[] jogadores;
	   protected static Pilha pilha;


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
		
		public static void descobreOperacao(String dado)throws Exception{
			String[] divisao = dado.split(" ",3);
			int posicao = 0;
			if(divisao.length > 1)
				posicao = Integer.parseInt(divisao[1]);
				
			Jogador retorno;
			switch (divisao[0]){
				case "I":
					pilha.empilhar(jogadores[posicao]);
					break;
				
				case "R":
					retorno = pilha.desempilhar();
					MyIO.println("(R) " + retorno.getNome());
					break;
				
			
			
			}
		
		}

        


        public static void main(String[] Args)throws Exception{
            int tam = 3922;

            jogadores = new Jogador[tam];//vetor original
			pilha= new Pilha();
            

            leArquivoInicial(jogadores);
            
            int posicao = 0;
            
            String palavra = MyIO.readString();

            while(!isFim(palavra)){
                posicao = Integer.parseInt(palavra);
                
                pilha.empilhar(jogadores[posicao]);
                
                palavra = MyIO.readString();
                     
            }

            long inicio = now();
            int operacoes = MyIO.readInt();//quantidade de operacoes na Estrutura
            
            for(int i = 0;i<operacoes;i++){
            	String dado = MyIO.readLine();
            
            	descobreOperacao(dado);	
            }
            
          	pilha.mostrar();

        }






    }
    
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

        MyIO.println("## " + getNome() + " ## " +getAltura() + " ## " + getPeso() + " ## " + getAnoNascimento() + " ## " + getUniversidade() + " ## " + getCidadeNascimento() + " ## "+ getEstadoNascimento() +" ##");
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
	public Celula() {
		this.prox = null;
		this.elemento = new Jogador();
	}

	/**
	 * Construtor da classe.
	 * @param elemento int inserido na celula.
	 */
	public Celula(Jogador elemento) {
      this.elemento = elemento.clone();
      this.prox = null;
	}
}

/**
 * Pilha dinamica
 * 
 * @author Max do Val Machado
 * @version 2 01/2015
 */
class Pilha {
	private Celula topo;

	/**
	 * Construtor da classe que cria uma fila sem elementos.
	 */
	public Pilha() {
		topo = null;
	}

	/**
	 * Insere elemento na pilha (politica FILO).
	 * 
	 * @param x int elemento a inserir.
	 */
	public void empilhar(Jogador x) {
		Celula tmp = new Celula(x);
		tmp.prox = topo;
		topo = tmp;
		tmp = null;
	}

	/**
	 * Remove elemento da pilha (politica FILO).
	 * 
	 * @return Elemento removido.
	 * @trhows Exception Se a sequencia nao contiver elementos.
	 */
	public Jogador desempilhar() throws Exception {
		if (topo == null) {
			throw new Exception("Erro ao remover!");
		}
		Jogador resp = topo.elemento.clone();
		Celula tmp = topo;
		topo = topo.prox;
		tmp.prox = null;
		tmp = null;
		return resp;
	}

	/**
	 * Mostra os elementos separados por espacos, comecando do topo.
	 */
	public void mostrar() {
		mostraPilha(topo);
	}

	private int mostraPilha(Celula i) {
		int j = 0;
		if (i != null) {
		
			j = mostraPilha(i.prox);
			MyIO.print("["+j+"] ");
			j++;
			i.elemento.imprimir();
		}
		return j;
	}

}

















    






