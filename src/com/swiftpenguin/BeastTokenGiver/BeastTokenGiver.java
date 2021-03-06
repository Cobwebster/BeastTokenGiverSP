package com.swiftpenguin.BeastTokenGiver;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BeastTokenGiver extends JavaPlugin implements Listener {

    private HashMap<UUID, Long> data = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {

            long timeStampNow = TimeUnit.SECONDS.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);

            for (UUID key : data.keySet() ) {

                    long math = timeStampNow - data.get(key);

                    if (math >= 1800) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "token add " + Bukkit.getPlayer(key).getName() + " 5" );
                        data.replace(key, System.currentTimeMillis() / 1000);
                    }
            }
        }, 60 * 20, 60*20);
    }

    @Override
    public void onDisable() {
        data.clear();
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        UUID uuid = event.getPlayer().getUniqueId();
        long timestamp = System.currentTimeMillis() / 1000;

        data.put(uuid, timestamp);
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event) {

        data.remove(event.getPlayer().getUniqueId());
    }
}



