package server;

import com.company.sourse.Dragon;

import java.io.Serializable;
import java.util.*;

/**
 * Class to sort collection
 */
public class CollectionSorter implements Serializable {
    public static LinkedHashMap<Long, Dragon> collection = FileController.getCollection();

    /**
     * Method to sort collection by name
     */
    public static void sortCollection() {
        List<Map.Entry<Long, Dragon>> list = new ArrayList<>(collection.entrySet());
        Collections.sort(list, Comparator.comparing(o -> o.getValue().getName()));
        collection.clear();
        list.forEach(s -> collection.put(s.getKey(), s.getValue()));
    }

    /**
     * Method to get sorted collection
     *
     * @return collection
     */
    public static Map<Long, Dragon> getCollection() {
        sortCollection();
        return collection;
    }
}