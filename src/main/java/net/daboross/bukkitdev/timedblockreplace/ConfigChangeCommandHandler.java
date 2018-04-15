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
import net.daboross.bukkitdev.timedblockreplace.commands.AddCommand;
import net.daboross.bukkitdev.timedblockreplace.commands.ListCommand;
import net.daboross.bukkitdev.timedblockreplace.commands.RemoveCommand;
import org.bukkit.command.PluginCommand;

/**
 *
 * @author daboross
 */
public class ConfigChangeCommandHandler {

    private final CommandExecutorBase commandExecutorBase;

    public ConfigChangeCommandHandler(TimedBlockReplacePlugin main) {
        commandExecutorBase = new CommandExecutorBase("timedblockreplace.config");
        commandExecutorBase.addSubCommand(new AddCommand(main));
        commandExecutorBase.addSubCommand(new RemoveCommand(main));
        commandExecutorBase.addSubCommand(new ListCommand(main));
    }

    protected void registerCommand(PluginCommand command) {
        command.setExecutor(commandExecutorBase);
    }
}
