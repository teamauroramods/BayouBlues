package com.teamaurora.bayou_blues.core.registry;

import com.teamaurora.bayou_blues.core.BayouBlues;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.function.Supplier;

public class BayouBluesFeatures {
    public static final PollinatedRegistry<Feature<?>> FEATURES = PollinatedRegistry.create(Registry.FEATURE, BayouBlues.MOD_ID);
}
