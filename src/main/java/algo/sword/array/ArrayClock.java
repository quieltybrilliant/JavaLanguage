package algo.sword.array;

import java.util.ArrayList;

/**
 * @author guang
 * @date Create in 2020/1/2 22:06
 */
public class ArrayClock {
    
    
    /**
     * 29-顺时针打印矩阵
     * - https://www.nowcoder.com/practice/9b4c81a02cd34f76be2659fa0d54342a
     */
    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 10, 11}, {8, 9, 4, 12, 13}, {7, 6, 5, 14, 15}};
        int ceilX = 0;
        int ceilY = 0;
        int floorX = matrix.length - 1;
        int floorY = matrix[0].length - 1;

        while (ceilX <= floorX && ceilY <= floorY) {
            clockprintMatrix(matrix, ceilX++, ceilY++, floorX--, floorY--);
        }
    }



    
    //1, 2, 3, 10, 11
    //8, 9, 4, 12, 13
    //7, 6, 5, 14, 15
    public static void clockprintMatrix(int[][] m, int ceilX, int ceilY, int floorX, int floorY) {

        if (ceilX == floorX) {
            for (int i = ceilY; i <= floorY; i++) {
                System.out.println(m[ceilX][i]);
            }
        } else if (ceilY == floorY) {
            for (int i = ceilX; i <= floorX; i++) {
                System.out.println(m[ceilY][i]);
            }
        } else {
            for (int i = ceilY; i < floorY; i++) {
                System.out.println(m[ceilX][i]);
            }

            for (int i = ceilX; i < floorX; i++) {
                System.out.println(m[i][floorY]);
            }

            for (int i = floorY; i > ceilY; i--) {
                System.out.println(m[floorX][i]);
            }


            for (int i = floorX; i > ceilX; i--) {
                System.out.println(m[i][ceilY]);
            }
        }


    }
}
