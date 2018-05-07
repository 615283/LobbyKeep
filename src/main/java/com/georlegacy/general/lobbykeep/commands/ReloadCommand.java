package com.georlegacy.general.lobbykeep.commands;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private LobbyKeep lk;
    public ReloadCommand(LobbyKeep lk) {
        this.lk = lk;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        lk.reload();
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lSuccess: &7The config(s) have been reloaded!"));
        return true;
    }

}
