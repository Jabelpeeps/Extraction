package mc.euro.extraction.nms;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.server.v1_10_R1.BiomeBase;
import net.minecraft.server.v1_10_R1.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.EntityTypes;
import net.minecraft.server.v1_10_R1.EntityVillager;

/**
 * http://forums.bukkit.org/threads/nms-tutorial-how-to-override-default-minecraft-mobs.216788/
 *
 * @author TeeePeee
 */
@AllArgsConstructor
public enum CustomEntityType {

    HOSTAGE("Villager", 120, EntityType.VILLAGER, EntityVillager.class, Hostage.class);

    final public String author = "TeeePeee";
    final public String source = "http://forums.bukkit.org/threads/nms-tutorial-how-to-override-default-minecraft-mobs.216788/";

    @Getter private String name;
    @Getter private int iD;
    @Getter private EntityType entityType;
    private Class<? extends EntityInsentient> nmsClass;
    @Getter private Class<? extends EntityInsentient> customClass;


    public Class<? extends EntityInsentient> getNMSClass() {
        return nmsClass;
    }

    /**
     * Register our entities.
     */
    public static void registerEntities() {
        for (CustomEntityType entity : values()) {
            a(entity.getCustomClass(), entity.getName(), entity.getID());
        }

        for (BiomeBase biomeBase : BiomeBase.i ) {
            if (biomeBase == null) {
                break;
            }

            for (String field : new String[]{"u", "v", "w", "x"}) {
                try {
                    Field list = BiomeBase.class.getDeclaredField(field);
                    list.setAccessible(true);
                    @SuppressWarnings("unchecked")
                    List<BiomeMeta> mobList = (List<BiomeMeta>) list.get(biomeBase);

                    for (BiomeMeta meta : mobList) {
                        for (CustomEntityType entity : values()) {
                            if (entity.getNMSClass().equals(meta.b)) {
                                meta.b = entity.getCustomClass();
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Unregister our entities to prevent memory leaks. Call on disable.
     */
    public static void unregisterEntities() {
        for (CustomEntityType entity : values()) {
            try {
                ((Map) getPrivateStatic(EntityTypes.class, "d")).remove(entity.getCustomClass());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                ((Map) getPrivateStatic(EntityTypes.class, "f")).remove(entity.getCustomClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (CustomEntityType entity : values()) {
            try {
// Unregister each entity by writing the NMS back in place of the custom class.
                a(entity.getNMSClass(), entity.getName(), entity.getID());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        for (BiomeBase biomeBase : BiomeBase.i ) {
            if (biomeBase == null) {
                break;
            }

// The list fields changed names but update the meta regardless.
            for (String field : new String[]{"u", "v", "w", "x"}) {
                try {
                    Field list = BiomeBase.class.getDeclaredField(field);
                    list.setAccessible(true);
                    @SuppressWarnings("unchecked")
                    List<BiomeMeta> mobList = (List<BiomeMeta>) list.get(biomeBase);

// Make sure the NMS class is written back over our custom class.
                    for (BiomeMeta meta : mobList) {
                        for (CustomEntityType entity : values()) {
                            if (entity.getCustomClass().equals(meta.b)) {
                                meta.b = entity.getNMSClass();
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * A convenience method.
     *
     * @param clazz The class.
     * @param f The string representation of the private static field.
     * @return The object found
     * @throws Exception if unable to get the object.
     */
    private static Object getPrivateStatic(Class clazz, String f) throws Exception {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return field.get(null);
    }

    /*
     * Since 1.7.2 added a check in their entity registration, simply bypass it and write to the maps ourself.
     */
    @SuppressWarnings( "unchecked" )
    private static void a(Class paramClass, String paramString, int paramInt) {
        try {
            ((Map) getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
            ((Map) getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
            ((Map) getPrivateStatic(EntityTypes.class, "e")).put(Integer.valueOf(paramInt), paramClass);
            ((Map) getPrivateStatic(EntityTypes.class, "f")).put(paramClass, Integer.valueOf(paramInt));
            ((Map) getPrivateStatic(EntityTypes.class, "g")).put(paramString, Integer.valueOf(paramInt));
        } catch (Exception ex) {
            // Unable to register the new class.
            ex.printStackTrace();
        }
    }
}
