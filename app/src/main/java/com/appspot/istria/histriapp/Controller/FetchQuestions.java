package com.appspot.istria.histriapp.Controller;

/**
 * Created by bozidarkokot on 19/06/16.
 */
public class FetchQuestions {

    /*
    static FetchTreasureLocations fetchTreasureLocations;
    public static String question = "";
    public  static  String answer1 = "";
    public  static String answer2 ="";
    public  static String answer3 = "";
    public  static  String correct = "";


   public static String inst_name="";
   public static String inst_id="";
    public static  String id = "";
    public static  String discount = "";
    public static  String item = "";
    public static  String company = "";

    public static String time = "";
    public  static  String score = "";
    public static String username = "";

    private static final String[] databaseFields = new String[]{
            "question",
            "answer1",
            "answer2",
            "answer3",
            "inst_id",
            "correct",
    };

    private static  int[] imageFields = new int[]{
        R.drawable.puljanka_logo,
            R.drawable.tivoli,
            R.drawable.beer,

    };

    private static final String[] institutionFields = new String[]{
            "id",
            "username",

            // "institution_id"

    };


    private static final String[] discountFields = new String[]{
            "id",
            "discount",
            "item",
            "company",
            // "institution_id"

    };

    private static final String[] userFields = new String[]{
            "username",
            "score",
            "time",
            // "institution_id"

    };
    private  static final String[] treasureFields = new String[]{
            "lat",
            "long",
            "description",
            "image",
    };






    public static void fetchQuestionsAndProceed(final Context context) {
        final Intent goToNext = new Intent(context, QuizActivity.class);
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://histriapp.esy.es/get_questions.php";

        JSONArray params = null;
        try {
            params = new JSONArray("[{\"question\": \"" + question + "\"},{\"answer1\":\"" + answer1 + "\"},{\"answer2\":\"" + answer2 + "\"},{\"answer3\":\"\"" + answer3 + "\"},{\"inst_id\":\"" + inst_id + "\"},{\"correct\":\"\"\"" + correct + "\"\"\"}]");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        DatabaseHelper db = new DatabaseHelper(context);
                        HashMap<String, String> business = new HashMap<>();
                        HashMap<Integer, Integer> category = new HashMap<>();


                        String dropCategoriesSQL = "DROP TABLE IF EXISTS "+ DatabaseHelper.databaseTable.QUESTIONS.table;
                        db.getWritableDatabase().execSQL(dropCategoriesSQL);

                        String createQuestionsTable = "CREATE TABLE "+ DatabaseHelper.databaseTable.QUESTIONS.table+
                                "("+ DatabaseHelper.questionColumn.ID.column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                DatabaseHelper.questionColumn.QUESTION.column+" VARCHAR, "+
                                DatabaseHelper.questionColumn.ANSWER1.column+" VARCHAR, "+
                                DatabaseHelper.questionColumn.ANSWER2.column+" VARCHAR," +
                                DatabaseHelper.questionColumn.ANSWER3.column+" VARCHAR," +
                                DatabaseHelper.questionColumn.CORRECT.column+" VARCHAR," +

                                DatabaseHelper.questionColumn.INSTITUTION_ID.column+" INTEGER);";
                        db.getWritableDatabase().execSQL(createQuestionsTable);

                        try {
                            for(int i = 0; i < response.length(); i++){
                                for(String fieldName : databaseFields){
                                    business.put(fieldName, response.getJSONObject(i).get(fieldName).toString());
                                 /*   if(fieldName.equals("category")){
                                        if(category.containsKey(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()))) {
                                            int services = category.get(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()));
                                            category.put(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()), ++services);
                                        }else{
                                            category.put(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()), 1);
                                        }
                                    }*/
                           /*     }

                                db.addQuestions(business.get("question"),business.get("answer1"),
                                        business.get("answer2"),business.get("answer3"),business.get("correct"),business.get("inst_id"));
                                business.clear();
                            }


                            //Take note of last update datetime
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("last_database_update", dateFormat.format(new Date())).apply();
                            db.close();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            db.close();
                        }

                        fetchTreasureLocations = new FetchTreasureLocations();
                        fetchTreasureLocations.syncTreasuresAndProceed(context);
                      // context.startActivity(goToNext);
                       // ((Activity)context).finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Services", error.toString());

                        fetchTreasureLocations = new FetchTreasureLocations();
                        fetchTreasureLocations.syncTreasuresAndProceed(context);

                       // context.startActivity(goToNext);
                       // ((Activity)context).finish();
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public static void fetchRanking(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://histriapp.esy.es/get_ranking.php";
        JSONArray params = null;
        try {
            params = new JSONArray("[{\"username\": \"" + username + "\"},{\"score\":\"" + score + "\"},{\"time\":\"" + time + "\"}]");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        DatabaseHelper db = new DatabaseHelper(context);
                        HashMap<String, String> business = new HashMap<>();
                        HashMap<Integer, Integer> category = new HashMap<>();


                        String dropCategoriesSQL = "DROP TABLE IF EXISTS "+ DatabaseHelper.databaseTable.RANKING.table;
                        db.getWritableDatabase().execSQL(dropCategoriesSQL);

                        String createRankingTable = "CREATE TABLE "+ DatabaseHelper.databaseTable.RANKING.table+
                                "("+ DatabaseHelper.rankingColumn.ID.column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                DatabaseHelper.rankingColumn.SCORE.column+" VARCHAR, "+
                                DatabaseHelper.rankingColumn.TIME.column+" VARCHAR, "+
                                DatabaseHelper.rankingColumn.USERNAME.column+" VARCHAR );";
                        db.getWritableDatabase().execSQL(createRankingTable);

                        try {
                            for(int i = 0; i < response.length(); i++){
                                for(String fieldName : userFields){
                                    business.put(fieldName, response.getJSONObject(i).get(fieldName).toString());
                                 /*   if(fieldName.equals("category")){
                                        if(category.containsKey(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()))) {
                                            int services = category.get(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()));
                                            category.put(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()), ++services);
                                        }else{
                                            category.put(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()), 1);
                                        }
                                    }*/
                               // }

                               /*db.addRanking(business.get("username"),business.get("score"),business.get("time"));
                                business.clear();
                            }


                            //Take note of last update datetime
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("last_database_update", dateFormat.format(new Date())).apply();
                            db.close();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            db.close();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Services", error.toString());

                    }
                });
        queue.add(jsonObjectRequest);
    }

/*
    public  static  void  singIn(final  Context context,final String username,final String email){
        String url = "http://histriapp.esy.es/insert_user.php";

        final  Intent intent = new Intent(context,InstitutionSelection.class);


        final StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
        /*get user id based on the user mail*/
    /*
                try {
                    if (new JSONObject(response).getBoolean("success")) {

                        Log.d("The file audit", " has been uploaded");
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    } else {
                        Toast.makeText(context,"The user with that name already exists",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("log za audit", "catch");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("server resp: ", "" + error);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("user_name", username);
                params.put("check", "bla");




                return params;
            }

        };

        RequestApp requestApp = new RequestApp();
        requestApp.setContext(context);
        requestApp.getInstance();
        requestApp.addToReqQueue(postRequest);
    }

*/



/*

    public static void fetchDiscounts(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://histriapp.esy.es/get_discount.php";

        JSONArray params = null;
        try {
            params = new JSONArray("[{\"discount\": \"" + discount + "\"},{\"item\":\"" + item + "\"},{\"company\":\"" + company + "\"},{\"id\":\"\"" + id + "\"\"}]");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        DatabaseHelper db = new DatabaseHelper(context);
                        HashMap<String, String> business = new HashMap<>();
                        HashMap<Integer, Integer> category = new HashMap<>();


                        String dropCategoriesSQL = "DROP TABLE IF EXISTS "+ DatabaseHelper.databaseTable.DISCOUNTS.table;
                        db.getWritableDatabase().execSQL(dropCategoriesSQL);

                        String createDiscountTable = "CREATE TABLE "+ DatabaseHelper.databaseTable.DISCOUNTS.table+
                                "("+ DatabaseHelper.discountColumn.ID.column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                DatabaseHelper.discountColumn.DISCOUNT.column+" VARCHAR, "+
                                DatabaseHelper.discountColumn.ADDRESS.column+" VARCHAR, "+
                                DatabaseHelper.discountColumn.LOGO.column+" BLOB, "+

                                DatabaseHelper.discountColumn.ITEM.column+" VARCHAR, "+
                                DatabaseHelper.discountColumn.COMPANY.column+" VARCHAR );";
                        db.getWritableDatabase().execSQL(createDiscountTable);

                        try {
                            for(int i = 0; i < response.length(); i++){
                                for(String fieldName : discountFields){
                                    business.put(fieldName, response.getJSONObject(i).get(fieldName).toString());
                                 /*   if(fieldName.equals("category")){
                                        if(category.containsKey(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()))) {
                                            int services = category.get(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()));
                                            category.put(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()), ++services);
                                        }else{
                                            category.put(Integer.valueOf(response.getJSONObject(i).get(fieldName).toString()), 1);
                                        }
                                    }*/
                              /*  }

                               // db.addDiscount(business.get("id"),business.get("discount"),
                               //         business.get("item"),business.get("company"));
                                business.clear();
                            }


                            //Take note of last update datetime
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("last_database_update", dateFormat.format(new Date())).apply();
                            db.close();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            db.close();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Services", error.toString());

                    }
                });
        queue.add(jsonObjectRequest);
    }
        }
*/
}