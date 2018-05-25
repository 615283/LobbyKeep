package com.georlegacy.general.lobbykeep.commands;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

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
            if (!key.startsWith(args[0] + ".Attempts.")) {
                continue;
            }
            times.put(lk.getUserManager().getFromUUID(key.replaceFirst(args[0] + ".Attempts.", "")), lk.getParkourData().parkour.getDouble(key));
        }
        if (times.isEmpty()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBC &7| &cThere have not been any attempts as of yet on this parkour."));
            return true;
        }

        List<Map.Entry<String, Double>> sortedTimes = new LinkedList<Map.Entry<String, Double>>(times.entrySet());
        Collections.sort(sortedTimes, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        int i = 1;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m---&2&l[ &aTop 10 Times &2&l]&2&l&m---"));
        for (Map.Entry<String, Double> entry : sortedTimes) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + i + ". &a" + entry.getKey() + "&7: &a" + entry.getValue()));
            i++;
            if (i>10) break;
        }
        return true;
    }
}
