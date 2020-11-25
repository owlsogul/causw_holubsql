package com.holub.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Table people = TableFactory.create( "people", col);
        people.insert( row1 );
        people.insert( row2 );
        people.insert( row3 );
    }

    @Test
    void onStart() {

    }

    @Test
    void onMeta() {
    }

    @Test
    void onDataStart() {
    }

    @Test
    void onDataRow() {
    }

    @Test
    void onDataEnd() {
    }

    @Test
    void onEnd() {
    }
}