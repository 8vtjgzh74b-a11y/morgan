package com.morgan.morganmod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;

public class PunishmentLogger {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void logPunishment(String targetNick, String command, String ruleId, String ruleDesc) {
        File modDir = new File(FabricLoader.getInstance().getGameDir().toFile(), "MorganMod");
        File logDir = new File(modDir, "logs");

        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        String currentDate = LocalDateTime.now().format(DATE_FORMATTER);
        File logFile = new File(logDir, currentDate + ".log");

        try (FileWriter writer = new FileWriter(logFile, true)) {
            String timestamp = LocalDateTime.now().format(FORMATTER);

            writer.write("==================================================\n");
            writer.write("[" + timestamp + "] Выдано наказание: " + command + "\n");
            writer.write("Игрок: " + targetNick + "\n");
            writer.write("Пункт правил: " + ruleId + " (" + ruleDesc + ")\n");

            List<String> history = SpamDetector.getFormattedHistory(targetNick);

            if (!history.isEmpty()) {
                writer.write("Последние сообщения от " + targetNick + ":\n");

                for (String msg : history) {
                    writer.write("  " + msg + "\n");
                }
            }

            writer.write("==================================================\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}