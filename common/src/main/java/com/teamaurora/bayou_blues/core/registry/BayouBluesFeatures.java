package com.teamaurora.bayou_blues.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamaurora.bayou_blues.common.levelgen.decorators.*;
import com.teamaurora.bayou_blues.common.levelgen.feature.*;
import com.teamaurora.bayou_blues.core.BayouBlues;
import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
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
    public static final Supplier<Feature<BlockStateConfiguration>> LARGE_PATCH = FEATURES.register("large_patch", () -> new LargePatchFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<Feature<BlockStateConfiguration>> LARGE_LAND_PATCH = FEATURES.register("large_land_patch", () -> new LargeLandPatchFeature(BlockStateConfiguration.CODEC));

    public static final Supplier<TreeDecoratorType<?>> HANGING_CYPRESS_LEAVES = TREE_DECORATOR_TYPES.register("hanging_cypress_leaves", () -> new TreeDecoratorType<>(HangingCypressLeavesTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> CYPRESS_BRANCH = TREE_DECORATOR_TYPES.register("cypress_branch", () -> new TreeDecoratorType<>(CypressBranchTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> BEARD_MOSS = TREE_DECORATOR_TYPES.register("beard_moss", () -> new TreeDecoratorType<>(BeardMossTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> CYPRESS_KNEES = TREE_DECORATOR_TYPES.register("cypress_knees", () -> new TreeDecoratorType<>(CypressKneeTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> SPARSE_CYPRESS_KNEES = TREE_DECORATOR_TYPES.register("sparse_cypress_knees", () -> new TreeDecoratorType<>(SparseCypressKneeTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> SPARSE_LEAVE_VINES = TREE_DECORATOR_TYPES.register("sparse_leave_vines", () -> new TreeDecoratorType<>(SparseLeaveVineDecorator.CODEC));

    public static class BlockStates {
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
        public static final BlockState FERN = Blocks.FERN.defaultBlockState();
        public static final BlockState LARGE_FERN = Blocks.LARGE_FERN.defaultBlockState();
        public static final BlockState GIANT_FERN = BayouBluesBlocks.GIANT_FERN.get().defaultBlockState();
    }

    public static class Configs {
         public static final TreeConfiguration.TreeConfigurationBuilder GROWN_CYPRESS = (new TreeConfiguration.TreeConfigurationBuilder(
                 BlockStateProvider.simple(BlockStates.CYPRESS_LOG),
                 new StraightTrunkPlacer(0, 0, 0),
                 BlockStateProvider.simple(BlockStates.CYPRESS_LEAVES),
                 new BlobFoliagePlacer(UniformInt.of(0, 0), UniformInt.of(0, 0), 0),
                 new TwoLayersFeatureSize(0, 0, 0)
         )).ignoreVines().decorators(ImmutableList.of(HangingCypressLeavesTreeDecorator.DECORATOR, CypressBranchTreeDecorator.DECORATOR));

        public static final TreeConfiguration.TreeConfigurationBuilder NATURAL_CYPRESS = (new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BlockStates.CYPRESS_LOG),
                new StraightTrunkPlacer(0, 0, 0),
                BlockStateProvider.simple(BlockStates.CYPRESS_LEAVES),
                new BlobFoliagePlacer(UniformInt.of(0, 0), UniformInt.of(0, 0), 0),
                new TwoLayersFeatureSize(0, 0, 0)
        )).ignoreVines().decorators(ImmutableList.of(HangingCypressLeavesTreeDecorator.DECORATOR, SparseCypressKneeTreeDecorator.DECORATOR, CypressBranchTreeDecorator.DECORATOR, SparseLeaveVineDecorator.DECORATOR, BeardMossTreeDecorator.DECORATOR));

        public static final TreeConfiguration.TreeConfigurationBuilder NATURAL_CYPRESS_KNEES = (new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BlockStates.CYPRESS_LOG),
                new StraightTrunkPlacer(0, 0, 0),
                BlockStateProvider.simple(BlockStates.CYPRESS_LEAVES),
                new BlobFoliagePlacer(UniformInt.of(0, 0), UniformInt.of(0, 0), 0),
                new TwoLayersFeatureSize(0, 0, 0)
        )).ignoreVines().decorators(ImmutableList.of(HangingCypressLeavesTreeDecorator.DECORATOR, CypressKneeTreeDecorator.DECORATOR, CypressBranchTreeDecorator.DECORATOR, SparseLeaveVineDecorator.DECORATOR, BeardMossTreeDecorator.DECORATOR));

        public static final TreeConfiguration.TreeConfigurationBuilder CYPRESS_BUSH = (new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BlockStates.CYPRESS_LOG),
                new StraightTrunkPlacer(1, 0, 0),
                BlockStateProvider.simple(BlockStates.CYPRESS_LEAVES),
                new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                new TwoLayersFeatureSize(0, 0, 0)
        ));

        public static final BlockStateConfiguration ALGAE_PATCH = new BlockStateConfiguration(BlockStates.ALGAE);
        public static final BlockStateConfiguration CYPRESS_LEAF_CARPET_PATCH = new BlockStateConfiguration(BlockStates.CYPRESS_LEAF_CARPET);
        public static final RandomPatchConfiguration PATCH_LILY_COOL = new RandomPatchConfiguration(6, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlockStates.BLUE_LILY, 2).add(BlockStates.CYAN_LILY, 2).add(BlockStates.LIGHT_BLUE_LILY, 2).add(BlockStates.LILY_PAD, 5)))));
        public static final RandomPatchConfiguration PATCH_LILY_NEUTRAL = new RandomPatchConfiguration(6, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlockStates.LIGHT_GRAY_LILY, 3).add(BlockStates.WHITE_LILY, 3).add(BlockStates.LILY_PAD, 5)))));
        public static final RandomPatchConfiguration PATCH_LILY_WARM = new RandomPatchConfiguration(6, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlockStates.PINK_LILY, 2).add(BlockStates.MAGENTA_LILY, 2).add(BlockStates.PURPLE_LILY, 2).add(BlockStates.LILY_PAD, 5)))));
    }

    public static class Configured {

        private static final Logger LOGGER = LogManager.getLogger();
        public static final PollinatedRegistry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = PollinatedRegistry.create(BuiltinRegistries.CONFIGURED_FEATURE, BayouBlues.MOD_ID);
        public static final PollinatedRegistry<PlacedFeature> PLACEMENTS = PollinatedRegistry.create(BuiltinRegistries.PLACED_FEATURE, BayouBlues.MOD_ID);

        public static final Supplier<ConfiguredFeature<BlockStateConfiguration, ?>> ALGAE_PATCH = CONFIGURED_FEATURES.register("algae_patch", () -> new ConfiguredFeature<>(LARGE_PATCH.get(), Configs.ALGAE_PATCH));
        public static final Supplier<ConfiguredFeature<BlockStateConfiguration, ?>> FALLEN_CYPRESS_LEAVES = CONFIGURED_FEATURES.register("fallen_cypress_leaves", () -> new ConfiguredFeature<>(LARGE_LAND_PATCH.get(), Configs.CYPRESS_LEAF_CARPET_PATCH));
        public static final Supplier<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_GIANT_FERN = CONFIGURED_FEATURES.register("patch_giant_fern", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BayouBluesBlocks.GIANT_FERN.get())))));
        public static final Supplier<ConfiguredFeature<NoneFeatureConfiguration, ?>> PODZOL = CONFIGURED_FEATURES.register("podzol_patch", () -> new ConfiguredFeature<>(PODZOL_PATCH.get(), FeatureConfiguration.NONE));
        public static final Supplier<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_LILY_COOL = CONFIGURED_FEATURES.register("patch_lily_cool", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, Configs.PATCH_LILY_COOL));
        public static final Supplier<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_LILY_NEUTRAL = CONFIGURED_FEATURES.register("patch_lily_neutral", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, Configs.PATCH_LILY_NEUTRAL));
        public static final Supplier<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_LILY_WARM = CONFIGURED_FEATURES.register("patch_lily_warm", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, Configs.PATCH_LILY_WARM));
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> CYPRESS_BUSH = CONFIGURED_FEATURES.register("cypress_bush", () -> new ConfiguredFeature<>(Feature.TREE, Configs.CYPRESS_BUSH.build()));
        public static final Supplier<PlacedFeature> PATCH_GIANT_FERN_PLACED = PLACEMENTS.register("patch_giant_fern", () -> new PlacedFeature(Holder.direct(PATCH_GIANT_FERN.get()), List.of(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
        public static final Supplier<PlacedFeature> PODZOL_PLACED = PLACEMENTS.register("podzol_patch", () -> new PlacedFeature(Holder.direct(PODZOL.get()), List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.countExtra(1, 0.1F, 1), BiomeFilter.biome())));
        public static final Supplier<PlacedFeature> ALGAE_PATCH_PLACED = PLACEMENTS.register("algae_patch", () -> new PlacedFeature(Holder.direct(ALGAE_PATCH.get()), List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(2), BiomeFilter.biome())));
        public static final Supplier<PlacedFeature> FALLEN_CYPRESS_LEAVES_PLACED = PLACEMENTS.register("fallen_cypress_leaves", () -> new PlacedFeature(Holder.direct(FALLEN_CYPRESS_LEAVES.get()), List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(6), BiomeFilter.biome())));
        public static final Supplier<PlacedFeature> PATCH_LILY_COOL_PLACED = PLACEMENTS.register("patch_lily_cool", () -> new PlacedFeature(Holder.direct(PATCH_LILY_COOL.get()), VegetationPlacements.worldSurfaceSquaredWithCount(4)));
        public static final Supplier<PlacedFeature> PATCH_LILY_NEUTRAL_PLACED = PLACEMENTS.register("patch_lily_neutral", () -> new PlacedFeature(Holder.direct(PATCH_LILY_NEUTRAL.get()), VegetationPlacements.worldSurfaceSquaredWithCount(4)));
        public static final Supplier<PlacedFeature> PATCH_LILY_WARM_PLACED = PLACEMENTS.register("patch_lily_warm", () -> new PlacedFeature(Holder.direct(PATCH_LILY_WARM.get()), VegetationPlacements.worldSurfaceSquaredWithCount(4)));

        // Trees
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> GROWN_CYPRESS_TREE = CONFIGURED_FEATURES.register("cypress_grown", () -> new ConfiguredFeature<>(CYPRESS_TREE.get(), Configs.GROWN_CYPRESS.build()));
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> GROWN_MEGA_CYPRESS_TREE = CONFIGURED_FEATURES.register("mega_cypress_grown", () -> new ConfiguredFeature<>(MEGA_CYPRESS_TREE.get(), Configs.GROWN_CYPRESS.build()));
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> CYPRESS = CONFIGURED_FEATURES.register("cypress", () -> new ConfiguredFeature<>(CYPRESS_TREE.get(), Configs.NATURAL_CYPRESS.build()));
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> MEGA_CYPRESS = CONFIGURED_FEATURES.register("mega_cypress", () -> new ConfiguredFeature<>(MEGA_CYPRESS_TREE.get(), Configs.NATURAL_CYPRESS.build()));
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> MEGA_CYPRESS_KNEES = CONFIGURED_FEATURES.register("mega_cypress_knees", () -> new ConfiguredFeature<>(MEGA_CYPRESS_TREE.get(), Configs.NATURAL_CYPRESS_KNEES.build()));
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> WATER_CYPRESS = CONFIGURED_FEATURES.register("water_cypress", () -> new ConfiguredFeature<>(WATER_CYPRESS_TREE.get(), Configs.NATURAL_CYPRESS.build()));
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> WATER_MEGA_CYPRESS = CONFIGURED_FEATURES.register("water_mega_cypress", () -> new ConfiguredFeature<>(WATER_MEGA_CYPRESS_TREE.get(), Configs.NATURAL_CYPRESS.build()));

        public static final Supplier<PlacedFeature> CYPRESS_CHECKED = PLACEMENTS.register("cypress_checked", () -> new PlacedFeature(Holder.direct(CYPRESS.get()), checked()));
        public static final Supplier<PlacedFeature> MEGA_CYPRESS_CHECKED = PLACEMENTS.register("mega_cypress_checked", () -> new PlacedFeature(Holder.direct(MEGA_CYPRESS.get()), checked()));
        public static final Supplier<PlacedFeature> WATER_CYPRESS_CHECKED = PLACEMENTS.register("water_cypress_checked", () -> new PlacedFeature(Holder.direct(WATER_CYPRESS.get()), checked()));
        public static final Supplier<PlacedFeature> WATER_MEGA_CYPRESS_CHECKED = PLACEMENTS.register("water_mega_cypress_checked", () -> new PlacedFeature(Holder.direct(WATER_MEGA_CYPRESS.get()), checked()));
        public static final Supplier<PlacedFeature> MEGA_CYPRESS_KNEES_CHECKED = PLACEMENTS.register("mega_cypress_knees_checked", () -> new PlacedFeature(Holder.direct(MEGA_CYPRESS_KNEES.get()), checked()));
        public static final Supplier<PlacedFeature> CYPRESS_BUSH_PLACED = PLACEMENTS.register("cypress_bush", () -> new PlacedFeature(Holder.direct(CYPRESS_BUSH.get()), checked()));

        public static final Supplier<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BAYOU_WATER = CONFIGURED_FEATURES.register("trees_bayou_water", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(getHolder(WATER_MEGA_CYPRESS_CHECKED, "water_mega_cypress_checked"), 0.333333334F)), getHolder(WATER_CYPRESS_CHECKED, "water_cypress_checked"))));
        public static final Supplier<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BAYOU = CONFIGURED_FEATURES.register("trees_bayou", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(getHolder(CYPRESS_BUSH_PLACED, "cypress_bush"), 0.35f), new WeightedPlacedFeature(getHolder(MEGA_CYPRESS_KNEES_CHECKED, "mega_cypress_knees_checked"), 0.333333334F)), getHolder(CYPRESS_CHECKED, "cypress_checked"))));

        public static final Supplier<PlacedFeature> TREES_BAYOU_WATER_PLACED = PLACEMENTS.register("trees_bayou_water", () -> new PlacedFeature(Holder.direct(TREES_BAYOU_WATER.get()), List.of(PlacementUtils.countExtra(9, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(3), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(BayouBluesBlocks.CYPRESS_SAPLING.get().defaultBlockState(), BlockPos.ZERO)))));
        public static final Supplier<PlacedFeature> TREES_BAYOU_PLACED = PLACEMENTS.register("trees_bayou", () -> new PlacedFeature(Holder.direct(TREES_BAYOU.get()), VegetationPlacements.treePlacement(PlacementUtils.countExtra(20, 0.1F, 1))));


        private static List<PlacementModifier> checked() {
            return List.of(PlacementUtils.filteredByBlockSurvival(BayouBluesBlocks.CYPRESS_SAPLING.get()));
        }

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