package com.georlegacy.general.lobbykeep.commands;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
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
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(Click below to begin a parkour)"));
        }

        int i = 0;
        for ( String pkname : lk.registeredParkours) {
            TextComponent prefix = new TextComponent("► ");
            prefix.setColor(net.md_5.bungee.api.ChatColor.DARK_GREEN);
            TextComponent text = new TextComponent(pkname);
            text.setColor(net.md_5.bungee.api.ChatColor.GREEN);
            if (sender.hasPermission("lobbykeep.parkour.start")) {
                text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pkstart " + pkname));
                TextComponent[] hover = { new TextComponent("Click to begin this parkour!") };
                text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
                prefix.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pkstart " + pkname));
            }
            sender.spigot().sendMessage(prefix, text);
            i++;
        }
        return true;
    }
}
