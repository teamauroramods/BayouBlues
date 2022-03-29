package com.teamaurora.bayou_blues.common.block.trees;

import com.teamaurora.bayou_blues.core.registry.BayouBluesFeatures;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CypressTreeGrower extends AbstractMegaTreeGrower {
    @Override
    @Nullable
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(@Nullable Random randomIn, boolean largeHive) {
        return BayouBluesFeatures.Configured.CYPRESS_GROWN.get();
    }

    @Override
    @Nullable
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredMegaFeature(@Nullable Random randomIn) {
        return BayouBluesFeatures.Configured.MEGA_CYPRESS_GROWN.get();
    }
}