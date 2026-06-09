package com.morgan.morganmod;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.morgan.morganmod.config.MorganConfig;
import com.morgan.morganmod.gui.RuleScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.class_2172;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_3675;
import net.minecraft.class_437;

public class MorganModClient implements ClientModInitializer {
    public static MorganConfig config;
    private static boolean f4WasPressed;
    private static String pendingPushNick;

    public void onInitializeClient() {
        config = MorganConfig.load();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    (LiteralArgumentBuilder) ClientCommandManager.literal((String) "morgan_automute")
                            .then(
                                    ClientCommandManager.argument(
                                                    (String) "target",
                                                    (ArgumentType) StringArgumentType.word()
                                            )
                                            .suggests((context, builder) -> {
                                                class_310 client = class_310.method_1551();

                                                if (client.method_1562() != null) {
                                                    return class_2172.method_9264(
                                                            client.method_1562()
                                                                    .method_2880()
                                                                    .stream()
                                                                    .map(entry -> entry.method_2966().getName()),
                                                            (SuggestionsBuilder) builder
                                                    );
                                                }

                                                return builder.buildFuture();
                                            })
                                            .executes(context -> {
                                                String targetNick = StringArgumentType.getString(
                                                        (CommandContext) context,
                                                        (String) "target"
                                                );

                                                MorganModClient.executeMute(targetNick);
                                                return 1;
                                            })
                            )
            );

            dispatcher.register(
                    (LiteralArgumentBuilder) ClientCommandManager.literal((String) "push")
                            .then(
                                    ClientCommandManager.argument(
                                                    (String) "target",
                                                    (ArgumentType) StringArgumentType.word()
                                            )
                                            .suggests((context, builder) -> {
                                                class_310 client = class_310.method_1551();

                                                if (client.method_1562() != null) {
                                                    return class_2172.method_9264(
                                                            client.method_1562()
                                                                    .method_2880()
                                                                    .stream()
                                                                    .map(entry -> entry.method_2966().getName()),
                                                            (SuggestionsBuilder) builder
                                                    );
                                                }

                                                return builder.buildFuture();
                                            })
                                            .executes(context -> {
                                                String targetNick = StringArgumentType.getString(
                                                        (CommandContext) context,
                                                        (String) "target"
                                                );

                                                pendingPushNick = targetNick;
                                                return 1;
                                            })
                            )
            );
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.method_22683() == null) {
                return;
            }

            boolean f4IsPressed = class_3675.method_15987(
                    (long) client.method_22683().method_4490(),
                    (int) 293
            );

            if (f4IsPressed && !f4WasPressed && client.field_1755 == null) {
                MorganModClient.config.enabled = !MorganModClient.config.enabled;
                config.save();

                if (client.field_1724 != null) {
                    client.field_1724.method_7353(
                            (class_2561) class_2561.method_43470(
                                    (String) (
                                            "§b[MorganMod] §fМод: "
                                                    + (MorganModClient.config.enabled ? "§aВКЛ" : "§cВЫКЛ")
                                    )
                            ),
                            true
                    );
                }
            }

            f4WasPressed = f4IsPressed;

            if (pendingPushNick != null && client.field_1755 == null) {
                client.method_1507((class_437) new RuleScreen(pendingPushNick));
                pendingPushNick = null;
            }
        });
    }

    public static void executeMute(String targetNick) {
        class_310 client = class_310.method_1551();

        if (client.field_1724 == null) {
            return;
        }

        String fullCommand = "/tempmute " + targetNick + " 1h 3.3";

        client.field_1724.field_3944.method_45729(fullCommand);

        PunishmentLogger.logPunishment(
                targetNick,
                fullCommand,
                "3.3",
                "Спам и флуд (Автомут)"
        );

        if (config != null) {
            ++MorganModClient.config.punishmentCount;
            config.save();
        }

        client.field_1724.method_7353(
                (class_2561) class_2561.method_43470(
                        (String) ("§a[MorganMod] Игрок " + targetNick + " получил автомут за спам и флуд.")
                ),
                false
        );
    }

    static {
        f4WasPressed = false;
        pendingPushNick = null;
    }
}