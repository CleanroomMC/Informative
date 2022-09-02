package com.cleanroommc.informative.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.awt.*;

public class RenderUtil {

    public static void renderEntity(Entity entity, int xPos, int yPos, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.color(1f, 1f, 1f);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(xPos + 8, yPos + 24, 50F);
        GlStateManager.scale(-scale, scale, scale);
        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(135F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
        entity.rotationPitch = 0.0F;
        GlStateManager.translate(0.0F, (float) entity.getYOffset() + (entity instanceof EntityHanging ? 0.5F : 0.0F), 0.0F);
        Minecraft.getMinecraft().getRenderManager().playerViewY = 180F;
        Minecraft.getMinecraft().getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        GlStateManager.enableDepth();
        GlStateManager.disableColorMaterial();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static boolean renderObject(Minecraft mc, int x, int y, Object object, boolean highlight) {
        if (object instanceof Entity) {
            renderEntity((Entity) object, x, y, 10);
            return true;
        }
        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
        return renderObject(mc, itemRender, x, y, object, highlight, 200);
    }

    public static boolean renderObject(Minecraft mc, RenderItem itemRender, int x, int y, Object itm, boolean highlight, float lvl) {
        itemRender.zLevel = lvl;
        if (itm == null) {
            return renderItemStack(mc, itemRender, null, x, y, "", highlight);
        }
        if (itm instanceof Item) {
            return renderItemStack(mc, itemRender, new ItemStack((Item) itm, 1), x, y, "", highlight);
        }
        if (itm instanceof Block) {
            return renderItemStack(mc, itemRender, new ItemStack((Block) itm, 1), x, y, "", highlight);
        }
        if (itm instanceof ItemStack) {
            return renderItemStackWithCount(mc, itemRender, (ItemStack) itm, x, y, highlight);
        }
        if (itm instanceof TextureAtlasSprite) {
            return renderIcon(mc, itemRender, (TextureAtlasSprite) itm, x, y, highlight);
        }
        return renderItemStack(mc, itemRender, ItemStack.EMPTY, x, y, "", highlight);
    }

    public static boolean renderIcon(Minecraft mc, RenderItem itemRender, TextureAtlasSprite itm, int xo, int yo, boolean highlight) {
        //itemRender.renderIcon(xo, yo, itm, 16, 16); //TODO: Make
        return true;
    }

    public static boolean renderItemStackWithCount(Minecraft mc, RenderItem itemRender, ItemStack itm, int xo, int yo, boolean highlight) {
        if (itm.getCount() <= 1) {
            return renderItemStack(mc, itemRender, itm, xo, yo, "", highlight);
        } else {
            return renderItemStack(mc, itemRender, itm, xo, yo, String.valueOf(itm.getCount()), highlight);
        }
    }

    public static boolean renderItemStack(Minecraft mc, RenderItem itemRender, ItemStack itm, int x, int y, String txt, boolean highlight) {
        GlStateManager.color(1F, 1F, 1F);

        boolean rc = false;
        if (highlight) {
            GlStateManager.disableLighting();
            drawVerticalGradientRect(x, y, x + 16, y + 16, 0x80ffffff, 0xffffffff);
        }
        if (!itm.isEmpty()) {
            rc = true;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 32.0F);
            GlStateManager.color(1F, 1F, 1F, 1F);
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableLighting();
            short short1 = 240;
            short short2 = 240;
            RenderHelper.enableGUIStandardItemLighting();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, short1, short2);
            itemRender.renderItemAndEffectIntoGUI(itm, x, y);
            itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, itm, x, y, txt);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableLighting();
        }

        return rc;
    }

    public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float zLevel = 0.0F;
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(left + right, top, zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos(left, top, zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos(left, top + bottom, zLevel).color(f5, f6, f7, f4).endVertex();
        buffer.pos(left + right, top + bottom, zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors.
     * x2 and y2 are not included.
     */
    public static void drawVerticalGradientRect(int x1, int y1, int x2, int y2, int color1, int color2) {
        //        this.zLevel = 300.0F;
        float zLevel = 0.0f;
        float f = (color1 >> 24 & 255) / 255.0F;
        float f1 = (color1 >> 16 & 255) / 255.0F;
        float f2 = (color1 >> 8 & 255) / 255.0F;
        float f3 = (color1 & 255) / 255.0F;
        float f4 = (color2 >> 24 & 255) / 255.0F;
        float f5 = (color2 >> 16 & 255) / 255.0F;
        float f6 = (color2 >> 8 & 255) / 255.0F;
        float f7 = (color2 & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x2, y1, zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos(x1, y1, zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos(x1, y2, zLevel).color(f5, f6, f7, f4).endVertex();
        buffer.pos(x2, y2, zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    /**
     * Draws a rectangle with a horizontal gradient between the specified colors.
     * x2 and y2 are not included.
     */
    public static void drawHorizontalGradientRect(int x1, int y1, int x2, int y2, int color1, int color2) {
        //        this.zLevel = 300.0F;
        float zLevel = 0.0f;
        float f = (color1 >> 24 & 255) / 255.0F;
        float f1 = (color1 >> 16 & 255) / 255.0F;
        float f2 = (color1 >> 8 & 255) / 255.0F;
        float f3 = (color1 & 255) / 255.0F;
        float f4 = (color2 >> 24 & 255) / 255.0F;
        float f5 = (color2 >> 16 & 255) / 255.0F;
        float f6 = (color2 >> 8 & 255) / 255.0F;
        float f7 = (color2 & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x1, y1, zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos(x1, y2, zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos(x2, y2, zLevel).color(f5, f6, f7, f4).endVertex();
        buffer.pos(x2, y1, zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawHorizontalLine(int x1, int y1, int x2, int color) {
        Gui.drawRect(x1, y1, x2, y1 + 1, color);
    }

    public static void drawVerticalLine(int x1, int y1, int y2, int color) {
        Gui.drawRect(x1, y1, x1 + 1, y2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the left point
    public static void drawLeftTriangle(int x, int y, int color) {
        drawVerticalLine(x, y, y, color);
        drawVerticalLine(x + 1, y - 1, y + 1, color);
        drawVerticalLine(x + 2, y - 2, y + 2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the right point
    public static void drawRightTriangle(int x, int y, int color) {
        drawVerticalLine(x, y, y, color);
        drawVerticalLine(x - 1, y - 1, y + 1, color);
        drawVerticalLine(x - 2, y - 2, y + 2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the top point
    public static void drawUpTriangle(int x, int y, int color) {
        drawHorizontalLine(x, y, x, color);
        drawHorizontalLine(x - 1, y + 1, x + 1, color);
        drawHorizontalLine(x - 2, y + 2, x + 2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the bottom point
    public static void drawDownTriangle(int x, int y, int color) {
        drawHorizontalLine(x, y, x, color);
        drawHorizontalLine(x - 1, y - 1, x + 1, color);
        drawHorizontalLine(x - 2, y - 2, x + 2, color);
    }

    /**
     * Draw a button box. x2 and y2 are not included.
     */
    public static void drawFlatButtonBox(int x1, int y1, int x2, int y2, int bright, int average, int dark) {
        drawBeveledBox(x1, y1, x2, y2, bright, dark, average);
    }

    /**
     * Draw a button box. x2 and y2 are not included.
     */
    public static void drawFlatButtonBoxGradient(int x1, int y1, int x2, int y2, int bright, int average1, int average2, int dark) {
        drawVerticalGradientRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, average2, average1);
        drawHorizontalLine(x1, y1, x2 - 1, bright);
        drawVerticalLine(x1, y1, y2 - 1, bright);
        drawVerticalLine(x2 - 1, y1, y2 - 1, dark);
        drawHorizontalLine(x1, y2 - 1, x2, dark);
    }

    /**
     * Draw a beveled box. x2 and y2 are not included.
     */
    public static void drawBeveledBox(int x1, int y1, int x2, int y2, int topleftcolor, int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
        }
        drawHorizontalLine(x1, y1, x2 - 1, topleftcolor);
        drawVerticalLine(x1, y1, y2 - 1, topleftcolor);
        drawVerticalLine(x2 - 1, y1, y2 - 1, botrightcolor);
        drawHorizontalLine(x1, y2 - 1, x2, botrightcolor);
    }

    /**
     * Draw a thick beveled box. x2 and y2 are not included.
     */
    public static void drawThickBeveledBox(int x1, int y1, int x2, int y2, int thickness, int topleftcolor, int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
        }
        Gui.drawRect(x1, y1, x2 - 1, y1 + thickness, topleftcolor);
        Gui.drawRect(x1, y1, x1 + thickness, y2 - 1, topleftcolor);
        Gui.drawRect(x2 - thickness, y1, x2, y2 - 1, botrightcolor);
        Gui.drawRect(x1, y2 - thickness, x2, y2, botrightcolor);
    }

    public static void drawThickBeveledBoxGradient(int x1, int y1, int x2, int y2, int thickness, int topleftcolor, int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
        }
        Gui.drawRect(x1, y1, x2 - 1, y1 + thickness, topleftcolor);
        drawVerticalGradientRect(x1, y1, x1 + thickness, y2 - 1, topleftcolor, botrightcolor);
        drawVerticalGradientRect(x2 - thickness, y1, x2, y2 - 1, topleftcolor, botrightcolor);
        Gui.drawRect(x1, y2 - thickness, x2, y2, botrightcolor);
    }

    public static void drawOutlineBox(int x1, int y1, int x2, int y2, int thickness, int fillcolor) {

        Gui.drawRect(x1 + 1, y1, x2 - 1, y1 + thickness, fillcolor);
        Gui.drawRect(x1, y1 + 1 , x1 + thickness, y2 - 1, fillcolor);
        Gui.drawRect(x2 - thickness, y1 + 1, x2, y2 - 1, fillcolor);
        Gui.drawRect(x1 + 1, y2 - thickness, x2 - 1, y2, fillcolor);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, int twidth, int theight) {
        float zLevel = 0.01f;
        float f = (1.0F / twidth);
        float f1 = (1.0F / theight);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, zLevel).tex(u + 0 * f, (v + height) * f1).endVertex();
        buffer.pos(x + width, y + height, zLevel).tex((u + width) * f, (v + height) * f1).endVertex();
        buffer.pos(x + width, y, zLevel).tex((u + width) * f, v * f1).endVertex();
        buffer.pos(x, y, zLevel).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
        float zLevel = 0.01f;
        float f = (1 / 256.0F);
        float f1 = (1 / 256.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, zLevel).tex(u + 0 * f, (v + height) * f1).endVertex();
        buffer.pos(x + width, y + height, zLevel).tex((u + width) * f, (v + height) * f1).endVertex();
        buffer.pos(x + width, y, zLevel).tex((u + width) * f, v * f1).endVertex();
        buffer.pos(x, y, zLevel).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawTexturedModalRect(int x, int y, TextureAtlasSprite sprite, int width, int height) {
        float zLevel = 0.01f;
        float u1 = sprite.getMinU();
        float v1 = sprite.getMinV();
        float u2 = sprite.getMaxU();
        float v2 = sprite.getMaxV();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, zLevel).tex(u1, v1).endVertex();
        buffer.pos(x + width, y + height, zLevel).tex(u1, v2).endVertex();
        buffer.pos(x + width, y, zLevel).tex(u2, v2).endVertex();
        buffer.pos(x, y, zLevel).tex(u2, v1).endVertex();
        tessellator.draw();
    }

    public static void renderBillboardQuadBright(double scale) {
        int brightness = 240 & 65535;
        GlStateManager.pushMatrix();
        rotateToPlayer();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
        buffer.pos(-scale, -scale, 0.0D).tex(0.0D, 0.0D).lightmap(0, brightness).color(255, 255, 255, 128).endVertex();
        buffer.pos(-scale, scale, 0.0D).tex(0.0D, 1.0D).lightmap(0, brightness).color(255, 255, 255, 128).endVertex();
        buffer.pos(scale, scale, 0.0D).tex(1.0D, 1.0D).lightmap(0, brightness).color(255, 255, 255, 128).endVertex();
        buffer.pos(scale, -scale, 0.0D).tex(1.0D, 0.0D).lightmap(0, brightness).color(255, 255, 255, 128).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
    }

    public static void renderBillboardQuad(double scale) {
        GlStateManager.pushMatrix();

        rotateToPlayer();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-scale, -scale, 0).tex(0, 0).endVertex();
        buffer.pos(-scale, +scale, 0).tex(0, 1).endVertex();

        buffer.pos(+scale, +scale, 0).tex(1, 1).endVertex();

        buffer.pos(+scale, -scale, 0).tex(1, 0).endVertex();

        tessellator.draw();
        GlStateManager.popMatrix();
    }

    public static void renderBillboardQuadWithRotation(float rot, double scale) {
        GlStateManager.pushMatrix();

        rotateToPlayer();

        GlStateManager.rotate(rot, 0, 0, 1);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-scale, -scale, 0).tex(0, 0).endVertex();
        buffer.pos(-scale, +scale, 0).tex(0, 1).endVertex();

        buffer.pos(+scale, +scale, 0).tex(1, 1).endVertex();

        buffer.pos(+scale, -scale, 0).tex(1, 0).endVertex();

        tessellator.draw();
        GlStateManager.popMatrix();
    }

    public static void rotateToPlayer() {
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
    }

    public static boolean renderItemStack(Minecraft mc, RenderItem itemRender, ItemStack itm, int x, int y, String txt) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        boolean rc = true;
        if (!itm.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 32.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableLighting();
            short short1 = 240;
            short short2 = 240;
            RenderHelper.enableGUIStandardItemLighting();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, short1, short2);
            itemRender.renderItemAndEffectIntoGUI(itm, x, y);
            renderItemOverlayIntoGUI(mc.fontRenderer, itm, x, y, txt, txt.length() - 2);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableLighting();
        }

        return rc;
    }

    /**
     * Renders the stack size and/or damage bar for the given ItemStack.
     */
    public static void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, @Nullable String text,
                                                int scaled) {
        if (!stack.isEmpty()) {
            if (stack.getCount() != 1 || text != null) {
                String s = text == null ? String.valueOf(stack.getCount()) : text;
                if (text == null && stack.getCount() < 1) {
                    s = TextFormatting.RED + String.valueOf(stack.getCount());
                }

                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                if (scaled >= 2) {
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(.5f, .5f, .5f);
                    fr.drawStringWithShadow(s, ((xPosition + 19 - 2) * 2 - 1 - fr.getStringWidth(s)), yPosition * 2 + 24, 16777215);
                    GlStateManager.popMatrix();
                } else if (scaled == 1) {
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(.75f, .75f, .75f);
                    fr.drawStringWithShadow(s, ((xPosition - 2) * 1.34f + 24 - fr.getStringWidth(s)), yPosition * 1.34f + 14, 16777215);
                    GlStateManager.popMatrix();
                } else {
                    fr.drawStringWithShadow(s, (xPosition + 19 - 2 - fr.getStringWidth(s)), (yPosition + 6 + 3), 16777215);
                }
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                // Fixes opaque cooldown overlay a bit lower
                // TODO: check if enabled blending still screws things up down the line.
                GlStateManager.enableBlend();
            }

            if (stack.getItem().showDurabilityBar(stack)) {
                double health = stack.getItem().getDurabilityForDisplay(stack);
                int j = (int) Math.round(13.0D - health * 13.0D);
                int i = (int) Math.round(255.0D - health * 255.0D);
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder vertexbuffer = tessellator.getBuffer();
                draw(vertexbuffer, xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
                draw(vertexbuffer, xPosition + 2, yPosition + 13, 12, 1, (255 - i) / 4, 64, 0, 255);
                draw(vertexbuffer, xPosition + 2, yPosition + 13, j, 1, 255 - i, i, 0, 255);
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }

            EntityPlayerSP entityplayersp = Minecraft.getMinecraft().player;
            float f = entityplayersp == null ? 0.0F : entityplayersp.getCooldownTracker().getCooldown(stack.getItem(), Minecraft.getMinecraft().getRenderPartialTicks());

            if (f > 0.0F) {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                Tessellator tessellator1 = Tessellator.getInstance();
                BufferBuilder vertexbuffer1 = tessellator1.getBuffer();
                draw(vertexbuffer1, xPosition, yPosition + (int)Math.floor(16.0F * (1.0F - f)), 16, (int)Math.ceil(16.0F * f), 255, 255, 255, 127);
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
    }

    /**
     * Draw with the WorldRenderer
     */
    private static void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((x + 0), (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((x + 0), (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((x + width), (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((x + width), (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }


    public static int renderText(Minecraft mc, int x, int y, String txt) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableLighting();
        RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        int width = mc.fontRenderer.getStringWidth(txt);
        mc.fontRenderer.drawStringWithShadow(txt, x, y, 16777215);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        // Fixes opaque cooldown overlay a bit lower
        // TODO: check if enabled blending still screws things up down the line.
        GlStateManager.enableBlend();


        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();

        return width;
    }

    public static int renderText(Minecraft mc, int x, int y, String txt, int color) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableLighting();
        RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        int width = mc.fontRenderer.getStringWidth(txt);
        mc.fontRenderer.drawStringWithShadow(txt, x, y, color);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        // Fixes opaque cooldown overlay a bit lower
        // TODO: check if enabled blending still screws things up down the line.
        GlStateManager.enableBlend();


        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();

        return width;
    }

    /**
     * renders text at half its size
     */
    public static int renderSmallText(Minecraft mc, int x, int y, String txt) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.scale(0.5F, 0.5F, 1.0F);
        GlStateManager.enableLighting();
        RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        int width = mc.fontRenderer.getStringWidth(txt) / 2;
        mc.fontRenderer.drawStringWithShadow(txt, x * 2 , y * 2, 16777215);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        // Fixes opaque cooldown overlay a bit lower
        // TODO: check if enabled blending still screws things up down the line.
        GlStateManager.enableBlend();


        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();

        return width;
    }

    /**
     * renders text at half its size
     */
    public static int renderSmallText(Minecraft mc, int x, int y, String txt, int color) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.scale(0.5F, 0.5F, 1.0F);
        GlStateManager.enableLighting();
        RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        int width = mc.fontRenderer.getStringWidth(txt) / 2;
        mc.fontRenderer.drawStringWithShadow(txt, x * 2 , y * 2, color);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        // Fixes opaque cooldown overlay a bit lower
        // TODO: check if enabled blending still screws things up down the line.
        GlStateManager.enableBlend();


        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();

        return width;
    }

    /**
     * basic colour rendering, converts rgb to hexadecimal
     */
    public static void renderColor(int color) {

        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        float a = (color >> 24 & 255) / 255.0F;

        GlStateManager.color(r, g, b, a);
    }

    /**
     * colour brightening,
     * specifically used for certain coloured progress bars that have overlay text that is too illegible.
     */
    public static int renderBarTextColor(int color) {
        Color c = new Color(color);
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);

        //almost black
        if (hsb[2] <= 0.13f) {
            return new Color(color).brighter().brighter().brighter().brighter().hashCode();
        }
        //lighter black
        if (hsb[2] > 0.13f && hsb[2] <= 0.2f) {
            return new Color(color).brighter().brighter().brighter().hashCode();
        }
        //lighter black
        if (hsb[2] > 0.2f && hsb[2] <= 0.3f) {
            return new Color(color).brighter().brighter().hashCode();
        }
        //lighter black
        if (hsb[2] > 0.3f && hsb[2] <= 0.4f) {
            return new Color(color).brighter().hashCode();
        }
        //fallback
        else return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    /**
     * colour brightening,
     * specifically used for progress bars
     */
    public static int renderBarColor(int color) {
        Color c = new Color(color);
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);

        //almost black
        if (hsb[2] <= 0.13f) {
            return new Color(color).brighter().brighter().brighter().hashCode();
        }
        //lighter black
        if (hsb[2] > 0.13f && hsb[2] <= 0.2f) {
            return new Color(color).brighter().brighter().hashCode();
        }
        //lighter black
        if (hsb[2] > 0.2f && hsb[2] <= 0.3f) {
            return new Color(color).brighter().hashCode();
        }
        //fallback
        else return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    /**
     * better colour brightening,
     * converts hexadecimal from rgb to hsb colour space.
     * useful for properly brightnening and saturating colours.
     * attempts to automatically brighten colours based on input colour
     */
    public static int renderColorToHSB(int color, float saturation, float brightness) {
        Color c = new Color(color);
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        //most greys
        if (hsb[1] < 0.05f && hsb[2] >= 0.2f) {
            return new Color(color).brighter().hashCode();
        }
        //almost black
        if (hsb[1] < 0.05f && hsb[2] < 0.2f) {
            return new Color(color).brighter().brighter().brighter().hashCode();
        }
        //light almost grey
        else if (hsb[1] < 0.2f && hsb[2] > 0.8f) {
            return Color.HSBtoRGB(hsb[0], hsb[1] - 0.1f, hsb[2] + 0.1f);
        }
        //others
        else if (hsb[2] == 1.0f) {
            return Color.HSBtoRGB(hsb[0], saturation, hsb[2]);
        }
        else if (hsb[2] == 0.9f) {
            return Color.HSBtoRGB(hsb[0], saturation, hsb[2] + 0.1f);
        }
        else if (hsb[2] == 0.8f) {
            return Color.HSBtoRGB(hsb[0], saturation, hsb[2] + 0.2f);
        }
        else if (hsb[2] == 0.7f) {
            return Color.HSBtoRGB(hsb[0], saturation, hsb[2] + 0.3f);
        }
        else if (hsb[2] == 0.6f) {
            return Color.HSBtoRGB(hsb[0], saturation, hsb[2] + 0.3f);
        }
        else if (hsb[2] < 0.6f) {
            return Color.HSBtoRGB(hsb[0], saturation, hsb[2] + 0.4f);
        }
        //fallback
        else return Color.HSBtoRGB(hsb[0], saturation, brightness);
    }

    /**
     * simple colour brightening,
     * saturates the colour, doesnt actually 'brighten'
     */
    public static int renderColorBrighter(int color, int amount) {
        return new Color(Math.min(color | ((amount & 255) << 16) | ((amount & 255) << 8) | (amount & 255), 16777215)).brighter().hashCode();
    }
    
    private RenderUtil() { }
    
}
