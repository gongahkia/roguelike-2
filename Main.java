import java.util.Scanner;
import java.util.HashMap;

public class Main {

    public static void main(String[] args){

        Integer[] userStartPosition = {1,1};
        Integer[] userAttackBox = new Integer[]{0,0};
        Integer[] footSoldierPosition = {5,5};
        HashMap<String, Integer> userStats = new HashMap<String, Integer>() {{
            put("moves", 0);
            put("speed", 1);
            put("health", 1);
            put("attack", 1);
            put("maxMoves", 100);
        }};
        HashMap<String, Integer> userArmour = new HashMap<String, Integer>() {{
            put("bronzeHelmet", 0);
            put("bronzeChestplate", 0);
            put("bronzeLeggings", 0);
            put("bronzeBoots", 0);
            put("ironHelmet", 0);
            put("ironChestplate", 0);
            put("ironLeggings", 0);
            put("ironBoots", 0);
            put("goldHelmet", 0);
            put("goldChestplate", 0);
            put("goldLeggings", 0);
            put("goldBoots", 0);
            put("diamondHelmet", 0);
            put("diamondChestplate", 0);            
            put("diamondLeggings", 0);
            put("diamondBoots", 0);
        }};
        HashMap<String, Integer> userInventory = new HashMap<String, Integer>() {{
            put("sword", 0);
            put("shield", 0);
            put("bread", 0);
            put("kebab", 0);
            put("bow", 0);
            put("arrow", 0);
            put("healthPotion", 0);
            put("speedPotion", 0);
            put("movePotion", 0);
        }};
        
        Integer minX = 1;
        Integer maxX = 11;
        Integer minY = 1;
        Integer maxY = 11;
        Integer spaceOffset = 10;

        Scanner sc = new Scanner(System.in);
        String input = readUserInput(sc);

        int turnCounter = 0;
        while (!input.equals("q")) { // game loop

            Integer[][] userPositions = moveUser(input, userStartPosition, userStats.get("speed"), minX, maxX, minY, maxY, userAttackBox); // move user
            Integer[] newUserPosition = userPositions[0];
            Integer[] newUserAttackBox = userPositions[1];
            userStartPosition = newUserPosition;

            if (turnCounter % 2 == 0) {
                footSoldierPosition = moveFootSoldier(footSoldierPosition, 1, minX, maxX, minY, maxY, userStartPosition);
            }

            if (footSoldierPosition[0].equals(userStartPosition[0]) && footSoldierPosition[1].equals(userStartPosition[1])) {
                System.out.println("      The foot soldier has caught you!");
                userStats.put("health", userStats.get("health") - 1);
            }

            if (newUserAttackBox[0].equals(footSoldierPosition[0]) && newUserAttackBox[1].equals(footSoldierPosition[1])) {
                System.out.println("      You have killed the foot soldier!");
                footSoldierPosition = new Integer[]{-1, -1}; // remove foot soldier
            }

            drawScreen(createScreen(newUserPosition, minX, maxX, minY, maxY, footSoldierPosition, newUserAttackBox), spaceOffset);
            drawSpace(spaceOffset); System.out.println("    ~Roguelike 2~");
            userStats.put("moves", userStats.get("moves") + 1);
            drawSpace(spaceOffset); System.out.println("    Moves: " + userStats.get("moves") + "/" + userStats.get("maxMoves"));
            drawSpace(spaceOffset); System.out.println("      Health: " + userStats.get("health"));
            // System.out.println(input);
            drawSpace(spaceOffset); System.out.println(" User position: " + newUserPosition[0] + ", " + newUserPosition[1]);
            input = readUserInput(sc);

            if (userStats.get("moves") >= userStats.get("maxMoves")) { // run out of moves
                System.out.println("        You have run out of moves!");
                input = "q";
            }

            if (userStats.get("health") <= 0) { // run out of health
                System.out.println("        You have run out of health!");
                input = "q";
            }

            turnCounter++;

        }

    }

    public static String readUserInput(Scanner sc){
        String userInput = sc.nextLine();
        return userInput;
    }

    public static Integer[][] moveUser(String input, Integer[] userPosition, Integer userSpeed, Integer minX, Integer maxX, Integer minY, Integer maxY, Integer[] userAttackBox){
        switch (input) {
            case "w": // up
                if (checkBounds(userPosition[0], userPosition[1] + userSpeed, minX, maxX, minY, maxY)) {
                    userPosition[1] += userSpeed;
                }
                break;
            case "a": // left
                if (checkBounds(userPosition[0] - userSpeed, userPosition[1], minX, maxX, minY, maxY)) {
                    userPosition[0] -= userSpeed;
                }
                break;
            case "s": // down
                if (checkBounds(userPosition[0], userPosition[1] - userSpeed, minX, maxX, minY, maxY)) {
                    userPosition[1] -= userSpeed;
                }
                break;
            case "d": // right
                if (checkBounds(userPosition[0] + userSpeed, userPosition[1], minX, maxX, minY, maxY)) {
                    userPosition[0] += userSpeed;
                }
                break;
            case "\u001B[A": // arrow up
                userAttackBox[0] = userPosition[0];
                userAttackBox[1] = userPosition[1] + 1;
                break;
            case "\u001B[B": // arrow down
                userAttackBox[0] = userPosition[0];
                userAttackBox[1] = userPosition[1] - 1; 
                break;
            case "\u001B[C": // arrow right
                userAttackBox[0] = userPosition[0] + 1;
                userAttackBox[1] = userPosition[1]; 
                break;
            case "\u001B[D": // arrow left
                userAttackBox[0] = userPosition[0] - 1;
                userAttackBox[1] = userPosition[1]; 
                break;
            default:
                System.out.println("Invalid input: " + input);
                break;
        }
        return new Integer[][]{userPosition, userAttackBox};
    }

    public static String[] createScreen(Integer[] newUserPosition, Integer minX, Integer maxX, Integer minY, Integer maxY, Integer[] footSoldierPosition, Integer[] userAttackBox){
        Integer userX = newUserPosition[0];
        Integer userY = newUserPosition[1];
        String[] screenBuffer = new String[10];

        for (int y = minY; y < maxY; y++) {
            String rowBuffer = "";
            for (int x = minX; x < maxX; x++) {
                if (x == userX && y == userY) {
                    rowBuffer += "|P";
                } else if (x == footSoldierPosition[0] && y == footSoldierPosition[1]) {
                    rowBuffer += "|F";
                } else if (x == userAttackBox[0] && y == userAttackBox[1]) {
                    rowBuffer += "|*";
                } else {
                    rowBuffer += "|.";
                }
            }
            screenBuffer[y - minY] = rowBuffer + "|";
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

    public static Integer[] moveFootSoldier(Integer[] footSoldierPosition, Integer speed, Integer minX, Integer maxX, Integer minY, Integer maxY, Integer[] userPosition){ // movement purely based on absolute distance from user

        Integer[] newPos = {footSoldierPosition[0], footSoldierPosition[1]};

        for (int i = 0; i < speed; i++) {
            int dx = userPosition[0] - newPos[0];
            int dy = userPosition[1] - newPos[1];

            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    newPos[0]++;
                } else {
                    newPos[0]--;
                }
            } else {
                if (dy > 0) {
                    newPos[1]++;
                } else {
                    newPos[1]--;
                }
            }
        }

        if (checkBounds(newPos[0], newPos[1], minX, maxX, minY, maxY)) {
            return newPos;
        } else {
            return footSoldierPosition;
        }
    }

}
