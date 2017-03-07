package org.sf.planspy.h2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sf.planspy.PlanSpyFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class H2Test {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private Connection conn;

    @Before
    public void openConnectionAndRedirectStreams() throws ClassNotFoundException, SQLException {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        Class.forName("com.p6spy.engine.spy.P6SpyDriver");
        conn = DriverManager.getConnection("jdbc:p6spy:h2:mem:", "sa", "");

        prepareDatabase();

    }

    private void prepareDatabase() throws SQLException {
        Statement stmt = conn.createStatement();

        createFunctions(stmt);

        createTables(stmt);

        insertData(stmt);

        stmt.close();
    }

    private void createFunctions(Statement stmt) throws SQLException {
        stmt.execute("-- TO_DATE \n" +
                "drop ALIAS if exists TO_DATE; \n" +
                "CREATE ALIAS TO_DATE as '\n" +
                "import java.text.*;\n" +
                "@CODE\n" +
                "java.util.Date toDate(String s, String dateFormat) throws Exception { \n" +
                "  return new SimpleDateFormat(dateFormat).parse(s); \n" +
                "} \n" +
                "';");
    }


    @After
    public void closeConnectionAndCleanUpStreams() throws SQLException {
        System.setOut(null);
        System.setErr(null);

        if (conn != null) {
            conn.close();
        }
    }

    @Test
    public void generateSimpleExecutionPlanSelect() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeQuery("select * from dept, emp");
        stmt.close();
        assertThat("key word not found", outContent.toString(), containsString("tableScan"));
    }

    @Test
    public void generatePreparedStatementExecutionPlanSelect() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select * from dept, emp where rownum > " + "?");
        stmt.setInt(1, 0);
        ResultSet set = stmt.executeQuery();
        stmt.close();
        assertThat("key word not found", outContent.toString(), containsString("tableScan"));
    }

    private void insertData(Statement stmt) throws SQLException {
        stmt.executeUpdate("insert into dept\n" +
                "values(10, 'ACCOUNTING', 'NEW YORK');\n");
        stmt.executeUpdate("insert into dept\n" +
                "values(20, 'RESEARCH', 'DALLAS');\n");
        stmt.executeUpdate("insert into dept\n" +
                "values(30, 'SALES', 'CHICAGO');\n");
        stmt.executeUpdate("insert into dept\n" +
                "values(40, 'OPERATIONS', 'BOSTON');\n" +
                " \n");

        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7839, 'KING', 'PRESIDENT', null,\n" +
                " to_date('17-11-1981','dd-mm-yyyy'),\n" +
                " 5000, null, 10\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7698, 'BLAKE', 'MANAGER', 7839,\n" +
                " to_date('1-5-1981','dd-mm-yyyy'),\n" +
                " 2850, null, 30\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7782, 'CLARK', 'MANAGER', 7839,\n" +
                " to_date('9-6-1981','dd-mm-yyyy'),\n" +
                " 2450, null, 10\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7566, 'JONES', 'MANAGER', 7839,\n" +
                " to_date('2-4-1981','dd-mm-yyyy'),\n" +
                " 2975, null, 20\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7788, 'SCOTT', 'ANALYST', 7566,\n" +
                " to_date('13-07-1987','dd-mm-yyyy') - 85,\n" +
                " 3000, null, 20\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7902, 'FORD', 'ANALYST', 7566,\n" +
                " to_date('3-12-1981','dd-mm-yyyy'),\n" +
                " 3000, null, 20\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7369, 'SMITH', 'CLERK', 7902,\n" +
                " to_date('17-12-1980','dd-mm-yyyy'),\n" +
                " 800, null, 20\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7499, 'ALLEN', 'SALESMAN', 7698,\n" +
                " to_date('20-2-1981','dd-mm-yyyy'),\n" +
                " 1600, 300, 30\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7521, 'WARD', 'SALESMAN', 7698,\n" +
                " to_date('22-2-1981','dd-mm-yyyy'),\n" +
                " 1250, 500, 30\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7654, 'MARTIN', 'SALESMAN', 7698,\n" +
                " to_date('28-9-1981','dd-mm-yyyy'),\n" +
                " 1250, 1400, 30\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7844, 'TURNER', 'SALESMAN', 7698,\n" +
                " to_date('8-9-1981','dd-mm-yyyy'),\n" +
                " 1500, 0, 30\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7876, 'ADAMS', 'CLERK', 7788,\n" +
                " to_date('13-07-1987', 'dd-mm-yyyy') - 51,\n" +
                " 1100, null, 20\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7900, 'JAMES', 'CLERK', 7698,\n" +
                " to_date('3-12-1981','dd-mm-yyyy'),\n" +
                " 950, null, 30\n" +
                ");\n");
        stmt.executeUpdate("insert into emp\n" +
                "values(\n" +
                " 7934, 'MILLER', 'CLERK', 7782,\n" +
                " to_date('23-1-1982','dd-mm-yyyy'),\n" +
                " 1300, null, 10\n" +
                ");\n");
    }

    private void createTables(Statement stmt) throws SQLException {
        stmt.execute("create table dept(\n" +
                "  deptno number(2,0),\n" +
                "  dname  varchar2(14),\n" +
                "  loc    varchar2(13),\n" +
                "  constraint pk_dept primary key (deptno)\n" +
                ");\n" +
                " \n");
        stmt.execute("create table emp(\n" +
                "  empno    number(4,0),\n" +
                "  ename    varchar2(10),\n" +
                "  job      varchar2(9),\n" +
                "  mgr      number(4,0),\n" +
                "  hiredate date,\n" +
                "  sal      number(7,2),\n" +
                "  comm     number(7,2),\n" +
                "  deptno   number(2,0),\n" +
                "  constraint pk_emp primary key (empno),\n" +
                "  constraint fk_deptno foreign key (deptno) references dept (deptno)\n" +
                ");");
    }
}