package metodosdebusca;

import java.io.IOException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws IOException {
        
        
        Vetor v = new Vetor();
        v.Preencher();
        v.Exibir();
        System.out.println("////////////");
        v.GnomeSort();
        v.Exibir();
        
        //Arquivo_Java a = new Arquivo_Java("d:\\arquivo.dat");
        //a.executa();
        
        /*
        ListaDuplamente lista = new ListaDuplamente();
        lista.Insere_Inicio(10);
        lista.Insere_Inicio(20);
        lista.Insere_Inicio(30);
        
        lista.Exibe();
        System.out.println("////////////");
        lista.Remove(10);
        lista.Exibe();
        */
    }

}
