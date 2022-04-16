package com.teamaurora.bayou_blues.common.world.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamaurora.bayou_blues.common.util.DirectionalBlockPos;
import com.teamaurora.bayou_blues.common.util.TreeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.*;
import java.util.function.BiConsumer;

public class CypressFeature extends Feature<TreeConfiguration> {
    public CypressFeature(Codec<TreeConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> ctx) {
        int height = ctx.random().nextInt(5) + 9;
        boolean bald = ctx.random().nextInt(15) == 0;
        if (ctx.origin().getY() <= -64 || ctx.origin().getY() + height > ctx.level().getHeight() - 1) {
            return false;
        }
        if (!TreeUtil.isValidGround(ctx.level(), ctx.origin().below())) {
            return false;
        }

        List<DirectionalBlockPos> logs = new ArrayList<>();
        List<BlockPos> leaves = new ArrayList<>();

        for (int i = 0; i <= height; i++) {
            logs.add(new DirectionalBlockPos(ctx.origin().above(i), Direction.UP));
        }
        int numBranches = ctx.random().nextInt(4) + 1;
        if (numBranches == 4) numBranches = 2;
        for (int i = 0; i < numBranches; i++) {
            Direction dir = Direction.from2DDataValue(ctx.random().nextInt(4));
            int x;
            if (bald)
                x = ctx.random().nextInt(height - 3) + 3;
            else
                x = ctx.random().nextInt(height - 5) + 3;
            logs.add(new DirectionalBlockPos(ctx.origin().above(x).relative(dir), dir));
            logs.add(new DirectionalBlockPos(ctx.origin().above(x).relative(dir, 2), dir));
            disc2H(ctx.origin().above(x).relative(dir,2), leaves, ctx.random());
            disc1(ctx.origin().above(x+1).relative(dir,2), leaves);
        }
        if (!bald) {
            disc1(ctx.origin().above(height - 1), leaves);
            disc3H(ctx.origin().above(height), leaves, ctx.random());
            disc2(ctx.origin().above(height + 1), leaves);
        }

        List<BlockPos> leavesClean = cleanLeavesArray(leaves, logs);

        boolean flag = true;
        for (DirectionalBlockPos log : logs) {
            if (!TreeUtil.isAirOrLeaves(ctx.level(), log.pos)) {
                flag = false;
            }
        }
        if (!flag) return false;

        TreeUtil.setDirtAt(ctx.level(), ctx.origin().below());

        for (DirectionalBlockPos log : logs) {
            TreeUtil.placeDirectionalLogAt(ctx.level(), log.pos, log.direction, ctx.random(), ctx.config());
        }
        for (BlockPos leaf : leavesClean) {
            TreeUtil.placeLeafAt(ctx.level(), leaf, ctx.random(), ctx.config());
        }

        Set<BlockPos> set = Sets.newHashSet();
        BiConsumer<BlockPos, BlockState> decSet = (blockPos, blockState) -> {
            set.add(blockPos.immutable());
            ctx.level().setBlock(blockPos, blockState, 19);
        };
        BoundingBox mutableBoundingBox = BoundingBox.infinite();

        List<BlockPos> logsPos = new ArrayList<>();
        for (DirectionalBlockPos log : logs) {
            logsPos.add(log.pos);
        }

        if (!ctx.config().decorators.isEmpty()) {
            logsPos.sort(Comparator.comparingInt(Vec3i::getY));
            leavesClean.sort(Comparator.comparingInt(Vec3i::getY));
            ctx.config().decorators.forEach((decorator) -> decorator.place(ctx.level(), decSet, ctx.random(), logsPos, leavesClean));
        }

        return true;
    }

    private void disc1(BlockPos pos, List<BlockPos> leaves) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (Math.abs(x) != 1 || Math.abs(z) != 1) {
                    leaves.add(pos.offset(x, 0, z));
                }
            }
        }
    }

    private void disc2(BlockPos pos, List<BlockPos> leaves) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (Math.abs(x) != 2 || Math.abs(z) != 2) {
                    leaves.add(pos.offset(x, 0, z));
                }
            }
        }
    }

    private void disc2H(BlockPos pos, List<BlockPos> leaves, Random rand) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (Math.abs(x) != 2 || Math.abs(z) != 2) {
                    leaves.add(pos.offset(x, 0, z));
                    if (rand.nextInt(3) == 0) {
                        leaves.add(pos.offset(x, -1, z));
                        if (rand.nextInt(3) == 0) {
                            leaves.add(pos.offset(x, -2, z));
                        }
                    }
                }
            }
        }
    }

    private void disc3H(BlockPos pos, List<BlockPos> leaves, Random rand) {
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                if (Math.abs(x) != 3 || Math.abs(z) != 3) {
                    leaves.add(pos.offset(x, 0, z));
                    if (rand.nextInt(3) == 0) {
                        leaves.add(pos.offset(x, -1, z));
                        if (rand.nextBoolean()) {
                            leaves.add(pos.offset(x, -2, z));
                        }
                    }
                }
            }
        }
    }

    private List<BlockPos> cleanLeavesArray(List<BlockPos> leaves, List<DirectionalBlockPos> logs) {
        List<BlockPos> logsPos = new ArrayList<>();
        for (DirectionalBlockPos log : logs) {
            logsPos.add(log.pos);
        }
        List<BlockPos> newLeaves = new ArrayList<>();
        for (BlockPos leaf : leaves) {
            if (!logsPos.contains(leaf)) {
                newLeaves.add(leaf);
            }
        }
        return newLeaves;
    }
}
