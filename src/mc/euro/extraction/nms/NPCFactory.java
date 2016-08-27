package mc.euro.extraction.nms;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

import mc.euro.extraction.HostagePlugin;
import mc.euro.extraction.util.Villagers;
import net.minecraft.server.v1_10_R1.World;


public class NPCFactory {
    
    protected HostagePlugin plugin;
    
    public NPCFactory( HostagePlugin _plugin) {
        plugin = _plugin;
    }
  
    /**
     * The factory made it, so the factory should know if it's a Hostage or not.
     */
    public boolean isHostage(Entity entity) {
        return Hostage.class.isAssignableFrom( ((CraftEntity)entity).getHandle().getClass() );
    }
    
    /**
     * If the Entity is not a hostage, then it will get replaced with one.
     */
    public Hostage getHostage(Entity entity) {
        Hostage h;
        try {
            h = (Hostage) ((CraftEntity)entity).getHandle();
        } 
        catch (ClassCastException ex) {
            // Caused by baby villager or a non-Hostage Villager.
            plugin.debug().log("onHostageInteract() ClassCastException: most likely "
                    + "caused by a baby villager or a Villager that is not a Hostage.");
            
            Villager v = (Villager) entity;
            World world = ((CraftWorld) v.getWorld()).getHandle();
            
            Hostage hostage = new Hostage(world, v.getProfession());
            hostage.setLocation(v.getLocation());
            
            world.removeEntity(((CraftEntity)entity).getHandle());
            world.addEntity(hostage);
            
            hostage.setHealth((float) v.getHealth());
            hostage.setProfessionType(v.getProfession());
            hostage.setCustomName(v.getCustomName());
            
            return hostage;
        }
        return h;
    }
    
    public Hostage spawnHostage(Location loc) {
        Profession profession = Villagers.getRandomType();
        return spawnHostage(loc, profession);
    }
    
    public Hostage spawnHostage(Location loc, Profession prof) {
        World world = ((CraftWorld) loc.getWorld()).getHandle();
        Hostage hostage = new Hostage(world, prof );
        hostage.setLocation(loc);
        world.addEntity(hostage);
        return hostage;
    }
}
