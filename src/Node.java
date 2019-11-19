public class Node<T extends Comparable<T>> {

    private T element;
    private Node<T> left;
    private Node<T> right;
    private int height;

    public Node(T element) {
        this.element = element;
    }

    // region Get/Set methods

    public T getElement() { return this.element; }
    public void setElement(T element) { this.element = element; }

    public Node<T> getLeft() { return this.left; }
    public void setLeft(Node<T> left) { this.left = left; }

    public Node<T> getRight() { return this.right; }
    public void setRight(Node<T> right) { this.right = right; }

    public int getHeight() { return this.height; }
    public void setHeight(int height) { this.height = height; }

    // endregion

}
