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
