package com.mrhi.class_six.person_identity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DBUtillity {
	
	
	// use to set func() doing Action.
	public static final int ACTION_ID_QUERY = 1;
	public static final int ACTION_NAME_QUERY = 2;
	public static final int ACTION_SORT_ASC = 1;
	public static final int ACTION_SORT_DESC = 2;
	private DBConnector dbInit;
	
	public DBUtillity() {
		this.dbInit = new DBConnector();
		
	}
	
	
	
	// INSERT INTO ... VALUES ...
	public boolean insertPersonIdentity( PersonIdentity person) {
		
		// open DBOpenHelper
		Connection connection = this.dbInit.initConnection();
		
		boolean isInsert = true;
		PreparedStatement safeQurey = null;
		final String QUERY_STRING = 
				"insert into personidentitytable values(?,?,?,?,?,?,?,?,?)";
		
		
		// INSERT
		try {
			if ( connection.isClosed()) {
				throw new SQLException();
			}
			// depend to SQL injection	
			safeQurey = (PreparedStatement ) connection.prepareStatement( QUERY_STRING);
			
			safeQurey.setString(1, person.getPersonId());
			safeQurey.setString(2, person.getPersonName());
			safeQurey.setInt(3, person.getPersonAge());
			safeQurey.setInt(4, person.getPointLangC());
			safeQurey.setInt(5, person.getPointLangJava());
			safeQurey.setInt(6, person.getPointLangSql());
			safeQurey.setInt(7, person.getPointSum());
			safeQurey.setDouble(8, person.getPointAvg());
			safeQurey.setString(9, person.getPointGrade());
			
			if(safeQurey.executeUpdate() <= 0) {
				System.out.println("삽입 실패 ㅠㅜ");
				isInsert = false;
			}
			
			
		} catch (SQLException e) {
			System.err.println("error occur");
			isInsert = false;
			
		// Close to DBOpenHelper.
		} finally {
			
			try {
				if(	safeQurey != null && !safeQurey.isClosed() )	
					safeQurey.close();
				
				if ( connection != null && !connection.isClosed()) 
					connection.close();
				
				} catch (SQLException e) {
					System.out.println("error occur");
					isInsert = false;
			}
		}
		
		return isInsert;
	}
	
	
	// Update set ~ where personId like ?
	public boolean updatePersonIdentity( PersonIdentity person) {
		boolean isSuccess = true;
		
		final String QUERY_UPDATE_ID 	= 
				"update personidentitydb.personidentitytable " +
				"set personName = ?, " 	+
				"personAge = ?, " 		+
				"pointLangC = ?, " 		+
				"pointLangJava = ?, " 	+
				"pointLangSql = ?, "	+
				"pointSum = ?, "		+
				"pointAvg = ?, "		+
				"pointGrade = ? "		+
				"where personId like ? ";
		
		PreparedStatement safeQurey = null;
		Connection connection = this.dbInit.initConnection();
		
		
		if(connection == null) {
			System.err.println("DB 로딩 실패 ㅠㅜ");
			return false;
		}
		person.initPersonIdentity();
		
		try {
			safeQurey = (PreparedStatement ) connection.prepareStatement( QUERY_UPDATE_ID) ;
			
			safeQurey.setString(1, person.getPersonName());
			safeQurey.setInt(2, person.getPersonAge());
			safeQurey.setInt(3, person.getPointLangC());
			safeQurey.setInt(4, person.getPointLangJava());
			safeQurey.setInt(5, person.getPointLangSql());
			safeQurey.setInt(6, person.getPointSum());
			safeQurey.setDouble(7, person.getPointAvg());
			safeQurey.setString(8, person.getPointGrade());
			safeQurey.setString(9, person.getPersonId());
			
			
			int isUpdate = safeQurey.executeUpdate();
			if( isUpdate <= 0) {
				System.out.println("변경할 대상이 없어요!!");
				isSuccess = false;
			}
			
			
		} catch (SQLException e) {
			System.err.println("error occur");
			isSuccess = false;
			
		} catch (Exception e) {
			System.err.println("error occur");
			isSuccess = false;
			
			
		// try to Close DBOpenHelper.	
		} finally {
			
			try {
				if( safeQurey != null && !safeQurey.isClosed())
					safeQurey.close();
				
				if( connection != null && !connection.isClosed())
					connection.close();
				
			} catch (SQLException e) {
				System.err.println("error occur");
				isSuccess = false;
			}
		}
		
		return isSuccess;
	}
	
	
	
	
	
	public List<PersonIdentity> selectPersonIdentity( String queryTarget ,int action){	
		final String QUERY_SELECT_ID = 	
				"select * from personidentitydb.personidentitytable where personId like ?";
		final String QUERY_SELECT_NAME	 = 
				"select * from personidentitydb.personidentitytable where personName like ?";

		String querySyntax = null;
		
		List<PersonIdentity> recodeSet = new ArrayList<PersonIdentity>();
		ResultSet resultSet = null;
		PreparedStatement safeQurey = null;
		Connection connection = this.dbInit.initConnection();
		
		if( connection == null) {
			System.out.println("DB 로딩 실패 ㅠㅜ");
			return null;
		}
		
		
		switch ( action) {
			case ACTION_ID_QUERY:		querySyntax = QUERY_SELECT_ID; 		break;
			case ACTION_NAME_QUERY: 	querySyntax = QUERY_SELECT_NAME; 	break;
			default: System.out.println("??????"); return null;
		}
		
		queryTarget = "%" + queryTarget + "%";
		try {
			safeQurey = (PreparedStatement ) connection.prepareStatement( querySyntax) ;
			safeQurey.setString(1, queryTarget);
			resultSet = safeQurey.executeQuery();
			
			
			while( resultSet.next()) {
				PersonIdentity buf = new PersonIdentity();

				buf.setPersonId(resultSet.getString(1));
				buf.setPersonName(resultSet.getString(2));
				buf.setPersonAge(resultSet.getInt(3));
				buf.setPointLangC(resultSet.getInt(4));
				buf.setPointLangJava(resultSet.getInt(5));
				buf.setPointLangSql(resultSet.getInt(6));
				buf.setPointSum(resultSet.getInt(7));
				buf.setPointAvg(resultSet.getDouble(8));
				buf.setPointGrade(resultSet.getString(9));
				
				recodeSet.add( buf);	
			}
			
		//// END SELECT	////
		} catch ( SQLException e) {
			System.err.println("error occur");			
			return null;
			
		} catch ( Exception e) {
			System.err.println("error occur");
			return null;
			
		} finally {
			
			try {
				if( safeQurey != null && !safeQurey.isClosed())
					safeQurey.close();
				
				if( connection != null && !connection.isClosed())
					connection.close();
				
			} catch ( SQLException e) {
				System.err.println("error occur");
				return null;
			}
		}
		return recodeSet;
	}
	
	
	
	public List<PersonIdentity> selectAllPhoneBook() {
		
		ArrayList<PersonIdentity> recodeSet = new ArrayList<PersonIdentity >();
		PreparedStatement safeQuery = null;
		final String QUERY_SELECT_ALL = "select * from personidentitydb.personidentitytable";
		Connection connection = this.dbInit.initConnection();
		ResultSet resultSet = null;
		
		if(connection == null) {
			System.err.println("DB 로딩 실패 ㅠㅜ");
			return null;
		}
		
		try {
			safeQuery = ( PreparedStatement) connection.prepareStatement( QUERY_SELECT_ALL);
			resultSet = safeQuery.executeQuery();
			
			while( resultSet.next()) {
				PersonIdentity buf = new PersonIdentity();

				buf.setPersonId(resultSet.getString(1));
				buf.setPersonName(resultSet.getString(2));
				buf.setPersonAge(resultSet.getInt(3));
				buf.setPointLangC(resultSet.getInt(4));
				buf.setPointLangJava(resultSet.getInt(5));
				buf.setPointLangSql(resultSet.getInt(6));
				buf.setPointSum(resultSet.getInt(7));
				buf.setPointAvg(resultSet.getDouble(8));
				buf.setPointGrade(resultSet.getString(9));
				
				recodeSet.add( buf);	
			}
		} catch ( SQLException e) {
			System.err.println("error occur");
			return null;
			
		} finally {
			try {
				if( safeQuery != null && !safeQuery.isClosed())
					safeQuery.close();
				
				if( connection != null && !connection.isClosed())
					connection.close();
				
			}catch ( SQLException e) {
				System.out.println("error occur");
				return null;
				
			}
		}
	
		return recodeSet;
	}
	
	

	
	public boolean deletePersonIdentity(String queryTarget, int action) {
		boolean isSuccess = true;
		
		final String QUERY_DELETE_ID = 
				"delete from personidentitydb.personidentitytable where personId like ?";
		final String QUERY_DELETE_NAME = 
				"delete from personidentitydb.personidentitytable where personName like ?";
		
		Connection connection = this.dbInit.initConnection();
		PreparedStatement safeQuery = null;
		String querySyntax 	= null;
		
		
		if(connection == null) {
			System.out.println("DB 로딩 실패 ㅠㅜ");
			return false;
		}
		
		
		switch ( action) {
			
			case 1: querySyntax = QUERY_DELETE_ID;		break;
			case 2: querySyntax = QUERY_DELETE_NAME;	break;
			default: return false;
		}
		
		queryTarget = "%" + queryTarget + "%";
		
		try {
			safeQuery = ( PreparedStatement) connection.prepareStatement( querySyntax);
			safeQuery.setString(1, queryTarget);
			int isDelete= safeQuery.executeUpdate();
			
			if( isDelete <= 0) {
				System.out.println("대상을 찾을수 없어요!!");
				isSuccess = false;
			}
			
			
		} catch (SQLException e) {
			System.out.println("error occur");
			isSuccess = false;
			
		} finally {
			try {
				if( safeQuery != null && !safeQuery.isClosed())
					safeQuery.close();
				
				if( connection != null && !connection.isClosed())
					connection.close();
				
			}catch ( SQLException e) {
				System.err.println("error occur!");
				isSuccess = false;
				
			}
		}
		
		return isSuccess;
	}
	
	
	
	
	
	
	public List<PersonIdentity > sortPersonIdentity(int action) {
		final String QUERY_SELECT_SORT = 
				"select * from personidentitydb.personidentitytable order by pointSum ";
		final String QUERY_SELECT_SORT_ASC 	= "asc";
		final String QUERY_SELECT_SORT_DESC	= "desc";
		
		String querySyntax = null;
		ResultSet resultSet = null;
		List<PersonIdentity > recodeSet = new ArrayList<PersonIdentity >();
		
		Connection connection = this.dbInit.initConnection();
		PreparedStatement safeQuery = null;
		
		switch( action) {			
		
			case DBUtillity.ACTION_SORT_ASC: 
				querySyntax = QUERY_SELECT_SORT + QUERY_SELECT_SORT_ASC;	break;
			
			case DBUtillity.ACTION_SORT_DESC:	
				querySyntax = QUERY_SELECT_SORT + QUERY_SELECT_SORT_DESC;	break;
			default:
				return null;
		}
		
		try {
			safeQuery = (PreparedStatement) connection.prepareStatement( querySyntax);
			resultSet = safeQuery.executeQuery( querySyntax);
		
			while( resultSet.next()) {
				PersonIdentity buf =  new PersonIdentity();
				
				buf.setPersonId(resultSet.getString(1));
				buf.setPersonName(resultSet.getString(2));
				buf.setPersonAge(resultSet.getInt(3));
				buf.setPointLangC(resultSet.getInt(4));
				buf.setPointLangJava(resultSet.getInt(5));
				buf.setPointLangSql(resultSet.getInt(6));
				buf.setPointSum(resultSet.getInt(7));
				buf.setPointAvg(resultSet.getDouble(8));
				buf.setPointGrade(resultSet.getString(9));
				
				recodeSet.add( buf);
			}
			
			
		} catch ( SQLException e) {
			System.err.println("error occur!");
			return null;
			
		} finally {
			
			try {
				if( safeQuery != null && !safeQuery.isClosed())
					safeQuery.close();
				
				if( connection != null && !connection.isClosed())
					connection.close();
				
			} catch ( SQLException e) {
			 	System.err.println("error occur!");
				return null;
			}
		}
	
		return recodeSet;
	}
}
