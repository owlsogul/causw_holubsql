package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 *
 * Html Exporter.
 * HTML 형식으로 테이블을 Export 하는 클래스.
 *
 */
public class HtmlExporter extends SectionExporter{
    private final Writer out;
    private int width, height;

    private enum Type {
        HEADER("thead", "tr", "th"),
        BODY("tbody", "tr", "td");

        private final String wrapper, rowContainer, cellContainer;
        Type(String wrapper, String rowContainer, String cellContainer){
            this.wrapper = wrapper; this.rowContainer = rowContainer; this.cellContainer = cellContainer;
        }
    }

    public HtmlExporter( Writer out ) {
        this.out = out;
    }

    private void storeRowByType(Iterator data, Type type) throws IOException{
        StringBuilder rowStr = new StringBuilder();
        rowStr.append("<").append(type.rowContainer).append(">");
            while(data.hasNext()){
                Object datum = data.next();
                rowStr.append("<").append(type.cellContainer).append(">");
                rowStr.append(datum.toString());
                rowStr.append("</").append(type.cellContainer).append(">");
            }
        rowStr.append("</").append(type.rowContainer).append(">");
        out.write(rowStr.toString());
    }

    @Override
    public void onStart() throws IOException {
        out.write("<html><body><table>");
    }

    @Override
    public void onMeta(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.width = width;
        this.height = height;
        String tempTableName = tableName == null ? "" : tableName;
        StringBuilder headerStr = new StringBuilder();
        out.write(String.format("<caption>%s</caption>", tempTableName));
        out.write("<" + Type.HEADER.wrapper + ">");
        storeRowByType(columnNames, Type.HEADER);
        out.write("</" + Type.HEADER.wrapper + ">");
    }

    @Override
    public void onDataStart() throws IOException {
        out.write("<" + Type.BODY.wrapper + ">");
    }

    @Override
    public void onDataRow(Iterator data) throws IOException {
        storeRowByType(data, Type.BODY);
    }

    @Override
    public void onDataEnd() throws IOException {
        out.write("</" + Type.BODY.wrapper + ">");
    }

    @Override
    public void onEnd() throws IOException {
        out.write("</table></body></html>");
    }
}
