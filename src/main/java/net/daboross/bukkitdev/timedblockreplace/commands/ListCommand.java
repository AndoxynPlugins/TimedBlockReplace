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

import java.util.Collection;
import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.timedblockreplace.Configuration;
import net.daboross.bukkitdev.timedblockreplace.TimedBlockReplacePlugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author daboross
 */
public class ListCommand extends SubCommand {

    private final TimedBlockReplacePlugin tbr;

    public ListCommand(TimedBlockReplacePlugin tbr) {
        super("list", true, "timedblockreplace.list", "Lists all currently active blocks set to change");
        this.tbr = tbr;
    }

    @Override
    public void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        Collection<Material> enabledBlocks = tbr.configuration.getEnabled();
        if (enabledBlocks.isEmpty()) {
            sender.sendMessage(ColorList.REG + "There are no records. Use " + ColorList.CMD + "/" + baseCommandLabel + ColorList.SUBCMD + " add" + ColorList.REG + " to add one.");
            return;
        }
        sender.sendMessage(ColorList.TOP_SEPERATOR + " -- " + ColorList.TOP + "Block Records" + ColorList.TOP_SEPERATOR + " --");
        sender.sendMessage(ColorList.TOP + " FromBlock" + ColorList.TOP_SEPERATOR + " -- " + ColorList.TOP + "ToBlock" + ColorList.TOP_SEPERATOR + " -- " + ColorList.TOP + "Time");
        for (Material material : enabledBlocks) {
            Configuration.ReplaceInfo info = tbr.configuration.replacing(material);

            sender.sendMessage(ColorList.REG + " " + material
                    + ColorList.TOP_SEPERATOR + " -- "
                    + ColorList.REG + info.material
                    + ColorList.TOP_SEPERATOR + " -- "
                    + ColorList.REG + info.timeout + "s");
        }
    }
}
