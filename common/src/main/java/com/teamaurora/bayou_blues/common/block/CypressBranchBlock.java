package com.teamaurora.bayou_blues.common.block;

import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import com.teamaurora.bayou_blues.core.registry.BayouBluesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class CypressBranchBlock extends Block implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE_NORTH = Block.box(4.0D, 4.0D, 3.0D, 12.0D, 12.0D, 16.0D);
    protected static final VoxelShape SHAPE_EAST = Block.box(0.0D, 4.0D, 4.0D, 13.0D, 12.0D, 12.0D);
    protected static final VoxelShape SHAPE_SOUTH = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 13.0D);
    protected static final VoxelShape SHAPE_WEST = Block.box(3.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);

    public CypressBranchBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().getOwner().defaultBlockState().setValue(AGE, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE) < 2;
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), 2);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            default:
            case NORTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_WEST;
            case SOUTH:
                return SHAPE_NORTH;
            case WEST:
                return SHAPE_EAST;
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 2;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        int j = state.getValue(AGE);

        if (j < 2 && random.nextInt(10) == 0) {
            worldIn.setBlock(pos, state.setValue(AGE, j+1), 2);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        int i = state.getValue(AGE);
        boolean flag = i == 2;

        if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) {
            return InteractionResult.PASS;
        } else if (i > 1) {
            popResource(worldIn, pos, new ItemStack(BayouBluesItems.GOOSEBERRIES.get(), 1));
            worldIn.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            worldIn.setBlock(pos, state.setValue(AGE, 0), 2);
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        } else {
            return super.use(state, worldIn, pos, player, handIn, hit);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.offset(state.getValue(FACING).getNormal())).getBlock();
        return block == BayouBluesBlocks.CYPRESS_LOG.get() || block == BayouBluesBlocks.CYPRESS_WOOD.get();
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.canSurvive(worldIn, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (!context.replacingClickedOnBlock()) {
            BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().offset(context.getClickedFace().getOpposite().getNormal()));
            if (blockstate.is(this) && blockstate.getValue(FACING) == context.getClickedFace()) {
                return null;
            }
        }
        BlockState blockstate1 = this.defaultBlockState();
        LevelReader levelReader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                blockstate1 = blockstate1.setValue(FACING, direction.getOpposite());
                if (blockstate1.canSurvive(levelReader, blockpos)) {
                    return blockstate1;
                }
            }
        }

        return null;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING, AGE));
    }
}