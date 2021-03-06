package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class MoneyCalculator {
    double amount;
    double exchangerate;
    String currencyFrom;
    String currencyTo;
    
    public static void main(String[] args) throws IOException {
        MoneyCalculator moneycalculator = new MoneyCalculator();
        moneycalculator.control();
    }

    private void control() throws IOException {
        input();
        process();
        output();
    }

    private void input() {
        System.out.println("Introduce una cantidad: ");
        Scanner scanner = new Scanner(System.in);
        amount = scanner.nextDouble();
        
        System.out.println("Introduce una divisa inicial: ");
        currencyFrom = scanner.next().toUpperCase();
        
        System.out.println("Introduce una divisa final: ");
        currencyTo = scanner.next().toUpperCase();
    }

    private void process() throws IOException {
        exchangerate = getExchangeRate(currencyFrom,currencyTo);
    }

    private void output() {
        System.out.println(amount + " " + currencyFrom + " equivalen a " +
                amount*exchangerate + " " + currencyTo);
    }

    private static double getExchangeRate(String from, String to) throws IOException {
        URL url = 
            new URL("http://free.currencyconverterapi.com/api/v5/convert?q=" +
                    from + "_" + to + "&compact=y");
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine();
            String line1 = line.substring(line.indexOf(to)+12, line.indexOf("}"));
            return Double.parseDouble(line1);
        }
    }
}