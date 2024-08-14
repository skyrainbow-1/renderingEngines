package com.cavetale.itemmarker;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * Conveniently listen for some item related actions which are
 * otherwise inconvenient to listen for.
 */
final class EventListener implements Listener {
    void onRightClick(@NonNull Player player,
                      @NonNull ItemStack item,
                      @NonNull EquipmentSlot hand,
                      @NonNull Cancellable cause) {
        String customId = ItemMarker.getCustomId(item).orElse(null);
        if (customId == null) return;
        CustomItemUseEvent event =
            new CustomItemUseEvent(player, item, customId, hand, cause);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            cause.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK
            && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (!event.hasItem()) return;
        onRightClick(event.getPlayer(), event.getItem(),
                     event.getHand(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        final EquipmentSlot hand = event.getHand();
        if (hand == EquipmentSlot.HAND) {
            onRightClick(player, player.getInventory().getItemInMainHand(),
                         hand, event);
        } else if (hand == EquipmentSlot.OFF_HAND) {
            onRightClick(player, player.getInventory().getItemInOffHand(),
                         hand, event);
        }
    }
}
