package org.params.sort;


import java.util.Arrays;

/**
 *   时间复杂度 log n
 */
public class QuickSort {

    // 快速排序入口方法
    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            // 分区操作，获取基准索引
            int partitionIndex = partition(arr, left, right);
            // 递归排序左子数组
            quickSort(arr, left, partitionIndex - 1);
            // 递归排序右子数组
            quickSort(arr, partitionIndex + 1, right);
        }
    }

    // 分区操作（核心逻辑）
    private static int partition(int[] arr, int left, int right) {
        // 选择最右侧元素作为基准（可优化为三数取中法）
        int pivot = arr[right];
        int i = left; // 指向小于基准的最后一个元素

        // 遍历数组，将小于基准的元素交换到左侧
        for (int j = left; j < right; j++) {
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        // 将基准放到正确位置（i的右侧）
        swap(arr, i, right);
        return i; // 返回基准的最终位置
    }

    // 交换数组中两个元素
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 测试代码
    public static void main(String[] args) {
        int[] arr = {6, 3, 8, 2, 9, 1, 5};
        System.out.println((arr.length - 1)/2);
        quickSort(arr, 0, (arr.length - 1)/2);
        System.out.println("排序结果: " + Arrays.toString(arr));
        // 输出: [1, 2, 3, 5, 6, 8, 9]
    }
}
