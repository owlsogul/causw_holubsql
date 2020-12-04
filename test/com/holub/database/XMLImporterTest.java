package com.holub.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.*;

class XMLImporterTest {

    Table testTable;
    Table importedTable;
    private String tableName;
    private String[] col, row1, row2, row3;

    @BeforeEach
    void setUp() throws IOException, ParserConfigurationException, SAXException {
        col = new String[]{ "First", "Last"	};
        row1 = new String[]{ "Allen",	"Holub" };
        row2 = new String[]{ "Ichabod",	"Crane" };
        row3 = new String[]{ "Rip",		"VanWinkle"};
        tableName = "people";
        testTable = TableFactory.create( tableName, col);
        testTable.insert( row1 );
        testTable.insert( row2 );
        testTable.insert( row3 );

        File outDir = new File("C://dp2020");
        File output = new File(outDir, "xml_export.xml");
        if (!output.exists()) output.createNewFile();
        Writer writer = new FileWriter(output);
        XMLExporter exporter = new XMLExporter(writer);
        testTable.export(exporter);
        writer.close();

        Table.Importer importer = new XMLImporter(output);
        importedTable = TableFactory.create(importer);
    }

    @Test
    void importTest() throws IOException {
        assertEquals(importedTable.toString(), testTable.toString());
    }
}