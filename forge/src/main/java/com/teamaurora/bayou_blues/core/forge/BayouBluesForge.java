package com.teamaurora.bayou_blues.core.forge;

import com.teamaurora.bayou_blues.core.BayouBlues;
import com.teamaurora.bayou_blues.core.other.BayouBluesSurfaceRules;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(BayouBlues.MOD_ID)
public class BayouBluesForge {
    public BayouBluesForge() {
        BayouBlues.PLATFORM.setup();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> Regions.register(new BayouBluesRegion(BayouBlues.location("overworld"), 2)));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, BayouBlues.MOD_ID, BayouBluesSurfaceRules.BAYOU);
    }
}
