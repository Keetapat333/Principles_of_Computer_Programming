import java.util.Scanner;

public class ElectricityBillCalculator {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter electricity usage in kWh: ");
        double units = scanner.nextDouble();
        
        double billAmount = calculateElectricityBill(units);
        
        System.out.println("\nElectricity Bill Calculation:");
        System.out.println("Units consumed: " + units + " kWh");
        System.out.printf("Total amount: %.2f THB%n", billAmount);
        
        // Show breakdown
        showCalculationBreakdown(units);
        
        scanner.close();
    }
    
    public static double calculateElectricityBill(double units) {
        double totalAmount = 0.0;
        
        if (units <= 0) {
            return 0.0;
        }
        
        // Calculate based on tiers
        if (units <= 150) {
            // First tier: 0-150 units at 3.50 THB/unit
            totalAmount = units * 3.50;
        } else if (units <= 400) {
            // First tier: 0-150 units at 3.50 THB/unit
            totalAmount = 150 * 3.50;
            // Second tier: 151-400 units at 4.20 THB/unit
            totalAmount += (units - 150) * 4.20;
        } else {
            // First tier: 0-150 units at 3.50 THB/unit
            totalAmount = 150 * 3.50;
            // Second tier: 151-400 units at 4.20 THB/unit
            totalAmount += (400 - 150) * 4.20;
            // Third tier: 401+ units at 5.00 THB/unit
            totalAmount += (units - 400) * 5.00;
        }
        
        // Add service fee
        totalAmount += 50.0;
        
        return totalAmount;
    }
    
    public static void showCalculationBreakdown(double units) {
        System.out.println("\nCalculation Breakdown:");
        double totalAmount = 0.0;
        
        if (units <= 0) {
            System.out.println("No consumption, no charge.");
            return;
        }
        
        if (units <= 150) {
            System.out.printf("First tier (%.2f units @ 3.50 THB/unit): %.2f THB%n", 
                units, units * 3.50);
            totalAmount = units * 3.50;
        } else if (units <= 400) {
            System.out.printf("First tier (150 units @ 3.50 THB/unit): %.2f THB%n", 
                150 * 3.50);
            System.out.printf("Second tier (%.2f units @ 4.20 THB/unit): %.2f THB%n", 
                (units - 150), (units - 150) * 4.20);
            totalAmount = (150 * 3.50) + ((units - 150) * 4.20);
        } else {
            System.out.printf("First tier (150 units @ 3.50 THB/unit): %.2f THB%n", 
                150 * 3.50);
            System.out.printf("Second tier (250 units @ 4.20 THB/unit): %.2f THB%n", 
                (400 - 150) * 4.20);
            System.out.printf("Third tier (%.2f units @ 5.00 THB/unit): %.2f THB%n", 
                (units - 400), (units - 400) * 5.00);
            totalAmount = (150 * 3.50) + ((400 - 150) * 4.20) + ((units - 400) * 5.00);
        }
        
        System.out.printf("Service fee: 50.00 THB%n");
        System.out.printf("Total: %.2f THB%n", totalAmount + 50.0);
    }
}