package us.ajg0702.leaderboards;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import us.ajg0702.leaderboards.boards.StatEntry;
import us.ajg0702.leaderboards.boards.TimedType;
import us.ajg0702.leaderboards.boards.TopManager;
import us.ajg0702.leaderboards.displays.signs.BoardSign;
import us.ajg0702.leaderboards.displays.signs.SignManager;
import us.ajg0702.utils.common.Messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public class Listeners implements Listener {

    private final LeaderboardPlugin plugin;
    private final SignManager signManager;
    private final TopManager topManager;

    private static final LegacyComponentSerializer LEGACY_SIGN_SERIALIZER = LegacyComponentSerializer.builder().hexColors().useUnusualXRepeatedCharacterHexFormat().build();

    public Listeners(LeaderboardPlugin plugin) {
        this.plugin = plugin;
        this.signManager = plugin.getSignManager();
        this.topManager = plugin.getTopManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(!plugin.getAConfig().getBoolean("update-stats")) return;
        if(!plugin.getAConfig().getBoolean("update-on-join")) return;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> plugin.getCache().updatePlayerStats(e.getPlayer()));
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (block == null || !block.getType().name().contains("SIGN")) {
            return;
        }

        BoardSign sign = signManager.findSign(block.getLocation());

        if (sign == null) return;

        Inventory inventory = createInventory(sign);
        event.getPlayer().openInventory(inventory);

    }

    @EventHandler
    public void onInventoryInteractEvent(InventoryInteractEvent event) {
        if (!event.getView().getTitle().equals(plugin.getMessages().getString("gui.top.name"))) return;
        if (event.getWhoClicked() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(plugin.getMessages().getString("gui.top.name"))) return;
        if (event.getWhoClicked() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent event) {
        if (!event.getView().getTitle().equals(plugin.getMessages().getString("gui.top.name"))) return;
        if (event.getWhoClicked() instanceof Player) {
            event.setCancelled(true);
        }
    }

    private Inventory createInventory(BoardSign sign) {
        Inventory inventory = Bukkit.createInventory(null, 36, plugin.getMessages().getString("gui.top.name"));

        for (int i = 0; i < inventory.getSize(); ++i) {
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
            StatEntry statEntry = topManager.getStat(i + 1, sign.getBoard(), sign.getType());
            meta.setDisplayName(LEGACY_SIGN_SERIALIZER.serialize(plugin.getMessages().getComponent("gui.top.item-name",
                    "POSITION:"+statEntry.getPosition(), "NAME:"+statEntry.getPlayerName())));
            meta.setOwner(statEntry.getPlayerName());
            meta.setLore(getFormattedLore(sign, statEntry));

            itemStack.setItemMeta(meta);

            inventory.setItem(i, itemStack);
        }

        return inventory;
    }

    private List<String> getFormattedLore(BoardSign sign, StatEntry r) {
        Messages msgs = plugin.getMessages();

        String name = "";
        if(signManager.getNames().containsKey(sign.getBoard())) {
            name = signManager.getNames().get(sign.getBoard());
        }

        String[] placeholders = Arrays.asList(
                "POSITION:"+sign.getPosition(),
                "NAME:"+r.getPlayerName(),
                "VALUE:"+r.getScorePretty(),
                "FVALUE:"+r.getScoreFormatted(),
                "TVALUE:"+r.getTime(),
                "VALUENAME:"+Matcher.quoteReplacement(name),
                "TIMEDTYPE:"+sign.getType().lowerName()
        ).toArray(new String[]{});

        List<Component> lines;
        lines = msgs.getComponentList("gui.top.default", placeholders);


        List<String> pLines = new ArrayList<>();
        lines.forEach(c -> pLines.add(LEGACY_SIGN_SERIALIZER.serialize(c)));

        return pLines;
    }


}
