package com.georlegacy.general.lobbykeep.util;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;

public class UserManager {
    private LobbyKeep lk;
    public UserManager(LobbyKeep lk) {
        this.lk = lk;
    }

    private HashMap<String, String> users;

    public void load() {
        users = new HashMap<String, String>();
        File f = new File(lk.getDataFolder() + File.separator + "users.dat");
        ObjectInputStream ois;
        if (!f.exists()) {
            try {
                f.createNewFile();
                users = new HashMap<String, String>();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ois = new ObjectInputStream(new FileInputStream(f));
            users = (HashMap<String, String>) ois.readUnshared();
            System.out.println(users);
            ois.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        System.out.println(users);
        File f = new File(lk.getDataFolder() + File.separator + "users.dat");
        ObjectOutputStream oos;
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (users == null) return;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.reset();
            oos.writeUnshared(users);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(Player p) {
        users.put(p.getUniqueId().toString(), p.getName());
    }

    public String getFromUUID(String uuid) {
        System.out.println("getting from uuid");
        return users.get(uuid);
    }

    public HashMap<String, String> get() {
        return users;
    }

}
