package com.cavetale.itemmarker;

import com.cavetale.dirty.Dirty;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public final class ItemMarker {
    public static final String ITEM_ID_KEY = "cavetale.id";
    public static final String ITEM_OWNER_KEY = "cavetale.owner";
    private ItemMarker() { }

    // --- Marker methods

    public static ItemStack setMarker(@NonNull ItemStack item,
                                      @NonNull String key,
                                      @NonNull Object value) {
        item = Dirty.toCraftItemStack(item);
        Optional<Object> tag = Dirty.accessItemNBT(item, true);
        Dirty.setNBT(tag, key, value);
        return item;
    }

    public static <T> Optional<T> getMarker(@NonNull ItemStack item,
                                            @NonNull String key,
                                            @NonNull Class<T> clazz) {
        Optional<Object> tag = Dirty.accessItemNBT(item, false);
        if (!tag.isPresent()) return Optional.empty();
        tag = Dirty.getNBT(tag, key);
        Object result = Dirty.fromNBT(tag);
        if (!clazz.isInstance(result)) return Optional.empty();
        return Optional.of(clazz.cast(result));
    }

    // --- Custom ID methods

    public static ItemStack setCustomId(@NonNull ItemStack item,
                                        @NonNull String customId) {
        return setMarker(item, ITEM_ID_KEY, customId);
    }

    public static Optional<String> getCustomId(@NonNull ItemStack item) {
        return getMarker(item, ITEM_ID_KEY, String.class);
    }

    public static boolean hasCustomId(@NonNull ItemStack item,
                                      @NonNull String customId) {
        return customId.equals(getCustomId(item).orElse(null));
    }

    // --- Owner marker

    public static ItemStack setOwner(@NonNull ItemStack item,
                                     @NonNull UUID owner) {
        return setMarker(item, ITEM_OWNER_KEY, owner.toString());
    }

    public static Optional<UUID> getOwner(@NonNull ItemStack item) {
        String val = getMarker(item, ITEM_OWNER_KEY, String.class)
            .orElse(null);
        if (val == null) return Optional.empty();
        try {
            return Optional.of(UUID.fromString(val));
        } catch (IllegalArgumentException iae) {
            return Optional.empty();
        }
    }

    public static boolean isOwner(@NonNull ItemStack item,
                                  @NonNull UUID owner) {
        return owner.equals(getOwner(item).orElse(null));
    }
}
