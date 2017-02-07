package com.appspot.istria.histriapp.Model;


/**
 * Created by bozidarkokot on 10/12/16.
 */
public class AdventureModel {
    public String riddleDesc;
    public  String riddleResult;

    public  AdventureModel(String riddleDesc,String riddleResult){
        this.riddleDesc = riddleDesc;
        this.riddleResult = riddleResult;
    }
    public  String getRiddleDesc(){
        return  riddleDesc;
    }
    public String getRiddleResult(){
        return  riddleResult;
    }
}
