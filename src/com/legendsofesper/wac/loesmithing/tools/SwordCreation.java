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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.material.Cauldron;
import org.bukkit.plugin.Plugin;

import java.util.Random;

/**
 * Implementation for Sword Shaping process event handlers.
 *
 * TODO: update skill check to work with server skills plugin
 * TODO: finish grinding phase
 *
 * @version 5-Apr-2018
 */
public class SwordCreation implements Listener {
    private Plugin plugin;

    public SwordCreation(Plugin plugin){
        this.plugin = plugin;
    }

    // event handler for heating unfinished swords at magma block
    @EventHandler
    public boolean onUnfinishedSwordHeat(PlayerInteractEvent pie){
        // make sure trying ot not handle invalid event with stationary water
        if(pie.getClickedBlock() == null){
            return true;
        }

        Player player = pie.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(player.isSneaking() && pie.getClickedBlock().getType() ==
                Material.MAGMA && pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
                pie.getHand() == EquipmentSlot.HAND && item.hasItemMeta()){
            String itemName = item.getItemMeta().getLocalizedName();
            String hiddenMeta;
            if(itemName.length() >= 6){
                hiddenMeta = itemName.substring(itemName.length() - 6);
            }else{
                return true;
            }

            ItemMeta newMeta = item.getItemMeta();
            // for iron sword
            if(item.getType() == Material.IRON_INGOT &&
                    itemName.equals("Unfinished Iron Sword" + hiddenMeta)){
                newMeta.setLocalizedName("§4[Heated] §fUnfinished Iron Sword" +
                        hiddenMeta);
            }
            // on serasyll sword
            else if(item.getType() == Material.REDSTONE &&
                    itemName.equals("Unfinished Serasyll Sword" + hiddenMeta)){
                newMeta.setLocalizedName("§4[Heated] §fUnfinished Serasyll" +
                        " Sword" + hiddenMeta);
            }
            // on adamantium sword
            else if(item.getType() == Material.DIAMOND &&
                    itemName.equals("Unfinished Adamantium Sword" +
                            hiddenMeta)){
                newMeta.setLocalizedName("§4[Heated] §fUnfinished Adamantium" +
                        " Sword" + hiddenMeta);
            }else{
                return true;
            }

            // remove item from inventory
            if(item.getAmount() == 1){
                player.getInventory().remove(item);
            }else{
                item.setAmount(item.getAmount() - 1);
            }

            // play sound for immersion
            player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP,
                    20, 0);

            player.sendMessage("You rest the unfinished sword in the burning" +
                    " coals");

            // create delay of 40 ticks or 2 seconds before giving ore back
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                    new Runnable() {
                @Override
                public void run() {
                    // set new heated item's name and give to player
                    ItemStack newItem = new ItemStack(item.getType());
                    newItem.setItemMeta(newMeta);
                    player.getInventory().addItem(newItem);

                    // give it 2 seconds for player to recieve item, then item cools
                    //   in 2400 ticks or 2 minutes
                    Bukkit.getServer().getScheduler().
                                scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if(newItem != null){
                                        if(newItem.hasItemMeta()){
                                            if(newItem.getItemMeta().getLocalizedName().contains("§4[Heated]")){
                                                player.sendMessage("got here");
                                                player.getInventory().remove(newItem);
//                                                ItemMeta brandNewMeta = item.getItemMeta();
//                                                brandNewMeta.setLocalizedName(brandNewMeta.getLocalizedName().substring(12));
//                                                newItem.setItemMeta(brandNewMeta);
                                            }
                                        }
                                    }
                                }
                            }, 40);
                }
            }, 40);
        }

        return true;
    }

    @EventHandler
    public boolean onUnfinishedSwordHammer(PlayerInteractEvent pie){
        // make sure trying ot not handle invalid event with stationary water
        if(pie.getClickedBlock() == null){
            return true;
        }

        Player player = pie.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(player.isSneaking() && pie.getClickedBlock().getType() ==
                Material.ANVIL && pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
                pie.getHand() == EquipmentSlot.HAND && item.hasItemMeta()){

            String itemName = item.getItemMeta().getLocalizedName();
            String hiddenMeta;
            if(itemName.length() >= 6){
                hiddenMeta = itemName.substring(itemName.length() - 6);
            }else{
                return true;
            }

            // if it has already been hammered in this iteration, don't let
            //   player do another skill check
            if(hiddenMeta.charAt(5) == 'f'){
                player.sendMessage(ChatColor.WHITE + "The sword is searing hot"
                        + ". You should try cooling it down.");

                return true;
            }

            // get hidden metadata for updating
            int timesHammered = ((int)hiddenMeta.charAt(1)) - 48;
            int qualityPoints = ((int)hiddenMeta.charAt(3)) - 48;

            int amountToPassCheck;
            // for iron sword
            if(item.getType() == Material.IRON_INGOT &&
                    itemName.equals("§4[Heated] §fUnfinished Iron Sword" +
                            hiddenMeta)){
                amountToPassCheck = 40;
            }
            // on serasyll sword
            else if(item.getType() == Material.REDSTONE &&
                    itemName.equals("§4[Heated] §fUnfinished Serasyll Sword" +
                            hiddenMeta)){
                amountToPassCheck = 70;
            }
            // on adamantium sword
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

            // remove item from inventory
            if(item.getAmount() == 1){
                player.getInventory().remove(item);
            }else{
                item.setAmount(item.getAmount() - 1);
            }

            // play sound for immersion
            player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP,
                    20, 0);

            timesHammered++;
            String newItemName = itemName.substring(0,
                    itemName.length() - 6) + "§" + timesHammered + "§" +
                    qualityPoints + "§f";

            // set new item meta with updated hidden meta and give to player
            ItemStack newItem = new ItemStack(item.getType());
            ItemMeta meta = item.getItemMeta();
            meta.setLocalizedName(newItemName);
            newItem.setItemMeta(meta);
            player.getInventory().addItem(newItem);
        }

        return true;
    }

    @EventHandler
    public boolean onUnfinishedSwordCool(PlayerInteractEvent pie){
        // make sure trying ot not handle invalid event with stationary water
        if(pie.getClickedBlock() == null){
            return true;
        }

        Player player = pie.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        Cauldron cauldron;
        if(pie.getClickedBlock().getState().getData() instanceof Cauldron)
            cauldron = ((Cauldron) pie.getClickedBlock().
                    getState().getData());
        else
            return true;

        if(player.isSneaking() &&
                cauldron.isFull() &&
                pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
                pie.getHand() == EquipmentSlot.HAND && item.hasItemMeta()){
            String itemName = item.getItemMeta().getLocalizedName();
            String hiddenMeta;
            // see if can get full hidden meta data, not correct item if not
            if(itemName.length() >= 6){
                hiddenMeta = itemName.substring(itemName.length() - 6);
            }else{
                return true;
            }

            // check to see if has been hammered in this iteration, if not
            //   don't let player cool it and tell them what to do
            if(hiddenMeta.charAt(5) == 't'){
                player.sendMessage(ChatColor.WHITE + "The sword is hot and " +
                    " ready to be shaped. You should try hammering it on an " +
                        "anvil.");

                return true;
            }

            // get hidden metadata for checking/updating
            int timesHammered = ((int)hiddenMeta.charAt(1)) - 48;
            int qualityPoints = ((int)hiddenMeta.charAt(3)) - 48;

            String newName;
            Material swordType;
            Material oreType;
            // for iron Sword
            if(item.getType() == Material.IRON_INGOT &&
                    itemName.equals("§4[Heated] §fUnfinished Iron Sword" +
                            hiddenMeta)){
                newName = "Iron Sword";

                swordType = Material.IRON_SWORD;
                oreType = Material.IRON_INGOT;
            }
            // on serasyll Sword
            else if(item.getType() == Material.REDSTONE &&
                    itemName.equals("§4[Heated] §fUnfinished Serasyll Sword" +
                            hiddenMeta)){
                newName = "Serasyll Sword";

                swordType = Material.GOLD_SWORD;
                oreType = Material.REDSTONE;
            }
            // on adamantium Sword
            else if(item.getType() == Material.DIAMOND &&
                    itemName.equals("§4[Heated] §fUnfinished Adamantium Sword" +
                            hiddenMeta)){
                newName = "Adamantium Sword";

                swordType = Material.DIAMOND_SWORD;
                oreType = Material.DIAMOND;
            }else{
                return true;
            }

            //
            String state;
            ItemStack newItem;
            // moving onto grinding phase
            if(timesHammered >= 3){
                state = "Unsharpened";
                hiddenMeta = "§0§" + qualityPoints;

                newItem = new ItemStack(swordType);
            }
            // staying in shaping phase
            else{
                state = "Unfinished";
                hiddenMeta = "§" + timesHammered + "§" + qualityPoints + "§t";

                newItem = new ItemStack(oreType);
            }

            // remove item from inventory
            if(item.getAmount() == 1){
                player.getInventory().remove(item);
            }else{
                item.setAmount(item.getAmount() - 1);
            }

            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH,
                    20, 0);

            // create new item with appropriate meta and give to player
            String metaName = state + " " + newName + hiddenMeta;
            ItemMeta newMeta = newItem.getItemMeta();
            newMeta.setLocalizedName(metaName);

            newItem.setItemMeta(newMeta);

            player.getInventory().addItem(newItem);
        }

        return true;
    }

    @EventHandler
    public boolean onSwordGrinding(PlayerInteractEvent pie){
        return true;
    }
}
