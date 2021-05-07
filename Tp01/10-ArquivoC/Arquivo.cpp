#include <stdio.h>
#include <stdlib.h>

void contador(int n){
    double x;
    FILE* arq = fopen("numeros.dat","w");
    int i = 0;

    while(i<n){
        scanf("%lf",&x);
        fseek(arq,i*sizeof(double),SEEK_SET);
        fprintf(arq,"%.3lf",x);
        i++;
    }

    fclose(arq);
    
    arq = fopen("numeros.dat","r");

    for(int j = 1;j <= n;j++){

        fseek(arq,((n-j)*sizeof(double)),SEEK_SET);
        fscanf(arq,"%lf",&x);

        if( x / (int)x != 1)
            printf("%g\n",x);
        else
            printf("%d\n",(int)x);

        
              

    }
    

}


int main(){

    int n;
    scanf("%d",&n);
    contador(n);

    return 0;
}
