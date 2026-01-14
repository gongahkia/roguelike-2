import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        System.out.println("~Roguelike 2~");
        System.out.println("Use WASD to move, Q to quit.");
        Integer[] userStartPosition = {0,0};
        Integer minX = 0;
        Integer maxX = 10;
        Integer minY = 0;
        Integer maxY = 10;

        String input = readUserInput();

        while (!input.equals("q")){

            System.out.println(input);
            System.out.println(userStartPosition[0] + ", " + userStartPosition[1]);
            moveUser(input, userStartPosition, minX, maxX, minY, maxY);
            input = readUserInput();
            drawScreen(createScreen(userStartPosition, minX, maxX, minY, maxY));

        } 

    }

    public static String readUserInput(){
        Scanner sc = new Scanner(System.in);
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
        String rowBuffer = "";
        String[] screenBuffer = new String[10];
        for (int y=minY; y<maxY; y++){
            for (int x=minX; x<maxX; x++){
                if (x == userX && y == userY){
                    rowBuffer += "P";
                } else {
                    rowBuffer += ".";
                }
            }
            screenBuffer[y] = rowBuffer;
        }
        return screenBuffer;
    }

    public static void drawScreen(String[] screenBuffer){
        for (int i=0; i<screenBuffer.length; i++){
            System.out.println(screenBuffer[i]);
        }
    }

    public static boolean checkBounds(Integer x, Integer y, Integer minX, Integer maxX, Integer minY, Integer maxY){
        return (x >= minX && x <= maxX && y >= minY && y <= maxY);
    }

}