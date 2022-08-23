package com.teamaurora.bayou_blues.common.levelgen.decorators;

import com.mojang.serialization.Codec;
import com.teamaurora.bayou_blues.core.registry.BayouBluesFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class SparseLeaveVineDecorator extends TreeDecorator {

    public static final Codec<SparseLeaveVineDecorator> CODEC;
    public static final SparseLeaveVineDecorator DECORATOR = new SparseLeaveVineDecorator();

    static {
        CODEC = Codec.unit(() -> DECORATOR);
    }

    protected TreeDecoratorType<?> type() {
        return BayouBluesFeatures.SPARSE_LEAVE_VINES.get();
    }

    public void place(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, Random random, List<BlockPos> list, List<BlockPos> list2) {
        list2.forEach((blockPos) -> {
            BlockPos blockPos2;
            if (random.nextInt(16) == 0) {
                blockPos2 = blockPos.west();
                if (Feature.isAir(levelSimulatedReader, blockPos2)) {
                    addHangingVine(levelSimulatedReader, blockPos2, VineBlock.EAST, biConsumer);
                }
            }

            if (random.nextInt(16) == 0) {
                blockPos2 = blockPos.east();
                if (Feature.isAir(levelSimulatedReader, blockPos2)) {
                    addHangingVine(levelSimulatedReader, blockPos2, VineBlock.WEST, biConsumer);
                }
            }

            if (random.nextInt(16) == 0) {
                blockPos2 = blockPos.north();
                if (Feature.isAir(levelSimulatedReader, blockPos2)) {
                    addHangingVine(levelSimulatedReader, blockPos2, VineBlock.SOUTH, biConsumer);
                }
            }

            if (random.nextInt(16) == 0) {
                blockPos2 = blockPos.south();
                if (Feature.isAir(levelSimulatedReader, blockPos2)) {
                    addHangingVine(levelSimulatedReader, blockPos2, VineBlock.NORTH, biConsumer);
                }
            }

        });
    }

    private static void addHangingVine(LevelSimulatedReader levelSimulatedReader, BlockPos blockPos, BooleanProperty booleanProperty, BiConsumer<BlockPos, BlockState> biConsumer) {
        placeVine(biConsumer, blockPos, booleanProperty);
        int i = 4;

        for(blockPos = blockPos.below(); Feature.isAir(levelSimulatedReader, blockPos) && i > 0; --i) {
            placeVine(biConsumer, blockPos, booleanProperty);
            blockPos = blockPos.below();
        }

    }
}
