import java.util.Scanner;

public class claculater {
   public static void main(String[] args) {
    System.out.println("This is a simple calculator program.");
    Scanner scn=new Scanner(System.in);
    System.out.println("Enter first Number");
    int a=scn.nextInt();
    System.out.println("Enter second Number");
    int b=scn.nextInt();
    System.out.println("Enter the operation");
    String inp=scn.next();
    if(inp.equals("add")){
        add(a,b);
    }
    else if(inp.equals("multiply")){
        multiply(a,b);
    }
    else if(inp.equals("subtract")){
        subtract(a,b);
    }
    else if(inp.equals("divide")){
        divide(a,b);
    }
    else{
        System.out.println("bhai shi operation likh");
    }

    

   } 
   public static int add(int a, int b) {
       return a + b;
   }
    public static int subtract(int a, int b) {
         return a - b;
    }
    public static int multiply(int a, int b) {
            return a * b;
    }
    public static int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return a / b;
    }
}
