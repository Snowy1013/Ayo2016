package com.cowthan.sample;

import com.cowthan.sample.menu.Leaf;
import com.cowthan.sample.menu.Menu;
import com.cowthan.sample.menu.MenuItem;
import com.ayoview.sample.zbutton.ButtonDemoActivity;
import com.ayo.sample.zdatabase.XUtilsDBDemoActivity;
import com.ayo.sample.zeventbus.EventBusDemoActivity;
import com.snowy.demo.zfragment.FragmentDemoActivity;
import com.ayo.opensource.zglass_broken.GlassBrokenMainActivity;
import com.ayo.opensource.ziosmessage_list.IosMessageMainActivity;
import com.ayo.opensource.zlayout.LayoutDemoActivity;
import com.ayo.opensource.zlikeview.LikeViewMainActivity;
import com.ayo.opensource.zlikeview2.LikeView2MainActivity;
import com.ayo.opensource.zmathview.MathViewMainActivity;
import com.ayo.opensource.zpartial_explode.PartialExplodeMainActivity;
import com.ayo.sample.zsdk.ToolkitSampleActivity;
import com.ayoview.sample.zsimplepromt.SvProgressHudDemoActivity;
import com.ayoview.sample.ztextview.TextViewDemoActivity;
import com.ayoview.sample.ztmpl_listview.TmplListActivity;
import com.snowy.demo.zviewpager.ViewPagerDemoActivity;
import com.example.administrator.myapplication.Particle2MainActivity;

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

        ///--------------------------菜单1：sdk
        Menu m1 = new Menu("sdk", R.drawable.weixin_normal, R.drawable.weixin_pressed);
        menus.add(m1);
        {
            MenuItem menuItem1 = new MenuItem("sdk", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m1.addMenuItem(menuItem1);
            {
                menuItem1.addLeaf(new Leaf("常用工具类", "", ToolkitSampleActivity.class));
                menuItem1.addLeaf(new Leaf("缓存", "", null));
                menuItem1.addLeaf(new Leaf("数据库", "", XUtilsDBDemoActivity.class));
                menuItem1.addLeaf(new Leaf("控件注入", "", null));
                menuItem1.addLeaf(new Leaf("日志系统", "", null));
                menuItem1.addLeaf(new Leaf("io", "", null));
                menuItem1.addLeaf(new Leaf("http", "", null));
                menuItem1.addLeaf(new Leaf("下载文件", "", null));
                menuItem1.addLeaf(new Leaf("上传文件", "", null));
                menuItem1.addLeaf(new Leaf("日志", "", null));
                menuItem1.addLeaf(new Leaf("多线程", "", null));
                menuItem1.addLeaf(new Leaf("RxJava", "", null));
                menuItem1.addLeaf(new Leaf("事件总线", "", EventBusDemoActivity.class));

            }


            MenuItem menuItem2 = new MenuItem("动画", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m1.addMenuItem(menuItem2);
            {
                menuItem2.addLeaf(new Leaf("Tween动画", "", null));
                menuItem2.addLeaf(new Leaf("属性动画", "", null));
                menuItem2.addLeaf(new Leaf("Daimajia-YoYo", "", null));
                menuItem2.addLeaf(new Leaf("Activity切换", "", null));
                menuItem2.addLeaf(new Leaf("TypeEvaluator-缓动函数", "", null));
                menuItem2.addLeaf(new Leaf("动效工厂", "", null));
            }


            MenuItem menuItem3 = new MenuItem("控件", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m1.addMenuItem(menuItem3);
            {
                menuItem3.addLeaf(new Leaf("TextView：显示系列", "", TextViewDemoActivity.class));
                menuItem3.addLeaf(new Leaf("EditText：输入系列", "", TextViewDemoActivity.class));
                menuItem3.addLeaf(new Leaf("Button：点击系列", "", ButtonDemoActivity.class));
                menuItem3.addLeaf(new Leaf("ImageView系列", "", null));
                menuItem3.addLeaf(new Leaf("ProgressView系列", "", null));
                menuItem3.addLeaf(new Leaf("View Flipper系列", "", null));
                menuItem3.addLeaf(new Leaf("表单系列", "", null));
                menuItem3.addLeaf(new Leaf("布局", "", LayoutDemoActivity.class));
                menuItem3.addLeaf(new Leaf("列表", "", null));
            }

            MenuItem menuItem4 = new MenuItem("提示", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m1.addMenuItem(menuItem4);
            {
                menuItem4.addLeaf(new Leaf("简单提示框: SV系列", "", SvProgressHudDemoActivity.class));
                menuItem4.addLeaf(new Leaf("简单提示框: Sweet系列", "", null));
                menuItem4.addLeaf(new Leaf("提示条: Snack系列", "", null));
                menuItem4.addLeaf(new Leaf("提示条: Toast系列", "", null));
                menuItem4.addLeaf(new Leaf("提示条: 小黄条", "", null));
                menuItem4.addLeaf(new Leaf("普通dialog: 动画和布局自定义", "", null));
                menuItem4.addLeaf(new Leaf("popupwindow", "", null));
                menuItem4.addLeaf(new Leaf("notification", "", null));
                menuItem4.addLeaf(new Leaf("headup", "", null));
                menuItem4.addLeaf(new Leaf("声音，led，震动，亮屏", "", null));
                menuItem4.addLeaf(new Leaf("其他", "", null));
            }

            MenuItem menuItem5 = new MenuItem("模板库", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m1.addMenuItem(menuItem5);
            {
                menuItem5.addLeaf(new Leaf("Activity和Fragment基类", "", null));
                menuItem5.addLeaf(new Leaf("ListView和GridView模板", "", TmplListActivity.class));
                menuItem5.addLeaf(new Leaf("界面主框架", "", null));
            }

        }


        ///--------------------------菜单1：笔记
        Menu m2 = new Menu("笔记", R.drawable.contact_list_normal, R.drawable.contact_list_pressed);
        menus.add(m2);
        {
            MenuItem menuItem = new MenuItem("snowy", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m2.addMenuItem(menuItem);
            {
                menuItem.addLeaf(new Leaf("ViewPager", "", ViewPagerDemoActivity.class));
                menuItem.addLeaf(new Leaf("Fragment", "", FragmentDemoActivity.class));
                menuItem.addLeaf(new Leaf("控件", "", null));
                menuItem.addLeaf(new Leaf("http", "", null));
                menuItem.addLeaf(new Leaf("数据库", "", null));
            }

            menuItem = new MenuItem("seven", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m2.addMenuItem(menuItem);
            {
                menuItem.addLeaf(new Leaf("控件", "", null));
                menuItem.addLeaf(new Leaf("http", "", null));
                menuItem.addLeaf(new Leaf("数据库", "", null));
            }
        }

        ///--------------------------菜单1：开源
        Menu m3 = new Menu("开源", R.drawable.find_normal, R.drawable.find_pressed);
        menus.add(m3);
        {
            MenuItem menuItem = new MenuItem("军用级", R.drawable.weixin_normal, R.drawable.weixin_pressed);
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

            menuItem = new MenuItem("民用级", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m3.addMenuItem(menuItem);
            {
                menuItem.addLeaf(new Leaf("仿微信", "", null));
                menuItem.addLeaf(new Leaf("凡信", "", null));
            }
        }

        ///--------------------------菜单1：常见问题
        Menu m4 = new Menu("常见问题", R.drawable.profile_normal, R.drawable.profile_pressed);
        menus.add(m4);
        {
            MenuItem menuItem = new MenuItem("常见问题", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m4.addMenuItem(menuItem);
            {
                menuItem.addLeaf(new Leaf("滑动冲突", "", null));
                menuItem.addLeaf(new Leaf("小键盘", "", null));
            }

            menuItem = new MenuItem("常用代码段", R.drawable.weixin_normal, R.drawable.weixin_pressed);
            m4.addMenuItem(menuItem);
            {
                menuItem.addLeaf(new Leaf("selector", "", null));
                menuItem.addLeaf(new Leaf("border", "", null));
            }
        }
    }

}
