package com.teamaurora.bayou_blues.common.world.decorators;

import com.mojang.serialization.Codec;
import com.teamaurora.bayou_blues.common.util.TreeUtil;
import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import com.teamaurora.bayou_blues.core.registry.BayouBluesFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class HangingCypressLeavesTreeDecorator extends TreeDecorator {
    public static final Codec<HangingCypressLeavesTreeDecorator> CODEC;
    public static final HangingCypressLeavesTreeDecorator DECORATOR = new HangingCypressLeavesTreeDecorator();

    @Override
    protected TreeDecoratorType<?> type() {
        return BayouBluesFeatures.HANGING_CYPRESS_LEAVES.get();
    }

    static {
        CODEC = Codec.unit(() -> DECORATOR);
    }

    @Override
    public void place(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> biConsumer, Random rand, List<BlockPos> logs, List<BlockPos> leaves) {
        for (BlockPos pos : leaves) {
            if (TreeUtil.isAir(world, pos.below())) {
                if (rand.nextInt(3) == 0) {
                    biConsumer.accept(pos.below(), BayouBluesBlocks.HANGING_CYPRESS_LEAVES.get().defaultBlockState());
                }
            }
        }
    }
}