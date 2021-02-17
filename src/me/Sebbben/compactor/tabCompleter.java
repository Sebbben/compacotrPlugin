package me.Sebbben.compactor;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class tabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> mats;
        if (args.length == 1) mats = Arrays.asList("add");
        else if (args.length == 2) mats = Arrays.asList("4","9");
        else if (args.length == 3 || args.length == 4) {
            mats = new ArrayList<>();
            for (Material mat : Material.values()) {
                String matString = mat.toString().toLowerCase();
                if(matString.startsWith(args[args.length-1])) mats.add(matString);
            }
        } else
            return null;
        return mats;
    }
}
