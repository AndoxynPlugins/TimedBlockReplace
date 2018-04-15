/*
 * Copyright (C) 2013-2014 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.timedblockreplace;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author daboross
 */
public class TimedBlockReplacePlugin extends JavaPlugin {

    public static final String CONFIG_FROMBLOCK_LIST = "from-blocks";
    public static final String CONFIG_TO_BLOCK_PREFIX = "to-blocks.";
    public static final String CONFIG_TIMES_PREFIX = "block-times.";
    public Configuration configuration;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        try {
            configuration = new Configuration(this);
        } catch (NotAMaterial ex) {
            getLogger().log(Level.WARNING, "Failed to load configuration", ex);
            throw new RuntimeException(ex);
        }
        BlockPlaceListener bpl = new BlockPlaceListener(this);
        Bukkit.getPluginManager().registerEvents(bpl, this);
        PluginCommand tbr = getCommand("tbr");
        if (tbr != null) {
            new ConfigChangeCommandHandler(this).registerCommand(tbr);
        }
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public void addConfigBlock(Material fromBlock, Material toBlock, int timeTillReplace) {
        configuration.addReplace(fromBlock, toBlock, timeTillReplace);
    }

    public boolean removeConfigBlock(Material fromBlock) {
        return configuration.removeReplace(fromBlock);
    }

    public static Material parseName(String input) throws NotAMaterial {
        try {
            return Material.valueOf(input.toUpperCase().trim().replace(' ', '_'));
        } catch (IllegalArgumentException ex) {
            int num;
            try {
                num = Integer.parseInt(input);
            } catch (NumberFormatException ignored) {
                throw new NotAMaterial("Failed to parse configuration! The string \""
                        + input + "\" is not a valid material name. To see all valid"
                        + " material names, go to https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html.");
            }
            try {
                @SuppressWarnings("deprecation")
                Material value = Material.getMaterial(num);
                if (value == null) {
                    throw new NotAMaterial("Failed to parse configuration! The integer \""
                            + num + "\" is not a valid material integer.");
                }
                return value;
            } catch (NoSuchMethodError er) {
                throw new NotAMaterial("Failed to parse configuration! The string \""
                        + input + "\" is not a valid material name. To see all valid"
                        + " material names, go to https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html.");
            }
        }
    }

    public static class NotAMaterial extends Exception {

        public NotAMaterial(String msg) {
            super(msg);
        }
    }
}
