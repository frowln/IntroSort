import java.io.*;

public class IntroSort {
    private static final int INSERTION_SORT_THRESHOLD = 16;

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            String filename = "data_" + i + ".txt";
            int[] data = readDataFromFile(filename);
            long startTime = System.nanoTime();
            int iterations = introsort(data);
            long endTime = System.nanoTime();
            long resultOfWork = endTime - startTime;
            System.out.println("Файл: " + filename);
            System.out.println("Количество итерация: " + iterations);
            System.out.println("Время работы алгоритма: " + (double) resultOfWork/1000000 + " миллисекунд\n");
            saveResultsToFile("results_" + i + ".csv", data.length, resultOfWork, iterations);
        }
    }

    public static int introsort(int[] array) {
        return introsort(array, 0, array.length - 1, (int) (2 * Math.log(array.length) / Math.log(2)));
    }

    private static int introsort(int[] array, int lo, int hi, int depthLimit) {
        int iterations = 0;
        if (hi - lo > INSERTION_SORT_THRESHOLD) {
            if (depthLimit == 0) {
                heapsort(array, lo, hi);
                return iterations;
            }
            int p = partition(array, lo, hi);
            iterations++;
            iterations += introsort(array, lo, p, depthLimit - 1);
            iterations += introsort(array, p + 1, hi, depthLimit - 1);
        } else {
            insertionsort(array, lo, hi);
        }
        return iterations;
    }

    private static int partition(int[] array, int lo, int hi) {
        int pivot = array[lo + (hi - lo) / 2];
        int i = lo - 1;
        int j = hi + 1;
        while (true) {
            do {
                i++;
            } while (array[i] < pivot);
            do {
                j--;
            } while (array[j] > pivot);
            if (i >= j) return j;
            swap(array, i, j);
        }
    }

    private static void insertionsort(int[] array, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= lo && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private static void heapsort(int[] array, int lo, int hi) {
        for (int i = (hi - lo) / 2; i >= 0; i--) {
            heapify(array, i, hi, lo);
        }
        for (int i = hi; i > lo; i--) {
            swap(array, lo, i);
            heapify(array, lo, i - 1, lo);
        }
    }

    private static void heapify(int[] array, int root, int bottom, int offset) {
        while (root * 2 <= bottom) {
            int maxChild;
            int leftChild = root * 2;
            int rightChild = root * 2 + 1;
            if (leftChild == bottom || array[leftChild + offset] > array[rightChild + offset]) {
                maxChild = leftChild;
            } else {
                maxChild = rightChild;
            }
            if (array[root + offset] < array[maxChild + offset]) {
                swap(array, root + offset, maxChild + offset);
                root = maxChild;
            } else {
                return;
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static int[] readDataFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int size = (int) new java.io.File(filename).length() / 4; // Assuming each number is represented by 4 bytes
            int[] data = new int[size];
            int index = 0;
            while ((line = reader.readLine()) != null) {
                data[index++] = Integer.parseInt(line);
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void saveResultsToFile(String filename, int dataSize, long duration, int iterations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Data Size,Time (nanoseconds),Iterations\n");
            writer.write(dataSize + "," + duration + "," + iterations + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
