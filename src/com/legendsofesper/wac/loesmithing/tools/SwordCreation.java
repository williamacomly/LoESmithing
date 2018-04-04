//****************************************************
//
// File:    SwordCreation.java
// Package: com.legendsofesper.wac.loesmithing.tools
// Class:   SwordCreation
//
// Author:  William Comly <williamacomly@gmail.com>
//
//****************************************************

package com.legendsofesper.wac.loesmithing.tools;

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

import java.util.Random;

/**
 * Implementation for Sword Shaping process event handlers.
 *
 * TODO: update skill check to work with server skills plugin
 * TODO: finish sword cooling step of shaping phase
 * TODO: finish grinding phase
 *
 * @version 4-Apr-2018
 */
public class SwordCreation implements Listener {
    // event handler for heating unfinished swords at magma block
    @EventHandler
    public boolean onUnfinishedSwordHeat(PlayerInteractEvent pie){
        Player player = pie.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(player.isSneaking() && pie.getClickedBlock().getType() ==
                Material.MAGMA && pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
                pie.getHand() == EquipmentSlot.HAND && item.hasItemMeta()){
            String itemName = item.getItemMeta().getLocalizedName();
            String hiddenMeta;
            if(itemName.length() >= 4){
                hiddenMeta = itemName.substring(itemName.length() - 4);
            }else{
                return true;
            }

            ItemMeta newMeta = item.getItemMeta();
            // for iron tools
            if(item.getType() == Material.IRON_INGOT &&
                    itemName.equals("Unfinished Iron Sword" + hiddenMeta)){
                newMeta.setLocalizedName("§4[Heated] §fUnfinished Iron Sword" +
                        hiddenMeta);
            }
            // on serasyll tools
            else if(item.getType() == Material.REDSTONE &&
                    itemName.equals("Unfinished Serasyll Sword" + hiddenMeta)){
                newMeta.setLocalizedName("§4[Heated] §fUnfinished Serasyll" +
                        " Sword" + hiddenMeta);
            }
            // on adamantium tools
            else if(item.getType() == Material.DIAMOND &&
                    itemName.equals("Unfinished Adamantium Sword" +
                            hiddenMeta)){
                newMeta.setLocalizedName("§4[Heated] §fUnfinished Adamantium" +
                        " Sword" + hiddenMeta);
            }else{
                return true;
            }

            // play sound for immersion
            player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP,
                    20, 0);

            // set item's name and give to player
            item.setItemMeta(newMeta);
        }

        return true;
    }

    @EventHandler
    public boolean onUnfinishedSwordHammer(PlayerInteractEvent pie){
        Player player = pie.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(player.isSneaking() && pie.getClickedBlock().getType() ==
                Material.ANVIL && pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
                pie.getHand() == EquipmentSlot.HAND && item.hasItemMeta()){
            String itemName = item.getItemMeta().getLocalizedName();
            String hiddenMeta;
            if(itemName.length() >= 4){
                hiddenMeta = itemName.substring(itemName.length() - 4);
            }else{
                return true;
            }

            // get hidden metadata for updating
            int timesHammered = ((int)hiddenMeta.charAt(1)) - 48;
            int qualityPoints = ((int)hiddenMeta.charAt(3)) - 48;

            int amountToPassCheck;
            // for iron tools
            if(item.getType() == Material.IRON_INGOT &&
                    itemName.equals("§4[Heated] §fUnfinished Iron Sword" +
                            hiddenMeta)){
                amountToPassCheck = 40;
            }
            // on serasyll tools
            else if(item.getType() == Material.REDSTONE &&
                    itemName.equals("§4[Heated] §fUnfinished Serasyll Sword" +
                            hiddenMeta)){
                amountToPassCheck = 70;
            }
            // on adamantium tools
            else if(item.getType() == Material.DIAMOND &&
                    itemName.equals("§4[Heated] §fUnfinished Adamantium Sword" +
                            hiddenMeta)){
                amountToPassCheck = 100;
            }else{
                return true;
            }

            // skill check for quality point increase/decrease
            int smithingLevel = 1;
            int checkValue = ((new Random()).nextInt(100) + 1) +
                    (3 * smithingLevel);
            // update quality points based on skill check pass/fail
            qualityPoints = (checkValue >= amountToPassCheck) ?
                    qualityPoints++ : qualityPoints--;

            // play sound for immersion
            player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP,
                    20, 0);

            timesHammered++;
            String newItemName = itemName.substring(0,
                    itemName.length() - 4) + "§" + timesHammered + "§" +
                    qualityPoints;

            // set new item meta with updated hidden meta
            ItemMeta meta = item.getItemMeta();
            meta.setLocalizedName(newItemName);
            item.setItemMeta(meta);
        }

        return true;
    }

    @EventHandler
    public boolean onUnfinishedSwordCool(PlayerInteractEvent pie){
        Player player = pie.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(player.isSneaking() && pie.getClickedBlock().getType() ==
                Material.WATER && pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
                pie.getHand() == EquipmentSlot.HAND && item.hasItemMeta()){
            String itemName = item.getItemMeta().getLocalizedName();
            String hiddenMeta;
            // see if can get full hidden meta data, not correct item if not
            if(itemName.length() >= 4){
                hiddenMeta = itemName.substring(itemName.length() - 4);
            }else{
                return true;
            }

            // get hidden metadata for checking/updating
            int timesHammered = ((int)hiddenMeta.charAt(1)) - 48;
            int qualityPoints = ((int)hiddenMeta.charAt(3)) - 48;

            // for iron tools
            if(item.getType() == Material.IRON_INGOT &&
                    itemName.equals("§4[Heated] §fUnfinished Iron Sword" +
                            hiddenMeta)){
            }
            // on serasyll tools
            else if(item.getType() == Material.REDSTONE &&
                    itemName.equals("§4[Heated] §fUnfinished Serasyll Sword" +
                            hiddenMeta)){
            }
            // on adamantium tools
            else if(item.getType() == Material.DIAMOND &&
                    itemName.equals("§4[Heated] §fUnfinished Adamantium Sword" +
                            hiddenMeta)){
            }else{
                return true;
            }
        }

        return true;
    }
}
