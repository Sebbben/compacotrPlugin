package me.Sebbben.compactor;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;

public class Compactor implements Listener {

    @EventHandler(
            priority = EventPriority.NORMAL,
            ignoreCancelled = true
    )
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity player = event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        if (inv != null && event.getClick().equals(ClickType.MIDDLE)) {
            InventoryType invType = inv.getType();
            if (this.isSupportedInventory(invType)) {
                ItemStack[] items = inv.getContents();
                items = this.compactItems(items);
                inv.setContents(items);
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_COMPOSTER_FILL, 0.75F, 1.0F);
                event.setCancelled(true);
            }
        }

    }

    private ItemStack[] compactItems(ItemStack[] items) {
        ItemStack[] newItems = new ItemStack[items.length];
        int lastAvailableSpot = items.length - 1;
        int firstAvailableSpot = 0;
        //loop trough inventory
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                ItemStack item = items[i];
                // if the item can be compacted

                if (this.isCompatibleItem(item)) {
                    int counter = item.getAmount();
                    for (int j = i + 1; j < items.length - 1; j++) {
                        if (items[j] != null) {
                            if (item.getType() == items[j].getType()) {
                                counter += items[j].getAmount();
                                items[j] = null;
                            }
                        }
                    }
                    if (Main.getCompMatsNine().containsKey(item.getType())) {
                        int itemsInProduct = 9;
                        int productsToAdd = counter / itemsInProduct;
                        int itemsLeft = counter % itemsInProduct;
                        while (productsToAdd >= 64) {
                            newItems[firstAvailableSpot] = new ItemStack(Main.getCompMatsNine().get(item.getType()), 64);
                            productsToAdd -= 64;
                            firstAvailableSpot++;
                        }
                        if (productsToAdd > 0) {
                            newItems[firstAvailableSpot] = new ItemStack(Main.getCompMatsNine().get(item.getType()), productsToAdd);
                            firstAvailableSpot++;
                        }
                        if (itemsLeft > 0) {
                            newItems[firstAvailableSpot] = new ItemStack(item.getType(), itemsLeft);
                            firstAvailableSpot++;
                        }

                    }
                    else if (Main.getCompMatsFour().containsKey(item.getType())) {
                        int itemsInProduct = 4;
                        int productsToAdd = counter / itemsInProduct;
                        int itemsLeft = counter % itemsInProduct;
                        while (productsToAdd >= 64) {
                            newItems[firstAvailableSpot] = new ItemStack(Main.getCompMatsFour().get(item.getType()), 64);
                            productsToAdd -= 64;
                            firstAvailableSpot++;
                        }
                        if (productsToAdd > 0) {
                            newItems[firstAvailableSpot] = new ItemStack(Main.getCompMatsFour().get(item.getType()), productsToAdd);
                            firstAvailableSpot++;
                        }
                        if (itemsLeft > 0) {
                            newItems[firstAvailableSpot] = new ItemStack(item.getType(), itemsLeft);
                            firstAvailableSpot++;
                        }
                    }
                } else { // if it is a normal item / not a compatible item
                    newItems[lastAvailableSpot] = item;
                    lastAvailableSpot--;
                    items[i] = null;
                }
            }
        }

        return newItems;
    }

    private boolean isSupportedInventory(InventoryType invType) {
        return Arrays.asList(InventoryType.PLAYER, InventoryType.CHEST, InventoryType.BARREL, InventoryType.SHULKER_BOX).contains(invType);
    }


    private boolean isCompatibleItem(ItemStack item) {
        Map<Material, Material> compMatsNine = Main.getCompMatsNine();
        Map<Material, Material> compMatsFour = Main.getCompMatsNine();
        return compMatsNine.containsKey(item.getType()) || compMatsFour.containsKey(item.getType());
    }
}

