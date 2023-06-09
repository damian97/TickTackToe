import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();

        game.startGame();


    }
}



class Game {


    String[][] gameTable;
    int x, y;
    int counter;
    int randomX, randomY;


    public Game() {
        counter = 0;
        gameTable = new String[3][3];
        randomX = 1;
        randomY = 1;
        for (int i = 0; i < gameTable.length; i++) {
            for (int j = 0; j < gameTable.length; j++) {
                gameTable[i][j] = " ";
            }
        }

    }


    public void startGame() {
        System.out.println("You start the game. Select a position to insert. 3x3");
        gameLogic();
    }

    public void getNumbers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Eneter first number: ");
        x = scanner.nextInt() - 1;
        System.out.println("Enter second number: ");
        y = scanner.nextInt() - 1;
        if (x >= 0 && x <= 2 && y >= 0 && y <= 2) {
            if (isX(x, y) || isO(x, y)) {
                System.out.println("There is x/o in this place.");
                getNumbers();
            } else {
                setX(x, y);
                counter++;
            }
        } else {
            System.out.println("Wrong number. Enter again.");
            getNumbers();
        }
    }

    public void computerRound() {

        System.out.println("Wait for the computer move");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (isO(randomX, randomY) || isX(randomX, randomY)) {
            randomPlace();

            if (isFull()) {
                break;
            }
        }


        setO(randomX, randomY);
        counter++;
    }

    public void randomPlace() {
        Random random = new Random();
        randomX = random.nextInt(3);
        randomY = random.nextInt(3);
    }

    public boolean isFull() {
        for (int i = 0; i < gameTable.length; i++) {
            for (int j = 0; j < gameTable.length; j++) {
                if (gameTable[i][j].equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isCross(String character) {

        int counterLeft = 0;
        int counterRight = 0;
        for (int i = 0, j = 2; i < gameTable.length; i++, j--) {

            if (gameTable[i][i].equals(character)) {
                counterLeft++;
            }
            if (gameTable[i][j].equals(character)) {
                counterRight++;
            }
        }

        return counterLeft == 3 || counterRight == 3;
    }



    public boolean isWin(String character) {

        int combinations = 0;
        int count = 0, count1 = 0;

        while (combinations < 8) {
            for (int i = 0; i < gameTable.length; i++) {
                for (int j = 0; j < gameTable.length; j++) {

                    if (gameTable[i][j].equals(character)) {
                        count++;
                    }
                    if (gameTable[j][i].equals(character)) {
                        count1++;
                    }

                    if (count >= 3 || count1 >= 3) {
                        return true;
                    }

                }
                count = 0;
                count1 = 0;

            }
            combinations++;
        }

        return isCross(character);
    }


    public void gameLogic() {

        while (counter < 9) {
            getNumbers();
            showTable();

            if (isWin("x")) {
                System.out.println();
                System.out.println("You win! Congratulation");
                break;
            } else if (counter >= 8) {
                System.out.println();
                System.out.println("Remis");
                break;
            }
            computerRound();
            showTable();

            if (isWin("o")) {
                System.out.println();
                System.out.println("You lost!");
                break;
            }
        }

        System.out.println("Thanks for game");


    }


    public void setX(int x, int y) {
        gameTable[x][y] = "x";
    }

    public void setO(int x, int y) {
        gameTable[x][y] = "o";
    }

    public boolean isX(int x, int y) {
        return gameTable[x][y].equals("x");
    }

    public boolean isO(int x, int y) {
        return gameTable[x][y].equals("o");
    }

    public void showTable() {

        System.out.println(gameTable[0][0] +  "|" + gameTable[0][1] + "|" + gameTable[0][2]);
        System.out.println("-" +  "+" + "-" + "+" + "-");
        System.out.println(gameTable[1][0] +  "|" + gameTable[1][1] + "|" + gameTable[1][2]);
        System.out.println("-" +  "+" + "-" + "+" + "-");
        System.out.println(gameTable[2][0] +  "|" + gameTable[2][1] + "|" + gameTable[2][2]);

    }



    public void testGame() {

        int counterTest = 1;
        int maxText = 8;
        boolean passed;
        int k = 0;
        int counterX = 0, counterO = 0;


            // x test
        while (counterTest <= maxText) {
            passed = false;


            if (counterTest >= 1 && counterTest <= 3) {

                for (int i = 0; i < 3; i++) {
                    setX(k, i);
                }

            } else if (counterTest >= 4 && counterTest <= 6) {
                for (int i = 0; i < 3; i++) {
                    setX(i, k);
                }
            } else if (counterTest == 7){
                for (int i = 0; i < 3; i++) {
                    setX(i, i);
                }
            } else {
                for (int i = 0, j = 2; i < 3; i++, j--) {
                    setX(i, j);
                }
            }

            if (k >= 2) {
                k = 0;
            } else {
                k++;
            }


            if (isWin("x")) {
                System.out.println();
                passed = true;
                counterX++;
            }

            showTable();



            for (int i = 0; i < gameTable.length; i++) {
                for (int j = 0; j < gameTable.length; j++) {
                    gameTable[i][j] = " ";              // clen table
                }
            }



            System.out.print("Test number " + counterTest + ": ");
            if (passed) {
                System.out.println("passed");
            } else {
                System.out.println("fail");
            }
            System.out.println();
            counterTest++;
        }


        counterTest = 1;
        k = 0;

            // o test
        while (counterTest <= maxText) {
            passed = false;


            if (counterTest >= 1 && counterTest <= 3) {

                for (int i = 0; i < 3; i++) {
                    setO(k, i);
                }

            } else if (counterTest >= 4 && counterTest <= 6) {
                for (int i = 0; i < 3; i++) {
                    setO(i, k);
                }
            } else if (counterTest == 7){
                for (int i = 0; i < 3; i++) {
                    setO(i, i);
                }
            } else {
                for (int i = 0, j = 2; i < 3; i++, j--) {
                    setO(i, j);
                }
            }

            if (k >= 2) {
                k = 0;
            } else {
                k++;
            }


            if (isWin("o")) {
                System.out.println();
                passed = true;
                counterO++;
            }

            showTable();



            for (int i = 0; i < gameTable.length; i++) {
                for (int j = 0; j < gameTable.length; j++) {
                    gameTable[i][j] = " ";              // clen table
                }
            }



            System.out.print("Test number " + counterTest + ": ");
            if (passed) {
                System.out.println("passed");
            } else {
                System.out.println("fail");
            }
            System.out.println();
            counterTest++;
        }




        if (counterX == 8) {
            System.out.println("Test x: passed");
        } else {
            System.out.println("Test x: failed");
        }

        if (counterO == 8) {
            System.out.println("Test o: passed");
        } else {
            System.out.println("Test o: failed");
        }

    }




}