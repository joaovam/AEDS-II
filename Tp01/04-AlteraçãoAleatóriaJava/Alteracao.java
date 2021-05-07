import java.util.Random;

public class Alteracao{
   
    public static String alteracao(String palavra,Random gerador){

       
            String resp;
            char target =(char) ('a' + (Math.abs(gerador.nextInt()) % 26));//define a letra para mudar
            char troca = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));//define para qual letra sera mudada

            resp = "";

            for(int k = 0; k < palavra.length();k++){

                if(palavra.charAt(k) == target){//testa se e a letra que deve ser trocada ou nao

                    resp += troca;
            
                }else{

                    resp += palavra.charAt(k);
                }
            }
            
        
        return resp;
       
    }

    public static boolean isFim(String palavra){

        return palavra.length() == 3 && palavra.charAt(0) == 'F' && palavra.charAt(1) =='I' && palavra.charAt(2) == 'M'; 
    }

    public static void main(String[] Args){

            String palavra;
            Random gerador = new Random();
            gerador.setSeed(4);
            String resp = "";
            
            palavra = MyIO.readLine();

            while(!isFim(palavra)){//testa se acabou a sequencia de entradas
                    
                resp = alteracao(palavra,gerador);
                
                MyIO.println(resp);
                
                palavra = MyIO.readLine();
            }

            
                


    }


}
