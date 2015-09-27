package db;

import java.util.List;
import java.util.Map;

public interface DBSQLiteService  {
	public boolean addUser(Object [] params) ;
	
	public boolean deleteUser(Object [] params) ;
	
	public boolean updateUser(Object [] params) ;
	
	public Map<String, String> GetUser(String[] selectionArgs);
	
	public List<Map<String, String>> ListUserMap(String[] selectionArgs);
}
