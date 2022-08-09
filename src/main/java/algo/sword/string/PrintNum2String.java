package algo.sword.string;

import java.util.LinkedList;

/**
 * @Author: guang
 * @Date: 2022/8/9
 * @Desc: PrintNum2String
 *
 *  * 字符串解码
 *  * 给定一个经过编码的字符串，返回它解码后的字符串。
 *  * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 *  * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 *  * <p>
 *  * 示例 1：
 *  * 输入：s = "3[a]2[bc]"
 *  * 输出："aaabcbc"
 *  * 示例 2：
 *  * <p>
 *  * 输入：s = "3[a2[c]]"
 *  * 输出："accaccacc"
 *  * 示例 3：
 *  * <p>
 *  * 输入：s = "2[abc]3[cd]ef"
 *  * 输出："abcabccdcdcdef"
 *  * 示例 4：
 *  * <p>
 *  * 输入：s = "abc3[cd]xyz"
 *  * 输出："abccdcdcdxyz"
 *  * <p>
 *  * 提示：
 *  * 1 <= s.length <= 30
 *  * 由小写英文字母、数字和方括号'[]' 组成
 *  * 保证是一个有效的输入。
 *  * 中所有整数的取值范围为[1, 300]
 *  */
public class PrintNum2String {
    public static void main(String[] args) {
        toString("abc3[cd]xyz".toCharArray(), 0);
        System.out.println("\n------------------------------");
        toString("2[abc]3[cd]ef".toCharArray(), 0);
        System.out.println("\n------------------------------");
        toString("3[a2[c]]".toCharArray(), 0);
    }

    public static void toString(char[] str, int index) {
        LinkedList<String> queue = new LinkedList<>();
        while (index < str.length) {
            if (str[index] == ']') {
                queue.add(process(queue));
                index++;
            } else {
                queue.add(String.valueOf(str[index]));
                index++;
            }

        }

        while (!queue.isEmpty()) {
            System.out.print(queue.poll());
        }

    }


    public static String process(LinkedList<String> queue) {

        String str = "";
        while (!queue.isEmpty() && !queue.peekLast().equals("[")) {
            str = queue.pollLast() + str;
        }
        queue.pollLast();
        int m = 0;
        while (!queue.isEmpty() && queue.peekLast() != "["
                && queue.peekLast().toCharArray()[0] >= '0' && queue.peekLast().toCharArray()[0] <= '9'
        ) {
            m = m * 10 + queue.pollLast().toCharArray()[0] - '0';
        }

        String res = "";
        for (int i = 0; i < m; i++) {
            res = res + str;
        }

        return res;
    }
}