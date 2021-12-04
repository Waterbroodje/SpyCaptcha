package me.waterbroodje.captcha.listeners;

import me.waterbroodje.captcha.Main;
import me.waterbroodje.captcha.inventory.InventoryManager;
import me.waterbroodje.captcha.utilities.Difficulties;
import me.waterbroodje.captcha.utilities.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class PlayerConnectEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!Main.kicked.contains(p.getUniqueId()) && !p.hasPermission("spycaptcha.bypass")) {
            Main.map.remove(p.getUniqueId());
            Main.chances.put(p.getUniqueId(), 3);
            InventoryManager inventory = new InventoryManager(p);
            int random = new Random().nextInt(10);
            inventory.setAmount((random < 5 ? random + 5 : random));
            inventory.setDifficulty(Difficulties.NORMAL);

            new BukkitRunnable() {
                @Override
                public void run() {
                    p.openInventory(inventory.build());
                }
            }.runTaskLater(Main.getInstance(), 20);
        }
    }

    @EventHandler
    public void onLoginEvent(PlayerLoginEvent e) {
        if (Main.kicked.contains(e.getPlayer().getUniqueId())) {
            e.setKickMessage(Util.chat("&cYou've failed the Captcha. You can try again in 5 minutes."));
            e.setResult(PlayerLoginEvent.Result.KICK_FULL);
        }
    }

}
