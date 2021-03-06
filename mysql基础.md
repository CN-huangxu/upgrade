# MySQL

## 介绍

    MySQL 是一种关系型数据库，(5.5 版本后)默认存储引擎 InnoDB（事务性数据库引擎）

## 事务

    事务是逻辑上的一组操作，要么都执行，要么都不执行。

```SQL
# 开启一个事务
START TRANSACTION;
# 多条 SQL 语句
SQL1,SQL2...
## 提交事务
COMMIT;
```

## 并发事务带来哪些问题?

1. 脏读（Dirty read）: 读取了还没正式提交的数据
   当一个事务正在访问数据并且对数据进行了修改，而这种修改还没有提交到数据库中，这时另外一个事务也访问了这个数据，然后使用了这个数据。因为这个数据是还没有提交的数据，那么另外一个事务读到的这个数据是“脏数据”，依据“脏数据”所做的操作可能是不正确的。

2. 丢失修改（Lost to modify）: 两个事务同时修改了同一条数据
   指在一个事务读取一个数据时，另外一个事务也访问了该数据，那么在第一个事务中修改了这个数据后，第二个事务也修改了这个数据。这样第一个事务内的修改结果就被丢失，因此称为丢失修改。 例如：事务 1 读取某表中的数据 A=20，事务 2 也读取 A=20，事务 1 修改 A=A-1，事务 2 也修改 A=A-1，最终结果 A=19，事务 1 的修改被丢失。

3. 不可重复读（Unrepeatable read）: 一个事务内多次读取同一数据结果不一样
   指在一个事务内多次读同一数据。在这个事务还没有结束时，另一个事务也访问该数据。那么，在第一个事务中的两次读数据之间，由于第二个事务的修改导致第一个事务两次读取的数据可能不太一样。这就发生了在一个事务内两次读到的数据是不一样的情况，因此称为不可重复读。

4. 幻读（Phantom read）: 一个事务内多次查询 结果数据量不一样
   幻读与不可重复读类似。它发生在一个事务（T1）读取了几行数据，接着另一个并发事务（T2）插入了一些数据时。在随后的查询中，第一个事务（T1）就会发现多了一些原本不存在的记录，就好像发生了幻觉一样，所以称为幻读

## 事务隔离级别

1. READ-UNCOMMITTED(读取未提交)： 最低的隔离级别，允许读取尚未提交的数据变更，可能会导致脏读、幻读或不可重复读。
2. READ-COMMITTED(读取已提交)： 允许读取并发事务已经提交的数据，可以阻止脏读，但是幻读或不可重复读仍有可能发生。
3. REPEATABLE-READ(可重复读)： 对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读和不可重复读，但幻读仍有可能发生。
4. SERIALIZABLE(可串行化)： 最高的隔离级别，完全服从 ACID 的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。

> MySQL 的默认隔离级别是
>
> REPEATABLE-READ(可重复读)

## 实战

### 数据增删改查

1.  插入

```SQL
    语法：insert [into] <表名> [列名] values <列值>
    insert into Strdents (姓名,性别,出生日期) values ('王伟华','男','1983/6/15')
```

2. 删除

```SQL
    语法：delete from <表名> [where <删除条件>]
    delete from a where name='王伟华'（删除表a中列值为王伟华的行）　
```

3. 改

```SQL
   语法 update <表名> set <列名=更新值> [where <更新条件>]
    update addressList set 年龄=18 where 姓名='王伟华'
```

4. 查询

```SQL
   语法 select <列名> from <表名> [where <查询条件表达试>] [order by <排序的列名>[asc或desc]]
   select name as 姓名　from a where  gender='男'
--  查询空行
    select name from a where email is null
-- 查询中使用常量
    select name '北京' as 地址 from　a
-- 查询返回限制行数(关键字：top )
    select top 6 name from a
-- 查询排序（关键字：order by , asc , desc）
    select name
　　　　　　from a
　　　　　　where grade>=60
　　　　　　order by desc
```

模糊查询
```sql
-- 模糊查询
    select * from a where name like '赵%'
-- 使用between在某个范围内进行查询
    select * from a where age between 18 and 20
-- 使用in在列举值内进行查询(in后是多个的数据)
    select name from a where address in ('北京','上海','唐山')

```

分组查询
```SQL 
-- 分组查询
    select studentID as 学员编号, AVG(score) as 平均成绩  (注释:这里的score是列名)
　　　　　　from score (注释:这里的score是表名)
　　　　　　group by studentID
-- 使用having子句进行分组筛选
    select studentID as 学员编号, AVG　　　
    from score
　　　　　　group by studentID
　　　　　　having count(score)>1
-- 备注 分组后只能使用having来限制条件，
```

多表查询
```sql
-- 多表联接查询
select a.name,b.mark
　　　　　　from a,b
　　　　　　where a.name=b.name
```

子查询
```sql
/* 子查询 */ ------------------
    - 子查询需用括号包裹。
-- from型
    from后要求是一个表，必须给子查询结果取个别名。
    - 简化每个查询内的条件。
    - from型需将结果生成一个临时表格，可用以原表的锁定的释放。
    - 子查询返回一个表，表型子查询。
    select * from (select * from tb where id>0) as subfrom where id>1;
-- where型
    - 子查询返回一个值，标量子查询。
    - 不需要给子查询取别名。
    - where子查询内的表，不能直接用以更新。
    select * from tb where money = (select max(money) from tb);
    -- 列子查询
        如果子查询结果返回的是一列。
        使用 in 或 not in 完成查询
        exists 和 not exists 条件
            如果子查询返回数据，则返回1或0。常用于判断条件。
            select column1 from t1 where exists (select * from t2);
    -- 行子查询
        查询条件是一个行。
        select * from t1 where (id, gender) in (select id, gender from t2);
        行构造符：(col1, col2, ...) 或 ROW(col1, col2, ...)
        行构造符通常用于与对能返回两个或两个以上列的子查询进行比较。
    -- 特殊运算符
    != all()    相当于 not in
    = some()    相当于 in。any 是 some 的别名
    != some()   不等同于 not in，不等于其中某一个。
    all, some 可以配合其他运算符一起使用。
```

连接查询
```sql
/* 连接查询(join) */ ------------------
    将多个表的字段进行连接，可以指定连接条件。
-- 内连接(inner join)
    - 默认就是内连接，可省略inner。
    - 只有数据存在时才能发送连接。即连接结果不能出现空行。
    on 表示连接条件。其条件表达式与where类似。也可以省略条件（表示条件永远为真）
    也可用where表示连接条件。
    还有 using, 但需字段名相同。 using(字段名)
    -- 交叉连接 cross join
        即，没有条件的内连接。
        select * from tb1 cross join tb2;
-- 外连接(outer join)
    - 如果数据不存在，也会出现在连接结果中。
    -- 左外连接 left join
        如果数据不存在，左表记录会出现，而右表为null填充
    -- 右外连接 right join
        如果数据不存在，右表记录会出现，而左表为null填充
-- 自然连接(natural join)
    自动判断连接条件完成连接。
    相当于省略了using，会自动查找相同字段名。
    natural join
    natural left join
    natural right join
select info.id, info.name, info.stu_num, extra_info.hobby, extra_info.sex from info, extra_info where info.stu_num = extra_info.stu_id;
```

参考 [一千行 MySQL 学习笔记](https://github.com/Snailclimb/JavaGuide/blob/main/docs/database/mysql/a-thousand-lines-of-mysql-study-notes.md)
