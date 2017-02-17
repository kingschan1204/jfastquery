package com.kingschan.fastquery.conf;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;


import org.apache.log4j.Logger;

import com.kingschan.fastquery.sql.AbstractConnection;

/**
 * 
*  <pre>    
* 类名称：EasyQueryConfigure 
* 类描述：   配置
* 创建人：陈国祥   (kingschan)
* 创建时间：2015-7-23 下午5:24:16   
* 修改人：Administrator   
* 修改时间：2015-7-23 下午5:24:16   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class EasyQueryConfigure {
    
    private EasyQueryConfigure(){}
    private static EasyQueryConfigure instance;
    private static Logger log = Logger.getLogger(EasyQueryConfigure.class);
    private static final String CONFIG_FNAME="easyquery.properties";//配置文件名称
    private  AbstractConnection defaultCon;//默认连接
    private String SqlFolder;//sql目录
    

    public void setDefaultCon(AbstractConnection defaultCon) {
        this.defaultCon = defaultCon;
    }
    public AbstractConnection getConn(){
        return this.defaultCon;
    }
    /**
     *read default configure file
     * @return
     * @throws Exception
     */
    public static Properties getConfigure() throws Exception {
        Properties pps = new Properties();
        InputStream in = EasyQueryConfigure.class.getClassLoader(). getResourceAsStream(CONFIG_FNAME);
        pps.load(in);
        return pps;
    }
    
    public static synchronized EasyQueryConfigure getInstance(){
        if (null==instance) {
            instance= new EasyQueryConfigure();
            try {
//                String path=instance.getClass().getClassLoader().getResource("").toURI().getPath().concat("easyquery.properties");
//                PropertiesUtil.getProperties(new File(path));
              /*  Properties p = getConfigure();
                String className= p.getProperty("easyquery.defaultConClass");*/
                instance.setDefaultCon((AbstractConnection) Class.forName("com.kingschan.fastquery.DefaultConnection").newInstance());
            } catch (Exception e) {
                log.error(e);
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * 得到默认连接
     * @return
     * @throws Exception
     */
  public  Connection getDefaultCon() throws Exception{
      return defaultCon.getConnection();
    }
}
