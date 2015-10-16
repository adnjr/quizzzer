package quizzer;

import ErrorService.AbstractErrorReporter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Armando
 */
public class Database extends AbstractErrorReporter {
    private Connection con = null;
    
    private String url = null;
    private String user = null;
    private String password = null;
    
    public Database(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    public boolean openConnection() {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public void closeConnection() {
        if (con != null) {
            try { con.close(); }
            catch (SQLException e) { }
        }
    }
   
    /**
     * Performs the specified SQL query with the given <code>parameters</code>,
     * and returns whether or not any errors were generated.
     * @param sql The SQL statement to send to the database, with a
     *        question-mark used for parameter place holders.
     * @param parameters 1 or more parameters to substitute for place holders
     *        (in order) in the <code>sql</code> string.
     * @return True on success, or false on failure or if any errors occurred.
     */
    public boolean query(String sql, Object... parameters) {
        Query pQuery = new Query(prepareStatement(sql), parameters);
        return pQuery.executeQuery(false);
    }
    
    public boolean batchQuery(String sql, Object... parameters) {
        BatchQuery bQuery = new BatchQuery(prepareStatement(sql), parameters);
        return bQuery.executeQuery(false);
    }
    
    /**
     * Performs the specified SQL query with the given <code>parameters</code>,
     * and returns the auto-generated key resulting from the query. If the query
     * returned errors or did not result in an auto-generated key, -1 is returned.
     * @param sql The SQL statement to send to the database, with a
     *        question-mark used for parameter place holders.
     * @param parameters 1 or more parameters to substitute for place holders
     *        (in order) in the <code>sql</code> string.
     * @return The auto-generated key that the given <code>sql</code> statement
     *        produced. If no key was generated, or if the query was unsuccessful
     *        or had errors, then -1 is returned. 
     */
    public int getGeneratedKey_Query(String sql, Object... parameters) {
        Query pQuery;
        List<Integer> keys;
        
        pQuery = new Query(prepareStatement(sql), parameters);
        
        if (!pQuery.executeQuery(true))
            return -1;
        
        if ((keys = pQuery.getGeneratedKeys()) == null || keys.isEmpty())
            return -1;
        
        return keys.get(0);
    }
    
    public List<Map<String, Object>> getResults_Query(String sql
            , Map<String,Integer> resultKeys, Object... parameters) {
        Query query;
           
        query = new Query(prepareStatement(sql), parameters);
        
        if (!query.executeQuery(true))
            return null;
        
        return query.getResults(resultKeys);
    }
    
    private boolean isCallable(String sql) {
        if (sql != null && sql.length() > 0)
            return sql.charAt(0) == '{';
        return false;
    }
    
    private PreparedStatement prepareStatement(String sql) {
        PreparedStatement stmt;
        System.err.println("sql: " + sql);
        try {
            stmt = (isCallable(sql))
                    ? con.prepareCall(sql)
                    : con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            reportError(e.getMessage());
            return null;
        }
        
        return stmt;
    }
    
}