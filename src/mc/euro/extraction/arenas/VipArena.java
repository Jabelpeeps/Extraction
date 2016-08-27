package mc.euro.extraction.arenas;

import mc.alk.arena.objects.ArenaPlayer;
import mc.euro.extraction.HostagePlugin;
import mc.euro.extraction.nms.NPCFactory;


/**
 * VipArena: Either team can rescue/capture the VIPs.
 * 
 * Game can end in a tie.
 * 
 * @author Nikolai
 */
public class VipArena extends ExtractionArena {
 
    public VipArena(HostagePlugin plugin, NPCFactory npcFactory) {
        super(plugin, npcFactory);
    }
    
    @Override
    public boolean canInteract(ArenaPlayer ap) {
        return true;
    }
    
    @Override
    public boolean canRescue(ArenaPlayer ap) {
        return true;
    }

}
