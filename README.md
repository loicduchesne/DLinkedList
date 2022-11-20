# Basic implementation of Doubly Linked List

Basic implementation of a Doubly Linked List with its object class template "Shape" for the context of COMP 250 at McGill University.

Holds methods for list operations + bubbleSort(), selectionSort() & insertionSort() iterative algorithms to sort the elements within the list. Additionally, it contains recursive algorithms such as binary search (if the list is sorted, it will use an iterative approach if unsorted), mergeSort() & quickSort().

Finally, it contains node operations required to make the algorithms function properly. There is also a detectLoop() method that uses Floyd's Tortoise & Hare Algorithm to detect if there is an accidental loop in the list itself.