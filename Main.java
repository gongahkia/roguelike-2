import java.util.Scanner;
import java.util.HashMap;

public class Main {

    public static void main(String[] args){

        Integer[] userStartPosition = {1,1};
        Integer userSpeed = 1;
        Integer userHealth = 1;
        HashMap<String, Integer> userArmour = new HashMap<String, Integer>() {{
            put("helmet", 0);
            put("chestplate", 0);
            put("leggings", 0);
            put("boots", 0);
        }};
        HashMap<String, Integer> userInventory = new HashMap<String, Integer>() {{
            put("sword", 0);
            put("shield", 0);
            put("healthPotion", 0);
            put("speedPotion", 0);
            put("bread", 0);
        }};
        
        Integer minX = 1;
        Integer maxX = 11;
        Integer minY = 1;
        Integer maxY = 11;
        Integer spaceOffset = 10;
        Integer moveCount = 0; // can also be used to record clocktick

        Scanner sc = new Scanner(System.in);
        String input = readUserInput(sc);

        while (!input.equals("q")){

            moveUser(input, userStartPosition, userSpeed,  minX, maxX, minY, maxY);
            input = readUserInput(sc);
            drawScreen(createScreen(userStartPosition, minX, maxX, minY, maxY), spaceOffset);
            drawSpace(spaceOffset); System.out.println("    ~Roguelike 2~");
            moveCount++;
            drawSpace(spaceOffset); System.out.println("       Moves: " + moveCount);
            drawSpace(spaceOffset); System.out.println("      Health: " + userHealth);
            // System.out.println(input);
            drawSpace(spaceOffset); System.out.println(" User position: " + userStartPosition[0] + ", " + userStartPosition[1]);

        } 

    }

    public static String readUserInput(Scanner sc){
        String userInput = sc.next();
        return userInput;
    }

    public static Integer[] moveUser(String input, Integer[] userPosition, Integer userSpeed, Integer minX, Integer maxX, Integer minY, Integer maxY){
        switch (input){
            case "w": // up
                if (checkBounds(userPosition[0], userPosition[1] + userSpeed, minX, maxX, minY, maxY)){
                    userPosition[1] += userSpeed;
                }
                break;
            case "a": // left
                if (checkBounds(userPosition[0] - userSpeed, userPosition[1], minX, maxX, minY, maxY)){
                    userPosition[0] -= userSpeed;
                }
                break;
            case "s": // down
                if (checkBounds(userPosition[0], userPosition[1] - userSpeed, minX, maxX, minY, maxY)){
                    userPosition[1] -= userSpeed;
                }
                break;
            case "d": // right
                if (checkBounds(userPosition[0] + userSpeed, userPosition[1], minX, maxX, minY, maxY)){
                    userPosition[0] += userSpeed;
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
