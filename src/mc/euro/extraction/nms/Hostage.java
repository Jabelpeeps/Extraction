package mc.euro.extraction.nms;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

import com.google.common.collect.Sets;

import mc.euro.extraction.util.Villagers;
import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntityAgeable;
import net.minecraft.server.v1_10_R1.EntityOwnable;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.EntityVillager;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_10_R1.World;

/**
 *
 * @author Nikolai
 */
public class Hostage extends EntityVillager implements EntityOwnable {
    
    private UUID owner;
    private UUID lastOwner;
    
    public Hostage(World w) {
        super(w);
        clearPathfinders();
        this.goalSelector.a(10, new PathfinderGoalFollowPlayer(this, 1.0D, 2.0F, 2.0F));
    }
    
    public Hostage(World w, Profession p ) {
        super(w);
        setProfessionType(p); 
        clearPathfinders();
        this.goalSelector.a(10, new PathfinderGoalFollowPlayer(this, 1.0D, 2.0F, 2.0F));
    }
    
    private void clearPathfinders() {
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(goalSelector, Sets.newLinkedHashSet());
            bField.set(targetSelector, Sets.newLinkedHashSet());
            cField.set(goalSelector, Sets.newLinkedHashSet());
            cField.set(targetSelector, Sets.newLinkedHashSet());
        } 
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    
    public void stay() {
        lastOwner = owner;
        owner = null;
    }
    
    public boolean isStopped() {
        return owner == null;
    }
    
    public boolean isFollowing() {
        return owner != null;
    }
    
    public void follow(Player p) {
        follow(p.getUniqueId());
    }
    
    public void follow(UUID p) {
        owner = p;
    }
    
    public void setOwner(Player p) {
        setOwner( p.getUniqueId() );
    }
    
    public void setOwner(UUID name) {
        owner = name;
    }

    @Override
    public Entity getOwner() {
        if ( owner == null ) return null;
        Player player = Bukkit.getPlayer( owner );
        EntityPlayer ep = ((CraftPlayer)player).getHandle();
        return ep;
    }
    
    @Override
    public UUID getOwnerUUID() {     
        return owner;
    }
    
    public Location getLocation() {
        return new Location(world.getWorld(), locX, locY, locZ, yaw, pitch);
    }
    
    public void setLocation(Location loc) {
        setLocation( loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch() );
    }

    public void removeEntity() {
        world.removeEntity(this);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ea) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Profession getProfessionType() {
        return Villagers.getbyInt( getProfession() );
    }
    
    public void setProfessionType( Profession x ) {
        setProfession( Villagers.getIndex( x ) );
    }

    public void setHealth( double health ) {
        setHealth( (float) health );
    }
    
    public Player getRescuer() {
        UUID uuid = (owner == null) ? lastOwner : owner;
        if (uuid == null) return null;
        Player rescuer = Bukkit.getPlayer(uuid);
        return rescuer;
    }
}
