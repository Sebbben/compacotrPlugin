package me.Sebbben.compactor;

import org.bukkit.Material;
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

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new Compactor(), this);

        this.loadSupportedMaterials();

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
