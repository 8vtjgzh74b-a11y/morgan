package com.morgan.morganmod;

import java.util.ArrayList;
import java.util.List;

public class RuleData {
    public String id;
    public String duration;
    public String shortDesc;
    public String commandPrefix;

    public static final List<RuleData> MUTE_RULES = new ArrayList<RuleData>();

    public RuleData(String id, String duration, String shortDesc, String commandPrefix) {
        this.id = id;
        this.duration = duration;
        this.shortDesc = shortDesc;
        this.commandPrefix = commandPrefix;
    }

    static {
        MUTE_RULES.add(new RuleData("3.1", "3h", "Упоминание без рекламы", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.2", "30m", "CapsLock", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.2.1", "1h", "Попрошайничество", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.3", "1h", "Спам и флуд", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.5", "12h", "Оск. админ/проекта", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.6", "12h", "Оскорбление родных", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.7", "1h", "Организация флуда", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.8", "3h", "Дискриминация", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.9.1", "8h", "Нацизм/расизм/наркотики/оружие", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.9.2", "12h", "Терроризм", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.9.3", "12h", "Политические призывы", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.12", "6h", "Ввод в заблуждение", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.13", "3h", "Сексуальный характер", "/tempmute"));
        MUTE_RULES.add(new RuleData("3.14", "6h", "Подстрекательство", "/tempmute"));
    }
}