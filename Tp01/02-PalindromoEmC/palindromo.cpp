#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

bool palin(char array[]){

    bool resp = true;
    int i = 0;
    int j = strlen(array) - 1;
    
    for(;i<=j;i++,j--){

        if(array[i]!=array[j]){//confere letra a letra 

            resp = false;
            i = j+1; // finaliza a repeticao
        }
    }

    return resp;

}


int main(){

    char palavra[1000];

    scanf(" %[^\n]",palavra);//le ate encontrar uma quebra de linha

    while(strcmp(palavra,"FIM")!=0){

        if(palin(palavra))
            printf("SIM\n");

        else
            printf("NAO\n");

        scanf(" %[^\n]",palavra);

    }


    return 0;
}
