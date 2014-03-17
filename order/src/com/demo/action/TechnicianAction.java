
package com.demo.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.dao.DocumentDao;
import com.demo.entity.DocumentTable;
import com.demo.entity.order.OrderTable;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
@Controller("technicianAction")
@Scope("prototype")
public class TechnicianAction extends BaseAction implements ServletRequestAware
{
    private static final long serialVersionUID = 1L;
    private PageBean pageBean;
    @Resource
	private PageBiz pageBiz;
    @Resource
	private DocumentDao documentDao;
    public int pageNumber;
    public String time;
    public String time1;
    private HttpServletRequest request;
    public PageBean getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
 	public String getUnfinished()
 	{
 		int pageSize=10;
 		pageBean = pageBiz.selUnfinish(pageSize, pageNumber, time, time1);
 		return "getunfinished";
 	}
 	//发给管理员审核
 	public String audit(){
 	    String ch[] = request.getParameter("bulletinId").split("-");
        for(int i = 0; i < ch.length; i++)
        {
            List<DocumentTable> ls = documentDao.getDocumentId(Long.parseLong(ch[i]));
            if(ls.size() != 0)
            {
                ls.get(0).setId(Long.parseLong(ch[i]));
                ls.get(0).setGetDocuments(0l);
                ls.get(0).setAudit(1l);
                documentDao.merge(ls.get(0));
             }
        }
        msg = "操作成功 ";
        return getUnfinished();
 	}
 	//完成
 	public String getCompleted(){
 		int pageSize= 10;
 		pageBean = pageBiz.selCompletes(pageSize, pageNumber, time, time1);
 		return "getCompleted";
 	}
}
