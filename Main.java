import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        System.out.println("~Roguelike 2~");
        String input = readUserInput();
        System.out.println(input);
    }

    public static String readUserInput(){
        Scanner sc = new Scanner(System.in);
        String userInput = sc.next();
        sc.close();
        return userInput;
    }

}