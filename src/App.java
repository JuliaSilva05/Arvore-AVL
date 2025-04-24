import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        AVLTree arvore = new AVLTree();
        int op, num;
        Scanner input = new Scanner(System.in);
    
        do {
            exibirOpcoes();
            op = input.nextInt();
            switch(op) {
                case 1: 
                    System.out.println("Diga um numero");
                    num = input.nextInt();
                    arvore.insert(num);
                    break;
                case 2:
                    
                    break;
                case 3: 
                    System.out.println("Em ordem:");
                    arvore.emOrdem();
                    break;
                case 4:
                    System.out.println("Por nível:\n");
                    arvore.porNivel();
                    break;
                case 5:
                    arvore.altura();
                    break;
                case 6:
                    System.out.println("Diga o que deseja remover");
                    num = input.nextInt();
                    arvore.remocaoPt1(num);
                    break;
                case 0:
                    System.out.println("Bye bye!");
                    break;                    
            }
        } while (op != 0);
        input.close();
    }
    public static void exibirOpcoes () {
        System.out.println("Opções");
        System.out.println("1 - Inserir valor");
        System.out.println("2 - Buscar valor");
        System.out.println("3 - Exibir em ordem");
        System.out.println("4 - Exibir por nível");
        System.out.println("5 - Contar a altura da árvore");
        System.out.print("Informe a opção: ");
    }
    
}
