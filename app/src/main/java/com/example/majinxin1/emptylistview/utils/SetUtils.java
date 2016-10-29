package com.example.majinxin1.emptylistview.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by MAJINXIN1 on 2016/10/28.
 */
public class SetUtils {
    public static Set<String> intersection(Set<String> set1,Set<String> set2) {//交集
        Set<String> result = new HashSet<>();
        result.clear();
        result.addAll(set1);
        result.retainAll(set2);
        return result;

    }
    public static Set<String> different(Set<String> set1,Set<String> set2) {//差集set1-set2
        Set<String> result = new HashSet<>();
        result.clear();
        result.addAll(set1);
        result.removeAll(set2);
        return result;

    }
    public static Set<String> union(Set<String> set1,Set<String> set2) {//并集
        Set<String> result = new HashSet<>();
        result.clear();
        result.addAll(set1);
        result.addAll(set2);
        return result;

    }
}
