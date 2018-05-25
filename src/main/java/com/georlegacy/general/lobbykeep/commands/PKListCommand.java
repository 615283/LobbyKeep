package com.georlegacy.general.lobbykeep.commands;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PKListCommand implements CommandExecutor {
    private LobbyKeep lk;
    public PKListCommand(LobbyKeep lk) {
        this.lk = lk;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m---&2&l[ &aParkours &2&l]&2&l&m---"));
        if (sender.hasPermission("lobbykeep.parkour.start")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m--&2&l[ &aClick below to start a parkour &2&l]&2&l&m--"));
        }

        int i = 0;
        for ( String pkname : lk.registeredParkours) {
            TextComponent text = new TextComponent(pkname);
            text.setColor(net.md_5.bungee.api.ChatColor.GREEN);
            if (sender.hasPermission("lobbykeep.parkour.start")) {
                text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pkstart " + pkname));
            }
            sender.spigot().sendMessage(text);
            i++;
        }


        return false;
    }
}
