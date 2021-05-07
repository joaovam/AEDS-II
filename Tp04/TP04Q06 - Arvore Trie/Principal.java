import java.util.*;

public class Principal{
	   protected static Jogador[] jogadores;
	   public static ArvoreTrie av1;
	   public static ArvoreTrie av2;
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


			Arq.openWrite("698159_arvoreTrie.txt");
			Arq.println("698159\t" + tempo +"ms" + "\t" + comp);
			Arq.close();


		}
		
		
		
		
        


        public static void main(String[] Args)throws Exception{
            int tam = 3922;

            jogadores = new Jogador[tam];//vetor original
			av1 = new ArvoreTrie();
			av2 = new ArvoreTrie();
			
            

            leArquivoInicial(jogadores);
            comp = 0;
            int posicao = 0;
            
            String palavra = MyIO.readString();

            while(!isFim(palavra)){
                posicao = Integer.parseInt(palavra);
                
                MyIO.println(jogadores[posicao].getId() + "\t" + jogadores[posicao].getNome());
                
                av1.inserir(jogadores[posicao].getNome());
                
                palavra = MyIO.readString();
                     
            }
            palavra = MyIO.readString();
            while(!isFim(palavra)){
                posicao = Integer.parseInt(palavra);
                
                MyIO.println(jogadores[posicao].getId() + "\t" + jogadores[posicao].getNome());
                
                av2.inserir(jogadores[posicao].getNome());
                
                palavra = MyIO.readString();
             
            }
            
            av1.merge(av2);
           // MyIO.println("------------------------------------------mostrar");
           // av2.mostrar();
           // MyIO.println("------------------------------------------mostrarFim");
            
            
            palavra = MyIO.readLine();
            boolean resp = false;
            
            while(!isFim(palavra)){
            
                resp = av2.pesquisar(palavra);
                MyIO.println( palavra + ((resp)?" SIM":" NAO"));
                
                palavra = MyIO.readLine();
                     
            }
            
            


            long inicio = now();
            

			
            long fim = now();
            long tempo = fim-inicio;
            
            criaLog(tempo);
            
        }
    }

class No {
   public char elemento;
   public int tamanho = 255;
   public No[] prox;
   public boolean folha;
   
   public No (){
      this(' ');
   }

   public No (char elemento){
      this.elemento = elemento;
      prox = new No [tamanho];
      for (int i = 0; i < tamanho; i++) prox[i] = null;
      folha = false;
   }

   public static int hash (char x){
      return (int)x;
   }
}



/**
 * Arvore binaria de pesquisa
 * @author Max do Val Machado
 */
class ArvoreTrie {
    private No raiz;

    public ArvoreTrie(){
        raiz = new No();
    }

    public void inserir(String s) throws Exception {
        inserir(s, raiz, 0);
    }

    private void inserir(String s, No no, int i) throws Exception {
       // System.out.print("\nEM NO(" + no.elemento + ") (" + i + ")");
        
        if(no.prox[s.charAt(i)] == null){
        
         //   System.out.print("--> criando filho(" + s.charAt(i) + ")");
            no.prox[s.charAt(i)] = new No(s.charAt(i));

            if(i == s.length() - 1){
           //     System.out.print("(folha)");
                no.prox[s.charAt(i)].folha = true;
            }else{
                inserir(s, no.prox[s.charAt(i)], i + 1);
            }

        } else if (no.prox[s.charAt(i)].folha == false && i < s.length() - 1){
            inserir(s, no.prox[s.charAt(i)], i + 1);

        } else {
            throw new Exception("Erro ao inserir!");
        } 
    }


    public boolean pesquisar(String s) throws Exception {
        return pesquisar(s, raiz, 0);
    }

    public boolean pesquisar(String s, No no, int i) throws Exception {
        boolean resp;
        Principal.comp++;
        if(no.prox[s.charAt(i)] == null){
            resp = false;
        } else if(i == s.length() - 1){
        	Principal.comp++;
            resp = (no.prox[s.charAt(i)].folha == true);
        } else if(i < s.length() - 1 ){
			Principal.comp++;
            resp = pesquisar(s, no.prox[s.charAt(i)], i + 1);
        } else {
            throw new Exception("Erro ao pesquisar!");
        }
        return resp;
    }


    public void mostrar(){
        mostrar("", raiz);
    }

    public void mostrar(String s, No no) {
        if(no.folha == true){
            System.out.println("Palavra: " + (s + no.elemento));
        } else {
            for(int i = 0; i < no.prox.length; i++){
                if(no.prox[i] != null){
           //         System.out.println("ESTOU EM (" + no.elemento + ") E VOU PARA (" + no.prox[i].elemento + ")");
                    mostrar(s + no.elemento, no.prox[i]);
                }
            }
        }
    }
    
    public void merge(ArvoreTrie av)throws Exception{
        merge("", raiz,av);
    }

    public void merge(String s, No no,ArvoreTrie av)throws Exception {
        if(no.folha == true){
        	s = s+no.elemento;
        	s = s.replaceFirst(" ","");
        	//MyIO.println("inserindo ao merge: " + s);
            av.inserir(s);
        } else {
            for(int i = 0; i < no.prox.length; i++){
                if(no.prox[i] != null){
          //          System.out.println("ESTOU EM (" + no.elemento + ") E VOU PARA (" + no.prox[i].elemento + ")");
                    merge(s + no.elemento, no.prox[i],av);
                }
            }
        }
    }

    public int contarAs(){
        int resp = 0;
        if(raiz != null){
            resp = contarAs(raiz);
        }
        return resp;
    }

    public int contarAs(No no) {
        int resp = (no.elemento == 'A') ? 1 : 0;

        if(no.folha == false){
            for(int i = 0; i < no.prox.length; i++){
                if(no.prox[i] != null){
                    resp += contarAs(no.prox[i]);
                }
            }
        }
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
