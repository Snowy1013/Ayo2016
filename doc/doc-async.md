异步
===========================
* 异步
    * Java多线程管理和通信
    * android多线程管理和通信
    * 如何自定义线程池，控制任务
    * AsyncTask的封装，仿XUtils的task
    * RxJava


##1 java异步机制

* 不说了
    * Thread
    * 线程池
    * Future，Callable，Runnable
    * Cancel机制
    * 各种通信手段

##2 android异步机制

* 不说了
    * Handler，looper，消息队列
    * AsyncTask对Handler和线程池的封装
    * AsyncTask的execute可以接受一个Executor作为参数，但线程管理原则应该是什么呢，不知道


##3 Ayo库提供的AsyncTask和Handler封装：Aync类

```java
//执行异步任务并回调
Aync.newTask(runnable).post(runnable).go();

//执行异步任务，不回调
Aync.newTask(runnable).go();

//UI同步执行
Aync.post(runnable, delay);
```

例子：
```java
public void test(){
    Async.newTask(new Runnable() {
        @Override
        public void run() {
            ///后台任务，直接使用AsyncTask默认的线程管理，怎么个行为？
        }
    }).post(new Runnable() {
        @Override
        public void run() {
            ///主线程回调
        }
    }).go();
}
```

* 强烈注意：
    * 你可能说这个类还有很多可以优化的地方，没错，但是这个类不会再有功能上的提升了，为何？
    * 因为再写就是往RxJava写了，但很难写的比RxJava更牛逼，所以再复杂点的任务，就直接用RxJava吧

##4 RxJava：牛逼的响应式编程

* 怎么好？
    * 就我个人的理解，响应式编程可以很好的组织你的业务流程逻辑代码
    * 方便的指定在哪个线程运行


##5 怎么样管理线程池？

* 额。。。
    * 主线程：就一个，无需多说
    * IO密集型线程：几个呢
    * CPU密集型线程：几个CPU就几个