package com.legendsofesper.wac.loesmithing;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GeneralCreation implements Listener{
    /**
     * Implementation for Ore Heating EventHandler which starts all creation.
     * A player takes a normal Ore item and holds shift and right clicks on
     * a magma block to "heat" it. The condition is shown on the item. The meta
     * data is changed.
     *
     * @param pie the PlayerInteractEvent created when player clicks on block
     * @return    boolean value representing if Event successfully handled
     */
    @EventHandler
    public boolean onOreHeat(PlayerInteractEvent pie) {
        Player player = pie.getPlayer();
        ItemStack item = player.getInventory().getItem(player.getInventory().
            getHeldItemSlot());

        if(player.isSneaking() && pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
           pie.getClickedBlock().getType() == Material.MAGMA && pie.getHand().
               equals(EquipmentSlot.HAND)){

            // handle iron heating
            if(item.getType() == Material.IRON_INGOT && !item.hasItemMeta()){
                // remove old item from inventory
                if(item.getAmount() == 1){
                    player.getInventory().remove(item);
                }else{
                    item.setAmount(item.getAmount() - 1);
                }

                // create the new ingot item and give it meta data to reflect
                //   the change
                ItemStack newIngot =
                    new ItemStack(Material.IRON_INGOT, 1);
                ItemMeta ingotMeta = newIngot.getItemMeta();
                ingotMeta.setLocalizedName("§4[Heated] §fIron Ingot");
                newIngot.setItemMeta(ingotMeta);

                // add item to inventory and play sound clip to signify
                //   completion
                player.getInventory().addItem(newIngot);
                player.playSound(player.getLocation(),
                                 Sound.BLOCK_LAVA_POP, 20, 0);
            }
            // handle Andaryll heating
            else if(item.getType() == Material.NETHER_BRICK_ITEM &&
                    item.hasItemMeta()){
                if(item.getItemMeta().getLocalizedName().equals("Andaryll")){
                    // remove old item from inventory
                    if(item.getAmount() == 1){
                        player.getInventory().remove(item);
                    }else{
                        item.setAmount(item.getAmount() - 1);
                    }

                    // create the new ore item and give it meta data to reflect
                    //   the change
                    ItemStack newOre = new ItemStack(Material.NETHER_BRICK_ITEM,
                                                     1);
                    ItemMeta ingotMeta = newOre.getItemMeta();
                    ingotMeta.setLocalizedName("§4[Heated] §fAndaryll");
                    newOre.setItemMeta(ingotMeta);

                    // add item to inventory and play sound clip to signify completion
                    player.getInventory().addItem(newOre);
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 20, 0);
                }
            }
            // handle Serasyll heating
            else if(item.getType() == Material.REDSTONE && item.hasItemMeta()){
                if(item.getItemMeta().getLocalizedName().equals("Serasyll")){
                    // remove old item from inventory
                    if(item.getAmount() == 1){
                        player.getInventory().remove(item);
                    }else{
                        item.setAmount(item.getAmount() - 1);
                    }

                    // create the new ore item and give it meta data to reflect the change
                    ItemStack newOre = new ItemStack(Material.REDSTONE, 1);
                    ItemMeta ingotMeta = newOre.getItemMeta();
                    ingotMeta.setLocalizedName("§4[Heated] §fSerasyll");
                    newOre.setItemMeta(ingotMeta);

                    // add item to inventory and play sound clip to signify completion
                    player.getInventory().addItem(newOre);
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 20, 0);
                }
            }
            // handle mythril heating
            else if(item.getType() == Material.EMERALD && item.hasItemMeta()){
                if(item.getItemMeta().getLocalizedName().equals("Mythril")){
                    // remove old item from inventory
                    if(item.getAmount() == 1){
                        player.getInventory().remove(item);
                    }else{
                        item.setAmount(item.getAmount() - 1);
                    }

                    // create the new ore item and give it meta data to reflect the change
                    ItemStack newOre = new ItemStack(Material.EMERALD, 1);
                    ItemMeta ingotMeta = newOre.getItemMeta();
                    ingotMeta.setLocalizedName("§4[Heated] §fMythril");
                    newOre.setItemMeta(ingotMeta);

                    // add item to inventory and play sound clip to signify completion
                    player.getInventory().addItem(newOre);
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 20, 0);
                }
            }
            // handle adamantium heating
            else if(item.getType() == Material.DIAMOND && item.hasItemMeta()){
                if(item.getItemMeta().getLocalizedName().equals("Adamantium")){
                    // remove old item from inventory
                    if(item.getAmount() == 1){
                        player.getInventory().remove(item);
                    }else{
                        item.setAmount(item.getAmount() - 1);
                    }

                    // create the new ore item and give it meta data to reflect the change
                    ItemStack newOre = new ItemStack(Material.DIAMOND, 1);
                    ItemMeta ingotMeta = newOre.getItemMeta();
                    ingotMeta.setLocalizedName("§4[Heated] §fAdamantium");
                    newOre.setItemMeta(ingotMeta);

                    // add item to inventory and play sound clip to signify completion
                    player.getInventory().addItem(newOre);
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 20, 0);
                }
            }
        }

        return true;
    }

    @EventHandler
    public boolean onOreShape(PlayerInteractEvent pie){
        Player player = pie.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // make sure player sneak and right clicks on anvil to activate
        if(player.isSneaking() && pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
        pie.getClickedBlock().getType() == Material.ANVIL && pie.getHand().
        equals(EquipmentSlot.HAND) && item.hasItemMeta()){

            // menu for players to choose what to make heated ore into
            Inventory menu = Bukkit.createInventory(player, InventoryType.CHEST, "Choose what to create");

            if(item.getType() == Material.IRON_INGOT &&
               item.getItemMeta().getLocalizedName().
               equals("§4[Heated] §fIron Ingot")){
                //create Iron helmet for use in menu and set position
                ItemStack helmet = new ItemStack(Material.IRON_HELMET);
                menu.setItem(4, helmet);
                // create Iron chest plate and set position in menu
                ItemStack chestPlate = new ItemStack(Material.IRON_CHESTPLATE);
                menu.setItem(13, chestPlate);
                // create Iron legging and set position in menu
                ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
                menu.setItem(22, leggings);
                // create sword and set position in menu
                ItemStack sword = new ItemStack(Material.IRON_SWORD);
                menu.setItem(11, sword);

                player.getOpenInventory().close();
                player.openInventory(menu);
            }
            else if(item.getType() == Material.IRON_INGOT &&
                    item.getItemMeta().getLocalizedName().
                    equals("§4[Heated] §fAndaryll")){
            }
        }

        return true;
    }

    @EventHandler
    public boolean onUnfinishedMenuChoice(InventoryClickEvent ice){
        if(ice.isLeftClick() && ice.getClickedInventory()
           ice.getClickedInventory().getName().equals("Choose what to create")){
            // remove heated ingot from inventory
            ItemStack item = ice.getWhoClicked().
                             getInventory().getItemInMainHand();
            if(item.getAmount() == 1){
                ice.getWhoClicked().getInventory().remove(item);
            }else{
                item.setAmount(item.getAmount() - 1);
            }

            ItemStack unfinishedItem = null;
            ItemMeta unfinishedMeta = null;
            // set unfinished item to handled iron item
            if(item.getItemMeta().getLocalizedName().contains("Iron Ingot")){
                unfinishedItem = new ItemStack(Material.IRON_INGOT);
                unfinishedMeta = unfinishedItem.getItemMeta();
                unfinishedMeta.setLocalizedName("Unfinished Iron ");
            }

            // clicked on slot containing helmet, give unfinished helmet ingot
            if(ice.getSlot() == 4 && !item.getItemMeta().
                    getLocalizedName().contains("Serasyll") &&
                    !item.getItemMeta().
                    getLocalizedName().contains("Adamantium")){
                unfinishedMeta.setLocalizedName(unfinishedMeta.
                    getLocalizedName() + "Helmet");
                unfinishedItem.setItemMeta(unfinishedMeta);
                ice.getWhoClicked().getInventory().addItem(unfinishedItem);
            }
            // clicked on slot containing chest plate, give unfin. chest plate
            else if(ice.getSlot() == 13 && !item.getItemMeta().
                    getLocalizedName().contains("Serasyll") &&
                    !item.getItemMeta().
                    getLocalizedName().contains("Adamantium")){
                unfinishedMeta.setLocalizedName(unfinishedMeta.
                    getLocalizedName() + "Chestplate");
                unfinishedItem.setItemMeta(unfinishedMeta);
                ice.getWhoClicked().getInventory().addItem(unfinishedItem);
            }
            // clicked on slot with leggings, give unfin. leggings
            else if(ice.getSlot() == 22 && !item.getItemMeta().
                    getLocalizedName().contains("Serasyll") &&
                    !item.getItemMeta().
                    getLocalizedName().contains("Adamantium")){
                unfinishedMeta.setLocalizedName(unfinishedMeta.
                    getLocalizedName() + "Leggings");
                unfinishedItem.setItemMeta(unfinishedMeta);
                ice.getWhoClicked().getInventory().addItem(unfinishedItem);
            }
            // clicked on slot with sword, give unfin. sword
            else if(ice.getSlot() == 11 && !item.getItemMeta().
                    getLocalizedName().contains("Andaryll") &&
                    !item.getItemMeta().getLocalizedName().contains("Mythril")){
                unfinishedMeta.setLocalizedName(unfinishedMeta.
                    getLocalizedName() + "Sword");
                unfinishedItem.setItemMeta(unfinishedMeta);
                ice.getWhoClicked().getInventory().addItem(unfinishedItem);
            }

            ice.getWhoClicked().closeInventory();
        }

        return true;
    }
}
