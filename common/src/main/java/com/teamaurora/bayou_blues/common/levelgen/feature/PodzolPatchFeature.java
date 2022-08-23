package com.teamaurora.bayou_blues.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class PodzolPatchFeature extends Feature<NoneFeatureConfiguration> {

    public PodzolPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos pos = featurePlaceContext.origin();
        WorldGenLevel level = featurePlaceContext.level();
        Random random = featurePlaceContext.random();
        int i = 0;
        for (BlockPos newPos : BlockPos.betweenClosed(pos.offset(-4, -4, -4), pos.offset(4, 4, 4))) {
            if (level.getBlockState(newPos).getBlock() == Blocks.GRASS_BLOCK) {
                if (random.nextFloat() <= 1.0F - (newPos.distSqr(pos) / 32)) {
                    level.setBlock(newPos, Blocks.PODZOL.defaultBlockState(), 3);
                    i++;
                }
            }
        }
        return i > 0;
    }
}
