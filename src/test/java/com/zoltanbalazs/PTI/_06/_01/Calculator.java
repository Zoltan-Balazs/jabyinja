package com.zoltanbalazs.PTI._06._01;

class Calculator {
    public static void main(String[] args) {
        try {
            if (args.length != 3) {
                throw new IllegalArgumentException();
            } else {
                double a = Double.parseDouble(args[0]);
                char m = args[1].charAt(0);
                double b = Double.parseDouble(args[2]);

                System.out.print("" + a + " " + m + " " + b + " = ");
                switch (m) {
                    case '+':
                        System.out.println(a + b);
                        break;
                    case '-':
                        System.out.println(a - b);
                        break;
                    case 'x': // Windows-on nem adható át paraméternek könnyen a *, most a szorzást jelölje az
                              // x
                        System.out.println(a * b);
                        break;
                    case '/':
                        if (b == 0) {
                            throw new ArithmeticException();
                        }
                        System.out.println(a / b);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error converting to double!\n" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid program arguments provided\n" + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Divison by zero not possible!\n" + e.getMessage());
        }
    }
}
