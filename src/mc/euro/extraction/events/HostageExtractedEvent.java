package mc.euro.extraction.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.alk.arena.objects.ArenaPlayer;
import mc.euro.extraction.nms.Hostage;

/**
 * 
 * 
 * @author Nikolai
 */
@AllArgsConstructor
public class HostageExtractedEvent extends Event {
    
    private static final HandlerList handlers = new HandlerList();
    @Getter Hostage hostage;
    @Getter ArenaPlayer rescuer;
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
