//****************************************************
//
// File:    SwordShaping.java
// Package: com.legendsofesper.wac.loesmithing.sword
// Class:   SwordShaping
//
// Author:  William Comly <williamacomly@gmail.com>
//
//****************************************************

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
 * Implementation for Sword Shaping process event handlers.
 *
 * @version 4-Apr-2018
 */
public class SwordShaping implements Listener {
    @EventHandler
    public boolean onUnfinishedSwordHeat(PlayerInteractEvent pie){
        Player player = pie.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(player.isSneaking() && pie.getClickedBlock().getType() ==
                Material.MAGMA && pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
                pie.getHand() == EquipmentSlot.HAND && item.hasItemMeta()){
            boolean isSuccessful = true;

            String itemName = item.getItemMeta().getLocalizedName();
            ItemStack newHeatedSword = new ItemStack(Material.AIR);
            ItemMeta newMeta = newHeatedSword.getItemMeta();
            // for iron sword
            if(item.getType() == Material.IRON_INGOT &&
                    itemName.equals("Unfinished Iron Sword")){
                newHeatedSword = new ItemStack(Material.IRON_SWORD);
                newMeta.setLocalizedName("§4[Heated] §fUnfinished Iron Sword");
            }
            // on serasyll sword
            else if(item.getType() == Material.REDSTONE &&
                    itemName.equals("Unfinished Serasyll Sword")){
                newHeatedSword = new ItemStack(Material.GOLD_SWORD);
                newMeta.setLocalizedName("§4[Heated] §fUnfinished Serasyll" +
                        " Sword");
            }
            // on adamantium sword
            else if(item.getType() == Material.DIAMOND &&
                    itemName.equals("Unfinished Adamantium Sword")){
                newHeatedSword = new ItemStack(Material.DIAMOND_SWORD);
                newMeta.setLocalizedName("§4[Heated] §fUnfinished Adamantium" +
                        " Sword");
            }else{
                isSuccessful = false;
            }

            if(isSuccessful){
                // remove old item from inventory
                if(item.getAmount() == 1){
                    player.getInventory().remove(item);
                }else{
                    item.setAmount(item.getAmount() - 1);
                }

                // play sound for immersion
                player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP,
                        20, 0);

                // set item's name and give to player
                newHeatedSword.setItemMeta(newMeta);
                player.getInventory().addItem(newHeatedSword);
            }
        }

        return true;
    }
}
