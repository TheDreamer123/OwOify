package net.dreamer.owoify.mixin;

import com.google.common.collect.ImmutableList;
import me.shedaniel.autoconfig.AutoConfig;
import net.dreamer.owoify.util.EmotionifyText;
import net.dreamer.owoify.util.OwOifyConfig;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.text.TranslationException;
import net.minecraft.util.Language;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


@Mixin(TranslatableTextContent.class)
public abstract class TranslatableTextContentMixin {
    @Mutable @Shadow @Final private String key;
    @Shadow private List<StringVisitable> translations;
    @Shadow @Nullable private Language languageCache;
    @Shadow protected abstract void forEachPart(String translation,Consumer<StringVisitable> partsConsumer);

    @Inject(at = @At("HEAD"), method = "updateTranslations", cancellable = true)
    public void updateTranslationsInject(CallbackInfo info) {
        OwOifyConfig OwOifyConfig = AutoConfig.getConfigHolder(OwOifyConfig.class).getConfig();

        Language language = Language.getInstance();
        if (language != this.languageCache) {
            this.languageCache = language;
            String string = language.get(this.key);
            char[] stringAsCharArray = string.toCharArray();

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

            try {
                ImmutableList.Builder<StringVisitable> builder = ImmutableList.builder();
                Objects.requireNonNull(builder);
                this.forEachPart(string, builder::add);
                this.translations = builder.build();
            } catch (TranslationException var4) {
                this.translations = ImmutableList.of(StringVisitable.plain(string));
            }
        }

        info.cancel();
    }
}
