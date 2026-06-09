package com.morgan.morganmod.gui;

import com.morgan.morganmod.config.MorganConfig;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

public class MorganConfigScreen extends class_437 {
    private final MorganConfig config;

    public MorganConfigScreen(MorganConfig config) {
        super((class_2561) class_2561.method_43470((String) "MorganMod Settings"));
        this.config = config;
    }

    protected void method_25426() {
        int centerX = this.field_22789 / 2;
        int centerY = this.field_22790 / 2;

        this.method_37063(
                (class_364) class_4185.method_46430(
                                (class_2561) class_2561.method_43470(
                                        (String) ("Мод: " + (this.config.enabled ? "§aВКЛ" : "§cВЫКЛ"))
                                ),
                                button -> {
                                    this.config.enabled = !this.config.enabled;
                                    this.config.save();

                                    button.method_25355(
                                            (class_2561) class_2561.method_43470(
                                                    (String) ("Мод: " + (this.config.enabled ? "§aВКЛ" : "§cВЫКЛ"))
                                            )
                                    );
                                }
                        )
                        .method_46434(centerX - 75, centerY - 20, 150, 20)
                        .method_46431()
        );

        this.method_37063(
                (class_364) class_4185.method_46430(
                                (class_2561) class_2561.method_43470((String) "Готово"),
                                button -> this.method_25419()
                        )
                        .method_46434(centerX - 50, centerY + 20, 100, 20)
                        .method_46431()
        );
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context, mouseX, mouseY, delta);
        super.method_25394(context, mouseX, mouseY, delta);

        context.method_27534(
                this.field_22793,
                this.field_22785,
                this.field_22789 / 2,
                20,
                0xFFFFFF
        );
    }
}