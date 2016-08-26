package net.dimkonko.vkmlj.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import net.dimkonko.vkmlj.controllers.Changeable;

/**
 * Created by Dima Karacheban on 30.04.2015.
 */
public class ControllableScene extends Scene {

    private Changeable controller;

    public ControllableScene(Parent parent, Changeable controller) {
        super(parent);

        this.controller = controller;
    }

    public Changeable getController() {
        return controller;
    }
}
