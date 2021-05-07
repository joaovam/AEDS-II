#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

bool palin(char array[],int i,int j){

    bool resp = true;

    if(i<j && resp){
        if(array[i]!=array[j]){//confere letra a letra 

            resp = false;
        }else{
            resp = palin(array,++i,--j);
        }
    }

    return resp;

}


int main(){

    char palavra[1000];

    scanf(" %[^\n]",palavra);//le ate encontrar uma quebra de linha

    int i = 0;
    int j;

    while(strcmp(palavra,"FIM")!=0){
        j = strlen(palavra)-1;

        if(palin(palavra,i,j))
            printf("SIM\n");

        else
            printf("NAO\n");

        scanf(" %[^\n]",palavra);

    }


    return 0;
}
