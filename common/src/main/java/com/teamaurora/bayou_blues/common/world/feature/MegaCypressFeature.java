package com.teamaurora.bayou_blues.common.world.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamaurora.bayou_blues.common.util.DirectionalBlockPos;
import com.teamaurora.bayou_blues.common.util.TreeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
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
    public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
        int height = context.random().nextInt(7) + 15;
        boolean bald = context.random().nextInt(15) == 0;
        if (context.origin().getY() <= 0 || context.origin().getY() + height > context.level().getHeight() - 1) {
            return false;
        }
        for (BlockPos pos2 : BlockPos.betweenClosed(context.origin(), context.origin().offset(1, 0, 1))) {
            if (!TreeUtil.isValidGround(context.level(), pos2.below())) {
                return false;
            }
        }

        List<DirectionalBlockPos> logs = new ArrayList<>();
        List<BlockPos> leaves = new ArrayList<>();

        for (int i = 0; i <= height; i++) {
            logs.add(new DirectionalBlockPos(context.origin().above(i), Direction.UP));
            logs.add(new DirectionalBlockPos(context.origin().offset(1, i, 0), Direction.UP));
            logs.add(new DirectionalBlockPos(context.origin().offset(0, i, 1), Direction.UP));
            logs.add(new DirectionalBlockPos(context.origin().offset(1, i, 1), Direction.UP));
        }
        int numBranches = context.random().nextInt(5) + 4;
        for (int i = 0; i < numBranches; i++) {
            int x;
            if (bald)
                x = context.random().nextInt(height - 5) + 4;
            else
                x = context.random().nextInt(height - 7) + 4;
            Direction dir = Direction.from2DDataValue(context.random().nextInt(4));
            if (dir == Direction.NORTH) {
                // min z, x varies
                addBranch(context.origin().offset(context.random().nextInt(2),x,0), dir, logs, leaves, context.random());
            } else if (dir == Direction.EAST) {
                // max x, z varies
                addBranch(context.origin().offset(1,x,context.random().nextInt(2)), dir, logs, leaves, context.random());
            } else if (dir == Direction.SOUTH) {
                // max z, x varies
                addBranch(context.origin().offset(context.random().nextInt(2),x,1), dir, logs, leaves, context.random());
            } else if (dir == Direction.WEST) {
                // min x, z varies
                addBranch(context.origin().offset(0,x,context.random().nextInt(2)), dir, logs, leaves, context.random());
            }
        }
        if (bald) {
            int variant = context.random().nextInt(4);
            switch (variant) {
                case 0:
                    logs.add(new DirectionalBlockPos(context.origin().above(height+1), Direction.UP));
                    break;
                case 1:
                    logs.add(new DirectionalBlockPos(context.origin().offset(1, height+1, 0), Direction.UP));
                    break;
                case 2:
                    logs.add(new DirectionalBlockPos(context.origin().offset(0, height+1, 1), Direction.UP));
                    break;
                case 3:
                    logs.add(new DirectionalBlockPos(context.origin().offset(1, height+1, 1), Direction.UP));
            }
        } else {
            canopyDisc1(context.origin().above(height - 2), leaves);
            canopyDisc3Bottom(context.origin().above(height - 1), leaves, context.random());
            canopyDisc3Top(context.origin().above(height), leaves);
            canopyDisc1(context.origin().above(height + 1), leaves);
        }


        List<BlockPos> leavesClean = cleanLeavesArray(leaves, logs);

        boolean flag = true;
        for (DirectionalBlockPos log : logs) {
            if (!TreeUtil.isAirOrLeaves(context.level(), log.pos)) {
                flag = false;
            }
        }
        if (!flag) return false;

        TreeUtil.setDirtAt(context.level(), context.origin().below());

        for (DirectionalBlockPos log : logs) {
            TreeUtil.placeDirectionalLogAt(context.level(), log.pos, log.direction, context.random(), context.config());
        }
        for (BlockPos leaf : leavesClean) {
            TreeUtil.placeLeafAt(context.level(), leaf, context.random(), context.config());
        }


        Set<BlockPos> set = Sets.newHashSet();
        BiConsumer<BlockPos, BlockState> decSet = (blockPos, blockState) -> {
            set.add(blockPos.immutable());
            context.level().setBlock(blockPos, blockState, 19);
        };
        BoundingBox mutableBoundingBox = BoundingBox.infinite();

        List<BlockPos> logsPos = new ArrayList<>();
        for (DirectionalBlockPos log : logs) {
            logsPos.add(log.pos);
        }

        if (!context.config().decorators.isEmpty()) {
            logsPos.sort(Comparator.comparingInt(Vec3i::getY));
            leavesClean.sort(Comparator.comparingInt(Vec3i::getY));
            context.config().decorators.forEach((decorator) -> decorator.place(context.level(), decSet, context.random(), logsPos, leavesClean));
        }

        return true;
    }

    private void addBranch(BlockPos pos, Direction dir, List<DirectionalBlockPos> logs, List<BlockPos> leaves, Random rand) {
        logs.add(new DirectionalBlockPos(pos.offset(dir.getNormal()), dir));
        logs.add(new DirectionalBlockPos(pos.offset(dir.getNormal()), dir));
        disc2H(pos.offset(dir.getNormal()), leaves, rand);
        disc1(pos.offset(dir.getNormal()).above(), leaves);
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
