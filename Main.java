import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        Integer[] userStartPosition = {0,0};
        Integer minX = 0;
        Integer maxX = 10;
        Integer minY = 0;
        Integer maxY = 10;

        Scanner sc = new Scanner(System.in);
        String input = readUserInput(sc);

        while (!input.equals("q")){

            moveUser(input, userStartPosition, minX, maxX, minY, maxY);
            input = readUserInput(sc);
            drawScreen(createScreen(userStartPosition, minX, maxX, minY, maxY));
            System.out.println("~Roguelike 2~");
            System.out.println("Use WASD to move, Q to quit.");
            // System.out.println(input);
            System.out.println("User position: " + userStartPosition[0] + ", " + userStartPosition[1]);

        } 

    }

    public static String readUserInput(Scanner sc){
        String userInput = sc.next();
        return userInput;
    }

    public static Integer[] moveUser(String input, Integer[] userPosition, Integer minX, Integer maxX, Integer minY, Integer maxY){
        switch (input){
            case "w": // up
                if (checkBounds(userPosition[0], userPosition[1] + 1, minX, maxX, minY, maxY)){
                    userPosition[1] += 1;
                }
                break;
            case "a": // left
                if (checkBounds(userPosition[0] - 1, userPosition[1], minX, maxX, minY, maxY)){
                    userPosition[0] -= 1;
                }
                break;
            case "s": // down
                if (checkBounds(userPosition[0], userPosition[1] - 1, minX, maxX, minY, maxY)){
                    userPosition[1] -= 1;
                }
                break;
            case "d": // right
                if (checkBounds(userPosition[0] + 1, userPosition[1], minX, maxX, minY, maxY)){
                    userPosition[0] += 1;
                }
                break;
            default:
                System.out.println("Invalid input: " + input);
                break;
        }
        return userPosition;     
    }

    public static String[] createScreen(Integer[] userStartPosition, Integer minX, Integer maxX, Integer minY, Integer maxY){
        Integer userX = userStartPosition[0];
        Integer userY = userStartPosition[1];
        String[] screenBuffer = new String[10];
        for (int y=minY; y<maxY; y++){
            String rowBuffer = "";
            for (int x=minX; x<maxX; x++){
                if (x == userX && y == userY){
                    rowBuffer += "P";
                } else {
                    rowBuffer += ".";
                }
            }
            screenBuffer[y-minY] = rowBuffer;
        }
        return screenBuffer;
    }

    public static void drawScreen(String[] screenBuffer){
        for (int i=screenBuffer.length-1; i>=0; i--){
            System.out.println(screenBuffer[i]);
        }
    }

    public static boolean checkBounds(Integer x, Integer y, Integer minX, Integer maxX, Integer minY, Integer maxY){
        return (x >= minX && x < maxX && y >= minY && y < maxY);
    }

}