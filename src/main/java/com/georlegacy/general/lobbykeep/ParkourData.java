package com.georlegacy.general.lobbykeep;

import org.apache.commons.lang.time.StopWatch;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkourData {
    private LobbyKeep lk;
    public ParkourData(LobbyKeep lk) {
        this.lk = lk;
    }

    public void load() {
        File file = new File(lk.getDataFolder() + File.separator + "parkour.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        parkour = YamlConfiguration.loadConfiguration(new File(lk.getDataFolder() + File.separator + "parkour.yml"));
    }

    public void save() {
        try {
            parkour.save(new File(lk.getDataFolder() + File.separator + "parkour.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration parkour;

    public HashMap<Player, Vector> plocs = new HashMap<Player, Vector>();

    public List<Location> getStartPoints() {
        List<Location> locs = new ArrayList<Location>();
        for (String s : lk.registeredParkours) {
            locs.add(new Location(
                    lk.getServer().getWorld(parkour.getString(s + ".World")),
                    parkour.getDouble(s + ".Start.X"),
                    parkour.getDouble(s + ".Start.Y"),
                    parkour.getDouble(s + ".Start.Z")
            ));
        }
        return locs;
    }

    public HashMap<Player, String> parkourAttempts = new HashMap<Player, String>();

    public Location getEndFromParkour(String name) {
        return new Location(
                    lk.getServer().getWorld(parkour.getString(name + ".World")),
                    parkour.getDouble(name + ".End.X"),
                    parkour.getDouble(name + ".End.Y"),
                    parkour.getDouble(name + ".End.Z"));
    }

    public String getParkourByStart(Location l) {
        for (String s : lk.registeredParkours) {
            if (new Location(
                    lk.getServer().getWorld(parkour.getString(s + ".World")),
                    parkour.getDouble(s + ".Start.X"),
                    parkour.getDouble(s + ".Start.Y"),
                    parkour.getDouble(s + ".Start.Z")
            ).equals(l)) {
                return s;
            }
        }
        return null;
    }

    public String getParkourByEnd(Location l) {
        for (String s : lk.registeredParkours) {
            if (new Location(
                    lk.getServer().getWorld(parkour.getString(s + ".World")),
                    parkour.getDouble(s + ".End.X"),
                    parkour.getDouble(s + ".End.Y"),
                    parkour.getDouble(s + ".End.Z")
            ).equals(l)) {
                return s;
            }
        }
        return null;
    }

}
