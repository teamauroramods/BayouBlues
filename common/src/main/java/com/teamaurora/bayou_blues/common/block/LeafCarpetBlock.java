package com.teamaurora.bayou_blues.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * @author JustinPlayzz
 * @author Steven
 * @author ebo2022
 */
@SuppressWarnings("deprecation")
public class LeafCarpetBlock extends Block {
    private static final VoxelShape SHAPE = box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public LeafCarpetBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
}