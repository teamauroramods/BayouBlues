package com.teamaurora.bayou_blues.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class DirectionalBlockPos {
    public BlockPos pos;
    public Direction direction;

    public DirectionalBlockPos(BlockPos p, Direction a) {
        pos = p;
        direction = a;
    }
}
