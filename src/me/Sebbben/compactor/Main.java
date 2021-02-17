package me.Sebbben.compactor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {
    private static Map<Material, Material> needsNineToCraft;
    private static Map<Material, Material> needsFourToCraft;
    public static Map<Material, Material> getCompMatsNine() {
        return needsNineToCraft;
    }
    public static Map<Material, Material> getCompMatsFour() {
        return needsFourToCraft;
    }
    public static Map<String, String> matsToAddNine;
    public static Map<String, String> matsToAddFour;


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new Compactor(), this);
        this.getCommand("compactor").setExecutor(new compactorCommand());
        this.getCommand("compactor").setTabCompleter(new tabCompleter());
        this.loadSupportedMaterials();
    }

    @Override
    public void onDisable() {
        if(matsToAddNine != null) {
            for (Map.Entry entry : matsToAddNine.entrySet()) {
                this.getConfig().set("materials.needs_nine_to_craft." +  entry.getKey(), "entry.getValue()");
            }
            this.saveConfig();

        }
        if(matsToAddFour != null) {
            for (Map.Entry entry : matsToAddFour.entrySet()) {
                this.getConfig().set("materials.needs_four_to_craft." + entry.getKey(), entry.getValue());
            }
            this.saveDefaultConfig();
        }
    }

    private void loadSupportedMaterials() {
        if(this.getConfig().get("materials") != null) {
            needsNineToCraft = new HashMap<>();
            needsFourToCraft = new HashMap<>();

            this.getConfig()
                    .getConfigurationSection("materials.needs_nine_to_craft")
                    .getKeys(false)
                    .forEach(mat -> needsNineToCraft.put(Material.matchMaterial(mat), Material.matchMaterial((String) this.getConfig().get("materials.needs_nine_to_craft." + mat))));
            this.getConfig()
                    .getConfigurationSection("materials.needs_four_to_craft")
                    .getKeys(false)
                    .forEach(mat -> needsFourToCraft.put(Material.matchMaterial(mat), Material.matchMaterial((String) this.getConfig().get("materials.needs_four_to_craft." + mat))));

        }
    }

}
