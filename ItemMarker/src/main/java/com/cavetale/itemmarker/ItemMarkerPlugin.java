package com.cavetale.itemmarker;

import org.bukkit.plugin.java.JavaPlugin;

public final class ItemMarkerPlugin extends JavaPlugin {
    private ItemMarkerCommand itemMarkerCommand;

    @Override
    public void onEnable() {
        this.itemMarkerCommand = new ItemMarkerCommand(this);
        getCommand("itemmarker").setExecutor(this.itemMarkerCommand);
        getServer().getPluginManager()
            .registerEvents(new EventListener(), this);
    }
}
