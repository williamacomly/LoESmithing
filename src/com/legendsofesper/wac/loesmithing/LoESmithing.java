//***********************************************
//
// File:    LoESmithing.java
// Package: com.legendsofesper.wac.loesmithing
// Class:   LoESmithing
//
// Author:  William Comly <williamacomly@gmail.com>
//
//***********************************************

package com.legendsofesper.wac.loesmithing;

import com.legendsofesper.wac.loesmithing.tools.SwordCreation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of LoESmithing plugin. determined what plugin does on enable and
 * disable. Also registers and activates events and commands contained
 * elsewhere.
 *
 * @version 28-Mar-2018
 */
public class LoESmithing extends JavaPlugin {
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        pluginManager = Bukkit.getServer().getPluginManager();

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands(){
        this.getCommand("giveore").setExecutor(new GiveOre());
    }

    private void registerEvents(){
        pluginManager.registerEvents(new SwordCreation(), this);
        pluginManager.registerEvents(new GeneralCreation(), this);
    }
}
