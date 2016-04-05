package pits;


import com.kinvey.nativejava.AppData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sandi on 3/31/2016.
 *
 */


public class Report {



    @FXML
    private AnchorPane content;


    public ObservableList<ItemEntity> reportList ;

    public Report(ObservableList<ItemEntity> list)
    {
        this.reportList = list;
    }


    public void execute() throws Exception{


        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Save File");
        dialog.setHeaderText("Enter the Name for the file and location");

//        // Set the icon (must be included in the project).
//        dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

// Set the button types.
        ButtonType loginButtonType = new ButtonType("Browse Location", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Monthly Report");
        TextField password = new TextField();
        password.setPromptText("C://Reports");

        grid.add(new Label("Name of the File: "), 0, 0);
        grid.add(username, 1, 0);
       // grid.add(new Label("Location of the File: "), 0, 1);
        // grid.add(password, 1, 1);
        // grid.add(new Label("Browse"),3,1);
        //grid.add(btn,2,0);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());






// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                System.out.println("This works, why not the other one");


                DirectoryChooser dc = new DirectoryChooser();
                File file = dc.showDialog(null);
                if (file != null) {
                    file = new File(file.getAbsolutePath() + "/fileName.xls");}

                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();




        //  fileName
        System.out.println("");
        String fileName = username.getText();

        System.out.println("The name of the file is " + fileName);






        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Monthly Report");


        Map<Integer, Object[]> data = new TreeMap<>();

        data.put(1, new Object[] {"Monthly Report for Paglia's Pizza"});
     data.put(2, new Object[] {"Name", "Unit", "Walmart/Hyvee","US Foods","Roma ","Count"});


            int count = 2;
            for(ItemEntity item: reportList)
            {
                count++;
              //  data.put(count+"", new Object[] {1d, "John", 1500000d});
                data.put(count, new Object[] {item.getId() , item.getUnit() , item.getWalmartHyvee(), item.getUsFoods(),item.getRoma() ,item.getCount()});
        //  System.out.println(item.getId() + " " + item.getUnit() + " " + item.getWalmartHyvee() + " " + item.getUsFoods() + " " + item.getRoma() + " " + item.getCount());



            }
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
            /*
            This has to be changed later
             */
            FileOutputStream out = new FileOutputStream(new File("C:\\Users\\sandi\\Google Drive\\" + fileName + ".xls"));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



//
//        DirectoryChooser dc = new DirectoryChooser();
//        File file = dc.showDialog(null);
//        if (file != null) {
//            file = new File(file.getAbsolutePath() + "/dafaultFilename.extension");}
//
//



    }



}
