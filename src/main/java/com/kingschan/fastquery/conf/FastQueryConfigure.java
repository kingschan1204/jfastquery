package com.kingschan.fastquery.conf;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import com.kingschan.fastquery.conf.model.KeyWordArgs;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * <pre>
 * 类名称：FastQueryConfigure
 * 类描述:配置
 * 创建人：陈国祥(kingschan)
 * 创建时间：2015-7-23 下午5:24:16
 * 修改人：Administrator
 * 修改时间：2015-7-23 下午5:24:16
 * 修改备注：
 * @version V1.0
 * </pre>
 */
@Slf4j
public class FastQueryConfigure {

    private FastQueryConfigure() {
    }

    private static FastQueryConfigure instance;
    private static final String CONFIG_FILE_NAME = "fastquery.properties";//配置文件名称
    private DataSource defaultDs;//默认连接
    private KeyWordArgs args;

    public void setDefaultDs(DataSource defaultDs) {
        this.defaultDs = defaultDs;
    }
    public KeyWordArgs getArgs() {
        return args;
    }

    /**
     * read default configure file
     *
     * @return
     * @throws Exception
     */
    public static Properties getConfigure() throws Exception {
        Properties pps = new Properties();
        InputStream in = FastQueryConfigure.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
        pps.load(in);
        return pps;
    }

    /**
     * 单例实例化当前对象
     * @return
     */
    public static synchronized FastQueryConfigure getInstance() {
        if (null == instance) {
            instance = new FastQueryConfigure();
            try {
               /* String path=instance.getClass().getClassLoader().getResource("").toURI().getPath().concat("easyquery.properties");
               PropertiesUtil.getProperties(new File(path));*/
                Properties p = getConfigure();
                String className = p.getProperty("app.default.datasource");
                Configure conf = (Configure) Class.forName(className).newInstance();
                instance.setDefaultDs(conf.getDataSource());
                //init keyword args
                KeyWordArgs args=new KeyWordArgs();
                args.setFilter(p.getProperty("app.keyword.filter"));
                args.setOrder(p.getProperty("app.keyword.order"));
                args.setPageIndex(p.getProperty("app.keyword.page.index"));
                args.setPageSize(p.getProperty("app.keyword.page.size"));
                args.setSort(p.getProperty("app.keyword.sort"));
                instance.args=args;
            } catch (Exception e) {
                log.error("{}", e);
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * 得到默认连接
     *
     * @return
     * @throws Exception
     */
    public Connection getDefaultCon() throws Exception {
        return defaultDs.getConnection();
    }
}
