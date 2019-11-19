public class Tree<T extends Comparable<T>> {

    private Node<T> root;

    // region Get/Set

    public Node<T> getRoot() { return this.root; }
    public void setRoot(Node<T> root) { this.root = root; }

    // endregion

    // region Tree methods

    public void insertElement(T element) {
//        System.out.println("Inserting: " + element.toString());
        this.root = insertElementRec(element, root, 0, 0);
//        this.magicPrint();
    }

    private Node<T> insertElementRec(T element, Node<T> root, int xOffset, int yOffset) {
        if (root == null) {
            root = new Node<T>(element);
        } else {
            if (element.compareTo(root.getElement()) > 0) {
                root.setRight(insertElementRec(element, root.getRight(), xOffset * 2 + 1, yOffset + 1));
//                this.magicPrint(element, xOffset, yOffset);
                if (calcHeightOf(root.getLeft()) - calcHeightOf(root.getRight()) == -2) {
                    if (element.compareTo(root.getRight().getElement()) > 0) {
                        root = simpleLeftRotation(root);
                    } else {
                        root = doubleLeftRotation(root);
                    }
                }
            }
            if (element.compareTo(root.getElement()) < 0) {
                root.setLeft(insertElementRec(element, root.getLeft(), xOffset * 2, yOffset + 1));
//                this.magicPrint(element, xOffset, yOffset);
                if (calcHeightOf(root.getLeft()) - calcHeightOf(root.getRight()) == 2) {
                    if (element.compareTo(root.getLeft().getElement()) < 0) {
                        root = simpleRightRotation(root);
                    } else {
                        root = doubleRightRotation(root);
                    }
                }
            }
        }

//        this.magicPrint(element, xOffset, yOffset);
        int height = max(calcHeightOf(root.getLeft()), calcHeightOf(root.getRight())) + 1;
        root.setHeight((height));

        return root;
    }
    // endregion

    // region Tree helping methods

    private Node<T> simpleLeftRotation(Node<T> root) {
        Node<T> temp = root.getRight();

        root.setRight(temp.getLeft());
        temp.setLeft(root);

        root.setHeight(max(calcHeightOf(root.getLeft()), calcHeightOf(root.getRight())) + 1);
        temp.setHeight(max(calcHeightOf(root), calcHeightOf(temp.getRight())) + 1);

        return temp;
    }

    private Node<T> simpleRightRotation(Node<T> root) {
        Node<T> temp = root.getLeft();

        root.setLeft(root.getRight());
        temp.setRight(root);

        root.setHeight(max(calcHeightOf(root.getLeft()), calcHeightOf(root.getRight())) + 1);
        temp.setHeight(max(calcHeightOf(temp.getLeft()), calcHeightOf(root)) + 1);

        return temp;
    }

    private Node<T> doubleLeftRotation(Node<T> root) {
        root.setRight(simpleRightRotation(root.getRight()));
        return simpleLeftRotation(root);
    }

    private Node<T> doubleRightRotation(Node<T> root) {
        root.setLeft(simpleLeftRotation(root.getLeft()));
        return simpleRightRotation(root);
    }

    private int max(int a, int b) {
        int result = a > b ? a : b;
        return result;
    }

    private int calcHeightOf(Node<T> root) {
        if (root == null) { return -1; }
        return root.getHeight();
    }

    // endregion

    // region magic print

    /*

    xxxxxxxxxxxxxxx_______________90_______________
    xxxxxxx_______90_______xxxxxxxxxxxxxxxx_______90_______
    xxx___09___xxxxxxxx___90___xxxxxxxx___90___xxxxxxxx___90___
    x_09_xxxx_90_xxxx_90_xxxx_90_xxxx_90_xxxx_90_xxxx_90_xxxx_90_
    90xx90xx90xx90xx90xx90xx90xx90xx90xx90xx90xx90xx90xx90xx90xx90

    0 1 3 7 15 length: 5
    4 3 2 1 0
    1 2 3 4 16

    2 4 8 16 32

    length - xoff - 1 pow 2 - 1

    length - xoff pow 2

    */
    public void magicPrint(T insElement, int xInsOffset, int yInsOffset) {
        if (this.root != null) {
            int rootHeight = this.root.getHeight();
            int xSizeMax = (int) Math.pow(2, rootHeight);
            String[][] nodeArray = new String[rootHeight + 1][xSizeMax];
            sortNodesRec(nodeArray, this.root, 0, 0);
            printArray(nodeArray, insElement, xInsOffset, yInsOffset);
        }

    }

    public void magicPrint() {
        if (this.root != null) {
            int rootHeight = this.root.getHeight();
            int xSizeMax = (int) Math.pow(2, rootHeight);
            String[][] nodeArray = new String[rootHeight + 1][xSizeMax];
            sortNodesRec(nodeArray, this.root, 0, 0);
            printArray(nodeArray);
        }

    }

    private void sortNodesRec(String[][] array, Node<T> temp, int xOffset, int yOffset) {
        if (temp != null) {
            array[yOffset][xOffset] = temp.getElement().toString();
            sortNodesRec(array, temp.getLeft(), xOffset * 2, yOffset + 1);
            sortNodesRec(array, temp.getRight(), xOffset * 2 + 1, yOffset + 1);
        }
    }

    private void printArray(String[][] array, T insElement, int xInsOffset, int yInsOffset) {
        for (int i = 0; i < array.length; i++) {
            String result = "";
            result += initialSpaces(i, array.length);
            int xMaxNode = (int) Math.pow(2, i);
            for (int j = 0; j < xMaxNode; j++) {
                if (array[i][j] != null) {
                    // Imprime si hay rama izquierda
                    if (i + 1 < array.length) {
                        boolean hasLeft = array[i + 1][j * 2] != null;
                        result += branches(i, array.length, hasLeft);
                    }
                    // Imprime nodo
                    result += (xInsOffset == i && yInsOffset == j) ? insElement.toString() : array[i][j];
                    // Imprime si hay rama derecha
                    if (i + 1 < array.length) {
                        boolean hasRight = array[i + 1][j * 2 +  1] != null;
                        result += branches(i, array.length, hasRight);
                    }
                } else {
                    // Imprime espacio si no hay nodo
                    // TODO: Maybe put insElement here.
                    result += branches(i, array.length, false) + "  " + branches(i, array.length, false);
                }
                // Imprime espacio libre.
                result += spacing(i, array.length);
            }
            System.out.println(result);
        }
        System.out.println("---------------------");
    }

    private void printArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            String result = "";
            result += initialSpaces(i, array.length);
            int xMaxNode = (int) Math.pow(2, i);
            for (int j = 0; j < xMaxNode; j++) {
                if (array[i][j] != null) {
                    // Imprime si hay rama izquierda
                    if (i + 1 < array.length) {
                        boolean hasLeft = array[i + 1][j * 2] != null;
                        result += branches(i, array.length, hasLeft);
                    }
                    // Imprime nodo
                    result += array[i][j];
                    // Imprime si hay rama derecha
                    if (i + 1 < array.length) {
                        boolean hasRight = array[i + 1][j * 2 +  1] != null;
                        result += branches(i, array.length, hasRight);
                    }
                } else {
                    // Imprime espacio si no hay nodo
                    // TODO: Maybe put insElement here.
                    result += branches(i, array.length, false) + "  " + branches(i, array.length, false);
                }
                // Imprime espacio libre.
                result += spacing(i, array.length);
            }
            System.out.println(result);
        }
        System.out.println("---------------------");
    }

    private String initialSpaces(int yOffset, int yLength) {
        int spaceNum = (int) Math.pow(2, yLength - yOffset - 1) - 1;
        return chainOfChar(spaceNum, ' ');
    }

    private String branches(int yOffset, int yLength, boolean hasBranch) {
        int spaceNum = (int) Math.pow(2, yLength - yOffset - 1) - 1;
        if (hasBranch) {
            return chainOfChar(spaceNum, '_');
        } else {
            return  chainOfChar(spaceNum, ' ');
        }
    }

    private String spacing(int yOffset, int yLength) {
        int spaceNum = (int) Math.pow(2, yLength - yOffset);
        return chainOfChar(spaceNum, ' ');
    }

    private String chainOfChar(int length, char usingChar) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += usingChar;
        }
        return result;
    }

    // endregion

}
