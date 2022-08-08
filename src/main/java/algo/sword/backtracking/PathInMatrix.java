package algo.sword.backtracking;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author guang
 * @date Create in 2019/12/20 23:02
 */
public class PathInMatrix {
    /**
     * 12-矩阵中的路径
     * <p>
     * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，
     * 每一步可以在矩阵中向左，向右，向上，向下移动一个格子。如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。
     * matrix
     * a b c e
     * s f c s
     * a d e e
     * 矩阵中包含一条字符串"bcced"的路径，但是矩阵中不包含"abcb"路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
     * https://www.nowcoder.com/practice/c61c6999eecb4b8f88a98f66b273a3cc
     * <p>
     * * ABCE
     * * SFCS
     * * ADEE
     * * 3，4
     * * ABCCED
     */

    public static void main(String[] args) {
        String[] matrixStr = "ABCESFCSADEE".split("");
        int row = 3;
        int col = 4;
        String[] path = "ABCCED".split("");

        String[][] matrix = new String[row][col];
        int index = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = matrixStr[index];
                index += 1;
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                boolean[][] visitDFS = new boolean[row][col];
                int value = 0;
                if (dfs(matrix, i, j, path, value, visitDFS)) {
                    System.out.println("start: " + i + "," + j);
                    return;
                }
            }
        }

    }

    public static boolean dfs(String[][] matrix, int x, int y, String[] path, int index, boolean[][] visit) {
        if (x < 0
                || y < 0
                || x >= matrix.length
                || y >= matrix[0].length
                || index >= path.length
                || !(path[index].equals(matrix[x][y]))
                || visit[x][y]
        ) {
            return false;
        }

        if (index == path.length - 1 && (path[index].equals(matrix[x][y]))) {
            System.out.println("true");
            System.out.println("end: " +x + "," + y);
            return true;
        }
        visit[x][y] = true;
        index += 1;
        if (dfs(matrix, x + 1, y, path, index, visit)
                || dfs(matrix, x - 1, y, path, index, visit)
                || dfs(matrix, x, y - 1, path, index, visit)
                || dfs(matrix, x, y + 1, path, index, visit)
        ) {

            return true;
        } else {
            visit[x][y] = false;
            return false;
        }
    }
}
