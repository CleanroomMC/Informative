package com.cleanroommc.informative.impl.element.panel;

import com.cleanroommc.informative.api.IElement;
import com.cleanroommc.informative.api.ITooltip;
import com.cleanroommc.informative.impl.element.ElementItemStack;
import com.cleanroommc.informative.impl.element.ElementSprite;
import com.cleanroommc.informative.impl.element.ElementText;
import com.cleanroommc.informative.util.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class ElementPanel<T extends ElementPanel<T>> implements IElement, ITooltip<T> {

    protected final ElementPanel<?> master;
    protected final List<IElement> children = new ArrayList<>();

    protected int borderColour, borderColourHSB, spacing;
    protected boolean show = true, alignToCenter = false;

    public ElementPanel() {
        this.master = null;
    }

    public ElementPanel(ElementPanel<?> master) {
        this.master = master;
    }

    public void clear() {
        this.children.clear();
    }

    public T borderColour(int borderColour) {
        return this.borderColour(borderColour, 0.8F, 1.0F);
    }

    public T borderColour(int borderColour, float saturation, float brightness) {
        this.borderColour = borderColour;
        this.borderColourHSB = RenderUtil.renderColorToHSB(borderColour, saturation, brightness);
        return (T) this;
    }

    public T spacing(int spacing) {
        this.spacing = spacing;
        return (T) this;
    }

    public T show(boolean show) {
        this.show = show;
        return (T) this;
    }

    public T alignToCenter(boolean alignToCenter) {
        this.alignToCenter = alignToCenter;
        return (T) this;
    }

    @Override
    public List<IElement> getChildren() {
        return children;
    }

    @Override
    public <M extends ITooltip<M>> M master() {
        return master == null ? (M) this : (M) master;
    }

    @Override
    public HorizontalElementPanel horizontal() {
        HorizontalElementPanel panel = new HorizontalElementPanel(this);
        panel.spacing(this.spacing).alignToCenter(this.alignToCenter);
        this.children.add(panel);
        return panel;
    }

    @Override
    public HorizontalElementPanel horizontal(int spacing) {
        HorizontalElementPanel panel = new HorizontalElementPanel(this);
        panel.spacing(spacing).alignToCenter(this.alignToCenter);
        this.children.add(panel);
        return panel;
    }

    @Override
    public VerticalElementPanel vertical() {
        VerticalElementPanel panel = new VerticalElementPanel(this);
        panel.spacing(2).alignToCenter(this.alignToCenter);
        this.children.add(panel);
        return panel;
    }

    @Override
    public VerticalElementPanel vertical(int spacing) {
        VerticalElementPanel panel = new VerticalElementPanel(this);
        panel.spacing(spacing).alignToCenter(this.alignToCenter);
        this.children.add(panel);
        return panel;
    }

    @Override
    public T text(String text) {
        this.children.add(new ElementText(text));
        return (T) this;
    }

    @Override
    public T sprite(ResourceLocation location, int width, int height) {
        this.children.add(new ElementSprite(location, width, height));
        return (T) this;
    }

    @Override
    public T sprite(String location, int width, int height) {
        this.children.add(new ElementSprite(location, width, height));
        return (T) this;
    }

    @Override
    public T item(ItemStack stack) {
        this.children.add(new ElementItemStack(stack));
        return (T) this;
    }

    @Override
    public T item(ItemStack stack, int width, int height) {
        this.children.add(new ElementItemStack(stack, width, height));
        return (T) this;
    }

    @Override
    public T itemLabel(ItemStack stack) {
        this.children.add(new ElementText(stack.getDisplayName()));
        return (T) this;
    }

    @Override
    public T entity(String entityName) {
        return (T) this;
    }

    @Override
    public T entity(Entity entity) {
        return (T) this;
    }

    @Override
    public T entity(Class<? extends Entity> entityClass) {
        return (T) this;
    }

}
