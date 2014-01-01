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
package net.daboross.bukkitdev.timedblockreplace.subcommandhandlers;

import java.util.logging.Level;
import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.commandexecutorbase.SubCommandHandler;
import net.daboross.bukkitdev.timedblockreplace.TimedBlockReplacePlugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public class RemoveCommandHandler implements SubCommandHandler {

    private final TimedBlockReplacePlugin tbr;

    public RemoveCommandHandler(TimedBlockReplacePlugin tbr) {
        this.tbr = tbr;
    }

    @Override
    public void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, SubCommand subCommand, String subCommandLabel, String[] subCommandArgs) {
        if (subCommandArgs.length < 1) {
            sender.sendMessage(ColorList.ERR + "Please specify a block record to remove!");
            sender.sendMessage(subCommand.getHelpMessage(baseCommandLabel, subCommandLabel));
            return;
        }
        int fromBlockID;
        try {
            fromBlockID = Integer.parseInt(subCommandArgs[0]);
        } catch (NumberFormatException nfe) {
            sender.sendMessage(ColorList.ERR_ARGS + subCommandArgs[0] + ColorList.ERR + " is not an integer! Please use the block ID, not the block name!");
            sender.sendMessage(subCommand.getHelpMessage(baseCommandLabel, subCommandLabel));
            return;
        }
        if (fromBlockID <= 0) {
            sender.sendMessage(ColorList.ERR_ARGS + fromBlockID + ColorList.ERR + " is not a non-0 positive integer! Please use the block ID, not the block name!");
            sender.sendMessage(subCommand.getHelpMessage(baseCommandLabel, subCommandLabel));
            return;
        }
        tbr.getLogger().log(Level.INFO, "Removing Record: fromBlockID={0}", fromBlockID);
        if (tbr.removeConfigBlock(fromBlockID)) {
            sender.sendMessage(ColorList.REG + "Record for " + ColorList.DATA + fromBlockID + ColorList.REG + " has been removed!");
        } else {
            sender.sendMessage(ColorList.REG + "Record for " + ColorList.DATA + fromBlockID + ColorList.REG + " didn't exist!");
        }
        sender.sendMessage(ColorList.DATA + fromBlockID + ColorList.REG + "'s name is: " + Material.getMaterial(fromBlockID));
    }
}
