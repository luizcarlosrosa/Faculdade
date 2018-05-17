
package indexada;

public class Principal_Indexada 
{
    Indexada index;
    
    public void executa()
    {
        index = new Indexada("Indexada.dat", "Arquivo.dat");
        index.insere();
        index.exclui();
        index.reorganiza();
        //index.consulta();
        //index.altera();
        index.relatorio();
        
    }
    
    public static void main(String[] args)
    {
        Principal_Indexada app = new Principal_Indexada();
        app.executa();
    }
}