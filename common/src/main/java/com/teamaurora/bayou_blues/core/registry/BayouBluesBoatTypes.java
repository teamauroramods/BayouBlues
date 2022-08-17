package com.teamaurora.bayou_blues.core.registry;

import com.teamaurora.bayou_blues.core.BayouBlues;
import gg.moonflower.pollen.api.PollenRegistries;
import gg.moonflower.pollen.api.entity.PollinatedBoatType;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class BayouBluesBoatTypes {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final PollinatedRegistry<PollinatedBoatType> BOAT_TYPES = PollinatedRegistry.create(PollenRegistries.BOAT_TYPE_REGISTRY, BayouBlues.MOD_ID);
    public static final Supplier<PollinatedBoatType> CYPRESS_BOAT_TYPE = BOAT_TYPES.register("cypress_boat", () -> new PollinatedBoatType(BayouBlues.location("textures/entity/boat/cypress_boat.png")));

    public static void load(Platform platform) {
        LOGGER.debug("Registered to platform");
        BOAT_TYPES.register(platform);
    }
}
