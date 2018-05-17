
package arvorenarea;


public class No {
    private int vInfo [];
    private No vLig [];
    private int TL;
    
    public No(int qtd, int info){
        this.vInfo = new int [qtd];
        this.vLig = new No [qtd+1];
        this.vInfo[0] = info;
        TL = 1;
    }

    public int getvInfo(int info) {
        return vInfo[info];
    }

    public void setvInfo(int pos, int info) {
        this.vInfo[pos] = info;
    }

    public No getvLig(int pos) {
        return vLig[pos];
    }

    public void setvLig(int pos, No vLig) {
        this.vLig[pos] = vLig;
    }

    public int getTL() {
        return TL;
    }

    public void setTL(int TL) {
        this.TL = TL;
    }
    
    
}
