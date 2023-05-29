package Hotel;

import java.sql.SQLException;
import java.util.Scanner;

public class Uihotel {
	
  public  static void Strat() throws SQLException {
	  Scanner sc=new Scanner(System.in);
	while(true) {
	System.out.println("WELCOME TO JAVA HOTEL:");
	System.out.println("YES HOW CAN HELP U:");
	System.out.println("1. BOOK A ROOM:");
	System.out.println("2. LOGIN FOR FOOD HUB:");
	System.out.println("3. EXIT");
	System.out.println("ENTER YOUR CHOICE:");
	int choice=sc.nextInt();
	if(choice==3) {
		break;
	}
	switch (choice) {
	case 1:
		BookARoom();
		break;
	case 2:
	Login();
    break;
	default:
		throw new IllegalArgumentException("Unexpected value: " + choice);
	}
}
}
private static void Login() throws SQLException {
	Scanner sc=new Scanner(System.in);
	while(true) {
	System.out.println("*********************");
	System.out.println("SELECT OPERATOIN TO PERFORM:");
	System.out.println("1. LOGIN BY ROOMNO AND ADHARNO:");
	System.out.println("2. FORGET ROOM NO");
	System.out.println("3. EXIT");
	System.out.println("ENTER YOUR CHOICE:");
	int choice=sc.nextInt();
	if(choice==3) {
		break;
		}
	switch(choice) {
	case 1:
	  ROOMNOAADHARNO();
	break;
	case 2: 
		FOGERTROOMNO();
		break;
		default :
			System.out.println("PLAES  GIVE VALID INPUT:");
	}
	
}
}
private static void FOGERTROOMNO() throws SQLException {
	 Scanner sc=new Scanner(System.in);
	System.out.println("ENTER YOUR ADHARNO NO:");
	int Cadharno=sc.nextInt();
	System.out.println(Hotel.FORGETROOMNO(Cadharno));
	
	
}
private static void ROOMNOAADHARNO() throws SQLException {
	 Scanner sc=new Scanner(System.in);
System.out.println("ENTER YOUR ROOM:");
int roomno=sc.nextInt();
System.out.println("ENTER ADHAR NO:");
	int Cadharno=sc.nextInt();
	if(Hotel.RNandANIsAuthenticate(roomno, Cadharno)) {
		System.out.println("\n\t LOGIN SUCCESSFUL!:");
		while(true) {
			System.out.println("\n*********************");
			System.out.println("SELECT OPERATION:");
			System.out.println("1. Order Food:");
			System.out.println("2. Check Out");
			System.out.println("3. Go BACK:");
			System.out.println("ENTER YOUR CHOICE:");
			int choice=sc.nextInt();
			switch(choice) {
			case 1:
				orderFood(Cadharno, roomno);
				break;
			case 2:
				CheckOut( Cadharno, roomno);;
				break;
				default :
					System.out.println("PLEASE ENTER VALID CHOICE:");
			}
		}
	}else {
		System.out.println("PLEASE ENTER VALID DETAILS:");
	}
}
private static void CheckOut( int Cadharno, int roomno) throws SQLException {
	Scanner sc=new Scanner(System.in);
	System.out.println("***-SHOW MY  BILLS-***");
	System.out.println(Hotel.getFINALBILL( Cadharno, roomno));
	int finalAoumnt=Hotel.GrantTotalAmount(roomno, Cadharno);
	System.out.println(" CLEAR ROOM BILL BEFORE CHECKOUT Rs/:-"+finalAoumnt);
	while(true) {
		System.out.println("ENTER A AMOINT TO PAY:");
		int payment=sc.nextInt();
		if(payment==finalAoumnt) {
			System.out.println("THANK U WISH VISIT AGAIN:");
			System.out.println("**********ENJOY YOUR DAY************");
			Hotel.removeCustomerDetail(roomno, Cadharno);
			System.exit(0);
			break;
		}else {
			System.out.println("PLEASE PAY:");
		}
	}
	
}
public static void orderFood(int Cadharno, int roomno) throws SQLException {
	Scanner sc=new Scanner(System.in);
	String TotalOrder="";
	int totalamount=0;
	while(true) {
	System.out.println("****************************");
	System.out.println("WELCOME TO PIZZA HUB:");
	System.out.println("HERE IS MENU CARD:");
	System.out.println(Hotel.FoodMenu());
	System.out.println("ENTER FOOD ID U WANT TO BOOK:");
	int Fid=sc.nextInt();
	if(Hotel.foodidisvalid()) {
	System.out.println("ENTER QUANTITY:");
	int Fquantity=sc.nextInt();
	String order=Hotel.addFoodOrdertable( Fid,  roomno,  Fquantity);
	TotalOrder+=order;
	int curamount=Hotel.TotalPrice( Fid, Fquantity);
	System.out.println(curamount);
	totalamount+=curamount;
	System.out.println("ORDER MORE ITEMS? [y/n]:");
	char ans=sc.next().toLowerCase().charAt(0);
	if(ans=='n') {

		if(ans == 'n') {	
			System.out.println("+-----------------+---------+------------+---------+");
			System.out.println("|Fname            | FPrice  | Fquqntity  | Subtotal|");
			System.out.println("+-----------------+---------+------------+---------+");
			System.out.print(TotalOrder);
			System.out.println("+-----------------+---------+------------+---------+");
			System.out.println("Total Amount: Rs."+totalamount);
	       Hotel.TotalAmount( totalamount,  Cadharno, roomno);
	       System.out.println("PLEASE PAY BEFORE CHECKOUT:");
			break; 
}}}else {System.out.println("ENTER VALID FOOD ID:");}
}
}
static void BookARoom() throws SQLException {
	 Scanner sc=new Scanner(System.in);
	System.out.println("WELCOME TO JAVA HOTEL:");
	System.out.println("1. SHOW ALL ROOM:");
	System.out.println("2. BOOK A ROOM:");
	System.out.println("3. EXIT");
	System.out.println("ENTER YOUR CHOICE:");
	int choice=sc.nextInt();
	if(choice==3) {
		System.out.println("Exit");
	}
	switch(choice) {
	case 1:
		System.out.println("WELCOME TO JAVA HOTEL:");
		System.out.println("ROOM INFORMATION:");
		Hotel.Details();
		break;
	case 2:
		System.out.println("***************************");
		System.out.println("DISPLAY ALL ROOM WITH AVAILABLETY:");
		System.out.println(Hotel.RoomWithAvailablety());
		System.out.println("ENTER ROOM NO FOR BOOKED:");
		int roomno=sc.nextInt();
		if(Hotel.isvalid(roomno)){
			if(Hotel.isAvailabel(roomno)) {
				System.out.println("ENTER DAYS FOR BOOKED:");
				int bookeddays=sc.nextInt();
				System.out.println("ENTER YOUR FIRST NAME:");
				String Fname=sc.next();
				System.out.println("ENTER LAST NAME:");
				String Lname=sc.next();
				System.out.println("ENTER ADHAR NO:");
				int Cadharno=sc.nextInt();
				System.out.println("BOOKING CONFIRM?(y:n)");
				char bookingConfirm=sc.next().toLowerCase().charAt(0);
				if(bookingConfirm !='n') {
					boolean isbooking=Hotel.BookARoom(roomno, Fname, Lname, Cadharno, roomno, bookeddays);
					if(isbooking) {
						System.out.println("CONGRATS "+Fname+" YOUR ROOM IS BOOKED FOR "+bookeddays+" DAYS ENJOY YOUR DAYS:");
					}else {
						System.out.println(" SORRY ROOM IS NOT BOOKED:");
					}
				}else {
					System.out.println("BOOKING CANCEL:");
				}
	}else {
		System.out.println("SORRY: ROOM IS NOT AVAILABEL:");
	}
}else {
	System.out.println("INVALID ROOM NO:");
}
		break;
		default:
			System.out.println("INVALID CHOICE:");
}
}
}