package com.dsa.cache;

/**
 * Generic Node for Doubly Linked List.
 * Stores Key and Value to allow O(1) removal from Map.
 */
public class Node<K, V> {
    K key;
    V value;
    Node<K, V> prev;
    Node<K, V> next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}