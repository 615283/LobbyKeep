package com.georlegacy.general.lobbykeep.commands;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class ParkourTopCommand implements CommandExecutor {
    private LobbyKeep lk;
    public ParkourTopCommand(LobbyKeep lk) {
        this.lk = lk;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length!=1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| &cYou have provided incorrect arguments. Correct usage is &7/pktop <parkour>"));
            return true;
        }
        if (!lk.registeredParkours.contains(args[0])) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| " + args[0] + " &cdoesn't appear to be a valid parkour. Please try again."));
            return true;
        }
        HashMap<String, Double> times = new HashMap<String, Double>();
        for (String key : lk.getParkourData().parkour.getKeys(true)) {
            System.out.println(key.replace(args[0] + ".Attempts.", ""));
            if (!key.startsWith(args[0] + ".Attempts")) {
                continue;
            }
            System.out.println(lk.getUserManager().getFromUUID(key.replace(args[0] + ".Attempts.", "")));
            times.put(lk.getUserManager().getFromUUID(key.replaceFirst(args[0] + ".Attempts.", "")), lk.getParkourData().parkour.getDouble(key));
        }
        if (times.isEmpty()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| &cThere have not been any attempts as of yet on this parkour."));
            return true;
        }
        int i = 1;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| &bReading top 10 times"));
        for (String pname : times.keySet()) {
            System.out.println(pname);
            System.out.println(times.get(pname));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + i + ". &e" + pname + "&7: &e" + times.get(pname)));
            i++;
            if (i>10) break;
        }
        return true;
    }
}
