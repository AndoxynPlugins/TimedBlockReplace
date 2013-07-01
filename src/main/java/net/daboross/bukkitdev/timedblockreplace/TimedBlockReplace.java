/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.timedblockreplace;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author daboross
 */
public class TimedBlockReplace extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        PluginCommand tbr = getCommand("tbr");
        if (tbr != null) {
            new ConfigChangeCommandHandler(this).registerCommand(tbr);
        } else {
            getLogger().warning("Command not found! Is another plugin using /tbr?");
        }
    }
}
