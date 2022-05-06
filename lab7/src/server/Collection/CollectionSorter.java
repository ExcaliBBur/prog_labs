package server.Collection;

import com.company.sourse.Dragon;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Class to sort collection
 */
public class CollectionSorter {
    public static ConcurrentHashMap<Long, Dragon> collection = CollectionDownloader.getCollection();

    /**
     * Method to sort collection by name
     */
    public static void sortCollection() {
        List<ConcurrentHashMap.Entry<Long, Dragon>> list = new ArrayList<>(collection.entrySet());
        //list.sort(Comparator.comparing(o -> o.getValue().getName()));
        Collections.sort(list, Comparator.comparing((ConcurrentHashMap.Entry<Long, Dragon> o) -> o.getValue().getName()));
        collection.clear();
        list.forEach(s -> collection.put(s.getKey(), s.getValue()));
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