package org.sf.badassql;

public class RegexBadasSQLMatcher implements BadasSQLMatcher {

    private String regex = BadasSQLOptions.getProperty(BadasSQLOptions.REGEX1);

    @Override
    public boolean match(String sql) {
        if (sql == null || regex == null)
            return false;

        if (sql.matches(regex))
            return true;
        else
            return false;
    }
}
