
package arvorenarea;

public class Pilha {
    private PNo inicio;
    private PNo fim;
    
    public Pilha(){
        inicializa();
    }
    public void inicializa(){
        inicio = fim = null;
    }
    public boolean isEmpty(){
        if(inicio == null)
            return true;
        return false;
    }
    public void push(No info){
        PNo nova = new PNo(null, inicio, info);
        if(inicio == null){
            inicio = fim = nova;
        }
        else{
            inicio.setAnt(nova);
            inicio = nova;
        }
    }
    public No pop(){
        PNo p = null;
        if(isEmpty()){
            System.out.println("Pilha Vazia");
        }
        else{
           p = inicio;
           inicio = inicio.getProx();
        }
        return p.getInfo();
    }
}
