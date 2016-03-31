package pits;


import com.kinvey.nativejava.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public ObservableList<ItemEntity> list = FXCollections.observableArrayList();
    AppData<ItemEntity> myEvents;

    public Report(ObservableList<ItemEntity> list){
        this.reportList = list;
    }


    public void execute(){


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Monthly Report");


        Map<Integer, Object[]> data = new TreeMap<>();
     data.put(1, new Object[] {"Name", "Unit", "Walmart/Hyvee","US Foods","Roma ","Count"});


            int count = 1;
            for(ItemEntity item: reportList)
            {
                count++;
              //  data.put(count+"", new Object[] {1d, "John", 1500000d});
                data.put(count, new Object[] {item.getId() , item.getUnit() , item.getWalmartHyvee(), item.getUsFoods(),item.getRoma() ,item.getCount()});
          System.out.println(item.getId() + " " + item.getUnit() + " " + item.getWalmartHyvee() + " " + item.getUsFoods() + " " + item.getRoma() + " " + item.getCount());


              //  System.out.println("we are here" + item);
                if(!list.contains(item)){
                    list.add(item);
                }

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
            FileOutputStream out = new FileOutputStream(new File("C:\\Users\\sandi\\Google Drive\\Monthly Report.xls"));
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
