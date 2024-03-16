import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter3 = new AtomicInteger(0);
    public static AtomicInteger counter4 = new AtomicInteger(0);
    public static AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread palindrom = new Thread(() -> {
            for (String text : texts) {
                if (text.equals(new StringBuilder(text).reverse().toString())) {
                    count(text.length());
                }
            }
        });
        palindrom.start();

        Thread sameLetters = new Thread(() -> {
            for (String text : texts) {
                if (text.chars().allMatch(c -> text.charAt(0) == c)) {
                   count(text.length());
                }
            }
        });
        sameLetters.start();

        Thread lexicographically = new Thread(() -> {
            for (String text : texts) {
                boolean order = true;
                for (int i = 0; i < text.length() - 1; i++) {
                    if (text.charAt(i) > text.charAt(i + 1)) {
                        order = false;
                        break;
                    }
                }
                if (order) {
                    count(text.length());
                }
            }
        });
        lexicographically.start();

        System.out.println("Красивых слов с длиной 3: " + counter3);
        System.out.println("Красивых слов с длиной 4: " + counter4);
        System.out.println("Красивых слов с длиной 5: " + counter5);
    }


    public static void count(int length) {
        switch (length) {
            case 3 -> counter3.getAndIncrement();
            case 4 -> counter4.getAndIncrement();
            case 5 -> counter5.getAndIncrement();
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}