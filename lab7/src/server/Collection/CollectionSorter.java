package server.Collection;

import com.company.sourse.Dragon;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Class to sort collection
 */
public class CollectionSorter {
    public static ConcurrentHashMap<Long, Dragon> collection = CollectionDownloader.getCollection();
    static List<ConcurrentHashMap.Entry<Long, Dragon>> list;



    /**
     * Method to sort collection by name
     */
    public static void sortCollection() {
        list = new ArrayList<>(collection.entrySet());
        Collections.sort(list, Comparator.comparing((ConcurrentHashMap.Entry<Long, Dragon> o) -> o.getValue().getName()));
        collection.clear();
        list.forEach(s -> collection.put(s.getKey(), s.getValue()));
    }

    /**
     * Method to show collection to user
     */
    public static StringBuilder getCollectionForUser() {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(s -> stringBuilder.append(s.getValue()));
        return stringBuilder;
    }
    /**
     * Method to get sorted collection
     *
     * @return collection
     */
    public static ConcurrentHashMap<Long, Dragon> getCollection() {
        sortCollection();
        return collection;
    }

}