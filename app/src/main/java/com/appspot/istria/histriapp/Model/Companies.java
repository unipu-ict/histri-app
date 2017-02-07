package com.appspot.istria.histriapp.Model;

import com.appspot.istria.histriapp.R;

/**
 * Created by bozidarkokot on 20/06/16.
 */
public class Companies {
    private  String id;
    private  String discount,item,company;
    private  String address;
    private  byte[] logo;


    public static int[] categoryStrings = {
             // Uvijek nula, nemamo nulti index na bazi
            R.string.puljanka,
            R.string.beer_club,
            R.string.tivoli

    };

    public Companies(String id, String discount,String item,String company,String address,byte[] logo) {
        this.id = id;
        this.discount = discount;
        this.item = item;
        this.company = company;
        this.address = address;
        this.logo = logo;


    }

    public  byte[] getLogo(){
        return logo;
    }
    public  String getAddress(){
        return  address;
    }
    public String getDiscount() {return discount;}
    public String getItem() {return item;}
    public String getCompany() {return company;}

    public String getId() {return id;}
}