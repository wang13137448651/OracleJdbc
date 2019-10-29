package Jdbc;

import java.util.ArrayList;

public class Test_Oracle {
	public static void main(String[] args) {
		
		String sql = "select * from student";
		ArrayList<studentbean> dbDQLWithSQL = OracleUtil.dbDQLWithSQL(sql, studentbean.class);
		for (studentbean studentbean : dbDQLWithSQL) {
			System.out.println(studentbean);
		}
		
	}

}
