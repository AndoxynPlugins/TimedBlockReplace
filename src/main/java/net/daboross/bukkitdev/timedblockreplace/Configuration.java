package net.daboross.bukkitdev.timedblockreplace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

    private final TimedBlockReplacePlugin tbc;
    private final Map<Material, ReplaceInfo> blocksToReplace = new HashMap<Material, ReplaceInfo>();

    public Configuration(TimedBlockReplacePlugin tbc) throws TimedBlockReplacePlugin.NotAMaterial {
        this.tbc = tbc;
        reloadConfig();
    }

    public void reloadConfig() throws TimedBlockReplacePlugin.NotAMaterial {
        blocksToReplace.clear();

        FileConfiguration config = tbc.getConfig();

        List<?> objs = config.getList(TimedBlockReplacePlugin.CONFIG_FROMBLOCK_LIST);

        for (Object object : objs) {
            String objString = String.valueOf(object);
            Material material = TimedBlockReplacePlugin.parseName(objString);
            String endType = String.valueOf(config.get(TimedBlockReplacePlugin.CONFIG_TO_BLOCK_PREFIX + objString));
            Material endMat = TimedBlockReplacePlugin.parseName(endType);
            int timeout = config.getInt(TimedBlockReplacePlugin.CONFIG_TIMES_PREFIX + objString, -1);
            blocksToReplace.put(material, new ReplaceInfo(endMat, timeout));
            if (!objString.equals(material.toString())) {
                config.set(TimedBlockReplacePlugin.CONFIG_TIMES_PREFIX + objString, null);
                config.set(TimedBlockReplacePlugin.CONFIG_TIMES_PREFIX + material, timeout);
                config.set(TimedBlockReplacePlugin.CONFIG_TO_BLOCK_PREFIX + objString, null);
                config.set(TimedBlockReplacePlugin.CONFIG_TO_BLOCK_PREFIX + material, String.valueOf(endMat));
            }
        }
    }

    public boolean shouldReplace(Material mat) {
        return blocksToReplace.containsKey(mat);
    }

    public ReplaceInfo replacing(Material mat) {
        return blocksToReplace.get(mat);
    }

    private void updateConfigList() {
        List<String> listToSave = new ArrayList<String>(blocksToReplace.size());
        for (Material mat : blocksToReplace.keySet()) {
            listToSave.add(mat.toString());
        }
        tbc.getConfig().set(TimedBlockReplacePlugin.CONFIG_FROMBLOCK_LIST, listToSave);
    }

    public void addReplace(Material fromMaterial, Material toMaterial, int timeout) {
        blocksToReplace.put(fromMaterial, new ReplaceInfo(toMaterial, timeout));

        FileConfiguration config = tbc.getConfig();

        config.set(TimedBlockReplacePlugin.CONFIG_TIMES_PREFIX + fromMaterial, timeout);
        config.set(TimedBlockReplacePlugin.CONFIG_TO_BLOCK_PREFIX + fromMaterial, String.valueOf(toMaterial));
        updateConfigList();
        tbc.saveConfig();
    }

    public boolean removeReplace(Material fromMaterial) {
        FileConfiguration config = tbc.getConfig();

        boolean wasRemoved = blocksToReplace.remove(fromMaterial) != null;
        config.set(TimedBlockReplacePlugin.CONFIG_TIMES_PREFIX + fromMaterial, null);
        config.set(TimedBlockReplacePlugin.CONFIG_TO_BLOCK_PREFIX + fromMaterial, null);
        updateConfigList();
        tbc.saveConfig();

        return wasRemoved;
    }

    public Collection<Material> getEnabled() {
        return blocksToReplace.keySet();
    }

    public static class ReplaceInfo {

        public final Material material;
        public final int timeout;

        public ReplaceInfo(final Material material, final int timeout) {
            this.material = material;
            this.timeout = timeout;
        }
    }
}
