/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
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

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author daboross
 */
public class TimedBlockReplace extends JavaPlugin {

    public static final String CONFIG_FROMBLOCK_LIST = "from-blocks";
    public static final String CONFIG_TO_BLOCK_PREFIX = "to-blocks.";
    public static final String CONFIG_TIMES_PREFIX = "block-times.";
    private BlockPlaceListener bpl;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        bpl = new BlockPlaceListener(this);
        Bukkit.getPluginManager().registerEvents(bpl, this);
        PluginCommand tbr = getCommand("tbr");
        if (tbr != null) {
            new ConfigChangeCommandHandler(this).registerCommand(tbr);
        } else {
            getLogger().warning("Command not found! Is another plugin using /tbr? Command not registered! It won't work now! Already configured block-replace records will still work though! You just won't be able to configure the plugin!");
        }
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public void addConfigBlock(int fromBlockID, int toBlockID, int timeTillReplace) {
        FileConfiguration config = getConfig();
        List<Integer> list = config.getIntegerList(CONFIG_FROMBLOCK_LIST);
        config.set(CONFIG_TO_BLOCK_PREFIX + fromBlockID, toBlockID);
        config.set(CONFIG_TIMES_PREFIX + fromBlockID, timeTillReplace);
        if (!list.contains(fromBlockID)) {
            list.add(fromBlockID);
            config.set(CONFIG_FROMBLOCK_LIST, list);
            saveConfig();
            bpl.reloadConfig();
        }
    }

    public boolean removeConfigBlock(int fromBlockID) {
        FileConfiguration config = getConfig();
        List<Integer> list = config.getIntegerList(CONFIG_FROMBLOCK_LIST);
        config.set(CONFIG_TO_BLOCK_PREFIX + fromBlockID, null);
        config.set(CONFIG_TIMES_PREFIX + fromBlockID, null);
        if (list.contains(fromBlockID)) {
            list.remove((Integer) fromBlockID);
            config.set(CONFIG_FROMBLOCK_LIST, list);
            saveConfig();
            bpl.reloadConfig();
            return true;
        }
        return false;
    }
}
