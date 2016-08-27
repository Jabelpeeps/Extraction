package mc.euro.extraction.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.alk.arena.objects.arenas.Arena;
import mc.euro.extraction.timers.ExtractionTimer;

/**
 * 
 * 
 * @author Nikolai
 */
@AllArgsConstructor
public class ExtractionTimerEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    @Getter private final Arena arena;
    private final ExtractionTimer timer;
    
    public int getTime() {
        return timer.getTime();
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }   
}
