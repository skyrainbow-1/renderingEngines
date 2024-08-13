package com.cavetale.itemmarker;

import java.util.Arrays;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
final class ItemMarkerCommand implements CommandExecutor {
    private final ItemMarkerPlugin plugin;

    final class ItemMarkerException extends Exception {
        ItemMarkerException(@NonNull final String message) {
            super(message);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String alias,
                             String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null) {
            this.plugin.getLogger().info("Player expected");
            return true;
        }
        if (args.length == 0) return false;
        try {
            return onCommand(player, args[0], Arrays
                             .copyOfRange(args, 1, args.length));
        } catch (ItemMarkerException ime) {
            player.sendMessage(ChatColor.RED + ime.getMessage());
            return true;
        }
    }

    private boolean onCommand(@NonNull Player player,
                              @NonNull String cmd,
                              @NonNull String[] args)
        throws ItemMarkerException {
        switch (cmd) {
        case "info": {
            ItemStack item = player.getInventory().getItemInHand();
            if (item == null) {
                throw new ItemMarkerException("No item in hand!");
            }
            player.sendMessage("Item=" + item.getType()
                               + " " + ItemMarker.ITEM_ID_KEY + "="
                               + ItemMarker.getCustomId(item)
                               + " " + ItemMarker.ITEM_OWNER_KEY + "="
                               + ItemMarker.getOwner(item));
            return true;
        }
        default: return false;
        }
    }
}
