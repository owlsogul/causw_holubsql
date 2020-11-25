package com.holub.database;

import java.io.IOException;
import java.util.Iterator;

/**
 *  섹션 별로 나누기 위한 클래스.
 *  startTable, storeMetadata, storeRow, endTable 순으로 export 된다는 가정하에
 *  현재 상태를 통해 메타데이터 섹션, 데이터 섹션으로 나누어 작업하기 위한 클래스
 *  주의) 기존에 존재하는 Table.Exporter 인터페이스에 맞춰 제작되어,
 *  startTable, endTable이 실질적으로 영역을 의미하지않을 수 있다.
 */
public abstract class SectionExporter implements Table.Exporter{

    /**
     * 로우의 인덱스를 추적하는 변수
     *  현재 상태를 통해 메타데이터 섹션, 데이터 섹션으로 나누기 위한 변수.
     */
    private int rowIdx = -1;

    @Override
    public final void startTable() throws IOException {
        rowIdx = -1;
        onStart();
    }

    @Override
    public final void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        rowIdx = -1;
        onMeta(tableName, width, height, columnNames);
    }

    @Override
    public final void storeRow(Iterator data) throws IOException {
        if (rowIdx == -1) onDataStart();
        onDataRow(data);
        rowIdx++;
    }

    @Override
    public final void endTable() throws IOException {
        // data가 하나도 없을 경우에는 영역 인디케이터만 표시
        if (rowIdx == -1) {
            onDataStart();
            onDataEnd();
        }
        else {
            onDataEnd();
        }
        onEnd();
        rowIdx = -1;
    }
    
    public abstract void onStart() throws IOException;
    public abstract void onMeta(String tableName, int width, int height, Iterator columnNames) throws IOException;
    public abstract void onDataStart() throws IOException;
    public abstract void onDataRow(Iterator data) throws IOException;
    public abstract void onDataEnd() throws IOException;
    public abstract void onEnd() throws IOException;
    
}
