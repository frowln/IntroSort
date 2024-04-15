import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenerator {

    private static int min_size = 100;
    private static int max_size = 10000;

    public static void main(String[] args) {
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            int size = random.nextInt(max_size - min_size + 1) + min_size;
            int[] data = new int[size];
            for (int j = 0; j < size; j++) {
                data[j] = random.nextInt();
            }
            saveDataToFile(data, i);
        }
    }

    public static void saveDataToFile(int[] data, int index) {
        String filename = "data_" + index + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int value : data) {
                writer.write(Integer.toString(value));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}