package net.dreamer.owoify.mixin;

import net.dreamer.owoify.OwOifyAccessible;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreditsScreen.class)
public class CreditsScreenMixin {
    @Inject(at = @At("RETURN"), method = "<init>")
    public void constructorInject(boolean endCredits,Runnable finishAction,CallbackInfo ci) {
        if(!endCredits) {
            TitleScreen titleScreen = new TitleScreen();
            ((OwOifyAccessible) titleScreen).setOwOEndCredits(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "closeScreen")
    public void closeScreenInject(CallbackInfo info) {
        TitleScreen titleScreen = new TitleScreen();
        if(((OwOifyAccessible) titleScreen).getOwOEndCredits()) ((OwOifyAccessible) titleScreen).setOwOEndCredits(false);
    }
}
