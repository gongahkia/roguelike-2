import java.util.Scanner;
import java.util.HashMap;

public class Main {

    public static void main(String[] args){

        Integer[] userStartPosition = {1,1};
        Integer[] footSoldierPosition = {5,5};
        HashMap<String, Integer> userStats = new HashMap<String, Integer>() {{
            put("moves", 0);
            put("speed", 1);
            put("health", 2);
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

        while (!input.equals("q")){ // game loop

            moveUser(input, userStartPosition, userStats.get("speed"), minX, maxX, minY, maxY); // move user
            footSoldierPosition = moveFootSoldier(footSoldierPosition, 1, minX, maxX, minY, maxY, userStartPosition); 

            if (footSoldierPosition[0].equals(userStartPosition[0]) && footSoldierPosition[1].equals(userStartPosition[1])){
                System.out.println("      The foot soldier has caught you!");
                userStats.put("health", userStats.get("health") - 1);
                break;
            }

            drawScreen(createScreen(userStartPosition, minX, maxX, minY, maxY, footSoldierPosition), spaceOffset);
            drawSpace(spaceOffset); System.out.println("    ~Roguelike 2~");
            userStats.put("moves", userStats.get("moves") + 1);
            drawSpace(spaceOffset); System.out.println("    Moves: " + userStats.get("moves") + "/" + userStats.get("maxMoves"));
            drawSpace(spaceOffset); System.out.println("      Health: " + userStats.get("health"));
            // System.out.println(input);
            drawSpace(spaceOffset); System.out.println(" User position: " + userStartPosition[0] + ", " + userStartPosition[1]);
            input = readUserInput(sc);

            if (userStats.get("moves") >= userStats.get("maxMoves")){ // run out of moves
                drawSpace(spaceOffset); System.out.println("  You have run out of moves!");
                input = "q";
            }

            if (userStats.get("health") <= 0){ // run out of health
                drawSpace(spaceOffset); System.out.println("  You have run out of health!");
                input = "q";
            }

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

    public static String[] createScreen(Integer[] userStartPosition, Integer minX, Integer maxX, Integer minY, Integer maxY, Integer[] footSoldierPosition){
        Integer userX = userStartPosition[0];
        Integer userY = userStartPosition[1];
        String[] screenBuffer = new String[10];

        for (int y=minY; y<maxY; y++){

            String rowBuffer = "";
            for (int x=minX; x<maxX; x++){

                if (x == userX && y == userY){
                    rowBuffer += "|P";
                } else if (x == footSoldierPosition[0] && y == footSoldierPosition[1]) {
                    rowBuffer += "|F";
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

    public static Integer[] moveFootSoldier(Integer[] footSoldierPosition, Integer speed, Integer minX, Integer maxX, Integer minY, Integer maxY, Integer[] userPosition){

        boolean hasLOS = true;
        int x0 = footSoldierPosition[0], y0 = footSoldierPosition[1];
        int x1 = userPosition[0], y1 = userPosition[1];
        int dx = Math.abs(x1 - x0), dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        int cx = x0, cy = y0;
        while (!(cx == x1 && cy == y1)) {
            int e2 = 2 * err;
            if (e2 > -dy) { err -= dy; cx += sx; }
            if (e2 < dx) { err += dx; cy += sy; }
        }
        if (!hasLOS) return footSoldierPosition;

        class Node {
            Integer[] pos;
            int g, h;
            Node parent;
            Node(Integer[] pos, int g, int h, Node parent) {
                this.pos = pos;
                this.g = g;
                this.h = h;
                this.parent = parent;
            }
            int f() { return g + h; }
        }

        java.util.PriorityQueue<Node> open = new java.util.PriorityQueue<>(
            (a, b) -> Integer.compare(a.f(), b.f())
        );
        java.util.HashSet<String> closed = new java.util.HashSet<>();

        Integer[] start = {footSoldierPosition[0], footSoldierPosition[1]};
        Integer[] goal = {userPosition[0], userPosition[1]};

        Node startNode = new Node(start, 0, Math.abs(start[0]-goal[0]) + Math.abs(start[1]-goal[1]), null);
        open.add(startNode);

        Node found = null;
        int maxSearch = 100; 
        while (!open.isEmpty() && maxSearch-- > 0) {
            Node current = open.poll();
            String key = current.pos[0] + "," + current.pos[1];
            if (closed.contains(key)) continue;
            closed.add(key);
            if (current.pos[0].equals(goal[0]) && current.pos[1].equals(goal[1])) {
                found = current;
                break;
            }
            int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
            for (int[] d : dirs) {
                int nx = current.pos[0] + d[0];
                int ny = current.pos[1] + d[1];
                if (nx >= minX && nx < maxX && ny >= minY && ny < maxY) {
                    Integer[] npos = {nx, ny};
                    int ng = current.g + 1;
                    int nh = Math.abs(nx-goal[0]) + Math.abs(ny-goal[1]);
                    open.add(new Node(npos, ng, nh, current));
                }
            }
        }

        java.util.List<Integer[]> path = new java.util.ArrayList<>();
        if (found != null) {
            Node cur = found;
            while (cur != null) {
                path.add(0, cur.pos);
                cur = cur.parent;
            }
        }

        Integer[] newPos = {footSoldierPosition[0], footSoldierPosition[1]};
        if (path.size() > 1) {
            int steps = Math.min(speed, path.size()-1);
            newPos = path.get(steps);
        }
        return newPos;
    }

}
