package dev.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class CollectionUtils {

    public static int getNextTaskId(List<Integer> list) {
        int key = 0;

        while (list.contains(key)) {
            key++;
        }
        return key;
    }

    public static int getNextTaskId(Set<Integer> collection) {
        List<Integer> list = new ArrayList<>(collection);
        int key = 0;

        while (list.contains(key)) {
            key++;
        }
        return key;
    }
}
