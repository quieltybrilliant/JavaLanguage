package algo.sword.binarytree;

/**
 * @author guang
 * @date Create in 2020/1/10 14:19
 */
public class BSTPostorder {
    /**
     * 33-二叉搜索树的后序遍历序列
     * <p>
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则返回 true ,否则返回 false 。
     * 假设输入的数组的任意两个数字都互不相同。
     * <p>
     * 要求：空间复杂度 O(n)，时间时间复杂度 O(n^2)
     * <p>
     * 提示：
     * 1.二叉搜索树是指父亲节点大于左子树中的全部节点，但是小于右子树中的全部节点的树。
     * 2.该题我们约定空树不是二叉搜索树
     * 3.后序遍历是指按照 “左子树-右子树-根节点” 的顺序遍历
     * <p>
     * 二叉搜索树： 若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
     * 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
     * 它的左、右子树也分别为二叉排序树。
     * <p>
     * - https://www.nowcoder.com/practice/a861533d45854474ac791d90e447bafd
     * [5,7,6,9,11,10,8]
     */


    public static void main(String[] args) {
//        int[] seq = {5, 7, 6, 9, 11, 10, 8};
//        int[] seq = {1, 3, 2};
        int[] seq = {3, 1, 2};
        System.out.println(isPostSer(seq, 0, seq.length - 1));
    }

    public static boolean isPostSer(int[] arr, int start, int end) {
        if (start >= end) {
            return true;
        }
        boolean flag = true;

        int mid = 0;
        for (int i = start; i < end; i++) {
            if (arr[i] > arr[end]) {
                if (flag) {
                    mid = i;
                }
                flag = false;

            }
            if (flag && arr[i] > arr[end]) {
                return false;
            }

            if (!flag && arr[i] < arr[end]) {
                return false;
            }
        }

        return isPostSer(arr, start, mid - 1) && isPostSer(arr, mid, end - 1);
    }
}