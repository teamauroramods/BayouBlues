package com.teamaurora.bayou_blues.common.levelgen.decorators;

import com.mojang.serialization.Codec;
import com.teamaurora.bayou_blues.common.block.CypressKneeBlock;
import com.teamaurora.bayou_blues.common.block.DoubleCypressKneeBlock;
import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import com.teamaurora.bayou_blues.core.registry.BayouBluesFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class SparseCypressKneeTreeDecorator extends TreeDecorator {
    public static final Codec<SparseCypressKneeTreeDecorator> CODEC;
    public static final SparseCypressKneeTreeDecorator DECORATOR = new SparseCypressKneeTreeDecorator();

    @Override
    protected TreeDecoratorType<?> type() {
        return BayouBluesFeatures.SPARSE_CYPRESS_KNEES.get();
    }

    static {
        CODEC = Codec.unit(() -> DECORATOR);
    }

    @Override
    public void place(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, Random random, List<BlockPos> logs, List<BlockPos> leaves) {
        int minY = 384;
        int maxWaterY = 0;
        for (BlockPos pos : logs) {
            if (pos.getY() < minY) minY = pos.getY();
            if (pos.getY() > maxWaterY) {
                for (int i = 0; i < 4; i++) {
                    Direction dir = Direction.from2DDataValue(i);
                    if (levelSimulatedReader.isStateAtPosition(pos.relative(dir), blockState -> blockState.is(Blocks.WATER))) {
                        maxWaterY = pos.getY();
                    }
                }
            }
        }
        if (maxWaterY > minY) minY = maxWaterY;
        for (BlockPos pos : logs) {
            if (pos.getY() == minY && random.nextInt(6) == 0) {
                for (int x = -2; x <= 2; x++) {
                    for (int y = -2; y <= 2; y++) {
                        for (int z = -2; z <= 2; z++) {
                            BlockPos newPos = pos.offset(x, y, z);
                            if (pos.closerThan(newPos, 3.0D)) {
                                if ((levelSimulatedReader.isStateAtPosition(newPos.below(), state -> state.is(Blocks.GRASS_BLOCK)) && levelSimulatedReader.isStateAtPosition(newPos, BlockState::isAir))) {
                                    if (random.nextInt(9) == 0) {
                                        if (random.nextInt(3) == 0 && levelSimulatedReader.isStateAtPosition(newPos.above(), BlockState::isAir)) {
                                            biConsumer.accept(pos, BayouBluesBlocks.LARGE_CYPRESS_KNEE.get().defaultBlockState().setValue(DoubleCypressKneeBlock.HALF, DoubleBlockHalf.LOWER).setValue(DoubleCypressKneeBlock.WATERLOGGED, levelSimulatedReader.isStateAtPosition(pos, state -> state.is(Blocks.WATER))));
                                            biConsumer.accept(pos.above(), BayouBluesBlocks.LARGE_CYPRESS_KNEE.get().defaultBlockState().setValue(DoubleCypressKneeBlock.HALF, DoubleBlockHalf.UPPER).setValue(DoubleCypressKneeBlock.WATERLOGGED, levelSimulatedReader.isStateAtPosition(pos.above(), state -> state.is(Blocks.WATER))));
                                        } else {
                                            biConsumer.accept(newPos, BayouBluesBlocks.CYPRESS_KNEE.get().defaultBlockState().setValue(CypressKneeBlock.WATERLOGGED, false));
                                        }
                                    }
                                } else if (levelSimulatedReader.isStateAtPosition(newPos, state -> state.is(Blocks.WATER)) && levelSimulatedReader.isStateAtPosition(newPos.below(), BlockState::canOcclude)) {
                                    if (random.nextInt(8) == 0) {
                                        if (random.nextInt(4) != 0 && levelSimulatedReader.isStateAtPosition(newPos.above(), BlockState::isAir)) {
                                            biConsumer.accept(newPos, BayouBluesBlocks.LARGE_CYPRESS_KNEE.get().defaultBlockState().setValue(DoubleCypressKneeBlock.HALF, DoubleBlockHalf.LOWER).setValue(DoubleCypressKneeBlock.WATERLOGGED, levelSimulatedReader.isStateAtPosition(pos, state -> state.is(Blocks.WATER))));
                                            biConsumer.accept(newPos.above(), BayouBluesBlocks.LARGE_CYPRESS_KNEE.get().defaultBlockState().setValue(DoubleCypressKneeBlock.HALF, DoubleBlockHalf.UPPER).setValue(DoubleCypressKneeBlock.WATERLOGGED, levelSimulatedReader.isStateAtPosition(pos.above(), state -> state.is(Blocks.WATER))));
                                        } else {
                                            biConsumer.accept(newPos, BayouBluesBlocks.CYPRESS_KNEE.get().defaultBlockState().setValue(CypressKneeBlock.WATERLOGGED, true));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
