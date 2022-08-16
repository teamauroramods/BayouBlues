package com.teamaurora.bayou_blues.common.treegrowers;

import com.teamaurora.bayou_blues.core.registry.BayouBluesFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CypressTreeGrower extends AbstractMegaTreeGrower {

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(Random random) {
        return BayouBluesFeatures.Configured.MEGA_CYPRESS_GROWN;
    }

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean bl) {
        return BayouBluesFeatures.Configured.CYPRESS_GROWN;
    }
}
