package com.teamaurora.bayou_blues.common.block.thatch;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ThatchStairBlock extends StairBlock {
    public ThatchStairBlock(BlockState state, BlockBehaviour.Properties properties) {
        super(state, properties);
    }

    public float getAmbientOcclusionLightLevel(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1.0F;
    }

    public boolean isTranslucent(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }
}
