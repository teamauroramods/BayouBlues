package com.teamaurora.bayou_blues.common.world.treedecorator;

import com.mojang.serialization.Codec;
import com.teamaurora.bayou_blues.common.block.CypressBranchBlock;
import com.teamaurora.bayou_blues.common.util.TreeUtil;
import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import com.teamaurora.bayou_blues.core.registry.BayouBluesFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class CypressBranchTreeDecorator extends TreeDecorator {
    public static final Codec<CypressBranchTreeDecorator> CODEC;
    public static final CypressBranchTreeDecorator DECORATOR = new CypressBranchTreeDecorator();

    @Override
    protected TreeDecoratorType<?> type() {
        return BayouBluesFeatures.CYPRESS_BRANCH.get();
    }

    static {
        CODEC = Codec.unit(() -> DECORATOR);
    }

    @Override
    public void place(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> biConsumer, Random rand, List<BlockPos> logs, List<BlockPos> leaves) {
        for (BlockPos pos : logs) {
            if (rand.nextInt(25) == 0) {
                Direction dir = Direction.from2DDataValue(rand.nextInt(4));
                if (TreeUtil.isAir(world, pos.offset(dir.getNormal()))) {
                    biConsumer.accept(pos.offset(dir.getNormal()), BayouBluesBlocks.CYPRESS_BRANCH.get().defaultBlockState().setValue(CypressBranchBlock.FACING, dir.getOpposite()).setValue(CypressBranchBlock.AGE, 2));
                }
            }
        }
    }
}