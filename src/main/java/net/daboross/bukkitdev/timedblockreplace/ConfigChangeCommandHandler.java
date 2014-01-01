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
package net.daboross.bukkitdev.timedblockreplace;

import net.daboross.bukkitdev.commandexecutorbase.CommandExecutorBase;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.timedblockreplace.subcommandhandlers.AddCommandHandler;
import net.daboross.bukkitdev.timedblockreplace.subcommandhandlers.ListCommandHandler;
import net.daboross.bukkitdev.timedblockreplace.subcommandhandlers.RemoveCommandHandler;
import org.bukkit.command.PluginCommand;

/**
 *
 * @author daboross
 */
public class ConfigChangeCommandHandler {

    private final CommandExecutorBase commandExecutorBase;

    public ConfigChangeCommandHandler(TimedBlockReplacePlugin main) {
        commandExecutorBase = new CommandExecutorBase("timedblockreplace.config");
        SubCommand addCommand = new SubCommand("add", true, "timedblockreplace.add", new String[]{"FromBlockID", "ToBlock", "TimeTillChange"},
                "Sets the FromBlock to change to the ToBlock after TimeTillChange seconds", new AddCommandHandler(main));
        SubCommand removeCommand = new SubCommand("remove", true, "timedblockreplace.remove",
                new String[]{"FromBlockID"}, "Removes a record added with `add`", new RemoveCommandHandler(main));
        SubCommand listCommand = new SubCommand("list", true, "timedblockreplace.list",
                "Lists all currently active blocks set to change", new ListCommandHandler(main));
        commandExecutorBase.addSubCommand(addCommand);
        commandExecutorBase.addSubCommand(removeCommand);
        commandExecutorBase.addSubCommand(listCommand);
    }

    protected void registerCommand(PluginCommand command) {
        command.setExecutor(commandExecutorBase);
    }
}
