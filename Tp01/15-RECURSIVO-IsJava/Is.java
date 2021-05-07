public class Is{

    public static boolean isVogal(String palavra,int i,int j){

        String vogais = "AaEeIiOoUu";
        boolean resp = true;
            
        if(i<palavra.length() && resp){
            resp = false;
            if(j<vogais.length() && !resp){
                if(palavra.charAt(i)==vogais.charAt(j))
                    resp = true;
                else{
                    resp = isVogal(palavra,i,++j);

                }
            }
            if(resp)
               resp = isVogal(palavra,++i,0);
        }
        return resp;
    }


    public static boolean isConsoante(String palavra,int i){

        boolean resp = true;
        String teste = "";
        if(i <palavra.length() && resp){

            resp = false;
            teste += palavra.charAt(i);

            if(palavra.charAt(i)>= 'A' && palavra.charAt(i)<= 'z' && !isVogal(teste,0,0) && !resp){
                resp = true;
             resp = isConsoante(palavra,++i);
            }
            teste = "";
        }
        return resp;

    }

    public static boolean isInteiro(String palavra,int i,int j){

        String numeros = "0123456789";
        boolean resp = true;

        if(i <palavra.length() && resp){
            resp = false;
            if(j<numeros.length() && !resp){
                if(palavra.charAt(i) ==numeros.charAt(j))
                    resp = true;
                else 
                    resp = isInteiro(palavra,i,++j);
            
            }  
            if(resp)
                resp = isInteiro(palavra,++i,0);
            
        }
        return resp;

    }

    public static boolean isReal(String palavra,int i,int cont){

        boolean resp = true;
        String teste = "";
        if(i<palavra.length() && resp){
           
            teste +=palavra.charAt(i);

            if(!isInteiro(teste,0,0) && palavra.charAt(i)!='.' && palavra.charAt(i) !=',' )
                resp = false;

            if((palavra.charAt(i)=='.' || palavra.charAt(i)==',') && palavra.length() == 1)
                resp = false;

            if(palavra.charAt(i)=='.' || palavra.charAt(i)==',')
                cont++;

            if(resp)
                resp = isReal(palavra,++i,cont);

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

            vogal = isVogal(palavra,0,0);
            resposta+=formulaResposta(vogal);

            if(!vogal)
                consoante = isConsoante(palavra,0);

            resposta += formulaResposta(consoante);

            if(!vogal && !consoante)
                inteiro = isInteiro(palavra,0,0);

            resposta += formulaResposta(inteiro);

            if(!vogal && !consoante && !inteiro)
                real = isReal(palavra,0,0);

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
