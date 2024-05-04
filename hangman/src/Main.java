import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        gameLoop();
    }

    public static void gameLoop() throws IOException {
        StringBuilder hidden = new StringBuilder();
        DrawHangman hangman = new DrawHangman();
        Scanner sc = new Scanner(System.in);
        String randomWord = getRandomWord().toLowerCase();
        hidden.append("*".repeat(randomWord.length()));
        int mistakes = 0;

        System.out.println(randomWord);

        while (true) {
            System.out.print("Input a keyword: ");
            String keyword = sc.next();

            if (randomWord.contains(keyword)) {
                for (int i = 0; i < randomWord.length(); i++) {
                    if (randomWord.charAt(i) == keyword.charAt(0)) {
                        hidden.replace(i, i + 1, keyword);
                    }
                }
                System.out.println(hidden);
            } else {
                hangman.drawHangman(mistakes);
                mistakes++;
            }

            if (mistakes == 7) {
                System.out.println("Sorry but you lost this game");
                break;
            }

            if (hidden.toString().equals(randomWord)) {
                System.out.println("Congratulation! You won this game");
                break;
            }
        }
    }

    public static String getRandomWord() throws IOException {
        Random random = new Random();
        List<String> words = Files.readAllLines(Paths.get("src/wordsDB/words.txt"));

        return words.get(random.nextInt(words.size()));
    }
}

