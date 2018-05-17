package arquivojava;

public class Principal_Sequencial 
{
    static Sequencial arq;

    //método principal
    public static void main(String args[])
    {
        arq = new Sequencial("d:\\desktop\\arq.dat");
        arq.executa();
    }
}
