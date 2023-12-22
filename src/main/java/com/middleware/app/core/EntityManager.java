package com.middleware.app.core;

import com.middleware.app.game.bosses.Boss;
import com.middleware.app.game.bosses.IceDragon;
import com.middleware.app.game.players.LightGuardian;
import com.middleware.app.network.NetworkPlayer;

import java.util.*;

public class EntityManager {

    private final Integer currentPlayerId;
    private final Map<Integer, LightGuardian> heroes;
    private final Map<Integer, NetworkPlayer> players;
    private final IceDragon boss;

    public EntityManager(Integer currentPlayerId, List<NetworkPlayer> players) {

        this.currentPlayerId = currentPlayerId;

        this.players = new HashMap<>();
        this.heroes = new HashMap<>();

        for (NetworkPlayer player : players) {
            this.players.put(player.getPlayerId(), player);
            this.heroes.put(player.getPlayerId(), new LightGuardian());
        }

        this.boss = new IceDragon();
    }

    public NetworkPlayer getCurrentPlayer() {
        return players.get(currentPlayerId);
    }

    public LightGuardian getCurrentHero() {
        return heroes.get(currentPlayerId);
    }

    public NetworkPlayer getPlayerById(Integer id) {
        return players.get(id);
    }

    public LightGuardian getHeroById(Integer id) {
        return heroes.get(id);
    }

    public Boss getBoss() {
        return boss;
    }

    public List<NetworkPlayer> getOtherPlayers() {
        List<NetworkPlayer> otherPlayers = new ArrayList<>();
        for (NetworkPlayer player : players.values()) {
            if (!player.getPlayerId().equals(currentPlayerId)) {
                otherPlayers.add(player);
            }
        }
        return otherPlayers;
    }
}
