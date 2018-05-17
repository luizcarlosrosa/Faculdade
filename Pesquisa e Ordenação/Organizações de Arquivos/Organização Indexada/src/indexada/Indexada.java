package indexada;

import java.io.IOException;
import java.io.RandomAccessFile;


//... classe Arquivo (onde vai estar o método para ordernar, etc) ....
public class Indexada 
{
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private String nomearquivoind;
    private RandomAccessFile arqind;

    public Indexada(String nomearquivoind, String nomearquivo)
    {
        this.nomearquivo = nomearquivo;
        this.nomearquivoind = nomearquivoind;
        try
        {
            arqind = new RandomAccessFile(this.nomearquivoind,"rw");
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

    public void seekArq(int pos)
    {
        try
        {
        arquivo.seek(pos*Registro.length());
            }
            catch(IOException e){}
    }
    
    public int filesizeInd()
    {
        try
        {
            return (int)(arqind.length() / RegistroInd.length());
        }
        catch(IOException e){}
        return 0;
    }

    public void truncateInd(long pos) //desloca eof
    {
        try
        {
            arqind.setLength(pos*RegistroInd.length());
        }
        catch(IOException exc) { }
    }

    //igual ao eof do Pascal
    public boolean eofInd()      //se o ponteiro esta no eof
    {
        boolean retorno = false;
        try
        {
            if (arqind.getFilePointer()==arqind.length())  //retorna posicao em bytes
                retorno = true;                                //entao div por tam reg(52)
        }
        catch(IOException e){}
        finally
        {
            return(retorno);
        }
    }

    public void seekArqInd(int pos)
    {
        try
        {
        arqind.seek(pos*RegistroInd.length());
            }
            catch(IOException e){}
    }
    
    public int buscaBinariaInd(int chave)
    {
        RegistroInd reg = new RegistroInd();
        int ini,fim,meio;  
        ini=0;
        if(filesizeInd() == 0)
            return 0;
        
        fim=filesizeInd()-1;
         meio=(ini+fim)/2;
        
        seekArqInd(meio);
        reg.leDoArq(arqind);
        while(ini<fim && chave!=reg.getChave())
        {
            if(reg.getChave()>chave)
              fim=meio;

            else
            ini=meio+1;

            meio=(ini+fim)/2;
            
            seekArqInd(meio);
            reg.leDoArq(arqind);
        }
        
        if(reg.getChave()==chave)
            return meio;
        if(reg.getChave()<chave)
            return meio+filesizeInd()+1;
        return meio+filesizeInd();
    }
 
    //insere ordenado
    public void insere()     //fazer considerando status
    {
        RegistroInd regind;
        Registro reg;
        int numero, idade, pos;
        String nome;
        numero = Entrada.leInteger("Numero");
        while(numero>0)
        {
            pos = buscaBinariaInd(numero);
            if (pos >=filesizeInd())  //não achou
            {
                pos -= filesizeInd();
                nome = Entrada.leString("Nome");
                idade = Entrada.leInteger("Idade");
                for(int i=filesizeInd() - 1;i>=pos;i--)
                {
                    RegistroInd aux = new RegistroInd();
                    seekArqInd(i);
                    aux.leDoArq(arqind);
                    aux.gravaNoArq(arqind);
                }
                regind = new RegistroInd(numero,filesize());
                seekArqInd(pos);
                regind.gravaNoArq(arqind);
                reg = new Registro(numero,nome,idade);
                seekArq(filesize());
                reg.gravaNoArq(arquivo);
            }
            else
                System.out.println("Número já existe!");
            numero = Entrada.leInteger("Numero");
        }
    }

    public void reorganiza()   //retira fisicamente os já excluído logicamente
    {
//        int it=0;  //it é a posição até onde só tem validos
//        RegistroInd regind = new RegistroInd();
//        Registro reg = new Registro();
//        for(int i=0; i<filesizeInd(); i++)
//        {
//            seekArqInd(i);
//            regind.leDoArq(arqind);
//            seekArq(regind.getPosdad());
//            reg.leDoArq(arquivo);
//            if(reg.getStatus())
//            {
//                if(i!=it)          //esse if pode ser feito com um AND no if anterior, mais processamento por causa do AND
//                {                 //só grava se não for na mesma posição 
//                    seekArqInd(it);
//                    regind.gravaNoArq(arqind);
                    
//                    seekArqInd(it);
//                    regind.leDoArq(arqind);
//                    seekArq(regind.getPosdad());
//                    reg.leDoArq(arquivo);
//                    for(int j=regind.getPosdad();j<filesize();j++)
//                    {
//                        seekArq(j+1);
//                        reg.leDoArq(arquivo);
//                        seekArq(j);
//                        reg.gravaNoArq(arquivo);
//                        int pos=buscaBinariaInd(reg.getNumero());
//                        seekArqInd(pos);
//                        regind.leDoArq(arqind);
//                        regind.setPosdad(j);
//                        seekArqInd(pos);
//                        regind.gravaNoArq(arqind);
//                    }
//                    truncate(filesize()-1);
//                    seekArqInd(i);
//                    regind.leDoArq(arqind);
//                    seekArqInd(i);
//                    regind.gravaNoArq(arqind);
//                }
//                it++;
//            }
//        }
//        truncateInd(it);
        int it=0;  //it é a posição até onde só tem validos
        
        Registro reg = new Registro();
        truncateInd(0);
        for(int i=0; i<filesize(); i++)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            if(reg.getStatus())
            {
                if(i!=it)          //esse if pode ser feito com um AND no if anterior, mais processamento por causa do AND
                { 
                    seekArq(it);
                    reg.gravaNoArq(arquivo);
                }
                it++;
            }
        }
        truncate(it);
        seekArqInd(0);
        for(int i=0;i<filesize();i++)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            RegistroInd regind = new RegistroInd(reg.getNumero(),i);
            regind.gravaNoArq(arqind);
        }
    }
    
    public void exclui()
    {
        try
        {
            int pos = Entrada.leInteger("Excluir logico :");
            if(pos>0)
            {
                pos = buscaBinariaInd(pos);
                if (pos >=filesizeInd())
                    System.out.println("Número nao encontrado!");
                else
                {
                    RegistroInd regind = new RegistroInd();
                    seekArqInd(pos);
                    regind.leDoArq(arqind);
                    Registro reg = new Registro();
                    seekArq(regind.getPosdad());
                    reg.leDoArq(arquivo);
                    reg.setStatus(false);
                    seekArq(regind.getPosdad());
                    reg.gravaNoArq(arquivo);
                }
            }
        }
        catch(Exception e){System.out.println("Erro na exclusão lógica: " + e.getMessage());}
    }

    public void relatorio()
    {
        RegistroInd regind = new RegistroInd();
        Registro reg = new Registro();
        try
        {
            seekArqInd(0);
            while(!eofInd())
            {
                regind.leDoArq(arqind);
                seekArq(regind.getPosdad());
                reg.leDoArq(arquivo);
                if(reg.getStatus())
                   reg.exibirReg();
            }
        }
        catch(Exception e){System.out.println("Erro ao exibir: " + e.getMessage());}
    }
    
    public void altera()
    {
        try
        {
            int pos = Entrada.leInteger("Alterar :");
            if(pos>0)
            {
                pos = buscaBinariaInd(pos);
                if (pos >=filesizeInd())
                    System.out.println("Número nao encontrado!");
                else
                {
                    RegistroInd regind = new RegistroInd();
                    seekArqInd(pos);
                    regind.leDoArq(arqind);
                    Registro reg = new Registro();
                    seekArq(regind.getPosdad());
                    reg.leDoArq(arquivo);
                    reg.exibirReg();
                    
                    String nome  = Entrada.leString("Nome :");
                    int idade  = Entrada.leInteger("Idade :");
                    String status  = Entrada.leString("Status (T/F):");
                    
                    reg.setNome(nome);
                    reg.setIdade(idade);
                    if(status.equals("T"))
                        reg.setStatus(true);
                    else
                        if(status.equals("F"))
                            reg.setStatus(false);
                    seekArq(regind.getPosdad());
                    reg.gravaNoArq(arquivo);
                }
            }
        }
        catch(Exception e){System.out.println("Erro na alteração: " + e.getMessage());}
    }
    public void consulta()
    {
        try
        {
            int pos = Entrada.leInteger("Consultar :");
            if(pos>0)
            {
                pos = buscaBinariaInd(pos);
                if (pos >=filesizeInd())
                    System.out.println("Número nao encontrado!");
                else
                {
                    RegistroInd regind = new RegistroInd();
                    seekArqInd(pos);
                    regind.leDoArq(arqind);
                    Registro reg = new Registro();
                    seekArq(regind.getPosdad());
                    reg.leDoArq(arquivo);
                    reg.exibirReg();
                    seekArq(regind.getPosdad());
                    reg.gravaNoArq(arquivo);
                }
            }
        }
        catch(Exception e){System.out.println("Erro na consulta: " + e.getMessage());}
    }
}