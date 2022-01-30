package com.company;

import com.company.utilities.ConsoleController;
import com.company.utilities.FileController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Main {
//переменные среды не работают в интеллиже! надо жарник!
    public static void main(String[] args) {
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("id","10");
        map.put("name","artem");
        Gson gson = new Gson();
        String json = gson.toJson(map);
        System.out.println(json);
        String test = "{\"id\":\"10\",\"name\":\"artem\"}";
        Type type = new TypeToken<LinkedHashMap<String,String>>(){}.getType();
        LinkedHashMap<String,String> list = new Gson().fromJson(test,type);
        System.out.println(list);
        ConsoleController console = new ConsoleController();
    }
}
