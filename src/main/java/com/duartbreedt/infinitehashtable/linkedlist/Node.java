package com.duartbreedt.infinitehashtable.linkedlist;

import java.util.ArrayList;
import java.util.List;

public class Node {

    List<String> array;

    public Node(String val) {
        array = new ArrayList<>(List.of(val));
    }
}