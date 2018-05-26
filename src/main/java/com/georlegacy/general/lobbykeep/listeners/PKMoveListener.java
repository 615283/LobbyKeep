package com.georlegacy.general.lobbykeep.listeners;

import com.georlegacy.general.lobbykeep.LobbyKeep;
import com.google.common.base.Stopwatch;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class PKMoveListener implements Listener {
    private LobbyKeep lk;
    public PKMoveListener(LobbyKeep lk) {
        this.lk = lk;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (lk.getParkourData().parkourAttempts.containsKey(e.getPlayer())) {
            Location loc = e.getPlayer().getLocation();
            Location end = lk.getParkourData().getEndFromParkour(lk.getParkourData().parkourAttempts.get(e.getPlayer()));
                if (
                        end.getBlockX()==(loc.getBlockX()) &&
                                end.getBlockY()==(loc.getBlockY()) &&
                                end.getBlockZ()==(loc.getBlockZ()) &&
                                end.getWorld()==(loc.getWorld())
                        ){
                    endParkour(e.getPlayer(), end);
                }
        }

        if (lk.getParkourData().plocs.containsKey(e.getPlayer())) {
            if (
                    (e.getPlayer().getLocation().getBlockX() == lk.getParkourData().plocs.get(e.getPlayer()).getBlockX()) &&
                            (e.getPlayer().getLocation().getBlockY() == lk.getParkourData().plocs.get(e.getPlayer()).getBlockY()) &&
                            (e.getPlayer().getLocation().getBlockZ() == lk.getParkourData().plocs.get(e.getPlayer()).getBlockZ())
                    ) {
                lk.getParkourData().plocs.put(e.getPlayer(), e.getPlayer().getLocation().toVector());
                return;
            }
        }
        lk.getParkourData().plocs.put(e.getPlayer(), e.getPlayer().getLocation().toVector());
        Location loc = e.getPlayer().getLocation();
        for (Location l : lk.getParkourData().getStartPoints()) {
            if (
                    l.getBlockX()==(e.getPlayer().getLocation().getBlockX()) &&
                            l.getBlockY()==(e.getPlayer().getLocation().getBlockY()) &&
                            l.getBlockZ()==(e.getPlayer().getLocation().getBlockZ()) &&
                            l.getWorld()==(e.getPlayer().getWorld())
                    ){
                startParkour(e.getPlayer(), l);
            }
        }
    }

    private void endParkour(Player p, Location l) {
        String pkname = lk.getParkourData().getParkourByEnd(l);
        if (lk.getParkourData().parkourAttempts.containsKey(p)) {
            if (!lk.getParkourData().parkourAttempts.get(p).equals(pkname)) return;
            lk.getParkourData().parkourAttempts.remove(p);
            Stopwatch timer = lk.getParkourData().parkourAttemptTimes.get(p);
            lk.getParkourData().parkourAttemptTimes.remove(p);
            timer.stop();
            float secs = (float) timer.elapsed(TimeUnit.MILLISECONDS)/1000F;
            Firework firework = (Firework) p.getWorld().spawn(p.getLocation(), Firework.class);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.setPower(0);
            fireworkMeta.addEffect(FireworkEffect.builder().trail(true).withColor(Color.GREEN, Color.LIME, Color.OLIVE).flicker(true).with(FireworkEffect.Type.BALL_LARGE).build());
            firework.setFireworkMeta(fireworkMeta);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            p.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, p.getLocation(), 25);
            if (!lk.getParkourData().parkour.getKeys(true).contains(pkname + ".Attempts." + p.getUniqueId().toString())) {
                lk.getParkourData().parkour.set(pkname + ".Attempts." + p.getUniqueId().toString(), Float.parseFloat(new DecimalFormat("#.#").format(secs)));
            } else if (lk.getParkourData().parkour.getDouble(pkname + ".Attempts." + p.getUniqueId().toString()) > Float.parseFloat(new DecimalFormat("#.#").format(secs))) {
                lk.getParkourData().parkour.set(pkname + ".Attempts." + p.getUniqueId().toString(), Float.parseFloat(new DecimalFormat("#.#").format(secs)));
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(lk.endmsg, new DecimalFormat("#.#").format(secs))));
            lk.getParkourData().save();
        }
    }

    private void startParkour(Player p, Location l) {
        String pkname = lk.getParkourData().getParkourByStart(l);
        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', lk.startmsg));
        lk.getParkourData().parkourAttempts.put(p, pkname);
        lk.getParkourData().parkourAttemptTimes.put(p, Stopwatch.createStarted());
    }

}
