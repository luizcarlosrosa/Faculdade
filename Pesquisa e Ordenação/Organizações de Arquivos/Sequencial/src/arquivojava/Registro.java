package arquivojava;

import java.io.IOException;
import java.io.RandomAccessFile;


//... classe Registro (será um objeto que simboliza o registro em pascal) ....
class Registro
{
    public final int tf=20;//variavel do tipo "final" é constante
    private int numero, tl=tf, idade;
    private char nome[] = new char[tf];
    private boolean status;

    public Registro(){}

    public Registro(int numero, String Snome, int idade)
    {
        this.numero=numero; //this é variavel de instância
        this.idade=idade;
        this.tl=Snome.length();
        this.status = true;
        for (int i=0 ; i<Snome.length() ; i++)
        {
        nome[i]=Snome.charAt(i);
        }
    }

    public int getNumero()
    {
        return(numero);
    }

    public String getNome()
    {
        String Snome = new String(nome);
        return(Snome);
    }

    public boolean getStatus() 
    {
        return status;
    }
    
    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public void setIdade(int idade) 
    {
        this.idade = idade;
    }

    public void setNome(String Snome) 
    {
        this.tl=Snome.length();
        for (int i=0 ; i<Snome.length() ; i++)
        {
             nome[i]=Snome.charAt(i);
        }
    }
    
  
  

    public void gravaNoArq(RandomAccessFile arquivo)
    {
        int i;
            try
            {
            arquivo.writeInt(numero);
            arquivo.writeInt(idade);
            arquivo.writeInt(tl);
            arquivo.writeBoolean(status);
            for(i=0;i<tf;i++)
                    arquivo.writeChar(nome[i]);
            }
            catch(IOException e){}
    }

    public void leDoArq(RandomAccessFile arquivo)
    {
            int i;
            try
            {
            this.numero = arquivo.readInt();
            this.idade = arquivo.readInt();
            this.tl = arquivo.readInt();
            this.status = arquivo.readBoolean();
            for(i=0;i<this.tf;i++)
                this.nome[i] = arquivo.readChar();
            for(i=this.tl ; i<tf ; i++)
                this.nome[i] =' ';
            }
            catch(IOException e){System.out.print("erro ao ler arquivo");}
    }

    public void exibirReg()
    {
            System.out.print("numero....."+this.numero);
            System.out.print(" nome.......");
            String Snome = new String(nome);
            System.out.print(Snome);
            System.out.print(" idade......."+this.idade);
            System.out.println("status....."+this.status);
            System.out.println("--------------------------------------------------");
    }

    static int length()
    {
            return(53); //int codigo, tl=20, idade; ------------> 12 bytes
                    //private char nome[] = new char[20]; --> 40 bytes
                    //------------------------------------------------- +
                    //                      Total : 40 + 12 = 52 bytes
    }

}