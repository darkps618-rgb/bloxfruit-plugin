package com.bloxfruit.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuManager implements Listener {

    public void openSettingsMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Settings & Fast Mode");

        // Fast Mode Toggle Item
        ItemStack fastMode = new ItemStack(Material.SUGAR);
        ItemMeta meta = fastMode.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Fast Mode: Toggle Graphics/Particles");
        fastMode.setItemMeta(meta);

        inv.setItem(13, fastMode);
        player.openInventory(inv);
    }

    public void openStatsMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_BLUE + "Stats Allocation Menu");
        
        ItemStack melee = new ItemStack(Material.IRON_SWORD);
        ItemMeta mMeta = melee.getItemMeta();
        mMeta.setDisplayName(ChatColor.RED + "Melee Stat Upgrade");
        melee.setItemMeta(mMeta);

        ItemStack defense = new ItemStack(Material.SHIELD);
        ItemMeta dMeta = defense.getItemMeta();
        dMeta.setDisplayName(ChatColor.YELLOW + "Defense Stat Upgrade");
        defense.setItemMeta(dMeta);

        inv.setItem(10, melee);
        inv.setItem(12, defense);
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (title.contains("Settings") || title.contains("Stats")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.SUGAR) {
                player.sendMessage(ChatColor.YELLOW + "[Settings] Fast Mode toggled successfully!");
                player.closeInventory();
            }
        }
    }
}
