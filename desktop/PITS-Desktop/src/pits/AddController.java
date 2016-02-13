package pits;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Eric on 2/13/2016.
 */
public class AddController implements Initializable{

    @FXML
    Button addItemOk;
    @FXML
    Button addItemCancel;
    @FXML
    TextField addName;
    @FXML
    TextField addUnit;
    @FXML
    TextField addWalmartHyvee;
    @FXML
    TextField addUSFoods;
    @FXML
    TextField addRoma;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("in addController");
    }

    public void setAddItemCancel()
    {
        Stage stage = (Stage) addItemCancel.getScene().getWindow();
        stage.close();
    }

    public void setAddItemOk()
    {
        Double walmartHyvee = 0.0;
        Double USFoods = 0.0;
        Double roma = 0.0;

        if(textFieldsValid())
        {
            System.out.println("Text fields are valid.");
        }

        //get values of prices if not null
        if(!addWalmartHyvee.getText().equals(""))
        {
            walmartHyvee=Double.valueOf(addWalmartHyvee.getText());
        }
        if(!addUSFoods.getText().equals(""))
        {
            USFoods=Double.valueOf(addUSFoods.getText());
        }
        if(!addRoma.getText().equals(""))
        {
            roma=Double.valueOf(addRoma.getText());
        }

        Item newItem = new Item(addName.getText(),addUnit.getText(),walmartHyvee,USFoods,roma,0);
        MainController mainController = new MainController();

        Stage stage = (Stage) addItemOk.getScene().getWindow();
        stage.close();
    }

    public boolean textFieldsValid()
    {
        if(!addName.getText().equals("") && !addUnit.getText().equals("") && (!addWalmartHyvee.getText().equals("") || !addRoma.getText().equals("") || !addUSFoods.getText().equals("")))
        {
            return true;
        }
        return false;
    }
}
