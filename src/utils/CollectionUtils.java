package utils;

import java.util.Set;

public class CollectionUtils {

    public static int getNextTaskId(Set<Integer> collection) {
        int key = 0;

        while (collection.contains(key)) {
            key++;
        }
        return key;
    }
}
