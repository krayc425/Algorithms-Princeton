import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] itemArray;
    
    private int n;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        itemArray = (Item[]) new Object[1];
        itemArray[0] = null;
        n = 0;
    } 
    
    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return n;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        } else {
            if (itemArray.length == n) {
                resize(itemArray.length * 2);
            }
            itemArray[n++] = item;
        }
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            // save the random item
            int randomIndex = StdRandom.uniform(n);
            Item randomItem = itemArray[randomIndex];
            
            // random one to null to avoid loitering
            itemArray[randomIndex] = null;
            
            // swap random one with last one
            Item lastItem = itemArray[n - 1];
            itemArray[randomIndex] = lastItem;
            itemArray[n - 1] = null;
            
            n--;
            
            // resize array
            if ((!isEmpty()) && (n <= itemArray.length / 4)) {
                resize(itemArray.length / 2);
            }
            
            return randomItem;
        }
    }
    
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return itemArray[StdRandom.uniform(n)];
        }
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    // resize the array
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = itemArray[i];
        }
        itemArray = temp;
    }
    
    // inner class Iterator
    private class RandomizedQueueIterator implements Iterator<Item> {
        
        private Item[] innerItems;
        private int index;
        
        public RandomizedQueueIterator() {
            Item[] temp = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                temp[i] = itemArray[i];
            }
            innerItems = temp;
            StdRandom.shuffle(innerItems);
            index = innerItems.length - 1;
        }
        
        public boolean hasNext() {
            return index >= 0;
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = innerItems[index];
            index--;
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    
//   public static void main (String[] args) {
//        RandomizedQueue<String> rq = new RandomizedQueue<String>();
//        rq.enqueue("1");
//        rq.enqueue("2");
//        rq.enqueue("3");
//        rq.enqueue("4");
//        rq.enqueue("5");
//        rq.enqueue("6");
//        Iterator i = rq.iterator();
//        while (i.hasNext()) {
//            String it = (String) i.next();
//            StdOut.print(it);
//        }
////        StdOut.println("Size " + rq.size());
//    }
}