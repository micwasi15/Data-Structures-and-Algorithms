public class BinarySearchTree<T extends Comparable<T>> {
    private Node root;

    public void add(T value) throws DuplicateElementException {
        // TODO: Dodawanie nowej wartości do drzewa. Rzuć DuplicateElementException, jeśli element już istnieje.
        Node position = root;
        Node parent = null;
        int comp;
        while (position != null) {
            parent = position;
            comp = value.compareTo(position.key);
            if (comp < 0)
                position = position.left;
            else if (comp > 0)
                position = position.right;
            else
                throw new DuplicateElementException();
        }
        Node newNode = new Node(value, parent);
        if (parent == null)
            root = newNode;
        else if (value.compareTo(parent.key) < 0)
            parent.left = newNode;
        else
            parent.right = newNode;
    }

    public boolean contains(T value) {
        // TODO: Sprawdzenie, czy drzewo zawiera podaną wartość.
        return findNode(value) != null;
    }

    private Node findNode(T value) {
        Node node = root;
        int comp;
        while (node != null) {
            comp = value.compareTo(node.key);
            if (comp == 0)
                return node;
            else if (comp < 0)
                node = node.left;
            else
                node = node.right;
        }
        return null;
    }

    public void delete(T value) {
        // TODO: Usunięcie wskazanej wartości z drzewa.
        Node node = findNode(value);
        if (node == null)
            return;
        Node x;
        Node remNode;
        if (node.left == null || node.right == null)    // maksymalnie 1 potomek
            remNode = node;
        else                                            // 2 potomkow
            remNode = treeSuccessor(node);
        if (remNode.left != null)
            x = remNode.left;
        else
            x = remNode.right;
        if (x != null)
            x.parent = remNode.parent;
        if (remNode.parent == null)
            root = x;
        else if (remNode == remNode.parent.left)
            remNode.parent.left = x;
        else remNode.parent.right = x;
        if (remNode != node)
            swapKey(node, remNode);
    }

    private Node treeSuccessor(Node node) {
        if (node.right != null) {
            return minimum(node.right);
        }
        Node ancestor = node.parent;
        while (ancestor != null && node == ancestor.right) {
            node = ancestor;
            ancestor = ancestor.parent;
        }
        return ancestor;
    }

    private Node minimum(Node node){
        while (node.left != null)
            node = node.left;
        return node;
    }

    private void swapKey(Node x, Node y) {
        T temp = x.key;
        x.key = y.key;
        y.key = temp;
    }

    public String toStringPreOrder() {
        // TODO: Zwróć wartość String reprezentującą drzewo po przejściu metodą pre-order.
        if (root == null)
            return "";
        IExecutor<T, String> exec = new IntegerToStringExec();
        preOrderHelper(exec, root);
        return exec.getResult();
    }

    private <R> void preOrderHelper(IExecutor<T, R> exec, Node node) {
        if (node != null) {
            exec.execute(node.key);
            preOrderHelper(exec, node.left);
            preOrderHelper(exec, node.right);
        }
    }

    public String toStringInOrder() {
        // TODO: Zwróć wartość String reprezentującą drzewo po przejściu metodą in-order.
        if (root == null)
            return "";
        IExecutor<T, String> exec = new IntegerToStringExec();
        inOrderHelper(exec, root);
        return exec.getResult();
    }

    private <R> void inOrderHelper(IExecutor<T, R> exec, Node node) {
        if (node != null) {
            inOrderHelper(exec, node.left);
            exec.execute(node.key);
            inOrderHelper(exec, node.right);
        }
    }

    public <R> void inOrderWithExecutor(IExecutor<T, R> exec) {
        inOrderHelper(exec, root);
    }

    public String toStringPostOrder() {
        // TODO: Zwróć wartość String reprezentującą drzewo po przejściu metodą in-order.
        if (root == null)
            return "";
        IExecutor<T, String> exec = new IntegerToStringExec();
        postOrderHelper(exec, root);
        return exec.getResult();
    }

    private <R> void postOrderHelper(IExecutor<T, R> exec, Node node) {
        if (node != null) {
            postOrderHelper(exec, node.left);
            postOrderHelper(exec, node.right);
            exec.execute(node.key);
        }
    }

    private class IntegerToStringExec implements IExecutor<T, String> {
        StringBuffer line = new StringBuffer();

        @Override
        public void execute(T elem) {
            line.append(elem).append(", ");
        }

        @Override
        public String getResult() {
            line.delete(line.length() - 2, line.length());
            return line.toString();
        }
    }

    private class Node {
        private T key;
        private Node parent;
        private Node left;
        private Node right;

        public Node(T key, Node parent) {
            this.key = key;
            this.parent = parent;
        }
    }
}
