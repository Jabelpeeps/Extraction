package mc.euro.extraction.factory;

import mc.alk.arena.objects.arenas.Arena;
import mc.alk.arena.objects.arenas.ArenaFactory;
import mc.euro.extraction.HostagePlugin;
import mc.euro.extraction.arenas.VipArena;
import mc.euro.extraction.nms.NPCFactory;

/**
 * 
 * 
 * @author Nikolai
 */
public class VipArenaFactory implements ArenaFactory {
    
    HostagePlugin plugin;
    NPCFactory npcFactory;
    
    public VipArenaFactory(HostagePlugin reference) {
        plugin = reference;
        npcFactory = new NPCFactory(plugin);
    }
    
    public VipArenaFactory(HostagePlugin reference, NPCFactory _npcFactory) {
        plugin = reference;
        npcFactory = _npcFactory;
    }

    @Override
    public Arena newArena() {
        Arena arena = new VipArena(plugin, npcFactory);
        return arena;
    }
}
