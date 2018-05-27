package com.georlegacy.general.lobbykeep.util;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class ActionBarTimer implements Runnable {
    private LobbyKeep lk;
    private Player p;
    public ActionBarTimer(Player p, LobbyKeep lk) {
        this.p = p;
        this.lk = lk;
    }

    @Override
    public void run() {
        System.out.println("in runnable duh");

        TextComponent prefix = new TextComponent("Current Time: ");
        prefix.setColor(ChatColor.DARK_GREEN);
        prefix.setBold(true);

        TextComponent time = new TextComponent(new DecimalFormat("#.#").format((float) lk.getParkourData().parkourAttemptTimes.get(p).elapsed(TimeUnit.MILLISECONDS)/1000F));
        time.setBold(true);
        time.setColor(ChatColor.GREEN);

        TextComponent suffix = new TextComponent(" seconds");
        suffix.setColor(ChatColor.DARK_GREEN);
        suffix.setBold(true);

        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, prefix, time, suffix);
    }

}
