package com.morgan.morganmod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_124;
import net.minecraft.class_2558;
import net.minecraft.class_2561;
import net.minecraft.class_2568;
import net.minecraft.class_2583;
import net.minecraft.class_5250;

public class SpamDetector {
    private static final Map<String, List<MessageData>> playerMessages = new HashMap<String, List<MessageData>>();

    public static class_2561 processMessage(class_2561 message) {
        String rawContent = message.getString();
        String cleanContent = rawContent.replaceAll("(?i)§[0-9A-FK-OR]", "");

        String nick = null;
        String msgContent = null;

        String[] separators = new String[]{"»", "> ", ": ", "->", "►", "| Автор: "};

        int sepIdx = -1;
        int sepLen = 1;

        for (String sep : separators) {
            sepIdx = cleanContent.lastIndexOf(sep);

            if (sepIdx == -1) {
                continue;
            }

            sepLen = sep.length();
            break;
        }

        if (sepIdx > 0) {
            if (cleanContent.contains("| Автор: ")) {
                nick = cleanContent.substring(cleanContent.indexOf("| Автор:") + 8).trim();
                msgContent = cleanContent.substring(0, cleanContent.indexOf("| Автор:")).trim();
            } else {
                String leftSide = cleanContent.substring(0, sepIdx).trim();
                msgContent = cleanContent.substring(sepIdx + sepLen).trim();

                String[] parts = leftSide.split("\\s+");

                if (parts.length > 0) {
                    String lastWord = parts[parts.length - 1];
                    lastWord = lastWord.replaceAll("[^a-zA-Z0-9_]+", "");

                    if (lastWord.length() >= 3 && lastWord.length() <= 16) {
                        nick = lastWord;
                    }
                }
            }
        }

        if (nick != null && msgContent != null && !msgContent.isEmpty()) {
            long now = System.currentTimeMillis();

            List<MessageData> messages = playerMessages.computeIfAbsent(nick, k -> new ArrayList<MessageData>());

            messages.removeIf(m -> now - m.timestamp > 60000L);

            int existingCount = 0;
            String lowerMsg = msgContent.toLowerCase();

            for (MessageData m2 : messages) {
                if (!m2.content.toLowerCase().equals(lowerMsg)) {
                    continue;
                }

                ++existingCount;
            }

            messages.add(new MessageData(msgContent, now));

            int totalCount = existingCount + 1;

            if (totalCount >= 3) {
                class_5250 original = message.method_27661();

                class_5250 spamButton = class_2561.method_43470((String)(" [" + totalCount + "]"))
                        .method_10862(
                                class_2583.field_24360
                                        .method_10977(class_124.field_1061)
                                        .method_10982(Boolean.valueOf(true))
                                        .method_10958(
                                                new class_2558(
                                                        class_2558.class_2559.field_11750,
                                                        "/morgan_automute " + nick
                                                )
                                        )
                                        .method_10949(
                                                new class_2568(
                                                        class_2568.class_5247.field_24342,
                                                        (Object) class_2561.method_43470(
                                                                (String) "§cКликни, чтобы замутить за спам и флуд (3.3)"
                                                        )
                                                )
                                        )
                        );

                return original.method_10852((class_2561) spamButton);
            }
        }

        return message;
    }

    public static List<String> getFormattedHistory(String nick) {
        ArrayList<String> history = new ArrayList<String>();

        List<MessageData> messages = playerMessages.get(nick);

        if (messages != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            for (MessageData m : messages) {
                history.add("[" + sdf.format(new Date(m.timestamp)) + "] - " + m.content);
            }
        }

        return history;
    }

    private static class MessageData {
        String content;
        long timestamp;

        public MessageData(String content, long timestamp) {
            this.content = content;
            this.timestamp = timestamp;
        }
    }
}