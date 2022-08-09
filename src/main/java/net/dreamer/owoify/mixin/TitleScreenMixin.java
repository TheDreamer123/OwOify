package net.dreamer.owoify.mixin;

import net.dreamer.owoify.OwOifyAccessible;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TitleScreen.class)
public class TitleScreenMixin implements OwOifyAccessible {
    private static boolean OwONotEndCredits;

    public boolean getOwOEndCredits() {
        return OwONotEndCredits;
    }

    public void setOwOEndCredits(boolean newVal) {
        OwONotEndCredits = newVal;
    }
}
