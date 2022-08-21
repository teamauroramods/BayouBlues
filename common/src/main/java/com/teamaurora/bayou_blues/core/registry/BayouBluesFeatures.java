package com.teamaurora.bayou_blues.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamaurora.bayou_blues.common.world.decorators.CypressBranchTreeDecorator;
import com.teamaurora.bayou_blues.common.world.decorators.HangingCypressLeavesTreeDecorator;
import com.teamaurora.bayou_blues.common.world.feature.CypressFeature;
import com.teamaurora.bayou_blues.common.world.feature.MegaCypressFeature;
import com.teamaurora.bayou_blues.core.BayouBlues;
import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
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
    public static final Supplier<TreeDecoratorType<?>> HANGING_CYPRESS_LEAVES = TREE_DECORATOR_TYPES.register("hanging_cypress_leaves", () -> new TreeDecoratorType<>(HangingCypressLeavesTreeDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<?>> CYPRESS_BRANCH = TREE_DECORATOR_TYPES.register("cypress_branch", () -> new TreeDecoratorType<>(CypressBranchTreeDecorator.CODEC));

    public static class Configured {

        private static final Logger LOGGER = LogManager.getLogger();
        public static final PollinatedRegistry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = PollinatedRegistry.create(BuiltinRegistries.CONFIGURED_FEATURE, BayouBlues.MOD_ID);
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> GROWN_CYPRESS_TREE = CONFIGURED_FEATURES.register("cypress_grown", () -> new ConfiguredFeature<>(CYPRESS_TREE.get(), grownCypressConfig().build()));
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> GROWN_MEGA_CYPRESS_TREE = CONFIGURED_FEATURES.register("mega_cypress_grown", () -> new ConfiguredFeature<>(MEGA_CYPRESS_TREE.get(), grownCypressConfig().build()));

        public static TreeConfiguration.TreeConfigurationBuilder grownCypressConfig() {
           return new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BayouBluesBlocks.CYPRESS_LOG.get()),
                    new StraightTrunkPlacer(0, 0, 0),
                    BlockStateProvider.simple(BayouBluesBlocks.CYPRESS_LEAVES.get()),
                    new BlobFoliagePlacer(UniformInt.of(0, 0), UniformInt.of(0, 0), 0),
                    new TwoLayersFeatureSize(0, 0, 0)
            ).ignoreVines().decorators(ImmutableList.of(HangingCypressLeavesTreeDecorator.DECORATOR, CypressBranchTreeDecorator.DECORATOR));
        }
        @ExpectPlatform
        public static Holder<PlacedFeature> getHolder(Supplier<PlacedFeature> feature, String name) {
            return Platform.error();
        }
        public static void load(Platform platform) {
            LOGGER.debug("Registered to platform");
            CONFIGURED_FEATURES.register(platform);
        }
    }
}