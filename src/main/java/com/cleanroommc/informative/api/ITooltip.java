package com.cleanroommc.informative.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Represents the current state of the tooltip
 */
public interface ITooltip<T extends ITooltip<T>> {

    List<IElement> getChildren();

    <M extends ITooltip<M>> M master();

    <H extends ITooltip<H>> H horizontal();

    <H extends ITooltip<H>> H horizontal(int spacing);

    <V extends ITooltip<V>> V vertical();

    <V extends ITooltip<V>> V vertical(int spacing);

    /**
     * Creates an ElementText.
     *
     * @param text text to be displayed
     * @return current state of the ITooltip, for method-chaining
     */
    T text(String text);

    /**
     * Creates an ElementSprite with the following specifications.
     *
     * @param location name of the TextureAtlasSprite
     * @param width width of the icon
     * @param height height of the icon
     * @return current state of the ITooltip, for method-chaining
     */
    T sprite(ResourceLocation location, int width, int height);

    /**
     * Creates an ElementSprite with the following specifications.
     *
     * @param location name of the TextureAtlasSprite
     * @param width width of the icon
     * @param height height of the icon
     * @return current state of the ITooltip, for method-chaining
     */
    T sprite(String location, int width, int height);

    /**
     * Creates an ElementItemStack with the following specifications.
     *
     * @param stack ItemStack to be displayed
     * @return current state of the ITooltip, for method-chaining
     */
    T item(ItemStack stack);

    /**
     * Creates an ElementItemStack with the following specifications.
     *
     * @param stack ItemStack to be displayed
     * @param width width of the icon
     * @param height height of the icon
     * @return current state of the ITooltip, for method-chaining
     */
    T item(ItemStack stack, int width, int height);

    /**
     * Creates an ElementText with the following specifications.
     *
     * @param stack ItemStack's name to be displayed
     * @return current state of the ITooltip, for method-chaining
     */
    T itemLabel(ItemStack stack);

    /**
     * Creates an ElementEntity with the following specifications.
     *
     * @param entityName Entity name
     * @return current state of the ITooltip, for method-chaining
     */
    T entity(String entityName);

    /**
     * Creates an ElementEntity with the following specifications.
     *
     * @param entity Entity instance
     * @return current state of the ITooltip, for method-chaining
     */
    T entity(Entity entity);

    /**
     * Creates an ElementEntity with the following specifications.
     *
     * @param entityClass Entity class
     * @return current state of the ITooltip, for method-chaining
     */
    T entity(Class<? extends Entity> entityClass);

    default boolean isEmpty() {
        return getChildren().isEmpty();
    }

}
