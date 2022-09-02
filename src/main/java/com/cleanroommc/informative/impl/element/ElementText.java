package com.cleanroommc.informative.impl.element;

import com.cleanroommc.informative.api.IElement;
import com.cleanroommc.informative.util.RenderUtil;
import net.minecraft.client.Minecraft;

public class ElementText implements IElement {

    public static void render(String text, int x, int y) {
        RenderUtil.renderText(Minecraft.getMinecraft(), x, y, text);
    }

    private final String text;
    private final int width;

    public ElementText(String text) {
        this.text = text;
        this.width = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }

    @Override
    public void render(int x, int y, int scaledWidth, int scaledHeight) {
        render(text, x, y);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return 10;
    }

}
