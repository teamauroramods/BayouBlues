package com.teamaurora.bayou_blues.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.BiomeUtil;
import com.minecraftabnormals.abnormals_core.core.util.registry.BiomeSubRegistryHelper;
import com.mojang.datafixers.util.Pair;
import com.teamaurora.bayou_blues.core.BayouBlues;
import com.teamaurora.bayou_blues.core.BayouBluesConfig;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BayouBlues.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BayouBluesBiomes {
    private static final BiomeSubRegistryHelper HELPER = BayouBlues.REGISTRY_HELPER.getBiomeSubHelper();

    public static final BiomeSubRegistryHelper.KeyedBiome BAYOU = HELPER.createBiome("bayou", () -> makeBayouBiome(-0.175F, 0.2F));
    public static final BiomeSubRegistryHelper.KeyedBiome BAYOU_HILLS = HELPER.createBiome("bayou_hills", () -> makeBayouBiome(-0.1F, 0.4F));

    public static void addHillBiome() {
        BiomeUtil.addHillBiome(BAYOU.getKey(), Pair.of(BAYOU_HILLS.getKey(), 1));
    }

    public static void registerBiomesToDictionary() {
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BAYOU.getKey(), BayouBluesConfig.COMMON.bayouWeight.get()));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BAYOU_HILLS.getKey(), BayouBluesConfig.COMMON.bayouHillsWeight.get()));
    }

    public static void addBiomeTypes() {
        BiomeDictionary.addTypes(BAYOU.getKey(), BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.RARE, BiomeDictionary.Type.OVERWORLD);
        BiomeDictionary.addTypes(BAYOU_HILLS.getKey(), BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.RARE, BiomeDictionary.Type.OVERWORLD);
    }

    private static Biome makeBayouBiome(float depth, float scale) {
        return (new Biome.Builder())
                .precipitation(Biome.RainType.RAIN)
                .category(Biome.Category.SWAMP)
                .depth(depth)
                .scale(scale)
                .temperature(0.75F)
                .downfall(1.0F)
                .setEffects((new BiomeAmbience.Builder())
                        .setWaterColor(0x87C0C6)
                        .setWaterFogColor(0x3D5156)
                        .setFogColor(0xA0E2E5)
                        .withSkyColor(getSkyColorWithTemperatureModifier(0.75F))
                        .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
                        .withFoliageColor(0x69AA2F)
                        .withGrassColor(0x6CC147)
                        .build())
                .withMobSpawnSettings(new MobSpawnInfo.Builder().copy())
                .withGenerationSettings((new BiomeGenerationSettings.Builder())
                        .withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244189_u)
                        .build()).build();
    }

    private static int getSkyColorWithTemperatureModifier(float temperature) {
        float f = temperature / 3.0F;
        f = MathHelper.clamp(f, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
}
