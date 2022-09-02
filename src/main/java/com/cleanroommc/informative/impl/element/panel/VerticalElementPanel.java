package com.cleanroommc.informative.impl.element.panel;

import com.cleanroommc.informative.api.IElement;

public class VerticalElementPanel<T extends ElementPanel<T>> extends ElementPanel<T> {

    public VerticalElementPanel() {
        super();
    }

    public VerticalElementPanel(ElementPanel<?> master) {
        super(master);
    }

    @Override
    public void render(int x, int y, int scaledWidth, int scaledHeight) {
        // super.render(x, y);
        if (this.show) {
            x += 3;
            y += 3;
        }
        int width = getWidth();
        for (IElement element : this.children) {
            int w = element.getWidth();
            if (this.alignToCenter) {
                element.render(x + (width - w) / 2, y, scaledWidth, scaledHeight);
            } else {
                element.render(x, y, scaledWidth, scaledHeight);
            }
            y += element.getHeight() + this.spacing;
        }
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IElement element : this.children) {
            int width = element.getWidth();
            if (width > w) {
                w = width;
            }
        }
        return w + getBorderSpacing();
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IElement element : this.children) {
            h += element.getHeight();
        }
        return h + this.spacing * (this.children.size() - 1) + getBorderSpacing();
    }

    private int getBorderSpacing() {
        return this.show ? 6 : 0;
    }

}
