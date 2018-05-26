package com.georlegacy.general.lobbykeep.util;

import com.google.common.base.Stopwatch;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class ActionBarTimer {
    Player p;
    Stopwatch s = Stopwatch.createUnstarted();

    public ActionBarTimer(Player p) {
        this.p = p;
        s.start();
        send();
    }

    private void send() {
        if (!s.isRunning()) {
            return;
        }
        while (s.isRunning()) {
            TextComponent prefix = new TextComponent("");

            TextComponent time = new TextComponent("" + s.elapsed(TimeUnit.SECONDS));
            time.setBold(true);
            time.setColor(ChatColor.GREEN);

            TextComponent suffix = new TextComponent("");

            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, prefix, time, suffix);
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public void stop() {
        s.stop();
    }

}
