package quizzer;

import ErrorService.AbstractErrorReporter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Armando
 */
public abstract class AbstractQuery extends AbstractErrorReporter {
    protected PreparedStatement stmt;
    
    public static final int T_INT = 0;
    public static final int T_DOUBLE = 1;
    public static final int T_STRING = 2;
    public static final int T_BOOLEAN = 3;
    public static final int T_BYTES = 4;
    
    public abstract boolean executeQuery(boolean leaveOpen);
    
    public AbstractQuery(PreparedStatement statement, Object... parameters) {
        this.stmt = statement;
        
        if (parameters != null && parameters.length > 0 && parameters[0] != null)
            setParams(new ArrayList<>(Arrays.asList(parameters)));
    }
    
    /**
     * This is a convenience method for calling <code>setParam</code> on a
     * Collection.
     * @param params
     */
    public final void setParams(List<Object> params) {
        
        if (params == null || params.isEmpty())
            return;

        for (int i = 1; i <= params.size(); i++)
            setParam(params.get(i-1), i);
    }
    
    /**
     * Sets parameter <code>n</code> of this query's statement. It handles
     * type concerns, calling the statement's type-specific methods.
     * @param param Parameter to set. Supported types are: <code>null</code>,
     *        {@link Integer}, {@link Double}, {@link String}, {@link File}, and
     *        {@link Boolean}.
     * @param n Specifies which place holder to replace with the given parameter
     *          value. Place holder numbering starts at 1.
     */
    protected void setParam(Object param, int n) {
        
        try {
            if (param == null)
                stmt.setNull(n, java.sql.Types.VARCHAR);
            else if (param instanceof Integer)
                stmt.setInt(n, (Integer)param);
            else if (param instanceof Double)
                stmt.setDouble(n, (Double)param);
            else if (param instanceof String)
                stmt.setString(n, (String)param);
            else if (param instanceof File)
                stmt.setBlob(n, new FileInputStream((File)param));
            else if (param instanceof Boolean)
                stmt.setBoolean(n, (Boolean)param);
            else
                throw new IllegalArgumentException("DatabaseQuery.setParam: invalid parameter type.");
        } catch (SQLException | FileNotFoundException e) {
            reportError(e.getMessage());
        }
    }
    
    
}
