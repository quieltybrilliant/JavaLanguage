package algo.sword.array;

/**
 * @author guang
 * @date Create in 2020/1/23 22:24
 */
public class CountOneTimes {
    /**
     * 43-从1到n整数中1出现的次数
     * - https://www.nowcoder.com/practice/bd7f978302044eee894445e244c7eee6
     */
    public static void main(String[] args) {
        System.out.println(getNumberOneCount(11));
    }


    /**
     * 计算1的个数，比如11个数为2
     * @param n
     * @return
     */
    public static int getNumberOneCount(int n) {
        int cnt = 0;
        for (int m = 1; m <= n; m *= 10) {
            int a = n / m;
            int b = n % m;
            cnt += (a + 8) / 10 * m + (a % 10 == 1 ? b + 1 : 0);
        }
        return cnt;
    }


}
