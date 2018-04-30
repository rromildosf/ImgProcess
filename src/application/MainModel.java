package application;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainModel {
	private Label savedLabel;
	private VBox imagesBox;
	private Pane mainImagePane; // rigth pane for editing image

	public Pane getMainImagePane() {
		return mainImagePane;
	}

	public void setMainImagePane(Pane mainImagePane) {
		this.mainImagePane = mainImagePane;
	}

	public VBox getImagesBox() {
		return imagesBox;
	}

	public void setImagesBox(VBox imagesBox) {
		this.imagesBox = imagesBox;
	}

	public Label getSavedLabel() {
		return savedLabel;
	}

	public void setSavedLabel(Label savedLabel) {
		this.savedLabel = savedLabel;
	}
	
	
}
