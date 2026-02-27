void main() {

    try(Scanner input = new Scanner(System.in)){
    IO.print("Enter your name : ");
    String name = input.nextLine();
    IO.print("Enter your Birth : ");
    int YearOfBirth = input.nextInt();
    IO.print("Are you Thai People? (y/n)");
    char answer = input.next().charAt(0);
    IO.print(""+answer);
    boolean isThaiNationality;
    if(answer=='y'){
        isThaiNationality = true;
    } else {
        isThaiNationality = false;
    }

        IO.print("Are you male? (y/n)");
    char answer2 = input.next().charAt(0);
    IO.print(""+answer2);
    boolean isMale;
    if(answer2=='y'){
        isMale = true;
    } else {
         isMale = false;
    }

    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
    String formattedDate = date.format(formatter);
    int currentYear = Integer.parseInt(formattedDate);
    int age = currentYear - YearOfBirth;
    IO.print("Your name is "+ name);
    IO.print("Todat Date : "+age);
    boolean isEligibleToVore = (age >= 18) && isThaiNationality;
    if(isEligibleToVore && isMale){
        IO.print(" You have the right to vote and to Serve the nation");
    }else if (isEligibleToVore){
        IO.print(" You have the right to vote ");
    } else {
        IO.print(" You have not the right to vote ");
    }
    input.close();
    } catch(InputMismatchException e){
        IO.print("Input Invalid");
    }
}

