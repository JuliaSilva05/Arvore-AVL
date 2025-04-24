import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class AVLTree<T extends Comparable<T>> {

    private AVLNode<T> root;
    private boolean status;
    
    public boolean isEmpty () {
        if (this.root == null) {
            return true;
        }
        else {
            return false;
        }
    }

    public void insert(T value){
        if (this.isEmpty()==true){
            this.root = new AVLNode<T>(value);
        }
        else{
            this.root = insertNode(this.root, value);
            this.status = false;
        }
    }
    private AVLNode<T> insertNode(AVLNode<T> r, T value){
        if (r==null){
            r = new AVLNode<T>(value);
            this.status = true;
        }
        else if (r.getInfo().compareTo(value)>0){
            r.setLeft(insertNode(r.getLeft(),value));
            if (this.status==true){
                switch (r.getFatBal()){
                    case 1: 
                        r.setFatBal(0);
                        this.status = false;
                        break;
                    case 0:
                        r.setFatBal(-1);
                        break;
                    case -1:
                        r = this.rotateRight(r);
                        break;
                }
            }
        }
        else {
            r.setRight(insertNode(r.getRight(), value));
            if (this.status == true){
                switch(r.getFatBal()){
                    case -1:
                        r.setFatBal(0);
                        this.status = false;
                        break;
                    case 0:
                        r.setFatBal(1);
                        break;
                    case 1:
                        r = this.rotateLeft(r);
                        break;
                }
            }
        }
        return r;
    }


    private AVLNode<T> rotateLeft(AVLNode<T> a){
        AVLNode<T> b, c;
        b = a.getRight();
        if (b.getFatBal()==1){ // rotação simples
            a.setRight(b.getLeft());
            b.setLeft(a);
            a.setFatBal(0);
            a = b;
        }
        else { // rotação dupla
            c = b.getLeft();
            b.setLeft(c.getRight());
            c.setRight(b);
            a.setRight(c.getLeft());
            c.setLeft(a);
            if (c.getFatBal()==1){
                a.setFatBal(-1);
            } else {
                a.setFatBal(0);
            }
            if (c.getFatBal() == -1){
                b.setFatBal(1);
            } else {
                b.setFatBal(0);
            }
            a = c;
        }
        a.setFatBal(0);
        this.status = false;
        return a;
    }

    private AVLNode<T> rotateRight(AVLNode<T> a){
        AVLNode<T> b, c;
        b = a.getLeft();
        if (b.getFatBal()==-1){
            a.setLeft(b.getRight());
            b.setRight(a);
            a.setFatBal(0);
            a = b;
        }
        else {
            c = b.getRight();
            b.setRight(c.getLeft());
            c.setLeft(b);
            a.setLeft(c.getRight());
            c.setRight(a);
            if (c.getFatBal()==-1){
                a.setFatBal(1);
            } else{
                a.setFatBal(0);
            }
            if (c.getFatBal()==1){
                b.setFatBal(-1);
            } else{
                b.setFatBal(0);
            }
            a = c;
        }
        a.setFatBal(0);
        this.status = false;
        return a;
    }

    public void emOrdem(){
        if (this.isEmpty()==true){
            System.out.println("Arvore vazia!");
        } else{
            this.passeioEmOrdem(root);
        }
    }
    private void passeioEmOrdem(AVLNode<T> r){
        if (r != null){
            passeioEmOrdem(r.getLeft());
            System.out.println(r.getInfo());
            //System.out.println(r.getInfo() +" "+ r.getLeft() +" "+ r.getRight());
            passeioEmOrdem(r.getRight());
        }
    }

    public void porNivel(){
        if (this.isEmpty()==true){
            System.out.println("Arvore vazia!");
        } else{
            this.passeioPorNivel(root);
        }
    }
    private void passeioPorNivel(AVLNode<T> r){
        if (r != null){
            Queue<AVLNode<T>> fila = new ArrayDeque<>();
            fila.add(r);
            int i = 0;
            while (fila.isEmpty() == false) {
                //System.out.println(" i"+i);
                i++;
                AVLNode<T> aux = fila.remove();
                /*
                if (aux.getLeft()==null){
                    System.out.print("null ");
                }*/
                System.out.print(aux.getInfo() + " fatbal:" + aux.getFatBal());
                if (aux.getLeft() != null){
                    fila.add(aux.getLeft());
                } 
                if (aux.getRight() != null){
                    fila.add(aux.getRight());
                } /*else {
                    System.out.print(" null");
                }*/
                System.out.println("\n");
            }
        }
        
    }

    public void altura(){
        if (this.isEmpty()==true){
            System.out.println("Arvore vazia!");
        } else{
            System.out.println(this.calcularAlturaArvore(root));
        }
    }
    private int calcularAlturaArvore(AVLNode<T> r){
        int cont = -1;
        AVLNode<T> aux;
        aux = r;
        if (r!=null){
            if (r.getFatBal()==0 ){ // se for equilibrada, é só ir seguindo e contando pra um lado qualquer
                while (aux != null){
                    if (aux.getLeft() != null){
                        aux = aux.getLeft();
                    } else {
                        aux = aux.getRight();
                    }
                    cont++;
                }
            }
            else {
                while (aux != null){
                    if (aux.getFatBal()==1){
                        aux = aux.getRight();
                    } else {
                        aux = aux.getLeft();
                    } 
                    cont++;
                }
            }
            
        }
        return cont;
    }

    public void remocaoPt1(T value){
        if (this.isEmpty()==true){
            System.out.println("Árvore vazia!");
        } else {
            remocaoPt2(root, value);
        }
    }
    private void remocaoPt2(AVLNode<T> r, T value){
        AVLNode<T> pai, filho;
        pai = r;
        filho = r;
        if (r!=null){
            while (filho.getInfo().compareTo(value)!=0){ // procurando
                if (filho.getInfo().compareTo(value) > 0){ // se value for menor
                    pai = filho;
                    filho = filho.getLeft();
                } else if (filho.getInfo().compareTo(value) < 0){ // se value for maior
                    pai = filho;
                    filho = filho.getRight();
                }
            }
            if (filho != null){ //se achar o valor
                // se for uma folha
                if (filho.getLeft()==null && filho.getRight()==null){
                    
                    if (filho.getInfo().compareTo(pai.getInfo()) > 0){
                        //se for uma folha direita
                        pai.setRight(null);
                        pai.setFatBal(pai.getFatBal()-1);
                    } else if (filho.getInfo().compareTo(pai.getInfo()) < 0){
                        //se for uma folha esquerda
                        pai.setLeft(null);
                        pai.setFatBal(pai.getFatBal()+1);
                    } else {
                        // se  folha for a raiz
                        System.out.println("ashdaagd" + pai.getInfo()+filho.getInfo()+r.getInfo());
                        root = null;
                    }
                    filho = null;
                }
                // possui apenas um filho direito
                else if (filho.getLeft() == null){
                    if (filho.getInfo().compareTo(pai.getInfo()) > 0){
                        //se for na direita do pai
                        pai.setRight(filho.getRight());
                        pai.setFatBal(pai.getFatBal()-1);
                    } else if (filho.getInfo().compareTo(pai.getInfo()) < 0){
                        //se for na esquerda do pai
                        pai.setLeft(filho.getRight());
                        pai.setFatBal(pai.getFatBal()+1);
                    } else {
                        System.out.println("kdgs" + filho.getInfo() + pai.getInfo());
                        r.setInfo(filho.getRight().getInfo());
                        r.setRight(null);
                        r.setFatBal(0);
                    }
                    filho.setRight(null);
                    filho = null;
                }
                // possui apenas um filho esquerdo
                else if (filho.getRight() == null){  
                    if (filho.getInfo().compareTo(pai.getInfo()) > 0){
                        //se for na direita do pai
                        pai.setRight(filho.getLeft());
                        pai.setFatBal(pai.getFatBal()-1);
                    } else if (filho.getInfo().compareTo(pai.getInfo()) < 0){
                        //se for na esquerda do pai
                        pai.setLeft(filho.getLeft());
                        pai.setFatBal(pai.getFatBal()+1);
                    } else {
                        System.out.println("kdgs" + filho.getInfo() + pai.getInfo());
                        r.setInfo(filho.getLeft().getInfo());
                        r.setLeft(null);
                        r.setFatBal(0);
                    }
                    filho.setLeft(null);
                    filho = null;
                    
                }
                // possui 2 filhos
                else {
                    // fazer mais testes
                    System.out.println("Tem 2 filhos");
                    AVLNode<T> aux = filho;
                    pai = filho;
                    filho = filho.getLeft();
                    while (filho.getRight() != null){
                        pai = filho;
                        filho = filho.getRight();
                    }
                    aux.setInfo(filho.getInfo());

                    // se filho for a direita de pai
                    if (filho.getInfo().compareTo(pai.getInfo())>0){ 
                        pai.setRight(filho.getLeft());
                        pai.setFatBal(pai.getFatBal()-1);
                        if (aux == r){
                            System.out.println("raiz");
                            aux.setFatBal(aux.getFatBal()+1);
                        }
                    } 
                    // se filho for a esquerda de pai
                    else {
                        pai.setLeft(filho.getLeft());
                        pai.setFatBal(pai.getFatBal()+1);
                    }
                    
                } 

                // se pai ficar com +ou-2
                if (pai.getFatBal()==-2){
                    System.out.println("oi");
                    if (pai == root){
                        root = pai.getLeft();
                    }
                    rotateRight(pai);
                }
                else if (pai.getFatBal()==2){
                    System.out.println("he");
                    if (pai == root){
                        root = pai.getRight();
                    }
                    rotateLeft(pai);
                }
                
            }
        }

    }

    public void procurar(T value){
        if (isEmpty()){
            System.out.println("Árvore vazia!");
        } else {
            if (search(value) != null){
                System.out.println("Valor encontrado!");
            } else {
                System.out.println("Valor não encontrado!");
            }
        }
    }
    private AVLNode<T> search(T val){
        AVLNode<T> aux = this.root;
        while (aux != null) {
            if (aux.getInfo() == val){
                //System.out.println("Valor encontrado!");
                break;
            }
            else if (aux.getInfo().compareTo(val) > 0){ // se aux.getinfo > val
                aux = aux.getLeft();
            }
            else {
                aux = aux.getRight();
            }
        }
        //if (aux == null){
            //System.out.println("Valor não encontrado!");
        //}
        return aux;
    }
}