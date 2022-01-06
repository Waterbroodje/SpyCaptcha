package me.waterbroodje.captcha;

import me.waterbroodje.captcha.listeners.PlayerJoinListener;
import me.waterbroodje.captcha.listeners.PlayerInventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public static Main instance;
    public static HashMap<UUID, List<Long>> map = new HashMap<>();
    public static HashMap<UUID, Integer> chances = new HashMap<>();
    public static List<UUID> kicked = new ArrayList<>();
    public static HashMap<UUID, Boolean> booleans = new HashMap<>();
    public static List<UUID> finished = new ArrayList<>();

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInventoryListener(), this);
        System.out.println("[SpyCaptcha] SpyCaptcha is succesfully installed.");
    }
}
