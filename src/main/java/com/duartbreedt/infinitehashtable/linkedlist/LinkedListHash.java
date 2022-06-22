package com.duartbreedt.infinitehashtable.linkedlist;

public class LinkedListHash {

    private final int[] PI = {1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9, 3, 2, 3, 8, 4, 6};
    private final Integer MAX_ARRAY_SIZE = 1000;

    private final Node[] headArray;
    private Integer numberOfInsertedNodes = 0;

    public LinkedListHash() {
        headArray = new Node[MAX_ARRAY_SIZE];
    }

    public int getNumberOfInsertedNodes() {
        return numberOfInsertedNodes;
    }

    public void insert(String value) {
        int hash = hash(0, value);

        if (headArray[hash] != null) {
            headArray[hash].array.add(value);
        } else {
            headArray[hash] = new Node(value);
        }
        numberOfInsertedNodes++;
    }

    private int hash(int level, String value) {
        int seed = PI[(level + 1) % PI.length];
        int h = 1;
        char[] val = value.toCharArray();
        for (int i = 0; i < value.length(); i++) {
            h = seed * h * level + val[i] + (seed << 2);
        }
        h ^= (seed << 3) + h >> seed;

        return h % MAX_ARRAY_SIZE;
    }

    public void delete(int val) {
        // TODO: Implement me!
    }

    public String find(String value) {

        if (value != null && !value.isEmpty() && numberOfInsertedNodes != 0) {
            String node = find(headArray, value);
            if (node == null) {
                System.out.println(node + " could not be found!!!");
            }
            return node;
        } else {
            System.out.println("Invalid String");
        }

        return null;
    }

    private String find(Node[] arr, String value) {
        int hashedValue = hash(0, value);

        if (hashedValue < arr.length && arr[hashedValue] != null) {

            for (String item : arr[hashedValue].array) {
                if (item.equals(value)) {
                    return item;
                }
            }
        }

        return null;
    }

    public void display() {
        display(headArray);
    }

    private void display(Node[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                System.out.println(String.join(",", arr[i].array) + " i: " + i);
            }
        }
    }

    public int getNumberOfSlots() {
        return numberOfSlots(headArray);
    }

    private int numberOfSlots(Node[] arr) {
        int acc = arr.length;
        for (Node node : arr) {
            if (node != null) {
                acc += node.array.size() - 1;
            }
        }
        return acc;
    }
}
