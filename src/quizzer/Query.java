package quizzer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static quizzer.AbstractQuery.T_BOOLEAN;
import static quizzer.AbstractQuery.T_BYTES;
import static quizzer.AbstractQuery.T_DOUBLE;
import static quizzer.AbstractQuery.T_INT;
import static quizzer.AbstractQuery.T_STRING;

/**
 *
 * @author Armando
 */
public class Query extends AbstractQuery {
    boolean hasResults = false;
    
    public Query(PreparedStatement statement, Object... parameters) {
        super(statement, parameters);
    }
    
    public Query(PreparedStatement statement) {
        super(statement, (Object) null);
    }
    
    @Override
    public boolean executeQuery(boolean leaveOpen) {
        boolean success = true;
        
        try {
            hasResults = stmt.execute();
        } catch (SQLException e) {
            
            reportError(e.getMessage());
            success = false;
        } finally {
            if (!leaveOpen && stmt != null) {
                try { stmt.close(); }
                catch (SQLException ex) { reportError(ex.getMessage()); }
            }
        }
        
        return success;
    }
    
    public List<Integer> getGeneratedKeys() {
        ResultSet rs;
        List<Integer> keys;
        
        if (stmt == null) {
            reportError("Cannot obtain generated key: query not yet executed.");
            return null;
        }
        
        keys = new ArrayList<>();
        try {
            if (stmt.isClosed())
                System.err.println("Cannot obtain generated keys from a closed"
                        + " query. Call execute(true) to keep the query opened"
                        + " after execution.");
            else {
                rs = stmt.getGeneratedKeys();
                while (rs.next())
                    keys.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            reportError(e.getMessage());
        } finally {
            try { stmt.close(); }
            catch (SQLException ex) { reportError(ex.getMessage()); }
        }
        
        return keys;
    }
    
    public int getRowsAffected() {
        int rowsAff;
        
        if (hasResults) {
            reportError("Cannot get the number of rows affected on a query "
                    + "that returns results.");
            return -1;
        }
        
        try {
            rowsAff = stmt.getUpdateCount();
        } catch (SQLException e) {
            reportError(e.getMessage());
            return -1;
        } finally {
            try { stmt.close(); }
            catch (SQLException ex) { reportError(ex.getMessage()); }
        }
        
        return rowsAff;
    }
    
    /**
     * Returns a list of results of this query. The result keys and types must
     * be specified with a <code>Map</code> of keys to an integer constant
     * representing their corresponding data types.
     * @param keysToTypes The keys used to retrieve the values of each result
     * in the returned list, mapped to the value's data type represented by one
     * of the following integer constants: <code>T_INT</code>,
     * <code>T_DOUBLE</code>, <code>T_STRING</code>, <code>T_BOOLEAN</code>,
     * <code>T_BYTES</code>.
     * @return A list results, with each result represented by a map of result
     * keys (<code>String</code>) to result values (<code>Object</code>). On
     * error (e.g. query was not executed or did not return results), returns
     * <code>null</code>.
     */
    public List<Map<String, Object>> getResults(Map<String,Integer> keysToTypes) {
        List<Map<String,Object>> results;
        Map<String,Object> aResult;
        ResultSet resultSet;
        Iterator<String> iter;
        String aKey;
        Integer aType;
        
        if (!hasResults) {
            reportError("Cannot get results: query did not return results.");
            return null;
        }
        
        results = new ArrayList<>();
        
        try {
            if ((resultSet = stmt.getResultSet()) == null) {
                reportError("The query returned no results.");
                return null;
            }
            
            while (resultSet.next()) {
                
                aResult = new HashMap<>();
                for (iter = keysToTypes.keySet().iterator(); iter.hasNext(); ) {
                    aKey = iter.next();
                    aType = keysToTypes.get(aKey);
                    addValueToResult(resultSet, aResult, aKey, aType);
                }
                if (!aResult.isEmpty())
                    results.add(aResult);
            }
        } catch (SQLException e) {
            reportError(e.getMessage());
        } finally {
            try { stmt.close(); }
            catch (SQLException ex) { reportError(ex.getMessage()); }
        }
        
        return results;
    }
    
    private void addValueToResult(ResultSet rs, Map<String, Object> result
            , String key, int type) throws SQLException {
        String sVal;
        byte[] baVal;
        
        switch (type) {
            case T_INT:;
                result.put(key, rs.getInt(key)); break;
            case T_DOUBLE:
                result.put(key, rs.getDouble(key)); break;
            case T_STRING:
                sVal = rs.getString(key);
                if (sVal != null)
                    result.put(key, sVal); break;
            case T_BOOLEAN:
                result.put(key, rs.getBoolean(key)); break;
            case T_BYTES:
                if ((baVal = rs.getBytes(key)) != null)
                    result.put(key, baVal); break;
            default:
                throw new IllegalArgumentException("Invalid result value type.");
        }
            
    }
    
}
