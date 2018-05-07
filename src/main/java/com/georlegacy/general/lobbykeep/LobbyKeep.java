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
        getParkourData().load();
        registeredParkours = getParkourData().parkour.getStringList("RegisteredParkourNames");
    }

    public void reload() {
        this.getParkourData().parkour = YamlConfiguration.loadConfiguration(new File(this.getDataFolder() + File.separator + "parkour.yml"));
        startmsg = getConfig().getString("PKStartMsg");
        endmsg = getConfig().getString("PKEndMsg");
        registeredParkours = getParkourData().parkour.getStringList("RegisteredParkourNames");
        diffLevels = getConfig().getBoolean("DiffLevels");
        level = getConfig().getInt("FallLimit");
        worlds = getConfig().getStringList("WorldNames");
    }

    public String startmsg = getConfig().getString("PKStartMsg");

    public String endmsg = getConfig().getString("PKEndMsg");

    public List<String> registeredParkours;

    public boolean diffLevels = getConfig().getBoolean("DiffLevels");

    public int level = getConfig().getInt("FallLimit");

    public List<String> worlds = getConfig().getStringList("WorldNames");

}
