package com.georlegacy.general.lobbykeep.listeners;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import org.apache.commons.lang.time.StopWatch;
import org.bukkit.Bukkit;
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
        if (lk.getParkourData().parkourAttempts.containsKey(e.getPlayer())) {
            Location loc = e.getPlayer().getLocation();
            Location end = lk.getParkourData().getEndFromParkour(lk.getParkourData().parkourAttempts.get(e.getPlayer()));
            Bukkit.broadcastMessage("" + loc.getBlockX() + "  " + end.getBlockX());
            Bukkit.broadcastMessage("" + loc.getBlockY() + "  " + end.getBlockY());
            Bukkit.broadcastMessage("" + loc.getBlockZ() + "  " + end.getBlockZ());
                if (
                        end.getBlockX()==(loc.getBlockX()) &&
                                end.getBlockY()==(loc.getBlockY()) &&
                                end.getBlockZ()==(loc.getBlockZ()) &&
                                end.getWorld()==(loc.getWorld())
                        ){
                    endParkour(e.getPlayer(), end);
                }
        }

        if (lk.getParkourData().plocs.containsKey(e.getPlayer())) {
            if (
                    (e.getPlayer().getLocation().getBlockX() == lk.getParkourData().plocs.get(e.getPlayer()).getBlockX()) &&
                            (e.getPlayer().getLocation().getBlockY() == lk.getParkourData().plocs.get(e.getPlayer()).getBlockY()) &&
                            (e.getPlayer().getLocation().getBlockZ() == lk.getParkourData().plocs.get(e.getPlayer()).getBlockZ())
                    ) {
                lk.getParkourData().plocs.put(e.getPlayer(), e.getPlayer().getLocation().toVector());
                return;
            }
        }
        lk.getParkourData().plocs.put(e.getPlayer(), e.getPlayer().getLocation().toVector());
        Location loc = e.getPlayer().getLocation();
        for (Location l : lk.getParkourData().getStartPoints()) {
            if (
                    l.getBlockX()==(e.getPlayer().getLocation().getBlockX()) &&
                            l.getBlockY()==(e.getPlayer().getLocation().getBlockY()) &&
                            l.getBlockZ()==(e.getPlayer().getLocation().getBlockZ()) &&
                            l.getWorld()==(e.getPlayer().getWorld())
                    ){
                startParkour(e.getPlayer(), l);
            }
        }
    }

    private void endParkour(Player p, Location l) {
        String pkname = lk.getParkourData().getParkourByEnd(l);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5test you finished the parkour mate!"));
        lk.getParkourData().parkourAttempts.remove(p);
    }

    private void startParkour(Player p, Location l) {
        String pkname = lk.getParkourData().getParkourByStart(l);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5test you started the parkour mate!"));
        lk.getParkourData().parkourAttempts.put(p, pkname);
        lk.getParkourData().parkourAttempsTimes.put(p, new StopWatch());
    }

}
