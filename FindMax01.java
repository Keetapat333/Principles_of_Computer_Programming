void main() {
    // กำหนดค่าตัวแปร
    int a = 1000;
    int b = 555;
    int c = 2555;

    // เช็คว่า a เป็นค่ามากที่สุด
    if ((a > b) && (a > c)) {
        IO.println("A = " + a);
    } else {
        // ถ้า a ไม่มากที่สุด เช็คว่า b เป็นค่ามากที่สุด
        if ((b > a) && (b > c)) {
            IO.println("B = " + b);
        } else {
            // ถ้า b ก็ไม่มากที่สุด พิมพ์ C
            IO.println("C = " + c);
        }
    }
}