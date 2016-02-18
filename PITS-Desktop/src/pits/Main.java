package pits;

import com.kinvey.java.Query;
import com.kinvey.nativejava.AppData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.kinvey.nativejava.Client;

import java.io.IOException;


/*
App Id : kid_ZyBaA-MgAe
App Secret: 4e43176fdfe14d44878492851d93385f
Master Secret: 81181f297d044117a537c53378eae68d
 */


public class Main extends Application {

    public static final String appKey = "kid_ZyBaA-MgAe";
    public static final String mastersecret = "81181f297d044117a537c53378eae68d";



    final Client mKinveyClient = new Client.Builder("kid_ZyBaA-MgAe", "4e43176fdfe14d44878492851d93385f").build();


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Pagliai's Inventory Tracking System");
        primaryStage.setScene(new Scene(root, 1220, 850));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {

        /*
        * Do not delete this part ...
        * It has key and app id for our kinvey
        * */

        Client mKinveyClient = new Client.Builder("kid_ZyBaA-MgAe", "4e43176fdfe14d44878492851d93385f").build();
        try{
            mKinveyClient.user().loginBlocking("kid_ZyBaA-MgAe", "81181f297d044117a537c53378eae68d").execute();
            System.out.println("Client logged in -> " + mKinveyClient.user().isUserLoggedIn());
        }catch (IOException e){
            System.out.println("Couldn't login -> " + e);
            e.printStackTrace();
        }


        /*
        * This query is for testing the table structure of Kinvey database
        * */
        //The EventEntity class is defined above


        // first row
        EventEntity haha = new EventEntity();
        haha.setId("haha");
        haha.set("dinner","this is a pizza");
        haha.set("members","Eric,Dakota, Kyle, COleten, Jerry, Sandip");


        // second row
        EventEntity pepperoni = new EventEntity();
        pepperoni.setId("pepperoni");
        pepperoni.put("unit","Package");
        pepperoni.put("walmart/Hyvee","13.44");

        // second row
        EventEntity sauce = new EventEntity();
        sauce.setId("sauce");
        sauce.put("unit","can");
        sauce.put("walmart/Hyvee","0.0");

        // second row
        EventEntity blackOlives = new EventEntity();
        blackOlives.setId("blackOlives");
        blackOlives.put("unit","package");
        blackOlives.put("walmart/Hyvee","12.34");

        // second row
        EventEntity beef = new EventEntity();
        beef.setId("beef");
        beef.put("unit","we are testing");
        beef.put("walmart/Hyvee","12.24");






       // this will create a collection k=in kinvey
        AppData<EventEntity> myevents = mKinveyClient.appData("eventsCollection",EventEntity.class);
        try{

            EventEntity result1 = myevents.saveBlocking(haha).execute();
            EventEntity result2 = myevents.saveBlocking(pepperoni).execute();
            EventEntity result3 = myevents.saveBlocking(sauce).execute();
            EventEntity result4 = myevents.saveBlocking(blackOlives).execute();
            EventEntity result5 = myevents.saveBlocking(beef).execute();

        }catch (IOException e){
            System.out.println("Couldn't save! -> " + e);
        }




        launch(args);

    }




}





