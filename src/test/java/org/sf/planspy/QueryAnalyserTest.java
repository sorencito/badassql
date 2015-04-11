package org.sf.planspy;

import org.junit.Test;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertFalse;

public class QueryAnalyserTest {

    @Test
    public void test_empty_query_information_doesnt_produce_plan() {
        QueryAnalyser analyser = new QueryAnalyser(new QueryInformation(getDummyConnection(), ""));
        analyser.run();

        assertFalse(analyser.hasPlanBeenProduced());
    }

    private Connection getDummyConnection() {
        return new Connection() {

            @Override
            public <T> T unwrap(Class<T> iface) throws SQLException {
                return null;
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) throws SQLException {
                return false;
            }

            @Override
            public Statement createStatement() throws SQLException {
                return new Statement() {
                    @Override
                    public ResultSet executeQuery(String sql) throws SQLException {
                        return null;
                    }

                    @Override
                    public int executeUpdate(String sql) throws SQLException {
                        return 0;
                    }

                    @Override
                    public void close() throws SQLException {

                    }

                    @Override
                    public int getMaxFieldSize() throws SQLException {
                        return 0;
                    }

                    @Override
                    public void setMaxFieldSize(int max) throws SQLException {

                    }

                    @Override
                    public int getMaxRows() throws SQLException {
                        return 0;
                    }

                    @Override
                    public void setMaxRows(int max) throws SQLException {

                    }

                    @Override
                    public void setEscapeProcessing(boolean enable) throws SQLException {

                    }

                    @Override
                    public int getQueryTimeout() throws SQLException {
                        return 0;
                    }

                    @Override
                    public void setQueryTimeout(int seconds) throws SQLException {

                    }

                    @Override
                    public void cancel() throws SQLException {

                    }

                    @Override
                    public SQLWarning getWarnings() throws SQLException {
                        return null;
                    }

                    @Override
                    public void clearWarnings() throws SQLException {

                    }

                    @Override
                    public void setCursorName(String name) throws SQLException {

                    }

                    @Override
                    public boolean execute(String sql) throws SQLException {
                        return false;
                    }

                    @Override
                    public ResultSet getResultSet() throws SQLException {
                        return null;
                    }

                    @Override
                    public int getUpdateCount() throws SQLException {
                        return 0;
                    }

                    @Override
                    public boolean getMoreResults() throws SQLException {
                        return false;
                    }

                    @Override
                    public int getFetchDirection() throws SQLException {
                        return 0;
                    }

                    @Override
                    public void setFetchDirection(int direction) throws SQLException {

                    }

                    @Override
                    public int getFetchSize() throws SQLException {
                        return 0;
                    }

                    @Override
                    public void setFetchSize(int rows) throws SQLException {

                    }

                    @Override
                    public int getResultSetConcurrency() throws SQLException {
                        return 0;
                    }

                    @Override
                    public int getResultSetType() throws SQLException {
                        return 0;
                    }

                    @Override
                    public void addBatch(String sql) throws SQLException {

                    }

                    @Override
                    public void clearBatch() throws SQLException {

                    }

                    @Override
                    public int[] executeBatch() throws SQLException {
                        return new int[0];
                    }

                    @Override
                    public Connection getConnection() throws SQLException {
                        return null;
                    }

                    @Override
                    public boolean getMoreResults(int current) throws SQLException {
                        return false;
                    }

                    @Override
                    public ResultSet getGeneratedKeys() throws SQLException {
                        return null;
                    }

                    @Override
                    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
                        return 0;
                    }

                    @Override
                    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
                        return 0;
                    }

                    @Override
                    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
                        return 0;
                    }

                    @Override
                    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
                        return false;
                    }

                    @Override
                    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
                        return false;
                    }

                    @Override
                    public boolean execute(String sql, String[] columnNames) throws SQLException {
                        return false;
                    }

                    @Override
                    public int getResultSetHoldability() throws SQLException {
                        return 0;
                    }

                    @Override
                    public boolean isClosed() throws SQLException {
                        return false;
                    }

                    @Override
                    public boolean isPoolable() throws SQLException {
                        return false;
                    }

                    @Override
                    public void setPoolable(boolean poolable) throws SQLException {

                    }

                    @Override
                    public void closeOnCompletion() throws SQLException {

                    }

                    @Override
                    public boolean isCloseOnCompletion() throws SQLException {
                        return false;
                    }

                    @Override
                    public <T> T unwrap(Class<T> iface) throws SQLException {
                        return null;
                    }

                    @Override
                    public boolean isWrapperFor(Class<?> iface) throws SQLException {
                        return false;
                    }
                };
            }

            @Override
            public PreparedStatement prepareStatement(String sql) throws SQLException {
                return null;
            }

            @Override
            public CallableStatement prepareCall(String sql) throws SQLException {
                return null;
            }

            @Override
            public String nativeSQL(String sql) throws SQLException {
                return null;
            }

            @Override
            public boolean getAutoCommit() throws SQLException {
                return false;
            }

            @Override
            public void setAutoCommit(boolean autoCommit) throws SQLException {

            }

            @Override
            public void commit() throws SQLException {

            }

            @Override
            public void rollback() throws SQLException {

            }

            @Override
            public void close() throws SQLException {

            }

            @Override
            public boolean isClosed() throws SQLException {
                return false;
            }

            @Override
            public DatabaseMetaData getMetaData() throws SQLException {
                return null;
            }

            @Override
            public boolean isReadOnly() throws SQLException {
                return false;
            }

            @Override
            public void setReadOnly(boolean readOnly) throws SQLException {

            }

            @Override
            public String getCatalog() throws SQLException {
                return null;
            }

            @Override
            public void setCatalog(String catalog) throws SQLException {

            }

            @Override
            public int getTransactionIsolation() throws SQLException {
                return 0;
            }

            @Override
            public void setTransactionIsolation(int level) throws SQLException {

            }

            @Override
            public SQLWarning getWarnings() throws SQLException {
                return null;
            }

            @Override
            public void clearWarnings() throws SQLException {

            }

            @Override
            public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
                return null;
            }

            @Override
            public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
                return null;
            }

            @Override
            public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
                return null;
            }

            @Override
            public Map<String, Class<?>> getTypeMap() throws SQLException {
                return null;
            }

            @Override
            public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

            }

            @Override
            public int getHoldability() throws SQLException {
                return 0;
            }

            @Override
            public void setHoldability(int holdability) throws SQLException {

            }

            @Override
            public Savepoint setSavepoint() throws SQLException {
                return null;
            }

            @Override
            public Savepoint setSavepoint(String name) throws SQLException {
                return null;
            }

            @Override
            public void rollback(Savepoint savepoint) throws SQLException {

            }

            @Override
            public void releaseSavepoint(Savepoint savepoint) throws SQLException {

            }

            @Override
            public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
                return null;
            }

            @Override
            public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
                return null;
            }

            @Override
            public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
                return null;
            }

            @Override
            public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
                return null;
            }

            @Override
            public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
                return null;
            }

            @Override
            public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
                return null;
            }

            @Override
            public Clob createClob() throws SQLException {
                return null;
            }

            @Override
            public Blob createBlob() throws SQLException {
                return null;
            }

            @Override
            public NClob createNClob() throws SQLException {
                return null;
            }

            @Override
            public SQLXML createSQLXML() throws SQLException {
                return null;
            }

            @Override
            public boolean isValid(int timeout) throws SQLException {
                return false;
            }

            @Override
            public void setClientInfo(String name, String value) throws SQLClientInfoException {

            }

            @Override
            public String getClientInfo(String name) throws SQLException {
                return null;
            }

            @Override
            public Properties getClientInfo() throws SQLException {
                return null;
            }

            @Override
            public void setClientInfo(Properties properties) throws SQLClientInfoException {

            }

            @Override
            public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
                return null;
            }

            @Override
            public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
                return null;
            }

            @Override
            public String getSchema() throws SQLException {
                return null;
            }

            @Override
            public void setSchema(String schema) throws SQLException {

            }

            @Override
            public void abort(Executor executor) throws SQLException {

            }

            @Override
            public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

            }

            @Override
            public int getNetworkTimeout() throws SQLException {
                return 0;
            }
        };
    }
}