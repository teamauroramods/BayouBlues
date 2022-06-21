package com.teamaurora.bayou_blues.core.forge;

import com.teamaurora.bayou_blues.common.block.LilyFlowerBlock;
import com.teamaurora.bayou_blues.core.BayouBlues;
import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BayouBlues.MOD_ID)
public class BayouBluesCommonForgeEvents {

    private static boolean checkAdjacentForSolid(Level level, BlockPos pos) {
        for (int i = 0; i < 4; i++) {
            Direction dir = Direction.from2DDataValue(i);
            BlockPos poffset = pos.relative(dir);

            if (level.getFluidState(poffset).getType() != Fluids.WATER) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void onBonemealUse(BonemealEvent event) {
        BlockState state = event.getBlock();
        BlockPos pos = event.getPos();
        Level level = event.getWorld();

        if (state.getBlock() == Blocks.LILY_PAD) {
            if (level.getRandom().nextBoolean() || checkAdjacentForSolid(level, pos.below())) {
                Block lily = LilyFlowerBlock.getRandomLily(level.getRandom());

                if (!level.isClientSide) {
                    level.setBlock(pos, lily.defaultBlockState(), 3);
                    event.setResult(Event.Result.ALLOW);
                }
            }
        }
        if (state.getBlock() == Blocks.LARGE_FERN) {
            if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                DoublePlantBlock.placeAt(level, BayouBluesBlocks.GIANT_FERN.get().defaultBlockState(), pos, 3);
            } else {
                DoublePlantBlock.placeAt(level, BayouBluesBlocks.GIANT_FERN.get().defaultBlockState(), pos.below(), 3);
            }
            event.setResult(Event.Result.ALLOW);
        }
    }
}
