/**
 * Implementation of a basic Doubly Linked List meant for "Shape" custom object type.
 * @author Loic Duchesne
 */

public class DLinkedList {
    private SNode head;
    private SNode tail;
    private int size;
    private class SNode {
        Shape element;
        SNode next;
        SNode prev;
    }

    public void addFirst(Shape elementToAdd) {
        SNode newNode = new SNode();
        newNode.element = elementToAdd;

        // Edge case.
        if (head == null) {
            head = newNode;
            tail = newNode;
        }

        // Assigning the newNode's .next to the head that will be replaced.
        newNode.next = head;

        // Assigning the current head's prev to newNode.
        head.prev = newNode;

        // Assigning head to the new head.
        head = newNode;

        size++;
    }

    public Shape removeFirst() {
        SNode temp = head;

        // Edge cases
        if (size == 0 ) {
            throw  new IllegalArgumentException("There is no element to remove because there is no elements in the list.");
        }
        if (size == 1) {
            head = null;
            tail = null;
            // Main loop
        } else {
            // Assigning the current head to its next element.
            head = head.next;

            // Assigning its previous element to null.
            head.prev = null;

            // Assigning the temp next's element to null (not really necessary).
            temp.next = null;
        }
        size--;
        return temp.element;
    }

    public void addLast(Shape elementToAdd) {
        SNode newNode = new SNode();
        newNode.element = elementToAdd;

        // Edge cases
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            // Using a pointer to point the old tail.
            SNode pointer = tail;

            // Assigning the tail's next to the newNode.
            tail.next = newNode;

            // Assigning tail to the new tail (newNode).
            tail = newNode;

            // Assigning newNode's .prev to the current tail.
            newNode.prev = pointer;
        }
        size ++;
    }

    public Shape removeLast() {
        SNode temp = tail;

        // Edge cases
        if (size == 0 ) {
            throw  new IllegalArgumentException("There is no element to remove because there is no elements in the list.");
        }
        if (size == 1) {
            head = null;
            tail = null;
            // Main loop
        } else {
            // Setting tail to the previous tail element.
            tail = tail.prev;

            // Setting the new tail's next element to null.
            tail.next = null;

            // Setting the old tail .prev to null (not necessary).
            temp.prev = null;
        }
        size--;
        return temp.element;
    }

    /**
     * This method swaps the elements between two different nodes. Complements switchNode.
     * @param n1 The first input.
     * @param n2 The second input.
     * <br> *Note: the input orders does not matter.
     */
    private void swapElements(SNode n1, SNode n2) {
        if (n1 == null || n2 == null) {
            throw new IllegalArgumentException("One or both of the input nodes are null.");
        }
        // Temporary element pointer to refer to n1's element.
        Shape temp = n1.element;

        // Swapping the elements.
        n1.element = n2.element;
        n2.element = temp;
    }

    /**
     * This method sorts the current Linked List by size (from smallest to largest). It uses the private integer size stored in {@link Shape}.
     * @return Returns true if the bubbleSort loops occurs, returns false if it does not reach the loop segment.
     * @author Loic Duchesne
     */
    public boolean bubbleSort() {
        // Edge case handling. No sorting if size is 1 or less.
        if (size <= 1) {
            return false;
        }  else {
            SNode sortedIndex = tail;
            SNode current = head;

            while (head != sortedIndex) {
                // When the current node arrives at the end.
                if (current == sortedIndex) {
                    current = head;
                    sortedIndex = sortedIndex.prev;
                }
                // Compare element sizes.
                if (current.element.getSize() > current.next.element.getSize()) {
                    swapElements(current, current.next);
                }
                // Move the current pointer to the next element.
                current = current.next;
            }
            return true;
        }
    }


    // Print methods & print debugs.

    public void printElements() {
        SNode pointer = head;

        System.out.println("Elements from 0 to n index:");
        while (pointer != null) {
            System.out.println(pointer.element);
            System.out.println(pointer.element.getShapeName());

            pointer = pointer.next;
        }
        System.out.println("----------------------------");
    }

    public void printElementSizes() {
        SNode pointer = head;

        System.out.println("Object sizes from 0 to n index:");
        while (pointer != null) {
            System.out.println(pointer.element.getSize());

            pointer = pointer.next;
        }
        System.out.println("----------------------------");
    }

    public void debugPrint() {
        SNode pointer = head;

        System.out.println("----------------------------");
        while (pointer != null) {
            System.out.println(pointer.element);
            System.out.println(pointer.element.getSize());

            pointer = pointer.next;
        }
        System.out.println("----------------------------");
    }
}
