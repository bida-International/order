package com.demo.dao.Impl.user;

import com.demo.bean.MyHibernateTemplate;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.user.UserDao;
import com.demo.entity.*;
import com.demo.entity.user.CaiGou;
import com.demo.entity.user.CaiGouAdmin;
import com.demo.entity.user.UserInfo;
import com.demo.entity.user.YeWu;

import java.io.PrintStream;
import java.util.List;

import org.springframework.stereotype.Repository;

// Referenced classes of package com.demo.dao:
//            BaseDaoImpl, UserDao
@Repository
public class UserDaoImpl extends BaseDaoImpl<UserInfo,Long>
    implements UserDao
{

    public UserDaoImpl()
    {
        super(UserInfo.class);
    }

    public UserInfo getByAccount(String account)
    {
        return (UserInfo)ht.findFirst("from UserInfo a where a.username = ?", new Object[] {
            account
        });
    }

    public UserInfo getByUserNameOnPwd(String account, String pwd)
    {
        return (UserInfo)ht.findFirst("from UserInfo a where a.username=? and a.pwd = ?", new Object[] {
            account, pwd
        });
    }

    public UserInfo getUserId(Long caigouid)
    {
        return (UserInfo)ht.findFirst("from UserInfo a where id = ?", new Object[] {
            caigouid
        });
    }

    public UserInfo getPwd(String pwd)
    {
        return (UserInfo)ht.findFirst("from UserInfo a where a.pwd = ?", new Object[] {
            pwd
        });
    }
    //管理员查看全部员工账号
    public String getzhanghao(String username)
    {
    	String stu = null;
        if(username != null && !"".equals(username))
        {
            stu = "from UserInfo a where a.admin != 1 and id != 27 and a.username = '"+username+"'";
        } else
        {
            stu = "from UserInfo a where a.admin != 1 and id != 27";
        }
        return stu;
    }

    public CaiGou getcaigou(Long userid)
    {
        return (CaiGou)ht.findFirst("from CaiGou a where a.userid = ?", new Object[] {
            userid
        });
    }

    public CaiGouAdmin getcaigouadmin(Long userid)
    {
        return (CaiGouAdmin)ht.findFirst("from CaiGouAdmin a where a.userid = ?", new Object[] {
            userid
        });
    }

    public YeWu getyewu(Long userid)
    {
        return (YeWu)ht.findFirst("from YeWu a where a.userid = ?", new Object[] {
            userid
        });
    }

    public void getUser(Long userId)
    {
        ht.delete("delete YeWu u where u.userid = ?", ((Object) (new Object[] {
            userId
        })));
    }
    //查看全部账号
    public  List<UserInfo> getAllUser(){
      return ht.find("from UserInfo"); 
    }
    //查询全部员工
    public List<UserInfo> getYuanGong(){
    	 return ht.find("from UserInfo where id not in(select userid from GuKeTable)"); 
    	
    }
    //查询管理员
    public  List<UserInfo> getAdmin(){
    	return ht.find("from UserInfo where admin = 1");
    }
    //查看未读信
    public List<UserInfo> getWeiDuXin(Long userid){
    	return ht.find("select count(*) from XieXinTable where (chakan is null or chakan = 0) and userid = ?",new Object[]{userid});
    }
}
