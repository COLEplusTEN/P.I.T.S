package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    @FXML
    TableView<Item> myTable;
    @FXML
    TableColumn colName;
    @FXML
    TableColumn colUnit;
    @FXML
    TableColumn colWalmartHyvee;
    @FXML
    TableColumn colUSFoods;
    @FXML
    TableColumn colRoma;
    @FXML
    TableColumn colCount;
    @FXML
    Button addItem;
    @FXML
    Button addItemOk;

    ObservableList<Item> list;

    public void configureTable()
    {
        colName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        colUnit.setCellValueFactory(new PropertyValueFactory<Item, String>("unit"));
        colWalmartHyvee.setCellValueFactory(new PropertyValueFactory<Item, Double>("walmartHyvee"));
        colUSFoods.setCellValueFactory(new PropertyValueFactory<Item, Double>("usfoods"));
        colRoma.setCellValueFactory(new PropertyValueFactory<Item, Double>("roma"));
        colCount.setCellValueFactory(new PropertyValueFactory<Item, Integer>("count"));

        list = FXCollections.observableArrayList(
                new Item("Pepperoni","Package", 13.44, 11111.0, 9999.0, 19),
                new Item("Sauce","Can",0.0, 0.0,15.35, 15),
                new Item("Black Olives","Package",0.0, 5.33, 0.0, 44),
                new Item("Beef","Pounds",5.39,5.24,0.0, 20)
        );
        myTable.setItems(list);
    }

    public void addItemClick() throws Exception {
        AddItem a = new AddItem();


    }

    public void addItemOk()
    {

    }

    public void addItemCancel()
    {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        configureTable();

    }
}

