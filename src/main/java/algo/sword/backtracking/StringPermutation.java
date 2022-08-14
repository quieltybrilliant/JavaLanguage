package algo.sword.backtracking;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author guang
 * @date Create in 2020/1/15 20:23
 */
public class StringPermutation {
    /**
     * 38-字符串的排列
     * 输入一个长度为 n 字符串，打印出该字符串中字符的所有排列，你可以以任意顺序返回这个字符串数组。
     * 例如输入字符串ABC,则输出由字符A,B,C所能排列出来的所有字符串ABC,ACB,BAC,BCA,CBA和CAB。
     * <p>
     * - https://www.nowcoder.com/practice/fe6b651b66ae47d7acce78ffdd9a96c7
     * ABC,ACB,BAC,BCA,CBA和CAB。
     * <p>
     * AABC,AACB,ABAC,ABCA,ACBA 和 ACAB
     * (AABC),(AACB),BAAC,BACA,CABA 和 CAAB
     * (ABAC),(ACAB),(BAAC),BCAA,CBAA 和 (CAAB)。
     * (ABCA),(ACBA),(BACA),(BCAA),(CBAA) 和 (CABA)。
     */

    public static void main(String[] args) {
        String str = "ABAC";
        StringBuffer temp = new StringBuffer();
        ArrayList<String> res = new ArrayList<String>();
        boolean[] visit = new boolean[str.length()];

        System.out.println(getrecursion(str.split(""), temp, res, visit).size());

    }

    public static ArrayList<String> getrecursion(String[] strArr, StringBuffer sb, ArrayList<String> res, boolean[] visit) {
        if (sb.length() == strArr.length) {
            if (!res.contains(sb.toString())) {
                res.add(sb.toString());
            }
        }

        for (int i = 0; i < strArr.length; i++) {
//            if (i > 0 && strArr[i - 1] == strArr[i] && !visit[i]) {
//                continue;
//            }
            if (!visit[i]) {
                visit[i] = true;
                sb.append(strArr[i]);
                getrecursion(strArr, sb, res, visit);
                visit[i] = false;
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return res;
    }


}
