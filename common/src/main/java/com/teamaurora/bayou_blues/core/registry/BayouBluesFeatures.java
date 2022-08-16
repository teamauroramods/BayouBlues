package com.teamaurora.bayou_blues.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamaurora.bayou_blues.common.world.decorators.CypressBranchTreeDecorator;
import com.teamaurora.bayou_blues.common.world.decorators.HangingCypressLeavesTreeDecorator;
import com.teamaurora.bayou_blues.common.world.feature.CypressFeature;
import com.teamaurora.bayou_blues.common.world.feature.MegaCypressFeature;
import com.teamaurora.bayou_blues.core.BayouBlues;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;


import java.util.List;
import java.util.function.Supplier;

public class BayouBluesFeatures {

    public static final PollinatedRegistry<Feature<?>> FEATURES = PollinatedRegistry.create(Registry.FEATURE, BayouBlues.MOD_ID);
    public static final PollinatedRegistry<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = PollinatedRegistry.create(Registry.TREE_DECORATOR_TYPES, BayouBlues.MOD_ID);

    public static final Supplier<Feature<TreeConfiguration>> CYPRESS_TREE = FEATURES.register("cypress_tree", () -> new CypressFeature(TreeConfiguration.CODEC));
    public static final Supplier<Feature<TreeConfiguration>> MEGA_CYPRESS_TREE = FEATURES.register("mega_cypress_tree", () -> new MegaCypressFeature(TreeConfiguration.CODEC));

    public static final Supplier<TreeDecoratorType<?>> HANGING_CYPRESS_LEAVES = TREE_DECORATOR_TYPES.register("hanging_cypress_leaves", () -> new TreeDecoratorType<>(HangingCypressLeavesTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> CYPRESS_BRANCH = TREE_DECORATOR_TYPES.register("cypress_branch", () -> new TreeDecoratorType<>(CypressBranchTreeDecorator.CODEC));

    public static final class BlockStates {
        public static final BlockState CYPRESS_LOG = BayouBluesBlocks.CYPRESS_LOG.get().defaultBlockState();
        public static final BlockState CYPRESS_LEAVES = BayouBluesBlocks.CYPRESS_LEAVES.get().defaultBlockState();
        public static final BlockState HANGING_CYPRESS_LEAVES = BayouBluesBlocks.HANGING_CYPRESS_LEAVES.get().defaultBlockState();

        public static final BlockState ALGAE = BayouBluesBlocks.ALGAE.get().defaultBlockState();
        public static final BlockState CYPRESS_LEAF_CARPET = BayouBluesBlocks.CYPRESS_LEAF_CARPET.get().defaultBlockState();

        public static final BlockState BLUE_LILY = BayouBluesBlocks.BLUE_LILY.get().defaultBlockState();
        public static final BlockState CYAN_LILY = BayouBluesBlocks.CYAN_LILY.get().defaultBlockState();
        public static final BlockState LIGHT_BLUE_LILY = BayouBluesBlocks.LIGHT_BLUE_LILY.get().defaultBlockState();
        public static final BlockState LIGHT_GRAY_LILY = BayouBluesBlocks.LIGHT_GRAY_LILY.get().defaultBlockState();
        public static final BlockState WHITE_LILY = BayouBluesBlocks.WHITE_LILY.get().defaultBlockState();
        public static final BlockState PINK_LILY = BayouBluesBlocks.PINK_LILY.get().defaultBlockState();
        public static final BlockState MAGENTA_LILY = BayouBluesBlocks.MAGENTA_LILY.get().defaultBlockState();
        public static final BlockState PURPLE_LILY = BayouBluesBlocks.PURPLE_LILY.get().defaultBlockState();
        public static final BlockState LILY_PAD = Blocks.LILY_PAD.defaultBlockState();

        public static final BlockState GRASS = Blocks.GRASS.defaultBlockState();
        public static final BlockState TALL_GRASS = Blocks.TALL_GRASS.defaultBlockState();
        public static final BlockState FERN = Blocks.FERN.defaultBlockState();
        public static final BlockState LARGE_FERN = Blocks.LARGE_FERN.defaultBlockState();
        public static final BlockState GIANT_FERN = BayouBluesBlocks.GIANT_FERN.get().defaultBlockState();
    }

    public static final class Configs {
        public static final TreeConfiguration CYPRESS_TREE_CONFIG_GROWN = (new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BayouBluesBlocks.CYPRESS_LOG.get()),
                new StraightTrunkPlacer(0, 0, 0),
                new SimpleStateProvider(BayouBluesFeatures.BlockStates.CYPRESS_LEAVES),
                new BlobFoliagePlacer(UniformInt.of(0, 0), UniformInt.of(0, 0), 0),
                new TwoLayersFeatureSize(0, 0, 0)
        )).ignoreVines().decorators(ImmutableList.of(HangingCypressLeavesTreeDecorator.DECORATOR, CypressBranchTreeDecorator.DECORATOR)).build();
    }

    public static final class Configured {

        public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CYPRESS_GROWN = configure("cypress_grown", CYPRESS_TREE.get(), Configs.CYPRESS_TREE_CONFIG_GROWN);
        public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> MEGA_CYPRESS_GROWN = configure("mega_cypress_grown", MEGA_CYPRESS_TREE.get(), Configs.CYPRESS_TREE_CONFIG_GROWN);

        public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> configure(String id, F feature, FC featureConfiguration) {
            return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, BayouBlues.MOD_ID  + ":" + id, new ConfiguredFeature<>(feature, featureConfiguration));
        }
    }

    public static final class Placed {

        public static Holder<PlacedFeature> register(String string, Holder<? extends ConfiguredFeature<?, ?>> holder, PlacementModifier... list) {
            return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, BayouBlues.MOD_ID + ":" + string, new PlacedFeature(Holder.hackyErase(holder), List.of(list)));
        }

        public static Holder<PlacedFeature> register(String string, Holder<? extends ConfiguredFeature<?, ?>> holder, List<PlacementModifier> list) {
            return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, BayouBlues.MOD_ID + ":" + string, new PlacedFeature(Holder.hackyErase(holder), List.copyOf(list)));
        }
    }
}