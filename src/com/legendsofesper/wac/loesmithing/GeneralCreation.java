//****************************************************
//
// File:    generalCreation.java
// Package: com.legendsofesper.wac.loesmithing
// Class:   GeneralCreation
//
// Author:  William Comly <williamacomly@gmail.com>
//
//****************************************************

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

/**
 * Event handlers and helper moethods that contribute to starting the smithing
 * process for all tools/arms/armor covered by plugin
 *
 * TODO: clean up and refractor code to cut down on unnecessary copies of blocks
 *
 * @author  William Comly
 * @version 4-Apr-2018
 */
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

                    // add item to inventory and play sound clip to signify
                    //   completion
                    player.getInventory().addItem(newOre);
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP,
                                     20, 0);
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

                    // create the new ore item and give it meta data to reflect
                    //   the change
                    ItemStack newOre = new ItemStack(Material.REDSTONE, 1);
                    ItemMeta ingotMeta = newOre.getItemMeta();
                    ingotMeta.setLocalizedName("§4[Heated] §fSerasyll");
                    newOre.setItemMeta(ingotMeta);

                    // add item to inventory and play sound clip to signify
                    //   completion
                    player.getInventory().addItem(newOre);
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP,
                                     20, 0);
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

                    // create the new ore item and give it meta data to reflect
                    //   the change
                    ItemStack newOre = new ItemStack(Material.EMERALD, 1);
                    ItemMeta ingotMeta = newOre.getItemMeta();
                    ingotMeta.setLocalizedName("§4[Heated] §fMythril");
                    newOre.setItemMeta(ingotMeta);

                    // add item to inventory and play sound clip to signify
                    //   completion
                    player.getInventory().addItem(newOre);
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP,
                                     20, 0);
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

                    // create the new ore item and give it meta data to reflect
                    //   the change
                    ItemStack newOre = new ItemStack(Material.DIAMOND, 1);
                    ItemMeta ingotMeta = newOre.getItemMeta();
                    ingotMeta.setLocalizedName("§4[Heated] §fAdamantium");
                    newOre.setItemMeta(ingotMeta);

                    // add item to inventory and play sound clip to signify
                    //   completion
                    player.getInventory().addItem(newOre);
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP,
                                     20, 0);
                }
            }
        }

        return true;
    }

    /**
     * Creates the menu for ore shaping and displays it when shift-left-clicking
     * on any anvil with a special ore/ingot in main hand. Allows for choosing
     * what to make in smithing plugin.
     *
     * @param pie PlayerInteractEvent event passes through when event listened.
     *            Allows for access of player, item, block clicked, of event.
     * @return boolean whether or not event successfully handled
     */
    @EventHandler
    public boolean onOreShape(PlayerInteractEvent pie){
        Player player = pie.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // make sure player sneak and right clicks on anvil to activate
        if(player.isSneaking() && pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
        pie.getClickedBlock().getType() == Material.ANVIL && pie.getHand().
        equals(EquipmentSlot.HAND) && item.hasItemMeta()){

            // menu for players to choose what to make heated ore into
            Inventory menu = Bukkit.createInventory(player, InventoryType.CHEST,
                                                    "Choose what to create");

            // for iron items
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

                // close auto anvil inventory and open configured menu
                player.getOpenInventory().close();
                player.openInventory(menu);
            }
            // for andaryll items
            else if(item.getType() == Material.NETHER_BRICK_ITEM &&
                    item.getItemMeta().getLocalizedName().
                    equals("§4[Heated] §fAndaryll")){
                // create Andaryll sword and set position in menu
                ItemStack sword = new ItemStack(Material.GOLD_SWORD);
                ItemMeta swordMeta = sword.getItemMeta();
                swordMeta.setLocalizedName("Andaryll Sword");
                sword.setItemMeta(swordMeta);
                menu.setItem(11, sword);

                // close auto anvil inventory and open configured menu
                player.getOpenInventory().close();
                player.openInventory(menu);
            }
            // for serasyll items
            else if(item.getType() == Material.REDSTONE &&
                    item.getItemMeta().getLocalizedName().
                            equals("§4[Heated] §fSerasyll")){
                //create Serasyll helmet for use in menu and set position
                ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
                ItemMeta helmetMeta = helmet.getItemMeta();
                helmetMeta.setLocalizedName("Serasyll Helmet");
                helmet.setItemMeta(helmetMeta);
                menu.setItem(4, helmet);
                // create Serasyll chest plate and set position in menu
                ItemStack chestPlate = new ItemStack(Material.GOLD_CHESTPLATE);
                ItemMeta chestPlateMeta = chestPlate.getItemMeta();
                chestPlateMeta.setLocalizedName("Serasyll Chestplate");
                chestPlate.setItemMeta(chestPlateMeta);
                menu.setItem(13, chestPlate);
                // create Serasyll legging and set position in menu
                ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS);
                ItemMeta leggingsMeta = leggings.getItemMeta();
                leggingsMeta.setLocalizedName("Serasyll Leggings");
                leggings.setItemMeta(leggingsMeta);
                menu.setItem(22, leggings);

                // close auto anvil inventory and open configured menu
                player.getOpenInventory().close();
                player.openInventory(menu);
            }
            // for Adamantium items
            else if(item.getType() == Material.DIAMOND &&
                    item.getItemMeta().getLocalizedName().
                            equals("§4[Heated] §fAdamantium")){
                // create Adamantium sword and set position in menu
                ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta swordMeta = sword.getItemMeta();
                swordMeta.setLocalizedName("Adamantium Sword");
                sword.setItemMeta(swordMeta);
                menu.setItem(11, sword);

                // close auto anvil inventory and open configured menu
                player.getOpenInventory().close();
                player.openInventory(menu);
            }
            // for Mythril items
            else if(item.getType() == Material.EMERALD &&
                    item.getItemMeta().getLocalizedName().
                            equals("§4[Heated] §fMythril")){
                //create Mythril helmet for use in menu and set position
                ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                ItemMeta helmetMeta = helmet.getItemMeta();
                helmetMeta.setLocalizedName("Mythril Helmet");
                helmet.setItemMeta(helmetMeta);
                menu.setItem(4, helmet);
                // create Mythril chest plate and set position in menu
                ItemStack chestPlate = new ItemStack(Material.
                                                     DIAMOND_CHESTPLATE);
                ItemMeta chestPlateMeta = chestPlate.getItemMeta();
                chestPlateMeta.setLocalizedName("Mythril Chestplate");
                chestPlate.setItemMeta(chestPlateMeta);
                menu.setItem(13, chestPlate);
                // create Mythril legging and set position in menu
                ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                ItemMeta leggingsMeta = leggings.getItemMeta();
                leggingsMeta.setLocalizedName("Mythril Leggings");
                leggings.setItemMeta(leggingsMeta);
                menu.setItem(22, leggings);

                // close auto anvil inventory and open configured menu
                player.getOpenInventory().close();
                player.openInventory(menu);
            }
        }

        return true;
    }

    /**
     * Get choice of what item to make from ore when choosing from unfinished
     * creation menu form anvil.
     *
     * @param ice Event given whenever player clicks ona squar ein an inventory
     * @return    boolean - was event handling successful
     */
    @EventHandler
    public boolean onUnfinishedMenuChoice(InventoryClickEvent ice){
        if(ice.isLeftClick() && ice.getCurrentItem() != null &&
           ice.getClickedInventory().getName().equals("Choose what to create")){
            // get player object for sound playing on create item
            Player player = Bukkit.getPlayer(ice.getWhoClicked().getName());

            // remove heated ingot from inventory
            ItemStack item = ice.getWhoClicked().
                             getInventory().getItemInMainHand();

            ItemStack unfinishedItem = null;
            ItemMeta unfinishedMeta = null;
            // set unfinished item to handled iron item
            if(item.getItemMeta().getLocalizedName().contains("Iron Ingot")){
                unfinishedItem = new ItemStack(Material.IRON_INGOT);
                unfinishedMeta = unfinishedItem.getItemMeta();
                unfinishedMeta.setLocalizedName("Unfinished Iron ");
            }
            // set unfinished item to handled Andaryll item
            else if(item.getItemMeta().getLocalizedName().contains("Andaryll")){
                unfinishedItem = new ItemStack(Material.NETHER_BRICK_ITEM);
                unfinishedMeta = unfinishedItem.getItemMeta();
                unfinishedMeta.setLocalizedName("Unfinished Andaryll ");
            }
            // set unfinished item to handled Serasyll item
            else if(item.getItemMeta().getLocalizedName().contains("Serasyll")){
                unfinishedItem = new ItemStack(Material.REDSTONE);
                unfinishedMeta = unfinishedItem.getItemMeta();
                unfinishedMeta.setLocalizedName("Unfinished Serasyll ");
            }
            // set unfinished item to handled Mythril
            else if(item.getItemMeta().getLocalizedName().contains("Mythril")){
                unfinishedItem = new ItemStack(Material.EMERALD);
                unfinishedMeta = unfinishedItem.getItemMeta();
                unfinishedMeta.setLocalizedName("Unfinished Mythril ");
            }
            // set unfinished item to handled Adamantium
            else if(item.getItemMeta().getLocalizedName().
                    contains("Adamantium")){
                unfinishedItem = new ItemStack(Material.DIAMOND);
                unfinishedMeta = unfinishedItem.getItemMeta();
                unfinishedMeta.setLocalizedName("Unfinished Adamantium ");
            }

            // clicked on slot containing helmet, give unfinished helmet ingot.
            //   Can't use Adamantium or Serasyll as they're only for weapons,
            //   tools
            if(ice.getSlot() == 4 && !item.getItemMeta().
                    getLocalizedName().contains("Serasyll") &&
                    !item.getItemMeta().
                    getLocalizedName().contains("Adamantium")){
                // remove item from inventory
                if(item.getAmount() == 1){
                    ice.getWhoClicked().getInventory().remove(item);
                }else{
                    item.setAmount(item.getAmount() - 1);
                }

                unfinishedMeta.setLocalizedName(unfinishedMeta.
                    getLocalizedName() + "Helmet");
                unfinishedItem.setItemMeta(unfinishedMeta);
                ice.getWhoClicked().getInventory().addItem(unfinishedItem);

                // play anvil sound for successful item creation
                player.playSound(player.getLocation(),
                                 Sound.BLOCK_ANVIL_HIT, 20, 0);

                // close menu when successfully made choice
                ice.getWhoClicked().closeInventory();
            }
            // clicked on slot containing chest plate, give unfin. chest plate.
            //   Can't use Adamantium or Serasyll as they're only for weapons,
            //   tools.
            else if(ice.getSlot() == 13 && !item.getItemMeta().
                    getLocalizedName().contains("Serasyll") &&
                    !item.getItemMeta().
                    getLocalizedName().contains("Adamantium")){
                // remove item from inventory
                if(item.getAmount() == 1){
                    ice.getWhoClicked().getInventory().remove(item);
                }else{
                    item.setAmount(item.getAmount() - 1);
                }

                unfinishedMeta.setLocalizedName(unfinishedMeta.
                    getLocalizedName() + "Chestplate");
                unfinishedItem.setItemMeta(unfinishedMeta);
                ice.getWhoClicked().getInventory().addItem(unfinishedItem);

                // play anvil sound for successful item creation
                player.playSound(player.getLocation(),
                                 Sound.BLOCK_ANVIL_HIT, 20, 0);

                // close menu when successfully made choice
                ice.getWhoClicked().closeInventory();
            }
            // clicked on slot with leggings, give unfin. leggings. Can't use
            //   Adamantium or Serasyll as they're only for weapons, tools
            else if(ice.getSlot() == 22 && !item.getItemMeta().
                    getLocalizedName().contains("Serasyll") &&
                    !item.getItemMeta().
                    getLocalizedName().contains("Adamantium")){
                // remove item from inventory
                if(item.getAmount() == 1){
                    ice.getWhoClicked().getInventory().remove(item);
                }else{
                    item.setAmount(item.getAmount() - 1);
                }

                unfinishedMeta.setLocalizedName(unfinishedMeta.
                    getLocalizedName() + "Leggings");
                unfinishedItem.setItemMeta(unfinishedMeta);
                ice.getWhoClicked().getInventory().addItem(unfinishedItem);

                // remove item from inventory
                if(item.getAmount() == 1){
                    ice.getWhoClicked().getInventory().remove(item);
                }else{
                    item.setAmount(item.getAmount() - 1);
                }

                // play anvil sound for successful item creation
                player.playSound(player.getLocation(),
                                 Sound.BLOCK_ANVIL_HIT, 20, 0);

                // close menu when successfully made choice
                ice.getWhoClicked().closeInventory();
            }
            // clicked on slot with sword, give unfin. sword. Can't use
            //   Mythril or Andaryll as they're only for armor
            else if(ice.getSlot() == 11 && !item.getItemMeta().
                    getLocalizedName().contains("Andaryll") &&
                    !item.getItemMeta().getLocalizedName().contains("Mythril")){
                // remove item from inventory
                if(item.getAmount() == 1){
                    ice.getWhoClicked().getInventory().remove(item);
                }else{
                    item.setAmount(item.getAmount() - 1);
                }

                unfinishedMeta.setLocalizedName(unfinishedMeta.
                    getLocalizedName() + "Sword");
                unfinishedItem.setItemMeta(unfinishedMeta);
                ice.getWhoClicked().getInventory().addItem(unfinishedItem);

                // play anvil sound for successful item creation
                player.playSound(player.getLocation(),
                                 Sound.BLOCK_ANVIL_HIT, 20, 0);

                // close menu when successfully made choice
                ice.getWhoClicked().closeInventory();
            }
        }

        return true;
    }
}
