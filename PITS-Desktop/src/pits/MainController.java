package pits;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable{

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


    public ObservableList<Item> list;

    public MainController()
    {

    }
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
                new Item("Beef","Pounds",5.39,5.24,0.0, 20),
                new Item("Pepperoni","Package", 13.44, 11111.0, 9999.0, 19),
                new Item("Sauce","Can",0.0, 0.0,15.35, 15),
                new Item("Black Olives","Package",0.0, 5.33, 0.0, 44),
                new Item("Beef","Pounds",5.39,5.24,0.0, 20),
                new Item("Pepperoni","Package", 13.44, 11111.0, 9999.0, 19),
                new Item("Sauce","Can",0.0, 0.0,15.35, 15),
                new Item("Black Olives","Package",0.0, 5.33, 0.0, 44),
                new Item("Beef","Pounds",5.39,5.24,0.0, 20),
                new Item("Pepperoni","Package", 13.44, 11111.0, 9999.0, 19),
                new Item("Sauce","Can",0.0, 0.0,15.35, 15),
                new Item("Black Olives","Package",0.0, 5.33, 0.0, 44),
                new Item("Beef","Pounds",5.39,5.24,0.0, 20),
                new Item("Pepperoni","Package", 13.44, 11111.0, 9999.0, 19),
                new Item("Sauce","Can",0.0, 0.0,15.35, 15),
                new Item("Black Olives","Package",0.0, 5.33, 0.0, 44),
                new Item("Beef","Pounds",5.39,5.24,0.0, 20)
                new Item("Beef","Pounds",5.39,5.24,0.0, 20),
                new Item("Sandip","Weight",1.1,1.1,1.1,33)
        );


        updateTable(list);


    }

    public void addItemClick() throws Exception {
        //AddItem a = new AddItem();
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Add New Item");
        dialog.setResizable(false);

        Label labelName = new Label("Name:");
        Label labelUnit = new Label("Unit:");
        Label labelWH = new Label("Walmart/Hyvee Price:");
        Label labelUSFoods = new Label("USFoods Price:");
        Label labelRoma = new Label("Roma Price:");
        TextField textName = new TextField();
        TextField textUnit = new TextField();
        TextField textWH = new TextField();
        TextField textUSFoods = new TextField();
        TextField textRoma = new TextField();

        GridPane grid = new GridPane();
        grid.add(labelName, 1,1);
        grid.add(labelUnit,1,2);
        grid.add(labelWH,1,3);
        grid.add(labelUSFoods,1,4);
        grid.add(labelRoma,1,5);
        grid.add(textName, 2,1);
        grid.add(textUnit,2,2);
        grid.add(textWH,2,3);
        grid.add(textUSFoods,2,4);
        grid.add(textRoma,2,5);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonOK = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonOK);

        dialog.setResultConverter(button -> {

            if(button == buttonOK)
            {
                Double walmartHyvee = 0.0;
                Double USFoods = 0.0;
                Double roma = 0.0;

                if(!textName.getText().equals("") && !textUnit.getText().equals(""))
                {
                    System.out.println("Text fields are valid.");
                }
                else
                {
                    return null;
                }

                //get values of prices if not null
                if(!textWH.getText().equals(""))
                {
                    if(isNumeric(textWH.getText()))
                        walmartHyvee=Double.valueOf(textWH.getText());
                }
                if(!textUSFoods.getText().equals(""))
                {
                    if(isNumeric(textUSFoods.getText()))
                        USFoods=Double.valueOf(textUSFoods.getText());
                }
                if(!textRoma.getText().equals(""))
                {
                    if(isNumeric(textRoma.getText()))
                        roma=Double.valueOf(textRoma.getText());
                }

                Item newItem = new Item(textName.getText(),textUnit.getText(),walmartHyvee,USFoods,roma,0);

                return newItem;
            }

            return null;
        });

        Optional<Item> result = dialog.showAndWait();
        if(result.isPresent())
        {
            System.out.println("Result is present");
            list.add(result.get());
            updateTable(list);
        }
    }

    public boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public void updateTable(ObservableList<Item> myList)
    {
        myTable.setItems(myList);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        configureTable();

    }
}


