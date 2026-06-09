package com.morgan.morganmod.gui;

import com.morgan.morganmod.MorganModClient;
import com.morgan.morganmod.PunishmentLogger;
import com.morgan.morganmod.RuleData;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

public class RuleScreen extends class_437 {
    private final String targetNick;

    public RuleScreen(String targetNick) {
        super((class_2561) class_2561.method_43470((String) ("§bMorganMod §7- §c" + targetNick)));
        this.targetNick = targetNick;
    }

    protected void method_25426() {
        int centerX = this.field_22789 / 2;

        this.method_37063(
                (class_364) class_4185.method_46430(
                                (class_2561) class_2561.method_43470((String) "Отмена"),
                                button -> this.method_25419()
                        )
                        .method_46434(centerX - 50, this.field_22790 - 30, 100, 20)
                        .method_46431()
        );

        this.method_37063(
                (class_364) class_4185.method_46430(
                                (class_2561) class_2561.method_43470((String) "§cСброс"),
                                button -> {
                                    if (MorganModClient.config != null) {
                                        MorganModClient.config.punishmentCount = 0;
                                        MorganModClient.config.save();
                                    }
                                }
                        )
                        .method_46434(centerX + 170, 8, 40, 14)
                        .method_46431()
        );

        int muteY = 40;

        for (RuleData rule : RuleData.MUTE_RULES) {
            this.method_37063(
                    (class_364) class_4185.method_46430(
                                    (class_2561) class_2561.method_43470(
                                            (String) ("§e" + rule.id + " - " + rule.shortDesc)
                                    ),
                                    button -> this.applyRule(rule)
                            )
                            .method_46434(centerX - 130, muteY, 260, 20)
                            .method_46431()
            );

            muteY += 24;
        }
    }

    private void applyRule(RuleData rule) {
        class_310 client = class_310.method_1551();

        if (client.field_1724 != null) {
            String fullCommand = rule.commandPrefix + " " + this.targetNick + " " + rule.duration + " " + rule.id;

            client.field_1724.field_3944.method_45729(fullCommand);

            PunishmentLogger.logPunishment(
                    this.targetNick,
                    fullCommand,
                    rule.id,
                    rule.shortDesc
            );

            if (MorganModClient.config != null) {
                ++MorganModClient.config.punishmentCount;
                MorganModClient.config.save();
            }

            client.field_1724.method_7353(
                    (class_2561) class_2561.method_43470(
                            (String) "§a[MorganMod] Наказание выдано."
                    ),
                    false
            );
        }

        this.method_25419();
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context, mouseX, mouseY, delta);
        super.method_25394(context, mouseX, mouseY, delta);

        int centerX = this.field_22789 / 2;

        context.method_27534(
                this.field_22793,
                this.field_22785,
                centerX,
                15,
                0xFFFFFF
        );

        String countText = "§bМутов: §l" + (
                MorganModClient.config != null ? MorganModClient.config.punishmentCount : 0
        );

        context.method_25303(
                this.field_22793,
                countText,
                centerX + 110,
                10,
                0xFFFFFF
        );

        context.method_25300(
                this.field_22793,
                "§7--- §fМУТЫ §7---",
                centerX,
                28,
                0xAAAAAA
        );
    }
}