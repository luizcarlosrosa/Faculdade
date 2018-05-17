package arvoreb;
//melhorar algumas ligações não ficam com null, (mas estão sendo controladas pelo tl)
//procurar o substituto dos dois lados, se da direita precisar fundir, pega da esquerda

public class Principal {

    public static void main(String[] args) {
        ArvoreB arvore;
        arvore = new ArvoreB();
    
        arvore.insereArvore(3);
        arvore.insereArvore(4);
        arvore.insereArvore(8);
        arvore.insereArvore(9);
        arvore.insereArvore(10);
        arvore.insereArvore(11);
        arvore.insereArvore(13);
        arvore.insereArvore(17);
        arvore.insereArvore(20);
        arvore.insereArvore(25);
        arvore.insereArvore(28);
        arvore.insereArvore(30);
        arvore.insereArvore(33);
        arvore.insereArvore(36);
        arvore.insereArvore(40);
        arvore.insereArvore(50);
        arvore.insereArvore(43);
        arvore.insereArvore(45);
        arvore.insereArvore(48);
        arvore.insereArvore(50);
        arvore.insereArvore(52);
        arvore.insereArvore(55);
        
        
        arvore.InOrdem();
        System.out.println();
        
        arvore.exclui(45);
        arvore.exclui(30);
        arvore.exclui(28);
        
        arvore.exclui(50);
        arvore.exclui(8);
        arvore.exclui(10);
        arvore.exclui(4);
        arvore.exclui(20);
        arvore.exclui(40);
        arvore.exclui(55);
        arvore.exclui(17);
        arvore.exclui(33);
        arvore.exclui(11);
        arvore.exclui(36);
        
        arvore.exclui(3);
        arvore.exclui(9);
        arvore.exclui(52);

         arvore.InOrdem();
        System.out.println();
        
        
        

    }

}
