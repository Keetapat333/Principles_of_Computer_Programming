import java.util.Scanner;

/**
 * Shipping Cost Calculator System
 * ระบบคำนวณค่าจัดส่งสินค้า
 */
public class ShippingCalculator {

    // Constants for Bangkok & surrounding areas (กรุงเทพฯ และปริมณฑล)
    private static final double BANGKOK_RATE_0_1KG = 40;
    private static final double BANGKOK_RATE_1_3KG = 60;
    private static final double BANGKOK_RATE_3_5KG = 80;
    private static final double BANGKOK_RATE_OVER_5KG = 100;

    // Constants for other provinces (ต่างจังหวัด)
    private static final double PROVINCE_RATE_0_1KG = 60;
    private static final double PROVINCE_RATE_1_3KG = 90;
    private static final double PROVINCE_RATE_3_5KG = 120;
    private static final double PROVINCE_RATE_OVER_5KG = 150;

    // Additional fees and discounts
    private static final double EXPRESS_FEE = 30;
    private static final double VIP_DISCOUNT = 0.20; // 20%

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("    ระบบคำนวณค่าจัดส่งสินค้า");
        System.out.println("    Shipping Cost Calculator");
        System.out.println("==========================================");

        // Input weight
        System.out.print("\nกรุณาระบุน้ำหนักสินค้า (กิโลกรัม): ");
        double weight = scanner.nextDouble();

        // Validate weight
        if (weight <= 0) {
            System.out.println("ข้อผิดพลาด: น้ำหนักต้องมากกว่า 0 กิโลกรัม");
            scanner.close();
            return;
        }

        // Input destination
        System.out.println("\nเลือกปลายทาง:");
        System.out.println("1. กรุงเทพฯ และปริมณฑล");
        System.out.println("2. ต่างจังหวัด");
        System.out.print("กรุณาเลือก (1 หรือ 2): ");
        int destinationChoice = scanner.nextInt();

        boolean isBangkok = (destinationChoice == 1);

        // Input express service
        System.out.print("\nต้องการบริการด่วน (Express)? (1 = ใช่, 0 = ไม่): ");
        int expressChoice = scanner.nextInt();
        boolean isExpress = (expressChoice == 1);

        // Input VIP membership
        System.out.print("เป็นสมาชิก VIP หรือไม่? (1 = ใช่, 0 = ไม่): ");
        int vipChoice = scanner.nextInt();
        boolean isVIP = (vipChoice == 1);

        // Calculate shipping cost
        double shippingCost = calculateShippingCost(weight, isBangkok, isExpress, isVIP);

        // Display result
        displayResult(weight, isBangkok, isExpress, isVIP, shippingCost);

        scanner.close();
    }

    /**
     * Calculate the base shipping cost based on weight and destination
     */
    private static double getBaseCost(double weight, boolean isBangkok) {
        if (isBangkok) {
            // Bangkok & surrounding areas rates
            if (weight <= 1) {
                return BANGKOK_RATE_0_1KG;
            } else if (weight <= 3) {
                return BANGKOK_RATE_1_3KG;
            } else if (weight <= 5) {
                return BANGKOK_RATE_3_5KG;
            } else {
                return BANGKOK_RATE_OVER_5KG;
            }
        } else {
            // Other provinces rates
            if (weight <= 1) {
                return PROVINCE_RATE_0_1KG;
            } else if (weight <= 3) {
                return PROVINCE_RATE_1_3KG;
            } else if (weight <= 5) {
                return PROVINCE_RATE_3_5KG;
            } else {
                return PROVINCE_RATE_OVER_5KG;
            }
        }
    }

    /**
     * Calculate total shipping cost including express fee and VIP discount
     */
    public static double calculateShippingCost(double weight, boolean isBangkok, 
                                                boolean isExpress, boolean isVIP) {
        // Get base cost
        double totalCost = getBaseCost(weight, isBangkok);

        // Add express fee if selected
        if (isExpress) {
            totalCost += EXPRESS_FEE;
        }

        // Apply VIP discount if applicable
        if (isVIP) {
            totalCost = totalCost * (1 - VIP_DISCOUNT);
        }

        return totalCost;
    }

    /**
     * Display the calculation result
     */
    private static void displayResult(double weight, boolean isBangkok, 
                                       boolean isExpress, boolean isVIP, double totalCost) {
        System.out.println("\n==========================================");
        System.out.println("           รายละเอียดค่าจัดส่ง");
        System.out.println("==========================================");
        System.out.printf("น้ำหนักสินค้า: %.2f กิโลกรัม%n", weight);
        System.out.println("ปลายทาง: " + (isBangkok ? "กรุงเทพฯ และปริมณฑล" : "ต่างจังหวัด"));
        System.out.println("บริการด่วน: " + (isExpress ? "ใช่ (+30 บาท)" : "ไม่"));
        System.out.println("สมาชิก VIP: " + (isVIP ? "ใช่ (ลด 20%)" : "ไม่"));
        System.out.println("------------------------------------------");
        System.out.printf("ค่าจัดส่งรวม: %.2f บาท%n", totalCost);
        System.out.println("==========================================");
    }
}
