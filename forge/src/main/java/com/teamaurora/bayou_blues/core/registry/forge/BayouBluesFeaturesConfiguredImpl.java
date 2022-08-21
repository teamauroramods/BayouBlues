package com.teamaurora.bayou_blues.core.registry.forge;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BayouBluesFeaturesConfiguredImpl {

    public static Holder<PlacedFeature> getHolder(Supplier<PlacedFeature> feature, String name) {
        return ((RegistryObject<PlacedFeature>) feature).getHolder().orElseThrow();
    }
}
