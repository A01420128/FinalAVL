/*
 * Entrega TreePrint
 *
 * Equipo Bendicion de mi jefa
 *
 * Juan Sebastian Rodriguez Galarza - A01656159
 * Eliu Castillo Ledesma - A01121561
 * Jose Javier Tlacuilo Fuentes - A01420128
 *
 * TC1018 Grupo 1
 *
 *
 *
 */

public class Main {

    public static void main(String args[]) {
        Tree<Integer> tree = new Tree<>();
        int randomSize = (int) (Math.random()*20);
        for (int i = 0; i < randomSize; i++) {
            tree.insertElement((int)(Math.random()*100));
        }
        tree.magicPrint();

//        Tree<Integer> tree = new Tree<>();
//        int[] elements = {14, 16, 13, 98, 43, 1, 2, 6};
//        for (int element: elements) {
//            tree.insertElement(element);
//        }

    }

}
