import java.util.*;
import java.util.concurrent.*;
import java.nio.file.*;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class AdvancedFeatures {

    public void runConcurrency() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Double> task1 = () -> 100.0;
        Callable<Double> task2 = () -> 200.0;

        var results = executor.invokeAll(List.of(task1, task2));

        for (Future<Double> result : results) {
            System.out.println(result.get());
        }

        executor.shutdown();
    }

    public void saveMenu(List<MenuItem> menu) {
        try {
            Files.write(Path.of("menu.txt"),
                    menu.stream().map(MenuItem::toString).toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showLocalization(List<MenuItem> menu) {
        NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);

        menu.forEach(item -> System.out.println(item.name() + " : " + us.format(item.price())));
    }
}