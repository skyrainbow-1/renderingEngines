package com.cavetale.itemmarker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * Call every time a player right clicks an item with a custom ID in
 * their hand or off-hand.
 * Cancelling this event will also cancel the causing event, which is
 * either PlayerInteractEvent or PlayerInteractEvent.
 */
@RequiredArgsConstructor @Getter
public final class CustomItemUseEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final ItemStack item;
    private final String customId;
    private final EquipmentSlot hand;
    private final Cancellable event;
    @Setter private boolean cancelled = false;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
