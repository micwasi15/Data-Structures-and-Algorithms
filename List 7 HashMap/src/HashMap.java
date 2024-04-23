import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class HashMap<TKey, TValue> {

    private final int initialSize;
    private final double loadFactor;
    private Function<TKey, Integer> hashFunction;
    private TwoWayLinkedList<Node>[] list;
    private int elements;

    public HashMap(int initialSize, double loadFactor, Function<TKey, Integer> hashFunction) {
        // TODO: Zainicjuj nową instancję klasy HashMap według podanych parametrów.
        //    InitialSize - początkowy rozmiar HashMap
        //    LoadFactor - stosunek elementów do rozmiaru HashMap po przekroczeniu którego należy podwoić rozmiar HashMap.
        //    HashFunction - funkcja, według której liczony jest hash klucza.
        //    Przykład użycia:   int hash = hashFunction.apply(key);
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.hashFunction = hashFunction;
        clear();
    }

    public void add(TKey key, TValue value) throws DuplicateKeyException {
        // TODO: Dodaj nową parę klucz-wartość. Rzuć wyjątek DuplicateKeyException, jeżeli dany klucz już istnieje w HashMap.
        if (containsKey(key))
            throw new DuplicateKeyException();
        int hash = hashFunction.apply(key);
        list[hash % size()].add(new Node(key, value));
        elements++;
        if ((float) elements / size() >= loadFactor)
            resize(2);
    }

    private void resize(int multiplier) throws InputMismatchException {
        if (multiplier < 1)
            throw new InputMismatchException();
        int hash;
        Iterator<Node> iterator;
        Node node;
        TwoWayLinkedList<Node>[] copy = list;
        list = new TwoWayLinkedList[size() * multiplier];
        for (int i = 0; i < size(); i++)
            list[i] = new TwoWayLinkedList<>();
        for (TwoWayLinkedList<Node> linkedList : copy) {
            iterator = linkedList.iterator();
            while (iterator.hasNext()) {
                node = iterator.next();
                hash = hashFunction.apply(node.key);
                list[hash % size()].add(node);
            }
        }
    }

    public void clear() {
        // TODO: Wyczyść zawartość HashMap.
        list = new TwoWayLinkedList[initialSize];
        for (int i = 0; i < size(); i++)
            list[i] = new TwoWayLinkedList<>();
        elements = 0;
    }

    public boolean containsKey(TKey key) {
        // TODO: Sprawdź, czy HashMap zawiera już dany klucz.
        return findNode(key) != null;
    }

    public boolean containsValue(TValue value) {
        // TODO: Sprawdź, czy HashMap zawiera już daną wartość.
        Iterator<Node> iterator;
        Node node;
        for (TwoWayLinkedList<Node> linkedList : list) {
            iterator = linkedList.iterator();
            while (iterator.hasNext()) {
                node = iterator.next();
                if (node.value.equals(value))
                    return true;
            }
        }
        return false;
    }

    public int elements() {
        // TODO: Zwróć liczbę par klucz-wartość przechowywaną w HashMap.
        return elements;
    }

    public TValue get(TKey key) throws NoSuchElementException {
        // TODO: Pobierz wartość powiązaną z danym kluczem. Rzuć wyjątek NoSuchElementException, jeżeli dany klucz nie istnieje.
        Node node = findNode(key);
        if (node == null)
            throw new NoSuchElementException();
        else
            return node.value;
    }

    public void put(TKey key, TValue value) {
        // TODO: Przypisz daną wartość do danego klucza.
        //   Jeżeli dany klucz już istnieje, nadpisz przypisaną do niego wartość.
        //   Jeżeli dany klucz nie istnieje, dodaj nową parę klucz-wartość.
        Node node = findNode(key);
        if (node == null)
            try {
                add(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        else node.value = value;
    }

    public TValue remove(TKey key) {
        // TODO: Usuń parę klucz-wartość, której klucz jest równy podanej wartości.
        int hash = hashFunction.apply(key);
        TwoWayLinkedList<Node> linkedList = list[hash % size()];
        Node node = findElement(key, linkedList);
        if (node == null)
            return null;
        elements--;
        linkedList.remove(node);
        return node.value;
    }

    public int size() {
        // TODO: Zwróć obecny rozmiar HashMap.
        return list.length;
    }

    private Node findNode(TKey key) {
        int hash = hashFunction.apply(key);
        TwoWayLinkedList<Node> linkedList = list[hash % size()];
        return findElement(key, linkedList);
    }

    private Node findElement(TKey key, TwoWayLinkedList<Node> linkedList) {
        Iterator<Node> iterator = linkedList.iterator();
        Node node;
        while (iterator.hasNext()) {
            node = iterator.next();
            if (node.key == key)
                return node;
        }
        return null;
    }

    public void rehash(Function<TKey, Integer> newHashFunction) {
        // TODO: Zmień obecną funkcję hashującą na nową (wymaga przeliczenia dla wszystkich par klucz-wartość).
        hashFunction = newHashFunction;
        resize(1);
    }

    private class Node {
        private final TKey key;
        private TValue value;

        public Node(TKey key, TValue value) {
            this.key = key;
            this.value = value;
        }
    }
}
