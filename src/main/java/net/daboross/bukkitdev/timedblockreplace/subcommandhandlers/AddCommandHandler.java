/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.timedblockreplace.subcommandhandlers;

import net.daboross.bukkitdev.timedblockreplace.TimedBlockReplace;
import net.daboross.bukkitdev.timedblockreplace.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.timedblockreplace.commandexecutorbase.SubCommandHandler;
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
    }
}
