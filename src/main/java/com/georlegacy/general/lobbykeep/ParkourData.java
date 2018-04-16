package com.georlegacy.general.lobbykeep;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    }

    public YamlConfiguration parkour = YamlConfiguration.loadConfiguration(new File(lk.getDataFolder() + File.separator + "parkour.yml"));

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

}
