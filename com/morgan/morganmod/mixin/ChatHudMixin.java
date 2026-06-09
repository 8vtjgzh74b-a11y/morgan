package com.morgan.morganmod.mixin;

import com.morgan.morganmod.MorganModClient;
import com.morgan.morganmod.SpamDetector;
import net.minecraft.class_2561;
import net.minecraft.class_338;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = {class_338.class})
public class ChatHudMixin {
    @ModifyVariable(method = {"addMessage(Lnet/minecraft/text/Text;)V"}, at = @At(value = "HEAD"), ordinal = 0)
    private class_2561 onAddMessage(class_2561 message) {
        if (MorganModClient.config != null && MorganModClient.config.enabled) {
            return SpamDetector.processMessage(message);
        }

        return message;
    }
}