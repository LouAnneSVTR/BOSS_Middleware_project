package com.middleware.app.network.udp;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GamePacketQueue {
    private final ConcurrentLinkedQueue<GamePacket> queue = new ConcurrentLinkedQueue<>();

    public void enqueue(GamePacket packet) {
        queue.add(packet);
    }

    public GamePacket dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

