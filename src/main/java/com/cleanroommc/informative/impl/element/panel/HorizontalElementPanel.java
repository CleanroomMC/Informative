package com.cleanroommc.informative.impl.element.panel;

import com.cleanroommc.informative.api.IElement;

public class HorizontalElementPanel<T extends ElementPanel<T>> extends ElementPanel<T> {

    public HorizontalElementPanel() {
        super();
    }

    public HorizontalElementPanel(ElementPanel<?> master) {
        super(master);
    }

    @Override
    public void render(int x, int y, int scaledWidth, int scaledHeight) {
        // super.render(x, y);
        if (this.show) {
            x += 3;
            y += 3;
        }
        int height = getHeight();
        for (IElement element : this.children) {
            int h = element.getHeight();
            if (this.alignToCenter) {
                element.render(x, y + (height - h) / 2, scaledWidth, scaledHeight);
            } else {
                element.render(x, y, scaledWidth, scaledHeight);
            }
            x += element.getWidth() + this.spacing;
        }
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IElement element : this.children) {
            w += element.getWidth();
        }
        return w + this.spacing * (this.children.size() - 1) + getBorderSpacing();
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IElement element : this.children) {
            int height = element.getHeight();
            if (height > h) {
                h = height;
            }
        }
        return h + getBorderSpacing();
    }

    private int getBorderSpacing() {
        return this.show ? 6 : 0;
    }

}
