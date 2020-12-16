package com.holub.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteTableTest {

    private Table people;
    private String[] col, row1, row2, row3;

    @BeforeEach
    void setUp() {
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
    void testSelect(){

        List targetCol = new ArrayList();
        targetCol.add("*");

        List reqTable = new ArrayList();

        Table result = people.select(Selector.ALL, null, reqTable);
        assertEquals(result.rows().columnCount(), people.rows().columnCount());

    }

}