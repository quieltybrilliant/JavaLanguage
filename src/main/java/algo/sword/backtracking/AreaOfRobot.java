package algo.sword.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author guang
 * @date Create in 2019/12/21 22:02
 */
public class AreaOfRobot {
    /**
     * 13-机器人的运动范围
     * 地上有一个 rows 行和 cols 列的方格。坐标从 [0,0] 到 [rows-1,cols-1] 。
     * 一个机器人从坐标 [0,0] 的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，
     * 但是不能进入行坐标和列坐标的数位之和大于 threshold 的格子。
     * 例如，当 threshold 为 18 时，机器人能够进入方格   [35,37] ，
     * 因为 3+5+3+7 = 18。但是，它不能进入方格 [35,38] ，因为 3+5+3+8 = 19 。
     * 请问该机器人能够达到多少个格子？
     *
     * 输入：
     * 10,1,100 （阈值，row，col）
     * 复制
     * 返回值：
     * 29
     * 复制
     * 说明：
     * [0,0],[0,1],[0,2],[0,3],[0,4],
     * [0,5],[0,6],[0,7],[0,8],[0,9],
     * [0,10],[0,11],[0,12],[0,13],[0,14],
     * [0,15],[0,16],[0,17],[0,18],[0,19],
     * [0,20],[0,21],[0,22],[0,23],[0,24],
     * [0,25],[0,26],[0,27],[0,28] 这29种，
     * 后面的[0,29],[0,30]以及[0,31]等等是无法到达的
     *
     * https://www.nowcoder.com/practice/6e5207314b5241fb83f2329e89fdecc8
     */

    public static void main(String[] args) {
        int row = 10;
        int col = 10;
        boolean[][] visitDFS = new boolean[row][col];
        boolean[][] visitBFS = new boolean[row][col];
        System.out.println(dfs(0, 0, row, col, 5, visitDFS));
        System.out.println(bfs(0, 0, row, col, 5, visitBFS));
    }

    /**
     * 广度优先遍历
     */
    public static int bfs(int x, int y, int row, int col, int threshold, boolean[][] visit) {
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.add(new int[]{x,y});
        visit[0][0] = true;
        int res = 0;
        int[] item ;
        while (!queue.isEmpty()) {
            item = queue.poll();
            res += 1;
            walkTo(item[0] - 1, item[1], row, col, threshold, visit,queue);
            walkTo(item[0] + 1, item[1], row, col, threshold, visit,queue);
            walkTo(item[0], item[1] + 1, row, col, threshold, visit,queue);
            walkTo(item[0], item[1] - 1, row, col, threshold, visit,queue);
        }
        return res;
    }

    public static void walkTo(int x, int y, int row, int col, int threshold, boolean[][] visit,Queue<int[]> queue) {
        if (x < 0 || y < 0 || x >= row || y >= col || visit[x][y] || isNotThreshold(x, y, threshold)) {
            return ;
        }
        visit[x][y] = true;
        queue.add(new int[]{x,y});
    }

    /**
     * 深度优先遍历
     */
    public static int dfs(int x, int y, int row, int col, int threshold, boolean[][] visit) {
        if (x < 0 || y < 0 || x >= row || y >= col || visit[x][y] || isNotThreshold(x, y, threshold)) {
            return 0;
        }

        visit[x][y] = true;

        return dfs(x - 1, y, row, col, threshold, visit)
                + dfs(x + 1, y, row, col, threshold, visit)
                + dfs(x, y + 1, row, col, threshold, visit)
                + dfs(x, y - 1, row, col, threshold, visit)
                + 1
                ;
    }


    public static boolean isNotThreshold(int a, int b, int threshold) {
        int sum = 0;
        while (a > 0) {
            sum += a % 10;
            a = a / 10;
        }
        while (b > 0) {
            sum += b % 10;
            b = b / 10;
        }
        if (sum > threshold) {
            return true;
        } else {
            return false;
        }
    }


}
