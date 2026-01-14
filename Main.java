import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        Integer[] userStartPosition = {1,1};
        Integer minX = 1;
        Integer maxX = 11;
        Integer minY = 1;
        Integer maxY = 11;
        Integer spaceOffset = 10;

        Scanner sc = new Scanner(System.in);
        String input = readUserInput(sc);

        while (!input.equals("q")){

            moveUser(input, userStartPosition, minX, maxX, minY, maxY);
            input = readUserInput(sc);
            drawScreen(createScreen(userStartPosition, minX, maxX, minY, maxY), spaceOffset);
            drawSpace(spaceOffset); System.out.println("    ~Roguelike 2~");
            // System.out.println(input);
            drawSpace(spaceOffset); System.out.println(" User position: " + userStartPosition[0] + ", " + userStartPosition[1]);

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
                    rowBuffer += "|P";
                } else {
                    rowBuffer += "|.";
                }
            }
            screenBuffer[y-minY] = rowBuffer + "|";
        }
        return screenBuffer;
    }

    public static void drawScreen(String[] screenBuffer, Integer offset){
        for (int i=screenBuffer.length-1; i>=0; i--){
            drawSpace(offset); System.out.println("---------------------");
            drawSpace(offset); System.out.println(screenBuffer[i]);
        }
        drawSpace(offset); System.out.println("---------------------");
    }

    public static boolean checkBounds(Integer x, Integer y, Integer minX, Integer maxX, Integer minY, Integer maxY){
        return (x >= minX && x < maxX && y >= minY && y < maxY);
    }

    public static void drawSpace(Integer offset){
        for (int i=0; i<offset; i++){
            System.out.print(" ");
        }
    }

}