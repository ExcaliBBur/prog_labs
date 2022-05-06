package server.Collection;

import com.company.sourse.Dragon;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Class to sort collection
 */
public class CollectionSorter {
    public static ConcurrentHashMap<Long, Dragon> collection = CollectionDownloader.getCollection();
<<<<<<< HEAD
=======
    static List<ConcurrentHashMap.Entry<Long, Dragon>> list;


>>>>>>> 9f538714d29227ba0f15670f8aa7bc0599191580

    /**
     * Method to sort collection by name
     */
    public static void sortCollection() {
<<<<<<< HEAD
        List<ConcurrentHashMap.Entry<Long, Dragon>> list = new ArrayList<>(collection.entrySet());
        //list.sort(Comparator.comparing(o -> o.getValue().getName()));
=======
        list = new ArrayList<>(collection.entrySet());
>>>>>>> 9f538714d29227ba0f15670f8aa7bc0599191580
        Collections.sort(list, Comparator.comparing((ConcurrentHashMap.Entry<Long, Dragon> o) -> o.getValue().getName()));
        collection.clear();
        list.forEach(s -> collection.put(s.getKey(), s.getValue()));
    }

    /**
<<<<<<< HEAD
=======
     * Method to show collection to user
     */
    public static StringBuilder getCollectionForUser() {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(s -> stringBuilder.append(s.getValue()));
        return stringBuilder;
    }
    /**
>>>>>>> 9f538714d29227ba0f15670f8aa7bc0599191580
     * Method to get sorted collection
     *
     * @return collection
     */
    public static ConcurrentHashMap<Long, Dragon> getCollection() {
        sortCollection();
        return collection;
    }

}