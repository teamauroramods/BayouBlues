package com.teamaurora.bayou_blues.core.other;

import com.teamaurora.bayou_blues.core.registry.BayouBluesBiomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class BayouBluesSurfaceRules {

    public static final SurfaceRules.ConditionSource Y_IS_62 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0);
    public static final SurfaceRules.ConditionSource Y_IS_63 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);

    public static final SurfaceRules.RuleSource SEA_LEVEL_WATER_NOISE = SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
            SurfaceRules.ifTrue(Y_IS_62,
                    SurfaceRules.ifTrue(SurfaceRules.not(Y_IS_63),
                            SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0D),
                                    SurfaceRules.state(Blocks.WATER.defaultBlockState())
                            )
                    )
            )
    );

    public static final SurfaceRules.RuleSource BAYOU = SurfaceRules.ifTrue(SurfaceRules.isBiome(BayouBluesBiomes.BAYOU), SEA_LEVEL_WATER_NOISE);
}
