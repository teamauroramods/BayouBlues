package com.teamaurora.bayou_blues.core.fabric;

import com.teamaurora.bayou_blues.core.BayouBlues;
import com.teamaurora.bayou_blues.core.other.BayouBluesSurfaceRules;
import net.fabricmc.api.ModInitializer;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class BayouBluesFabric implements ModInitializer, TerraBlenderApi {
    @Override
    public void onInitialize() {
        BayouBlues.PLATFORM.setup();
    }

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new BayouBluesRegion(BayouBlues.location("overworld"), 2));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, BayouBlues.MOD_ID, BayouBluesSurfaceRules.BAYOU);
    }
}
