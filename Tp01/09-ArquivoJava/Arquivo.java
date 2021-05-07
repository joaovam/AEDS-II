import java.io.*;

public class Arquivo{

    public static void LeituraContrariaArquivo(String arquivo,int n)throws Exception{

        RandomAccessFile raf = new RandomAccessFile(arquivo,"rw");
        double valor;

        for(int i =1;i <= n ;i++){

            raf.seek((n-i)*8);
            valor = raf.readDouble();

            if(valor % 1 != 0)
                MyIO.println(valor);
            else
                MyIO.println((int) valor);

        }
            

        raf.close();
    }



    public static void main(String[]Args)throws Exception{

        int n = MyIO.readInt();
        String arquivo = "Arquivo.dat";
        RandomAccessFile raf = new RandomAccessFile(arquivo,"rw");

        double valor;

        for(int i = 0;i < n;i++){
            valor = MyIO.readDouble();
            raf.writeDouble(valor);
        }
        raf.close();

        LeituraContrariaArquivo(arquivo,n);


    }



}
