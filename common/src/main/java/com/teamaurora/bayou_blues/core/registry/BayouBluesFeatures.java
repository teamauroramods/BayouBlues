package com.teamaurora.bayou_blues.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamaurora.bayou_blues.common.levelgen.decorators.*;
import com.teamaurora.bayou_blues.common.levelgen.feature.*;
import com.teamaurora.bayou_blues.core.BayouBlues;
import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.function.Supplier;

public class BayouBluesFeatures {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void load(Platform platform) {
        LOGGER.debug("Registered to platform");
        FEATURES.register(platform);
        TREE_DECORATOR_TYPES.register(platform);
    }

    public static final PollinatedRegistry<Feature<?>> FEATURES = PollinatedRegistry.create(Registry.FEATURE, BayouBlues.MOD_ID);
    public static final PollinatedRegistry<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = PollinatedRegistry.create(Registry.TREE_DECORATOR_TYPES, BayouBlues.MOD_ID);

    public static final Supplier<Feature<TreeConfiguration>> CYPRESS_TREE = FEATURES.register("cypress_tree", () -> new CypressFeature(TreeConfiguration.CODEC));
    public static final Supplier<Feature<TreeConfiguration>> MEGA_CYPRESS_TREE = FEATURES.register("mega_cypress_tree", () -> new MegaCypressFeature(TreeConfiguration.CODEC));
    public static final Supplier<Feature<TreeConfiguration>> WATER_CYPRESS_TREE = FEATURES.register("water_cypress_tree", () -> new WaterCypressFeature(TreeConfiguration.CODEC));
    public static final Supplier<Feature<TreeConfiguration>> WATER_MEGA_CYPRESS_TREE = FEATURES.register("water_mega_cypress_tree", () -> new WaterMegaCypressFeature(TreeConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> PODZOL_PATCH = FEATURES.register("podzol_patch", () -> new PodzolPatchFeature(NoneFeatureConfiguration.CODEC));


    public static final Supplier<TreeDecoratorType<?>> HANGING_CYPRESS_LEAVES = TREE_DECORATOR_TYPES.register("hanging_cypress_leaves", () -> new TreeDecoratorType<>(HangingCypressLeavesTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> CYPRESS_BRANCH = TREE_DECORATOR_TYPES.register("cypress_branch", () -> new TreeDecoratorType<>(CypressBranchTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> BEARD_MOSS = TREE_DECORATOR_TYPES.register("beard_moss", () -> new TreeDecoratorType<>(BeardMossTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> CYPRESS_KNEES = TREE_DECORATOR_TYPES.register("cypress_knees", () -> new TreeDecoratorType<>(CypressKneeTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> SPARSE_CYPRESS_KNEES = TREE_DECORATOR_TYPES.register("sparse_cypress_knees", () -> new TreeDecoratorType<>(SparseCypressKneeTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> SPARSE_LEAVE_VINES = TREE_DECORATOR_TYPES.register("sparse_leave_vines", () -> new TreeDecoratorType<>(SparseLeaveVineDecorator.CODEC));

    public static class Configs {
         public static final TreeConfiguration.TreeConfigurationBuilder GROWN_CYPRESS = (new TreeConfiguration.TreeConfigurationBuilder(
                 BlockStateProvider.simple(BayouBluesBlocks.CYPRESS_LOG.get()),
                 new StraightTrunkPlacer(0, 0, 0),
                 BlockStateProvider.simple(BayouBluesBlocks.CYPRESS_LEAVES.get()),
                 new BlobFoliagePlacer(UniformInt.of(0, 0), UniformInt.of(0, 0), 0),
                 new TwoLayersFeatureSize(0, 0, 0)
         )).ignoreVines().decorators(ImmutableList.of(HangingCypressLeavesTreeDecorator.DECORATOR, CypressBranchTreeDecorator.DECORATOR));

        public static final TreeConfiguration.TreeConfigurationBuilder NATURAL_CYPRESS = (new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BayouBluesBlocks.CYPRESS_LOG.get()),
                new StraightTrunkPlacer(0, 0, 0),
                BlockStateProvider.simple(BayouBluesBlocks.CYPRESS_LEAVES.get()),
                new BlobFoliagePlacer(UniformInt.of(0, 0), UniformInt.of(0, 0), 0),
                new TwoLayersFeatureSize(0, 0, 0)
        )).ignoreVines().decorators(ImmutableList.of(HangingCypressLeavesTreeDecorator.DECORATOR, SparseCypressKneeTreeDecorator.DECORATOR, CypressBranchTreeDecorator.DECORATOR, SparseLeaveVineDecorator.DECORATOR, BeardMossTreeDecorator.DECORATOR));

        public static final TreeConfiguration.TreeConfigurationBuilder NATURAL_CYPRESS_KNEES = (new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BayouBluesBlocks.CYPRESS_LOG.get()),
                new StraightTrunkPlacer(0, 0, 0),
                BlockStateProvider.simple(BayouBluesBlocks.CYPRESS_LEAVES.get()),
                new BlobFoliagePlacer(UniformInt.of(0, 0), UniformInt.of(0, 0), 0),
                new TwoLayersFeatureSize(0, 0, 0)
        )).ignoreVines().decorators(ImmutableList.of(HangingCypressLeavesTreeDecorator.DECORATOR, CypressKneeTreeDecorator.DECORATOR, CypressBranchTreeDecorator.DECORATOR, SparseLeaveVineDecorator.DECORATOR, BeardMossTreeDecorator.DECORATOR));

        public static final TreeConfiguration.TreeConfigurationBuilder CYPRESS_BUSH = (new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BayouBluesBlocks.CYPRESS_LOG.get()),
                new StraightTrunkPlacer(1, 0, 0),
                BlockStateProvider.simple(BayouBluesBlocks.CYPRESS_LEAVES.get()),
                new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                new TwoLayersFeatureSize(0, 0, 0)
        ));

        public static final BlockStateConfiguration ALGAE_PATCH = new BlockStateConfiguration(BayouBluesBlocks.ALGAE.get().defaultBlockState());
        public static final BlockStateConfiguration CYPRESS_LEAF_CARPET_PATCH_CONFIG = new BlockStateConfiguration(BayouBluesBlocks.CYPRESS_LEAF_CARPET.get().defaultBlockState());
    }

    public static class Configured {

        private static final Logger LOGGER = LogManager.getLogger();
        public static final PollinatedRegistry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = PollinatedRegistry.create(BuiltinRegistries.CONFIGURED_FEATURE, BayouBlues.MOD_ID);
        public static final PollinatedRegistry<PlacedFeature> PLACEMENTS = PollinatedRegistry.create(BuiltinRegistries.PLACED_FEATURE, BayouBlues.MOD_ID);

        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> GROWN_CYPRESS_TREE = CONFIGURED_FEATURES.register("cypress_grown", () -> new ConfiguredFeature<>(CYPRESS_TREE.get(), Configs.GROWN_CYPRESS.build()));
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> GROWN_MEGA_CYPRESS_TREE = CONFIGURED_FEATURES.register("mega_cypress_grown", () -> new ConfiguredFeature<>(MEGA_CYPRESS_TREE.get(), Configs.GROWN_CYPRESS.build()));

        public static final Supplier<ConfiguredFeature<NoneFeatureConfiguration, ?>> PODZOL = CONFIGURED_FEATURES.register("podzol_patch", () -> new ConfiguredFeature<>(PODZOL_PATCH.get(), FeatureConfiguration.NONE));

        public static final Supplier<PlacedFeature> PODZOL_PLACED = PLACEMENTS.register("podzol_patch", () -> new PlacedFeature(Holder.direct(PODZOL.get()), List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.countExtra(1, 0.1F, 1), BiomeFilter.biome())));

        @ExpectPlatform
        public static Holder<PlacedFeature> getHolder(Supplier<PlacedFeature> feature, String name) {
            return Platform.error();
        }

        public static void load(Platform platform) {
            LOGGER.debug("Registered to platform");
            CONFIGURED_FEATURES.register(platform);
            PLACEMENTS.register(platform);
        }
    }
}