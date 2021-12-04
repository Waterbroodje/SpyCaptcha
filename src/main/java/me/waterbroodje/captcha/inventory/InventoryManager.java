package me.waterbroodje.captcha.inventory;

import me.waterbroodje.captcha.Main;
import me.waterbroodje.captcha.utilities.Difficulties;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InventoryManager {

    Player player;
    Integer amount;
    Difficulties difficulty;
    boolean which;

    public InventoryManager(Player p) {
        this.player = p;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setDifficulty(Difficulties difficulty) {
        this.difficulty = difficulty;
    }

    public void clear() {
        this.player = null;
        this.amount = null;
        this.difficulty = null;
    }

    public Inventory build() {

        int random = new Random().nextInt(2);
        switch (random) {
            case 0:
                which = true;
                break;
            case 1:
                which = false;
                break;
            case 2:
                which = true;
                break;
        }

        Inventory inventory = Bukkit.createInventory(player, 54, "Captcha");

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

        List<Long> list = new ArrayList<>();

        if (which) {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, nothing);
            }

            for (int i = 0; i < amount; i++) {
                int number = new Random().nextInt(inventory.getSize());
                Long numberCheck = (long) number;
                if (number == 4 || list.contains(numberCheck)) {
                    i--;
                    continue;
                }
                list.add((long) number);
                inventory.setItem(number, cat);
            }


        } else {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, cat);
            }

            for (int i = 0; i < amount; i++) {
                int number = new Random().nextInt(inventory.getSize());
                Long numberCheck = (long) number;
                if (number == 4 || list.contains(numberCheck)) {
                    i--;
                    continue;
                }
                list.add((long) number);
                inventory.setItem(number, nothing);

            }
        }

        Main.map.put(player.getUniqueId(), list);
        Main.booleans.put(player.getUniqueId(), which);
        return inventory;
    }
}
