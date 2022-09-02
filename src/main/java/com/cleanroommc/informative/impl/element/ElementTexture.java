package com.cleanroommc.informative.impl.element;

import com.cleanroommc.informative.api.IElement;
import com.cleanroommc.informative.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ElementTexture implements IElement {

    public static void render(ResourceLocation location, int x, int y, int w, int h, int u, int v, int tw, int th) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(location);
        RenderUtil.drawTexturedModalRect(x, y, u, v, w, h, tw, th);
    }

    private final ResourceLocation location;
    private final int u, v, w, h, tw, th;

    public ElementTexture(ResourceLocation location, int u, int v, int w, int h, int tw, int th) {
        this.location = location;
        this.u = u;
        this.v = v;
        this.w = w;
        this.h = h;
        this.tw = tw;
        this.th = th;
    }

    @Override
    public void render(int x, int y, int scaledWidth, int scaledHeight) {
        render(location, x, y, w, h, u, v, tw, th);
    }

    @Override
    public int getWidth() {
        return tw;
    }

    @Override
    public int getHeight() {
        return th;
    }

}
