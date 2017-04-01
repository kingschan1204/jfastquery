# jfastQuery

> 一个用java实现的快速查询组件,支持三种数据库

- mysql
- oralce
- sqlserver

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
包含|c|Contain
左包含|sw| start with
右包含|ew|end with 
集合|...| in 
空|nl|is null
非空|nnl|is not null
