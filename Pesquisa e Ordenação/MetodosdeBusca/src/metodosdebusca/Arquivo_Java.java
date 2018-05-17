package metodosdebusca;

import java.io.IOException;
import java.io.RandomAccessFile;

//... classe Arquivo (onde vai estar o método para ordernar, etc) ....
public class Arquivo_Java {

    private String nomearquivo;
    private RandomAccessFile arquivo;

    public Arquivo_Java(String nomearquivo) {
        try {
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (IOException e) {
        }
    }

    public void truncate(long pos) //desloca eof
    {
        try {
            arquivo.setLength(pos * Registro.length());
        } catch (IOException exc) {
        }
    }

    //semelhante ao feof() da linguagem C
    //verifica se o ponteiro esta no <EOF> do arquivo
    public boolean eof() {
        boolean retorno = false;
        try {
            if (arquivo.getFilePointer() == arquivo.length()) {
                retorno = true;
            }
        } catch (IOException e) {
        }
        return (retorno);
    }

    private int filesize() throws IOException {

        return (int) arquivo.length() / Registro.length();
    }

    //insere um Registro no final do arquivo, passado por parâmetro
    public void inserirRegNoFinal(Registro reg) throws IOException {
        seekArq(filesize());//ultimo byte
        reg.gravaNoArq(arquivo);
    }

    public void exibirArq() {
        int i;
        Registro aux = new Registro();
        seekArq(0);
        i = 0;
        while (!this.eof()) {
            System.out.println("Posicao " + i);
            aux.leDoArq(arquivo);
            aux.exibirReg();
            i++;
        }
    }

    public void exibirUmRegistro(int pos) {
        Registro aux = new Registro();
        seekArq(pos);
        System.out.println("Posicao " + pos);
        aux.leDoArq(arquivo);
        aux.exibirReg();
    }

    public void seekArq(int pos) {
        try {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e) {
        }
    }

    public void leArq() throws IOException {
        int codigo, idade;
        String nome;
        codigo = Entrada.leInteger("Digite o código");
        while (codigo != 0) {
            nome = Entrada.leString("Digite o nome");
            idade = Entrada.leInteger("Digite a idade");
            inserirRegNoFinal(new Registro(codigo, nome, idade));
            codigo = Entrada.leInteger("Digite o código");
        }
    }

    public int Busca_Binaria(int aux, int i) throws IOException {
        Registro novo = new Registro();
        int meio, inicio, fim;
        inicio = 0;
        fim = filesize() - 1;
        meio = fim / 2;
        seekArq(meio);
        novo.leDoArq(arquivo);
        while (inicio < fim && novo.getCodigo() != aux) {
            if (novo.getCodigo() > aux) {
                fim = meio;
            }
            if (novo.getCodigo() < aux) {
                inicio = meio + 1;
            }
            meio = (inicio + fim) / 2;
            seekArq(meio);
            novo.leDoArq(arquivo);
        }
        seekArq(meio);
        novo.leDoArq(arquivo);
        if (novo.getCodigo() == aux) {
            return meio;
        } else if (aux > novo.getCodigo()) {
            return meio + 1;
        }

        return 0;
    }

    //Métodos de Ordenação
    public void Insercao_Direta() throws IOException {
        Registro aux = new Registro();
        Registro aux2 = new Registro();
        int pos, i;
        for (i = 1; i < filesize(); i++) {
            pos = i;
            seekArq(pos);
            aux.leDoArq(arquivo);
            seekArq(pos - 1);
            aux2.leDoArq(arquivo);
            while (pos > 0 && aux2.getCodigo() > aux.getCodigo()) {
                seekArq(pos);
                aux2.gravaNoArq(arquivo);
                pos--;
            }
            seekArq(pos);
            aux.gravaNoArq(arquivo);
        }
    }

    public void Insercao_Binaria() throws IOException {
        Registro aux = new Registro();
        Registro aux2 = new Registro();
        int i, pos = 0;
        for (i = 0; i < filesize(); i++) {
            seekArq(i);
            aux2.leDoArq(arquivo);
            pos = Busca_Binaria(aux.getCodigo(), i);
            if (pos > i) {
                pos -= i;
            }
            for (int j = i; j > pos; j--) {
                seekArq(j);
                aux.leDoArq(arquivo);
                seekArq(j - 1);
                aux.gravaNoArq(arquivo);
            }
            seekArq(pos);
            aux.gravaNoArq(arquivo);
        }
    }

    public void Selecao_Direta() throws IOException {
        int menor, posmenor, TL = filesize();
        Registro regi = new Registro();
        Registro regj = new Registro();

        for (int i = 0; i < TL - 1; i++) {
            seekArq(i);
            regi.leDoArq(arquivo);
            menor = regi.getCodigo();
            posmenor = i;

            for (int j = i + 1; j < TL; j++) {
                regj.leDoArq(arquivo);
                if (regj.getCodigo() < menor) {
                    menor = regj.getCodigo();
                    posmenor = j;
                }
                seekArq(posmenor);
                regj.leDoArq(arquivo);
                seekArq(posmenor);
                regi.gravaNoArq(arquivo);
                seekArq(i);
                regi.gravaNoArq(arquivo);
            }
        }
    }

    public void Bolha() throws IOException {
        int tl2 = filesize();
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        while (tl2 > 1) {

            for (int i = 0; i < tl2 - 1; i++) {
                seekArq(i);
                reg1.leDoArq(arquivo);
                seekArq(i + 1);
                reg2.leDoArq(arquivo);
                if (reg1.getCodigo() > reg2.getCodigo()) {
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    reg1.gravaNoArq(arquivo);
                }
            }
            tl2--;
        }
    }

    void Shake() throws IOException {
        int fim = filesize() - 1, inicio = 0;
        Registro aux = new Registro();
        Registro aux2 = new Registro();

        while (inicio < fim) {
            for (int i = inicio; i < fim; i++) {
                seekArq(i);
                aux.leDoArq(arquivo);
                seekArq(i + 1);
                aux2.leDoArq(arquivo);
                if (aux.getCodigo() > aux2.getCodigo()) {
                    seekArq(i);
                    aux.leDoArq(arquivo);
                    seekArq(i + 1);
                    aux2.leDoArq(arquivo);
                    seekArq(i);
                    aux2.gravaNoArq(arquivo);
                    seekArq(i + 1);
                    aux.gravaNoArq(arquivo);
                }
            }
            fim--;
            for (int i = fim; i > inicio; i--) {
                seekArq(i);
                aux.leDoArq(arquivo);
                seekArq(i - 1);
                aux2.leDoArq(arquivo);
                if (aux.getCodigo() < aux2.getCodigo()) {
                    seekArq(i);
                    aux.leDoArq(arquivo);
                    seekArq(i - 1);
                    aux2.leDoArq(arquivo);
                    seekArq(i);
                    aux2.gravaNoArq(arquivo);
                    seekArq(i - 1);
                    aux.gravaNoArq(arquivo);
                }
            }
            inicio++;
        }
    }

    public void Heap() throws IOException {
        int pai, f1, f2, maior, tl = filesize();
        Registro aux = new Registro();
        Registro aux1 = new Registro();
        while (tl > 1) {
            for (pai = tl / 2 - 1; pai > -1; pai--) {
                f1 = pai + pai + 1;
                f2 = f1 + 2;
                maior = f1;
                if (f2 < tl) {
                    seekArq(f2);
                    aux.leDoArq(arquivo);
                    seekArq(maior);
                    aux1.leDoArq(arquivo);
                    if (aux.getCodigo() > aux1.getCodigo()) {
                        maior = f2;
                    }
                }
                seekArq(maior);
                aux.leDoArq(arquivo);
                seekArq(pai);
                aux1.leDoArq(arquivo);
                if (aux.getCodigo() > aux1.getCodigo()) {
                    seekArq(pai);
                    aux.gravaNoArq(arquivo);
                    seekArq(maior);
                    aux1.gravaNoArq(arquivo);
                }
            }
            seekArq(0);
            aux.leDoArq(arquivo);
            seekArq(tl - 1);
            aux1.leDoArq(arquivo);
            seekArq(0);
            aux1.gravaNoArq(arquivo);
            seekArq(tl - 1);
            aux.gravaNoArq(arquivo);

            tl--;
        }

    }

    public void Shell() throws IOException {
        int j = 0, dist = 4, i, k = 0;
        Registro aux1 = new Registro(), aux2 = new Registro();
        while (dist > 0) {
            i = 0;
            while (i < dist) {
                j = i;
                while (j + dist < filesize()) {
                    seekArq(j);
                    aux1.leDoArq(arquivo);
                    seekArq(j + dist);
                    aux2.leDoArq(arquivo);
                    if (aux1.getCodigo() > aux2.getCodigo()) {
                        seekArq(j);
                        aux2.gravaNoArq(arquivo);
                        seekArq(j + dist);
                        aux1.gravaNoArq(arquivo);
                        k = j;
                        if (k - dist >= 0) {
                            seekArq(k - dist);
                            aux2.leDoArq(arquivo);
                            seekArq(k);
                            aux1.leDoArq(arquivo);

                            while (k - dist >= 0 && aux1.getCodigo() < aux2.getCodigo()) {
                                seekArq(k - dist);
                                aux1.gravaNoArq(arquivo);
                                seekArq(j);
                                aux2.gravaNoArq(arquivo);
                                k -= dist;
                                if (k - dist >= 0) {
                                    seekArq(k - dist);
                                    aux2.leDoArq(arquivo);
                                    seekArq(k);
                                    aux1.leDoArq(arquivo);

                                }
                            }
                        }
                    }
                    j += dist;
                }
                i++;
            }
            dist = dist / 2;
        }
    }

    public void QuickSemPivo(int ini, int fim) throws IOException {

        int i = ini, j = fim;
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        while (i < j) {
            seekArq(j);
            reg2.leDoArq(arquivo);
            seekArq(i);
            reg.leDoArq(arquivo);
            while (i < j && reg.getCodigo() <= reg2.getCodigo()) {
                i++;
                seekArq(i);
                reg.leDoArq(arquivo);
            }
            if (i < j) {
                seekArq(j);
                reg2.leDoArq(arquivo);
                seekArq(i);
                reg.leDoArq(arquivo);
                seekArq(j);
                reg.gravaNoArq(arquivo);
                seekArq(i);
                reg2.gravaNoArq(arquivo);
            }
            seekArq(j);
            reg2.leDoArq(arquivo);
            seekArq(i);
            reg.leDoArq(arquivo);
            while (i < j && reg2.getCodigo() >= reg.getCodigo()) {
                j--;
                seekArq(j);
                reg2.leDoArq(arquivo);
            }
            if (i < j) {
                seekArq(j);
                reg2.leDoArq(arquivo);
                seekArq(i);
                reg.leDoArq(arquivo);
                seekArq(j);
                reg.gravaNoArq(arquivo);
                seekArq(i);
                reg2.gravaNoArq(arquivo);
            }
        }
        if (0 < i - 1) {
            QuickSemPivo(0, i - 1);
        }
        if (j + 1 < fim) {
            QuickSemPivo(j + 1, fim);
        }
    }

    private void Particao(Registro[] vet1, Registro[] vet2) throws IOException {
        int i = 0, met = filesize() / 2;
        while (i < met) {
            seekArq(i);
            vet1[i] = new Registro();
            vet1[i].leDoArq(arquivo);
            seekArq(i + met);
            vet2[i] = new Registro();
            vet2[i++].leDoArq(arquivo);

        }
    }

    private void Fusao(Registro[] vet1, Registro[] vet2, int seq) throws IOException {
        int i = 0, j = 0, k = 0;
        int tseq = seq;
        while (k < filesize()) {
            while (i < seq && j < seq) {
                if (vet1[i].getCodigo() < vet2[j].getCodigo()) {
                    seekArq(k++);
                    vet1[i++].gravaNoArq(arquivo);

                } else {
                    seekArq(k++);
                    vet2[j++].gravaNoArq(arquivo);
                }
            }
            while (i < seq) {
                seekArq(k++);
                vet1[i++].gravaNoArq(arquivo);
            }
            while (j < seq) {
                seekArq(k++);
                vet2[j++].gravaNoArq(arquivo);
            }
            seq += tseq;

        }
    }

    public void Merge() throws IOException {
        Registro vet1[] = new Registro[filesize() / 2];
        Registro vet2[] = new Registro[filesize() / 2];
        int seq = 1;
        while (seq < filesize()) {
            Particao(vet1, vet2);
            Fusao(vet1, vet2, seq);
            seq *= 2;
        }
    }

    public void executa() throws IOException {
        leArq();
        Selecao_Direta();
        exibirArq();

    }

}
