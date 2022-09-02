package com.cleanroommc.informative.impl.element;

import com.cleanroommc.informative.api.IElement;
import com.cleanroommc.informative.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class ElementSprite implements IElement {

    public static void render(String location, int x, int y, int w, int h) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        RenderUtil.drawTexturedModalRect(x, y, sprite, w, h);
    }

    private final String location;
    private final int w, h;

    public ElementSprite(ResourceLocation location, int w, int h) {
        this(location.toString(), w, h);
    }

    public ElementSprite(String location, int w, int h) {
        this.location = location;
        this.w = w;
        this.h = h;
    }

    @Override
    public void render(int x, int y, int scaledWidth, int scaledHeight) {
        render(location, x, y, w, h);
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

}
