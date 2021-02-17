package me.Sebbben.compactor;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class compactorCommand implements CommandExecutor {
    @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length >= 1) {
            switch (args[0]) {
                // /compactor add 9 gold_nugget gold_ingot
                case "add":
                    if(args[1] == "9") {
                        if(Main.matsToAddNine == null) Main.matsToAddNine = new HashMap<>();
                        Main.matsToAddNine.put(Material.matchMaterial(args[3]).toString(), Material.matchMaterial(args[4]).toString());
                        for (Map.Entry e : Main.matsToAddNine.entrySet()) {
                            sender.sendMessage(e.getKey().toString());
                        }
                    } else if(args[1] == "4") {
                        if(Main.matsToAddFour == null) Main.matsToAddFour = new HashMap<>();
                        Main.matsToAddFour.put(Material.matchMaterial(args[3]).toString(), Material.matchMaterial(args[4]).toString());
                    }


            }
        }
        return true;
    }
}
