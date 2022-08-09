package net.dreamer.owoify.util;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "owoify")
@Config.Gui.Background("minecraft:textures/block/bedrock.png")
public class OwOifyConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip public boolean owOifyText = true;
    @ConfigEntry.Gui.Tooltip(count = 5) public EmotionifyText emotionifyText = EmotionifyText.NONE;
    @ConfigEntry.Gui.Tooltip public boolean reversifyText = false;
    @ConfigEntry.Gui.Tooltip public boolean eminemifyText = false;
}
