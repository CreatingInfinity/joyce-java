// Joyce Faith G. Laurete

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DescriptiveStatistics {

    public static void main(String[] args) {
        try {
            List<Double> data = readDataFromFile("temperature.txt");

            if (data != null) {
                calculateStatistics(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Double> readDataFromFile(String filename) throws IOException {
        List<Double> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(Double.parseDouble(line));
            }
        }

        return data;
    }

    public static void calculateStatistics(List<Double> data) {
        if (data.isEmpty()) {
            System.out.println("Data is empty. Cannot calculate statistics.");
            return;
        }

        Collections.sort(data);

        double mean = calculateMean(data);
        double median = calculateMedian(data);
        double mode = calculateMode(data);
        double min = data.get(0);
        double max = data.get(data.size() - 1);
        double range = max - min;
        double sum = calculateSum(data);
        double variance = calculateVariance(data, mean);
        double stdDeviation = Math.sqrt(variance);
        double coefficientOfVariation = (mean != 0) ? (stdDeviation / mean) * 100 : 0;
        double skewness = calculateSkewness(data, mean, stdDeviation);
        double kurtosis = calculateKurtosis(data, mean, stdDeviation);
        int count = data.size();
        double percentile25 = calculatePercentile(data, 25);
        double percentile75 = calculatePercentile(data, 75); // Complete the method call

        System.out.println("Mean: " + mean);
        System.out.println("Median: " + median);
        System.out.println("Mode: " + mode);
        System.out.println("Minimum: " + min);
        System.out.println("Maximum: " + max);
        System.out.println("Range: " + range);
        System.out.println("Sum: " + sum);
        System.out.println("Variance: " + variance);
        System.out.println("Standard Deviation: " + stdDeviation);
        System.out.println("Coefficient of Variation: " + coefficientOfVariation + "%");
        System.out.println("Skewness: " + skewness);
        System.out.println("Kurtosis: " + kurtosis);
        System.out.println("Count: " + count);
        System.out.println("25th Percentile: " + percentile25);
        System.out.println("75th Percentile: " + percentile75);
    }

    private static double calculateMean(List<Double> data) {
        double sum = 0;
        for (double num : data) {
            sum += num;
        }
        return sum / data.size();
    }

    private static double calculateMedian(List<Double> data) {
        int size = data.size();
        int median = size / 2;
        return (data.get(median));
    }

    private static double calculateMode(List<Double> data) {
        double mode = data.get(0);
        int maxCount = 1;

        int currentCount = 1;
        double currentElement = data.get(0);

        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).equals(currentElement)) {
                currentCount++;
            } else {
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                    mode = currentElement;
                }
                currentElement = data.get(i);
                currentCount = 1;
            }
        }

        if (currentCount > maxCount) {
            mode = currentElement;
        }

        return mode;
    }

    private static double calculateSum(List<Double> data) {
        double sum = 0;
        for (double num : data) {
            sum += num;
        }
        return sum;
    }

    private static double calculateVariance(List<Double> data, double mean) {
        double sumSquaredDiff = 0;
        for (double num : data) {
            sumSquaredDiff += Math.pow(num - mean, 2);
        }
        return sumSquaredDiff / data.size();
    }

    private static double calculateSkewness(List<Double> data, double mean, double stdDeviation) {
        double sumCubedDiff = 0;
        for (double num : data) {
            sumCubedDiff += Math.pow(num - mean, 3);
        }
        double n = data.size();
        return (n / ((n - 1) * (n - 2))) * (sumCubedDiff / Math.pow(stdDeviation, 3));
    }

    private static double calculateKurtosis(List<Double> data, double mean, double stdDeviation) {
        double sumFourthPowerDiff = 0;
        for (double num : data) {
            sumFourthPowerDiff += Math.pow(num - mean, 4);
        }
        double n = data.size();
        return ((n * (n + 1)) / ((n - 1) * (n - 2) * (n - 3))) * (sumFourthPowerDiff / Math.pow(stdDeviation, 4))
                - (3 * Math.pow(n - 1, 2) / ((n - 2) * (n - 3)));
    }

    private static double calculatePercentile(List<Double> data, double percentile) {
        Collections.sort(data);
        int n = data.size();
        double position = (percentile / 100) * (n + 1);
        if (position == 1) {
            return data.get(0);
        } else if (position == n) {
            return data.get(n - 1);
        } else {
            int lowerIndex = (int) position - 1;
            double lowerValue = data.get(lowerIndex);
            double upperValue = data.get(lowerIndex + 1);
            return lowerValue + (position - Math.floor(position)) * (upperValue - lowerValue);
        }
    }
}
