package com.bloxfruit.factions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class FactionManager implements Listener {

    private final HashMap<UUID, String> playerFactions = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Automatically assign new players or prompt faction choice (Pirate / Marine)
        playerFactions.putIfAbsent(player.getUniqueId(), "Pirate");
        player.sendMessage(ChatColor.GOLD + "[Faction] You have joined the " + ChatColor.RED + "Pirates" + ChatColor.GOLD + "!");
    }

    // Spawn specialized faction ship/boat
    @EventHandler
    public void onSpawnBoat(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.getInventory().getItemInMainHand().getType() == Material.OAK_BOAT) {
            event.setCancelled();
            player.getWorld().spawn(player.getLocation(), Boat.class);
            player.sendMessage(ChatColor.AQUA + "🚢 Your Faction Ship has been deployed!");
        }
    }

    // PvP Rules between Pirates and Marines
    @EventHandler
    public void onPvP(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player victim = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();

            String f1 = playerFactions.getOrDefault(attacker.getUniqueId(), "Pirate");
            String f2 = playerFactions.getOrDefault(victim.getUniqueId(), "Pirate");

            // Allow combat between different factions or bounty hunters
            if (f1.equals(f2)) {
                // Same faction safezone rules can be enforced here
            }
        }
    }
}
