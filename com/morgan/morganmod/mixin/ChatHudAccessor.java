package com.morgan.morganmod.mixin;

import java.util.List;
import net.minecraft.class_303;
import net.minecraft.class_338;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = {class_338.class})
public interface ChatHudAccessor {
    @Accessor(value = "messages")
    public List<class_303> getMessages();

    @Accessor(value = "visibleMessages")
    public List<class_303.class_7590> getVisibleMessages();

    @Accessor(value = "scrolledLines")
    public int getScrolledLines();
}