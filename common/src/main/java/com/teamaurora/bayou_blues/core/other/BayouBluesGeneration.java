package com.teamaurora.bayou_blues.core.other;

import com.teamaurora.bayou_blues.core.registry.BayouBluesFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.Nullable;

public class BayouBluesGeneration {

    @Nullable
    private static final Music NORMAL_MUSIC = null;

    protected static int calculateSkyColor(float color) {
        float $$1 = color / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }

    private static Biome biome(Biome.Precipitation precipitation, Biome.BiomeCategory category, float temperature, float downfall, int waterColor, int waterFogColor, int fogColor ,MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music) {
        return (new Biome.BiomeBuilder())
                .precipitation(precipitation)
                .biomeCategory(category)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(waterColor)
                        .waterFogColor(waterFogColor)
                        .fogColor(fogColor).skyColor(calculateSkyColor(temperature))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(music).
                        build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(biomeBuilder.build()).build();
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome bayou() {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);
        BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1, 1));

        BiomeDefaultFeatures.addFossilDecoration(biomeBuilder);
        globalOverworldGeneration(biomeBuilder);
        bayouVegetation(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addSwampClayDisk(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_SWAMP);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA_2);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_SWAMP);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_SWAMP);

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addSwampExtraVegetation(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
        return biome(Biome.Precipitation.RAIN, Biome.BiomeCategory.SWAMP, 0.75F, 1.0F, 0x87C0C6, 0x3D5156, 0xA0E2E5, spawnBuilder, biomeBuilder, NORMAL_MUSIC);
    }

    private static void bayouVegetation(BiomeGenerationSettings.Builder biomeBuilder) {
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BayouBluesFeatures.Configured.getHolder(BayouBluesFeatures.Configured.ALGAE_PATCH_PLACED, "algae_patch"));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BayouBluesFeatures.Configured.getHolder(BayouBluesFeatures.Configured.FALLEN_CYPRESS_LEAVES_PLACED, "fallen_cypress_leaves"));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BayouBluesFeatures.Configured.getHolder(BayouBluesFeatures.Configured.PODZOL_PLACED, "podzol_patch"));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BayouBluesFeatures.Configured.getHolder(BayouBluesFeatures.Configured.PATCH_GIANT_FERN_PLACED, "patch_giant_fern"));
        addLilies(biomeBuilder);
    }

    public static void addLilies(BiomeGenerationSettings.Builder biomeBuilder) {
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BayouBluesFeatures.Configured.getHolder(BayouBluesFeatures.Configured.PATCH_LILY_COOL_PLACED, "patch_lily_cool"));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BayouBluesFeatures.Configured.getHolder(BayouBluesFeatures.Configured.PATCH_LILY_NEUTRAL_PLACED, "patch_lily_neutral"));
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BayouBluesFeatures.Configured.getHolder(BayouBluesFeatures.Configured.PATCH_LILY_WARM_PLACED, "patch_lily_warm"));
    }
}
