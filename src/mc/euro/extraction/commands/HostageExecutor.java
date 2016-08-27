package mc.euro.extraction.commands;

import org.bukkit.command.CommandSender;

import mc.alk.arena.executors.MCCommand;
import mc.alk.arena.objects.arenas.Arena;
import mc.euro.extraction.arenas.HostageArena;

/**
 * 
 * @author Nikolai
 */
public class HostageExecutor extends ExtractionExecutor {

    @MCCommand( cmds = {"set"}, subCmds = {"guardInteraction","interaction"}, perm = "vips.set.guardinteraction" )
    public boolean setGuardInteraction(CommandSender sender, Arena a, boolean allow) {
        if (!(a instanceof HostageArena)) {
            sender.sendMessage("Arena must be a valid HostageArena.");
        }
        HostageArena arena = (HostageArena) a;
        arena.AllowGuardsToInteractWithHostages = allow;
        sender.sendMessage("Base added to arena: " + a.getName());
        arenaController.updateArena(arena);
        saveAllArenas();
        return true;
    }

}
