package pits;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;
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

    final Client mKinveyClient = new Client.Builder("kid_ZyBaA-MgAe", "4e43176fdfe14d44878492851d93385f").build();


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Pagliai's Inventory Tracking System");
        primaryStage.setScene(new Scene(root, 1200, 900));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);

        // this was to check if kinvey is working or not
        Client myJavaClient = new Client.Builder("kid_ZyBaA-MgAe", "4e43176fdfe14d44878492851d93385f").build();
        try{
            Boolean pingResult = myJavaClient.ping();
            System.out.println("Client ping result -> " + pingResult);
        }catch(Exception e){
            System.out.println("something went wrong!");
            e.printStackTrace();
        }

        try{
            myJavaClient.user().loginBlocking("kid_ZyBaA-MgAe", "81181f297d044117a537c53378eae68d").execute();
            System.out.println("Client logged in -> " + myJavaClient.user().isUserLoggedIn());
        }catch (IOException e){
            System.out.println("Couldn't login -> " + e);
            e.printStackTrace();
        }









    }




}





