package com.teamaurora.bayou_blues.core;

import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import com.teamaurora.bayou_blues.core.registry.BayouBluesItems;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.StrippingRegistry;
import gg.moonflower.pollen.api.registry.client.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

public class BayouBlues {
    public static final String MOD_ID = "bayou_blues";
    public static final Platform PLATFORM = Platform.builder(MOD_ID)
            .clientInit(BayouBlues::onClientInit)
            .clientPostInit(BayouBlues::onClientPostInit)
            .commonInit(BayouBlues::onCommonInit)
            .commonPostInit(BayouBlues::onCommonPostInit)
            .dataInit(BayouBlues::onDataInit)
            .build();

    public static void onClientInit() {
        RenderTypeRegistry.register(BayouBluesBlocks.HANGING_CYPRESS_LEAVES.get(), RenderType.cutoutMipped());

        RenderTypeRegistry.register(BayouBluesBlocks.BEARD_MOSS_BLOCK.get(), RenderType.cutout());
        RenderTypeRegistry.register(BayouBluesBlocks.BEARD_MOSS.get(), RenderType.cutout());
    }

    public static void onClientPostInit(Platform.ModSetupContext ctx) {
    }

    public static void onCommonInit() {
        BayouBluesBlocks.BLOCKS.register(BayouBlues.PLATFORM);
        BayouBluesItems.ITEMS.register(BayouBlues.PLATFORM);

        StrippingRegistry.register(BayouBluesBlocks.CYPRESS_LOG.get(), BayouBluesBlocks.STRIPPED_CYPRESS_LOG.get());
        StrippingRegistry.register(BayouBluesBlocks.CYPRESS_WOOD.get(), BayouBluesBlocks.STRIPPED_CYPRESS_WOOD.get());
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx) {
    }

    public static void onDataInit(Platform.DataSetupContext ctx) {
    }
}
