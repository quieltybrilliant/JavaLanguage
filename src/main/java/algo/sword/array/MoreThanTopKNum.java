package algo.sword.array;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author guang
 * @date Create in 2020/1/15 21:54
 */
public class MoreThanTopKNum {

    /**
     * 39-数组中出现次数超过一半的数字
     * - https://www.nowcoder.com/practice/e8a1b01a2df14cb2b228b30ee6a92163
     */
    public static int moreThanHalfNum(int[] array) {
        if (array.length == 0) {
            return -1;
        }
        int m = array[0];
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == m) {
                count++;
            } else {
                count--;
            }
            if (count == 0) {
                m = array[i];
                count = 1;
            }
        }

        count = 0;

        for (int j = 0; j < array.length; j++) {
            if (array[j] == m) {
                count++;
            }
        }
        if (count > array.length / 2) {
            return m;
        } else {
            return -1;
        }

    }


    /**
     * 数组中出现次数超过1/K的数字
     */
    public static void moreThanTopKNum(int[] array, int k) {
        if (array.length == 0) {
            System.out.println("array is empty");
        }
        if (array.length <= k) {
            for (int i = 0; i < array.length; i++) {
                System.out.println(array[i]);
            }
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (map.containsKey(array[i])) {
                map.put(array[i], map.get(array[i]) + 1);
            } else {
                if (map.size() == k) {
                    subEachKeyAndRemove(map);
                }
                map.put(array[i], 1);
            }
        }

        HashMap<Integer, Integer> countMap = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (map.containsKey(array[i])) {
                if (countMap.containsKey(array[i])) {
                    countMap.put(array[i], countMap.get(array[i]) + 1);
                } else {
                    countMap.put(array[i], 1);
                }
            }
        }

        for (Iterator<Map.Entry<Integer, Integer>> it = countMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, Integer> item = it.next();
            if (item.getValue() > array.length / k) {
                System.out.println(item.getKey() + ":" +item.getValue());
            }
        }
    }


    public static void subEachKeyAndRemove(HashMap<Integer, Integer> map) {

        for (Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, Integer> item = it.next();
            if (item.getValue() == 1) {
                it.remove();
            } else {
                map.put(item.getKey(), item.getValue() - 1);
            }
        }

    }

    public static void main(String[] args) {
        System.out.println(moreThanHalfNum(new int[]{1, 2, 3, 4, 1, 1, 1, 2}));
        System.out.println(moreThanHalfNum(new int[]{1, 2, 3, 4, 1, 1, 1}));
        System.out.println(moreThanHalfNum(new int[]{}));

        moreThanTopKNum(new int[]{1, 2, 3, 4, 1, 1, 1, 2}, 2);
        System.out.println("--------------------------------------------");
        moreThanTopKNum(new int[]{1, 2, 3, 4, 1, 1, 1, 2}, 3);
        System.out.println("--------------------------------------------");
        moreThanTopKNum(new int[]{1, 2, 3, 4, 1, 1, 1}, 2);


    }
}
