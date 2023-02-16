package com.HiWord9.MPMenuOpener.mixin;

import com.mojang.bridge.game.GameSession;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public abstract class MPMenuMixin extends Screen implements GameSession {

    protected MPMenuMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "init")
    private void addDisconnectButton(CallbackInfo ci) {
        if (client.world != null) {
            this.addDrawableChild(new ButtonWidget(this.width / 2 - 154 - 24, this.height - 28, 20, 20, Text.translatable("D"), (button) -> {
                this.client.world.disconnect();
                this.client.disconnect();
                client.setScreen((Screen) new MultiplayerScreen(new TitleScreen()));
            }));
        }
    }

    @Inject(at = @At("RETURN"), method = "init")
    private void addReturnButton(CallbackInfo ci) {
        if (client.world != null) {
            this.addDrawableChild(new ButtonWidget(this.width / 2 - 154 - 24 - 24, this.height - 28, 20, 20, Text.translatable("R"), (button) -> {
                client.setScreen((Screen) new GameMenuScreen(true));
            }));
        }
    }
}
