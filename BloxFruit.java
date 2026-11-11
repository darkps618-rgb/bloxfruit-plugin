package com.bloxfruit.main;

import com.bloxfruit.combat.CombatListener;
import com.bloxfruit.factions.FactionManager;
import com.bloxfruit.gui.MenuManager;
import com.bloxfruit.races.RaceManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BloxFruit extends JavaPlugin {

    private static BloxFruit instance;
    private MenuManager menuManager;
    private RaceManager raceManager;
    private FactionManager factionManager;

    @Override
    public void onEnable() {
        instance = this;
        this.menuManager = new MenuManager();
        this.raceManager = new RaceManager();
        this.factionManager = new FactionManager();

        // Register Event Listeners
        getServer().getPluginManager().registerEvents(new CombatListener(this), this);
        getServer().getPluginManager().registerEvents(this.menuManager, this);
        getServer().getPluginManager().registerEvents(this.raceManager, this);
        getServer().getPluginManager().registerEvents(this.factionManager, this);

        // Lower-Left HUD & Action Bar Updater (Health, Energy, V4 Red Bar, Mastery)
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                int v4Bar = raceManager.getV4Charge(player);
                String barColor = v4Bar >= 100 ? ChatColor.DARK_RED + "■■■■■" : ChatColor.RED + "██░░░";
                
                player.sendActionBar(
                    ChatColor.YELLOW + "HP: " + (int)player.getHealth() + "/20 | " +
                    ChatColor.AQUA + "Energy: 100/100 | " +
                    barColor + " V4: " + v4Bar + "% | " +
                    ChatColor.GOLD + "Mastery: 600"
                );
            });
        }, 0L, 10L);

        getLogger().info("Blox Fruits Core Plugin Enabled Successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Blox Fruits Core Plugin Disabled.");
    }

    public static BloxFruit getInstance() {
        return instance;
    }

    public MenuManager getMenuManager() { return menuManager; }
    public RaceManager getRaceManager() { return raceManager; }
    public FactionManager getFactionManager() { return factionManager; }
}
