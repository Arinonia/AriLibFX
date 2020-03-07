package fr.arinonia.arilibfx.ui.panel;

import javafx.scene.layout.GridPane;

public interface IPanel {
    void init(Object panelManager);
    GridPane getLayout();
    void onShow();
}
