package com.cleanroommc.informative.impl;

import com.cleanroommc.informative.impl.element.panel.VerticalElementPanel;
import com.cleanroommc.informative.util.RenderUtil;

public class Tooltip extends VerticalElementPanel<Tooltip> {

    public Tooltip() {
        alignToCenter(true);
    }

    @Override
    public void render(int x, int y, int scaledWidth, int scaledHeight) {
        int w = this.getWidth();
        int h = this.getHeight();
        // Outline of Tooltip
        RenderUtil.drawOutlineBox(x, y, x + w - 1, y + h - 1, 1, 0xCC100010);
        // Fill of the outline
        RenderUtil.drawThickBeveledBoxGradient(x, y, x + w - this.spacing, y + h - this.spacing, 1, this.borderColourHSB, this.borderColour, 0x00FFFFFF);
        // Tooltip box
        RenderUtil.drawThickBeveledBoxGradient(x, y, x + w - 1, y + h - 1, 1, 0xFF5000FF, 0xFF28007F, 0xCC100010);
        super.render(x, y, scaledWidth, scaledHeight);
    }

}
