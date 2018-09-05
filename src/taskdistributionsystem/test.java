package taskdistributionsystem;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itt.tds.coordinator.db.TDSDatabaseManager;
import java.sql.Statement;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello");
		TDSDatabaseManager tds1 = new TDSDatabaseManager();
		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			
			
			String sql = "show tables";
			
			rs    = tds1.executeSelectQuery( sql);
			
			 while (rs.next())	{
				 System.out.println(rs.getString("Tables_in_tds"));
			 }
			 int a = tds1.executeDMLQuery(sql);
			 System.out.println(a);
		} catch (SQLException e)	{
			System.out.println(e.getMessage());
		}
		finally {
			try {
				rs.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}		
	
		
	}

}
