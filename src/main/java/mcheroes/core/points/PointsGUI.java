package mcheroes.core.points;

import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.inventorygui.PaginationPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PointsGUI extends PaginationPanel {
    private final Map<UUID, Integer> pointMap;
    private final LocaleAdapter locale;
    private final InventoryGUI gui;

    public PointsGUI(InventoryGUI gui, LocaleAdapter locale, Map<UUID, Integer> pointMap) {
        super(gui);

        this.gui = gui;
        this.pointMap = pointMap;
        this.locale = locale;
        addSlots(9, 54);
    }

    public void open(Player player) {
        updatePoints();
        gui.open(player);
    }

    public void updatePoints() {
        removePagedItems(getItems());
        final List<ItemStack> buttons = new ArrayList<>();

        List<Map.Entry<UUID, Integer>> entryList = new ArrayList<>(pointMap.entrySet());
        entryList.sort(Map.Entry.<UUID, Integer>comparingByValue().reversed());

        for (int i = 0; i < entryList.size(); i++) {
            final Map.Entry<UUID, Integer> entry = entryList.get(i);
            buttons.add(generateHead(entry.getKey(), i + 1, entry.getValue()));
        }
        addPagedItems(buttons);
    }

    private ItemStack generateHead(UUID player, int placement, int points) {
        final ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(player));
        meta.displayName(Messages.POINTS_GUI_HEAD_NAME.build(locale, placement, Bukkit.getOfflinePlayer(player)));
        meta.lore(Messages.POINTS_GUI_HEAD_LORE.build(locale, points));
        item.setItemMeta(meta);

        return item;
    }
}
