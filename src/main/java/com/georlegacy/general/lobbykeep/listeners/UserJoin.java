package com.georlegacy.general.lobbykeep.listeners;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserJoin implements Listener {
    private LobbyKeep lk;
    public UserJoin(LobbyKeep lk) {
        this.lk = lk;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        lk.getUserManager().add(e.getPlayer());
    }

}
