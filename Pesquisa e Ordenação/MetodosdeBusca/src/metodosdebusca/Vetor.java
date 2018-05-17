package metodosdebusca;

import java.util.Random;

public class Vetor {

    private int vet[];
    private int TL = 8;

    public void Preencher() {
        int numero;
        vet = new int[TL];
        Random r = new Random();
        for (int i = 0; i < vet.length; i++) {
            numero = r.nextInt(60) + 1;
            for (int j = 0; j < vet.length; j++) {
                if (numero == vet[j] && j != i) {
                    numero = r.nextInt(60) + 1;
                } else {
                    vet[i] = numero;
                }
            }
        }
    }

    public void Exibir() {
        for (int i = 0; i < vet.length; i++) {
            System.out.println(vet[i] + " ");
        }
    }

    //Estruturas de Buscas Desordenadas   
    public int Busca_Exaustiva(int chave) {
        int pos = 0;
        while (pos < TL && chave != vet[pos]) {
            pos++;
        }
        if (pos < TL) {
            return pos;
        }
        return -1;
    }

    public int Busca_Sentinela(int chave) {
        int pos = 0;
        vet[TL] = chave; //sentinela

        while (chave != vet[pos]) {
            pos++;
        }
        if (pos < TL) {
            return pos;
        }
        return -1;
    }

    //Estruturas de Buscas Ordenadas   
    public int Busca_Sequencial(int chave) {
        int pos = 0;

        while (pos > TL && chave > vet[pos]) {
            pos++;
        }
        if (pos < TL && chave == vet[pos]) {
            return pos;
        }
        return pos + TL; //não achou, mas retorna a melhor posição
    }

    public int Busca_Binaria(int chave) {
        int inicio = 0, fim = TL - 1, meio;
        meio = inicio + fim / 2;
        while (inicio < fim && chave != vet[meio]) {
            if (chave < vet[meio]) {
                fim = meio;
            } else {
                inicio = meio + 1;
            }

            meio = inicio + fim / 2;
        }
        if (chave == vet[meio]) {
            return meio;
        }
        //achar a melhor posição
        if (chave > vet[meio]) {
            return meio + TL + 1;
        } else {
            return meio + TL;
        }
    }

    public int Busca_Binaria(int chave, int tamanho) {
        int inicio, fim, meio;
        inicio = 0;
        fim = tamanho - 1;
        meio = fim / 2;
        while (inicio < fim && vet[meio] != chave) {
            if (vet[meio] > chave) {
                fim = meio;
            } else {
                inicio = meio + 1;
            }
            meio = (inicio + fim) / 2;
        }
        if (vet[meio] == chave) {
            return meio;
        } else if (chave > vet[meio]) {
            return meio + tamanho + 1;
        }

        return meio + tamanho;
    }

    //Métodos de Ordenação
    public void Insercao_Direta() {
        int i, pos, aux;

        for (i = 1; i < TL; i++) {
            pos = i;
            aux = vet[i];

            while (pos > 0 && vet[pos - 1] > aux) {
                vet[pos] = vet[pos - 1];
                pos--;
            }
            vet[pos] = aux;
        }
    }

    public void Insercao_Binaria() {
        int pos, aux;

        for (int i = 1; i < TL; i++) {
            aux = vet[i];
            pos = Busca_Binaria(aux, i);
            if (pos >= i) {
                pos = pos - i;
            }
            for (int j = i; j > pos; j--) {
                vet[j] = vet[j - 1];
            }
            vet[pos] = aux;
        }
    }

    public void Selecao_Direta() {
        int menor = 9999, posmenor = 0;
        for (int i = 0; i < TL - 1; i++) {
            menor = vet[i];
            posmenor = i;
            for (int j = i + 1; j < TL; j++) {
                if (vet[j] < menor) {
                    menor = vet[j];
                    posmenor = j;
                }
            }
            vet[posmenor] = vet[i];
            vet[i] = menor;

        }
    }

    public void Bolha() {
        int aux, TL2 = TL;

        while (TL2 > 1) {
            for (int i = 0; i < TL2 - 1; i++) {
                if (vet[i] > vet[i + 1]) {
                    aux = vet[i];
                    vet[i] = vet[i + 1];
                    vet[i + 1] = aux;
                }
            }
            TL2--;
        }
    }

    public void Shake() {
        int aux, TL2 = TL, inicio = 0;

        while (TL2 > inicio) {
            for (int i = inicio; i < TL2 - 1; i++) {
                if (vet[i] > vet[i + 1]) {
                    aux = vet[i];
                    vet[i] = vet[i + 1];
                    vet[i + 1] = aux;
                }
            }
            TL2--;
            for (int i = TL2 - 1; i > inicio; i--) {
                if (vet[i] < vet[i - 1]) {
                    aux = vet[i];
                    vet[i] = vet[i - 1];
                    vet[i - 1] = aux;
                }
            }
            inicio++;
        }
    }

    public void Heap() {
        int TL2, aux, pai, filhoesq, filhodir, maiorfilho;
        TL2 = TL;
        while (TL2 > 1) {
            for (pai = TL2 / 2 - 1; pai >= 0; pai--) {
                filhoesq = 2 * pai + 1;
                filhodir = filhoesq + 1;
                maiorfilho = filhoesq;
                if (filhodir > TL2 && vet[filhodir] > vet[filhoesq]) {
                    maiorfilho = filhodir;
                } else {
                    maiorfilho = filhoesq;
                }
                if (vet[maiorfilho] > vet[pai]) {
                    aux = vet[maiorfilho];
                    vet[maiorfilho] = vet[pai];
                    vet[pai] = aux;
                }
            }
            aux = vet[0];
            vet[0] = vet[TL2 - 1];
            vet[TL2 - 1] = aux;
            TL2--;
        }
    }

    public void Shell() {

        int aux;
        for (int dist = 4; dist > 0; dist = dist / 2) {

            for (int i = 0; i < dist; i++) {

                for (int j = i; j + dist < TL; j = j + dist) {
                    if (vet[j] > vet[j + dist]) {
                        aux = vet[j];
                        vet[j] = vet[j + dist];
                        vet[j + dist] = aux;

                        for (int k = j; k - dist >= i && vet[k] < vet[k - dist]; k = k - dist) {
                            aux = vet[k];
                            vet[k] = vet[k - dist];
                            vet[k - dist] = aux;
                        }
                    }
                }

            }

        }

    }

    public void QuicksemPivo() {
        quickSP(0, TL - 1);
    }

    public void quickSP(int ini, int fim) {
        int i = ini, j = fim, aux;
        boolean flag = true;
        while (i < j) {
            if (flag) {
                while (i < j && vet[i] <= vet[j]) {
                    i++;
                }
            } else {
                while (i < j && vet[i] <= vet[j]) {
                    i--;
                }
            }

            aux = vet[i];
            vet[i] = vet[j];
            vet[j] = aux;
            flag = !flag;
        }
        if (ini < i - 1) {
            quickSP(ini, i - 1);
        }
        if (j + 1 < fim) {
            quickSP(j + 1, fim);
        }

    }

    public void QuickcomPivo() {
        quickCP(0, TL - 1);
    }

    public void quickCP(int ini, int fim) {
        int i = ini, j = fim, pivo, aux;
        pivo = vet[(ini + fim) / 2];
        while (i < j) {

            while (vet[i] < pivo) {
                i++;
            }
            while (vet[j] > pivo) {
                j--;
            }
            if (i <= j) {
                aux = vet[i];
                vet[i] = vet[j];
                vet[j] = aux;
                i++;
                j--;
            }

        }
        if (ini < i) {
            quickCP(ini, j);
        }
        if (i < fim) {
            quickCP(i, fim);
        }

    }

    public void particao(int vet1[], int vet2[]) {
        int meio = TL / 2;
        for (int i = 0; i < meio; i++) {
            vet1[i] = vet[i];
            vet2[i] = vet[i + meio];

        }

    }

    public void fusao(int vet1[], int vet2[], int seq) {
        int i = 0, j = 0, k = 0, tseq = seq;
        while (k < TL)//eqnto não terminar a fusao
        {
            while (i < seq && j < seq) {//resolve a sequencia
                if (vet1[i] < vet2[j]) {
                    vet[k++] = vet1[i++];

                } else {
                    vet[k++] = vet2[j++];
                }
            }
            //qndo a sequencia acaba
            while (i < seq) {
                vet[k++] = vet1[i++];
            }
            while (j < seq) {
                vet[k++] = vet2[j++];
            }
            seq = seq + tseq;
        }

    }

    public void Merge() {
        int vet1[] = new int[TL / 2];
        int vet2[] = new int[TL / 2];
        int seq = 1;
        while (seq <= TL / 2) {
            particao(vet1, vet2);
            fusao(vet1, vet2, seq);
            seq *= 2;
        }

    }

    //Algoritmos Trabalho
    public void Combsort() {
        int tam = TL;
        float shrinkFactor = 1.3f;
        boolean swapped = false;

        while (tam > 1 || swapped) {
            if (tam > 1) {
                tam = (int) (tam / shrinkFactor);
            }

            swapped = false;

            for (int i = 0; tam + i < TL; i++) {
                if (vet[i] > vet[i + tam]) {
                    Troca(i, i + tam);
                    swapped = true;
                }
            }
        }
    }

    private void Troca(int a, int b) {
        int aux = vet[a];
        vet[a] = vet[b];
        vet[b] = aux;
    }

    public void GnomeSort() {
        int index = 1;
        int aux;
        while (index < TL) {
            if (vet[index] < vet[index - 1]) {
                aux = vet[index];
                vet[index] = vet[index - 1];
                vet[index - 1] = aux;
                index--;
                if (index == 0) {
                    index = 1;
                }
            } else {
                index++;
            }
        }

    }

}
