package sequencialindexado;

import java.io.IOException;
import java.io.RandomAccessFile;


//... classe Arquivo (onde vai estar o método para ordernar, etc) ....
public class Sequencial_Indexada 
{
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private ArvoreB arvB;
    
    public Sequencial_Indexada(String nomearquivo)
    {
        arvB = new ArvoreB();
        
        this.nomearquivo = nomearquivo;
        try
        {
            arquivo = new RandomAccessFile(this.nomearquivo,"rw");
        }
        catch(IOException e){}
        
        Registro reg = new Registro();
        seekArq(0);
        
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
    
    public void leArq()  //função desnecessária
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
 
    //insere 
    public void insere()     
    {
            Registro reg;
            int numero, idade, pos;
            String nome;
            numero = Entrada.leInteger("Numero");
            while(numero>0)
            {
                    pos = arvB.busca(numero);
                    if (pos == -1)  //não achou
                    {
                            nome = Entrada.leString("Nome");
                            idade = Entrada.leInteger("Idade");
                            //O registro sempre será inserido no final
                            reg = new Registro(numero,nome,idade);
                            inserirRegNoFinal(reg);
                            //colocando o indice na arvore
                            arvB.insereArvore(numero, filesize()-1);
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
                    else
                    {
                        //exclui da arvore
                        arvB.exclui(reg.getNumero());
                    }
            }
            truncate(it);
    }
    
    public void exclui(int info)
    {
        int pos = arvB.busca(info);
        if(pos != -1)
          SetStatusFalse(pos);
        else
            System.out.println("Registro não encontrado");
    }
    
    public void SetStatusFalse(int pos)   //exclusão lógica
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

    
    public void RelatorioinOrdem(No raiz)  //somente os true
    {
        int i;
        if(raiz != null)
        {
            for (i=0; i<raiz.getTl(); i++)
            {
                RelatorioinOrdem(raiz.getLig(i));
                seekArq(raiz.getPosArq(i));
                Registro reg = new Registro();
                reg.leDoArq(arquivo);
                if(reg.getStatus())  //se for true
                    reg.exibirReg();
            }
            RelatorioinOrdem(raiz.getLig(i));
        }
    }
    
    public void altera(int info)
    {
        int pos = arvB.busca(info);
        if(pos != -1)
           altera2(pos);
        else
            System.out.println("Registro não encontrado");
    }
    
    public void altera2(int pos)
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
    
    public void consulta()  //exibe todos os registros na ordem em que estão gravados , só para conferir
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
    public void montaArvore()
    {
        Registro reg = new Registro();
        try
        {
            int i = 0;
            arquivo.seek(0);
            while(!eof())
            {
                reg.leDoArq(arquivo);
                arvB.insereArvore(reg.getNumero(), i++);
            }
        }   
        catch(Exception e){}
    }
    
    
    public void executa()
    {
        montaArvore();
//        insere();
//         // exclui(2);
        RelatorioinOrdem(arvB.getRaiz());
//        altera(1300);
        //consulta();
    }
    
}




//int buscaBinaria(int chave) busca na arvore
//insere()
//altera()
//consulta()
//exclui() //exclusão lógica
//reorganiza() //exclusão física
//relatorio()//somente os true