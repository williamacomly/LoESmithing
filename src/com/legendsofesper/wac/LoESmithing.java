//***********************************************
//
// File:    LoESmithing.java
// Package: ---
// Class:   LoESmithing
//
// Author:  William Comly <williamacomly@gmail.com>
//
//***********************************************

package com.legendsofesper.wac;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of LoESmithing plugin. determined what plugin does on enable and disable. Also registers and activates
 * events and commands contained elsewhere.
 *
 * @author  William Comly <williamacomly@gmail.com>
 * @version 28-Mar-2018
 */
public class LoESmithing extends JavaPlugin {
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        System.out.println("LoESmithing[0.0]: ENABLED");
    }

    @Override
    public void onDisable() {
        System.out.println("LoESmithing[0.0]: DISABLED");
    }

    private void registerCommands(){

    }

    private void registerEvents(){

    }
}
