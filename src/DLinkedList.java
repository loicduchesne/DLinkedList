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
            // Assigning the tail's next to the newNode.
            tail.next = newNode;

            // Assigning newNode's .prev to the current tail.
            newNode.prev = tail;

            // Assigning tail to the new tail (newNode).
            tail = newNode;
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


}
