package com.appspot.istria.histriapp.Model;

/**
 * Created by bozidarkokot on 03/07/16.
 */
public class Instituitons {
    public  String name;
    public  String id;
    public byte[] logo;
    public  String institutionName;
    public String description;

    public Instituitons(String id,String name,byte[] logo,String description,String institutionName){
        this.id=id;
        this.name=name;
        this.description=description;
        this.institutionName=institutionName;
        this.logo = logo;
    }

    public String getDescription(){
        return description;
    }
    public String getInstitutionName(){
        return institutionName;
    }

    public  byte[] getLogo(){
        return logo;
    }
    public  String getName(){
        return  name;
    }
    public  String getId(){
        return  id;
    }
}
