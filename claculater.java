public class claculater {
   public static void main(String[] args) {
    System.out.println("This is a simple calculator program.");
    // You can add more functionality here later

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
