
package indexada;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RegistroInd 
{
    private int chave, posdad;
    
    public RegistroInd(){}

    public RegistroInd(int chave, int posdad) {
        this.chave = chave;
        this.posdad = posdad;
    }
    
    public void gravaNoArq(RandomAccessFile arquivo)
    {
        try
        {
            arquivo.writeInt(chave);
            arquivo.writeInt(posdad);
        }
        catch(IOException e){}
    }

    public void leDoArq(RandomAccessFile arquivo)
    {
        try
        {
            this.chave = arquivo.readInt();
            this.posdad = arquivo.readInt();
        }
        catch(IOException e){System.out.print("erro ao ler arquivo");}
    }
    
    static int length()
    {
            return(8); //int codigo, tl=20, idade; ------------> 12 bytes
                    //private char nome[] = new char[20]; --> 40 bytes
                    //------------------------------------------------- +
                    //                      Total : 40 + 12 = 52 bytes
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public int getPosdad() {
        return posdad;
    }

    public void setPosdad(int posdad) {
        this.posdad = posdad;
    }
}
