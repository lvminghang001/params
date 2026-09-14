package org.params.sort.Afternoon; // 声明当前类所在的包。

import java.util.Arrays; // 导入数组输出工具类。

/** 软件设计师下午题原题风格：分治法实现归并排序。 */
public class MergeSort { // 定义归并排序算法类。

    /** 对数组 a 的下标 low 至 high 区间进行归并排序。 */
    public static void mergeSort(int[] a, int low, int high) { // 定义原题中的 MergeSort 函数。
        if (low < high) { // 若区间至少包含两个元素，则继续划分。
            int mid = (low + high) / 2; // 计算中间下标，将数组划分为左右两个子区间。

            mergeSort(a, low, mid); // 递归排序左半部分 a[low..mid]。
            mergeSort(a, mid + 1, high); // 递归排序右半部分 a[mid+1..high]。
            merge(a, low, mid, high); // 将两个已有序的子区间合并为一个有序区间。
        } // 当 low 等于 high 时，区间只有一个元素，天然有序。
    } // 结束 MergeSort 函数。

    /** 将两个有序区间 a[low..mid] 和 a[mid+1..high] 合并。 */
    private static void merge(int[] a, int low, int mid, int high) { // 定义原题中的 Merge 函数。
        int[] temp = new int[high - low + 1]; // 创建辅助数组，暂存本次合并后的有序结果。
        int i = low; // i 指向左侧有序区间的当前元素。
        int j = mid + 1; // j 指向右侧有序区间的当前元素。
        int k = 0; // k 指向辅助数组 temp 的当前位置。

        while (i <= mid && j <= high) { // 当左右两个区间都还存在未合并元素时循环。
            if (a[i] <= a[j]) { // 若左侧元素较小或相等，则先取左侧元素，保证排序稳定。
                temp[k++] = a[i++]; // 将左侧元素写入 temp，并移动 i 和 k。
            } else { // 否则右侧元素更小。
                temp[k++] = a[j++]; // 将右侧元素写入 temp，并移动 j 和 k。
            } // 结束左右元素的比较。
        } // 至少有一个子区间已经全部合并完成。

        while (i <= mid) { // 若左侧区间仍有剩余元素。
            temp[k++] = a[i++]; // 将左侧剩余元素依次复制到 temp。
        } // 左侧剩余元素复制完毕。

        while (j <= high) { // 若右侧区间仍有剩余元素。
            temp[k++] = a[j++]; // 将右侧剩余元素依次复制到 temp。
        } // 右侧剩余元素复制完毕。

        for (k = 0; k < temp.length; k++) { // 遍历辅助数组中的全部有序元素。
            a[low + k] = temp[k]; // 将结果复制回原数组对应的 low 至 high 区间。
        } // 本次合并完成。
    } // 结束 Merge 函数。

    public static void main(String[] args) { // 定义示例程序入口。
        int[] a = {38, 27, 43, 3, 9, 82, 10}; // 定义待排序的整数数组。

        System.out.println("排序前: " + Arrays.toString(a)); // 输出排序前的数组。
        mergeSort(a, 0, a.length - 1); // 调用归并排序，排序整个数组。
        System.out.println("排序后: " + Arrays.toString(a)); // 输出排序后的数组。
    } // 结束 main 方法。
} // 结束 MergeSort 类。
