package com.holub.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 */
public class AllTableExporter implements TableVisitor {

    private File parentDir;
    public AllTableExporter(File parentDir){
        this.parentDir = parentDir;
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    @Override
    public void visit(Table table) {

        String name = table.name();
        System.out.println(table);
        File file = new File(parentDir, name + ".html");
        try {
            FileWriter writer = new FileWriter(file);
            Table.Exporter exporter = new HtmlExporter(writer);
            table.export(exporter);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Table testTables[] = {
                TableFactory.create("people", new String[]{"First", "Last"}),
                TableFactory.create("test", new String[]{"a", "b", "c"}),
        };
        testTables[0].insert(new String[] { "Jo", "Mingyu" });
        testTables[0].insert(new String[] { "Lee", "Jeahwan" });
        testTables[0].insert(new String[] { "Ggyu", "woo" });

        testTables[1].insert(new String[] {"a", "a", "b"});
        testTables[1].insert(new String[] {"b", "a", "b"});
        testTables[1].insert(new String[] {"b", "b", "b"});

        TableVisitor visitor = new AllTableExporter(new File("W://out"));
        for (Table table : testTables){
            table.accept(visitor);
        }

    }

}
