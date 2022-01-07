package me.waterbroodje.captcha.listeners;

import me.waterbroodje.captcha.Main;
import me.waterbroodje.captcha.utilities.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class PlayerInventoryListener implements Listener {

    @EventHandler
    public void onClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase("Captcha") && Main.map.containsKey(player.getUniqueId()) && e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
            e.setCancelled(true);
            long slot = e.getSlot();
            List<Long> list = Main.map.get(player.getUniqueId());
            if (list.contains(slot)) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                list.remove(slot);

                ItemStack nothing = new ItemStack(Material.PLAYER_HEAD, 1, (short) 1);
                SkullMeta nothingMeta = (SkullMeta) nothing.getItemMeta();
                nothingMeta.setDisplayName(" ");
                nothingMeta.setOwner("blackk");
                nothing.setItemMeta(nothingMeta);

                ItemStack cat = new ItemStack(Material.PLAYER_HEAD, 1, (short) 1);
                SkullMeta catMeta = (SkullMeta) cat.getItemMeta();
                catMeta.setDisplayName(" ");
                catMeta.setOwner("MHF_GitHub");
                cat.setItemMeta(catMeta);

                if (Main.booleans.get(player.getUniqueId())) {
                    e.getInventory().setItem((int) slot, nothing);
                    player.updateInventory();
                } else {
                    e.getInventory().setItem((int) slot, cat);
                    player.updateInventory();
                }
                Main.map.remove(player.getUniqueId());
                Main.map.put(player.getUniqueId(), list);
                System.out.println(Main.map.get(player.getUniqueId()) + " - " + player.getName());

                if (Main.map.get(player.getUniqueId()).isEmpty()) {
                    player.sendMessage(Util.chat("&aYou've succesfully completed the captcha."));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    Main.map.remove(player.getUniqueId());
                    Main.booleans.remove(player.getUniqueId());
                    Main.chances.remove(player.getUniqueId());
                    Main.finished.add(player.getUniqueId());
                    player.closeInventory();
                }
            } else {
                if (e.getCurrentItem() != null) {
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                    Main.chances.put(player.getUniqueId(), Main.chances.get(player.getUniqueId()) - 1);
                    player.sendMessage(Util.chat("&cYou've clicked on the wrong head. You still have " + Main.chances.get(player.getUniqueId()) + "&c chances left."));
                    if (Main.chances.get(player.getUniqueId()) <= 0) {
                        player.kickPlayer(Util.chat("&cYou've failed the Captcha. You can try again in 5 minutes."));
                        Main.kicked.add(player.getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Main.kicked.remove(player.getUniqueId());
                            }
                        }.runTaskLater(Main.getInstance(), 300 * 20);
                    }
                }
            }

        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("Captcha") && Main.map.containsKey(e.getPlayer().getUniqueId())) {
            Player player = (Player) e.getPlayer();
            player.kickPlayer(Util.chat("&cYou may not close the inventory while executing the captcha. Please rejoin."));
        }
    }

}
