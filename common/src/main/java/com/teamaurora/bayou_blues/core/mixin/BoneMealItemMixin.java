package com.teamaurora.bayou_blues.core.mixin;

import com.teamaurora.bayou_blues.common.block.LilyFlowerBlock;
import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author JustinPlayzz
 * @author ebo2022
 */
@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
    private static boolean checkAdjacentForSolid(Level level, BlockPos pos) {
        for (int i = 0; i < 4; i++) {
            Direction dir = Direction.from2DDataValue(i);
            BlockPos poffset = pos.offset(dir.getNormal());

            if (level.getFluidState(poffset).getType() != Fluids.WATER) {
                return true;
            }
        }
        return false;
    }
    @Inject(method = "useOn", at = @At("HEAD"),cancellable = true)
    public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!world.isClientSide) {
            boolean success = false;
            int shrinkAmount = 1;
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == Blocks.LILY_PAD) {
                if (world.getRandom().nextBoolean() || checkAdjacentForSolid(world, pos.below())) {
                    Block lily = LilyFlowerBlock.getRandomLily(world.getRandom());

                    world.setBlock(pos, lily.defaultBlockState(), 3);
                    success = true;
                }
            } else if (state.getBlock() instanceof LilyFlowerBlock) {
                Block.popResource(world, pos, state.getBlock().getCloneItemStack(world, pos, state));
                success = true;
            } else if (state.getBlock() == Blocks.LARGE_FERN) {
                if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    DoublePlantBlock.placeAt(world, BayouBluesBlocks.GIANT_FERN.get().defaultBlockState(), pos, 3);
                } else {
                    DoublePlantBlock.placeAt(world, BayouBluesBlocks.GIANT_FERN.get().defaultBlockState(), pos.below(), 3);
                }
                success = true;
            }

            if (success) {
                context.getItemInHand().shrink(shrinkAmount);
                world.levelEvent(1505, pos, 0);
                cir.setReturnValue(InteractionResult.SUCCESS);
                cir.cancel();
            }
        }
    }
}