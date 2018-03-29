//***********************************************
//
// File:    SwordCreation.java
// Package: com.legendsofesper.wac.loesmithing.sword
// Class:   SwordCreation
//
// Author:  William Comly <williamacomly@gmail.com>
//
//***********************************************

package com.legendsofesper.wac.loesmithing.sword;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Hold implementation for Sword Creation process event handlers.
 *
 * @author  William Comly <williamacomly@gmail.com>
 * @version 29-Mar-2018
 */
public class SwordCreation implements Listener {
    /**
     * Implementation for Ore Heating EventHandelr which starts sword creation.
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
        ItemStack item = player.getInventory().getItem(player.getInventory().getHeldItemSlot());

        if(player.isSneaking() && pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
                pie.getClickedBlock().getType() == Material.MAGMA && pie.getHand().equals(EquipmentSlot.HAND)){

            // handle iron heating
            if(item.getType() == Material.IRON_INGOT && !item.hasItemMeta()){
                // remove old item from inventory
                if(item.getAmount() == 1){
                    player.getInventory().remove(item);
                }else{
                    item.setAmount(item.getAmount() - 1);
                }

                // create the new ingot item and give it meta data to reflect the change
                ItemStack newIngot = new ItemStack(Material.IRON_INGOT, 1);
                ItemMeta ingotMeta = newIngot.getItemMeta();
                ingotMeta.setLocalizedName("§4[Heated] §fIron Ingot");
                newIngot.setItemMeta(ingotMeta);

                // add item to inventory and play sound clip to signify completion
                player.getInventory().addItem(newIngot);
                player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 20, 0);
            }
            // handle Andaryll heating
            else if(item.getType() == Material.LAPIS_ORE && item.hasItemMeta()){
                if(item.getItemMeta().getLocalizedName().equals("Andaryll")){
                    // remove old item from inventory
                    if(item.getAmount() == 1){
                        player.getInventory().remove(item);
                    }else{
                        item.setAmount(item.getAmount() - 1);
                    }

                    // create the new ore item and give it meta data to reflect the change
                    ItemStack newOre = new ItemStack(Material.LAPIS_ORE, 1);
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
        }

        return true;
    }
}
