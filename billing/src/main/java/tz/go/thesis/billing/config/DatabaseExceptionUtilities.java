package tz.go.thesis.billing.config;

import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.jdbc.support.SQLErrorCodesFactory;
import org.springframework.stereotype.Component;

@Component
public class DatabaseExceptionUtilities {
	
	@Autowired
	DataSource dataSource;
	
	private SQLErrorCodes sqlErrorCodes;
	
	public boolean isExceptionADeadlock(SQLException exception)
	{
		String errorCode = getErrorCode(exception);
		String sqlState = getSqlState(exception);
		return Arrays.stream(getSqlErrorCodes().getDeadlockLoserCodes()).anyMatch(x -> x.equalsIgnoreCase(errorCode) || x.equalsIgnoreCase(sqlState));
	}
	
	public boolean isExceptionADuplicate(SQLException exception)
	{
		String errorCode = getErrorCode(exception);
		String sqlState = getSqlState(exception);
		return Arrays.stream(getSqlErrorCodes().getDuplicateKeyCodes()).anyMatch(x -> x.equalsIgnoreCase(errorCode) || x.equalsIgnoreCase(sqlState));
	}
	
	public boolean isExceptionADataIntegrityViolation(SQLException exception)
	{
		String errorCode = getErrorCode(exception);
		String sqlState = getSqlState(exception);
		return Arrays.stream(getSqlErrorCodes().getDataIntegrityViolationCodes()).anyMatch(x -> x.equalsIgnoreCase(errorCode) || x.equalsIgnoreCase(sqlState));
	}
	
	public boolean isExceptionBadGrammerSQL(SQLException exception)
	{
		String errorCode = getErrorCode(exception);
		String sqlState = getSqlState(exception);
		return Arrays.stream(getSqlErrorCodes().getBadSqlGrammarCodes()).anyMatch(x -> x.equalsIgnoreCase(errorCode) || x.equalsIgnoreCase(sqlState));
	}
	
	private String getErrorCode(SQLException exception)
	{
		return Integer.toString(exception.getErrorCode());
	}
	
	private String getSqlState(SQLException exception) 
	{
		return exception.getSQLState();
	}
	
	private SQLErrorCodes getSqlErrorCodes()
	{
		if (sqlErrorCodes == null)
			sqlErrorCodes = SQLErrorCodesFactory.getInstance().getErrorCodes(dataSource);
		
		return sqlErrorCodes;
	}
	
	
	public void handleDatabaseException(SQLException e) 
	{
		if (isExceptionBadGrammerSQL(e))
			System.out.println("Bad Grammar Exception: " + e.toString());
		
		else if (isExceptionADuplicate(e))
			System.out.println("Duplicate Exception: " + e.toString());
		
		else if (isExceptionADeadlock(e))
			System.out.println("Deadlock Exception: " + e.toString());
		
		else if (isExceptionADataIntegrityViolation(e))
			System.out.println("Data Integrity Violation Exception: " + e.toString());
			
	}
	
	public static void printSQLException(SQLException ex) {
	      for (Throwable e: ex) {
	          if (e instanceof SQLException) {
	              if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {
	                  e.printStackTrace(System.err);
	                  System.err.println("SQLState: " + ((SQLException) e).getSQLState());
	                  System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
	                  System.err.println("Message: " + e.getMessage());
	                  Throwable t = ex.getCause();
	                  while (t != null) {
	                      System.out.println("Cause: " + t);
	                      t = t.getCause();
	                  }
	              }
	          }
	      }
	  }
	
	
	public static boolean ignoreSQLException(String sqlState) {

	    if (sqlState == null) {
	        System.out.println("The SQL state is not defined!");
	        return false;
	    }

	    // X0Y32: Jar file already exists in schema
	    if (sqlState.equalsIgnoreCase("X0Y32"))
	        return true;

	    // 42Y55: Table already exists in schema
	    if (sqlState.equalsIgnoreCase("42Y55"))
	        return true;

	    return false;
	}

}
