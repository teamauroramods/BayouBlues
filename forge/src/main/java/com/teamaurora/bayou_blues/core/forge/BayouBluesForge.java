package com.teamaurora.bayou_blues.core.forge;

import com.teamaurora.bayou_blues.core.BayouBlues;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import terrablender.api.Regions;

@Mod(BayouBlues.MOD_ID)
public class BayouBluesForge {
    public BayouBluesForge() {
        BayouBlues.PLATFORM.setup();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> Regions.register(new BayouBluesRegion(BayouBlues.location("overworld"), 2)));
    }
}
