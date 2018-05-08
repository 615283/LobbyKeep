package com.georlegacy.general.lobbykeep.listeners;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FallListener implements Listener {
    private LobbyKeep lk;
    public FallListener(LobbyKeep lk) {
        this.lk = lk;
    }

    @EventHandler
    public void onFall(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        for (String worldName : lk.worlds()) {
            if (p.getWorld().getName().equals(worldName)) execute(p, p.getWorld());
        }
    }

    public void execute(Player p, World w) {
        Location location = w.getSpawnLocation();
        location.setX(location.getBlockX()+0.5);
        location.setZ(location.getBlockZ()+0.5);
        if (!lk.diffLevels()) {
            if (p.getLocation().getBlockY()<lk.level()) {
                p.teleport(location);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1.25F);
            } else return;
        } else {
            if (lk.getConfig().getInt(w.getName())>p.getLocation().getBlockY()) {
                p.teleport(location);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1.25F);
            } else return;
        }
    }

}
