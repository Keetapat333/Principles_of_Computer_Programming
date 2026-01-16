# โปรแกรมคำนวณค่าไฟฟ้า / Electricity Bill Calculator

## ภาษาไทย

โปรแกรมคำนวณค่าไฟฟ้าตามหน่วยการใช้งาน (kWh) ตามโครงสร้างอัตราค่าไฟฟ้าของประเทศไทย

### อัตราค่าไฟฟ้า:
- 0-150 หน่วย: 3.50 บาท/หน่วย
- 151-400 หน่วย: 4.20 บาท/หน่วย
- 401 หน่วยขึ้นไป: 5.00 บาท/หน่วย
- ค่าบริการ: 50 บาท (คงที่)

### วิธีการใช้งาน:
1. รันโปรแกรมโดยใช้คำสั่ง `java ElectricityBillCalculator`
2. ป้อนจำนวนหน่วยไฟฟ้าที่ใช้ไป (kWh)
3. โปรแกรมจะแสดงรายละเอียดการคำนวณและยอดรวมค่าไฟฟ้า

## English

Electricity bill calculator based on usage units (kWh) according to Thai electricity rate structure.

### Rate Structure:
- 0-150 units: 3.50 THB/unit
- 151-400 units: 4.20 THB/unit
- 401+ units: 5.00 THB/unit
- Service fee: 50 THB (fixed)

### How to use:
1. Run the program with command `java ElectricityBillCalculator`
2. Enter the electricity usage in kWh
3. The program will display calculation details and total amount

## Example Calculations:
- 100 kWh: 350.00 THB (first tier) + 50.00 THB (service fee) = 400.00 THB
- 200 kWh: 525.00 THB (first tier) + 210.00 THB (second tier) + 50.00 THB (service fee) = 785.00 THB
- 500 kWh: 525.00 THB (first tier) + 1,050.00 THB (second tier) + 500.00 THB (third tier) + 50.00 THB (service fee) = 2,125.00 THB

## คุณสมบัติ:
- การคำนวณแบบหลายระดับ (Tiered calculation)
- การแสดงรายละเอียดการคำนวณ (Detailed breakdown)
- การรองรับภาษาไทยและอังกฤษ (Thai and English support)
- โค้ด Java ที่เข้าใจง่าย (Easy-to-understand Java code)