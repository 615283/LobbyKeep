package com.georlegacy.general.lobbykeep.commands;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class PKStartCommand implements CommandExecutor {
    private LobbyKeep lk;
    public PKStartCommand(LobbyKeep lk) {
        this.lk = lk;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length!=1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| &cYou have provided incorrect arguments. Correct usage is &7/pkstart <parkour>"));
            return true;
        }
        boolean is = false;
        for (String rpn : lk.registeredParkours) {
            if (rpn.toLowerCase().equals(args[0].toLowerCase())) {
                is = true;
                break;
            }
        }
        if (!is) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| " + args[0] + " &cdoesn't appear to be a valid parkour. Please try again."));
            return true;
        }
        Location start = new Location(
                Bukkit.getServer().getWorld(lk.getParkourData().parkour.getString(args[0] + ".World")),
                lk.getParkourData().parkour.getInt(args[0] + ".Start.X"),
                lk.getParkourData().parkour.getInt(args[0] + ".Start.Y"),
                lk.getParkourData().parkour.getInt(args[0] + ".Start.Z"));
        ((Player) sender).teleport(start);
        return true;
    }
}
