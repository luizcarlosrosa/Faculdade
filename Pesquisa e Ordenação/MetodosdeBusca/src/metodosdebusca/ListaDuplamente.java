package metodosdebusca;

public class ListaDuplamente {

    private No inicio;
    private No fim;

    public ListaDuplamente() {
    }

    public void Inicializa() {
        inicio = null;
        fim = null;
    }

    public void Insere_Inicio(int info) {

        No nova = new No(null, inicio, info);
        if (inicio == null) {
            inicio = fim = nova;
        } else {
            inicio.setAnt(nova);
            inicio = nova;
        }
    }

    public void Insere_Final(int info) {
        No nova = new No(fim, null, info);
        if (inicio == null) {
            inicio = fim = nova;
        } else {
            fim.setProx(nova);
            fim = nova;
        }
    }

    public void Exibe() {
        No aux = inicio;
        while (aux != null) {
            System.out.println(aux.getInfo());
            aux = aux.getProx();
        }
    }

    public No Busca_Exaustiva(int info) {
        No aux = inicio;
        while (aux != null && aux.getInfo() != info) {
            aux = aux.getProx();
        }

        return aux;
    }

    public void Remove(int info) {

        No aux = Busca_Exaustiva(info);
        if (aux != null) {
            if (inicio == fim) {
                Inicializa();
            } else if (aux == inicio) {
                inicio = inicio.getProx();
                inicio.setAnt(null);
            } else if (aux == fim) {
                fim = fim.getAnt();
                fim.setProx(null);
            } else {
                aux.getAnt().setProx(aux.getProx());
                aux.getProx().setAnt(aux.getAnt());
            }
        }
    }

    //Métodos de Ordenação
    public void Insercao_Direta() {
        int num;
        No no = inicio.getProx();
        No aux, aux2;
        while (no != null) {
            aux = no;
            aux2 = no.getAnt();
            num = no.getInfo();
            while (aux2 != null && num < aux2.getInfo()) {
                aux.setInfo(aux2.getInfo());
                aux = aux2;
                aux2 = aux2.getAnt();
            }

            aux.setInfo(num);

            no = no.getProx();
        }
    }

     public void Selecao_Direta()
    {
        No pi=inicio, pmenor, pj;
        int menor;
        while(pi.getProx()!=null)
        {
            menor=pi.getInfo();
            pmenor=pi;
            pj=pi.getProx();
            while(pj!=null)
            {
                if(pj.getInfo()<menor)
                {
                    menor=pj.getInfo();
                    pmenor=pj;
                }
                pj=pj.getProx();
            }
            pmenor.setInfo(pi.getInfo());
            pi.setProx(pj);
        }
    }
    
    public void Bolha() {
        No TL2 = fim, pi;
        int aux;
        while (TL2 != inicio) {
            pi = inicio;
            while (pi != TL2) {
                if (pi.getInfo() > pi.getProx().getInfo()) {
                    aux = pi.getInfo();
                    pi.setInfo(pi.getProx().getInfo());
                    pi.getProx().setInfo(aux);
                }
                pi = pi.getProx();
            }
            TL2 = TL2.getAnt();
        }
    }
 
    public void Shake() {
        int num;
        No aux;
        No ini2, fim2;
 
        fim2 = fim;
        ini2 = inicio;
 
        while (fim2 != inicio) {
            aux = ini2;
            while (aux != fim2 && aux != fim) {
                if (aux.getInfo() > aux.getProx().getInfo()) {
                    num = aux.getInfo();
                    aux.setInfo(aux.getProx().getInfo());
                    aux.getProx().setInfo(num);
                }
                aux = aux.getProx();
            }
            fim2 = fim2.getAnt();
 
            aux = fim2;
            while (aux != ini2 && aux != inicio) {
                if (aux.getInfo() < aux.getAnt().getInfo()) {
                    num = aux.getInfo();
                    aux.setInfo(aux.getAnt().getInfo());
                    aux.getAnt().setInfo(num);
                }
                aux = aux.getAnt();
            }
            ini2 = ini2.getProx();
        }
    }
    
}
