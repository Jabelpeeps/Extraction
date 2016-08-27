package mc.euro.extraction.timers;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import lombok.Getter;
import mc.alk.arena.competition.Match;
import mc.alk.arena.controllers.PlayerController;
import mc.alk.arena.objects.ArenaPlayer;
import mc.alk.arena.objects.arenas.Arena;
import mc.euro.extraction.events.ExtractionTimerEvent;
import mc.euro.extraction.events.HostageExtractedEvent;
import mc.euro.extraction.nms.Hostage;

/**
 *
 * @author Nikolai
 */
public class ExtractionTimer implements Runnable {
    
    static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("HostageArena");
    int duration;
    boolean started = false;
    BukkitTask task;
    Set<Hostage> extractionZone = new LinkedHashSet<>();
    
    @Getter Arena arena;
    @Getter Match match;
    
    public ExtractionTimer(Arena _arena) {
        this( _arena, plugin.getConfig().getInt("ExtractionTimer", 30) + 1 );
    }
    
    public ExtractionTimer(Arena _arena, int time) {
        duration = time;
        arena = _arena;
        match = arena.getMatch();
    }
    
    public int start() {
        if ( started == false ) {
            started = true;
            task = Bukkit.getScheduler().runTaskTimer(plugin, this, 20L, 20L);
        }
        return task.getTaskId();
    }
    
    public void stop() {
        if ( task != null ) {
            task.cancel();
            task = null;
        }
    }
    
    public boolean hasStarted() {
        return started;
    }
    
    @Override
    public void run() {
        duration -= 1;
        Bukkit.getPluginManager().callEvent( new ExtractionTimerEvent( arena, this ) );
        
        if (duration <= 0 && !extractionZone.isEmpty()) {
            
            for ( Hostage h : new LinkedHashSet<>(extractionZone) ) {
                ArenaPlayer rescuer = PlayerController.toArenaPlayer( h.getRescuer() );
                HostageExtractedEvent rescuedEvent = new HostageExtractedEvent(h, rescuer);
                Bukkit.getServer().getPluginManager().callEvent(rescuedEvent);
                h.removeEntity();
                extractionZone.remove(h);
            }
        }
    }
    
    public int getTime() {
        return duration;
    }

    public synchronized void setExtractionZone(Set<Hostage> hostagesAtExtractionZone) {
        extractionZone = hostagesAtExtractionZone;
    }
}
