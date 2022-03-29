package com.teamaurora.bayou_blues.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.Random;

/**
 * @author JustinPlayzz
 * @author Steven
 * @author ebo2022
 */
public final class TreeUtil {
    public static void placeDirectionalLogAt(LevelWriter level, BlockPos pos, Direction direction, Random rand, TreeConfiguration config) {
        setForcedState(level, pos, config.trunkProvider.getState(rand, pos).setValue(RotatedPillarBlock.AXIS, direction.getAxis()));
    }

    public static void placeLeafAt(LevelSimulatedRW world, BlockPos pos, Random rand, TreeConfiguration config) {
        if (isAirOrLeaves(world, pos)) {
            setForcedState(world, pos, config.foliageProvider.getState(rand, pos).setValue(LeavesBlock.DISTANCE, 1));
        }
    }

    public static void setForcedState(LevelWriter world, BlockPos pos, BlockState state) {
        world.setBlock(pos, state, 18);
    }

    public static boolean isAir(LevelSimulatedReader level, BlockPos pos) {
        if (!(level instanceof BlockGetter)) {
            return level.isStateAtPosition(pos, BlockState::isAir);
        } else {
            return level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir);
        }
    }

    public static boolean isAirOrLeaves(LevelSimulatedReader level, BlockPos pos) {
        if (level instanceof LevelReader) {
            return level.isStateAtPosition(pos, state -> state.isAir() || state.is(BlockTags.LEAVES));
        }
         return level.isStateAtPosition(pos, (state) -> isAir(level, pos) || state.is(BlockTags.LEAVES));
    }

    public static void setDirtAt(LevelAccessor level, BlockPos pos) {
        Block block = level.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
        }
    }

    public static boolean isValidGround(LevelAccessor level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT) || state.is(Blocks.PODZOL) || state.is(Blocks.FARMLAND);
    }

}
