package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class XMLExporter extends SectionExporter{

    private final Writer writer;
    private String version, charset;
    public XMLExporter(Writer writer){ this(writer, "1.0", "UTF-8"); }
    public XMLExporter(Writer writer, String version, String charset){
        this.writer = writer; this.version = version; this.charset = charset;
    }

    private void storeArray(String label, Iterator data) throws IOException {
        writer.write(String.format("<%s>", label));
        while(data.hasNext()){
            Object datum = data.next();
            writer.write("<item>" + datum.toString() + "</item>");
        }
    }

    @Override
    public void onStart() throws IOException {
        writer.write(String.format("<?xml version=\"%s\" charset=\"%s\"?>", version, charset));
        writer.write("<table>");
    }

    @Override
    public void onMeta(String tableName, int width, int height, Iterator columnNames) throws IOException {
        writer.write(
                "<meta>" +
                        "<tableName>" + tableName + "</tableName>"
        );
        storeArray("col", columnNames);
        writer.write("</meta>");
    }

    @Override
    public void onDataStart() throws IOException {
        writer.write("<data>");
    }

    @Override
    public void onDataRow(Iterator data) throws IOException {
        storeArray("row", data);
    }

    @Override
    public void onDataEnd() throws IOException {
        writer.write("</data>");
    }

    @Override
    public void onEnd() throws IOException {
        writer.write("</table>");
    }
}
