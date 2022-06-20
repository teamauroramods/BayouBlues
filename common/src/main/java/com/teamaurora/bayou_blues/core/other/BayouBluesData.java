package com.teamaurora.bayou_blues.core.other;

import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import com.teamaurora.bayou_blues.core.registry.BayouBluesItems;
import gg.moonflower.pollen.api.registry.StrippingRegistry;
import gg.moonflower.pollen.api.registry.content.CompostablesRegistry;
import gg.moonflower.pollen.api.registry.content.FlammabilityRegistry;
import gg.moonflower.pollen.api.registry.content.FurnaceFuelRegistry;

public class BayouBluesData {
    public static void init() {
        registerStrippables();
        registerCompostables();
        registerFlammables();
        registerFurnaceFuel();
    }

    public static void registerStrippables() {
        StrippingRegistry.register(BayouBluesBlocks.CYPRESS_LOG.get(), BayouBluesBlocks.STRIPPED_CYPRESS_LOG.get());
        StrippingRegistry.register(BayouBluesBlocks.CYPRESS_WOOD.get(), BayouBluesBlocks.STRIPPED_CYPRESS_WOOD.get());
    }

    public static void registerCompostables() {
        CompostablesRegistry.register(BayouBluesBlocks.CYPRESS_LEAVES.get(), 0.3F);
        CompostablesRegistry.register(BayouBluesBlocks.CYPRESS_SAPLING.get(), 0.3F);
        CompostablesRegistry.register(BayouBluesBlocks.CYPRESS_LEAF_CARPET.get(), 0.3F);
        CompostablesRegistry.register(BayouBluesItems.GOOSEBERRIES.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.ALGAE.get(), 0.15F);
        CompostablesRegistry.register(BayouBluesBlocks.ALGAE_THATCH.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.ALGAE_THATCH_SLAB.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.ALGAE_THATCH_STAIRS.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.BEARD_MOSS.get(), 0.3F);
        CompostablesRegistry.register(BayouBluesBlocks.BEARD_MOSS_BLOCK.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.BLUE_LILY.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.LIGHT_BLUE_LILY.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.CYAN_LILY.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.LIGHT_GRAY_LILY.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.WHITE_LILY.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.MAGENTA_LILY.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.PINK_LILY.get(), 0.65F);
        CompostablesRegistry.register(BayouBluesBlocks.PURPLE_LILY.get(), 0.65F);
    }

    public static void registerFlammables() {
        FlammabilityRegistry.register(BayouBluesBlocks.CYPRESS_LEAVES.get(), 30, 60);
        FlammabilityRegistry.register(BayouBluesBlocks.HANGING_CYPRESS_LEAVES.get(), 30, 60);
        FlammabilityRegistry.register(BayouBluesBlocks.CYPRESS_LOG.get(), 5, 5);
        FlammabilityRegistry.register(BayouBluesBlocks.CYPRESS_WOOD.get(), 5, 5);
        FlammabilityRegistry.register(BayouBluesBlocks.STRIPPED_CYPRESS_LOG.get(), 5, 5);
        FlammabilityRegistry.register(BayouBluesBlocks.STRIPPED_CYPRESS_WOOD.get(), 5, 5);
        FlammabilityRegistry.register(BayouBluesBlocks.CYPRESS_PLANKS.get(), 5, 20);
        FlammabilityRegistry.register(BayouBluesBlocks.CYPRESS_SLAB.get(), 5, 20);
        FlammabilityRegistry.register(BayouBluesBlocks.CYPRESS_STAIRS.get(), 5, 20);
        FlammabilityRegistry.register(BayouBluesBlocks.CYPRESS_FENCE.get(), 5, 20);
        FlammabilityRegistry.register(BayouBluesBlocks.CYPRESS_FENCE_GATE.get(), 5, 20);
        FlammabilityRegistry.register(BayouBluesBlocks.CYPRESS_LEAF_CARPET.get(), 30, 60);
        FlammabilityRegistry.register(BayouBluesBlocks.CYPRESS_BRANCH.get(), 60, 100);
        FlammabilityRegistry.register(BayouBluesBlocks.BEARD_MOSS.get(), 15, 100);
        FlammabilityRegistry.register(BayouBluesBlocks.BEARD_MOSS_BLOCK.get(), 15, 100);
        FlammabilityRegistry.register(BayouBluesBlocks.ALGAE_THATCH.get(), 60, 20);
        FlammabilityRegistry.register(BayouBluesBlocks.ALGAE_THATCH_SLAB.get(), 60, 20);
        FlammabilityRegistry.register(BayouBluesBlocks.ALGAE_THATCH_STAIRS.get(), 60, 20);
    }

    public static void registerFurnaceFuel() {
        FurnaceFuelRegistry.register(BayouBluesBlocks.BEARD_MOSS.get(), 800);
        FurnaceFuelRegistry.register(BayouBluesBlocks.BEARD_MOSS_BLOCK.get(), 800);
    }
}
