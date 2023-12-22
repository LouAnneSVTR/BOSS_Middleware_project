package com.middleware.app.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeybindListener implements KeyListener {

    public static final int KEY_DIVINE_STRIKE = KeyEvent.VK_NUMPAD1;   // Key '1'
    public static final int KEY_HOLY_SHIELD = KeyEvent.VK_NUMPAD2;     // Key '2'
    public static final int KEY_BLESSED_HEALING = KeyEvent.VK_NUMPAD3; // Key '3'
    public static final int KEY_LIGHTS_JUDGMENT = KeyEvent.VK_NUMPAD4; // Key '4'

    private final ConcurrentLinkedQueue<Integer> keyQueue;

    public KeybindListener() {
        keyQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Typed: " + e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Pressed: " + e);
        keyQueue.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Released: " + e);
    }

    public Integer pollKey() {
        // Retrieve and remove the head of this queue, or return null if this queue is empty
        return keyQueue.poll();
    }

}
