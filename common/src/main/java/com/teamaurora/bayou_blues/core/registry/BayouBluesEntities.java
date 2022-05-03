package com.teamaurora.bayou_blues.core.registry;

import com.teamaurora.bayou_blues.core.BayouBlues;
import gg.moonflower.pollen.api.PollenRegistries;
import gg.moonflower.pollen.api.entity.PollinatedBoatType;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class BayouBluesEntities {
    public static final PollinatedRegistry<PollinatedBoatType> BOATS = PollinatedRegistry.create(PollenRegistries.BOAT_TYPE_REGISTRY, BayouBlues.MOD_ID);

    /* Boats */
    public static final Supplier<PollinatedBoatType> CYPRESS_BOAT = BOATS.register("cypress_boat", () -> new PollinatedBoatType(new ResourceLocation(BayouBlues.MOD_ID, "textures/entity/boat/cypress_boat.png")));
}
