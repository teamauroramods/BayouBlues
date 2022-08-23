package com.teamaurora.bayou_blues.common.levelgen.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamaurora.bayou_blues.common.util.DirectionalBlockPos;
import com.teamaurora.bayou_blues.common.util.TreeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.material.Fluids;

import java.util.*;
import java.util.function.BiConsumer;

public class WaterMegaCypressFeature extends Feature<TreeConfiguration> {

    public WaterMegaCypressFeature(Codec<TreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> ctx) {

        Random random = ctx.random();
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();

        int height = random.nextInt(7) + 18;
        boolean bald = random.nextInt(15) == 0;
        if (origin.getY() <= -64 || origin.getY() + height > level.getHeight() - 1) {
            return false;
        }
        int surfaceY = level.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR, origin).getY();
        int waterY = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, origin).getY();
        if (waterY <= surfaceY) {
            return false;
        }
        BlockPos position = new BlockPos(origin.getX(), waterY, origin.getZ());
        BlockPos bottom = new BlockPos(origin.getX(), surfaceY, origin.getZ());
        for (BlockPos pos2 : BlockPos.betweenClosed(bottom, bottom.offset(1, 0, 1))) {
            if (!isAirOrWaterOrLeaves(level, pos2)) {
                return false;
            }
        }

        List<DirectionalBlockPos> logs = new ArrayList<>();
        List<BlockPos> leaves = new ArrayList<>();

        for (int i = 0; i <= waterY - surfaceY; i++) {
            for (int x = -1; x <= 2; x++) {
                for (int z = -1; z <= 2; z++) {
                    if (i == 0 && level.getFluidState(bottom.offset(x, -1, z)).getType() == Fluids.WATER) {
                        return false;
                    }
                    logs.add(new DirectionalBlockPos(bottom.offset(x, i, z), Direction.UP));
                }
            }
        }
        for (int i = 1; i <= height; i++) {
            if (i <= 2) {
                for (int x = -1; x <= 2; x++) {
                    for (int z = -1; z <= 2; z++) {
                        if (!((x == -1 || x == 2) && (z == -1 || z == 2))) {
                            logs.add(new DirectionalBlockPos(position.offset(x, i, z), Direction.UP));
                        }
                    }
                }
            } else {
                logs.add(new DirectionalBlockPos(position.above(i), Direction.UP));
                logs.add(new DirectionalBlockPos(position.offset(1, i, 0), Direction.UP));
                logs.add(new DirectionalBlockPos(position.offset(0, i, 1), Direction.UP));
                logs.add(new DirectionalBlockPos(position.offset(1, i, 1), Direction.UP));
            }
        }
        int numBranches = random.nextInt(5) + 4;
        for (int i = 0; i < numBranches; i++) {
            int y;
            if (bald)
                y = random.nextInt(height - 5) + 7;
            else
                y = random.nextInt(height - 7) + 7;
            Direction dir = Direction.from2DDataValue(random.nextInt(4));
            if (dir == Direction.NORTH) {
                // min z, x varies
                addBranch(position.offset(random.nextInt(2), y,0), dir, logs, leaves, random);
            } else if (dir == Direction.EAST) {
                // max x, z varies
                addBranch(position.offset(1, y, random.nextInt(2)), dir, logs, leaves, random);
            } else if (dir == Direction.SOUTH) {
                // max z, x varies
                addBranch(position.offset(random.nextInt(2), y,1), dir, logs, leaves, random);
            } else if (dir == Direction.WEST) {
                // min x, z varies
                addBranch(position.offset(0, y, random.nextInt(2)), dir, logs, leaves, random);
            }
        }
        if (bald) {
            int variant = random.nextInt(4);
            switch (variant) {
                case 0:
                    logs.add(new DirectionalBlockPos(position.above(height + 1), Direction.UP));
                    break;
                case 1:
                    logs.add(new DirectionalBlockPos(position.offset(1, height + 1, 0), Direction.UP));
                    break;
                case 2:
                    logs.add(new DirectionalBlockPos(position.offset(0, height + 1, 1), Direction.UP));
                    break;
                case 3:
                    logs.add(new DirectionalBlockPos(position.offset(1, height + 1, 1), Direction.UP));
            }
        } else {
            canopyDisc1(position.above(height - 2), leaves);
            canopyDisc3Bottom(position.above(height - 1), leaves, random);
            canopyDisc3Top(position.above(height), leaves);
            canopyDisc1(position.above(height + 1), leaves);
        }


        List<BlockPos> leavesClean = cleanLeavesArray(leaves, logs);

        boolean flag = true;
        for (DirectionalBlockPos log : logs) {
            if (!TreeUtil.isAirOrLeaves(level, log.pos) && level.getBlockState(log.pos).getBlock() != Blocks.WATER) {
                flag = false;
            }
        }
        if (!flag) return false;

        TreeUtil.setDirtAt(level, position.below());

        for (DirectionalBlockPos log : logs) {
            TreeUtil.placeDirectionalLogAt(level, log.pos, log.direction, random, ctx.config());
        }
        for (BlockPos leaf : leavesClean) {
            TreeUtil.placeLeafAt(level, leaf, random, ctx.config());
        }


        Set<BlockPos> set = Sets.newHashSet();
        BiConsumer<BlockPos, BlockState> biConsumer = (blockPosx, blockState) -> {
            set.add(blockPosx.immutable());
            level.setBlock(blockPosx, blockState, 19);
        };

        List<BlockPos> logsPos = new ArrayList<>();
        for (DirectionalBlockPos log : logs) {
            logsPos.add(log.pos);
        }
        if (!ctx.config().decorators.isEmpty()) {
            logsPos.sort(Comparator.comparingInt(Vec3i::getY));
            leavesClean.sort(Comparator.comparingInt(Vec3i::getY));
            ctx.config().decorators.forEach((decorator) -> decorator.place(level, biConsumer, random, logsPos, leavesClean));
        }
        return true;
    }

    private void addBranch(BlockPos pos, Direction dir, List<DirectionalBlockPos> logs, List<BlockPos> leaves, Random rand) {
        logs.add(new DirectionalBlockPos(pos.relative(dir), dir));
        logs.add(new DirectionalBlockPos(pos.relative(dir, 2), dir));
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

    public static boolean isAirOrWater(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, BlockState::isAir) || level.isStateAtPosition(pos, state -> state.getFluidState().is(FluidTags.WATER));
    }

    public static boolean isAirOrWaterOrLeaves(LevelSimulatedReader world, BlockPos pos) {
        return world.isStateAtPosition(pos, (state) -> isAirOrWater(world, pos) || state.is(BlockTags.LEAVES));
    }
}
