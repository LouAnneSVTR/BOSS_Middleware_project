package com.middleware.app.abilities;

import java.sql.Timestamp;

//TODO changer la classe ability pour que ce soit un thread et que le joueur ne puisse utiliser qu'un seul thread à la fois (temps attaque)
public class Ability {
    private String name;
    private int power;
    private int cooldown; //value in second
    private Timestamp creation_timestamp;

    public Ability(String name, int power, int cooldown) {
        this.name = name;
        this.power = power;
        this.cooldown = cooldown;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getCooldown() {
        return cooldown;
    }

    // Handles the use and cooldown logic
    public int useAbility() {
        if (isAvailable()) {
            this.creation_timestamp = new Timestamp(System.currentTimeMillis());
            return 15;
        } else {
            System.out.println("Player needs time to reload his attack");
            return 0;
        }
    }

    // Check if the ability's cooldown is complete
    public boolean isAvailable() {
        if (creation_timestamp == null) {
            return true;
        }

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        long timeSinceLastUse = currentTime.getTime() - creation_timestamp.getTime();
        return (timeSinceLastUse >= (long) cooldown);
    }
}