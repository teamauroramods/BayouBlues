package com.teamaurora.bayou_blues.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author JustinPlayzz
 * @author Steven
 * @author ebo2022
 */
public class LilyFlowerBlock extends FlowerBlock {
    protected static final VoxelShape LILY_PAD_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);
    protected static final VoxelShape LILY_FLOWER_AABB = Block.box(3.0D, 1.5D, 3.0D, 13.0D, 13.D, 13.0D);
    protected static final VoxelShape SHAPE = Shapes.or(LILY_PAD_AABB, LILY_FLOWER_AABB);
    private final Item item;

    public static List<LilyFlowerBlock> LILY_FLOWERS = new ArrayList<>();

    public LilyFlowerBlock(Item item, BlockBehaviour.Properties builder) {
        super(MobEffects.POISON, 12, builder);
        this.item = item;
        LILY_FLOWERS.add(this);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return LILY_PAD_AABB;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(this.item);
    }

    public static Block getRandomLily(Random rand) {
        return LILY_FLOWERS.get(rand.nextInt(LILY_FLOWERS.size()));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        FluidState fluidstate = worldIn.getFluidState(pos.below());
        FluidState fluidstate1 = worldIn.getFluidState(pos);
        return (fluidstate.getType() == Fluids.WATER || state.getMaterial() == Material.ICE) && fluidstate1.getType() == Fluids.EMPTY;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        super.entityInside(state, worldIn, pos, entityIn);
        if (worldIn instanceof ServerLevel && entityIn instanceof Boat) {
            worldIn.removeBlock(new BlockPos(pos), true);
        }

    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        FluidState fluidstate = worldIn.getFluidState(pos);
        FluidState fluidstate1 = worldIn.getFluidState(pos.above());
        return (fluidstate.getType() == Fluids.WATER || state.getMaterial() == Material.ICE) && fluidstate1.getType() == Fluids.EMPTY;
    }

    @Override
    public BlockBehaviour.OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}