package arvoreb;

public class No 
{
    private static final int n = 2;  //ordem da arvore
    private int tl;
    private int info[];
    //public int pos[];
    private No lig[];

    public No() 
    {
        tl = 0;
        info = new int[2*n+1];  //o java ja zera
        lig = new No[2*n+2];  //uma ligação a mais
    }
    public int getInfo(int pos)
    {
        return info[pos];
    }
    public void setInfo(int pos, int info)
    {
        this.info[pos] = info;
    }
    public No getLig(int pos)
    {
        return lig[pos];
    }
    public void setLig(int pos, No lig)
    {
        this.lig[pos] = lig;
    }
    public int getTl()
    {
        return tl;
    }
    public void setTl(int tl)
    {
        this.tl = tl;
    }
    public static int getN()
    {
        return n;
    }
}
