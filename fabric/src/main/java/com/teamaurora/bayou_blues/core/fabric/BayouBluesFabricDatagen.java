package com.teamaurora.bayou_blues.core.fabric;

import com.teamaurora.bayou_blues.core.BayouBlues;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BayouBluesFabricDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        BayouBlues.PLATFORM.dataSetup(dataGenerator);
    }
}
