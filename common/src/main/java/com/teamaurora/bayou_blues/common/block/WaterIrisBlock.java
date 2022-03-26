package com.teamaurora.bayou_blues.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

/**
 * @author JustinPlayzz
 * @author Steven
 * @author ebo2022
 * Stole some of this code from BYG (credits- Corgi Taco, AOCAWOL)
 */
public class WaterIrisBlock extends DoublePlantBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WaterIrisBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER && state.getValue(WATERLOGGED)) {
            return false;
        }
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            BlockPos groundPos = pos.below();
            Block ground = world.getBlockState(groundPos).getBlock();

            if (world.getFluidState(pos).getType() == Fluids.WATER)
                return ground.defaultBlockState().is(BlockTags.DIRT) || ground instanceof GrassBlock || ground.defaultBlockState().is(BlockTags.SAND) || ground == Blocks.GRAVEL || ground == Blocks.CLAY;
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (world.getFluidState(groundPos.relative(direction)).getType() == Fluids.WATER) {
                    return ground.defaultBlockState().is(BlockTags.DIRT) || ground instanceof GrassBlock || ground.defaultBlockState().is(BlockTags.SAND) || ground == Blocks.GRAVEL || ground == Blocks.CLAY;
                }
            }

            return false;
        } else {
            BlockState blockstate = world.getBlockState(pos.below());
            if (state.getBlock() != this)
                return false;
            return blockstate.getBlock() == this && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!canSurvive(state, world, pos)) {
            if (state.getValue(WATERLOGGED)) {
                world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            } else {
                world.destroyBlock(pos, false);
            }
        }
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockState stateUpper = world.getBlockState(pos.above());
            if (stateUpper.getBlock() instanceof WaterIrisBlock) {
                if (!canSurvive(stateUpper, world, pos.above())) {
                    world.destroyBlock(pos.above(), false);
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world,
                                  BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());

        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            return state.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        } else {
            return null;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }
}