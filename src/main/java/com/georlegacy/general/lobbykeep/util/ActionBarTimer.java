package com.georlegacy.general.lobbykeep.util;

import com.google.common.base.Stopwatch;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBarTimer {
    Player p;
    Stopwatch s = Stopwatch.createUnstarted();

    public ActionBarTimer(Player p) {
        this.p = p;
    }

    public void start() {
        s.start();
        send();
    }

    private void send() {
        if (!s.isRunning()) {
            return;
        }
        while (s.isRunning()) {
            TextComponent prefix = new TextComponent();
            TextComponent time = new TextComponent("");
            TextComponent suffix = new TextComponent();

            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, prefix, time, suffix);
        }
    }

    public void stop() {
        s.stop();
    }

}
