package com.teamaurora.bayou_blues.core;

import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import com.teamaurora.bayou_blues.core.registry.BayouBluesItems;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.StrippingRegistry;
import gg.moonflower.pollen.api.registry.client.ColorRegistry;
import gg.moonflower.pollen.api.registry.client.RenderTypeRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * @author ebo2022
 */
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
        ColorRegistry.register((state, level, pos, tintIndex) -> {return level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor();}, BayouBluesBlocks.CYPRESS_LEAVES, BayouBluesBlocks.HANGING_CYPRESS_LEAVES);
        ColorRegistry.register((stack, tintIndex) -> {return FoliageColor.getDefaultColor();}, BayouBluesBlocks.CYPRESS_LEAVES, BayouBluesBlocks.HANGING_CYPRESS_LEAVES);
        ColorRegistry.register((state, level, pos, tintIndex) -> {return level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.get(0.5D, 1.0D);}, BayouBluesBlocks.GIANT_FERN);
        ColorRegistry.register((stack, tintIndex) -> {return GrassColor.get(0.5D, 1.0D);}, BayouBluesBlocks.GIANT_FERN);
        ColorRegistry.register((state, level, pos, tintIndex) -> {return level != null && pos != null ? 2129968 : 7455580;}, BayouBluesBlocks.BLUE_LILY, BayouBluesBlocks.LIGHT_GRAY_LILY, BayouBluesBlocks.CYAN_LILY, BayouBluesBlocks.LIGHT_BLUE_LILY, BayouBluesBlocks.MAGENTA_LILY, BayouBluesBlocks.PINK_LILY, BayouBluesBlocks.PURPLE_LILY, BayouBluesBlocks.WHITE_LILY);
    }

    public static void onClientPostInit(Platform.ModSetupContext ctx) {
        ctx.enqueueWork(() -> {
            RenderTypeRegistry.register(BayouBluesBlocks.HANGING_CYPRESS_LEAVES.get(), RenderType.cutoutMipped());

            RenderTypeRegistry.register(BayouBluesBlocks.ALGAE.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.ALGAE_THATCH.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.ALGAE_THATCH_SLAB.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.ALGAE_THATCH_STAIRS.get(), RenderType.cutout());

            RenderTypeRegistry.register(BayouBluesBlocks.CYPRESS_KNEE.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.LARGE_CYPRESS_KNEE.get(), RenderType.cutout());

            RenderTypeRegistry.register(BayouBluesBlocks.BLUE_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.LIGHT_GRAY_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.CYAN_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.LIGHT_BLUE_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.MAGENTA_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.PINK_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.PURPLE_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.WHITE_LILY.get(), RenderType.cutout());



            RenderTypeRegistry.register(BayouBluesBlocks.BEARD_MOSS_BLOCK.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.BEARD_MOSS.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.GIANT_FERN.get(), RenderType.cutout());
        });
    }

    public static void onCommonInit() {
        BayouBluesItems.ITEMS.register(BayouBlues.PLATFORM);
        BayouBluesBlocks.BLOCKS.register(BayouBlues.PLATFORM);
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx) {
        ctx.enqueueWork(() -> {
            StrippingRegistry.register(BayouBluesBlocks.CYPRESS_LOG.get(), BayouBluesBlocks.STRIPPED_CYPRESS_LOG.get());
            StrippingRegistry.register(BayouBluesBlocks.CYPRESS_WOOD.get(), BayouBluesBlocks.STRIPPED_CYPRESS_WOOD.get());
        });
    }

    public static void onDataInit(Platform.DataSetupContext ctx) {
    }
}
