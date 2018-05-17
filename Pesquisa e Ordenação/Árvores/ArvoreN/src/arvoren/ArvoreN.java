package arvoren;

import static arvoren.NNo.N;

public class ArvoreN {

    private No raiz;

    public ArvoreN() {
        raiz = null;
    }

    public No getRaiz() {
        return this.raiz;
    }
//------------------------------------------------------------------------------

    public int busca(No p, int info) {
        int pos = 0;
        while (pos < p.getTl() && info < p.getInfo(pos)) {
            pos++;
        }
        return pos;
    }
//------------------------------------------------------------------------------

    public void insere(int info) {
        No p, ant = null;
        int i = 0;
        boolean flag = false;
        if (raiz == null) {
            raiz = new No(info);
            flag = true;
        } else {
            p = raiz;
            while (p != null && !flag) {
                if (p.getTl() < N - 1) {
                    i = busca(p, info);
                    for (int j = p.getTl(); j > i; j--) {
                        p.setInfo(j, p.getInfo(j - 1));
                        p.setLig(i, p.getLig(-1));
                    }
                    p.setInfo(i, info);
                    p.setTl(p.getTl() + 1);
                    flag = true;
                } else {
                    ant = p;
                    i = busca(p, info);
                    p = p.getLig(i);
                }
            }
        }
        if (!flag) {
            ant.setLig(i, new No(info));
        }

    }
//------------------------------------------------------------------------------

    public void inOrden(No raiz) {
        if (raiz != null) {
            for (int i = 0; i < raiz.getTl(); i++) {
                inOrden(raiz.getLig(i));
                System.out.println("Aqui:" + raiz.getInfo(i));
            }
            inOrden(raiz.getLig(raiz.getTl()));
        }
    }

    public static void main(String[] args) {

    }

}
