

package ArvoreBMais;

public class ArvoreBMais {
    No raiz;
    
    public No navegaAteFolha(int info){
        No folha = raiz;
        while(folha.getvLig(0)!=null){
            int i = 0;
            while(i < folha.getTL() && info > folha.getvInfo(i)){
                i++;
            }
            folha = folha.getvLig(i);
        }
        return folha;
    }
    
    public int procuraPosicao(No folha, int info){
        int i = 0;
        while(i < folha.getTL() && info > folha.getvInfo(i)){
            i++;
        }
        return i;
    }
    
    public void remaneja(No folha, int pos){
        for(int i = folha.getTL(); i > pos; i--){
            folha.setvInfo(i, folha.getvInfo(i-1));
            folha.setvLig(i, folha.getvLig(i-1));
        }
    }
    public No procuraPai (No folha, int info){
        No ant;
        No aux;
        aux = ant = raiz;
        while(aux != folha){
            int i = 0;
            while(i < aux.getTL() && info > aux.getvInfo(i)){
                i++;
            }
            ant = aux;
            aux = aux.getvLig(i);
        }
        return ant;
    }
    public void split (No folha, No pai, int info){
        No cx1,cx2;
        int pos, tam, i;
        boolean flag = true;// Para saber se é folha.
        cx1 = new No();
        cx2 = new No();
        
        if(folha.getvLig(0) == null)//Folha.
            tam = Math.round( (float) (folha.getN() - 1) / 2);
        else
        {
            tam = Math.round((float)(folha.getN()/2)) - 1;
            
            flag = false;//Nó não é Folha.
        }
        
        //criando a caixa da esquerda
        for (i = 0; i < tam; i++)
        {
            cx1.setvInfo(i, folha.getvInfo(i));
            cx1.setvLig(i, folha.getvLig(i));
        }
        
        cx1.setvLig(i, folha.getvLig(i));
        cx1.setTL(i);
        
        if(!flag)//Quando não for folha, para não clonar no filho.
            tam+=1;
        //criando a caixa da direita
        for (i = tam; i < folha.getN()-1;i++)
        {
            cx2.setvInfo(i - tam , folha.getvInfo(i));
            cx2.setvLig(i - tam , folha.getvLig(i));
        }
        cx2.setvLig(i - tam , folha.getvLig(i));
        cx2.setTL(i - tam );
        
        if(flag)//Ligando as caixas da Folha.
        {
            cx1.setProx(cx2);
            cx2.setProx(folha.getProx());     
        }
        else//Restaurando valor de tam.
            tam-=1;
        
        if(pai == folha)
        {
            folha.setvInfo(0, folha.getvInfo(tam));  //pega o elemento do meio
            folha.setvLig(0, cx1);
            folha.setvLig(1, cx2);
            folha.setTL(1);
        }
        else
        {
            if(folha.getvLig(0) == null)//Folha, fazer a ligação com cx1.
            {
                pos = procuraPosicao(pai, folha.getvInfo(1)); //Pega posição da ligação da folha com o pai.
                if(pos > 0)
                    pai.getvLig(pos - 1).setProx(cx1);//atualizando ligação
            }
            ////////////////////////////////////////////////////////////////////
            info = folha.getvInfo(tam); //Pegando elemento da folha a ser inserido no pai. 
            pos = procuraPosicao(pai, info);
            remaneja(pai, pos);
            pai.setTL(pai.getTL()+1);
            
            pai.setvInfo(pos, info); //elemento subindo para o pai
            pai.setvLig(pos, cx1);
            pai.setvLig(pos+1, cx2);
            
            if (pai.getTL() >= folha.getN())  //novo split (recursivo)
            {
                folha = pai;
                info = folha.getvInfo(tam);//pega o elemento do meio
                pai = procuraPai(pai, info); //em vez de mandar pai por parametro pode mandar folha -dá na mesma pq folha recebeu pai
                split(folha,pai,info);
            }
            
        }
    }
       
    public void insere(int info){
        if(raiz == null){
            raiz = new No();
            raiz.setvInfo(0,info);
            raiz.setTL(1);
        }
        else{
            No folha = navegaAteFolha(info);
            int pos = procuraPosicao(folha, info);
            remaneja(folha, pos);
            folha.setvInfo(pos, info);
            folha.setTL(folha.getTL()+1);
            if(folha.getTL() >= folha.getN()-1){
                No pai = procuraPai(folha, info);
                split(folha,pai,info);
            }
        }
    }
    public void inOrdem(){
        inOrdem(raiz);
    }
    public void inOrdem(No raiz)
    {
        int i;
        if(raiz != null)
        {
            for (i=0; i<raiz.getTL(); i++)
            {
                //System.out.print(raiz.getInfo(i) + " ");//Pre-Ordem
                inOrdem(raiz.getvLig(i));
                System.out.print(raiz.getvInfo(i) + " ");//In-Ordem
            }
            inOrdem(raiz.getvLig(i));
        }
    }
    
}
