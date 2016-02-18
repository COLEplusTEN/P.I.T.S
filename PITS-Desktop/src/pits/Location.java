package pits;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/**
 * Created by sandi on 2/13/2016.
 */
public class Location extends GenericJson {

    @Key
    private String Name;
    @Key
    private String FoodType;
    public Location(){} //GenericJson classes must have a public empty constructor
    private static class Address extends GenericJson {
        @Key
        private String Address;
        @Key
        private String City;
        @Key
        private String State;
        @Key
        private String Zip;
        public Address(){} //GenericJson classes must have a public empty constructor

        public String getAddress(){
            return Address;
        }

        public void setAddress(String address){
            this.Address=address;
        }
    }





}
