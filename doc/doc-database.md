数据库
===========================
* 数据库
    * 系统sqlite接口
    * XUtils数据库接口
    * GreenDao
    * ormlite
    * 实践中常见问题


##1 原生SqliteHelper

看demo：还没写

##2 XUtils数据库接口

* 涉及到以下知识点：
    * Table的Entity定义
        * Table注解
        * Column注解
    * 数据库的配置在DbManager.DaoConfig里
    * 所有功能都在DbManager里，这是个接口
    * 没有个createTable方法，所有访问表的方法，都遵循无则自动创建的原则

贴代码：
```java
private DbManager getDB(){
    DbManager.DaoConfig dbConfig = new DbManager.DaoConfig();
    dbConfig.setAllowTransaction(true);
    dbConfig.setDbDir(new File(Ayo.ROOT));
    dbConfig.setDbName("a2016.db");
    dbConfig.setDbVersion(1);
    dbConfig.setTableCreateListener(new DbManager.TableCreateListener() {
        @Override
        public void onTableCreated(DbManager db, TableEntity<?> table) {
            Logger.info("表创建完事了");
        }
    });
    dbConfig.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
        @Override
        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

        }
    });

    DbManager db = x.getDb(dbConfig);
    return db;
}

插入：
private void testDB_add(){
    DbManager db = getDB();
    Emp emp = new Emp();
    try {
        db.save(emp);
        Logger.info("插入数据完事了");
    } catch (DbException e) {
        e.printStackTrace();
    }

    testDB_query();
}

更新：
private void testDB_update(){
    DbManager db = getDB();
    Emp emp; // = new Emp();
    try {
        emp = db.selector(Emp.class).findFirst();
        emp.name = "name new";
        db.saveOrUpdate(emp);
        Logger.info("更新数据完事了");

    } catch (DbException e) {
        e.printStackTrace();
    }

    testDB_query();
}

删除：
private void testDB_delete(){
    DbManager db = getDB();
    Emp emp; // = new Emp();
    try {
        emp = db.selector(Emp.class).findFirst();
        db.delete(Emp.class, WhereBuilder.b("id", "=", emp.id));
    } catch (DbException e) {
        e.printStackTrace();
    }

    testDB_query();
}

查询：简单方法
emp = db.selector(Emp.class).findFirst();
List<Emp> list = db.selector(Emp.class).findAll();

WhereBuilder：拼where
db.delete(Emp.class, WhereBuilder.b("id", "=", emp.id));

Selector：拼查询条件
Parent test = db.selector(Parent.class).where("id", "in", new int[]{1, 3, 6}).findFirst();
long count = db.selector(Parent.class).where("name", "LIKE", "w%").and("age", ">", 32).count();
List<Parent> testList = db.selector(Parent.class).where("id", "between", new String[]{"1", "5"}).findAll();

List<Parent> list = db.selector(Parent.class)
    .where("id", "<", 54)
    .and("time", ">", calendar.getTime())
    .orderBy("id", true)
    .limit(10).findAll();

List<DbModel> dbModels = db.selector(Parent.class)
    .groupBy("name")
    .select("name", "count(name) as count").findAll();
```

##3 GreenDao

还有必要研究吗

##4 Ormlite

可以看看