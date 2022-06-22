package com.duartbreedt.infinitehashtable.fractalhash;

public class Node {

    String value;
    Node[] array;

    public Node(String val) {
        value = val;
        array = null;
    }

    public boolean isParentOf(int index) {
        return array != null && array[index] != null;
    }
}