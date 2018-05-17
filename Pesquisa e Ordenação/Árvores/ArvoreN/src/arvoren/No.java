package arvoren;

public class No implements NNo {

   
    private int[] vInfo;
    private No[] vLig;
    private int tl;

    public No() {
        vLig = new No[N];
        vInfo = new int[N - 1];
        for (int i = 0; i < N; i++) {
            vLig[i] = null;
        }
        tl = 0;
    }

    public No(int info) {
        vLig = new No[N];
        vInfo = new int[N - 1];

        for (int i = 0; i < N; i++) {
            vLig[i] = null;
        }
        vInfo[0] = info;
        tl = 1;
    }

    public void setInfo(int pos, int info) {
        if (pos < N - 1) {
            vInfo[pos] = info;
            tl++;
        }
    }

    public int getInfo(int pos) {
        return vInfo[pos];
    }

    public void setLig(int pos, No no) {
        vLig[pos] = no;
    }

    public No getLig(int pos) {
        return vLig[pos];
    }

    public int getTl() {
        return tl;
    }

    public void setTl(int tl) {
        this.tl = tl;
    }
}
