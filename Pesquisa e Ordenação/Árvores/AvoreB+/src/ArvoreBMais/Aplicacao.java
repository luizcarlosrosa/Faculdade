
package ArvoreBMais;


public class Aplicacao {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArvoreBMais Bmais = new ArvoreBMais();
        Bmais.insere(8);
        Bmais.insere(13);
        Bmais.insere(21);
        Bmais.insere(25);
        Bmais.insere(28);
        Bmais.insere(36);
        Bmais.insere(53);
        Bmais.insere(75);
        Bmais.insere(91);
        Bmais.insere(95);
        Bmais.insere(15);
        Bmais.insere(26);
        Bmais.inOrdem();
    }
    
}
