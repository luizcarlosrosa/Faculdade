
package arvorenarea;


public class PNo {
    private No info;
    private PNo prox;
    private PNo ant;

    public PNo() {
    }

    public PNo(PNo ant, PNo prox, No info) {
        this.info = info;
        this.prox = prox;
        this.ant = ant;
    }

    public No getInfo() {
        return info;
    }

    public void setInfo(No info) {
        this.info = info;
    }

    public PNo getProx() {
        return prox;
    }

    public void setProx(PNo prox) {
        this.prox = prox;
    }

    public PNo getAnt() {
        return ant;
    }

    public void setAnt(PNo ant) {
        this.ant = ant;
    }
    
    
}
