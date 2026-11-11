package com.bloxfruit.combat;

import com.bloxfruit.main.BloxFruit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class CombatListener implements Listener {

    private final BloxFruit plugin;
    private final HashMap<UUID, Long> dashCooldown = new HashMap<>();

    public CombatListener(BloxFruit plugin) {
        this.plugin = plugin;
    }

    // Dash with 'Q' (Left click air or drop item mapped packet behavior)
    @EventHandler
    public void onPlayerArmSwing(PlayerAnimationEvent event) {
        Player player = event.getPlayer();
        
        // Q dash logic check via left-click/swing cooldown check
        long now = System.currentTimeMillis();
        if (dashCooldown.containsKey(player.getUniqueId()) && now < dashCooldown.get(player.getUniqueId())) {
            return;
        }

        // Trigger Dash forward
        Vector dashDir = player.getLocation().getDirection().multiply(1.8).setY(0.1);
        player.setVelocity(dashDir);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.5f);
        
        dashCooldown.put(player.getUniqueId(), now + 800); // 800ms cooldown
    }

    // 4 Slots for Blox Fruit Combat Loadout (Slot 0: Fruit, Slot 1: Fighting Style, Slot 2: Sword, Slot 3: Gun)
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            int slot = player.getInventory().getHeldItemSlot();
            if (slot > 3) return; // Strict 4-slot restriction rule

            event.setCancelled(true);
            switch (slot) {
                case 0:
                    player.sendMessage("§b[Blox Fruit] Executing Fruit Skill (Cooldown: 3s)");
                    break;
                case 1:
                    player.sendMessage("§c[Fighting Style] Executing Martial Arts Combo!");
                    break;
                case 2:
                    player.sendMessage("§e[Sword Skill] Slash wave projected!");
                    break;
                case 3:
                    player.sendMessage("§a[Gun Skill] Firing accurate projectile!");
                    break;
            }
        }
    }

    // Double Jump / Flash Step
    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getAllowFlight()) {
            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);

            Vector jumpDir = player.getLocation().getDirection().multiply(1.4).setY(0.4);
            player.setVelocity(jumpDir);
            player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 0.8f, 1.0f);
        }
    }
}
