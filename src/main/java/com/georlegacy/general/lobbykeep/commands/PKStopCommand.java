package com.georlegacy.general.lobbykeep.commands;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PKStopCommand implements CommandExecutor {
    private LobbyKeep lk;
    public PKStopCommand(LobbyKeep lk) {
        this.lk = lk;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!lk.getParkourData().parkourAttempts.containsKey((Player) sender)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| &cYou don't currently appear to be attempting any parkour."));
            return true;
        }
        Bukkit.getServer().getScheduler().cancelTask(lk.getParkourData().abts.get((Player) sender));
        lk.getParkourData().parkourAttempts.remove((Player) sender);
        lk.getParkourData().parkourAttemptTimes.remove((Player) sender);
        TextComponent empty = new TextComponent("");
        ((Player) sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, empty);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| &aYour current parkour attempt has been cancelled successfully."));
        return true;
    }

}
