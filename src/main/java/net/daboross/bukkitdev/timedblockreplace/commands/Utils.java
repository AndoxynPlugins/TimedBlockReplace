package net.daboross.bukkitdev.timedblockreplace.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.timedblockreplace.TimedBlockReplacePlugin;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class Utils {

    public static final List<String> ALL_MATERIAL_NAMES;

    static {
        Material[] materials = Material.values();
        List<String> names = new ArrayList<String>(materials.length);
        for (Material material : materials) {
            names.add(material.toString());
        }
        ALL_MATERIAL_NAMES = Collections.unmodifiableList(names);
    }

    public static Material attemptToGetMaterialName(CommandSender sender, SubCommand cmd, String baseCommandLabel, String subCommandLabel, String argument) {
        Material material = null;
        try {
            material = TimedBlockReplacePlugin.parseName(argument);
        } catch (TimedBlockReplacePlugin.NotAMaterial ex) {
            sender.sendMessage(ColorList.ERR_ARGS + argument + ColorList.ERR + " is not a material name! Please use a material name!");
            sender.sendMessage(cmd.getHelpMessage(baseCommandLabel, subCommandLabel));
        }
        return material;
    }

    public static List<String> autocompleteMaterialName(String argument) {
        String toComplete = argument.toUpperCase();
        List<String> possibleCompletions = new ArrayList<String>();
        for (String name : ALL_MATERIAL_NAMES) {
            if (name.startsWith(toComplete)) {
                possibleCompletions.add(name);
            }
        }
        return possibleCompletions;
    }
}
