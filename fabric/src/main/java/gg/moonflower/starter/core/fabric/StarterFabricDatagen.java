package gg.moonflower.starter.core.fabric;

import gg.moonflower.starter.core.Starter;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class StarterFabricDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        Starter.PLATFORM.dataSetup(dataGenerator);
    }
}
