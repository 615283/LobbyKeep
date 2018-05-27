package com.georlegacy.general.lobbykeep.commands;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PKStartCommand implements CommandExecutor {
    private LobbyKeep lk;
    public PKStartCommand(LobbyKeep lk) {
        this.lk = lk;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==0) {
            if (!lk.getParkourData().parkourAttempts.containsKey((Player) sender)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| &cYou are not currently attempting a parkour. To start one, use &7/pkstart <parkour>"));
                return true;
            }
            String pkn = lk.getParkourData().parkourAttempts.get((Player) sender);Bukkit.getServer().getScheduler().cancelTask(lk.getParkourData().abts.get((Player) sender));
            lk.getParkourData().parkourAttempts.remove((Player) sender);
            lk.getParkourData().parkourAttemptTimes.remove((Player) sender);
            TextComponent empty = new TextComponent("");
            ((Player) sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, empty);

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| &aSending you to &2" + pkn));
            Location start = new Location(
                    Bukkit.getServer().getWorld(lk.getParkourData().parkour.getString(pkn + ".World")),
                    lk.getParkourData().parkour.getInt(pkn + ".Start.X"),
                    lk.getParkourData().parkour.getInt(pkn + ".Start.Y"),
                    lk.getParkourData().parkour.getInt(pkn + ".Start.Z"));
            ((Player) sender).teleport(start);
            return true;
        }
        if (args.length!=1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| &cYou have provided incorrect arguments. Correct usage is &7/pkstart <parkour>"));
            return true;
        }
        boolean is = false;
        String pkn = null;
        for (String rpn : lk.registeredParkours) {
            if (rpn.toLowerCase().equals(args[0].toLowerCase())) {
                is = true;
                pkn = rpn;
                break;
            }
        }
        if (!is) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| " + args[0] + " &cdoesn't appear to be a valid parkour. Please try again."));
            return true;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| &aSending you to &2" + pkn));
        Location start = new Location(
                Bukkit.getServer().getWorld(lk.getParkourData().parkour.getString(pkn + ".World")),
                lk.getParkourData().parkour.getInt(pkn + ".Start.X"),
                lk.getParkourData().parkour.getInt(pkn + ".Start.Y"),
                lk.getParkourData().parkour.getInt(pkn + ".Start.Z"));
        ((Player) sender).teleport(start);
        return true;
    }
}
