public class Palindromo{

    public static boolean palin(String palavra){ // confere se e palindromo

        int i = 0;
        int j =palavra.length() - 1;
        boolean resp = true;

        for(;i<=j;i++,j--){ // confere letra a letra o palindromo

            if(palavra.charAt(i)!=palavra.charAt(j)){
                resp = false;
                i = j+1; // finaliza a repeticao do for
            }


        }

        return resp;

    }

    public static boolean isFim(String palavra){

        return palavra.length() ==3 && palavra.charAt(0) == 'F' && palavra.charAt(1) == 'I' && palavra.charAt(2) == 'M';  

    }

    public static void main(String[] Args){


        String palavra = MyIO.readLine();

        while(!isFim(palavra)){// confere se a entrada de palavras finalizou
            
            if(palin(palavra))
                MyIO.println("SIM");

            else
                MyIO.println("NAO");

            palavra = MyIO.readLine();

        }

            



    }




}
