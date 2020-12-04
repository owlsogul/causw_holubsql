package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Iterator;

public class XMLImporter implements Table.Importer {

    private InputStream in;
    private Document xmlDoc;
    private Element root;
    private Element meta;

    private String tableName;
    private String[] columns;
    private int rowIdx = 0;

    public XMLImporter(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder docBuilder =DocumentBuilderFactory.newInstance().newDocumentBuilder();
        xmlDoc = docBuilder.parse(file);
        xmlDoc.getDocumentElement().normalize();
    }

    public XMLImporter(InputStream in) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder docBuilder =DocumentBuilderFactory.newInstance().newDocumentBuilder();
        xmlDoc = docBuilder.parse(in);
        xmlDoc.getDocumentElement().normalize();
    }

    @Override
    public void startTable() throws IOException {
        root = xmlDoc.getDocumentElement();
        meta = (Element) root.getElementsByTagName("meta").item(0);
        tableName = meta.getElementsByTagName("tableName").item(0).getTextContent();

        NodeList cols = ((Element)meta.getElementsByTagName("col").item(0)).getElementsByTagName("item");
        columns = new String[cols.getLength()];
        for (int idx = 0; idx < cols.getLength(); idx++){
            Node colNode = cols.item(idx);
            columns[idx] = colNode.getTextContent();
        }

    }

    @Override
    public String loadTableName() throws IOException {
        return tableName;
    }

    @Override
    public int loadWidth() throws IOException {
        return columns.length;
    }

    @Override
    public Iterator loadColumnNames() throws IOException {
        return new ArrayIterator(columns);
    }

    @Override
    public Iterator loadRow() throws IOException {

        Element data = (Element) root.getElementsByTagName("data").item(0);
        NodeList rows = data.getElementsByTagName("row");
        if (rowIdx < rows.getLength()){
            Element rowContainer = (Element) rows.item(rowIdx);
            NodeList valueContainer = rowContainer.getElementsByTagName("item");
            String[] values = new String[valueContainer.getLength()];
            for (int valueIdx = 0; valueIdx < valueContainer.getLength(); valueIdx++){
                values[valueIdx] = valueContainer.item(valueIdx).getTextContent();
            }
            rowIdx++;
            return new ArrayIterator(values);
        }
        return null;
    }

    @Override
    public void endTable() throws IOException {}
}
