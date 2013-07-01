/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.timedblockreplace.subcommandhandlers;

import java.util.logging.Level;
import net.daboross.bukkitdev.timedblockreplace.TimedBlockReplace;
import net.daboross.bukkitdev.timedblockreplace.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.timedblockreplace.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.timedblockreplace.commandexecutorbase.SubCommandHandler;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public class RemoveCommandHandler implements SubCommandHandler {

    private final TimedBlockReplace tbr;

    public RemoveCommandHandler(TimedBlockReplace tbr) {
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
