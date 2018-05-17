
package arvorenarea;


public class Aplicacao {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       ArvoreNArea arvore = new ArvoreNArea();
       arvore.insere(23,3);
       arvore.insere(17,3);
       arvore.insere(8,3);
       arvore.insere(15,3);
       arvore.insere(9,3);
       arvore.insere(12,3);
       arvore.insere(19,3);
       arvore.insere(7,3);
       arvore.exibirInOrdem();
       arvore.exibirEmNivel();
       
    }
    
}
