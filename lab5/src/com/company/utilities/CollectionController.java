package com.company.utilities;

import com.company.sourse.Dragon;


import java.util.*;

public class CollectionController {
    public static LinkedHashMap<Long, Dragon> collection = FileController.getCollection();
    public static Map<Long, Dragon> sortedCollection = new TreeMap<>(collection);

    public CollectionController(){
        DataController data = new DataController();
        Dragon dragon = new Dragon(data.getId(), data.getName(), data.getCoordinates(), data.getCreationDate(), data.getAge(), data.getWingspan(),
                data.getWeight(), data.getColor(), data.getHead());
        collection.put(dragon.getId(), dragon);
        sortedCollection = new TreeMap<>(collection);
    }
    public CollectionController(String s){
        DataController data = new DataController("without id");
        Dragon dragon = new Dragon(Long.parseLong(s), data.getName(), data.getCoordinates(), data.getCreationDate(), data.getAge(), data.getWingspan(),
                data.getWeight(), data.getColor(), data.getHead());
        collection.put(Long.parseLong(s),dragon);
        sortedCollection = new TreeMap<>(collection);
    }
    public static Map<Long, Dragon> getCollection() {
        return sortedCollection;
    }
    public static void getCollectionForUser(){
        System.out.println(sortedCollection.values());
    }
}