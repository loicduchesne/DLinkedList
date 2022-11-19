import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a basic Doubly Linked List meant for "Shape" custom object type.
 * @author Loic Duchesne
 */

public class DLinkedList {
    private SNode head;
    private SNode tail;
    private int size;

    // True if the current list is sorted. False if it is not.
    private boolean isSorted;

    public DLinkedList() {
        this.isSorted = false;
    }

    public class SNode {
        Shape element;
        SNode next;
        SNode prev;

        /**
         * Overrides the .equals method in Java Objects. Compare if an SNode is equal to another SNode.
         * @param obj Object to compare with this SNode.
         * @return Return false if objects are not equal, return true if they are.
         */
        @Override
        public boolean equals(Object obj) {
            // Edge case can compare two null objects.
            if (obj == null) {
                return false;
            } else if (!(obj instanceof SNode)) {
                throw new IllegalArgumentException("Object to compare must be of type SNode");
            } else {
                if (this.next == ((SNode) obj).next && this.prev == ((SNode) obj).prev) {
                    if (this.element.getShapeName().equals(((SNode) obj).element.getShapeName())) {
                        return this.element.getSize() == ((SNode) obj).element.getSize();
                    }
                }
            }
            return false;
        }
    }

    // ELEMENT OPERATIONS (PUBLIC)

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
        isSorted = false;
    }

    public Shape removeFirst() {
        SNode temp = head;

        // Edge cases
        if (size == 0 ) {
            throw new IllegalArgumentException("There is no element to remove because there is no elements in the list.");
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
        isSorted = false;
    }

    public Shape removeLast() {
        SNode temp = tail;

        // Edge cases
        if (size == 0 ) {
            throw new IllegalArgumentException("There is no element to remove because there is no elements in the list.");
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

    // NODE OPERATIONS (PRIVATE)

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

        isSorted = false;
    }

    /**
     * This method moves a node beside a boundary node. The behaviour of the algorithm can be specified using the extra boolean parameters.
     * @param nodeToMove The node that you want to move.
     * @param boundaryNode The node in which we want to move the nodeToMove to one of its sides.
     * @param moveRight True if you want the algorithm to move the node by searching in the Right direction. False if you want it to move it by searching in the Left direction.
     * @param addLeft True if you want the node to be added on the left of the boundaryNode. False if you want it to be added to the right of the boundaryNode.
     * <br> Note: If you leave moveRight and addLeft blank, their default values are both True. You cannot leave only 1 blank.
     * @return Returns true if it successfully moved the node. Return false if it did not move the node.
     */
    private boolean moveNode(SNode nodeToMove, SNode boundaryNode, boolean moveRight, boolean addLeft) {
        if (size == 0) {
            throw new NullPointerException("Cannot move Node because there is no elements in the list");
        } else if (nodeToMove == null || boundaryNode == null) {
            throw new IllegalArgumentException("nodeToMove or boundaryNode must both be non-null values");
        }
        SNode originalNode = nodeToMove;

        if (moveRight) {
            while (true) {
                // If the node arrives at the tail.
                if (nodeToMove.next == null) {
                    addFirst(nodeToMove.element);
                    removeLast();

                    nodeToMove = head;
                }

                // If it hits the boundary node, add to the left of that node;
                if (nodeToMove.next == boundaryNode) {
                    if (!addLeft) { // If we want to add on the right of the boundary node.
                        swapElements(nodeToMove, nodeToMove.next);
                        return true;
                    }
                    return true;
                }
                // Move the node to the next one.
                swapElements(nodeToMove, nodeToMove.next);
                nodeToMove = nodeToMove.next;

                // Edge case if boundaryNode not in the list.
                if (nodeToMove == originalNode) {
                    throw new RuntimeException("boundaryNode must be in the list.");
                }
                isSorted = false;
            }
        } else {
            while (true) {
                // If the node arrives at the head.
                if (nodeToMove.prev == null) {
                    addLast(nodeToMove.element);
                    removeFirst();

                    nodeToMove = tail;
                }

                // If it hits the boundary node, add to the left of that node;
                if (nodeToMove.prev == boundaryNode) {
                    if (addLeft) { // If we want to add on the left of the boundary node.
                        swapElements(nodeToMove, nodeToMove.next);
                        return true;
                    }
                    return true;
                }
                // Move the node to the next one.
                swapElements(nodeToMove, nodeToMove.prev);
                nodeToMove = nodeToMove.prev; //

                // Edge case if boundaryNode not in the list.
                if (nodeToMove == originalNode) {
                    throw new RuntimeException("boundaryNode must be in the list.");
                }
                isSorted = false;
            }
        }
    }

    // Second constructor for moveNode.
    private boolean moveNode(SNode nodeToMove, SNode boundaryNode) {
        if (size == 0) {
            throw new NullPointerException("Cannot move Node because there is no elements in the list");
        } else if (nodeToMove == null || boundaryNode == null) {
            throw new IllegalArgumentException("nodeToMove or boundaryNode must both be non-null values");
        }
        SNode originalNode = nodeToMove;

        while (true) {
            // If the node arrives at the tail.
            if (nodeToMove.next == null) {
                addFirst(nodeToMove.element);
                removeLast();

                nodeToMove = head;
            }

            // If it hits the boundary node, add to the left of that node;
            if (nodeToMove.next == boundaryNode) {
                return true;
            }
            // Move the node to the next one.
            swapElements(nodeToMove, nodeToMove.next);
            nodeToMove = nodeToMove.next; //

            // Edge case if boundaryNode not in the list.
            if (nodeToMove == originalNode) {
                throw new RuntimeException("boundaryNode must be in the list.");
            }
            isSorted = false;
        }
    }

    /**
     * This method returns the current Doubly Linked List with its nodes stored into an array.
     */
    private SNode[] toArray() {
        // Edge case.
        if (head == null) {
            throw new ArrayIndexOutOfBoundsException("There is currently no nodes in the list.");
        }
        SNode[] array = new SNode[size];

        SNode pointer = head;
        for (int i=0; i < size; i++) {
            array[i] = pointer;
            pointer = pointer.next;
        }
        return array;
    }

    /**
     * This method rebuild the .prev and .next given an array containing all the SNodes from this Doubly Linked List.
     * @param arr Input any array that contains the SNodes, regardless of the order in the array.
     */
    private void rebuildPointers(SNode[] arr) {
        if (arr.length != size) {
            throw new IllegalArgumentException("The input array must be the same size than the DLinkedList");
        }
        head = arr[0];
        tail = arr[size-1];

        head.prev = null;
        head.next = arr[1];

        tail.next = null;
        tail.prev = arr[size-2];

        for (int i=1; i < size-1; i++) {
            arr[i].prev = arr[i-1];
            arr[i].next = arr[i+1];
        }
    }

    /**
     * This method removes a node if it is present in the list.
     * @param nodeToRemove This is the node that you want to remove.
     * @return Returns the removed node element if it was successfully removed. Returns null if it was not.
     */
    private Shape removeNode(SNode nodeToRemove) {
        if (size == 0) {
            throw new IllegalArgumentException("There is no element to remove because there is no elements in the list.");
        } else if (size == 1) {
            head = null;
            tail = null;

            size--;
            return nodeToRemove.element;
        } /*else if (!nodeInList(nodeToRemove)) {
            return null;
        }*/ else if (nodeToRemove == null) {
            return null;
        } else if (nodeToRemove == head) {
            removeFirst();
            return nodeToRemove.element;
        } else if (nodeToRemove == tail) {
            removeLast();
            return nodeToRemove.element;
        } else {
            // Connects the previous and next node together while disconnecting them from the node to remove.
            nodeToRemove.next.prev = nodeToRemove.prev;
            nodeToRemove.prev.next = nodeToRemove.next;

            // Sets the .prev and .next from the node to remove both to null (unnecessary but keeping as proof of concept).
            nodeToRemove.prev = null;
            nodeToRemove.next = null;

            // Reduce the size of the list.
            size--;

            return nodeToRemove.element;
        }
    }

    /**
     * This function checks in the list if a node is present.
     * @param nodeToCheck The node to check if it is in the list.
     * @return Return true if there is an equal node in the list, returns false if not.
     */
    private boolean nodeInList(SNode nodeToCheck) {
        if (size == 0) {
            return false;
        } if (size == 1) {
            return nodeToCheck.equals(head);
        } else {
            SNode pointer = head;
            boolean checkInList = true;

            while (pointer != null) {

                if (nodeToCheck.equals(pointer)) {
                    return true;
                }

                pointer = pointer.next;
            }
            return false;
        }
    }

    /**
     * Search for a Shape in the list. This function checks in the list if a node contains a Shape of a certain size and name.
     * If there is, it returns the node containing the shape.
     *
     * <br> Note: If there are more than one shape within the nodes, it will just return the first occurrence of that shape.
     * @param shapeToFind Check if the shape is in the list.
     * @return an SNode that contains the input size.
     */
    public SNode searchShape(Shape shapeToFind) {
        if (isSorted) {
            SNode[] arr = toArray();
            ArrayList<SNode> arrl = new ArrayList<>(Arrays.asList(arr));

            return binarySearchShape(arrl, shapeToFind);
        } else {
            SNode pointer = head;

            while (pointer != null) {
                if (pointer.element.equals(shapeToFind)) {
                    return pointer;
                }
                pointer = pointer.next;
            }
        }
        return null;
    }

    /**
     * Use {@link #searchShape(Shape) searchShape} instead.
     * @param arrl Takes an arraylist as input.
     * @param shapeToFind Check if the shape is in the list.
     * @return an SNode that contains the shape.
     */
    private SNode binarySearchShape(List<SNode> arrl, Shape shapeToFind) {
        int lsize = arrl.size();
        int index = lsize/2;
        SNode currSNode = arrl.get(index);

        // Base case and if no shape matches.
        if (currSNode.element.equals(shapeToFind)) {
            return currSNode;
        } else if (lsize <= 1) {
            return null;
        }

        // Recursion.
        if (currSNode.element.getSize() < shapeToFind.getSize()) {
            return binarySearchShape(arrl.subList(index+1, lsize), shapeToFind);
        }
        if (currSNode.element.getSize() > shapeToFind.getSize()) {
            return binarySearchShape(arrl.subList(0, index), shapeToFind);
        }
        return null;
    }


    // SORT OPERATIONS

    /**
     * This method sorts the current Linked List by size (from smallest to largest) using the bubble sort algorithm.
     * It uses the private integer size stored in {@link Shape}.
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
            isSorted = true;
            return true;
        }
    }

    /**
     * This method sorts the current Linked List by size (from smallest to largest) using the selection sort algorithm. It uses the private integer size stored in {@link Shape}.
     * @return Returns true if the selectionSort() loops occurs, returns false if it does not reach the loop segment.
     * @author Loic Duchesne
     */
    public boolean selectionSort() {
        // Edge case handling. No sorting if size is 1 or less.
        if (size <= 1) {
            return false;
        } else {
            SNode pointer = head; // Points to the current position for the loop.
            SNode sortedIndex = head; // Node right after the sorted list.
            int unsortedSize = size; // Amount of unsorted elements left.

            // While the sorted index is not at the end.
            while (sortedIndex.next != null) {
                SNode smallest = sortedIndex; // Smallest node in the current unsorted set of nodes.

                // Runs a loop n times of how many elements n are left.
                for (int i=0; i<unsortedSize; i++) {

                    // If the pointer node size is smaller than the current registered smallest, reassign smallest to pointer.
                    if (pointer.element.getSize() < smallest.element.getSize()) {
                        smallest = pointer;
                    }

                    // When the pointer reach the end of the unsorted list, push the smallest element into sorted list & adjust other conditions.
                    if (pointer.next == null) {
                        // Make the unsorted size smaller by 1.
                        unsortedSize--;

                        // Push the smallest element in the sorted list by swapping that element with the sortedIndex (node right after sorted list).
                        swapElements(smallest, sortedIndex);
                        // Set the sorted index node to the next one.
                        sortedIndex = sortedIndex.next;

                        // Reset pointer and smallest to their origin values.
                        pointer = sortedIndex;
                        smallest = sortedIndex;
                    }
                    // Move the pointer to its next element.
                    pointer = pointer.next;
                }
            }
            isSorted = true;
            return true;
        }
    }

    /**
     * This method sorts the current Linked List by size (from smallest to largest) using the insertion sort algorithm. It uses the private integer size stored in {@link Shape}.
     * @return Returns true if the insertionSort() loops occurs, returns false if it does not reach the loop segment.
     * @author Loic Duchesne
     */
    public boolean insertionSort() {
        // Edge case handling. No sorting if size is 1 or less.
        if (size <= 1) {
            return false;
        }
        // Set the first element in the unsorted list as the first element in the sorted list.
        SNode sortedIndex = head.next;
        SNode current = head.next;
        int sortedSize = 1;

        // Iterate over all the n elements.
        while (sortedSize < size) {

            // Iterate over the sorted elements size. Prevents null pointer.
            for (int i=0; i < sortedSize; i++) {
                if (current.prev == null || current.prev.element.getSize() <= current.element.getSize()) {
                    break; // Break if at the head or if it finds an element smaller in the sorted elements.
                }
                swapElements(current, current.prev); // Swap the current element to its previous (until find match).
                current = current.prev; // Move the current pointer to the previous.
            }
            sortedIndex = sortedIndex.next;
            current = sortedIndex;

            sortedSize++;
        }
        isSorted = true;
        return true;
    }


    // BUG DETECTION

    /**
     * This method detects if there is any .prev or .next accidentally assigned to create a loop in the list.
     * It is a basic implementation of Floyd's Tortoise And Hare Algorithm.
     * @return Returns true if there is a loop, returns false if there is no loop.
     * @author Loic Duchesne
     */
    public boolean detectLoop() {
        SNode pointerSlow = head;
        SNode pointerFast = head.next;

        // Edge case if pointer is null (e.g. list is empty).
        if (pointerSlow == null) {
            return false;
        } else {
            // Integer used to slow slowPointer down.
            int i = 0;

            // If the fast pointer reaches a null, that means that it is at a boundary, therefore no loop.
            while (pointerFast.next != null) {
                // Move fast pointer to the next node.
                pointerFast = pointerFast.next;

                // If at any instance, the fast pointer reaches the slow pointer, there is a loop in the .next pointers.
                if (pointerFast == pointerSlow) {
                    return true;
                }
                // Pointer slow moves to the .next node 2 times slower than pointer fast.
                if (i % 2 == 0) {
                    pointerSlow = pointerSlow.next;
                    i = 0;
                }
                i++;
            }
            // There is no loop in the .next pointers.
            // Reinitialize the values to check for a loop in the .prev pointers.
            i = 0;
            pointerSlow = pointerFast;
            pointerFast = pointerSlow.prev;

            // If the fast pointer reaches a null, that means that it is at a boundary, therefore no loop.
            while (pointerFast.prev != null) {
                // Move fast pointer to the prev node.
                pointerFast = pointerFast.prev;

                // If at any instance, the fast pointer reaches the slow pointer, there is a loop in the .prev pointers.
                if (pointerFast == pointerSlow) {
                    return true;
                }
                // Pointer slow moves to the .prev node 2 times slower than pointer fast.
                if (i % 2 == 0) {
                    pointerSlow = pointerSlow.prev;
                    i = 0;
                }
                i++;
            }
            // Return false if there is no loop.
            return false;
        }
    }

    // PRINT METHODS & DEBUGS

    public void printElements() {
        SNode pointer = head;

        System.out.println("Elements from 0 to n index:");
        while (pointer != null) {
            System.out.println(pointer.element.getShapeName() + " | size = " + pointer.element.getSize());

            pointer = pointer.next;
        }
        System.out.println("----------------------------");
    }

    public void printElementSizes() {
        SNode pointer = head;

        System.out.println("Object sizes from 0 to n index:");
        for (int i=0; i<size; i++) {
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

    // DEPRECATED METHODS
    // *These are broken/old methods that are either not working or not implemented properly. Keeping them for archive purposes.
    // **I was very sleep-deprived when I wrote those.

    /**
     * This method moves a node between two nodes "nodeBefore" and "nodeAfter". Handles edge cases if you want to move a node to the head or tail.
     * The two node pointers must be adjacent to each other and in their proper orders.
     * @param nodeToMove The node that you want to move.
     * @param nodeBefore The node before the new location. Write "null" if you want to move the node before the head.
     * @param nodeAfter The node after the new location. Write "null" if you want to move the node after the tail.
     * @deprecated As of 2022/11/04, because it was inefficient and buggy. Use {@link #moveNode(SNode, SNode)} instead.
     */
    @Deprecated
    private boolean DEPRECATEDmoveNode(SNode nodeToMove, SNode nodeBefore, SNode nodeAfter) { //note: nodeToMove will be moved in between nodeBefore & nodeAfter
        if (size == 0) {
            throw new NullPointerException("Cannot move Node because there is no elements in the list");
        } else if (size < 3) {
            throw new IllegalArgumentException("There is not enough elements in the list. There must be at least three elements to move a Node");
        } else if (nodeToMove == nodeBefore || nodeToMove == nodeAfter) {
            return false;
        } else if (nodeToMove.prev == nodeBefore && nodeToMove.next == nodeAfter) {
            return false;
        } else if (nodeBefore == null && nodeAfter == null) {
            throw new IllegalArgumentException("Both nodeBefore and nodeAfter are null. Only one can be null.");
        } else if (nodeBefore == null) {
            removeNode(nodeToMove);
            addFirst(nodeToMove.element);
            return true;
        } else if (nodeAfter == null) {
            removeNode(nodeToMove);
            addLast(nodeToMove.element);
            return true;
        } else if (nodeBefore.next == nodeAfter && nodeAfter.prev == nodeBefore) { // Moves the node if the two nodes are adjacent.
            // Check if current node is head or tail.
            boolean isHead = false;
            if (nodeToMove.prev == null) {
                nodeToMove.next = head;
                head.prev = null;
                isHead = true;
            } else {
                nodeToMove.prev.next = nodeToMove.next;
            }
            if (nodeToMove.next == null) {
                nodeToMove.prev = tail;
                tail.next = null;
            } else if (!isHead) {
                nodeToMove.next.prev = nodeToMove.prev;
            }

            // Connect the new node .prev and .next to their new neighbours.
            nodeToMove.prev = nodeBefore;
            nodeToMove.next = nodeAfter;

            // Reconnect the nodeBefore .next and nodeAfter .prev to the newNode.
            nodeBefore.next = nodeToMove;
            nodeAfter.prev = nodeToMove;

            return true;
        } else {
            throw new IllegalArgumentException("nodeBefore and nodeAfter must be adjacent to each others and be in the correct order.");
        }
    }

    /**
     * This method sorts the current Linked List by size (from smallest to largest) using the insertion sort algorithm. It uses the private integer size stored in {@link Shape}.
     * @return Returns true if the insertionSort() loops occurs, returns false if it does not reach the loop segment.
     * @deprecated As of 2022/11/04, because it was inefficient and prone to errors. Use {@link #insertionSort()} instead.
     */
    @Deprecated
    private boolean DEPRECATEDinsertionSort() {
        // Edge case handling. No sorting if size is 1 or less.
        if (size <= 1) {
            return false;
        } else {
            SNode sortedIndex = head.next;
            SNode current = head.next;
            SNode pointer = sortedIndex.prev;
            int sortedSize = 1;

            while (sortedSize < size) {
                boolean compareSortedCurrent = true;

                // Built-in debug to detect infinite loops.
                if (detectLoop()) {
                    throw new RuntimeException("Infinite loop.");
                }

                while (compareSortedCurrent) {
                    if (pointer == null) {
                        moveNode(current, head, false, true);

                        sortedSize++;

                        compareSortedCurrent = false;
                    } else if (pointer.element.getSize() <= current.element.getSize()) {
                        moveNode(current, pointer, false, false);

                        sortedSize++;
                        // Compare current is now false resetting the loop.
                        compareSortedCurrent = false;
                    } else {
                        pointer = pointer.prev;
                    }
                }
                // Move the sortedIndex and the current to the next node.
                sortedIndex = head.next;
                for (int i=1; i<sortedSize; i++) {
                    sortedIndex = sortedIndex.next;
                }

                if (sortedIndex == null) {
                    pointer = tail.prev;
                    current = tail;
                    return true;
                } else {
                    pointer = sortedIndex.prev;
                    current = sortedIndex;
                }
            }
            return true;
        }
    }
}