package net.dreamer.owoify.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.dreamer.owoify.OwOifyAccessible;
import net.dreamer.owoify.util.EmotionifyText;
import net.dreamer.owoify.util.OwOifyConfig;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.LiteralTextContent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiteralTextContent.class)
public class LiteralTextContentMixin {
    @Mutable @Shadow @Final private String string;

    @Inject(at = @At("RETURN"), method = "<init>")
    public void constructorInject(CallbackInfo info) {
        OwOifyConfig OwOifyConfig = AutoConfig.getConfigHolder(OwOifyConfig.class).getConfig();
        char[] stringAsCharArray = string.toCharArray();
        TitleScreen titleScreen = new TitleScreen();

        if(string.equals("Copyright Mojang AB. Do not distribute!") || ((OwOifyAccessible) titleScreen).getOwOEndCredits()) return;

        if(OwOifyConfig.owOifyText)
            for(int i = 0; i < stringAsCharArray.length; i++) {
                if(stringAsCharArray[i] == 'r' || stringAsCharArray[i] == 'l') stringAsCharArray[i] = 'w';
                if(stringAsCharArray[i] == 'R' || stringAsCharArray[i] == 'L') stringAsCharArray[i] = 'W';
            }
        string = String.valueOf(stringAsCharArray);

        if(OwOifyConfig.emotionifyText != EmotionifyText.NONE) {
            if(OwOifyConfig.emotionifyText == EmotionifyText.YELLIFY)
                for (int i = 0; i < string.length(); i++) stringAsCharArray[i] = string.toUpperCase().charAt(i);
            if(OwOifyConfig.emotionifyText == EmotionifyText.WHISPERIFY)
                for (int i = 0; i < string.length(); i++) stringAsCharArray[i] = string.toLowerCase().charAt(i);
        }
        string = String.valueOf(stringAsCharArray);

        if(OwOifyConfig.reversifyText) string = String.valueOf(new StringBuilder(string).reverse());

        if(OwOifyConfig.eminemifyText)
            for(int i = 0; i < string.length(); i++)
                if(string.charAt(i) == ' ') {
                    string = String.valueOf(new StringBuilder(string).deleteCharAt(i));
                    i = 0;
                }
    }
}
