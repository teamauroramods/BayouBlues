package com.teamaurora.bayou_blues.core.fabric;

import com.teamaurora.bayou_blues.core.BayouBlues;
import net.fabricmc.api.ModInitializer;

public class BayouBluesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BayouBlues.PLATFORM.setup();
    }
}
