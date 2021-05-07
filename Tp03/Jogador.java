public class Jogador {
    int id;
    String nome;
    int altura;
    int peso;
    String universidade;
    int anoNascimento;
    String cidadeNascimento;
    String estadoNascimento;

    public Jogador() {
    }

    public Jogador(String linha) {
        String campos[] = linha.split(",");
        this.id = Integer.parseInt(campos[0]);
        this.nome = campos[1];
        this.altura = Integer.parseInt(campos[2]);
        this.peso = Integer.parseInt(campos[3]);
        this.universidade = (campos[4].isEmpty()) ? "nao informado" : campos[4];
        this.anoNascimento = Integer.parseInt(campos[5]);
        if (campos.length > 6) {
            this.cidadeNascimento = (campos[6].isEmpty())? "nao informado": campos[6];
            if (campos.length < 8) {
                this.estadoNascimento = "nao informado";
            } else {
                this.estadoNascimento = campos[7];
            }
        } else {
            this.cidadeNascimento = "nao informado";
            this.estadoNascimento = "nao informado";
        }
    }

    // id,Player,height,weight,collage,born,birth_city,birth_state
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setAnoNascimento(int anoNascimento){
        this.anoNascimento = anoNascimento;
    }

    public int getAnoNascimento(){
        return anoNascimento;
    }

    public String getUniversidade() {
        return universidade;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    public String getCidadeNascimento() {
        return cidadeNascimento;
    }

    public void setCidadeNascimento(String cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }

    public String getEstadoNascimento() {
        return estadoNascimento;
    }

    public void setEstadoNascimetno(String estadoNascimento) {
        this.estadoNascimento = estadoNascimento;
    }

    public Jogador clone() {
        Jogador novo = new Jogador();
        novo.id = this.id;
        novo.nome = this.nome;
        novo.altura = this.altura;
        novo.peso = this.peso;
        novo.universidade = this.universidade;
        novo.cidadeNascimento = this.cidadeNascimento;
        novo.estadoNascimento = this.estadoNascimento;
        return novo;
    }

    public void imprimir() {
        System.out.println("## " + nome + " ## " + altura + " ## " + peso + " ## " + anoNascimento + " ## "
                + universidade + " ## " + cidadeNascimento + " ## " + estadoNascimento + " ##");
    }

    public String toString() {
        return "[" + id + " ## " + nome + " ## " + altura + " ## " + peso + " ## " + anoNascimento + " ## "
            + universidade + " ## " + cidadeNascimento + " ## " + estadoNascimento + "]";
    }
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
