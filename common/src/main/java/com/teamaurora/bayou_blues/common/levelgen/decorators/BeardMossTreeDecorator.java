package com.teamaurora.bayou_blues.common.levelgen.decorators;

import com.mojang.serialization.Codec;
import com.teamaurora.bayou_blues.common.block.BeardMossBlock;
import com.teamaurora.bayou_blues.common.block.BeardMossBlockBlock;
import com.teamaurora.bayou_blues.common.util.TreeUtil;
import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import com.teamaurora.bayou_blues.core.registry.BayouBluesFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class BeardMossTreeDecorator extends TreeDecorator {
    public static final Codec<BeardMossTreeDecorator> CODEC;
    public static final BeardMossTreeDecorator DECORATOR = new BeardMossTreeDecorator();

    @Override
    protected TreeDecoratorType<?> type() {
        return BayouBluesFeatures.BEARD_MOSS.get();
    }

    static {
        CODEC = Codec.unit(() -> DECORATOR);
    }

    @Override
    public void place(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, Random random, List<BlockPos> logs, List<BlockPos> leaves) {
        for (BlockPos pos : logs) {
            if (TreeUtil.isAirOrLeaves(levelSimulatedReader, pos.below())) {
                boolean flag = true;
                int rand1 = random.nextInt(3) + 1;
                for (int i = 0; i < rand1; i++) {
                    if (!TreeUtil.isAirOrLeaves(levelSimulatedReader, pos.below(i + 1))) {
                        flag = false;
                        break;
                    }
                }
                if (!TreeUtil.isAir(levelSimulatedReader, pos.below(rand1 + 1))) flag = false;
                if (flag) {
                    for (int i = 0; i < rand1; i++) {
                        biConsumer.accept(pos.below(i + 1), BayouBluesBlocks.BEARD_MOSS_BLOCK.get().defaultBlockState().setValue(BeardMossBlockBlock.PERSISTENT, false));
                    }
                    int rand2 = random.nextInt(6) + 1;
                    for (int i = 0; i < rand2; i++) {
                        if (!levelSimulatedReader.isStateAtPosition(pos.below(rand1 + i + 1), BlockState::isAir)) {
                            if (i > 0) {
                                biConsumer.accept(pos.below(rand1 + i), BayouBluesBlocks.BEARD_MOSS.get().defaultBlockState());
                            }
                            break;
                        }
                        if (i == rand2 - 1) {
                            biConsumer.accept(pos.below(rand1 + i + 1), BayouBluesBlocks.BEARD_MOSS.get().defaultBlockState());
                        } else {
                            biConsumer.accept(pos.below(rand1 + i + 1), BayouBluesBlocks.BEARD_MOSS.get().defaultBlockState().setValue(BeardMossBlock.HALF, DoubleBlockHalf.UPPER));
                        }
                    }
                }
            }
        }
        for (BlockPos pos : leaves) {
            if (random.nextInt(6) == 0) {
                if (TreeUtil.isAirOrLeaves(levelSimulatedReader, pos.below())) {
                    boolean flag = true;
                    int rand1 = random.nextInt(2) + 1;
                    for (int i = 0; i < rand1; i++) {
                        if (!TreeUtil.isAirOrLeaves(levelSimulatedReader, pos.below())) {
                            flag = false;
                            break;
                        }
                    }
                    if (!TreeUtil.isAir(levelSimulatedReader, pos.below(rand1 + 1))) flag = false;
                    if (flag) {
                        for (int i = 0; i < rand1; i++) {
                            biConsumer.accept(pos.below(i + 1), BayouBluesBlocks.BEARD_MOSS_BLOCK.get().defaultBlockState().setValue(BeardMossBlockBlock.PERSISTENT, false));
                        }
                        int rand2 = random.nextInt(4) + 1;
                        for (int i = 0; i < rand2; i++) {
                            if (!levelSimulatedReader.isStateAtPosition(pos.below(rand1 + i + 1), BlockState::isAir)) {
                                if (i > 0) {
                                    biConsumer.accept(pos.below(rand1 + i), BayouBluesBlocks.BEARD_MOSS.get().defaultBlockState());
                                }
                                break;
                            }
                            if (i == rand2 - 1) {
                                biConsumer.accept(pos.below(rand1 + i + 1), BayouBluesBlocks.BEARD_MOSS.get().defaultBlockState());
                            } else {
                                biConsumer.accept(pos.below(rand1 + i + 1), BayouBluesBlocks.BEARD_MOSS.get().defaultBlockState().setValue(BeardMossBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    }
                }
            }
        }
    }
}