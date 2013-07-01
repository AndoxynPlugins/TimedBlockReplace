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
public class AddCommandHandler implements SubCommandHandler {

    private final TimedBlockReplace tbr;

    public AddCommandHandler(TimedBlockReplace tbr) {
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
        sender.sendMessage(ColorList.DATA + fromBlockID + ColorList.REG + " will now be changed to " + ColorList.DATA + toBlockID + timeTillReplace + ColorList.REG + " seconds after it is placed!");
        sender.sendMessage(ColorList.REG + "To remove this record, use " + ColorList.CMD + "/" + baseCommandLabel + ColorList.SUBCMD + " remove " + ColorList.ARGS + fromBlockID);
        sender.sendMessage(ColorList.DATA + fromBlockID + ColorList.REG + "'s name is: " + Material.getMaterial(fromBlockID));
        sender.sendMessage(ColorList.DATA + toBlockID + ColorList.REG + "'s name is: " + Material.getMaterial(toBlockID));
        tbr.getLogger().log(Level.INFO, "Adding Record: fromBlockID={0} toBlockID={1} timeTillReplace={2}", new Object[]{fromBlockID, toBlockID, timeTillReplace});
        tbr.addConfigBlock(fromBlockID, toBlockID, timeTillReplace);
    }
}
