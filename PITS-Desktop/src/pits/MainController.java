package pits;

import com.kinvey.java.model.KinveyDeleteResponse;
import com.kinvey.nativejava.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable{

    @FXML
    TableView<EventEntity> myTable;
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


    public ObservableList<EventEntity> list;
    AppData<EventEntity> myEvents;




    public MainController() {




    }

     public void configureTable()
    {

        /**
         * set up columns and pull from database*/
        colName.setCellValueFactory(new PropertyValueFactory<EventEntity, String>("id"));
        colUnit.setCellValueFactory(new PropertyValueFactory<EventEntity, String>("unit"));
        colWalmartHyvee.setCellValueFactory(new PropertyValueFactory<EventEntity, Double>("walmartHyvee"));
        colUSFoods.setCellValueFactory(new PropertyValueFactory<EventEntity, Double>("usFoods"));
        colRoma.setCellValueFactory(new PropertyValueFactory<EventEntity, Double>("roma"));
        colCount.setCellValueFactory(new PropertyValueFactory<EventEntity, Integer>("count"));

        updateTable();


    }

    // delete button clicked
    public void deleteItemClick() throws Exception{
        String eventId;
        ObservableList<EventEntity> eventSelected, allEvents;

        // all the items on the table
        allEvents = myTable.getItems();
        // the highlighted item
        eventSelected = myTable.getSelectionModel().getSelectedItems();
        // we will be deting this id element from the database
        eventId = eventSelected.get(0).getId();
        // this will remove the item
        eventSelected.forEach(allEvents::remove);


        //The EventEntity class is defined above
        EventEntity event = new EventEntity();
        myEvents = Main.mKinveyClient.appData("eventsCollection", EventEntity.class);
        try{
            KinveyDeleteResponse result = myEvents.deleteBlocking(eventId).execute();
        }catch (IOException e){
            System.out.println("Couldn't delete! -> " + e);
        }

    }


    public void addItemClick() throws Exception {
        /**
         * Build the dialog box and create all of the text fields/labels (maybe make the unit a dropdown box)
         * when they press ok, validate input and add into kinvey*/
        Dialog<EventEntity> dialog = new Dialog<>();
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
                String walmartHyvee = "0.0";
                String USFoods = "0.0";
                String roma = "0.0";

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
                        walmartHyvee = textWH.getText();
                        //walmartHyvee=Double.valueOf(textWH.getText());
                }
                if(!textUSFoods.getText().equals(""))
                {
                    if(isNumeric(textUSFoods.getText()))
                        USFoods = textUSFoods.getText();
                        //USFoods=Double.valueOf(textUSFoods.getText());
                }
                if(!textRoma.getText().equals(""))
                {
                    if(isNumeric(textRoma.getText()))
                        roma = textRoma.getText();
                        //roma=Double.valueOf(textRoma.getText());
                }

                EventEntity newItem = new EventEntity(textName.getText(),textUnit.getText(),walmartHyvee,USFoods,roma,"0");
                try{
                    EventEntity result = myEvents.saveBlocking(newItem).execute();
                }catch (IOException e){
                    System.out.println("Couldn't save new item! -> " + e);
                }



                return newItem;
            }

            return null;
        });

        Optional<EventEntity> result = dialog.showAndWait();
        if(result.isPresent())
        {
            System.out.println("Result is present");
            list.add(result.get());
            updateTable();
        }
    }

    /** Make sure that the values in the addItem method are valid numbers*/
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

    public void updateTable()
    {
        myEvents = Main.mKinveyClient.appData("eventsCollection", EventEntity.class);
        list = FXCollections.observableArrayList();

        try {
            EventEntity[] results = myEvents.getBlocking().execute();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            for(EventEntity item: results)
            {
                list.add(item);
                System.out.println(item.getId());
                System.out.println(item.getUnit());
                System.out.println(item.getWalmartHyvee());
                System.out.println(item.getUsFoods());
                System.out.println(item.getRoma());
                System.out.println("---------------------");
            }
        } catch (IOException e) {
            System.out.println("Couldn't get! -> "+e);
        }

        myTable.setItems(list);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        configureTable();

    }



}


