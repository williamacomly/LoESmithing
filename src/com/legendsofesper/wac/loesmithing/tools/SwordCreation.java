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

import com.legendsofesper.wac.loesmithing.MaterialConstants;
import org.bukkit.Bukkit;
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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation for Sword Shaping process event handlers.
 *
 * TODO: update skill check to work with server skills plugin
 * TODO: finish grinding phase
 *
 * @version 9-Apr-2018
 */
public class SwordCreation implements Listener {
    private JavaPlugin plugin;

    public SwordCreation(JavaPlugin plugin){
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
            else if(item.getType() == Material.GLOWSTONE_DUST &&
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
                    " coals.");

            // create delay of 40 ticks or 2 seconds before giving ore back
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                    new Runnable() {
                @Override
                public void run() {
                    // set new heated item's name and give to player
                    ItemStack newItem = new ItemStack(item.getType());
                    newItem.setItemMeta(newMeta);
                    player.getInventory().addItem(newItem);
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
            pie.setCancelled(true);

            String itemName = item.getItemMeta().getLocalizedName();
            String hiddenMeta;
            if(itemName.length() >= 6){
                hiddenMeta = itemName.substring(itemName.length() - 6);
            }else{
                return true;
            }

            // get hidden metadata for updating
            int timesHammered = ((int)hiddenMeta.charAt(1)) - 48;
            int qualityPoints = ((int)hiddenMeta.charAt(3)) - 48;

            ItemStack itemToTake;
            int amountToPassCheck;
            // for iron sword
            if(item.getType() == Material.IRON_INGOT &&
                    itemName.equals("§4[Heated] §fUnfinished Iron Sword" +
                            hiddenMeta)){
                amountToPassCheck = 40;

                itemToTake = MaterialConstants.getIRON_INGOT();
            }
            // on serasyll sword
            else if(item.getType() == Material.GLOWSTONE_DUST &&
                    itemName.equals("§4[Heated] §fUnfinished Serasyll Sword" +
                            hiddenMeta)){
                amountToPassCheck = 70;

                itemToTake = MaterialConstants.getSERASYLL();
            }
            // on adamantium sword
            else if(item.getType() == Material.DIAMOND &&
                    itemName.equals("§4[Heated] §fUnfinished Adamantium Sword" +
                            hiddenMeta)){
                amountToPassCheck = 100;

                itemToTake = MaterialConstants.getADAMANTIUM();
            }else{
                return true;
            }

            // if it has already been hammered in this iteration, don't let
            //   player do another skill check
            if(hiddenMeta.charAt(5) == 'f'){
                player.sendMessage("The sword is searing" +
                " hot and has already been shaped." +
                ". You should try cooling it down.");

                return true;
            }

            // skill check for quality point increase/decrease
            int smithingLevel = 1;
            int checkValue = ((new Random()).nextInt(100) + 1) +
                    (3 * smithingLevel);
            // update quality points based on skill check pass/fail
            qualityPoints = (checkValue >= amountToPassCheck) ?
                    (qualityPoints + 1) : (qualityPoints - 1);

            // check to see if player has at least one other material in
            //   inventory to add to sword, if not do not complete step
            if(player.getInventory().
                containsAtLeast(itemToTake, 1)){
                // remove extra material from inventory
                for(ItemStack takeItem : player.getInventory().getContents()){
                    if(isSame(takeItem, itemToTake))
                        if(takeItem.getAmount() == 1){
                            player.getInventory().remove(takeItem);
                        }else{
                            takeItem.
                                    setAmount(takeItem.getAmount() - 1);
                        }
                }
            }else{
                player.sendMessage("You need more material to continue to " +
                    "shape the sword.");
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

            timesHammered++;
            String newItemName = itemName.substring(0,
                    itemName.length() - 6) + "§" + timesHammered + "§" +
                    qualityPoints + "§f";

            player.sendMessage("You begin to hammer the unfinished sword into" +
                " shape.");

            // create delay of 40 ticks or 2 seconds before player recieves
            //   completed item
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                new Runnable() {
                    @Override
                    public void run() {
                        // set new item meta with updated hidden meta and give
                        //   to player
                        ItemStack newItem = new ItemStack(item.getType());
                        ItemMeta meta = item.getItemMeta();
                        meta.setLocalizedName(newItemName);
                        newItem.setItemMeta(meta);
                        player.getInventory().addItem(newItem);
                    }
                }, 40);
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
            else if(item.getType() == Material.GLOWSTONE_DUST &&
                    itemName.equals("§4[Heated] §fUnfinished Serasyll Sword" +
                            hiddenMeta)){
                newName = "Serasyll Sword";

                swordType = Material.GOLD_SWORD;
                oreType = Material.GLOWSTONE_DUST;
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

            // check to see if has been hammered in this iteration, if not
            //   don't let player cool it and tell them what to do
            if(hiddenMeta.charAt(5) == 't'){
                if(itemName.contains("§4[Heated]")){
                    player.sendMessage("The sword is hot and " +
                            " ready to be shaped. You should try hammering it" +
                            " on an anvil.");
                }else{
                    player.sendMessage("The sword is ready to be shaped, but" +
                            " it is cool. You should try " +
                            "heating then hammering it on an anvil.");
                }

                return true;
            }

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

            player.sendMessage("You put the unfinished sword into the water," +
                " cooling it down.");

            // give a delay of 40 ticks or 2 seconds before player get completed
            //   item
            final String finalHiddenMeta = hiddenMeta;
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                new Runnable() {
                    @Override
                    public void run() {
                        // create new item with appropriate meta and give to
                        //   player
                        String metaName = state + " " + newName +
                            finalHiddenMeta;
                        ItemMeta newMeta = newItem.getItemMeta();
                        newMeta.setLocalizedName(metaName);

                        newItem.setItemMeta(newMeta);

                        player.getInventory().addItem(newItem);
                    }
                }, 40);
        }

        return true;
    }

    @EventHandler
    public boolean onSwordGrinding(PlayerInteractEvent pie){
        // make sure trying ot not handle invalid event with stationary water
        if(pie.getClickedBlock() == null){
            return true;
        }

        Player player = pie.getPlayer();
        ItemStack item = pie.getItem();

        if(pie.getClickedBlock().getType() == Material.STONE &&
           pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
           pie.getHand() == EquipmentSlot.HAND && item.hasItemMeta() &&
                player.isSneaking()){
            String itemName = item.getItemMeta().getLocalizedName();

            // get hidden meta info for item
            String hiddenMeta;
            if(itemName.length() >= 4)
                hiddenMeta = itemName.substring(itemName.length() - 4);
            else
                return true;

            int timesGrinded = ((int)hiddenMeta.charAt(1)) - 48;
            int qualityPoints = ((int)hiddenMeta.charAt(3)) - 48;

            ItemStack newItem;
            String newName;
            int amountToPassCheck;
            int maxQuality;
            if(item.getType() == Material.IRON_SWORD &&
                    itemName.equals("Unsharpened Iron Sword" + hiddenMeta)){
                newItem = new ItemStack(Material.IRON_SWORD);
                newName = "Iron Sword";

                amountToPassCheck = 40;
                maxQuality = 4;
            }else if(item.getType() == Material.GOLD_SWORD &&
                    itemName.equals("Unsharpened Serasyll Sword" + hiddenMeta)){
                newItem = new ItemStack(Material.GOLD_SWORD);
                newName = "Serasyll Sword";

                amountToPassCheck = 70;
                maxQuality = 5;
            }else if(item.getType() == Material.DIAMOND_SWORD &&
                    itemName.equals("Unsharpened Adamantium Sword" +
                        hiddenMeta)){
                newItem = new ItemStack(Material.DIAMOND_SWORD);
                newName = "Adamantium Sword";

                amountToPassCheck = 100;
                maxQuality = 6;
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
            player.playSound(player.getLocation(), Sound.BLOCK_SAND_PLACE,
                    20, 0);

            // skill check for quality point increase
            int smithingLevel = 1;
            int checkValue = ((new Random()).nextInt(100) + 1) +
                    (3 * smithingLevel);
            // update quality points based on skill check pass/fail
            qualityPoints = (checkValue >= amountToPassCheck) ?
                    (qualityPoints + 1) : qualityPoints;

            // set the name if still needs to be grinded or, if not, give it
            //   its quality
            String qualityMeta = "";
            if(timesGrinded >= 2){
                if(qualityPoints >= 6 && maxQuality >= 6)
                    qualityMeta = "§dThis sword is of §6Legendary§d quality.";
                else if(qualityPoints >= 5 && maxQuality >= 5)
                    qualityMeta = "§dThis sword is of §5Master§d quality.";
                else if(qualityPoints >= 4 && maxQuality >= 4)
                    qualityMeta = "§dThis sword is of §1Expert§d quality.";
                else if(qualityPoints >= 3)
                    qualityMeta = "§dThis sword is of §2Moderate§d quality.";
                else if(qualityPoints >= 2)
                    qualityMeta = "§dThis sword is of §cFair§d quality.";
                else if(qualityPoints >= 0)
                    qualityMeta = "§dThis sword is of §8Poor§d quality.";
                else {
                    player.sendMessage("The quality of the sword was too low " +
                            "and it broke on the grinding wheel. Hone your " +
                            "skills further.");

                    return true;
                }
            }else{
                newName = "Unsharpened " + newName + "§" + (timesGrinded + 1) +
                        "§" + qualityPoints;
            }

            // create finished item and give it necessary name/lore
            ItemMeta newMeta = newItem.getItemMeta();
            newMeta.setLocalizedName(newName);
            if(!qualityMeta.equals("")) {
                List<String> lore = new ArrayList<>();
                lore.add(qualityMeta);
                newMeta.setLore(lore);
            }

            // give a delay of 40 ticks or 2 seconds before player get completed
            //   item
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                    new Runnable() {
                        @Override
                        public void run() {
                            // create new item with appropriate meta and give to
                            //   player
                            newItem.setItemMeta(newMeta);

                            player.getInventory().addItem(newItem);
                        }
                    }, 40);
        }

        return true;
    }

    /**
     * Used in Sword Hammering step to see if current item is the same as an
     * to be taken from inventory as extra material.
     *
     * @param item1 The item to be tested
     * @param item2 The item to be taken
     * @return      t/f if the item tested is the same as what needs to be taken
     */
    private boolean isSame(ItemStack item1, ItemStack item2){
        if(item1 != null){
            if(item1.hasItemMeta()){
                if(item1.getItemMeta().getLocalizedName() != null){
                    if(item1.getItemMeta().getLocalizedName().
                            equals(item2.getItemMeta().getLocalizedName())){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
