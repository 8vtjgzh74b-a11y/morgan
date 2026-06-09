package com.morgan.morganmod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import net.fabricmc.loader.api.FabricLoader;

public class MorganConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final File CONFIG_FILE = new File(
            FabricLoader.getInstance().getConfigDir().toFile(),
            "morganmod.json"
    );

    public boolean enabled = true;
    public int punishmentCount = 0;

    public static MorganConfig load() {
        if (CONFIG_FILE.exists()) {
            try (Reader reader = new FileReader(CONFIG_FILE)) {
                MorganConfig config = GSON.fromJson(reader, MorganConfig.class);

                if (config != null) {
                    return config;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new MorganConfig();
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}