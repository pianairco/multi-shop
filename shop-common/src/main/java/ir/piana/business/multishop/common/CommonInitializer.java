package ir.piana.business.multishop.common;

import ir.piana.business.multishop.common.data.component.SpecificSchemaQueryExecutorProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

public abstract class CommonInitializer {
    protected SpecificSchemaQueryExecutorProvider queryExecutorProvider;

    public abstract InputStream getSupportSql();
    public abstract InputStream getAllSchemaSql();

    public void initData() {
        try (InputStream resourceAsStream = getSupportSql()) {
            if(resourceAsStream != null) {
                String[] split = new String[0];
                split = IOUtils.toString(resourceAsStream).split(";");

                for (String script : split) {
                    queryExecutorProvider.executeOnSupport(script);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try (InputStream resourceAsStream = getAllSchemaSql()) {
//            if(resourceAsStream != null) {
//                String[] split = new String[0];
//                split = IOUtils.toString(resourceAsStream).split(";");
//
//                for (String script : split) {
////                    queryExecutorProvider.executeOnAllDataSources(script);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
