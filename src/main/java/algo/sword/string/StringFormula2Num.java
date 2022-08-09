package algo.sword.string;

import java.util.LinkedList;

/**
 * @Author: guang
 * @Date: 2022/8/9
 * @Desc: StringFormula2Num
 */
public class StringFormula2Num {
    public static void main(String[] args) {
        System.out.println(toNum("2*3+4/2".split(""), 0)[0]);
        System.out.println("------------------------------");
        System.out.println(toNum("4+3+2+4)".split(""), 0)[0]);
        System.out.println("------------------------------");
        System.out.println(toNum("4*(3+2*4)".split(""), 0)[0]);
        System.out.println("------------------------------");
    }

    public static int[] toNum(String[] str, int index) {
        int pre = 0;
        LinkedList<String> queue = new LinkedList<>();
        while (index < str.length && !str[index].equals(")")) {
            if (str[index].toCharArray()[0] >= '0' && str[index].toCharArray()[0] <= '9') {
                pre = pre * 10 + Integer.valueOf(str[index]);
                index++;
            } else if (!str[index].equals("(")) {
                computeNum(queue, pre);
                queue.add(str[index]);
                pre = 0;
                index++;
            } else {
                int[] arr = toNum(str, index+1);
                pre = arr[0];
                index = arr[1] + 1;
            }

        }
        computeNum(queue, pre);
        return new int[]{getNum(queue),index};
    }

    public static void computeNum(LinkedList<String> queue, int num) {
        if (!queue.isEmpty()) {
            String preStr = queue.pollLast();

            if (preStr.equals("-") || preStr.equals("+")) {
                queue.add(preStr);
            } else {
                int preNm = Integer.valueOf(queue.pollLast());
                num = preStr.equals("*") ? preNm * num : preNm / num;
            }
        }

        queue.add(String.valueOf(num));
    }

    public static int getNum(LinkedList<String> que) {
        int res = 0;
        boolean add = true;
        String cur = null;
        int num = 0;
        while (!que.isEmpty()) {
            cur = que.pollFirst();
            if (cur.equals("+")) {
                add = true;
            } else if (cur.equals("-")) {
                add = false;
            } else {
                num = Integer.valueOf(cur);
                res += add ? num : (-num);
            }
        }
        return res;
    }

}