//Base archetype by James Conway (615283)

package com.georlegacy.general.lobbykeep;

import com.georlegacy.general.lobbykeep.commands.ParkourCreateCommand;
import com.georlegacy.general.lobbykeep.commands.ReloadCommand;
import com.georlegacy.general.lobbykeep.listeners.FallListener;
import com.georlegacy.general.lobbykeep.listeners.PKMoveListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LobbyKeep extends JavaPlugin {
    private ParkourData parkourData = new ParkourData(this);
    public ParkourData getParkourData() {
        return parkourData;
    }

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new FallListener(this), this);
        pm.registerEvents(new PKMoveListener(this), this);

        getCommand("pkcreate").setExecutor(new ParkourCreateCommand(this));
        getCommand("lkreload").setExecutor(new ReloadCommand(this));

        if (!new File(getDataFolder() + File.separator + "config.yml").exists()) {
            saveResource("config.yml", true);
        }
        reload();
        getParkourData().load();
    }

    public void reload() {
        this.getParkourData().parkour = YamlConfiguration.loadConfiguration(new File(this.getDataFolder() + File.separator + "parkour.yml"));
    }

    public String startmsg() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + "config.yml"));
        return config.getString("PKStartMsg");
    }

    public String endmsg() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + "config.yml"));
        return config.getString("PKEndMsg");
    }

    public List<String> registeredParkours() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + "config.yml"));
        return getParkourData().parkour.getStringList("RegisteredParkourNames");
    }

    public boolean diffLevels() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + "config.yml"));
        return config.getBoolean("DiffLevels");
    }

    public int level() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + "config.yml"));
        return config.getInt("FallLimit");
    }

    public List<String> worlds() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + "config.yml"));
        return config.getStringList("WorldNames");
    }

}
