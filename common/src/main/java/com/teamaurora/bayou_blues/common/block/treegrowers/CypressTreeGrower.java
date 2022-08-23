package com.teamaurora.bayou_blues.common.block.treegrowers;

import com.teamaurora.bayou_blues.core.registry.BayouBluesFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CypressTreeGrower extends AbstractMegaTreeGrower {

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(Random random) {
        return Holder.direct(BayouBluesFeatures.Configured.GROWN_MEGA_CYPRESS_TREE.get());
    }

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean bl) {
        return Holder.direct(BayouBluesFeatures.Configured.GROWN_CYPRESS_TREE.get());
    }
}
