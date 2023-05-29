package Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Connection.Database;

public class Hotel {
	static Connection con=Database.dbconnect();
  public static void Details() throws SQLException {
	Connection con=Database.dbconnect();
	  
		 Statement s=con.createStatement();
		 ResultSet rs=s.executeQuery("select * from troom");
		 String result="+-------+--------+-----+----------+\n";
               result+="|RoomNO |Type    |Price| Booking  |\n";	 
		       result+="+-------+--------+-----+----------+\n";
		 while(rs.next()) {
			result+="|"+rs.getInt(1)+"    |"+rs.getString(2)+"\t"+"|"+rs.getInt(3)+"\t"+"|"+rs.getString(4)+"|"+"\n";
		 }
		       result+="+-------+-------+------+----------+\n";
		System.out.println(result);
 }

public static boolean isvalid(int roomno) throws SQLException {
	Connection con=Database.dbconnect();
	String query="select roomno from troom ";
	Statement st=con.createStatement();
	ResultSet rs=st.executeQuery(query);
	while(rs.next()) {
		if(rs.getInt(1)==roomno) {
			return true;
		}
	}
	return false;
	
}

public static boolean isAvailabel(int roomno) throws SQLException {
	Connection con=Database.dbconnect();
String query="select Booking from troom where roomno=?";
PreparedStatement ps=con.prepareStatement(query);
ps.setInt(1,roomno);
ResultSet rs=ps.executeQuery();
rs.next();
	if(rs.getString(1).equals("Available")) {
		return true;
	}

	return false;
}

public static String RoomWithAvailablety() throws SQLException {

	 Statement s=con.createStatement();
	 ResultSet rs=s.executeQuery("select * from troom");
	 String result="+-------+--------+-----+----------+\n";
          result+="|RoomNO |Type    |Price| Booking  |\n";	 
	       result+="+-------+--------+-----+----------+\n";
	 while(rs.next()) {
		result+="|"+rs.getInt(1)+"    |"+rs.getString(2)+"\t"+"|"+rs.getInt(3)+"\t"+"|"+rs.getString(4)+"|"+"\n";
	 }
	       result+="+-------+-------+------+----------+\n";
	return result;
}

public static boolean BookARoom(int roomno, String Fname, String Lname, int Cadharno, int CRoomno, int Cbookeddays) throws SQLException {
	boolean check=false;
	String queryc="select Price from troom where roomno=?";
	PreparedStatement ps=con.prepareStatement(queryc);
	ps.setInt(1, roomno);
	ResultSet rs=ps.executeQuery();
	rs.next();
	int Price =rs.getInt(1);
	String query="insert into customer(Fname,Lname,Cadharno,CRoomno,Cbookeddays,Cbalance) values(?,?,?,?,?,?)";
	PreparedStatement s=con.prepareStatement(query);
	s.setString(1,Fname);
	s.setString(2,Lname);
	s.setInt(3, Cadharno);
	s.setInt(4,CRoomno);
	s.setInt(5, Cbookeddays);
	s.setInt(6,Price*Cbookeddays);
	int i=s.executeUpdate();
	
	String queryi="update troom set Cadharno=?,Booking=? where roomno=?";
	PreparedStatement st=con.prepareStatement(queryi);
	st.setInt(1, Cadharno);
	st.setString(2, "NOT AVAILABLE");
	st.setInt(3,roomno);
	int j=st.executeUpdate();
	if(i>0 && j>0) {
		check=true;
	}
	return check;
}

public static String FORGETROOMNO(int Cadharno) throws SQLException {
	String query="select roomno from troom where Cadharno=?";
	PreparedStatement ps=con.prepareStatement(query);
	ps.setInt(1, Cadharno);
	ResultSet rs=ps.executeQuery();
	if(rs.next()) {
		int roomno=rs.getInt(1);
		return "YOUR ROOM NO IS:-"+roomno;
	}
	return "NO ROOM BOOK THIS ADHARCARD ";
}

public static boolean RNandANIsAuthenticate( int roomno,int Cadharno) throws SQLException {
	if(isvalid(roomno)) {
	String query="select Cadharno from troom where roomno=?";
	PreparedStatement ps=con.prepareStatement(query);
	ps.setInt(1,roomno);
	ResultSet rs=ps.executeQuery();
	rs.next();
	int i=rs.getInt(1);
	if(i==Cadharno) {
		return true;
	}
	
}
	return false;
}

public static String FoodMenu() throws SQLException {
	Statement s=con.createStatement();
	ResultSet rs=s.executeQuery("select * from foodtable");
	String result="+----+---------------+----------+\n";
    result+="|Fid |Fname \t     |Fprice    |\n";
    result+="+----+---------------+----------+\n";
	      while(rs.next()) {
	    	  result+="|"+rs.getInt(1)+"   |"+rs.getString(2)+"\t"+" |"+rs.getInt(3)+"|"+"\n";
	      }
	      result+="+----+---------------+----------+\n";
	return result;
}

public static String addFoodOrdertable(int Fid, int roomno, int Fquantity) throws SQLException {
	String query="select * from foodtable where Fid=?";
	PreparedStatement ps=con.prepareStatement(query);
	ps.setInt(1,Fid);
	ResultSet rs=ps.executeQuery();
	rs.next();
	String Fname=rs.getString(2);
	int Fprice=rs.getInt(3);
	
	String queryic="insert into ordertable (Fid,roomno,Fname,Fprice,Fquantity,Ftotal) values(?,?,?,?,?,Fprice*Fquantity)";
	PreparedStatement pst=con.prepareStatement(queryic);
	pst.setInt(1, Fid);
	pst.setInt(2, roomno);
	pst.setString(3,Fname);
	pst.setInt(4, Fprice);
	pst.setInt(5, Fquantity);
	int irs=pst.executeUpdate();
	String result="";
	if(Fname.length()>14) {
		result="|"+Fname+"         |";
}
	else {
		result="|"+Fname+"\t |";
	}
	result+=Fprice+"       |";
	result +=Fquantity+"\t         |";
	result+=(Fprice*Fquantity)+"\t   |\n";
	return result;
}
public static boolean foodidisvalid() throws SQLException {
 String query="select Fid from foodtable";
 Statement st=con.createStatement();
 ResultSet rs=st.executeQuery(query);
 rs.next();
 if(rs.getInt(1)>0) {
	 return true;
 }
	return false;
}
public static int TotalPrice(int Fid, int Fquantity) throws SQLException {
	String query="select * from foodtable where Fid=?";
	PreparedStatement ps=con.prepareStatement(query);
    ps.setInt(1, Fid);
    ResultSet rs=ps.executeQuery();
    rs.next();
    int Fprice=rs.getInt(3);
    int amt=Fprice*Fquantity;
	return amt;
}
public static String TotalAmount(int totalamount, int Cadharno, int CRoomno) throws SQLException{
	String queryin="update customer set Cbalance=(Cbalance+?)where (CRoomno=? and Cadharno=? and Cbalance !=0)";
    PreparedStatement pst=con.prepareStatement(queryin);
    pst.setInt(1, totalamount);
    pst.setInt(2,Cadharno);
    pst.setInt(3, CRoomno);
    int affectrow=pst.executeUpdate();
    if(affectrow==1) {
    	return"ORDER IS PALACED! AOUMNUT WILL BE ADDED IN YOUR ACCOUNT ";
    }
    
	return "PLEASE TRY AGAIN LATER";
}
public static String RoomBill(int Cadharno, int roomno) throws SQLException {
	String result="";
	String query="select Booking,Price from troom where roomno=?  and Cadharno=?";
	PreparedStatement ps=con.prepareStatement(query);
	ps.setInt(1,roomno);
	ps.setInt(2,Cadharno);
	ResultSet rs=ps.executeQuery();
	rs.next();
	String Booking=rs.getString(1);
	int price=rs.getInt(2);
	//FACTH DAYS
	   String querydays ="select Cbookeddays from customer where (Cadharno=? and Croomno=? and Cbalance!= 0)";
	PreparedStatement psd = con.prepareStatement(querydays);
	psd.setInt(1, Cadharno);
	psd.setInt(2,roomno);
	ResultSet rsd=psd.executeQuery();
	rsd.next();
	int days=rsd.getInt(1);
	result+="\n**************************************\n";
	result+="\t\t\tFINAL BILL\n";
	result+="\t\t\t----------\n";
	result+="Room COSTS:\n";
	result+="+-------+-----------------------+-------+-------+-------+\n";
	result+="|Room \t|Details\t\t|Price \t|Days\t|Total\t|\n";
	result+="+-------+-----------------------+-------+-------+-------+\n";
	result+="|"+roomno+"\t";
	if(Booking.length()<20) {
		result+="|"+Booking+"\t\t";
	}else {
		result+="|"+Booking+"\t";
	}
	result+="| "+price+"\t";
	result+="| "+days+"\t";
	result+="| "+(days*price)+"\t|\n";
	result+="+-------+-----------------------+-------+------+--------+\n";
	return result;
}
 static String FooDBillFinal(int roomno) throws SQLException {
	String result="";
	String queryi="select roomno ,Fname,Fprice,sum(Fquantity),sum(Ftotal) from ordertable group by roomno,Fname having roomno=?";
	PreparedStatement ps=con.prepareStatement(queryi);
	ps.setInt(1, roomno);
	ResultSet rs=ps.executeQuery();
	int foodBill=0;
	result+="FOOD COSTS:\n";
	result+="+----------------+------+--------+------+\n";
	result+="|name\t\t |Price\t|quantity|Total\t|\n";
	result+="+----------------+------+--------+------+\n";
	while(rs.next()) {
		String Fname=rs.getString(2);
		int Fprice=rs.getInt(3);
		int Fquantity=rs.getInt(4);
		int Ftotal=rs.getInt(5);
		if(Fname.length()>14) {
			result+=" |"+Fname+"\t | ";
		}else {
			result+="|"+Fname+"\t |";
		}
		result+=Fprice+"\t|";
		result+=Fquantity+"\t |";
		result+=(Ftotal)+"\t|\n";
		
		foodBill+=Ftotal;
	}
	result+="+----------------+------+--------+------+\n";
	result+="YOUR BILL IS RS."+foodBill+"\n";
	result+="FOOD BILL IS PAID:-Rs."+foodBill+"\n";
	result+="****THANK YOU****";
	return result;
}
public static String getFINALBILL(int Cadharno, int roomno) throws SQLException {
	String roomBill=RoomBill( Cadharno, roomno);
	String FinalFoodBill=FooDBillFinal(roomno);
	String result=roomBill+FinalFoodBill;
	return result;
}
public static int GrantTotalAmount(int roomno, int Cadharno) throws SQLException {
	String query="select Cbalance from customer where  ( Cadharno=? and Croomno=? and Cbalance !=0);";
	PreparedStatement ps=con.prepareStatement(query);
	ps.setInt(1, Cadharno);
	ps.setInt(2,roomno);
	ResultSet rs=ps.executeQuery();
	rs.next();
	int totalamount=rs.getInt(1);
	
	return totalamount;
}
public static void removeCustomerDetail(int roomno, int Cadharno) throws SQLException {
String query="delete from customer where Croomno=?";
PreparedStatement ps=con.prepareStatement(query);
ps.setInt(1, roomno);
int delete=ps.executeUpdate();
String queryic="delete from ordertable where roomno=?";
PreparedStatement rev=con.prepareStatement(queryic);
rev.setInt(1, roomno);
int remove=rev.executeUpdate();
//Fetch days
String queryer="update troom set Booking=\"Available\" , Cadharno=0 where roomno=?";
PreparedStatement ups=con.prepareStatement(queryer);
ups.setInt(1,roomno);
int affect=ups.executeUpdate();
}	
}


