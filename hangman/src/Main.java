import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static final String WIN_MESSAGE = "Congratulation! You won this game";
    private static final String LOSE_MESSAGE = "Sorry but you lost this game";
    private static final String RANDOM_WORD;

    static {
        try {
            RANDOM_WORD = getRandomWord().toLowerCase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        gameLoop();
    }

    public static void gameLoop() throws IOException {
        StringBuilder hidden = new StringBuilder();
        DrawHangman hangman = new DrawHangman();
        Scanner sc = new Scanner(System.in);
        hidden.append("*".repeat(RANDOM_WORD.length()));
        int mistakes = 0;

        System.out.println(RANDOM_WORD);

        while (true) {
            System.out.print("Input a keyword: ");
            String keyword = sc.next();

            if (RANDOM_WORD.contains(keyword)) {
                for (int i = 0; i < RANDOM_WORD.length(); i++) {
                    if (RANDOM_WORD.charAt(i) == keyword.charAt(0)) {
                        hidden.replace(i, i + 1, keyword);
                    }
                }
                System.out.println(hidden);
            } else {
                hangman.drawHangman(mistakes);
                mistakes++;
            }

            if (mistakes == 7) {
                System.out.println(LOSE_MESSAGE);
                break;
            }

            if (hidden.toString().equals(RANDOM_WORD)) {
                System.out.println(WIN_MESSAGE);
                break;
            }
        }
    }

//    public String isWin(int mistakes) {
//        if () {
//            return LOSE_MESSAGE;
//        }
//
//        return ;
//    }

    public static String getRandomWord() throws IOException {
        Random random = new Random();
        List<String> words = Files.readAllLines(Paths.get("src/wordsDB/words.txt"));

        return words.get(random.nextInt(words.size()));
    }
}

