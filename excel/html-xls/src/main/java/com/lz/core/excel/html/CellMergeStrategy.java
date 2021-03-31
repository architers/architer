package com.lz.core.excel.html;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.lz.core.excel.html.entity.XlsCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * @author luyi
 * 合并策略
 */
public class CellMergeStrategy implements CellWriteHandler {

    private List<List<XlsCell>> xlsCellList;

    public CellMergeStrategy(List<List<XlsCell>> xlsCellList) {
        this.xlsCellList = xlsCellList;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        xlsCellList.forEach(xlsCells -> {
            xlsCells.forEach(xlsCell -> {
                if (xlsCell.getColspan() > 1 || xlsCell.getRowspan() > 1) {
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(xlsCell.getStartRow(), xlsCell.getEndRow(), xlsCell.getStartCol(), xlsCell.getEndCol());
                    writeSheetHolder.getSheet().addMergedRegion(cellRangeAddress);
                }
            });
        });
    }
}
