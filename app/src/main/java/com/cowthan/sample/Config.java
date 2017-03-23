package com.cowthan.sample;

import com.ayo.opensource.zglass_broken.GlassBrokenMainActivity;
import com.ayo.opensource.ziosmessage_list.IosMessageMainActivity;
import com.ayo.opensource.zlayout.LayoutDemoActivity;
import com.ayo.opensource.zlikeview.LikeViewMainActivity;
import com.ayo.opensource.zlikeview2.LikeView2MainActivity;
import com.ayo.opensource.zmathview.MathViewMainActivity;
import com.ayo.opensource.zpartial_explode.PartialExplodeMainActivity;
import com.ayo.sample.drawable.DemoDrawableActivity;
import com.ayo.sample.zasync.AsyncDemoActivity;
import com.ayo.sample.zdatabase.XUtilsDBDemoActivity;
import com.ayoview.sample.zbutton.ButtonDemoActivity;
import com.ayoview.sample.zsimplepromt.SvProgressHudDemoActivity;
import com.ayoview.sample.ztextview.TextViewDemoActivity;
import com.ayoview.sample.ztmpl_listview.TmplListActivity;
import com.cowthan.sample.menu.Leaf;
import com.cowthan.sample.menu.Menu;
import com.cowthan.sample.menu.MenuItem;
import com.example.administrator.myapplication.Particle2MainActivity;
import com.snowy.demo.retrofit.RetrofitDemoActivity;
import com.snowy.demo.zanimation.AnimationDemoActivity;
import com.snowy.demo.zcamera.CameraDemoActivity;
import com.snowy.demo.zcontacts.ContactsDemoActivity;
import com.snowy.demo.zdesignpattern.DesignPatternActivity;
import com.snowy.demo.zdpm.DpmActivity;
import com.snowy.demo.zeventbus.EventBusActivity;
import com.snowy.demo.zfragment.FragmentDemoActivity;
import com.snowy.demo.zgridview.GridViewMainActivity;
import com.snowy.demo.zhttp.HttpMainActivity;
import com.snowy.demo.zprint.PrintDemoActivity;
import com.snowy.demo.zreader.ReaderMainActivity;
import com.snowy.demo.zrxjava.RxJavaDemoActivity;
import com.snowy.demo.zsocket.SocketDemoActivity;
import com.snowy.demo.zsystembartint.SystemBarTintMainActivity;
import com.snowy.demo.zuicode.ClockActivity;
import com.snowy.demo.zuicode.DrawViewActivity;
import com.snowy.demo.zuicode.ExpandableListViewActivity;
import com.snowy.demo.zuicode.FrameLayoutActivity;
import com.snowy.demo.zuicode.GridViewActivity;
import com.snowy.demo.zuicode.ImageViewActivity;
import com.snowy.demo.zuicode.StreamLayoutActivity;
import com.snowy.demo.zviewpager.ViewPagerDemoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cowthan on 2016/1/17.
 */
public class Config{

    private static Config instance = new Config();

    private Config(){
        init();
    }

    public static Config getDefault() {
        return instance;
    }

    private List<Menu> menus;

    public List<Menu> getMenus(){
        return menus;
    }

    private void init(){
        menus = new ArrayList<Menu>();

        ///--------------------------菜单1：笔记
        Menu m1 = new Menu("笔记", R.drawable.contact_list_normal, R.drawable.contact_list_pressed);
        menus.add(m1);
        {
            MenuItem menuItem = new MenuItem("基础", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m1.addMenuItem(menuItem);
            {
                menuItem.addLeaf(new Leaf("动画", "", AnimationDemoActivity.class));
                menuItem.addLeaf(new Leaf("EventBus", "", EventBusActivity.class));
                menuItem.addLeaf(new Leaf("OkHttp", "", HttpMainActivity.class));
                menuItem.addLeaf(new Leaf("通讯录", "", ContactsDemoActivity.class));
                menuItem.addLeaf(new Leaf("DevicePolicyManager", "", DpmActivity.class));
                menuItem.addLeaf(new Leaf("Socket长连接", "", SocketDemoActivity.class));
                menuItem.addLeaf(new Leaf("Android浸入式 ", "", SystemBarTintMainActivity.class));
                menuItem.addLeaf(new Leaf("Reader ", "", ReaderMainActivity.class));
                menuItem.addLeaf(new Leaf("RxJava", "", RxJavaDemoActivity.class));
                menuItem.addLeaf(new Leaf("Retrofit", "", RetrofitDemoActivity.class));
                menuItem.addLeaf(new Leaf("相机", "", CameraDemoActivity.class));
                menuItem.addLeaf(new Leaf("打印", "", PrintDemoActivity.class));
            }

            MenuItem menuItem3 = new MenuItem("控件", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m1.addMenuItem(menuItem3);
            {
                menuItem3.addLeaf(new Leaf("Drawable系列", "", DemoDrawableActivity.class));
                menuItem3.addLeaf(new Leaf("TextView：显示系列", "", TextViewDemoActivity.class));
                menuItem3.addLeaf(new Leaf("EditText：输入系列", "", TextViewDemoActivity.class));
                menuItem3.addLeaf(new Leaf("Button：点击系列", "", ButtonDemoActivity.class));
//                menuItem3.addLeaf(new Leaf("ImageView系列", "", null));
//                menuItem3.addLeaf(new Leaf("ProgressView系列", "", null));
//                menuItem3.addLeaf(new Leaf("View Flipper系列", "", null));
//                menuItem3.addLeaf(new Leaf("表单系列", "", null));
                menuItem3.addLeaf(new Leaf("布局", "", LayoutDemoActivity.class));
//                menuItem3.addLeaf(new Leaf("列表", "", null));
                menuItem3.addLeaf(new Leaf("ViewPager", "", ViewPagerDemoActivity.class));
                menuItem3.addLeaf(new Leaf("Fragment", "", FragmentDemoActivity.class));
                menuItem3.addLeaf(new Leaf("跟随手指的小球", "", DrawViewActivity.class));
                menuItem3.addLeaf(new Leaf("霓虹灯效果", "", FrameLayoutActivity.class));
                menuItem3.addLeaf(new Leaf("时钟", "", ClockActivity.class));
                menuItem3.addLeaf(new Leaf("图片浏览器", "", ImageViewActivity.class));
                menuItem3.addLeaf(new Leaf("瀑布流", "", StreamLayoutActivity.class));
                menuItem3.addLeaf(new Leaf("带预览的图片浏览器", "", GridViewActivity.class));
                menuItem3.addLeaf(new Leaf("可展开的列表组件", "", ExpandableListViewActivity.class));
                menuItem3.addLeaf(new Leaf("带分隔线的GridView", "", GridViewMainActivity.class));
                menuItem3.addLeaf(new Leaf("简单提示框: SV系列", "", SvProgressHudDemoActivity.class));
            }

            MenuItem menuItem5 = new MenuItem("模板库", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m1.addMenuItem(menuItem5);
            {
//                menuItem5.addLeaf(new Leaf("Activity和Fragment基类", "", null));
                menuItem5.addLeaf(new Leaf("ListView和GridView模板", "", TmplListActivity.class));
//                menuItem5.addLeaf(new Leaf("界面主框架", "", null));
            }

            MenuItem menuItem6 = new MenuItem(" Java ", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m1.addMenuItem(menuItem6);
            {
                menuItem6.addLeaf(new Leaf("设计模式", "", DesignPatternActivity.class));
                menuItem6.addLeaf(new Leaf("多线程和RxJava", "", AsyncDemoActivity.class));
                menuItem6.addLeaf(new Leaf("数据库", "", XUtilsDBDemoActivity.class));
            }
        }

        ///--------------------------菜单1：开源
        Menu m3 = new Menu("开源", R.drawable.find_normal, R.drawable.find_pressed);
        menus.add(m3);
        {
            MenuItem menuItem = new MenuItem("军用级", R.drawable.find_normal, R.drawable.find_pressed);
            m3.addMenuItem(menuItem);
            {
                menuItem.addLeaf(new Leaf("粒子破碎", "", PartialExplodeMainActivity.class));
                menuItem.addLeaf(new Leaf("粒子破碎-优化版", "", Particle2MainActivity.class));
                menuItem.addLeaf(new Leaf("玻璃破碎", "", GlassBrokenMainActivity.class));
                menuItem.addLeaf(new Leaf("iOS短信列表页动效", "", IosMessageMainActivity.class));
                menuItem.addLeaf(new Leaf("数学公式绘制", "", MathViewMainActivity.class));
                menuItem.addLeaf(new Leaf("likeview-点击后闪亮的button--点赞", "", LikeViewMainActivity.class));
                menuItem.addLeaf(new Leaf("likeview更高级简便版", "", LikeView2MainActivity.class));
                menuItem.addLeaf(new Leaf("facebook rebound-弹簧阻尼器", "", LikeView2MainActivity.class));
                menuItem.addLeaf(new Leaf("dagger", "http://itlanbao.com/code/20151210/10000/100687.html", LikeView2MainActivity.class));
            }

            menuItem = new MenuItem("民用级", R.drawable.find_normal, R.drawable.find_pressed);
            m3.addMenuItem(menuItem);
            {
                menuItem.addLeaf(new Leaf("仿微信", "", null));
                menuItem.addLeaf(new Leaf("凡信", "", null));
            }
        }
    }

}
