package pits;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Eric on 2/8/2016.
 */
public class AddItem {

    //add all of the fxml buttons and textfields for the popup here
    //create an item and have it passed into the constructor, which calls display, that returns text or something that is parsed into an item
    public AddItem() throws IOException {

        try {
            display();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void display() throws IOException {
        Stage window = new Stage();
        window.setTitle("Add New Item");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("AddItem.fxml"));
        window.setScene(new Scene(root, window.getWidth(),window.getHeight()));
        window.showAndWait();

        System.out.println("lkadjsfklasd");
    }

}
