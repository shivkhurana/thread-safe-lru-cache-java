package com.dsa.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> map;
    
    // ReadWriteLock allows multiple readers but only one writer (High Performance)
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    // Dummy Head (Most Recently Used) and Tail (Least Recently Used)
    private final Node<K, V> head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        
        // Initialize Dummy Nodes to avoid null checks
        head = new Node<>(null, null);
        tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        lock.writeLock().lock(); // Must acquire write lock because we modify the list order
        try {
            if (!map.containsKey(key)) return null;

            Node<K, V> node = map.get(key);
            remove(node);
            addToHead(node); // Move to front (Most Recently Used)
            
            return node.value;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            if (map.containsKey(key)) {
                Node<K, V> node = map.get(key);
                node.value = value;
                remove(node);
                addToHead(node);
            } else {
                if (map.size() == capacity) {
                    map.remove(tail.prev.key); // Remove LRU from Map
                    remove(tail.prev);         // Remove LRU from List
                }
                Node<K, V> newNode = new Node<>(key, value);
                map.put(key, newNode);
                addToHead(newNode);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Helper: Add node right after Head
    private void addToHead(Node<K, V> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    // Helper: Remove node from list
    private void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}