package com.georlegacy.general.lobbykeep.listeners;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeResetListener implements Listener {
    private LobbyKeep lk;
    public WorldChangeResetListener(LobbyKeep lk) {
        this.lk = lk;
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (lk.getParkourData().parkourAttempts.containsKey(e.getPlayer())) {
            Bukkit.getServer().getScheduler().cancelTask(lk.getParkourData().abts.get(e.getPlayer()));
            lk.getParkourData().parkourAttempts.remove(e.getPlayer());
            lk.getParkourData().parkourAttemptTimes.remove(e.getPlayer());
            TextComponent empty = new TextComponent("");
            e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, empty);

            lk.getLogger().info(e.getPlayer().getName() + " has changed worlds mid-parkour-attempt. The parkour attempt has been cancelled successfully.");
        }
    }

}
