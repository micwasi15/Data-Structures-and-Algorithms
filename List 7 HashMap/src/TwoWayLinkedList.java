import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwoWayLinkedList<T> {
    private final Element sentinel;

    public TwoWayLinkedList() {
        sentinel = new Element(null);
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    private Element getElement(int index) throws NoSuchElementException {
        if (index < 0) throw new NoSuchElementException();
        Element elem = sentinel.getNext();
        int counter = 0;
        while (elem != sentinel && counter < index) {
            counter++;
            elem = elem.getNext();
        }
        if (elem == sentinel)
            throw new NoSuchElementException();
        return elem;
    }

    private Element getElement(T value) {
        Element elem = sentinel.getNext();
        while (elem != sentinel && !value.equals(elem.getValue())) {
            elem = elem.getNext();
        }
        if (elem == sentinel)
            return null;
        return elem;
    }


    public void add(T value) {
        sentinel.insertBefore(new Element(value));
    }

    public void addAt(int index, T value) throws NoSuchElementException {
        Element newElem = new Element(value);
        if (index == 0)
            sentinel.insertAfter(newElem);
        else {
            Element elem = getElement(index - 1);
            elem.insertAfter(newElem);
        }
    }

    public void clear() {
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    public T get(int index) throws NoSuchElementException {
        Element elem = getElement(index);
        return elem.getValue();
    }

    public void set(int index, T value) throws NoSuchElementException {
        getElement(index).setValue(value);
    }

    public int indexOf(T value) {
        Element elem = sentinel.getNext();
        int counter = 0;
        while (elem != sentinel && !elem.getValue().equals(value)) {
            counter++;
            elem = elem.getNext();
        }
        if (elem == sentinel)
            return -1;
        return counter;
    }


    public boolean isEmpty() {
        return sentinel.getNext() == sentinel;
    }

    public T removeAt(int index) throws NoSuchElementException {
        Element elem = getElement(index);
        elem.remove();
        return elem.getValue();
    }

    public boolean remove(T value) {
        Element elem = getElement(value);
        if (elem == null)
            return false;
        elem.remove();
        return true;
    }

    public int size() {
        Element elem = sentinel.getNext();
        int counter = 0;
        while (elem != sentinel) {
            counter++;
            elem = elem.getNext();
        }
        return counter;
    }

    public Iterator<T> iterator() {
        return new TwoWayLinkedListIterator();
    }

    private class TwoWayLinkedListIterator implements Iterator<T> {
        private Element actElement;

        private TwoWayLinkedListIterator() {
            actElement = sentinel;
        }

        @Override
        public boolean hasNext() {
            return actElement.getNext() != sentinel;
        }

        @Override
        public T next() throws NoSuchElementException {
            if (!hasNext())
                throw new NoSuchElementException();
            actElement = actElement.getNext();
            return actElement.getValue();
        }
    }

    public void insert(
            TwoWayLinkedList<T> anotherList,
            int beforeIndex) throws NoSuchElementException {
        if (this.size() <= beforeIndex || beforeIndex < 0)
            throw new NoSuchElementException();
        Element elem = this.getElement(beforeIndex);
        insertHelper2(anotherList, elem);
    }

    public void insert(
            TwoWayLinkedList<T> anotherList,
            T beforeElement) throws NoSuchElementException {
        Element elem = this.getElement(beforeElement);
        if (elem == null)
            throw new NoSuchElementException();
        insertHelper2(anotherList, elem);
    }
    private void insertHelper(TwoWayLinkedList<T> anotherList, Element elem){
        Iterator<T> i2 = anotherList.iterator();
        T value;
        while (i2.hasNext()) {
            value = i2.next();
            elem.insertBefore(new Element(value));
        }
    }

    private void insertHelper2(TwoWayLinkedList<T> anotherList, Element elem){
        Element previousElem = elem.prev;
        Element firstElem = anotherList.sentinel.next;
        Element lastElement = anotherList.sentinel.prev;

        previousElem.setNext(firstElem);
        firstElem.setPrev(previousElem);
        elem.setPrev(lastElement);
        lastElement.setNext(elem);
        anotherList.clear();
    }

    private class Element {
        private T value;
        private Element next;
        private Element prev;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }

        public Element getPrev() {
            return prev;
        }

        public void setPrev(Element prev) {
            this.prev = prev;
        }

        Element(T data) {
            this.value = data;
        }

        public void insertAfter(Element elem) {
            elem.setNext(this.getNext());
            elem.setPrev(this);
            this.getNext().setPrev(elem);
            this.setNext(elem);
        }

        public void insertBefore(Element elem) {
            elem.setNext(this);
            elem.setPrev(this.getPrev());
            this.getPrev().setNext(elem);
            this.setPrev(elem);
        }

        public void remove() {
            this.getNext().setPrev(this.getPrev());
            this.getPrev().setNext(this.getNext());
        }
    }
}
