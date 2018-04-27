package application;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainModel {
	private Label savedLabel;
	private VBox imagesBox;

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
