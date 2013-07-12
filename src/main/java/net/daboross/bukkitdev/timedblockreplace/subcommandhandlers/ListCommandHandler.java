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
