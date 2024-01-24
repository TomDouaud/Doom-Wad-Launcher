package application.controller;

import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

import javafx.scene.image.Image;

public class SourcePortListCell {
    private Image icon;
    private String name;

    public SourcePortListCell(Image icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public Image getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}