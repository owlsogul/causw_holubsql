package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class XMLExporterTest {

    private Table people;
    private String[] col, row1, row2, row3;

    @BeforeEach
    public void init(){
        col = new String[]{ "First", "Last"	};
        row1 = new String[]{ "Allen",	"Holub" };
        row2 = new String[]{ "Ichabod",	"Crane" };
        row3 = new String[]{ "Rip",		"VanWinkle"};
        people = TableFactory.create( "people", col);
        people.insert( row1 );
        people.insert( row2 );
        people.insert( row3 );
    }

    @Test
    void onStart() throws IOException {
        Writer writer = new StringWriter();
        XMLExporter tableBuilder = new XMLExporter(writer);
        tableBuilder.onStart();
        assertEquals(writer.toString(), "<?xml version=\"1.0\" encoding=\"UTF-8\"?><table>");
    }

    @Test
    void onMeta() throws IOException {
        Writer writer = new StringWriter();
        XMLExporter tableBuilder = new XMLExporter(writer);
        tableBuilder.onMeta("people", 2, 3, new ArrayIterator(col));
        assertEquals(writer.toString(), "<meta><tableName>people</tableName><col><item>First</item><item>Last</item></col></meta>");
    }

    @Test
    void onDataStart() throws IOException {
        Writer writer = new StringWriter();
        XMLExporter tableBuilder = new XMLExporter(writer);
        tableBuilder.onDataStart();
        assertEquals(writer.toString(), "<data>");
    }

    @Test
    void onDataRow() throws IOException {
        Writer writer = new StringWriter();
        XMLExporter tableBuilder = new XMLExporter(writer);
        tableBuilder.onDataRow(new ArrayIterator(row1));
        assertEquals(writer.toString(), "<row><item>Allen</item><item>Holub</item></row>");
    }

    @Test
    void onDataEnd() throws IOException {
        Writer writer = new StringWriter();
        XMLExporter tableBuilder = new XMLExporter(writer);
        tableBuilder.onDataEnd();
        assertEquals(writer.toString(), "</data>");
    }

    @Test
    void onEnd() throws IOException {
        Writer writer = new StringWriter();
        XMLExporter tableBuilder = new XMLExporter(writer);
        tableBuilder.onEnd();
        assertEquals(writer.toString(), "</table>");
    }

    @Test
    void exportTest() throws IOException {
        File outDir = new File("C://dp2020");
        File output = new File(outDir, "xml_export.xml");
        if (!output.exists()) output.createNewFile();
        Writer writer = new FileWriter(output);
        XMLExporter exporter = new XMLExporter(writer);
        System.out.println(people);
        people.export(exporter);
        writer.flush();
        writer.close();
    }
}