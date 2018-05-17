

package ArvoreBMais;


public class No {
    private int [] vInfo;
    private No [] vLig;
    private int TL;
    private int N = 5;
    private No prox;
    
    public No(){
        this.vInfo = new int [N-1];
        this.vLig = new No [N];
        prox = null;
    }

    public int getvInfo(int pos) {
        return vInfo[pos];
    }

    public void setvInfo(int pos, int info) {
        this.vInfo[pos] = info;
    }

    public No getvLig(int pos) {
        return vLig[pos];
    }

    public void setvLig(int pos, No info) {
        this.vLig[pos] = info;
    }

    public int getTL() {
        return TL;
    }

    public void setTL(int TL) {
        this.TL = TL;
    }

    public int getN() {
        return N;
    }

    public void setN(int N) {
        this.N = N;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }
    
    
    
    

}


