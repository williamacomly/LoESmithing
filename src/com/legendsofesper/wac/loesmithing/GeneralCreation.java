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
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Event handlers and helper moethods that contribute to starting the smithing
 * process for all tools/arms/armor covered by plugin
 *
 * @version 9-Apr-2018
 */
public class GeneralCreation implements Listener{
    private JavaPlugin plugin;

    public GeneralCreation(JavaPlugin plugin){
        this.plugin = plugin;
    }

    /**
     * Make sure players cannot access basic MC anvil inventory
     *
     * @param ioe event passes by listener to handler
     * @return    boolean whether or not handler succeeded
     */
    @EventHandler
    public boolean onAnvilInteract(InventoryOpenEvent ioe){
        // if opened anvil crafting inventory
        if(ioe.getInventory().getType() == InventoryType.ANVIL){
            ioe.setCancelled(true);
        }

        return true;
    }

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
        // make sure trying ot not handle invalid event with stationary water
        if(pie.getClickedBlock() == null){
            return true;
        }

        Player player = pie.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(player.isSneaking() && pie.getAction() == Action.RIGHT_CLICK_BLOCK &&
           pie.getClickedBlock().getType() == Material.MAGMA && pie.getHand().
               equals(EquipmentSlot.HAND) && item.hasItemMeta()){

            ItemStack newMaterial;
            String oldName = item.getItemMeta().getLocalizedName();
            String newName = "§4[Heated] §f" + oldName;
            // handle iron heating
            if(item.getType() == Material.IRON_INGOT &&
                    oldName.equals("Iron Ingot")){
                // create the new ingot item and set meta data to reflect
                //   the change
                newMaterial = new ItemStack(Material.IRON_INGOT);
            }
            // handle Andaryll heating
            else if(item.getType() == Material.GOLD_INGOT &&
                    oldName.equals("Andaryll")){
                // create the new ingot item and set meta data to reflect
                //   the change
                newMaterial = new ItemStack(Material.GOLD_INGOT);
            }
            // handle Serasyll heating
            else if(item.getType() == Material.GLOWSTONE_DUST &&
                    oldName.equals("Serasyll")){
                // create the new ingot item and set meta data to reflect
                //   the change
                newMaterial = new ItemStack(Material.GLOWSTONE_DUST);
            }
            // handle mythril heating
            else if(item.getType() == Material.EMERALD &&
                    oldName.equals("Mythril")){
                // create the new ingot item and set meta data to reflect
                //   the change
                newMaterial = new ItemStack(Material.EMERALD);
            }
            // handle adamantium heating
            else if(item.getType() == Material.DIAMOND &&
                    oldName.equals("Adamantium")){
                // create the new ingot item and set meta data to reflect
                //   the change
                newMaterial = new ItemStack(Material.DIAMOND);
            }else{
                return true;
            }

            // remove item from inventory
            if(item.getAmount() == 1){
                player.getInventory().remove(item);
            }else{
                item.setAmount(item.getAmount() - 1);
            }

            // play sound clip to signify
            //   completion
            player.playSound(player.getLocation(),
            Sound.BLOCK_LAVA_POP, 20, 0);

            player.sendMessage("You rest the " + oldName + " in the burning" +
                " coals.");

            // give this step a delay of 40 ticks or 2 seconds before player
            //   gets completed item
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                new Runnable() {
                    @Override
                    public void run() {
                        // set new status name in new meta data
                        ItemMeta newMeta = item.getItemMeta();
                        newMeta.setLocalizedName(newName);
                        newMaterial.setItemMeta(newMeta);

                        player.getInventory().addItem(newMaterial);
                    }
                }, 40);
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
        // make sure trying ot not handle invalid event with stationary water
        if(pie.getClickedBlock() == null){
            return true;
        }

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
                // create iron tools and set position in menu
                ItemStack sword = new ItemStack(Material.IRON_SWORD);
                ItemMeta swordMeta = sword.getItemMeta();
                swordMeta.setLocalizedName("Iron Sword");
                sword.setItemMeta(swordMeta);
                menu.setItem(11, sword);

                // close auto anvil inventory and open configured menu
                player.openInventory(menu);
            }
            // for serasyll items
            else if(item.getType() == Material.GLOWSTONE_DUST &&
                    item.getItemMeta().getLocalizedName().
                    equals("§4[Heated] §fSerasyll")){
                // create Serasyll sword and set position in menu
                ItemStack sword = new ItemStack(Material.GOLD_SWORD);
                ItemMeta swordMeta = sword.getItemMeta();
                swordMeta.setLocalizedName("Serasyll Sword");
                sword.setItemMeta(swordMeta);
                menu.setItem(11, sword);
            }
            // for andaryll items
            else if(item.getType() == Material.GOLD_INGOT &&
                    item.getItemMeta().getLocalizedName().
                            equals("§4[Heated] §fAndaryll")){
                //create Andaryll helmet for use in menu and set position
                ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
                ItemMeta helmetMeta = helmet.getItemMeta();
                helmetMeta.setLocalizedName("Andaryll Helmet");
                helmet.setItemMeta(helmetMeta);
                menu.setItem(4, helmet);
                // create Andaryll chest plate and set position in menu
                ItemStack chestPlate = new ItemStack(Material.GOLD_CHESTPLATE);
                ItemMeta chestPlateMeta = chestPlate.getItemMeta();
                chestPlateMeta.setLocalizedName("Andaryll Chestplate");
                chestPlate.setItemMeta(chestPlateMeta);
                menu.setItem(13, chestPlate);
                // create Andaryll legging and set position in menu
                ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS);
                ItemMeta leggingsMeta = leggings.getItemMeta();
                leggingsMeta.setLocalizedName("Andaryll Leggings");
                leggings.setItemMeta(leggingsMeta);
                menu.setItem(22, leggings);
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
            }else{
                return true;
            }

            // open specific menu for material type
            player.openInventory(menu);
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

            // get material object
            ItemStack item = ice.getWhoClicked().
                             getInventory().getItemInMainHand();

            ItemStack newItem;
            String oldName = item.getItemMeta().getLocalizedName();
            String newName;
            // set unfinished item to handled iron item
            if(oldName.equals("§4[Heated] §fIron Ingot")){
                newItem = new ItemStack(Material.IRON_INGOT);
                newName = "Unfinished Iron ";
            }
            // set unfinished item to handled Andaryll item
            else if(oldName.equals("§4[Heated] §fAndaryll")){
                newItem = new ItemStack(Material.GOLD_INGOT);
                newName = "Unfinished Andaryll ";
            }
            // set unfinished item to handled Serasyll item
            else if(oldName.equals("§4[Heated] §fSerasyll")){
                newItem = new ItemStack(Material.GLOWSTONE_DUST);
                newName = "Unfinished Serasyll ";
            }
            // set unfinished item to handled Mythril
            else if(oldName.equals("§4[Heated] §fMythril")){
                newItem = new ItemStack(Material.EMERALD);
                newName = "Unfinished Mythril ";
            }
            // set unfinished item to handled Adamantium
            else if(oldName.equals("§4[Heated] §fAdamantium")){
                newItem = new ItemStack(Material.DIAMOND);
                newName = "Unfinished Adamantium ";
            }else{
                return true;
            }

            // for use with shaping - times hammered, quality points, t/f needs
            //   to be hammered
            String hiddenMeta = "§0§0§t";

            // clicked on slot containing helmet, prepare ot give helmet with
            //   hidden meta data (for use in shaping phase).
            //   Can't use Adamantium or Serasyll as they're only for weapons,
            //   tools
            if(ice.getSlot() == 4 && !oldName.equals("§4[Heated] §fSerasyll") &&
                    !oldName.equals("§4[Heated] §fAdamantium")){
                newName = newName + "Helmet" + hiddenMeta;
            }
            // clicked on slot containing helmet, prepare to give chestplt. with
            //   hidden meta data (for use in shaping phase).
            //   Can't use Adamantium or Serasyll as they're only for weapons,
            //   tools
            else if(ice.getSlot() == 13 &&
                    !oldName.equals("§4[Heated] §fSerasyll") &&
                    !oldName.equals("§4[Heated] §fAdamantium")){
                newName = newName + "Chestplate" + hiddenMeta;
            }
            // clicked on slot containing helmet, prepare to give leggings with
            //   hidden meta data (for use in shaping phase).
            //   Can't use Adamantium or Serasyll as they're only for weapons,
            //   tools
            else if(ice.getSlot() == 22 &&
                    !oldName.equals("§4[Heated] §fSerasyll") &&
                    !oldName.equals("§4[Heated] §fAdamantium")){
                newName = newName + "Leggings" + hiddenMeta;
            }
            // clicked on slot containing sword, prepare to give sword with
            //   hidden meta data (for use in shaping phase).
            //   Can't use Andaryll or Mythril as they're only for armor
            else if(ice.getSlot() == 11 &&
                    !oldName.equals("§4[Heated] §fAndaryll") &&
                    !oldName.equals("§4[Heated] §fMythril")){
                // remove item from inventory
                newName = newName + "Sword" + hiddenMeta;
            }else{
                return true;
            }

            // remove item from inventory
            if(item.getAmount() == 1){
                player.getInventory().remove(item);
            }else{
                item.setAmount(item.getAmount() - 1);
            }

            // play anvil sound for successful item creation
            player.playSound(player.getLocation(),
                    Sound.BLOCK_ANVIL_HIT, 20, 0);

            // close menu when successfully made choice
            ice.getWhoClicked().closeInventory();

            player.sendMessage("You begin to hammer the material into shape.");

            // create delay of 40 ticks or 2 seconds for player to receive
            //   completed item
            final String finalNewName = newName;
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                new Runnable() {
                    @Override
                    public void run() {
                        // give new meta data to new item and give to player
                        ItemMeta newMeta = newItem.getItemMeta();
                        newMeta.setLocalizedName(finalNewName);
                        newItem.setItemMeta(newMeta);
                        player.getInventory().addItem(newItem);
                    }
                }, 40);
        }

        return true;
    }
}
