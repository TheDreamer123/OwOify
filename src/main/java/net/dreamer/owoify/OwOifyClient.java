package net.dreamer.owoify;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.dreamer.owoify.util.OwOifyConfig;
import net.fabricmc.api.ClientModInitializer;

public class OwOifyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AutoConfig.register(OwOifyConfig.class, GsonConfigSerializer::new);
    }
}
