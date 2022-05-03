package com.teamaurora.bayou_blues.core;

import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import com.teamaurora.bayou_blues.core.registry.BayouBluesEntities;
import com.teamaurora.bayou_blues.core.registry.BayouBluesFeatures;
import com.teamaurora.bayou_blues.core.registry.BayouBluesItems;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.StrippingRegistry;
import gg.moonflower.pollen.api.registry.client.ColorRegistry;
import gg.moonflower.pollen.api.registry.client.RenderTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;

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
        ColorRegistry.register((state, level, pos, tintIndex) -> {return level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor();}, BayouBluesBlocks.CYPRESS_LEAVES, BayouBluesBlocks.HANGING_CYPRESS_LEAVES, BayouBluesBlocks.CYPRESS_LEAF_CARPET);
        ColorRegistry.register((stack, tintIndex) -> {return FoliageColor.getDefaultColor();}, BayouBluesBlocks.CYPRESS_LEAVES, BayouBluesBlocks.HANGING_CYPRESS_LEAVES, BayouBluesBlocks.CYPRESS_LEAF_CARPET);
        ColorRegistry.register((state, level, pos, tintIndex) -> {return level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.get(0.5D, 1.0D);}, BayouBluesBlocks.GIANT_FERN);
        ColorRegistry.register((stack, tintIndex) -> {return GrassColor.get(0.5D, 1.0D);}, BayouBluesBlocks.GIANT_FERN);
        ColorRegistry.register((state, level, pos, tintIndex) -> {return level != null && pos != null ? 2129968 : 7455580;}, BayouBluesBlocks.BLUE_LILY, BayouBluesBlocks.LIGHT_GRAY_LILY, BayouBluesBlocks.CYAN_LILY, BayouBluesBlocks.LIGHT_BLUE_LILY, BayouBluesBlocks.MAGENTA_LILY, BayouBluesBlocks.PINK_LILY, BayouBluesBlocks.PURPLE_LILY, BayouBluesBlocks.WHITE_LILY);
    }

    public static void onClientPostInit(Platform.ModSetupContext ctx) {
        ctx.enqueueWork(() -> {
            RenderTypeRegistry.register(BayouBluesBlocks.HANGING_CYPRESS_LEAVES.get(), RenderType.cutoutMipped());
            RenderTypeRegistry.register(BayouBluesBlocks.CYPRESS_LEAF_CARPET.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.CYPRESS_SAPLING.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.POTTED_CYPRESS_SAPLING.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.ALGAE.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.ALGAE_THATCH.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.ALGAE_THATCH_SLAB.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.ALGAE_THATCH_STAIRS.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.CYPRESS_KNEE.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.LARGE_CYPRESS_KNEE.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.CYPRESS_BRANCH.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.BLUE_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.LIGHT_GRAY_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.CYAN_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.LIGHT_BLUE_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.MAGENTA_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.PINK_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.PURPLE_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.WHITE_LILY.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.BEARD_MOSS.get(), RenderType.cutout());
            RenderTypeRegistry.register(BayouBluesBlocks.GIANT_FERN.get(), RenderType.cutout());
        });
    }

    public static void onCommonInit() {
        BayouBluesItems.ITEMS.register(BayouBlues.PLATFORM);
        BayouBluesBlocks.BLOCKS.register(BayouBlues.PLATFORM);
        BayouBluesEntities.BOATS.register(PLATFORM);
        BayouBluesFeatures.FEATURES.register(BayouBlues.PLATFORM);
        BayouBluesFeatures.CONFIGURED_FEATURES.register(BayouBlues.PLATFORM);
        BayouBluesFeatures.TREE_DECORATOR_TYPES.register(BayouBlues.PLATFORM);
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx) {
        ctx.enqueueWork(() -> {

            /* Strippables */
            StrippingRegistry.register(BayouBluesBlocks.CYPRESS_LOG.get(), BayouBluesBlocks.STRIPPED_CYPRESS_LOG.get());
            StrippingRegistry.register(BayouBluesBlocks.CYPRESS_WOOD.get(), BayouBluesBlocks.STRIPPED_CYPRESS_WOOD.get());
            BayouBluesFeatures.Configured.registerConfiguredFeatures();

            /* Compostables */
            ComposterBlock.add(0.3F, BayouBluesBlocks.CYPRESS_LEAVES.get());
            ComposterBlock.add(0.3F, BayouBluesBlocks.CYPRESS_SAPLING.get());
            ComposterBlock.add(0.3F, BayouBluesBlocks.CYPRESS_LEAF_CARPET.get());

            ComposterBlock.add(0.65F, BayouBluesItems.GOOSEBERRIES.get());

            ComposterBlock.add(0.15F, BayouBluesBlocks.ALGAE.get());
            ComposterBlock.add(0.65F, BayouBluesBlocks.ALGAE_THATCH.get());
            ComposterBlock.add(0.65F, BayouBluesBlocks.ALGAE_THATCH_SLAB.get());
            ComposterBlock.add(0.65F, BayouBluesBlocks.ALGAE_THATCH_STAIRS.get());

            ComposterBlock.add(0.3F, BayouBluesBlocks.BEARD_MOSS.get());
            ComposterBlock.add(0.65F, BayouBluesBlocks.BEARD_MOSS_BLOCK.get());

            ComposterBlock.add(0.65F, BayouBluesBlocks.BLUE_LILY.get());
            ComposterBlock.add(0.65F, BayouBluesBlocks.LIGHT_BLUE_LILY.get());
            ComposterBlock.add(0.65F, BayouBluesBlocks.CYAN_LILY.get());
            ComposterBlock.add(0.65F, BayouBluesBlocks.LIGHT_GRAY_LILY.get());
            ComposterBlock.add(0.65F, BayouBluesBlocks.WHITE_LILY.get());
            ComposterBlock.add(0.65F, BayouBluesBlocks.MAGENTA_LILY.get());
            ComposterBlock.add(0.65F, BayouBluesBlocks.PINK_LILY.get());
            ComposterBlock.add(0.65F, BayouBluesBlocks.PURPLE_LILY.get());

            /* Flammables */
            FireBlock fireBlock = (FireBlock) Blocks.FIRE;

            fireBlock.setFlammable(BayouBluesBlocks.HANGING_CYPRESS_LEAVES.get(), 30, 60);
            fireBlock.setFlammable(BayouBluesBlocks.CYPRESS_LOG.get(), 5, 5);
            fireBlock.setFlammable(BayouBluesBlocks.CYPRESS_WOOD.get(), 5, 5);
            fireBlock.setFlammable(BayouBluesBlocks.STRIPPED_CYPRESS_LOG.get(), 5, 5);
            fireBlock.setFlammable(BayouBluesBlocks.STRIPPED_CYPRESS_WOOD.get(), 5, 5);
            fireBlock.setFlammable(BayouBluesBlocks.CYPRESS_PLANKS.get(), 5, 20);
            fireBlock.setFlammable(BayouBluesBlocks.CYPRESS_SLAB.get(), 5, 20);
            fireBlock.setFlammable(BayouBluesBlocks.CYPRESS_STAIRS.get(), 5, 20);
            fireBlock.setFlammable(BayouBluesBlocks.CYPRESS_FENCE.get(), 5, 20);
            fireBlock.setFlammable(BayouBluesBlocks.CYPRESS_FENCE_GATE.get(), 5, 20);
            fireBlock.setFlammable(BayouBluesBlocks.CYPRESS_LEAF_CARPET.get(), 30, 60);

            fireBlock.setFlammable(BayouBluesBlocks.CYPRESS_BRANCH.get(), 60, 100);

            fireBlock.setFlammable(BayouBluesBlocks.BEARD_MOSS.get(), 15, 100);
            fireBlock.setFlammable(BayouBluesBlocks.BEARD_MOSS_BLOCK.get(), 15, 100);

            fireBlock.setFlammable(BayouBluesBlocks.ALGAE_THATCH.get(), 60, 20);
            fireBlock.setFlammable(BayouBluesBlocks.ALGAE_THATCH_SLAB.get(), 60, 20);
            fireBlock.setFlammable(BayouBluesBlocks.ALGAE_THATCH_STAIRS.get(), 60, 20);
        });
    }

    public static void onDataInit(Platform.DataSetupContext ctx) {
    }

    public static ResourceLocation generateResourceLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
