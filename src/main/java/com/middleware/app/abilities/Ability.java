package com.middleware.app.abilities;

import com.middleware.app.colors.ConsoleColors;

import java.sql.Timestamp;

//TODO changer la classe ability pour que ce soit un thread et que le joueur ne puisse utiliser qu'un seul thread à la fois (temps attaque)
public class Ability {
    private final String name;
    private final int power;
    private final int cooldown; //value in second
    private Timestamp creation_timestamp;

    public Ability(String name, int power, int cooldown) {
        this.name = name;
        this.power = power;
        this.cooldown = cooldown * 1000;
    }

    // ############################ GETTER & SETTER ############################
    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getCooldown() {
        return cooldown;
    }

    // ############################ FUNCTION ############################
    // Handles the use and cooldown logic
    public void useAbility(String entityName) {
        if (isAvailable(entityName)) {
            this.creation_timestamp = new Timestamp(System.currentTimeMillis());
        } else {
            System.out.println(ConsoleColors.colorRed("[" + entityName + "]") + ConsoleColors.colorBlue(" Ability ") + ConsoleColors.colorWhiteBright(this.name) + ConsoleColors.colorBlue(" needs time to reload his attack"));
        }
    }

    // Check if the ability's cooldown is complete
    public boolean isAvailable(String entityName) {
        if (creation_timestamp == null) {
            return true;
        }

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        long timeSinceLastUse = currentTime.getTime() - creation_timestamp.getTime();
        System.out.println(ConsoleColors.colorWhiteBright("time : ") + ConsoleColors.colorRed("[" + entityName + "]") + ConsoleColors.colorGreen("[" + this.name + "]")+ ConsoleColors.colorWhiteBright(" : " + timeSinceLastUse));
        return (timeSinceLastUse >= (long) cooldown);
    }
}