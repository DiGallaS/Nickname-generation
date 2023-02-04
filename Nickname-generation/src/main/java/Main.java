import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger three = new AtomicInteger();
    static AtomicInteger four = new AtomicInteger();
    static AtomicInteger five = new AtomicInteger();
    static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        for (String text:texts) {
            Runnable logic = () -> {
                int count = 0;
                for (int i = 1; i < text.length(); i++){
                    if(text.charAt(i-1) == text.charAt(i)){
                        count++;
                    }
                }
                if (count == text.length()-1 && text.length() == 3){
                    three.getAndIncrement();
                } else if (count == text.length()-1 && text.length() == 4){
                    four.getAndIncrement();
                } else if (count == text.length()-1 && text.length() == 5){
                    five.getAndIncrement();
                }
            };
            threads.add(new Thread(logic));

            Runnable logic1 = () -> {
                int count = 0;
                for (int i = 0; i < text.length() / 2; i++){
                    if(text.charAt(i) == text.charAt(text.length() - 1 - i)){

                        count++;
                    }else{
                        break;
                    }
                }
                if (count == 1 && text.length() == 3){
                    three.getAndIncrement();
                } else if (count == 2 && text.length() == 4){
                    four.getAndIncrement();
                } else if (count == 2 && text.length() == 5){
                    five.getAndIncrement();
                }

            };
            threads.add(new Thread(logic1));

            Runnable logic2 = () -> {
                int count = 0;
                for (int i = 1; i < text.length(); i++){
                    if (text.charAt(i-1) <= text.charAt(i)){
                        count++;
                    }else{
                        break;
                    }
                }
                if (count == text.length() - 1 && text.length() == 3){
                    three.getAndIncrement();
                } else if (count == text.length() - 1 && text.length() == 4){
                    four.getAndIncrement();
                } else if (count == text.length() - 1 && text.length() == 5){
                    five.getAndIncrement();
                }
            };
            threads.add(new Thread(logic2));
        }

        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }

        System.out.println("Красивые слова с длиной 3: " + three + " шт");
        System.out.println("Красивые слова с длиной 4: " + four + " шт");
        System.out.println("Красивые слова с длиной 5: " + five + " шт");
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
