package org.params.sort;

import java.util.Arrays;

public class MergeSort {

    // 归并排序入口方法
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        int[] tempArray = new int[arr.length]; // 临时数组用于合并操作
        mergeSort(arr, 0, arr.length - 1, tempArray);
    }

    // 递归分割与合并
    private static void mergeSort(int[] arr, int left, int right, int[] tempArray) {
        if (left < right) {
            int mid = left + (right - left) / 2; // 防溢出写法  取中间值
            mergeSort(arr, left, mid, tempArray);    // 递归左半部分
            mergeSort(arr, mid + 1, right, tempArray); // 递归右半部分
            merge(arr, left, mid, right, tempArray);   // 合并两个有序子数组
        }
    }

    // 合并两个有序子数组 [left, mid] 和 [mid+1, right]
    private static void merge(int[] arr, int left, int mid, int right, int[] tempArray) {
        int i = left;    // 左子数组起始指针
        int j = mid + 1; // 右子数组起始指针
        int k = 0;       // 临时数组指针

        // 合并：将较小的元素放入临时数组
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                tempArray[k++] = arr[i++];
            } else {
                tempArray[k++] = arr[j++];
            }
        }

        // 处理剩余元素（左子数组或右子数组可能未完全合并）
        while (i <= mid) {
            tempArray[k++] = arr[i++];
        }
        while (j <= right) {
            tempArray[k++] = arr[j++];
        }

        // 将临时数组数据复制回原数组
        k = 0;
        while (left <= right) {
            arr[left++] = tempArray[k++];
        }
    }

    // 测试代码
    public static void main(String[] args) {
        int[] arr = {6, 3, 8, 2, 9, 1, 5};
        mergeSort(arr);
        System.out.println("排序结果: " + Arrays.toString(arr));
        // 输出: [1, 2, 3, 5, 6, 8, 9]
    }
}
