import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first;
    private Node last;
    private int n;
    
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return n;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        } else {
            Node oldFirst = first;
            Node newFirst = new Node();
            newFirst.item = item;
            newFirst.next = oldFirst;
            newFirst.prev = null;
            if (isEmpty()) {
                last = newFirst;
            } else {
                oldFirst.prev = newFirst;
            }
            first = newFirst;
            n++;
        }
    }
    
    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        } else {
            Node oldLast = last;
            Node newLast = new Node();
            newLast.item = item;
            newLast.next = null;
            newLast.prev = oldLast;
            if (isEmpty()) {
                first = newLast;
            } else {
                oldLast.next = newLast;
            }
            last = newLast;
            n++;
        }
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Item item = first.item;
            if (n == 1) {
                first = null;
                last = null;
            } else {
                first = first.next;
                first.prev = null;
            }
            n--;
            return item;
        }
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Item item = last.item;
            if (n == 1) {
                first = null;
                last = null;
            } else {
                last = last.prev;
                last.next = null;
            }
            n--;
            return item;
        }
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    // print all elements
//    public void print() {
//        Iterator di = iterator();
//            while (di.hasNext()) {
//                String s = (String) di.next();   
//                StdOut.print(s + " ");
//            }
//            StdOut.println();
//    }
    
    // inner class Node
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }
    
    // inner class Iterator
    private class DequeIterator implements Iterator<Item> {
        
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    
//    public static void main (String[] args) {
//        Deque<String> d = new Deque<String>();
//        d.addFirst("a");
//        d.addFirst("b");
//        d.addFirst("c");
//        d.addLast("d");
//        d.removeFirst();
//        d.removeLast();
//        d.removeLast();
//        d.removeLast();
//    }
}