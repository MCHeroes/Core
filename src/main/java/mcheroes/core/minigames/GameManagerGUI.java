package mcheroes.core.minigames;

import mcheroes.core.CoreProvider;
import mcheroes.core.api.minigame.Minigame;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import mcheroes.core.minigames.actions.SetCurrentMinigameAction;
import mcheroes.core.utils.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.inventorygui.ItemButton;

import java.util.Collection;

public class GameManagerGUI extends InventoryGUI {
    private final LocaleAdapter locale;
    private final MinigameManager manager;
    private final MinigameRegistry registry;

    public GameManagerGUI(LocaleAdapter locale, MinigameManager manager, MinigameRegistry registry) {
        super(Bukkit.createInventory(null, 36, Messages.GAME_MANAGER_TITLE.build(locale)));
        this.locale = locale;
        this.manager = manager;
        this.registry = registry;
    }

    public void build() {
        clear();
        fill(0, 9, InventoryGUI.FILLER);

        if (manager.getCurrentMinigame() != null) {
            addButton(22, ItemButton.create(ItemStackBuilder
                            .of(Material.RED_WOOL)
                            .name(Messages.GAME_MANAGER_STOP_ITEM_NAME.build(locale, manager.getCurrentMinigame()))
                            .lore(Messages.GAME_MANAGER_STOP_ITEM_LORE.build(locale))
                            .build(),
                    e -> {
                        final Minigame found = registry.get(manager.getCurrentMinigame());
                        if (found == null) throw new RuntimeException("Failed to find current/loaded minigame?!");

                        found.stop();
                        CoreProvider.get().getActionManager().run(new SetCurrentMinigameAction(null));
                        e.getWhoClicked().sendMessage(Messages.GAME_MANAGER_STOP_SUCCESS.build(locale));

                        build();
                    }));

            return;
        }

        final Collection<Minigame> minigames = registry.getMinigames().values();
        if (minigames.isEmpty()) return;

        for (Minigame minigame : minigames) {
            add(minigame);
        }
    }

    private void add(Minigame minigame) {
        final int slot = getInventory().firstEmpty();

        addButton(slot, ItemButton.create(ItemStackBuilder
                        .of(Material.PAPER)
                        .name(Messages.GAME_MANAGER_MINIGAME_ITEM_NAME.build(locale, minigame.getId()))
                        .lore(Messages.GAME_MANAGER_MINIGAME_ITEM_LORE.build(locale))
                        .build(),
                e -> {
                    CoreProvider.get().getActionManager().run(new SetCurrentMinigameAction(minigame));
                    e.getWhoClicked().sendMessage(Messages.GAME_MANAGER_START_SUCCESS.build(locale, minigame.getId()));

                    build();
                }));
    }
}
