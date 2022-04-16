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

public class MegaCypressFeature extends Feature<TreeConfiguration> {
    public MegaCypressFeature(Codec<TreeConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> ctx) {
        int height = ctx.random().nextInt(7) + 15;
        boolean bald = ctx.random().nextInt(15) == 0;
        if (ctx.origin().getY() <= -64 || ctx.origin().getY() + height > ctx.level().getHeight() - 1) {
            return false;
        }
        for (BlockPos pos2 : BlockPos.betweenClosed(ctx.origin(), ctx.origin().offset(1, 0, 1))) {
            if (!TreeUtil.isValidGround(ctx.level(), pos2.below())) {
                return false;
            }
        }

        List<DirectionalBlockPos> logs = new ArrayList<>();
        List<BlockPos> leaves = new ArrayList<>();

        for (int i = 0; i <= height; i++) {
            logs.add(new DirectionalBlockPos(ctx.origin().above(i), Direction.UP));
            logs.add(new DirectionalBlockPos(ctx.origin().offset(1, i, 0), Direction.UP));
            logs.add(new DirectionalBlockPos(ctx.origin().offset(0, i, 1), Direction.UP));
            logs.add(new DirectionalBlockPos(ctx.origin().offset(1, i, 1), Direction.UP));
        }
        int numBranches = ctx.random().nextInt(5) + 4;
        for (int i = 0; i < numBranches; i++) {
            int x;
            if (bald)
                x = ctx.random().nextInt(height - 5) + 4;
            else
                x = ctx.random().nextInt(height - 7) + 4;
            Direction dir = Direction.from2DDataValue(ctx.random().nextInt(4));
            if (dir == Direction.NORTH) {
                // min z, x varies
                addBranch(ctx.origin().offset(ctx.random().nextInt(2),x,0), dir, logs, leaves, ctx.random());
            } else if (dir == Direction.EAST) {
                // max x, z varies
                addBranch(ctx.origin().offset(1,x,ctx.random().nextInt(2)), dir, logs, leaves, ctx.random());
            } else if (dir == Direction.SOUTH) {
                // max z, x varies
                addBranch(ctx.origin().offset(ctx.random().nextInt(2),x,1), dir, logs, leaves, ctx.random());
            } else if (dir == Direction.WEST) {
                // min x, z varies
                addBranch(ctx.origin().offset(0,x,ctx.random().nextInt(2)), dir, logs, leaves, ctx.random());
            }
        }
        if (bald) {
            int variant = ctx.random().nextInt(4);
            switch (variant) {
                case 0:
                    logs.add(new DirectionalBlockPos(ctx.origin().above(height+1), Direction.UP));
                    break;
                case 1:
                    logs.add(new DirectionalBlockPos(ctx.origin().offset(1, height+1, 0), Direction.UP));
                    break;
                case 2:
                    logs.add(new DirectionalBlockPos(ctx.origin().offset(0, height+1, 1), Direction.UP));
                    break;
                case 3:
                    logs.add(new DirectionalBlockPos(ctx.origin().offset(1, height+1, 1), Direction.UP));
            }
        } else {
            canopyDisc1(ctx.origin().above(height - 2), leaves);
            canopyDisc3Bottom(ctx.origin().above(height - 1), leaves, ctx.random());
            canopyDisc3Top(ctx.origin().above(height), leaves);
            canopyDisc1(ctx.origin().above(height + 1), leaves);
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

    private void addBranch(BlockPos pos, Direction dir, List<DirectionalBlockPos> logs, List<BlockPos> leaves, Random rand) {
        logs.add(new DirectionalBlockPos(pos.relative(dir), dir));
        logs.add(new DirectionalBlockPos(pos.relative(dir,2), dir));
        disc2H(pos.relative(dir,2), leaves, rand);
        disc1(pos.relative(dir,2).above(), leaves);
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

    private void canopyDisc1(BlockPos pos, List<BlockPos> leaves) {
        for (int x = -1; x <= 2; x++) {
            for (int z = -1; z <= 2; z++) {
                if (!((x == -1 || x == 2) && (z == -1 || z == 2))) {
                    leaves.add(pos.offset(x, 0, z));
                }
            }
        }
    }

    private void canopyDisc3Top(BlockPos pos, List<BlockPos> leaves) {
        for (int x = -3; x <= 4; x++) {
            for (int z = -3; z <= 4; z++) {
                if (!((x <= -2 || x >= 3) && (z <= -2 || z >= 3)) || ((x == -2 || x == 3) && (z == -2 || z == 3))) {
                    leaves.add(pos.offset(x, 0, z));
                }
            }
        }
    }

    private void canopyDisc3Bottom(BlockPos pos, List<BlockPos> leaves, Random rand) {
        for (int x = -3; x <= 4; x++) {
            for (int z = -3; z <= 4; z++) {
                if (!((x == -3 || x == 4) && (z == -3 || z == 4))) {
                    leaves.add(pos.offset(x, 0, z));
                    if (rand.nextBoolean()) {
                        leaves.add(pos.offset(x, -1, z));
                        if (rand.nextInt(3) != 0) {
                            leaves.add(pos.offset(x, -2, z));
                            if (rand.nextBoolean()) {
                                leaves.add(pos.offset(x, -3, z));
                            }
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
