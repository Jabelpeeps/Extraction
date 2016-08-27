package mc.euro.extraction.factory;

import mc.alk.arena.objects.arenas.Arena;
import mc.alk.arena.objects.arenas.ArenaFactory;
import mc.euro.extraction.HostagePlugin;
import mc.euro.extraction.arenas.HostageArena;
import mc.euro.extraction.nms.NPCFactory;

/**
 * 
 * 
 * @author Nikolai
 */
public class HostageArenaFactory implements ArenaFactory {
    
    HostagePlugin plugin;
    NPCFactory npcFactory;
    
    public HostageArenaFactory(HostagePlugin reference) {
        plugin = reference;
        npcFactory = new NPCFactory(plugin);
    }
    
    public HostageArenaFactory(HostagePlugin reference, NPCFactory _npcFactory) {
        plugin = reference;
        npcFactory = _npcFactory;
    }

    @Override
    public Arena newArena() {
        Arena arena = new HostageArena(plugin, npcFactory);
        return arena;
    }
}
