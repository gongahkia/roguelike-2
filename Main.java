import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        System.out.println("~Roguelike 2~");
        Integer[] userStartPosition = {0,0};

        String input = readUserInput();
        while (input != "q"){
            System.out.println(input);
            System.out.println(userStartPosition);
            moveUser(input, userStartPosition);
            input = readUserInput();
        } 

    }

    public static String readUserInput(){
        Scanner sc = new Scanner(System.in);
        String userInput = sc.next();
        sc.close();
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

}