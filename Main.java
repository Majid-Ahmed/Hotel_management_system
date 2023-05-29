package Main;

import java.sql.SQLException;

import Connection.Database;
import Hotel.Uihotel;

public class Main {
public static void main(String[] args) throws  SQLException {
  Database.dbconnect();
	 Uihotel.Strat();
	

}
}
