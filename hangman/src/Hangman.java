public class Hangman {
    public void drawHangman(int pictureNumber) {
        switch (pictureNumber) {
            case 0 -> System.out.println("""
                    ___________              \s
                    |   \\    |
                    | \s
                    | \s
                    | \s
                    |
                    ~~~~~~~~~~~
                   \s""");
            case 1 -> System.out.println("""
                    ___________              \s
                    |   \\    |
                    |   ()
                    | \s
                    | \s
                    |
                    ~~~~~~~~~~~
                   \s""");
            case 2 -> System.out.println("""
                    ___________               \s
                    |   \\    |
                    |   ()
                    |   ||
                    | \s
                    |
                    ~~~~~~~~~~~
                   \s""");
            case 3 -> System.out.println("""
                    ___________               \s
                    |   \\    |
                    |   ()
                    |  /||
                    | \s
                    |
                    ~~~~~~~~~~~
                   \s""");
            case 4 -> System.out.println("""
                    ___________               \s
                    |   \\    |
                    |   ()
                    |  /||\\
                    | \s
                    |
                    ~~~~~~~~~~~
                   \s""");
            case 5 -> System.out.println("""
                    ___________               \s
                    |   \\    |
                    |   ()
                    |  /||\\
                    |  /  \s
                    |
                    ~~~~~~~~~~~
                   \s""");
            case 6 -> System.out.println("""
                    ___________               \s
                    |   \\    |
                    |   ()
                    |  /||\\
                    |  /  \\
                    |
                    ~~~~~~~~~~~
                   \s""");
            default -> System.out.println("Wrong number");
        }
    }
}
