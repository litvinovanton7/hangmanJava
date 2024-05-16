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
        StringBuilder hiddenRandomWord = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        hiddenRandomWord.append("*".repeat(RANDOM_WORD.length()));
        List<String> wrongKeyword = new ArrayList<>();
        List<String> usedKeywords = new ArrayList<>();
        int mistakes = 0;
        boolean playAgain;

        System.out.println(RANDOM_WORD);

        do {
            while (true) {
                System.out.print("Input a keyword: ");
                String keyword = sc.next();

                if (in(wrongKeyword, keyword)) {
                    System.out.println(WRONG_LETTER_ALREADY_USED_MESSAGE);
                }


                if (correctKeyword(keyword, RANDOM_WORD)) {
                    changeCorrectLetter(hiddenRandomWord, keyword);
                    usedKeywords.add(keyword);
                    System.out.println(hiddenRandomWord);
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

                if (isWin(hiddenRandomWord)) {
                    System.out.println(GAME_WIN_MESSAGE);
                    break;
                }
                if (isLose(mistakes)) {
                    System.out.println(GAME_LOST_MESSAGE);
                    break;
                }
            }
            System.out.println("Do you want to play again?[yes/no]");

            String userAnswer = sc.next().toLowerCase();

            if (userAnswer.equals("no")) {
                System.out.println("Thank you for a game. See you soon");
                break;
            }

            playAgain = isPlayAgain(userAnswer);
        } while (playAgain);
    }

    public static StringBuilder changeCorrectLetter(StringBuilder hiddenWord, String userKeyword) {
        for (int i = 0; i < RANDOM_WORD.length(); i++) {
            if (RANDOM_WORD.charAt(i) == userKeyword.charAt(0)) {
                hiddenWord.replace(i, i + 1, userKeyword);
            }
        }

        return hiddenWord;
    }

    public static String getRandomWord() throws IOException {
        Random random = new Random();
        List<String> words = Files.readAllLines(Paths.get("hangman/src/words.txt"));

        if (words.isEmpty()) {
            throw new IOException("The words file is empty");
        }

        return words.get(random.nextInt(words.size()));
    }

    public static boolean isPlayAgain(String answer) {
        return answer.equals("yes");
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

    public static boolean isWin(StringBuilder guessedWord) {
        return guessedWord.toString().equals(RANDOM_WORD);
    }

    public static boolean isLose(int userFails) {
        return userFails == DEAD_STATE;
    }

    public static boolean isKeyword(String keyword) {
        return Character.isLetter(keyword.charAt(0)) && keyword.length() == 1;
    }
}

