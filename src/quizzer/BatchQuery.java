package quizzer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

/**
 * This object represents a database query that executes multiple times, once
 * for each value of any parameter that is a <code>Collection</code> of values.
 * @author Armando
 */
public class BatchQuery extends AbstractQuery {
    
    private Object batchParam;
    private int batchParamNum;
    
    public BatchQuery(PreparedStatement statement, Object... parameters) {
        super(statement, parameters);
    }
    
    @Override
    public boolean executeQuery(boolean leaveOpen) {
        boolean success = true;
        
        if (batchParam == null) {
            reportError("A batch parameter must be set for this type of query.");
            return false;
        }
        
        for (Object param : (Collection)batchParam) {

            setParam(param, batchParamNum);

            try { stmt.executeUpdate(); }
            catch (SQLException ex) {
                success = false;
                reportError(ex.getMessage());
            }
        }
        
        try { stmt.close(); }
        catch (SQLException ex) { reportError(ex.getMessage()); }
        
        return success;
    }
    
    @Override
    protected void setParam(Object param, int n) {
        
        if (param instanceof Collection) {
            batchParam = param;
            batchParamNum = n;
        } else
            super.setParam(param, n);
    }
    
}
