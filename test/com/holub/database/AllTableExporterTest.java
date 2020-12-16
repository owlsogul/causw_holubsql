package com.holub.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AllTableExporterTest {

    Table[] testTables;

    @BeforeEach
    void setUp() {

        testTables = new Table[]{
                TableFactory.create("alltableexportertest_people", new String[]{"First", "Last"}),
                TableFactory.create("alltableexportertest_test", new String[]{"a", "b", "c"}),
        };
        testTables[0].insert(new String[]{"Jo", "Mingyu"});
        testTables[0].insert(new String[]{"Lee", "Jeahwan"});
        testTables[0].insert(new String[]{"Ggyu", "woo"});

        testTables[1].insert(new String[]{"a", "a", "b"});
        testTables[1].insert(new String[]{"b", "a", "b"});
        testTables[1].insert(new String[]{"b", "b", "b"});

    }

    @Test
    void visitTest() throws IOException {
        File testTargetFolder = new File("C://dp2020");
        TableVisitor visitor = new AllTableExporter(testTargetFolder);
        for (Table table : testTables) {
            table.accept(visitor);

            Writer writer = new StringWriter();
            table.export(new HtmlExporter(writer));

            FileReader fr = new FileReader(new File(testTargetFolder, table.name() + ".html"));
            BufferedReader br = new BufferedReader(fr);

            StringBuilder builder = new StringBuilder();
            String readLine = null;
            while( (readLine = br.readLine()) != null){
                builder.append(readLine);
            }
            assertEquals(writer.toString(), builder.toString());
            writer.close();
            br.close();
            fr.close();
        }

    }
}