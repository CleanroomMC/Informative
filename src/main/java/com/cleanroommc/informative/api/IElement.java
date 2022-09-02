package com.cleanroommc.informative.api;

/**
 * Represents an element of the tooltip, or the tooltip itself if it's the master.
 */
public interface IElement {

    /**
     * Renders the element in the specified location.
     *
     * @param x x position
     * @param y y position
     * @param scaledWidth scaledWidth of the screen
     * @param scaledHeight scaledHeight of the screen
     */
    void render(int x, int y, int scaledWidth, int scaledHeight);

    /**
     * @return width of the element
     */
    int getWidth();

    /**
     * @return height of the element
     */
    int getHeight();

}
