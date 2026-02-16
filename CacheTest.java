package com.dsa.cache;

public class CacheTest {
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(2);

        System.out.println("🔹 Putting (1, A)");
        cache.put(1, "A");
        
        System.out.println("🔹 Putting (2, B)");
        cache.put(2, "B");
        
        System.out.println("🔹 Getting 1: " + cache.get(1)); // Returns A, moves 1 to head
        
        System.out.println("🔹 Putting (3, C) -> Should evict 2 (LRU)");
        cache.put(3, "C");
        
        System.out.println("🔹 Getting 2: " + cache.get(2)); // Should be null
        System.out.println("🔹 Getting 3: " + cache.get(3)); // Should be C
    }
}