public class Is{

    public static boolean isVogal(String palavra){

        String vogais = "AaEeIiOoUu";
        boolean resp = true;
            
        for(int i = 0;i<palavra.length() && resp;i++){
            resp = false;
            for(int j = 0;j<vogais.length() && !resp;j++){
                if(palavra.charAt(i)==vogais.charAt(j))
                    resp = true;
            }

        }
        return resp;
    }
    public static boolean isConsoante(String palavra){

        boolean resp = true;
        String teste = "";
        for(int i = 0; i <palavra.length() && resp;i++ ){

            resp = false;
            teste += palavra.charAt(i);

            if(palavra.charAt(i)>= 'A' && palavra.charAt(i)<= 'z' && !isVogal(teste) && !resp)
                resp = true;

            teste = "";
        }
        return resp;

    }

    public static boolean isInteiro(String palavra){

        String numeros = "0123456789";
        boolean resp = true;

        for(int i = 0;i <palavra.length() && resp;i++){
            resp = false;
            for(int j = 0;j<numeros.length() && !resp;j++)
                if(palavra.charAt(i) ==numeros.charAt(j))
                    resp = true;
        }
        return resp;

    }

    public static boolean isReal(String palavra){

        boolean resp = true;
        int cont = 0;
        String teste = "";
        for(int i = 0; i<palavra.length() && resp;i++ ){
           
            teste +=palavra.charAt(i);

            if(!isInteiro(teste) && palavra.charAt(i)!='.' && palavra.charAt(i) !=',' )
                resp = false;

            if(palavra.charAt(i)=='.' || palavra.charAt(i)==',')
                cont++;

            teste = "";
        }
        if(cont > 1)
            resp = false;

        return resp;

    }
    public static boolean isFim(String palavra){

        return palavra.length() == 3 && palavra.charAt(0) == 'F' && palavra.charAt(1) == 'I' && palavra.charAt(2) == 'M';

    }
    public static String formulaResposta(boolean valor){

        String resp = "NAO ";

        if(valor)
            resp = "SIM ";
        
        return resp;
    }




    public static void main(String[] Args){

        String palavra = MyIO.readLine();
        boolean vogal = false;
        boolean consoante = false;
        boolean inteiro = false;
        boolean real = false;
        String resposta = "";

        while(!isFim(palavra)){

            vogal = isVogal(palavra);
            resposta+=formulaResposta(vogal);

            if(!vogal)
                consoante = isConsoante(palavra);

            resposta += formulaResposta(consoante);

            if(!vogal && !consoante)
                inteiro = isInteiro(palavra);

            resposta += formulaResposta(inteiro);

            if(!vogal && !consoante && !inteiro)
                real = isReal(palavra);

            resposta += formulaResposta(real);

            MyIO.println(resposta);
            palavra = MyIO.readLine();
            resposta = "";
            vogal = false;
            consoante = false;
            inteiro = false;
            real = false;
            

        }
    }
}
