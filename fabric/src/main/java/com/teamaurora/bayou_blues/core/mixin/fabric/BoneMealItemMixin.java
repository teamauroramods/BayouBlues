package com.teamaurora.bayou_blues.core.mixin.fabric;

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

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

    private boolean checkAdjacentForSolid(Level level, BlockPos pos) {
        for (int i = 0; i < 4; i++) {
            Direction dir = Direction.from2DDataValue(i);
            BlockPos poffset = pos.relative(dir);

            if (level.getFluidState(poffset).getType() != Fluids.WATER) {
                return true;
            }
        }
        return false;
    }

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOnBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!level.isClientSide) {
            boolean success = false;
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() == Blocks.LILY_PAD) {
                if (level.getRandom().nextBoolean() || checkAdjacentForSolid(level, pos.below())) {
                    Block lily = LilyFlowerBlock.getRandomLily(level.getRandom());

                    level.setBlock(pos, lily.defaultBlockState(), 3);
                    success = true;
                }
            } else if (state.getBlock() instanceof LilyFlowerBlock) {
                Block.popResource(level, pos, state.getBlock().getCloneItemStack(level, pos, state));
                success = true;
            } else if (state.getBlock() == Blocks.LARGE_FERN) {
                if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    DoublePlantBlock.placeAt(level, BayouBluesBlocks.GIANT_FERN.get().defaultBlockState(), pos, 3);
                } else {
                    DoublePlantBlock.placeAt(level, BayouBluesBlocks.GIANT_FERN.get().defaultBlockState(), pos.below(), 3);
                }
                success = true;
            }

            if (success) {
                context.getItemInHand().shrink(1);
                level.levelEvent(2005, pos, 0);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}
