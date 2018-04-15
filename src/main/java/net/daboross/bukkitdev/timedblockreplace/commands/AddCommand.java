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
public class AddCommand extends SubCommand {

    private final TimedBlockReplacePlugin tbr;

    public AddCommand(TimedBlockReplacePlugin tbr) {
        super("add", true, "timedblockreplace.add", "Sets the FromBlock to change to the ToBlock after TimeTillChange seconds");
        addArgumentNames("FromBlockID", "ToBlock", "TimeTillChange");
        addCommandFilter(new ArgumentFilter(ArgumentFilter.ArgumentCondition.GREATER_THAN, 2,
                ColorList.ERR + "Please sepcify a FromBlock, ToBlock and TimeTillReplace!", true));
        addCommandFilter(new ArgumentFilter(ArgumentFilter.ArgumentCondition.LESS_THAN, 4, "Too many arguments.", true));
        this.tbr = tbr;
    }

    @Override
    public void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        int timeTillReplace;

        Material fromBlockMaterial = Utils.attemptToGetMaterialName(sender, this, baseCommandLabel, subCommandLabel, subCommandArgs[0]);
        if (fromBlockMaterial == null) {
            return;
        }
        Material toBlockMaterial = Utils.attemptToGetMaterialName(sender, this, baseCommandLabel, subCommandLabel, subCommandArgs[1]);
        if (toBlockMaterial == null) {
            return;
        }

        try {
            timeTillReplace = Integer.parseInt(subCommandArgs[2]);
        } catch (NumberFormatException nfe) {
            sender.sendMessage(ColorList.ERR_ARGS + subCommandArgs[2] + ColorList.ERR + " is not an integer! Please use an integer in seconds for the TimeTillReplace!");
            sender.sendMessage(getHelpMessage(baseCommandLabel, subCommandLabel));
            return;
        }
        if (timeTillReplace < 0) {
            sender.sendMessage(ColorList.ERR_ARGS + timeTillReplace + ColorList.ERR + " is not a positive integer! I am sorry but I can't modify the past.");
            sender.sendMessage(getHelpMessage(baseCommandLabel, subCommandLabel));
            return;
        }
        sender.sendMessage(ColorList.DATA + fromBlockMaterial + ColorList.REG + " will now be changed to " + ColorList.DATA + toBlockMaterial + " " + timeTillReplace + ColorList.REG + " seconds after it is placed!");
        sender.sendMessage(ColorList.REG + "To remove this record, use " + ColorList.CMD + "/" + baseCommandLabel + ColorList.SUBCMD + " remove " + ColorList.ARGS + fromBlockMaterial);
        tbr.getLogger().log(Level.INFO, "Adding Record: fromBlock={0} toBlock={1} timeTillReplace={2}", new Object[]{fromBlockMaterial, toBlockMaterial, timeTillReplace});
        tbr.addConfigBlock(fromBlockMaterial, toBlockMaterial, timeTillReplace);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command baseCommand, String baseCommandLabel, SubCommand subCommand, String subCommandLabel, String[] subCommandArgs) {
        if (subCommandArgs.length > 0 && subCommandArgs.length < 3) {
            return Utils.autocompleteMaterialName(subCommandArgs[subCommandArgs.length - 1]);
        } else {
            return new ArrayList<String>();
        }
    }
}
