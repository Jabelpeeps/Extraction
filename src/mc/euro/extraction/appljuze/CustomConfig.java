package mc.euro.extraction.appljuze;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 * https://www.youtube.com/watch?v=pyBM83LbG9U <br/>
 * https://dl.dropboxusercontent.com/u/48481378/MyConfig.java <br/>
 * 
 * @author Appljuze
 */
public class CustomConfig {
    private int comments;
    private ConfigManager manager;
 
    private File file;
    private FileConfiguration config;
 
    public CustomConfig(InputStream configStream, File configFile, int _comments, JavaPlugin plugin) {
        comments = _comments;
        manager = new ConfigManager(plugin);
        file = configFile;
        config = YamlConfiguration.loadConfiguration(configFile);
    }
    
    public ItemStack getItemStack(String path) { return config.getItemStack(path);}
    
    public ItemStack getItemStack(String path, ItemStack item) { return config.getItemStack(path, item);}
    
    public Vector getVector(String path) {return config.getVector(path);}
 
    public Object get(String path) {return config.get(path);}
 
    public Object get(String path, Object def) {return config.get(path, def);}
 
    public String getString(String path) {return config.getString(path);}
 
    public String getString(String path, String def) {return config.getString(path, def);}
 
    public int getInt(String path) {return config.getInt(path);}
 
    public int getInt(String path, int def) {return config.getInt(path, def);}
 
    public boolean getBoolean(String path) {return config.getBoolean(path);}
 
    public boolean getBoolean(String path, boolean def) {return config.getBoolean(path, def);}
 
    public void createSection(String path) {config.createSection(path);}
 
    public ConfigurationSection getConfigurationSection(String path) {return config.getConfigurationSection(path);}
 
    public double getDouble(String path) {return config.getDouble(path);}
 
    public double getDouble(String path, double def) {return config.getDouble(path, def);}
 
    public List<?> getList(String path) {return config.getList(path);}
 
    public List<?> getList(String path, List<?> def) {return config.getList(path, def);}
 
    public boolean contains(String path) {return config.contains(path);}
 
    public void removeKey(String path) { config.set(path, null);}
 
    public void set(String path, Object value) { config.set(path, value);}
 
    public void set(String path, Object value, String comment) {
        if ( !config.contains(path) ) {
            config.set(manager.getPluginName() + "_COMMENT_" + comments, " " + comment);
            comments++;
        }
        config.set(path, value);
    }
 
    public void set(String path, Object value, String[] comment) {
        for(String comm : comment) {
            if ( !config.contains(path) ) {
                config.set(manager.getPluginName() + "_COMMENT_" + comments, " " + comm);
                comments++;
            }
        }
        config.set(path, value);
    }
 
    public void setHeader(String[] header) {
        manager.setHeader( file, header);
        comments = header.length + 2;
        reloadConfig();
    }
 
    public void reloadConfig() { config = YamlConfiguration.loadConfiguration( file ); }
 
    public void saveConfig() {
        manager.saveConfig( config.saveToString(), file );
    }
 
    public Set<String> getKeys() { return config.getKeys(false); }

    public List<String> getStringList( String path ) {
        return config.getStringList( path );
    }
}
