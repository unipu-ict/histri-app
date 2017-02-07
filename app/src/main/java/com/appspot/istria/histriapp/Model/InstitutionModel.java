package com.appspot.istria.histriapp.Model;

/**
 * Created by bozidarkokot on 04/01/17.
 */
public class InstitutionModel {
    public String institution_name="";
    public double longitude = 0;
    public String address = "";
    public int passedState = 0;
    public  double latitude = 0;
    public String institution_identifier="";

    public  InstitutionModel(String institution_identifier,String institution_name,double latitude,double longitude,String address,int passedState){
        this.institution_identifier = institution_identifier;
        this.institution_name = institution_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.passedState = passedState;
        this.address = address;
    }

    public String getInstitution_name(){
        return institution_name;
    }
    public String getInstitution_identifier(){
        return  institution_identifier;
    }
    public int getPassedState(){
        return passedState;
    }
    public double getLongitude(){
        return  longitude;
    }
    public double getLatitude(){
        return latitude;
    }
    public String getAddress(){
        return address;
    }


}
