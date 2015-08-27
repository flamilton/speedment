package com.speedment.core.config.dbms;

import com.speedment.api.Speedment;
import com.speedment.api.config.Dbms;
import com.speedment.api.config.parameters.DbmsType;
import com.speedment.core.db.DbmsHandler;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

/**
 *
 * @author pemi
 */
public class AbstractDbmsType implements DbmsType {
    
    private final String name;
    private final String driverManagerName;
    private final int defaultPort;
    private final String schemaTableDelimiter;
    private final String dbmsNameMeaning;
    private final String driverName;
    private final String defaultConnectorParameters;
    private final String jdbcConnectorName;
    private final String fieldEncloserStart;
    private final String fieldEncloserEnd;
    private final Set<String> schemaExcludeSet;
    private final BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper;

    protected AbstractDbmsType(
        String name, 
        String driverManagerName, 
        int defaultPort, 
        String schemaTableDelimiter, 
        String dbmsNameMeaning, 
        String driverName, 
        String defaultConnectorParameters, 
        String jdbcConnectorName, 
        String fieldEncloserStart, 
        String fieldEncloserEnd, 
        Set<String> schemaExcludeSet, 
        BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper) {
        
        this.name = name;
        this.driverManagerName = driverManagerName;
        this.defaultPort = defaultPort;
        this.schemaTableDelimiter = schemaTableDelimiter;
        this.dbmsNameMeaning = dbmsNameMeaning;
        this.driverName = driverName;
        this.defaultConnectorParameters = defaultConnectorParameters;
        this.jdbcConnectorName = jdbcConnectorName;
        this.fieldEncloserStart = fieldEncloserStart;
        this.fieldEncloserEnd = fieldEncloserEnd;
        this.schemaExcludeSet = schemaExcludeSet;
        this.dbmsMapper = dbmsMapper;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDriverManagerName() {
        return driverManagerName;
    }

    @Override
    public int getDefaultPort() {
        return defaultPort;
    }

    @Override
    public String getSchemaTableDelimiter() {
        return schemaTableDelimiter;
    }

    @Override
    public String getDriverName() {
        return driverName;
    }

    @Override
    public String getJdbcConnectorName() {
        return jdbcConnectorName;
    }

    @Override
    public String getFieldEncloserStart() {
        return fieldEncloserStart;
    }

    @Override
    public String getFieldEncloserEnd() {
        return fieldEncloserEnd;
    }
    
    @Override
    public String getDbmsNameMeaning() {
        return dbmsNameMeaning;
    }

    @Override
    public Optional<String> getDefaultConnectorParameters() {
        return Optional.ofNullable(defaultConnectorParameters);
    }

    @Override
    public String getFieldEncloserStart(boolean isWithinQuotes) {
        return escapeIfQuote(getFieldEncloserStart(), isWithinQuotes);
    }

    @Override
    public String getFieldEncloserEnd(boolean isWithinQuotes) {
        return escapeIfQuote(getFieldEncloserEnd(), isWithinQuotes);
    }

    @Override
    public Set<String> getSchemaExcludeSet() {
        return schemaExcludeSet;
    }

    @Override
    public DbmsHandler makeDbmsHandler(Speedment speedment, Dbms dbms) {
        return dbmsMapper.apply(speedment, dbms);
    }

    @Override
    public boolean isSupported() {
        return true;
    }
    
    private String escapeIfQuote(String item, boolean isWithinQuotes) {
        if (isWithinQuotes && "\"".equals(item)) {
            return "\\" + item;
        }
        return item;
    }
}