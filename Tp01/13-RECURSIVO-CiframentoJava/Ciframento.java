import java.util.Scanner;

public class Ciframento {

    public static boolean isFim(String palavra) {

        return palavra.length() == 3 && palavra.charAt(0) == 'F' && palavra.charAt(1) == 'I'
                && palavra.charAt(2) == 'M';

    }

    public static String cifrar(String palavra,int i, char array[]) {

       
        if (i < array.length) {
            array[i] += palavra.charAt(i) + 3;// guarda no array a letra cifrada

            palavra = cifrar(palavra,++i, array);
        }

        palavra = new String(array);// transforma o array em string para o retorno

        return palavra;

    }

    public static void main(String[] Args) {

        String palavra = MyIO.readLine();

        while (!isFim(palavra)) {// define se a sequencia de entrada acabou
            char[] array = new char[palavra.length()];

            MyIO.println(cifrar(palavra,0, array));

            palavra = MyIO.readLine();

        }
    }
}
