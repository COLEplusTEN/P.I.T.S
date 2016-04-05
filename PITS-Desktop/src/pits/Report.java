package pits;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by sandi on 3/31/2016.
 *
 */


public class Report {


    public ObservableList<ItemEntity> reportList ;

    public Report(ObservableList<ItemEntity> list)

    {
        this.reportList = list;
    }

    /*
    This function will ask the user for the  location and will call generateFile method
     */


    public void execute() throws Exception{
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Save File");
        dialog.setHeaderText("Enter the Name for the file: ");


        // Set the button types.
        ButtonType browseLocationBTN = new ButtonType("Browse Location", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(browseLocationBTN, ButtonType.CANCEL);

        // Create the userLocation and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField userLocation = new TextField();
        userLocation.setPromptText("Monthly Report");

        grid.add(new Label("File Name: "), 0, 0);
        grid.add(userLocation, 1, 0);


        // Enable/Disable login button depending on whether a userLocation was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(browseLocationBTN);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        userLocation.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the userLocation field by default.
        Platform.runLater(() -> userLocation.requestFocus());



        /*
        This will run when browse locaiton button is clicked
         */
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == browseLocationBTN) {

                DirectoryChooser dc = new DirectoryChooser();
                File fileLocation = dc.showDialog(null);
                if (fileLocation != null) {
                    fileLocation = new File(fileLocation.getAbsolutePath());
                }

                /*
                Generate File is called here
                 */
                generateFile(userLocation,fileLocation,reportList);

                return (userLocation.getText());
            }
            return null;
        });


        Optional<String> result = dialog.showAndWait();

    }

    /*
    This method will just generate the file in the selected location
     */

    public static void generateFile(TextField username, File fileLocation, ObservableList<ItemEntity> reportList){

        String fileName = username.getText();

        System.out.println("");
        System.out.println("FileName:" + fileName + "File Location: " + fileLocation) ;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Monthly Report");

        Map<Integer, Object[]> data = new TreeMap<>();

        data.put(1, new Object[] {"Monthly Report for Paglia's Pizza"});
        data.put(2, new Object[] {"Name", "Unit", "Walmart/Hyvee","US Foods","Roma ","Count"});
        int count = 2;
        for(ItemEntity item: reportList)
        {
            count++;
            data.put(count, new Object[] {item.getId() , item.getUnit() , item.getWalmartHyvee(), item.getUsFoods(),item.getRoma() ,item.getCount()});
        }

        System.out.println("");
        System.out.println("add everything to the list");


        Set<Integer> keyset = data.keySet();
        int rownum = 0;
        for (Integer key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof Date)
                    cell.setCellValue((Date)obj);
                else if(obj instanceof Boolean)
                    cell.setCellValue((Boolean)obj);
                else if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
            }
        }

        try {

            FileOutputStream out = new FileOutputStream(new File( fileLocation+ "\\" + fileName + ".xls"));
            System.out.println("The total was === "+  fileLocation+ "\\" + fileName + ".xls");
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
