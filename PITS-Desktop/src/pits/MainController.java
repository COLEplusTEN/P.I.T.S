package pits;

import com.kinvey.java.model.KinveyDeleteResponse;
import com.kinvey.nativejava.AppData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable{


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
    Label timeText;


    public ObservableList<ItemEntity> list = FXCollections.observableArrayList();
    AppData<ItemEntity> myEvents;
    ObservableList<ItemEntity> eventSelected;

    //Search bar :
    @FXML
    private TextField filterField;
    @FXML
    TableView<ItemEntity> myTable;


    public MainController() {

    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     *
     * Initializes the table columns and sets up sorting and filtering.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        /*
        Updating the time : when it is run for the first time :

         */
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd  HH::mm::ss").format(Calendar.getInstance().getTime());
        timeText.setText(timeStamp);



        configureTable();

     /*
     * This works when the user double clicks on the row twice
     * */

        myTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    System.out.println(myTable.getSelectionModel().getSelectedItem());
                    try {

                        String textName1  = myTable.getSelectionModel().getSelectedItem().getId();
                        String textUnit1  = myTable.getSelectionModel().getSelectedItem().getUnit();
                        String textWH1  = myTable.getSelectionModel().getSelectedItem().getWalmartHyvee();
                        String textUSFoods1  = myTable.getSelectionModel().getSelectedItem().getUsFoods();
                        String textRoma1 = myTable.getSelectionModel().getSelectedItem().getRoma();
                        String textCount1  = myTable.getSelectionModel().getSelectedItem().getCount();

                        editItemClick(textName1, textUnit1, textWH1,textUSFoods1,textRoma1,textCount1);
                    } catch (Exception e) {
                        System.out.println("the function will not work");
                    }
                }
            }
        });
    }




    public void searchBar(){

        /*
        * Checking the focus on search bar every single time
        * */

        // chagned from focusedProperty
        filterField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {

                // 0. Initialize the columns.
                colName.setCellValueFactory(new PropertyValueFactory<ItemEntity, String>("id"));

                // 1. Wrap the ObservableList in a FilteredList (initially display all data).
                FilteredList<ItemEntity> filteredData = new FilteredList<>(list, p -> true);
                // 2. Set the filter Predicate whenever the filter changes.
                filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(person -> {
                        // If filter text is empty, display all persons.
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        // Compare first name and last name of every person with filter text.
                        String lowerCaseFilter = newValue.toLowerCase();

                        if (person.getId().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches first name.
                        }
                        return false; // Does not match.
                    });
                });


                // this is when there is a focus .... and we activate the search methods
                if (newPropertyValue)
                {
                    System.out.println("Textfield on focus");
                }

                else
                {
                    System.out.println("Textfield out focus");
                }


                // 3. Wrap the FilteredList in a SortedList.
                SortedList<ItemEntity> sortedData = new SortedList<>(filteredData);

                // 4. Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(myTable.comparatorProperty());

                // 5. Add sorted (and filtered) data to the table.
                myTable.setItems(sortedData);
            }
        });

    }


    // when user clicks the refresh image
    public void refresh() {
        System.out.println("Table Updated !!!");

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd  HH::mm::ss").format(Calendar.getInstance().getTime());
        timeText.setText(timeStamp);

        updateTable();
    }


    // this is for add new item
    public  void editItemClick(String textName1,String textUnit1,String textWH1,String textUSFoods1,String textRoma1,String textCount1) throws Exception {
        /**
         * Build the dialog box and create all of the text fields/labels (maybe make the unit a dropdown box)
         * when they press ok, validate input and add into kinvey*/


        Dialog<ItemEntity> dialog = new Dialog<>();
        dialog.setTitle("Edit Item");
        dialog.setResizable(false);

        Label labelName = new Label("Name:");
        Label labelUnit = new Label("Unit:");
        Label labelWH = new Label("Walmart/Hyvee Price:");
        Label labelUSFoods = new Label("USFoods Price:");
        Label labelRoma = new Label("Roma Price:");
        Label labelCount =  new Label("Count:");


        TextField textName = new TextField();
        TextField textUnit = new TextField();
        TextField textWH = new TextField();
        TextField textUSFoods = new TextField();
        TextField textRoma = new TextField();
        TextField textCount =  new TextField();

        textName.setEditable(false);
        textName.setText(textName1);
        textUnit.setText(textUnit1);
        textWH.setText(textWH1);
        textUSFoods.setText(textUSFoods1);
        textRoma.setText(textRoma1);
        textCount.setText(textCount1);

        GridPane grid = new GridPane();
        grid.add(labelName, 1,1);
        grid.add(labelUnit,1,2);
        grid.add(labelWH,1,3);
        grid.add(labelUSFoods,1,4);
        grid.add(labelRoma,1,5);
        grid.add(labelCount,1,6);
        grid.add(textName, 2,1);
        grid.add(textUnit,2,2);
        grid.add(textWH,2,3);
        grid.add(textUSFoods,2,4);
        grid.add(textRoma,2,5);
        grid.add(textCount,2,6);


        dialog.getDialogPane().setContent(grid);
        ButtonType buttonOK = new ButtonType("Save Changes", ButtonBar.ButtonData.OK_DONE);


        dialog.getDialogPane().getButtonTypes().add(buttonOK);

        dialog.setResultConverter(button -> {

            if(button == buttonOK)
            {
                String walmartHyvee = "0.0";
                String USFoods = "0.0";
                String roma = "0.0";
                String count = "0";

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
                if(!textCount.getText().equals(""))
                {
                    if(isNumeric(textCount.getText()))
                            count= textCount.getText();

                }

                ItemEntity newItem = new ItemEntity(textName.getText(),textUnit.getText(),walmartHyvee,USFoods,roma,count);
                try{
                    ItemEntity result = myEvents.saveBlocking(newItem).execute();


                }catch (IOException e){
                    System.out.println("Couldn't save new item! -> " + e);
                }



                return newItem;
            }

            return null;
        });

        Optional<ItemEntity> result = dialog.showAndWait();
        if(result.isPresent())
        {
            System.out.println("Result is present");
            updateTable();
        }


    }



    public void configureTable()
    {

        /**
         * set up columns and pull from database*/
        colName.setCellValueFactory(new PropertyValueFactory<ItemEntity, String>("id"));

        ItemEntity test = new ItemEntity();



        colUnit.setCellValueFactory(new PropertyValueFactory<ItemEntity, String>("unit"));
        colWalmartHyvee.setCellValueFactory(new PropertyValueFactory<ItemEntity, Double>("walmartHyvee"));
        colUSFoods.setCellValueFactory(new PropertyValueFactory<ItemEntity, Double>("usFoods"));
        colRoma.setCellValueFactory(new PropertyValueFactory<ItemEntity, Double>("roma"));
        colCount.setCellValueFactory(new PropertyValueFactory<ItemEntity, Integer>("count"));

        updateTable();


    }

    // delete button clicked
    public void deleteItemClick() throws Exception{

        String eventId;

        // Alert dialog created
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete Item");
        alert.setHeaderText("This will delete the item permanently !");
        ButtonType cancelButtonDeleteDialog = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        ButtonType buttonTypeCancel = new ButtonType("Delete");

        alert.getButtonTypes().setAll(buttonTypeCancel,cancelButtonDeleteDialog);

        Optional<ButtonType> result1 = alert.showAndWait();
        if (result1.get() == buttonTypeCancel){
            // ... user chose "One"
            // all the items on the table
           // allEvents = myTable.getItems();
            // the highlighted item
            eventSelected = myTable.getSelectionModel().getSelectedItems();
            // we will be deting this id element from the database
            eventId = eventSelected.get(0).getId();
            // this will remove the item
            // eventSelected.forEach(allEvents::remove);


            //The ItemEntity class is defined above
            ItemEntity event = new ItemEntity();
            myEvents = Main.mKinveyClient.appData(Main.nameOfCollection, ItemEntity.class);
            try{
                KinveyDeleteResponse result = myEvents.deleteBlocking(eventId).execute();
            }catch (IOException e){
                System.out.println("Couldn't delete! -> " + e);
            }


            updateTable();


        }
        else{
            System.out.println("Exit : pressed");
        }

    }


    // this is for add new item
    public  void addItemClick() throws Exception {
        /**
         * Build the dialog box and create all of the text fields/labels (maybe make the unit a dropdown box)
         * when they press ok, validate input and add into kinvey*/
        Dialog<ItemEntity> dialog = new Dialog<>();
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

                ItemEntity newItem = new ItemEntity(textName.getText(),textUnit.getText(),walmartHyvee,USFoods,roma,"0");
                // newItem.getMeta().setGloballyWritable(true);
                try{
                    ItemEntity result = myEvents.saveBlocking(newItem).execute();
                }catch (IOException e){
                    System.out.println("Couldn't save new item! -> " + e);
                }



                return newItem;
            }

            return null;
        });

        Optional<ItemEntity> result = dialog.showAndWait();
        if(result.isPresent())
        {
            System.out.println("Result is present");
            updateTable();
        }


    }


    public void generateReport() throws Exception{

        // generating the report
        Report r = new Report(list);
        r.execute();

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
        myEvents = Main.mKinveyClient.appData(Main.nameOfCollection, ItemEntity.class);
        // this should be the final list that is displayed at the table
        //list = FXCollections.observableArrayList();
        list.clear();
        try {
            ItemEntity[] results = myEvents.getBlocking().execute();
            System.out.println("in updateTable");
            for(ItemEntity item: results)
            {
                if(!list.contains(item))
                    list.add(item);
            }
            System.out.println("add everything to the list");
        } catch (IOException e) {
            System.out.println("Couldn't get! -> "+e);
        }
        // whole list created till here
        myTable.setItems(list);
        // masterdata for the search bar
        //masterData.addAll(list);

    }



}


