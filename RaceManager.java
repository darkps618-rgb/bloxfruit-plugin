package com.bloxfruit.races;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class RaceManager implements Listener {

    private final HashMap<UUID, String> races = new HashMap<>();
    private final HashMap<UUID, Integer> stages = new HashMap<>(); // V1, V2, V3, V4
    private final HashMap<UUID, Integer> v4Charges = new HashMap<>(); // Red Bar 0-100%

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        races.putIfAbsent(player.getUniqueId(), "Human");
        stages.putIfAbsent(player.getUniqueId(), 1);
        v4Charges.putIfAbsent(player.getUniqueId(), 0);
    }

    public void addV4Charge(Player player, int amount) {
        UUID uuid = player.getUniqueId();
        int current = v4Charges.getOrDefault(uuid, 0) + amount;
        if (current >= 100) {
            current = 100;
            player.sendMessage(ChatColor.RED + "🔥 RACE V4 AWAKENING UNLOCKED! Press Transformation Key to activate! 🔥");
        }
        v4Charges.put(uuid, current);
    }

    public int getV4Charge(Player player) {
        return v4Charges.getOrDefault(player.getUniqueId(), 0);
    }
}
