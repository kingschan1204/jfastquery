# jfastQuery

> 一个用java实现的快速查询组件,支持三种数据库

- mysql
- oralce
- sqlserver

### 背景
> 解决在java jdbc 开发中拼接sql语句的情景，通常sql会和java代码混在一起可读性差，像古代妇女的裹脚布一样，又长又臭。

### 特性

- 分页
- 排序
- 动态传参
- 动态组合where条件
- 支持freemarker表达式（在sql里写ftl指令）
- `让Java和sql语句分离`

### 查询条件关键字

|关键字|说明|
|-|-|
logic|and or 运算符
operator|对应下方表格 where 操作符
table|sql表别名
field| 字段名称或者括号( )
value|值
value2|日期字段下的第2个值
type|字段类型：TEXT,NUMBER,DATE,STRING,SELECT,DATETIME,BOOLEAN

### where 操作符

|类型|jfastQuery|英文|
|-|-|-|
等于|eq|equals
不等于|neq| not equals
大于|gt| greater than
小于|lt|less than
大于等于|gte|Greater than or equal to
小于等于|lte|Less than equal
介于|bw|between
包含|c|Contain
左包含|sw| start with
右包含|ew|end with 
集合|...| in 
空|nl|is null
非空|nnl|is not null

### 配置文件`fastquery.properties`
```
#jfastquery默认数据源实现类
app.default.datasource=com.kingschan.fastquery.test.DruidDatasourceConfigure
#对应pageindex关键字
app.keyword.page.index=offset
#对应pagesize关键字
app.keyword.page.size=limit
#对应where 条件组合json字符串关键字
app.keyword.filter=filter
# 对应排序字段
app.keyword.sort=sort
# 对应排序方式关键字
app.keyword.order=order
```

### 默认数据源实现
实现类：`com.kingschan.fastquery.conf.Configure`
```
public class DruidDatasourceConfigure implements Configure{

	@Override
	public DataSource getDataSource() throws Exception {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("url", "jdbc:mysql://ip:端口/数据库名");
		properties.put("username", "用户名");
		properties.put("password", "密码");
		properties.put("filters", "stat");
		properties.put("maxActive", "20");
		properties.put("initialSize", "1");
		properties.put("maxWait", "60000");
		properties.put("minIdle", "1");
		properties.put("timeBetweenEvictionRunsMillis", "60000");
		properties.put("minEvictableIdleTimeMillis", "300000");
		properties.put("testWhileIdle", "true");
		properties.put("testOnBorrow", "false");
		properties.put("testOnReturn", "false");
		properties.put("poolPreparedStatements", "true");
		properties.put("maxOpenPreparedStatements", "20");
		return DruidDataSourceFactory.createDataSource(properties);
	}

}
```


## java运行demo参考
```
public class QueryDispacherTest {
    private static Logger log = LoggerFactory.getLogger(QueryDispacherTest.class);

    public static void main(String[] args) {
        List<SqlCondition> conditions=new ArrayList<SqlCondition>();
        conditions.add(new SqlCondition("and","a.req_datetime","DATE","bw","2017-01-01","2017-02-01"));
        String filter=JSONArray.fromObject(conditions).toString();
        //查询方案所有传入参数
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("filter",filter);//查询条件
        map.put("sort","req_datetime");//排序字段
        map.put("order","desc");//排序的方式
        map.put("limit","10");//显示条数
        map.put("offset","1");//第几页

        map.put("blog","kingschan");//传入变量blog


        SqlCommand cmd = new SqlCommand();
        cmd.setSql("select req_ip,count(1) total from blog_request_log a where a.req_blog='${blog}'  group by req_ip");
        cmd.setDBtype(DbType.MYSQL);
        DataTransfer dt=  new LogicHandleDispacher().handleDispacher(map,cmd,new Class[]{
                BuildConditionLogicHandle.class,
                BuildVariableLogicHandle.class,
                BuildPagingLogicHandle.class,
                ExecuteTotalLogicHandle.class,
                ExecuteMapQueryLogicHandle.class
        });
        log.info("{}", JSONObject.fromObject(dt));
    }
}
```

## servlet 运行demo参考类
```
public class ServletTest extends HttpServlet{

    private static final long serialVersionUID = 1L;

    //http://localhost/jfastquery/test?offset=1&limit=30&sort=req_datetime&order=desc
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        //查询方案所有传入参数
        Map<String,Object> map =ServletUtil.getParameterMap(req);
        List<SqlCondition> conditions= new ArrayList<SqlCondition>();
        conditions.add( new SqlCondition("and","a.req_datetime","DATE","bw","2017-01-01","2017-02-01"));
        conditions.add( new SqlCondition("and","a.req_blog","STRING","eq","kingschan",null));
        JSONArray jons=JSONArray.fromObject(conditions);
        map.put("filter",jons);//查询条件
        map.put("sort","id");//排序字段
        map.put("order","desc");//排序的方式
        map.put("limit","10");//显示条数
        map.put("offset","1");//第几页


        WebLogicHandleDispacher dispa = new WebLogicHandleDispacher(req, resp);
        SqlCommand cmd = new SqlCommand();
        cmd.setSql("select * from blog_request_log a limit 10");
        cmd.setDBtype(DbType.MYSQL);
        dispa.handleDispacher(map,cmd, new StandardOutPut(),
            new Class[]{
                BuildConditionLogicHandle.class,
                BuildVariableLogicHandle.class,
                BuildPagingLogicHandle.class,
                ExecuteTotalLogicHandle.class,
                ExecuteMapQueryLogicHandle.class
            });
    }
}
```

