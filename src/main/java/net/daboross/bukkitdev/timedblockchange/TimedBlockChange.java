/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.timedblockchange;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author daboross
 */
public class TimedBlockChange extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
    }
}
