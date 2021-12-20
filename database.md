# [数据库基础知识](https://github.com/Snailclimb/JavaGuide/blob/main/docs/database/%E6%95%B0%E6%8D%AE%E5%BA%93%E5%9F%BA%E7%A1%80%E7%9F%A5%E8%AF%86.md)

## 1. 数据库三范式

1. 1NF(第一范式)

    属性（对应于表中的字段）不能再被分割，也就是这个字段只能是一个值，不能再分为多个其他的字段了。关系型数据库中创建的表一定满足第一范式。

2. 2NF(第二范式)

    2NF 在 1NF 的基础之上，消除了非主属性对于码的部分函数依赖。第二范式在第一范式的基础上增加了一个列，这个列称为主键，非主属性都依赖于主键。

3. 3NF(第三范式)
    在2NF基础上，任何非主属性不依赖于其它非主属性（在2NF基础上消除传递依赖）

> 参考以下文章辅助理解
>
> [关系型数据库设计：三大范式的通俗理解](https://www.cnblogs.com/wsg25/p/9615100.html)

## ER 图

    也称实体-联系图(Entity Relationship Diagram)，提供了表示实体类型、属性和联系的方法，用来描述现实世界的概念模型。 它是描述现实世界关系概念模型的有效方法。 是表示概念关系模型的一种方式。

## 相关名词

    元组 ： 行。
    码 ：列。
    候选码 ： 区别一个元组（即表中的一行数据）的属性或属性的集合。

    主码 : 主键。
    外码 : 外键。如果一个关系中的一个属性是另外一个关系中的主码(主键)则这个属性为外码(外键)。
    主属性 ： 候选码中出现过的属性称为主属性
    非主属性： 不包含在任何一个候选码中的属性称为非主属性。
    
    元组 ： 元组（tuple）是关系数据库中的基本概念，关系是一张表，表中的每行（即数据库中的每条记录）就是一个元组，每列就是一个属性。 在二维表里，元组也称为行。
    码 ：码就是能唯一标识实体的属性，对应表中的列。
    候选码 ： 若关系中的某一属性或属性组的值能唯一的标识一个元组，而其任何、子集都不能再标识，则称该属性组为候选码。例如：在学生实体中，“学号”是能唯一的区分学生实体的，同时又假设“姓名”、“班级”的属性组合足以区分学生实体，那么{学号}和{姓名，班级}都是候选码。
    主码 : 主码也叫主键。主码是从候选码中选出来的。 一个实体集中只能有一个主码，但可以有多个候选码。
    外码 : 外码也叫外键。如果一个关系中的一个属性是另外一个关系中的主码则这个属性为外码。
    主属性 ： 候选码中出现过的属性称为主属性。比如关系 工人（工号，身份证号，姓名，性别，部门）. 显然工号和身份证号都能够唯一标示这个关系，所以都是候选码。工号、身份证号这两个属性就是主属性。如果主码是一个属性组，那么属性组中的属性都是主属性。
    非主属性： 不包含在任何一个候选码中的属性称为非主属性。比如在关系——学生（学号，姓名，年龄，性别，班级）中，主码是“学号”，那么其他的“姓名”、“年龄”、“性别”、“班级”就都可以称为非主属性。
    