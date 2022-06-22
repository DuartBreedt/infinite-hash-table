package com.duartbreedt.infinitehashtable.fractalhash;

import static java.lang.Math.abs;

public class FractalHash {

    private final int[] PI = {1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9, 3, 2, 3, 8, 4, 6};
    private final Integer MAX_ARRAY_SIZE = 5;

    private final Node[] headArray;
    private Integer numberOfInsertedNodes = 0;

    public FractalHash() {
        headArray = new Node[MAX_ARRAY_SIZE];
    }

    public int getNumberOfInsertedNodes() {
        return numberOfInsertedNodes;
    }

    private Node[] createArray(int size) {
        return new Node[size];
    }

    public void insert(String value) {
        insert(headArray, 0, value, hash(0, value));
    }

    private void insert(Node[] arr, int level, String value, int hash) {
        if (hash == -1) {
            System.out.println("ERROR!: function insert()");
            return;
        }

        if (arr[hash] != null) {

            int hashedValue = hash(level + 1, value);

            if (arr[hash].array == null) {
                arr[hash].array = createArray(hashedValue + 1);
            } else {
                if (arr[hash].array.length <= hashedValue) {
                    arr[hash].array = growArray(arr[hash].array, hashedValue + 1);
                }
            }

            insert(arr[hash].array, level + 1, value, hashedValue);

        } else {
            arr[hash] = new Node(value);
            numberOfInsertedNodes++;
        }
    }

    private Node[] growArray(Node[] arr, int size) {
        Node[] newArr = new Node[size];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        return newArr;
    }

    private int hash(int level, String value) {
        int seed = PI[(level + 1) % PI.length];
        int h = 1;
        char[] val = value.toCharArray();
        for (int i = 0; i < value.length(); i++) {
            h = seed * h * level + val[i] + (seed << 2);
        }
        h ^= (seed << 3) + h >> seed;

        return abs(h) % MAX_ARRAY_SIZE;
    }

    public void delete(int val) {
        // TODO: Implement me!
    }

    public Node find(String value) {

        if (value != null && !value.isEmpty() && numberOfInsertedNodes != 0) {
            Node node = find(headArray, 0, value);
            if (node == null) {
                System.out.println(value + " could not be found!!!");
            }
            return node;
        } else {
            System.out.println("Invalid String");
        }

        return null;
    }

    private Node find(Node[] arr, int level, String value) {
        int hashedValue = hash(level, value);

        if (hashedValue < arr.length && arr[hashedValue] != null) {
            if (arr[hashedValue].value.equals(value)) {
                return arr[hashedValue];
            } else if (arr[hashedValue].array != null) {
                return find(arr[hashedValue].array, level + 1, value);
            }
        }
        return null;
    }

    public void display() {
        display(headArray, 0);
    }

    private void display(Node[] arr, int level) {
        for (int i = 0; i < arr.length; i++) {

            StringBuilder spacer = new StringBuilder();
            spacer.append("    ".repeat(Math.max(0, level)));

            if (arr[i] != null) {
                System.out.println(spacer + arr[i].value + " i: " + i);
                if (arr[i].array != null) {
                    display(arr[i].array, level + 1);
                }
            }
        }
    }

    public int getNumberOfSlots() {
        return numberOfSlots(headArray);
    }

    private int numberOfSlots(Node[] arr) {
        int acc = arr.length;
        for (Node node : arr) {
            if (node != null && node.array != null) {
                acc += numberOfSlots(node.array);
            }
        }
        return acc;
    }
}
