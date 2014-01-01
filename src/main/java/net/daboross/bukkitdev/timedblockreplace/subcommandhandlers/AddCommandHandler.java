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
public class AddCommandHandler implements SubCommandHandler {

    private final TimedBlockReplacePlugin tbr;

    public AddCommandHandler(TimedBlockReplacePlugin tbr) {
        this.tbr = tbr;
    }

    @Override
    public void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, SubCommand subCommand, String subCommandLabel, String[] subCommandArgs) {
        if (subCommandArgs.length < 3) {
            sender.sendMessage(ColorList.ERR + "Please sepcify a FromBlock, ToBlock and TimeTillReplace!");
            sender.sendMessage(subCommand.getHelpMessage(baseCommandLabel, subCommandLabel));
            return;
        }
        int fromBlockID, toBlockID, timeTillReplace;
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
        try {
            toBlockID = Integer.parseInt(subCommandArgs[1]);
        } catch (NumberFormatException nfe) {
            sender.sendMessage(ColorList.ERR_ARGS + subCommandArgs[1] + ColorList.ERR + " is not an integer! Please use the block ID, not the block name!");
            sender.sendMessage(subCommand.getHelpMessage(baseCommandLabel, subCommandLabel));
            return;
        }
        if (toBlockID < 0) {
            sender.sendMessage(ColorList.ERR_ARGS + toBlockID + ColorList.ERR + " is not a positive integer! Block IDs are not negative!");
            sender.sendMessage(subCommand.getHelpMessage(baseCommandLabel, subCommandLabel));
            return;
        }
        try {
            timeTillReplace = Integer.parseInt(subCommandArgs[2]);
        } catch (NumberFormatException nfe) {
            sender.sendMessage(ColorList.ERR_ARGS + subCommandArgs[2] + ColorList.ERR + " is not an integer! Please use an integer in seconds for the TimeTillReplace!");
            sender.sendMessage(subCommand.getHelpMessage(baseCommandLabel, subCommandLabel));
            return;
        }
        if (timeTillReplace < 0) {
            sender.sendMessage(ColorList.ERR_ARGS + timeTillReplace + ColorList.ERR + " is not a positive integer! I am sorry but I can't modify the past.");
            sender.sendMessage(subCommand.getHelpMessage(baseCommandLabel, subCommandLabel));
            return;
        }
        sender.sendMessage(ColorList.DATA + fromBlockID + ColorList.REG + " will now be changed to " + ColorList.DATA + toBlockID + " " + timeTillReplace + ColorList.REG + " seconds after it is placed!");
        sender.sendMessage(ColorList.REG + "To remove this record, use " + ColorList.CMD + "/" + baseCommandLabel + ColorList.SUBCMD + " remove " + ColorList.ARGS + fromBlockID);
        sender.sendMessage(ColorList.DATA + fromBlockID + ColorList.REG + "'s name is: " + Material.getMaterial(fromBlockID));
        sender.sendMessage(ColorList.DATA + toBlockID + ColorList.REG + "'s name is: " + Material.getMaterial(toBlockID));
        tbr.getLogger().log(Level.INFO, "Adding Record: fromBlockID={0} toBlockID={1} timeTillReplace={2}", new Object[]{fromBlockID, toBlockID, timeTillReplace});
        tbr.addConfigBlock(fromBlockID, toBlockID, timeTillReplace);
    }
}
