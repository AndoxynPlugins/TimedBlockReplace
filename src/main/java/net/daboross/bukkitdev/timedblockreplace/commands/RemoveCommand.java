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
package net.daboross.bukkitdev.timedblockreplace.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.commandexecutorbase.filters.ArgumentFilter;
import net.daboross.bukkitdev.timedblockreplace.TimedBlockReplacePlugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author daboross
 */
public class RemoveCommand extends SubCommand {

    private final TimedBlockReplacePlugin tbr;

    public RemoveCommand(TimedBlockReplacePlugin tbr) {
        super("remove", true, "timedblockreplace.remove", "Removes a record added with `add`");
        addArgumentNames("FromBlockID");
        addCommandFilter(new ArgumentFilter(ArgumentFilter.ArgumentCondition.GREATER_THAN, 0,
                ColorList.ERR + "Please specify a block record to remove!", true));
        addCommandFilter(new ArgumentFilter(ArgumentFilter.ArgumentCondition.LESS_THAN, 2, "Too many arguments.", true));
        this.tbr = tbr;
    }

    @Override
    public void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        Material fromBlockMaterial = Utils.attemptToGetMaterialName(sender, this, baseCommandLabel, subCommandLabel, subCommandArgs[0]);
        if (fromBlockMaterial == null) {
            return;
        }
        tbr.getLogger().log(Level.INFO, "Removing Record: fromBlock={0}", fromBlockMaterial);
        if (tbr.removeConfigBlock(fromBlockMaterial)) {
            sender.sendMessage(ColorList.REG + "Record for " + ColorList.DATA + fromBlockMaterial + ColorList.REG + " has been removed!");
        } else {
            sender.sendMessage(ColorList.REG + "Record for " + ColorList.DATA + fromBlockMaterial + ColorList.REG + " didn't exist!");
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command baseCommand, String baseCommandLabel, SubCommand subCommand, String subCommandLabel, String[] subCommandArgs) {
        if (subCommandArgs.length == 1) {
            return Utils.autocompleteMaterialName(subCommandArgs[subCommandArgs.length - 1]);
        } else {
            return new ArrayList<String>();
        }
    }
}
