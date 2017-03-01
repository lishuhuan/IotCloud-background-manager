package com.nbicc.ita.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import com.nbicc.ita.model.Brand;
import com.nbicc.ita.model.SerialPortData;
import com.sun.enterprise.module.ModuleMetadata.Entry;

import static com.nbicc.ita.constant.ParameterKeys.WORD_PATH;

public class PoiXwpf {

	public boolean wordOperation(String docPath, String name, List<SerialPortData> list,String key) throws Exception {
		XWPFDocument xdoc = openDocument(docPath);
		replaceWord(xdoc, "productName", "极动云-" + name + "串口通讯协议(v1.0)");
		XWPFTable table = getTableByIndex(xdoc, 13);
		XWPFTable tablewr = getTableByIndex(xdoc, 15);
		int t=1;
		for (int i = 0; i < list.size(); i++) {
			int type = Integer.parseInt(list.get(i).getFormatType());
			String mptype = getPropertyType(type);
			insertTableRowAtIndex(table, i+1);
			table.getRow(i + 1).getCell(0).setText(list.get(i).getTagName());
			table.getRow(i + 1).getCell(1).setText(mptype);
			table.getRow(i + 1).getCell(2).setText(list.get(i).getFormatRule());
			table.getRow(i + 1).getCell(3).setText(list.get(i).getChName());
			if (list.get(i).getWrFlag() == 1) {
				insertTableRowAtIndex(tablewr, t);
				tablewr.getRow(t).getCell(0).setText(list.get(i).getTagName());
				tablewr.getRow(t).getCell(1).setText(mptype);
				tablewr.getRow(t).getCell(2).setText(list.get(i).getFormatRule());
				tablewr.getRow(t).getCell(3).setText(list.get(i).getChName());
				t++;
			}

		}

		saveDocument(xdoc, WORD_PATH+key+".docx");
		return true;
	}
	
	public boolean wordOperationBlueTooth(String docPath, String name, List<SerialPortData> list,String key) throws Exception {
		XWPFDocument xdoc = openDocument(docPath);
		replaceWord(xdoc, "productName", name + "蓝牙应用层协议");
		XWPFTable table = getTableByIndex(xdoc, 1);
		XWPFTable tablecon = getTableByIndex(xdoc, 3);
		XWPFTable tableque = getTableByIndex(xdoc, 5);
		int con=1;
		int que=1;
		for (int i = 0; i < list.size(); i++) {
			int type = Integer.parseInt(list.get(i).getFormatType());
			String mptype = getPropertyType(type);
			insertTableRowAtIndex(table, i+1);
			table.getRow(i + 1).getCell(0).setText(list.get(i).getTagName());
			table.getRow(i + 1).getCell(1).setText(mptype);
			table.getRow(i + 1).getCell(2).setText(list.get(i).getFormatRule());
			table.getRow(i + 1).getCell(3).setText(list.get(i).getChName());
			if (list.get(i).getWrFlag() == 1) {
				insertTableRowAtIndex(tablecon, con);
				tablecon.getRow(con).getCell(0).setText(list.get(i).getTagName());
				tablecon.getRow(con).getCell(1).setText(mptype);
				tablecon.getRow(con).getCell(2).setText(list.get(i).getFormatRule());
				tablecon.getRow(con).getCell(3).setText(list.get(i).getChName());
				con++;
			}
			else{
				insertTableRowAtIndex(tableque, que);
				tableque.getRow(que).getCell(0).setText(list.get(i).getTagName());
				tableque.getRow(que).getCell(1).setText(mptype);
				tableque.getRow(que).getCell(2).setText(list.get(i).getFormatRule());
				tableque.getRow(que).getCell(3).setText(list.get(i).getChName());
				que++;
			}

		}

		saveDocument(xdoc, WORD_PATH+key+".docx");
		return true;
	}
	
	public Boolean downloadLocal(HttpServletResponse response,Brand brand) throws FileNotFoundException, UnsupportedEncodingException {
        // 下载本地文件
		String name=brand.getProductName();
		String key=brand.getDeviceKey();
		String fname="极动云-"+name+"串口通讯协议(v1.0).docx";
		if(brand.getCommunicationProtocol()==2){
			fname=name+"蓝牙接入应用层协议.docx";
		}
        String fileName = fname.toString(); // 文件的默认保存名
        // 读到流中
        InputStream inStream = new FileInputStream(WORD_PATH+key+".docx");// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

	public Boolean replaceWord(XWPFDocument xdoc, String old, String newword) {

		List list = xdoc.getParagraphs();
		// 基本内容替换
		for (int i = 0; i < list.size(); i++) {

			if (((XWPFParagraph) list.get(i)).getParagraphText().contains(old)) {
				List runs = ((XWPFParagraph) list.get(i)).getRuns();
				// 删除原来内容
				for (int j = runs.size() - 1; j >= 0; j--) {
					((XWPFParagraph) list.get(i)).removeRun(j);
				}
				// 创建新内容
				XWPFRun paragraphRun = ((XWPFParagraph) list.get(i)).createRun();
				paragraphRun.setText(newword);
				/*System.out.println(((XWPFParagraph) list.get(i)).getParagraphText());*/
			}

		}
		return true;
	}

	/**
	 * @Description: 得到表格内容(第一次跨行单元格视为一个，第二次跳过跨行合并的单元格)
	 */
	public List<List<String>> getTableRContent(XWPFTable table) {
		List<List<String>> tableContentList = new ArrayList<List<String>>();
		for (int rowIndex = 0, rowLen = table.getNumberOfRows(); rowIndex < rowLen; rowIndex++) {
			XWPFTableRow row = table.getRow(rowIndex);
			List<String> cellContentList = new ArrayList<String>();
			for (int colIndex = 0, colLen = row.getTableCells().size(); colIndex < colLen; colIndex++) {
				XWPFTableCell cell = row.getCell(colIndex);
				CTTc ctTc = cell.getCTTc();
				if (ctTc.isSetTcPr()) {
					CTTcPr tcPr = ctTc.getTcPr();
					if (tcPr.isSetHMerge()) {
						CTHMerge hMerge = tcPr.getHMerge();
						if (STMerge.RESTART.equals(hMerge.getVal())) {
							cellContentList.add(getTableCellContent(cell));
						}
					} else if (tcPr.isSetVMerge()) {
						CTVMerge vMerge = tcPr.getVMerge();
						if (STMerge.RESTART.equals(vMerge.getVal())) {
							cellContentList.add(getTableCellContent(cell));
						}
					} else {
						cellContentList.add(getTableCellContent(cell));
					}
				}
			}
			tableContentList.add(cellContentList);
		}
		return tableContentList;
	}

	public String getTableCellContent(XWPFTableCell cell) {
		StringBuffer sb = new StringBuffer();
		List<XWPFParagraph> cellPList = cell.getParagraphs();
		if (cellPList != null && cellPList.size() > 0) {
			for (XWPFParagraph xwpfPr : cellPList) {
				List<XWPFRun> runs = xwpfPr.getRuns();
				if (runs != null && runs.size() > 0) {
					for (XWPFRun xwpfRun : runs) {
						sb.append(xwpfRun.getText(0));
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @Description: 得到单元格第一个Paragraph
	 */
	public XWPFParagraph getCellFirstParagraph(XWPFTableCell cell) {
		XWPFParagraph p;
		if (cell.getParagraphs() != null && cell.getParagraphs().size() > 0) {
			p = cell.getParagraphs().get(0);
		} else {
			p = cell.addParagraph();
		}
		return p;
	}

	/**
	 * @Description: 设置单元格内容
	 */
	public void setCellNewContent(XWPFTable table, int rowIndex, int col, String content) {
		XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
		XWPFParagraph p = getCellFirstParagraph(cell);
		List<XWPFRun> cellRunList = p.getRuns();
		if (cellRunList == null || cellRunList.size() == 0) {
			return;
		}
		for (int i = cellRunList.size() - 1; i >= 1; i--) {
			p.removeRun(i);
		}
		XWPFRun run = cellRunList.get(0);
		run.setText(content);
	}

	/**
	 * @Description: 打开word文档
	 */
	public XWPFDocument openDocument(String filePath) throws Exception {
		XWPFDocument xdoc = new XWPFDocument(POIXMLDocument.openPackage(filePath));
		return xdoc;
	}

	public XWPFTable getTableByIndex(XWPFDocument xdoc, int index) {
		List<XWPFTable> tablesList = getAllTable(xdoc);
		if (tablesList == null || index < 0 || index > tablesList.size()) {
			return null;
		}
		return tablesList.get(index);
	}

	public List<XWPFTable> getAllTable(XWPFDocument xdoc) {
		return xdoc.getTables();
	}

	/**
	 * @Description: 表格指定位置插入一行, index为新增行所在的行位置(不能大于表行数)
	 */
	public void insertTableRowAtIndex(XWPFTable table, int index) {
		XWPFTableRow firstRow = table.getRow(0);
		XWPFTableRow row = table.insertNewTableRow(index);
		if (row == null) {
			return;
		}
		CTTbl ctTbl = table.getCTTbl();
		CTTblGrid tblGrid = ctTbl.getTblGrid();
		int cellSize = 0;
		boolean isAdd = false;
		if (tblGrid != null) {
			List<CTTblGridCol> gridColList = tblGrid.getGridColList();
			if (gridColList != null && gridColList.size() > 0) {
				isAdd = true;
				for (CTTblGridCol ctlCol : gridColList) {
					XWPFTableCell cell = row.addNewTableCell();
					setCellWidthAndVAlign(cell, ctlCol.getW().toString(), STTblWidth.DXA, null);
				}
			}
		}
		// 大部分都不会走到这一步
		if (!isAdd) {
			cellSize = getCellSizeWithMergeNum(firstRow);
			for (int i = 0; i < cellSize; i++) {
				row.addNewTableCell();
			}
		}
	}

	/**
	 * 
	 * @Description: 得到Cell的CTTcPr,不存在则新建
	 */
	public CTTcPr getCellCTTcPr(XWPFTableCell cell) {
		CTTc cttc = cell.getCTTc();
		CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
		return tcPr;
	}

	/**
	 * @Description: 设置列宽和垂直对齐方式
	 */
	public void setCellWidthAndVAlign(XWPFTableCell cell, String width, STTblWidth.Enum typeEnum,
			STVerticalJc.Enum vAlign) {
		CTTcPr tcPr = getCellCTTcPr(cell);
		CTTblWidth tcw = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
		if (width != null) {
			tcw.setW(new BigInteger(width));
		}
		if (typeEnum != null) {
			tcw.setType(typeEnum);
		}
		if (vAlign != null) {
			CTVerticalJc vJc = tcPr.isSetVAlign() ? tcPr.getVAlign() : tcPr.addNewVAlign();
			vJc.setVal(vAlign);
		}
	}

	/**
	 * @Description: 统计列数(包括合并的列数)
	 */
	public int getCellSizeWithMergeNum(XWPFTableRow row) {
		List<XWPFTableCell> firstRowCellList = row.getTableCells();
		int cellSize = firstRowCellList.size();
		for (XWPFTableCell xwpfTableCell : firstRowCellList) {
			CTTc ctTc = xwpfTableCell.getCTTc();
			if (ctTc.isSetTcPr()) {
				CTTcPr tcPr = ctTc.getTcPr();
				if (tcPr.isSetGridSpan()) {
					CTDecimalNumber gridSpan = tcPr.getGridSpan();
					cellSize += gridSpan.getVal().intValue() - 1;
				}
			}
		}
		return cellSize;
	}

	public String getPropertyType(int type) {
		String result = null;
		switch (type) {
		case 0:
			result = "布尔型";
			break;
		case 1:
			result = "枚举";
			break;
		case 2:
			result = "小数";
			break;
		case 3:
			result = "有符号整型";
			break;
		case 4:
			result = "无符号整型";
			break;
		case 5:
			result = "字符型";
			break;
		case 6:
			result = "时间";
			break;

		default:
			result = "自定义";
			break;
		}
		return result;
	}

	/**
	 * @Description: 保存文档
	 */
	public void saveDocument(XWPFDocument document, String savePath) throws Exception {
		FileOutputStream fos = new FileOutputStream(savePath);
		document.write(fos);
		fos.close();
	}

}
