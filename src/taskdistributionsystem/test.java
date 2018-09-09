package taskdistributionsystem;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itt.tds.coordinator.db.TDSDatabaseManager;
import java.sql.Statement;
import com.itt.tds.cfg.TDSConfiguration;;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello");
		
//		TDSDatabaseManager test;
		TDSDatabaseManager tds1 = new TDSDatabaseManager();
		Connection dbConnection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			
			dbConnection = tds1.getConnection();
			String sql = "show tables";
			
			rs    = tds1.executeSelectQuery(dbConnection, sql);
			
			 while (rs.next())	{
				 System.out.println(rs.getString("Tables_in_tds"));
			 }
			 int a = tds1.executeDMLQuery(dbConnection, sql);
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
		
		TDSConfiguration tdsConfiguration1 = TDSConfiguration.getInstance();
		TDSConfiguration tdsConfiguration2 = TDSConfiguration.getInstance();
		
		System.out.println("Instance 1 hash:" + tdsConfiguration1.hashCode());
        System.out.println("Instance 2 hash:" + tdsConfiguration2.hashCode());  
		
		
		
	
		
	}

}
