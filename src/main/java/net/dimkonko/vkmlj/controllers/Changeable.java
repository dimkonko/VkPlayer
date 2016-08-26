package net.dimkonko.vkmlj.controllers;

/**
 * Used in Controllers to initialize before switch to scene with this controller
 */
public interface Changeable {
    void beforeChange();
}
