package com.georlegacy.general.lobbykeep.commands;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ParkourCreateCommand implements CommandExecutor {
    private LobbyKeep lk;
    public ParkourCreateCommand(LobbyKeep lk) {
        this.lk = lk;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length!=7) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError: &7You provided incorrect arguments, correct arguments are as follows:"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/pkcreate <name> <startX> <startY> <startZ> <endX> <endY> <endZ>"));
        }
        else {
            if (lk.getParkourData().parkour.contains(args[0] + ".World")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError: &7That parkour route already exists, try a different name."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/pkcreate <name> <startX> <startY> <startZ> <endX> <endY> <endZ>"));
            } else {
                int sx; int sy; int sz; int fx; int fy; int fz;
                try {
                    sx = Integer.parseInt(args[1]);
                    sy = Integer.parseInt(args[2]);
                    sz = Integer.parseInt(args[3]);
                    fx = Integer.parseInt(args[4]);
                    fy = Integer.parseInt(args[5]);
                    fz = Integer.parseInt(args[6]);
                } catch (NumberFormatException ex) {
                    sx = 0; sy = 0; sz = 0; fx = 0; fy = 0; fz = 0;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError: &7One or more of the coordinates you provided is not a number, please try again."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/pkcreate <name> <startX> <startY> <startZ> <endX> <endY> <endZ>"));
                    return true;
                }

                List<String> pknames = lk.getParkourData().parkour.getStringList("RegisteredParkourNames");
                pknames.add(args[0]);
                lk.getParkourData().parkour.set("RegisteredParkourNames", pknames);
                lk.getParkourData().parkour.set(args[0] + ".World", ((Player) sender).getWorld().getName());
                lk.getParkourData().parkour.set(args[0] + ".Start.X", sx);
                lk.getParkourData().parkour.set(args[0] + ".Start.Y", sy);
                lk.getParkourData().parkour.set(args[0] + ".Start.Z", sz);
                lk.getParkourData().parkour.set(args[0] + ".End.X", fx);
                lk.getParkourData().parkour.set(args[0] + ".End.Y", fy);
                lk.getParkourData().parkour.set(args[0] + ".End.Z", fz);
                lk.getParkourData().save();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lSuccess: &7The parkour route &e" + args[0] + " &7 has been created."));
                lk.reload();

            }
        }
        return true;
    }
}
