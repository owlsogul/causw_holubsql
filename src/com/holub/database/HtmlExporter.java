package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 *
 * TODO:
 *  1) docs 작성(저장되는 구조)
 *  2) 주석 작성. 1 항목과 비슷
 */
public class HtmlExporter implements Table.Exporter{
    private final Writer out;
    private int width, height;

    private enum Type {
        HEADER("thead", "tr", "th"),
        BODY("tbody", "tr", "td");

        private String wrapper, rowContainer, cellContainer;
        Type(String wrapper, String rowContainer, String cellContainer){
            this.wrapper = wrapper; this.rowContainer = rowContainer; this.cellContainer = cellContainer;
        }

        public String getWrapper() { return wrapper; }
        public String getRowContainer() { return rowContainer; }
        public String getCellContainer() { return cellContainer; }
    }

    public HtmlExporter( Writer out ) {
        this.out = out;
    }
    @Override
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.width = width;
        this.height = height;
        String tempTableName = tableName == null ? "" : tableName;
        out.write(String.format("<caption>%s</caption>", tempTableName));
        storeRowByType(columnNames, Type.HEADER);
    }

    public void storeRowByType(Iterator data, Type type) throws IOException{
        int idx = width;
        StringBuilder headerStr = new StringBuilder();
        headerStr.append("<").append(type.wrapper).append(">");
            headerStr.append("<").append(type.getRowContainer()).append(">");
                while(data.hasNext()){
                    Object datum = data.next();
                    headerStr.append("<").append(type.getCellContainer()).append(">");
                        headerStr.append(datum.toString());
                    headerStr.append("</").append(type.getCellContainer()).append(">");
                }
            headerStr.append("</").append(type.getRowContainer()).append(">");
        headerStr.append("</").append(type.wrapper).append(">");
        out.write(headerStr.toString());
    }


    @Override
    public void storeRow(Iterator data) throws IOException {
        storeRowByType(data, Type.BODY);
    }

    @Override public void startTable() throws IOException {
        out.write(
                "<html>" +
                        "<body>" +
                            "<table>"
        );
    }
    @Override public void endTable() throws IOException {
        out.write(
                        "</table>"+
                        "</body>" +
                    "</html>"
        );
    }
}
