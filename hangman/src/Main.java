import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static final String GAME_WIN_MESSAGE = "Congratulation! You won this game";
    private static final String GAME_LOST_MESSAGE = "Sorry but you lost this game";
    private static final String WRONG_KEYWORD_MESSAGE = "Please input a correct letter[a..z]";
    private static final String WRONG_LETTER_ALREADY_USED_MESSAGE = "You already used this letter, and it is the wrong letter.";
    private static final String CORRECT_LETTER_ALREADY_USED_MESSAGE = "You already used this letter, and it is the correct letter.";
    private static final int DEAD_STATE = 7;
    private static final Hangman HANGMAN = new Hangman();
    private static final String RANDOM_WORD;

    static {
        try {
            RANDOM_WORD = getRandomWord().toLowerCase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        gameLoop();
    }

    public static void gameLoop() {
        StringBuilder hidden = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        hidden.append("*".repeat(RANDOM_WORD.length()));
        List<String> wrongKeyword = new ArrayList<>();
        List<String> usedKeywords = new ArrayList<>();
        int mistakes = 0;

        System.out.println(RANDOM_WORD);

        while (true) {
            System.out.print("Input a keyword: ");
            String keyword = sc.next();

            if (in(wrongKeyword, keyword)) {
                System.out.println(WRONG_LETTER_ALREADY_USED_MESSAGE);
            }


            if (correctKeyword(keyword, RANDOM_WORD)) {
                for (int i = 0; i < RANDOM_WORD.length(); i++) {
                    if (RANDOM_WORD.charAt(i) == keyword.charAt(0)) {
                        hidden.replace(i, i + 1, keyword);
                    }
                }
                if (in(usedKeywords, keyword)) {
                    System.out.println(CORRECT_LETTER_ALREADY_USED_MESSAGE);
                }
                usedKeywords.add(keyword);
                System.out.println(hidden);
            } else {
                if (in(wrongKeyword, keyword)) {
                    continue;
                }

                if (!isKeyword(keyword)) {
                    System.out.println(WRONG_KEYWORD_MESSAGE);
                    continue;
                }
                wrongKeyword.add(keyword);
                HANGMAN.drawHangman(mistakes);
                System.out.println(wrongKeyword);
                mistakes++;
            }

            if (mistakes == DEAD_STATE) {
                System.out.println(GAME_LOST_MESSAGE);
                break;
            }

            if (isWin(hidden)) {
                System.out.println(GAME_WIN_MESSAGE);
                break;
            }
        }
    }

    public static String getRandomWord() throws IOException {
        Random random = new Random();
        List<String> words = Files.readAllLines(Paths.get("hangman/src/words.txt"));

        if (words.isEmpty()) {
            throw new IOException("The words file is empty");
        }

        return words.get(random.nextInt(words.size()));
    }

    public static boolean correctKeyword(String keyword, String word) {
        return isKeyword(keyword) && in(word, keyword);
    }

    public static boolean in(String word, String keyword) {
        return word.contains(keyword);
    }

    public static boolean in(List<String> list, String keyword) {
        return list.contains(keyword);
    }

    public static boolean isWin(StringBuilder word) {
        return word.toString().equals(RANDOM_WORD);
    }

    public static boolean isKeyword(String keyword) {
        return Character.isLetter(keyword.charAt(0)) && keyword.length() == 1;
    }
}

