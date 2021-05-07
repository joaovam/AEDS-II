#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>


bool isVogal(char palavra[]){

    char vogais[] = {'A','a','E','e','I','i','O','o','U','u'};
    bool resp = true;

    for(int i = 0;palavra[i]!='\0' && resp;i++){
        resp = false;
        for(int j = 0;j < 10 && !resp;j++){
            if(palavra[i]==vogais[j])
                resp = true;

        }
    }
    return resp;
}

bool isConsoante(char palavra[]){

    bool resp = true;
    char teste[1];

    for(int i = 0; palavra[i]!='\0' && resp; i++){

        teste[0] = palavra[i];
        resp = false;

        if(palavra[i]>='A' && palavra[i]<='z' && !isVogal(teste) && !resp)
            resp = true;

    }

    return resp;
}

bool isInteiro(char palavra[]){

    char numeros[] = {'0','1','2','3','4','5','6','7','8','9'};
    bool resp = true;

    for(int i = 0;palavra[i]!='\0' && resp;i++){
        resp = false;

        for(int j = 0;j< 10;j++)
            if(palavra[i] ==numeros[j] )
                resp = true;
    }

    return resp;

}

bool isReal(char palavra[]){

    bool resp = true;
    int cont = 0;
    char teste[1];

    for(int i = 0;i<palavra[i]!='\0' && resp;i++){

        teste[0] = palavra[i];
        if(!isInteiro(teste) && palavra[i]!='.' && palavra[i]!=',')
            resp = false;


        if(palavra[i]=='.' || palavra[i]==',')
            cont++;

    }

    if(cont > 1)
        resp = false;

    return resp;

}



int main(){

    char palavra[1000];
    bool vogal = false;
    bool consoante = false;
    bool inteiro = false;
    bool real = false;



    scanf(" %[^\n]",palavra);

    while(strcmp(palavra,"FIM")!= 0 ){

        vogal = isVogal(palavra);

        if(vogal)
            printf("SIM ");
        else
            printf("NAO ");

        if(!vogal)
            consoante = isConsoante(palavra);
        
        if(consoante)
            printf("SIM ");
        else
            printf("NAO ");

        if(!vogal && !consoante)
            inteiro = isInteiro(palavra);

        if(inteiro)
            printf("SIM ");
        else
            printf("NAO ");

        if(!vogal && !consoante && !inteiro)
            real = isReal(palavra);

        if(real)
            printf("SIM\n");
        else
            printf("NAO\n");


        scanf(" %[^\n]",palavra);
        vogal = false;
        consoante = false;
        inteiro = false;
        real = false;
    }
    return 0;
}


