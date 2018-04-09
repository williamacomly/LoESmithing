//****************************************************
//
// File:    MaterialConstants.java
// Package: com.legendsofesper.wac.loesmithing
// Class:   MaterialConstants
//
// Author:  William Comly <williamacomly@gmail.com>
//
//****************************************************

package com.legendsofesper.wac.loesmithing;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Holds material 'constants' for all new materials in order to compare with
 * items in player's inventory. Used primarily for Shaping steps for all items.
 *
 * @author  William Comly
 * @version 9-Apr-2018
 */
public class MaterialConstants {
    // getters for item 'constants'
    public static ItemStack getIRON_INGOT(){
        ItemStack IRON_INGOT = new ItemStack(Material.IRON_INGOT);
        ItemMeta IRON_META = IRON_INGOT.getItemMeta();
        IRON_META.setLocalizedName("Iron Ingot");
        IRON_INGOT.setItemMeta(IRON_META);

        return IRON_INGOT;
    }

    public static ItemStack getANDARYLL() {
        ItemStack ANDARYLL = new ItemStack(Material.GOLD_INGOT);
        ItemMeta ANDARYLL_META = ANDARYLL.getItemMeta();
        ANDARYLL_META.setLocalizedName("Andaryll");
        ANDARYLL.setItemMeta(ANDARYLL_META);

        return ANDARYLL;
    }

    public static ItemStack getSERASYLL() {
        ItemStack SERASYLL = new ItemStack(Material.GLOWSTONE_DUST);
        ItemMeta SERASYLL_META = SERASYLL.getItemMeta();
        SERASYLL_META.setLocalizedName("Serasyll");
        SERASYLL.setItemMeta(SERASYLL_META);

        return SERASYLL;
    }

    public static ItemStack getMYTHRIL() {
        ItemStack MYTHRIL = new ItemStack(Material.EMERALD);
        ItemMeta MYTHRIL_META = MYTHRIL.getItemMeta();
        MYTHRIL_META.setLocalizedName("Mythril");
        MYTHRIL.setItemMeta(MYTHRIL_META);

        return MYTHRIL;
    }

    public static ItemStack getADAMANTIUM() {
        ItemStack ADAMANTIUM = new ItemStack(Material.DIAMOND);
        ItemMeta ADAMANTIUM_META = ADAMANTIUM.getItemMeta();
        ADAMANTIUM_META.setLocalizedName("Adamantium");
        ADAMANTIUM.setItemMeta(ADAMANTIUM_META);

        return ADAMANTIUM;
    }
}
