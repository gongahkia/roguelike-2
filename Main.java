import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        System.out.println("~Roguelike 2~");
        Integer[] userStartPosition = {0,0};

        String input = readUserInput();
        while (!input.equals('q')){
            System.out.println(input);
            System.out.println(userStartPosition);
            moveUser(input, userStartPosition);
            input = readUserInput();
            drawScreen(userStartPosition);
        } 

    }

    public static String readUserInput(){
        Scanner sc = new Scanner(System.in);
        String userInput = sc.next();
        return userInput;
    }

    public static Integer[] moveUser(String input, Integer[] userPosition){
        switch (input){
            case "w": // up
                userPosition[1] += 1;
                break;
            case "a": // left
                userPosition[0] -= 1;
                break;
            case "s": // down
                userPosition[1] -= 1;
                break;
            case "d": // right
                userPosition[0] += 1;
                break;
            default:
                System.out.println("Invalid input: " + input);
                break;
        }
        return userPosition;     
    }

    public static String[] drawScreen(Integer[] userStartPosition){
        Integer userX = userStartPosition[0];
        Integer userY = userStartPosition[1];
        String rowBuffer = "";
        String[] screenBuffer = new String[10];
        for (int y=0; y<10; y++){
            for (int x=0; x<10; x++){
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

}