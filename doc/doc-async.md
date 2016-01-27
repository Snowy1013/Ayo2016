异步
===========================
* 异步
    * Java多线程管理和通信
    * android多线程管理和通信
    * 如何自定义线程池，控制任务
    * RxJava

XUtils提供的：
x.task().post(new Runnable() { // UI同步执行
    @Override
    public void run() {
        tv_db_result.setText(finalResult);
    }
});
