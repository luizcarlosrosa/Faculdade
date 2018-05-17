package sequencialindexado;

public class PrincipalSeqIndex 
{
    
    static Sequencial_Indexada arq;

    //método principal
    public static void main(String args[])
    {
        arq = new Sequencial_Indexada("d:\\desktop\\arq.dat");
        arq.executa();
    }
}
