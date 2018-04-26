package com.georlegacy.general.lobbykeep.listeners;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import org.apache.commons.lang.time.StopWatch;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PKMoveListener implements Listener {
    private LobbyKeep lk;
    public PKMoveListener(LobbyKeep lk) {
        this.lk = lk;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location loc = e.getPlayer().getLocation();
        for (Location l : lk.getParkourData().getStartPoints()) {
            if (
                    l.getBlockX()==(e.getPlayer().getLocation().getBlockX()) &&
                    l.getBlockY()==(e.getPlayer().getLocation().getBlockY()) &&
                    l.getBlockZ()==(e.getPlayer().getLocation().getBlockZ()) &&
                    l.getWorld()==(e.getPlayer().getWorld())
               ){
                if (
                        e.getFrom().getBlockX()==(e.getPlayer().getLocation().getBlockX()) &&
                        e.getFrom().getBlockY()==(e.getPlayer().getLocation().getBlockY()) &&
                        e.getFrom().getBlockZ()==(e.getPlayer().getLocation().getBlockZ()) &&
                        e.getFrom().getWorld()==(e.getPlayer().getWorld())
                        ){
                    return;
                }
                startParkour(e.getPlayer(), l);
            }
        }
    }

    private void startParkour(Player p, Location l) {
        String pkname = lk.getParkourData().getParkourByStart(l);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5test you started the parkour mate!"));
        lk.getParkourData().parkourAttempts.put(p, pkname);
        lk.getParkourData().parkourAttempsTimes.put(p, new StopWatch());
    }

}
