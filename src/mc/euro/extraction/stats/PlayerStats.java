 package mc.euro.extraction.stats;

import java.util.List;

import org.bukkit.Bukkit;

import mc.alk.arena.controllers.tracker.TrackerInterface;
import mc.alk.arena.objects.tracker.Stat;
import mc.alk.arena.objects.tracker.StatType;
import mc.alk.arena.objects.tracker.WLTRecord.WLT;
import mc.alk.arena.tracker.Tracker;
import mc.euro.extraction.HostagePlugin;

/**
 *
 * @author Nikolai
 */
public class PlayerStats {
    HostagePlugin plugin;
    public TrackerInterface tracker;
    boolean enabled;
    
    public PlayerStats(String x) {
        plugin = (HostagePlugin) Bukkit.getServer().getPluginManager().getPlugin("HostageArena");
        loadTracker(x);
    }
    
    public boolean isEnabled() {
        return enabled;
    }

    private void loadTracker(String i) {
        if ( Tracker.isEnabled() ){
            enabled = true;
            tracker = Tracker.getInterface(i);
            tracker.stopTracking(Bukkit.getOfflinePlayer(plugin.getConfig().getString("FakeName")));
        } 
        else {
            enabled = false;
            plugin.getLogger().warning("BattleTracker turned off or not found.");
        }
    }

    public void addPlayerRecord(String name, String bombs, WLT wlt) {
        if (this.isEnabled()) {
            tracker.addPlayerRecord(name, bombs, wlt);
        }
    }

    public List<Stat> getTopXWins(int n) {
        return tracker.getTopXWins(n);
    }

    public List<Stat> getTopX(StatType statType, int n) {
        return tracker.getTopX(statType, n);
    }  
}
