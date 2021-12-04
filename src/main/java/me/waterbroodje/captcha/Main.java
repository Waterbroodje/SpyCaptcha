package me.waterbroodje.captcha;

import me.waterbroodje.captcha.listeners.PlayerConnectEvent;
import me.waterbroodje.captcha.listeners.PlayerInventoryEvent;
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

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new PlayerConnectEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInventoryEvent(), this);
        System.out.println("[SpyCaptcha] SpyCaptcha is succesfully installed.");
    }
}
