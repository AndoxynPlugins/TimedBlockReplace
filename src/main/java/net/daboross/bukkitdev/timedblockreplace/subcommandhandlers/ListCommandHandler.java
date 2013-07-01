/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.timedblockreplace.subcommandhandlers;

import java.util.List;
import net.daboross.bukkitdev.timedblockreplace.TimedBlockReplace;
import net.daboross.bukkitdev.timedblockreplace.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.timedblockreplace.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.timedblockreplace.commandexecutorbase.SubCommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public class ListCommandHandler implements SubCommandHandler {

    private final TimedBlockReplace tbr;

    public ListCommandHandler(TimedBlockReplace tbr) {
        this.tbr = tbr;
    }

    @Override
    public void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, SubCommand subCommand, String subCommandLabel, String[] subCommandArgs) {
        List<Integer> enabledBlocks = tbr.getConfig().getIntegerList(TimedBlockReplace.CONFIG_FROMBLOCK_LIST);
        if (enabledBlocks.isEmpty()) {
            sender.sendMessage(ColorList.REG + "There are no records. Use " + ColorList.CMD + "/" + baseCommandLabel + ColorList.SUBCMD + " add" + ColorList.REG + " to add one.");
            return;
        }
        sender.sendMessage(ColorList.TOP_SEPERATOR + " -- " + ColorList.TOP + "Block Records" + ColorList.TOP_SEPERATOR + " --");
        sender.sendMessage(ColorList.TOP + " FromBlock" + ColorList.TOP_SEPERATOR + " -- " + ColorList.TOP + "ToBlock" + ColorList.TOP_SEPERATOR + " -- " + ColorList.TOP + "Time");
        for (Integer i : enabledBlocks) {
            int toBlock = tbr.getConfig().getInt(TimedBlockReplace.CONFIG_TO_BLOCK_PREFIX + i);
            int time = tbr.getConfig().getInt(TimedBlockReplace.CONFIG_TIMES_PREFIX + i);
            sender.sendMessage(ColorList.REG + " " + i + ColorList.TOP_SEPERATOR + " -- " + ColorList.REG + toBlock + ColorList.TOP_SEPERATOR + " -- " + ColorList.REG + "" + ColorList.TOP_SEPERATOR + " -- " + ColorList.REG + time + "s");
        }
    }
}
