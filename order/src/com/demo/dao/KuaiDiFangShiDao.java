package com.demo.dao;
import java.util.List;

import com.demo.entity.KuaiDiFangShi;

//Referenced classes of package com.demo.dao:
//         BaseDao

public interface KuaiDiFangShiDao extends BaseDao<KuaiDiFangShi,Long>
{

 public abstract List<KuaiDiFangShi> getSelKuaiDiFangShi();
 //¿ìµÝ²éÑ¯
 public List<KuaiDiFangShi> getCourier(String name);
}
