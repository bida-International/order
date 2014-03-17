package com.demo.action;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.Iterator;
import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.dao.user.UserDao;
import com.demo.entity.user.UserInfo;

@Controller("importUserAction")
@Scope("prototype")
public class ImportUserAction extends BaseAction {
	@Resource
	private UserDao userDao;
	private File excelfile;
	public String msg;
	@Override
	public String execute() throws Exception {
		if(excelfile == null || excelfile.length() <= 0){
			msg = "请选择正确的excel文件 !";
			return "main";
		}
		
		HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(excelfile));
		HSSFSheet sheet = book.getSheetAt(0);
		Iterator<Row> it = sheet.rowIterator();
		it.next();
		int total = 0;
		while(it.hasNext()){
			Row row = it.next();
			Iterator<Cell> cells = row.cellIterator();
			int index = 0;
			UserInfo u = new UserInfo();
			while(cells.hasNext()){		
				Cell cell = cells.next();
				if(index == 0){
					u.setAdmin("是".equals(cell.getStringCellValue()));					
				}else if(index == 1){
					u.setName(cell.getStringCellValue());
				}else if(index == 2){
					u.setPwd(cell.getStringCellValue());
				}else if(index == 3){
					u.setQuanxian(Double.valueOf(cell.getNumericCellValue()).longValue());
				}else if(index == 4){
					u.setUsername(cell.getStringCellValue());
				}
				index++;
			}
			if(ht.findFirst("from UserInfo a where a.username = ?", new Object[]{u.getUsername()}) == null){
				userDao.merge(u);
				total++;
			}
		}
		msg = "累计导入" + total + "条记录！";
		return "main";
	}
	public File getExcelfile() {
		return excelfile;
	}
	public void setExcelfile(File excelfile) {
		this.excelfile = excelfile;
	}
}
