package arquivojava;

import java.io.IOException;
import java.io.RandomAccessFile;


//... classe Arquivo (onde vai estar o método para ordernar, etc) ....
public class Sequencial 
{
    private String nomearquivo;
    private RandomAccessFile arquivo;

    public Sequencial(String nomearquivo)
    {
        this.nomearquivo = nomearquivo;
        try
        {
            arquivo = new RandomAccessFile(this.nomearquivo,"rw");
        }
        catch(IOException e){}
    }

    public int filesize()
    {
        try
        {
        return (int)(arquivo.length() / Registro.length());
        }
        catch(IOException e){}
        return 0;
    }

    public void truncate(long pos) //desloca eof
    {
        try
        {
        arquivo.setLength(pos*Registro.length());
        }
        catch(IOException exc) { }
    }

    //igual ao eof do Pascal
    public boolean eof()      //se o ponteiro esta no eof
    {
        boolean retorno = false;
        try
        {
        if (arquivo.getFilePointer()==arquivo.length())  //retorna posicao em bytes
            retorno = true;                                //entao div por tam reg(52)
        }
        catch(IOException e){}
        finally
        {
            return(retorno);
        }
    }

    //insere um Registro no final do arquivo, passado por parâmetro
    public void inserirRegNoFinal(Registro reg)
    {
        try
        {
        arquivo.seek(arquivo.length());//ultimo byte
        reg.gravaNoArq(arquivo);
        }
        catch(IOException e){}
    }

    public void exibirArq()
    {
        int i;
        Registro aux = new Registro();
        try
        {
            arquivo.seek(0);
            i=0;
            while(!this.eof())
            {
                System.out.println("Posicao " + i);
                aux.leDoArq(arquivo);
                aux.exibirReg();
                i++;
            }
        }
        catch(IOException e){}
    }

    public void exibirUmRegistro(int pos)
    {
        Registro aux = new Registro();
        try
        {
        arquivo.seek(pos*Registro.length());
        System.out.println("Posicao " + pos);
        aux.leDoArq(arquivo);
            aux.exibirReg();
            }
            catch(IOException e){}
    }

    public void seekArq(int pos)
    {
        try
        {
        arquivo.seek(pos*Registro.length());
            }
            catch(IOException e){}
    }
    
    public void leArq()
    {
        int codigo,idade;
        String nome;

        codigo=Entrada.leInteger("Digite o código");
        while (codigo!=0)
        {
            nome=Entrada.leString("Digite o nome");
            idade=Entrada.leInteger("Digite a idade");
            inserirRegNoFinal(new Registro(codigo,nome,idade));
            codigo=Entrada.leInteger("Digite o código");
        }
    }
    //bolha
    public int busca_binaria(int chave)
    {   
        Registro reg = new Registro();
        int ini,fim,meio;  
        ini=0;
        if(filesize() == 0)
            return 0;
        
        fim=filesize()-1;
         meio=(ini+fim)/2;
        
        seekArq(meio);
        reg.leDoArq(arquivo);
        while(ini<fim && chave!=reg.getNumero())
        {
            if(reg.getNumero()>chave)
              fim=meio;

            else
            ini=meio+1;

            meio=(ini+fim)/2;
            
            seekArq(meio);
            reg.leDoArq(arquivo);
        }
        
        if(reg.getNumero()==chave)
            return meio;
        if(reg.getNumero()<chave)
            return meio+filesize()+1;
        return meio+filesize();
    }
 
    //insere ordenado
    public void insere()     
    {
            Registro reg;
            int numero, idade, pos;
            String nome;
            numero = Entrada.leInteger("Numero");
            while(numero>0)
            {
                    pos = busca_binaria(numero);
                    if (pos >=filesize())  //não achou
                    {
                            pos -= filesize();
                            nome = Entrada.leString("Nome");
                            idade = Entrada.leInteger("Idade");
                            for(int i=filesize() - 1;i>=pos;i--)
                            {
                                    Registro aux = new Registro();
                                    seekArq(i);
                                    aux.leDoArq(arquivo);
                                    aux.gravaNoArq(arquivo);
                            }
                            reg = new Registro(numero,nome,idade);
                            seekArq(pos);
                            reg.gravaNoArq(arquivo);

                    }
                    else
                        System.out.println("Número já existe!");
                    numero = Entrada.leInteger("Numero");
            }
    }

    public void reorganiza()   //retira fisicamente os já excluído logicamente
    {
            int it=0;  //it é a posição até onde só tem validos
            Registro reg=new Registro();
            for(int i=0; i<filesize(); i++)
            {
                    seekArq(i);
                    reg.leDoArq(arquivo);
                    if(reg.getStatus())
                    {
                            if(i!=it)         //esse if pode ser feito com um AND no if anterior, mais processamento por causa do AND
                            {                 //só grava se não for na mesma posição 
                                    seekArq(it);
                                    reg.gravaNoArq(arquivo);
                            }
                            it++;
                    }
            }
            truncate(it);
    }
    
    public void exclui(int pos)
    {
        try
        {
            arquivo.seek(pos*Registro.length());
            Registro reg = new Registro();
            reg.leDoArq(arquivo);
            reg.setStatus(false);
            arquivo.seek(pos*Registro.length());
            reg.gravaNoArq(arquivo);
        }
        catch(Exception e){System.out.println("Erro na exclusão lógica!");}
    }

    public void relatorio()
    {
        Registro reg = new Registro();
        try
        {
            arquivo.seek(0);
            while(!eof())
            {
                reg.leDoArq(arquivo);
                if(reg.getStatus())
                   reg.exibirReg();
            }
        }
        catch(Exception e){System.out.println("Erro ao exibir: " + e.getMessage());}
    }
    
    public void altera(int pos)
    {
        int idade;
        String nome;
        Registro reg = new Registro();
        try
        {
            arquivo.seek(pos*Registro.length());
            reg.leDoArq(arquivo);
            reg.exibirReg();
            nome = Entrada.leString("nome");
            idade = Entrada.leInteger("idade");
            reg.setNome(nome);
            reg.setIdade(idade);
            arquivo.seek(pos*Registro.length());
            reg.gravaNoArq(arquivo);
        }
        catch(Exception e){System.out.println("Erro ao alterar: " + e.getMessage());}
    }
    public void consulta() //
    {
        Registro reg = new Registro();
        try
        {
            arquivo.seek(0);
            while(!eof())
            {
                reg.leDoArq(arquivo);
                //if(reg.getNumero() == numero)
                    reg.exibirReg();  //exibe status e exibe mesmo que eu não possa pel
            }
        }   
        catch(Exception e){}
    }
    
    public void executa()
    {
        insere();
        exclui(2);
        exibirArq();
        //altera(0);
        //relatorio();
    }
    
}




//int buscaBinaria(int chave)
//insere()
//altera()
//consulta()
//exclui() //exclusão lógica
//reorganiza() //exclusão física
//relatorio()//somente os true