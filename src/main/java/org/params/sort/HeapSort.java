package org.params.sort;

public class HeapSort {

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};
        heapSort(arr);
        System.out.println("排序结果:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

    /**
     * 堆排序入口函数
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // 1. 构建最大堆（初始堆化）
        buildMaxHeap(arr);

        // 2. 逐个提取堆顶元素（最大值）并调整堆
        for (int i = n - 1; i > 0; i--) {
            // 将当前堆顶（最大值）交换到数组末尾
            swap(arr, 0, i);
            // 调整剩余元素使其满足最大堆性质
            heapify(arr, i, 0);
        }
    }

    /**
     * 构建最大堆
     */
    private static void buildMaxHeap(int[] arr) {
        int n = arr.length;
        // 从最后一个非叶子节点开始向前调整
        for (int i = n/2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
    }

    /**
     * 调整堆（下沉操作）
     * @param heapSize 当前堆的有效长度
     * @param rootIdx  要调整的子堆的根节点索引
     */
    private static void heapify(int[] arr, int heapSize, int rootIdx) {
        int largestIdx = rootIdx;      // 初始化最大元素为根节点
        int leftChildIdx = 2*rootIdx + 1;  // 左子节点索引
        int rightChildIdx = 2*rootIdx + 2; // 右子节点索引

        // 比较左子节点和当前最大值
        if (leftChildIdx < heapSize && arr[leftChildIdx] > arr[largestIdx]) {
            largestIdx = leftChildIdx;
        }

        // 比较右子节点和当前最大值
        if (rightChildIdx < heapSize && arr[rightChildIdx] > arr[largestIdx]) {
            largestIdx = rightChildIdx;
        }

        // 如果最大值不是根节点，需要交换并递归调整
        if (largestIdx != rootIdx) {
            swap(arr, rootIdx, largestIdx);
            heapify(arr, heapSize, largestIdx); // 递归调整受影响的子树
        }
    }

    /**
     * 交换数组中的两个元素
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

