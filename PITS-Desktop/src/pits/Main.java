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


        try{


            // second row
            EventEntity pepperoni = new EventEntity();
            pepperoni.setId("pepperoni");
            pepperoni.put("unit","Package");
            pepperoni.put("walmart/Hyvee","13.44");
            pepperoni.put("usFoods","13.44");
            pepperoni.put("roma","13.44");
            pepperoni.put("count","13.44");

            // second row
            EventEntity sauce = new EventEntity();
            sauce.setId("sauce");
            sauce.put("unit","can");
            sauce.put("walmart/Hyvee","0.0");
            sauce.put("usFoods","13.44");
            sauce.put("roma","13.44");
            sauce.put("count","13.44");

            // second row
            EventEntity blackOlives = new EventEntity();
            blackOlives.setId("blackOlives");
            blackOlives.put("unit","package");
            blackOlives.put("walmart/Hyvee","12.34");
            blackOlives.put("usFoods","13.44");
            blackOlives.put("roma","13.44");
            blackOlives.put("count","13.44");

            // second row
            EventEntity beef = new EventEntity();
            beef.setId("beef");
            beef.put("unit","we are testing");
            beef.put("walmart/Hyvee","12.24");
            beef.put("usFoods","13.44");
            beef.put("roma","13.44");
            beef.put("count","13.44");


            // this will create a collection k=in kinvey
            AppData<EventEntity> myevents = mKinveyClient.appData("eventsCollection",EventEntity.class);
            try{

                EventEntity result2 = myevents.saveBlocking(pepperoni).execute();
                EventEntity result3 = myevents.saveBlocking(sauce).execute();
                EventEntity result4 = myevents.saveBlocking(blackOlives).execute();
                EventEntity result5 = myevents.saveBlocking(beef).execute();

            }catch (IOException e){
                System.out.println("Couldn't save! -> " + e);
            }


            /**
             * this also work:
             * basically its the same thing :::
             * */
            //another test from the kivey website:
            //The EventEntity class is defined above
            EventEntity event = new EventEntity();
            event.setName("Launch Party");
            //  event.setAddress("Kinvey HQ");
            AppData<EventEntity> myevents0 = mKinveyClient.appData("eventsCollection",EventEntity.class);
            try{
                EventEntity result = myevents0.saveBlocking(event).execute();
            }catch (IOException e){
                System.out.println("Couldn't save! -> " + e);
            }


        /*
        * fetching data from kinvey
        * */

            //The EventEntity class is defined above
            //The EventEntity class is defined above
            EventEntity event000 = new EventEntity();
            AppData<EventEntity> myEvents = mKinveyClient.appData("eventsCollection", EventEntity.class);
            try{
                EventEntity result = myEvents.getEntityBlocking("sauce").execute();
                System.out.println();
                System.out.println("We are printing the result here:");
                System.out.println("Printing the ID: " + result.getId());
               //  System.out.println(result.values().toArray().toString());
               // System.out.println(result);
              //  System.out.println(tempResult);
                System.out.println("This is name" + result.getName());
                System.out.println(result.getUnit());
            }catch (IOException e){
                System.out.println("Couldn't get! -> " + e);
            }


        }

        catch(Exception e){
            System.out.println("Some problem with Kinvey");
        }

        launch(args);

    }




}





