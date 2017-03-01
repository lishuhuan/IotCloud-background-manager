package com.nbicc.ita.util;

import java.util.List;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.nbicc.ita.model.SerialPortData;

public class WordUpload {
	
	// word文档  
    private Dispatch doc;  
  
    // word运行程序对象  
    private ActiveXComponent word;  
  
    // 所有word文档集合  
    private Dispatch documents;  
  
    // 选定的范围或插入点  
    private Dispatch selection;  
    
    private boolean saveOnExit = true; 
    
    public WordUpload(boolean visible) throws Exception {  
        if (word == null) {  
            word = new ActiveXComponent("Word.Application");  
            word.setProperty("Visible", new Variant(visible)); // 不可见打开word  
            word.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏  
        }  
        if (documents == null)  
            documents = word.getProperty("Documents").toDispatch();  
    }  
    
    public void closeDocument(int val) {  
        Dispatch.call(doc, "Close", new Variant(val));  
        doc = null;  
    }  
    
    public void closeDocument() {  
        if (doc != null) {  
            Dispatch.call(doc, "Save");  
            Dispatch.call(doc, "Close", new Variant(saveOnExit));  
            doc = null;  
        }  
    }  
    
    public void close() {  
        // closeDocument();  
        if (word != null) {  
            Dispatch.call(word, "Quit");  
            word = null;  
        }  
        selection = null;  
        documents = null;  
    } 
    
    public void putTxtToCell(int tableIndex, int cellRowIdx, int cellColIdx,  
            String txt) {  
        // 所有表格  
        Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();  
        // 要填充的表格  
        Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))  
                .toDispatch();  
        Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),  
                new Variant(cellColIdx)).toDispatch();  
        Dispatch.call(cell, "Select");  
        Dispatch.put(selection, "Text", txt);  
    }  
    
    public void openDocument(String docPath,String name,List<SerialPortData> list) {
        closeDocument();  
        doc = Dispatch.call(documents, "Open", docPath).toDispatch();  
        selection = Dispatch.get(word, "Selection").toDispatch();
        replaceAllText("productName", name);
        for(int i=0;i<list.size();i++){
        	int type=Integer.parseInt(list.get(i).getFormatRule());
        	String mptype=getPropertyType(type);
        	addRow(12);
        	putTxtToCell(12, i+2, 1, list.get(i).getTagName());
        	putTxtToCell(12, i+2, 2, mptype);
        	putTxtToCell(12, i+2, 3, list.get(i).getFormatRule());
        	putTxtToCell(12, i+2, 4, list.get(i).getChName());
        	if(list.get(i).getWrFlag()==1){
        		addRow(14);
        		putTxtToCell(14, i+2, 1, list.get(i).getTagName());
            	putTxtToCell(14, i+2, 2, mptype);
            	putTxtToCell(14, i+2, 3, list.get(i).getFormatRule());
            	putTxtToCell(14, i+2, 4, list.get(i).getChName());
        	}
        }
        save("D:\\360Downloads\\DesktopApp\\极动云-"+name+"串口通讯协议(v1.0).docx");
        closeDocumentWithoutSave();
        close();
    }
    
    public void addRow(int tableIndex) {  
        Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();  
        // 要填充的表格  
        Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))  
                .toDispatch();  
        // 表格的所有行  
        Dispatch rows = Dispatch.get(table, "Rows").toDispatch();  
        Dispatch.call(rows, "Add");  
    }
    
    public void save(String savePath) {  
        Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(),  
                "FileSaveAs", savePath);  
    }  
    
    public void closeDocumentWithoutSave() {  
        if (doc != null) {  
            Dispatch.call(doc, "Close", new Variant(false));  
            doc = null;  
        }  
    }
    
    public String getPropertyType(int type){
    	String result=null;
    	switch (type) {
		case 0:
			result="布尔型";
			break;
		case 1:
			result="枚举";
			break;
		case 2:
			result="小数";
			break;
		case 3:
			result="有符号整型";
			break;
		case 4:
			result="无符号整型";
			break;
		case 5:
			result="字符型";
			break;
		case 6:
			result="时间";
			break;

		default:
			result="自定义";
			break;
		}
    	return result;
    }
    
    /** 
     * 全局替换文本 
     *  
     * @param toFindText 
     *            查找字符串 
     * @param newText 
     *            要替换的内容 
     */  
    public void replaceAllText(String toFindText, String newText) {  
        while (find(toFindText)) {  
            Dispatch.put(selection, "Text", newText);  
            Dispatch.call(selection, "MoveRight");  
        }  
    }  
    
    @SuppressWarnings("static-access")  
    public boolean find(String toFindText) {  
        if (toFindText == null || toFindText.equals(""))  
            return false;  
        // 从selection所在位置开始查询  
        Dispatch find = word.call(selection, "Find").toDispatch();  
        // 设置要查找的内容  
        Dispatch.put(find, "Text", toFindText);  
        // 向前查找  
        Dispatch.put(find, "Forward", "True");  
        // 设置格式  
        Dispatch.put(find, "Format", "True");  
        // 大小写匹配  
        Dispatch.put(find, "MatchCase", "True");  
        // 全字匹配  
        Dispatch.put(find, "MatchWholeWord", "True");  
        // 查找并选中  
        return Dispatch.call(find, "Execute").getBoolean();  
    }  

}
