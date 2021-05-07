public class PalindromoRec{

    public static boolean palin(String palavra,int i,int j){ // confere se e palindromo

        boolean resp = true;     
        if(i<palavra.length() && resp){

            if(palavra.charAt(i)!=palavra.charAt(j)){
                resp = false;
            }else{
               resp = palin(palavra,++i,--j);
    
            }
        }

        return resp;

    }

    public static boolean isFim(String palavra){

        return palavra.length() ==3 && palavra.charAt(0) == 'F' && palavra.charAt(1) == 'I' && palavra.charAt(2) == 'M';  

    }

    public static void main(String[] Args){


        String palavra = MyIO.readLine();
        int i = 0;
        int j = 0;

        while(!isFim(palavra)){// confere se a entrada de palavras finalizou

            j = palavra.length()-1;
            
            if(palin(palavra,i,j))
                MyIO.println("SIM");

            else
                MyIO.println("NAO");

            palavra = MyIO.readLine();

        }

            



    }




}
