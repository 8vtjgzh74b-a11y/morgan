package com.morgan.morganmod.mixin;

import com.morgan.morganmod.MorganModClient;
import com.morgan.morganmod.gui.RuleScreen;
import java.util.List;
import net.minecraft.class_2583;
import net.minecraft.class_303;
import net.minecraft.class_310;
import net.minecraft.class_338;
import net.minecraft.class_3532;
import net.minecraft.class_408;
import net.minecraft.class_437;
import net.minecraft.class_5481;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = {class_408.class})
public class ChatScreenMixin {
    @Inject(method = {"mouseClicked"}, at = {@At(value = "HEAD")}, cancellable = true)
    private void onMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        class_310 client = class_310.method_1551();

        if (MorganModClient.config != null && !MorganModClient.config.enabled) {
            return;
        }

        if (button != 1) {
            return;
        }

        double x = mouseX;
        double y = mouseY;

        class_338 chatHud = client.field_1705.method_1743();
        class_2583 style = chatHud.method_1816(x, y);

        if (style == null) {
            return;
        }

        ChatHudAccessor accessor = (ChatHudAccessor) chatHud;

        double e = (double) client.method_22683().method_4502() - y - 40.0;
        double lineSpacing = (Double) client.field_1690.method_42546().method_41753() + 1.0;

        int scroll = accessor.getScrolledLines();

        e /= 9.0 * chatHud.method_1814() * lineSpacing;

        int visibleLineIdx = class_3532.method_15357((double) e) + scroll;

        List<class_303.class_7590> visibleMessages = accessor.getVisibleMessages();

        if (visibleLineIdx < 0 || visibleLineIdx >= visibleMessages.size()) {
            return;
        }

        class_5481 orderedText = visibleMessages.get(visibleLineIdx).comp_896();

        StringBuilder sb = new StringBuilder();

        orderedText.accept((index, style1, codePoint) -> {
            sb.appendCodePoint(codePoint);
            return true;
        });

        String content = sb.toString().replaceAll("(?i)§[0-9A-FK-OR]", "");

        String nick = null;

        if (content.contains("| Автор: ")) {
            nick = content.substring(content.lastIndexOf("| Автор: ") + 8).trim();
        } else {
            String[] separators = new String[]{"»", ">", ":", "->", "►"};

            int sepIdx = -1;

            for (String sep : separators) {
                sepIdx = content.lastIndexOf(sep);

                if (sepIdx != -1) {
                    break;
                }
            }

            if (sepIdx > 0) {
                String leftSide = content.substring(0, sepIdx).trim();
                String[] parts = leftSide.split("\\s+");

                if (parts.length > 0) {
                    String lastWord = parts[parts.length - 1].replaceAll("[^a-zA-Z0-9_]+", "");

                    if (lastWord.length() >= 3 && lastWord.length() <= 16) {
                        nick = lastWord;
                    }
                }
            }
        }

        if (nick != null && nick.length() >= 3) {
            client.method_1507((class_437) new RuleScreen(nick));
            cir.setReturnValue(Boolean.valueOf(true));
        }
    }
}