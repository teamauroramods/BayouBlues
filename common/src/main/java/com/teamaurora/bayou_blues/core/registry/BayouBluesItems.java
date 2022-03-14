package com.teamaurora.bayou_blues.core.registry;

import com.teamaurora.bayou_blues.common.item.AlgaeItem;
import com.teamaurora.bayou_blues.core.BayouBlues;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class BayouBluesItems {
    public static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, BayouBlues.MOD_ID);

    public static final Supplier<Item> ALGAE = registerItem("algae", () -> new AlgaeItem(BayouBluesBlocks.ALGAE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static Supplier<Item> registerItem(String id, Supplier<Item> item) {
         Supplier<Item> register = ITEMS.register(id, item);
         return register;
    }
}
