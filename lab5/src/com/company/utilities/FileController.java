package com.company.utilities;

import com.company.sourse.Dragon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;

public class FileController {
    final private String file = "lab5";
    final private String env = System.getenv().get(file);
    private FileInputStream fis = null;
    private Gson gson = new Gson();
    private String test = "[{'id':1,'name':'Oleg','coordinates':{'x':2.0,'y':3.0}]";
    public FileController(){}
    Type type = new TypeToken<ArrayList<String>>(){}.getType();
    List<String> list = new Gson().fromJson(test,type);
    public LinkedHashMap<String,String> readCollection(){
        if (env != null){
            LinkedHashMap<String,String> collection = new LinkedHashMap<>();
            try (Scanner scanner = new Scanner(new InputStreamReader(new FileInputStream(env)))){
                Type type = new TypeToken<LinkedHashMap<String,String>>(){}.getType();
                collection = gson.fromJson(test, type);
                return collection;
            }catch (IOException e) {
                e.printStackTrace();
            }
        }return new LinkedHashMap<>();
    }
}
