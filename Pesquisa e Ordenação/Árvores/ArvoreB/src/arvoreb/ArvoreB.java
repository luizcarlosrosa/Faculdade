package arvoreb;

public class ArvoreB 
{
    No raiz;
 
    public ArvoreB()    //no java o raiz ja fica com null, mas eh melhor garantir
    {    
        raiz = null;  
    }
    
    public No navegaAteFolha(int info)
    {
        No p;
        p = raiz;
        while (p.getLig(0) != null)  //enquanto tem filhos  
        {
             int i=0;
             while(i<p.getTl() && info>p.getInfo(i))   //achando a ligação 
                 i++;  
             
             p = p.getLig(i);
        }
        return p;
    }
    
    public int procuraPosicao(No folha, int info)
    {
        int i=0;
        while (i<folha.getTl() && info>folha.getInfo(i))
            i++;
        return i;
    }
    
    public void remaneja(No folha, int pos)
    {
        folha.setLig(folha.getTl() + 1, folha.getLig(folha.getTl()));
        for (int i=folha.getTl(); i>pos; i--)
        {
            folha.setInfo(i, folha.getInfo(i-1));
            folha.setLig(i, folha.getLig(i-1));
        }
    }
    
    public No localizaPai(No folha, int info)  //se folha for a raiz retorna ela mesma
    {
        No p,pai;
        p=raiz;
        pai=p;
        while(p != folha)   
        {
            int i=0;
            while(i<p.getTl() && info>p.getInfo(i))
                i++;
            pai=p;
            p=p.getLig(i);
        }
        return pai;
    }
    
    public void split(No folha, No pai, int info) //esse info pode ser declarado dentro da função pq ele nao precisa retornar
    {
        No cx1,cx2;
        int pos;
        cx1 = new No();
        cx2 = new No();
        //criando a caixa da esquerda
        for (int i=0; i<No.getN() ;i++)
        {
            cx1.setInfo(i, folha.getInfo(i));
            cx1.setLig(i, folha.getLig(i));
        }
        cx1.setLig(No.getN(), folha.getLig(No.getN()));
        cx1.setTl(No.getN());
        //criando a caixa da direita
        for (int i=No.getN()+1; i<2*No.getN()+1 ;i++)
        {
            cx2.setInfo(i-(No.getN()+1), folha.getInfo(i));
            cx2.setLig(i-(No.getN()+1), folha.getLig(i));
        }
        cx2.setLig(No.getN(), folha.getLig(2*No.getN()+1));
        cx2.setTl(No.getN());
        
        if(pai == folha)
        {
            folha.setInfo(0, folha.getInfo(No.getN()));  //pega o elemento do meio
            folha.setLig(0, cx1);
            folha.setLig(1, cx2);
            folha.setTl(1);
        }
        else
        {
            info = folha.getInfo(No.getN()); //pega o elemento do meio
            pos = procuraPosicao(pai, info);
            remaneja(pai, pos);
            pai.setTl(pai.getTl()+1);
            
            pai.setInfo(pos, folha.getInfo(No.getN()));  //elemento subindo para o pai
            pai.setLig(pos, cx1);
            pai.setLig(pos+1, cx2);
            //folha = null; num precisa (dispose)
            if (pai.getTl() > 2*No.getN())  //novo split (recursivo)
            {
                folha = pai;
                info = folha.getInfo(No.getN());//pega o elemento do meio
                pai = localizaPai(pai, info); //em vez de mandar pai por parametro pode mandar folha -dá na mesma pq folha recebeu pai
                split(folha,pai,info);
            }
        }
    }
    
    public void insereArvore(int info)
    {
        No folha = new No(), pai;   //inicializa o folha por causa do compilador
        int pos;
        if (raiz == null)
        {
            raiz = new No();
            raiz.setTl(1);
            raiz.setInfo(0, info);
        }
        else
        {
            folha = navegaAteFolha(info);    
            pos = procuraPosicao(folha, info);
            remaneja(folha,pos);     //não precisa remanejar lig pq nas folhas é tudo null
            folha.setTl(folha.getTl()+1);
            folha.setInfo(pos, info);
            if(folha.getTl() > 2*No.getN())
            {
                pai = localizaPai(folha, info);
                split(folha, pai, info);
            }
        }
    }
    
    public void inOrdem(No raiz)
    {
        int i;
        if(raiz != null)
        {
            for (i=0; i<raiz.getTl(); i++)
            {
                inOrdem(raiz.getLig(i));
                System.out.print(raiz.getInfo(i) + " ");
            }
            inOrdem(raiz.getLig(i));
        }
    }
    public void InOrdem()
    {
        inOrdem(raiz);
    }
///////////////////////////////////exclusao/////////////////////////////////////////////////    
    public void exclui(int info)
    {
        No p;
        p = encontraNo(info);
        if (p != null)
        {
            int pos = contem(p,info);   //retorna a posicao dentro do nó da info q vai ser retirada
            if(p.getLig(0) != null) //se não for folha - substitui o valor a ser retirado pelo valor adjacente na folha
            {
               No noAux = p.getLig(pos);
               while(noAux.getLig(0) != null)  //enquanto não  for folha
               {
                   noAux = noAux.getLig(noAux.getTl());
               }
               
               info = noAux.getInfo(noAux.getTl()-1);   //jogando o substituto
               p.setInfo(pos, info);
               p = noAux;
               
            }
            //retirando o elemento da folha
            remanejaParaExcluir(p, contem(p,info));    //se retira da folha num precisa remanejar as ligações pq são null
            p.setTl(p.getTl()-1);//decrementando tl    //mas para tornar a função mais genérica remanejamos as ligações
            if(p.getTl() < No.getN() && p != raiz)
            {
                //reestruturando 
                No pai = localizaPai(p, info);  
                corrige(p,pai);  //- recursivo
            }
            else if (p.getTl() == 0)
                raiz = null;
        }
        else
            System.out.print("Elemento não encontrado");
        
    }
    public int contem(No folha, int info)   //retornar a posição ou -1 se não encontrar
    {
        int pos = -1;
        for(int i=0; i<folha.getTl(); i++)
        {
            if(folha.getInfo(i) == info)
                pos = i;
        }
        return pos;
    }
    public No encontraNo(int info)  // se retornar null é porque não encontrou
    {
        No aux = raiz;
        while(aux != null && contem(aux, info) == -1) //não contem
        {
            int i = 0;
            while (i<aux.getTl() && info > aux.getInfo(i))
                i++;
            aux = aux.getLig(i);
        }
        return aux;
    }
    public void remanejaParaExcluir(No caixa,int pos)   //eh diferente do remaneja pq eh pra excluir
    {
        for(int i=pos; i<caixa.getTl()-1; i++)
        {
            caixa.setInfo(i, caixa.getInfo(i+1));
            caixa.setLig(i, caixa.getLig(i+1));
        }
        caixa.setLig(caixa.getTl() - 1, caixa.getLig(caixa.getTl()));
        //caixa.setTl(caixa.getTl()-1); //decrementar fora da função
       
        //nao remaneja as ligações
//        for(int i=pos; i<caixa.getTl()-1; i++)
//        {
//            caixa.setInfo(i, caixa.getInfo(i+1));
//        }
        
    }
    //java NAO tem passagem de parâmetro por valor
    //nada é excluído no corrige, exclui ante e chama o corrige somente para corrigir
    public void corrige(No p, No pai)   //desfaz split
    {
        //validar se p eh raiz, na primeira chamada ñ vai ser, mas nas outras pode ser
        if(p != raiz)  //ou if(p != pai)
        { 
            int posLig = 0;                                    //achando a posição da folha no pai
            while(posLig<=pai.getTl() && pai.getLig(posLig)!=p) //não precisaria do posLig <= pai.getTl() pq sempre vai achar
            posLig++;
            
            int pos;  //posicao do elemento na folha
            
            if(posLig>0 && pai.getLig(posLig-1).getTl() > No.getN())  
            {
                //se lado esquerdo existir e tiver mais de n elementos
                //pega do lado esquerdo     
                No vizinho = pai.getLig(posLig-1);         
                pos = 0;  //como é do lado esquerdo é menor então vai ser pos = 0
                remaneja(p,pos);
                p.setInfo(pos, pai.getInfo(posLig-1));
                p.setLig(pos, vizinho.getLig(vizinho.getTl()));   //erro!!!!!!!!!!!
                p.setTl(p.getTl()+1);
                pai.setInfo(posLig-1, vizinho.getInfo(vizinho.getTl()-1));
                vizinho.setTl(vizinho.getTl()-1);
            }
            else if(posLig < pai.getTl() && pai.getLig(posLig+1).getTl() > No.getN()) //se existir
            {
                //se lado direito tiver mais de n elementos e do esquerdo não tiver
                //pega do lado direito      
                No vizinho = pai.getLig(posLig+1);      
                pos = p.getTl();  //como é do lado direito vai ser maior
                p.setInfo(pos, pai.getInfo(posLig));
                p.setLig(pos+1, vizinho.getLig(0));
                p.setTl(p.getTl()+1);
                pai.setInfo(posLig, vizinho.getInfo(0));
                remanejaParaExcluir(vizinho, 0);
                vizinho.setTl(vizinho.getTl()-1);
            }
            else  if(posLig > 0)//pega do lado esquerdo e faz fusão
            {
                int i;                                  
                No vizinho = pai.getLig(posLig-1);
                for(i=0; i<vizinho.getTl(); i++)
                {
                    remaneja(p, i);
                    p.setInfo(i, vizinho.getInfo(i));
                    p.setLig(i, vizinho.getLig(i));
                    p.setTl(p.getTl() + 1);
                }
                remaneja(p, i);
                p.setInfo(i, pai.getInfo(posLig-1));  //desce um
                p.setLig(i, vizinho.getLig(i));
                p.setTl(p.getTl() + 1);
                
                remanejaParaExcluir(pai, posLig-1);
                pai.setTl(pai.getTl()-1);
                
                if(pai == raiz && pai.getTl() == 0)     //isso só ocorre se pai for raiz ou ordem ==1
                    raiz = pai.getLig(0);
                else if (pai != raiz && pai.getTl() < No.getN())
                {
                    p = pai;
                    pai = localizaPai(p, p.getInfo(0));
                    corrige(p, pai);
                }
                
            }
            else  //pega do lado direito e faz fusão-quando não existe vizinho do lado esquerdo e do lado direito tem n elementos
            {
                int i;
                No vizinho = pai.getLig(posLig+1);
                for(i=0; i<p.getTl(); i++)
                {
                    remaneja(vizinho, i);
                    vizinho.setInfo(i, p.getInfo(i));
                    vizinho.setLig(i, p.getLig(i));
                    vizinho.setTl(vizinho.getTl() + 1);
                }
                remaneja(vizinho, i);
                vizinho.setInfo(i, pai.getInfo(posLig));  //desce um
                vizinho.setLig(i, p.getLig(i));
                vizinho.setTl(vizinho.getTl() + 1);
                
                remanejaParaExcluir(pai, posLig);
                pai.setTl(pai.getTl()-1);
                
                if(pai == raiz && pai.getTl() == 0)     //isso só ocorre se pai for raiz ou ordem ==1
                    raiz = pai.getLig(0);
                else if (pai != raiz && pai.getTl() < No.getN())
                {
                    p = pai;
                    pai = localizaPai(p, p.getInfo(0));
                    corrige(p, pai);
                }
            }
        }
    }
}
