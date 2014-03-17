
package com.demo.action;

import com.demo.dao.OrderTableDao;
import com.demo.entity.order.OrderTable;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@Controller("ceShiAction")
@Scope("prototype")
public class CeShiAction extends BaseAction implements ServletResponseAware
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;
    public List<OrderTable> orders;
    public String orderId;
    public String time;
    public String guoneidanhao;
    public Long dc;
    @Resource
    private OrderTableDao orderTableDao;
    public void setServletResponse(HttpServletResponse arg0)
    {
        response = arg0;
    }

    public InputStream getExcelFile()
    {
    	System.out.println("+++����+++");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet1");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        HSSFCellStyle cellstyle = workbook.createCellStyle();
        sheet.setColumnWidth(0, 1500);
        sheet.setColumnWidth(1, 2500);
        sheet.setColumnWidth(2, 1500);
        sheet.setColumnWidth(3, 1500);
        sheet.setColumnWidth(4, 3500);
        sheet.setColumnWidth(5, 8500);
        sheet.setColumnWidth(6, 3000);
     
        try
        {
            orders = orderTableDao.getDaoChu(dc);
            if (dc == 2) {
            
				for (int i = 0; i < orders.size(); i++) {
					
					  row = sheet.createRow(i);
		                row.setHeight((short)2750);
		                if (orders.get(i).getGuoneiwangzhanId() != null && !"".equals(orders.get(i).getGuoneiwangzhanId())){
		                	cteateCell(workbook, row, 0, getGuoNeiWangZhan(orders.get(i).getGuoneiwangzhanId().toString()));
						}
		                cteateCell(workbook, row, 1, orders.get(i).getWuping());
		                cteateCell(workbook, row, 2, orders.get(i).getGongyunshang());
		                cteateCell(workbook, row, 3, orders.get(i).getCaigoutime().toString());
		                cteateCell(workbook, row, 4, orders.get(i).getOrderId());
				}
			}else{
			
	            for(int i = 0; i < orders.size(); i++)
	            {
	                row = sheet.createRow(i);
	                row.setHeight((short)2750);
	                if(orders.get(i).getZhanghaoId() != null && !"".equals(orders.get(i).getZhanghaoId()) && orders.get(i).getZhanghaoId() != 15){
	                	cteateCell(workbook, row, 0, getZhangHaoId(orders.get(i).getZhanghaoId().toString()));
	                }else{
	                	cteateCell(workbook, row, 0, "");
	                }
	                cteateCell(workbook, row, 1, orders.get(i).getWuping());
	                cteateCell(workbook, row, 2, orders.get(i).getDanhao());
	                if(orders.get(i).getKuaidifangshiId() != null && !"".equals(orders.get(i).getKuaidifangshiId()) && orders.get(i).getKuaidifangshiId() !=0){
	                	cteateCell(workbook, row, 3, getKuaiDiFangShi(orders.get(i).getKuaidifangshiId().toString()));
	                }else{
	                	cteateCell(workbook, row, 3, "");
	                }
	                if((orders.get(i).getGuoneikuaidiId() == null || "".equals(orders.get(i).getGuoneikuaidiId())) && (orders.get(i).getGuoneidanhao() == null || "".equals(orders.get(i).getGuoneidanhao()))){
	                	cteateCell(workbook, row, 4, "");
	                }else{
	                    cteateCell(workbook, row, 4, getGuoNeiKuaiDiFangShi(orders.get(i).getGuoneikuaidiId().toString())+orders.get(i).getGuoneidanhao().toString());
	                }
	                cteateCell(workbook, row, 5, orders.get(i).getGuowaidizhi());                
	                cteateCell(workbook, row, 6, orders.get(i).getOrderId());
	            }
			}
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            workbook.write(baos);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        byte ba[] = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        
        try
        {
        	java.util.Date d = new java.util.Date();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ff = f.format(d);
            for(int i = 0; i < orders.size(); i++)
            {
                orders.get(i).setId(orders.get(i).getId());
                orders.get(i).setDaochu(1l);
                orders.get(i).setDcsj(ff);
                orderTableDao.merge(orders.get(i));
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return bais;
    }

    public String execute() throws Exception
    {
        return super.execute();
    }

    private void cteateCell(HSSFWorkbook wb, HSSFRow row, int col, String val)
    {
    	  //����һ��celll��Ԫ��  
        HSSFCell cell=row.createCell(col);  
        cell.setCellValue(val);  
        //������ʽ  
        HSSFCellStyle cellstyle=wb.createCellStyle();    
        cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //�±߿�
        cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//��߿�
        cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//�ϱ߿�
        cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//�ұ߿�
        cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//��ֱ������ж���  
       // cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//���߿�  
        cellstyle.setWrapText(true);//�����Զ�����  
        cell.setCellStyle(cellstyle);//����Ԫ��������ʽ
    }
}