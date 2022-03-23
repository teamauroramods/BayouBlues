package com.teamaurora.bayou_blues.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

/**
 * @author JustinPlayzz
 * @author Steven
 * @author ebo2022
 */
@SuppressWarnings("deprecation")
public class AlgaeBlock extends BushBlock implements BonemealableBlock {
    protected static final VoxelShape ALGAE_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public AlgaeBlock(BlockBehaviour.Properties builder) {
        super(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return ALGAE_AABB;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        FluidState fluidstate = worldIn.getFluidState(pos.below());
        FluidState fluidstate1 = worldIn.getFluidState(pos);
        return (fluidstate.getType() == Fluids.WATER || state.getMaterial() == Material.ICE) && fluidstate1.getType() == Fluids.EMPTY;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        for (BlockPos pos2 : BlockPos.betweenClosed(pos.offset(-2, -2, -2), pos.offset(2, 2, 2))) {
            if (pos.closerThan(pos2, 2.0F)) {
                if (worldIn.getBlockState(pos2).isAir() && worldIn.getFluidState(pos2.below()).getType() == Fluids.WATER) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        for (BlockPos pos2 : BlockPos.betweenClosed(pos.offset(-3, -3, -3), pos.offset(3, 3, 3))) {
            if (pos.closerThan(pos2, 3.0F)) {
                if (rand.nextFloat() <= 1.0F - pos2.distSqr(pos)/18.0F) {
                    if (worldIn.getBlockState(pos2).isAir() && worldIn.getFluidState(pos2.below()).getType() == Fluids.WATER) {
                        worldIn.setBlockAndUpdate(pos2, this.defaultBlockState());
                    }
                }
            }
        }
    }
}