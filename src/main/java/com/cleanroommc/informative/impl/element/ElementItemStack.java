package com.cleanroommc.informative.impl.element;

import com.cleanroommc.informative.api.IElement;
import com.cleanroommc.informative.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ElementItemStack implements IElement {

    public static void render(ItemStack stack, int x, int y, int width, int height) {
        int size = stack.getCount();
        String amount;
        if (size <= 1) {
            amount = "";
        } else if (size < 100000) {
            amount = String.valueOf(size);
        } else if (size < 1000000) {
            amount = size / 1000 + "k";
        } else if (size < 1000000000) {
            amount = size / 1000000 + "m";
        } else {
            amount = size / 1000000000 + "g";
        }
        RenderUtil.renderItemStack(Minecraft.getMinecraft(), Minecraft.getMinecraft().getRenderItem(), stack, x, y, amount);
    }

    private final ItemStack stack;
    private final int width, height;

    public ElementItemStack(ItemStack stack) {
        this(stack, 16, 16);
    }

    public ElementItemStack(ItemStack stack, int width, int height) {
        this.stack = stack;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(int x, int y, int scaledWidth, int scaledHeight) {
        render(stack, x, y, width, height);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

}
