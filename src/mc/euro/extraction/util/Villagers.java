package mc.euro.extraction.util;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Nikolai
 */
public class Villagers {
    
    private static int tCounter = 0; // type counter
    private static int nCounter = 0; // name counter
    
    private static List<Profession> allProfs = new ArrayList<>(EnumSet.range( Profession.NORMAL, Profession.BUTCHER ));
    private static List<String> professions;
    
    static {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("HostageArena");
        professions = plugin.getConfig().getStringList( "HostageTypes" );
    }
    
    public static Profession getType() {
        Profession p = Profession.FARMER;
        Villagers.tCounter = Villagers.tCounter + 1;
        if ( professions != null && professions.size() > 0 && Villagers.tCounter <= professions.size() ) {
            try {
                p = Profession.valueOf(professions.get(tCounter - 1));
            } catch (IllegalArgumentException ex) {
                p = getRandomType();
            }
            if (Villagers.tCounter >= professions.size()) Villagers.tCounter = 0;
        }
        return p;
    }
    public static int getIndex( Profession p ) {
        return allProfs.indexOf( p );
    }
    public static Profession getbyInt( int i ) {
        return allProfs.get( i );
    }   
    public static Profession getRandomType() {
        return getbyInt( new Random().nextInt(5) );
    }
    
    public static String getName() {
        
        String name = "VIP";
        Plugin plugin = Bukkit.getPluginManager().getPlugin("HostageArena");
        Villagers.nCounter = Villagers.nCounter + 1;
        List<String> names = plugin.getConfig().getStringList("HostageNames");
        if (names != null && names.size() > 0 && Villagers.nCounter <= names.size()) {
            name = names.get(nCounter - 1);
            if (Villagers.nCounter >= names.size()) Villagers.nCounter = 0;
        }
        return name;
    }
}
