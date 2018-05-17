
package arvorenarea;


public class ArvoreNArea {
    No raiz = null;
    
    public ArvoreNArea(){}
    
    public No navegaAteFolha(int info){
        No folha = raiz;
        int i = 0;
        while(folha.getvLig(0) != null){
            i = 0;
            while(i < folha.getTL() && info > folha.getvInfo(i))
                i++;
            if(folha.getvLig(i) != null){
                folha = folha.getvLig(i);
            }
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
        folha.setvLig(folha.getTL()+1, folha.getvLig(folha.getTL()));
        for(int i = folha.getTL(); i>pos; i--){
            folha.setvInfo(i, folha.getvInfo(i-1));
            folha.setvLig(i, folha.getvLig(i-1));
        }
    }
    public void criaFolha(int qtdade){
        
    }
    public void insere (int info, int qtdade){
        boolean flag = false;
        int posInsere;
        No p = null;
        No ant = null;
        No folha = null;
        int i = 0;
        if(raiz == null)
            raiz = new No(qtdade,info);
        else{
            p = raiz;
            while(p != null && !flag){
                if(p.getTL()<qtdade){
                    //folha = navegaAteFolha(info);
                    posInsere = procuraPosicao(p,info);
                    remaneja(p,posInsere);
                    p.setvInfo(posInsere, info);
                    p.setTL(p.getTL()+1);
                    flag = true;
                }
                else{
                    ant = p;
                    posInsere = procuraPosicao(p, info);
                    p = p.getvLig(posInsere);
                }
            }
            if(!flag){
                posInsere = procuraPosicao(ant, info);
                ant.setvLig(posInsere, new No (qtdade, info));
            }
        }
    }
    public void exibirInOrdem(){
        exibirInOrdem(raiz);
    }
    public void exibirInOrdem(No folha)
    {
        if (folha != null)
        {
            int i;
            for (i = 0; i < folha.getTL(); i++)
            {
                exibirInOrdem(folha.getvLig(i));
                System.out.println(folha.getvInfo(i));
            }
            exibirInOrdem(folha.getvLig(i));
        }
    }
    public void exibirEmNivel(){
        exibirEmNivel(raiz);
    }
    public void exibirEmNivel(No Folha){
        No aux=null;
        No aux2=null;
         Pilha p1 = new Pilha();
         Pilha p2 = new Pilha();
        
        p1.push(raiz);
         while(!p1.isEmpty())
         {
           aux = p1.pop();
            
             if(aux !=null)
             {  int i=0;
                  for(;i<aux.getTL();i++)
                 {                         
                      p2.push(aux.getvLig(i));
                      System.out.println(aux.getvInfo(i));
                  }
                  
                 p2.push(aux.getvLig(i));
             }
          
             while(!p2.isEmpty())
             {
                aux =p2.pop(); 
                p1.push(aux);
             }
        }
    }
}
