package com.teamaurora.bayou_blues.core.forge;

import com.teamaurora.bayou_blues.core.BayouBlues;
import net.minecraftforge.fml.common.Mod;

@Mod(BayouBlues.MOD_ID)
public class BayouBluesForge {
    public BayouBluesForge() {
        BayouBlues.PLATFORM.setup();
    }
}
