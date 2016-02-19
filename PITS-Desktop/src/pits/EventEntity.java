package pits;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

/**
 * Created by sandi on 2/13/2016.
 */
public class EventEntity extends GenericJson {


    @Key("_id")
    private String id;
    @Key
    private String unit;
    @Key
    private String walmartHyvee;
    @Key
    private String usFoods;
    @Key
    private String roma;
    @Key
    private String count;
    @Key
    private String name;
    @Key
    private String location;
    @Key
    private String date;
    @Key("_kmd")
    private KinveyMetaData meta; // Kinvey metadata, OPTIONAL
    @Key("_acl")
    private KinveyMetaData.AccessControlList acl; //Kinvey access control, OPTIONAL
    public EventEntity(){}  //GenericJson classes must have a public empty constructor


    public String getId() {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }


    public String getUnit(){
        return unit;
    }

    public void setUnit(String unit){
        this.unit=unit;
    }


    public String getWalmartHyvee() {
        return walmartHyvee;
    }

    public void setWalmartHyvee(String walmartHyvee) {
        this.walmartHyvee = walmartHyvee;
    }

    public String getUsFoods() {
        return usFoods;
    }

    public void setUsFoods(String usFoods) {
        this.usFoods = usFoods;
    }

    public String getRoma() {
        return roma;
    }

    public void setRoma(String roma) {
        this.roma = roma;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}