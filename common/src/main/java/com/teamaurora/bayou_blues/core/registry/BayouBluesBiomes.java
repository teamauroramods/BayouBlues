package com.teamaurora.bayou_blues.core.registry;

import com.teamaurora.bayou_blues.core.BayouBlues;
import com.teamaurora.bayou_blues.core.other.BayouBluesGeneration;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class BayouBluesBiomes {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final PollinatedRegistry<Biome> BIOMES = PollinatedRegistry.create(BuiltinRegistries.BIOME, BayouBlues.MOD_ID);

    public static final ResourceKey<Biome> BAYOU = createBiome("bayou", BayouBluesGeneration::bayou);

    public static ResourceKey<Biome> createBiome(String name, Supplier<Biome> biomeSupplier) {
        ResourceLocation id = BayouBlues.location(name);
        BIOMES.register(name, biomeSupplier);
        return ResourceKey.create(Registry.BIOME_REGISTRY, id);
    }

    public static void load(Platform platform) {
        LOGGER.debug("Registered to platform");
        BIOMES.register(platform);
    }
}
