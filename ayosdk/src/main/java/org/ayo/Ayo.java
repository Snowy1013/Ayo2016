package org.ayo;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import org.ayo.lang.Lang;
import org.xutils.x;

import java.io.File;


public class Ayo {
	
	public static Context context;
	public static String spName = "default.conf";
	public static boolean debug = false;
	public static String ROOT;// default work directory
	//public static String SERVER_ENCODE = "utf-8";

	/**
	 * memory related
	 */
	public static final int MEM_LEVEL_NONE = 1000;
	public static final int MEM_LEVEL_EXERLLENT = 1;
	public static final int MEM_LEVEL_JUST_FINE = 2;
	public static final int MEM_LEVEL_BAD = 3;
	public static final int MEM_LEVEL_NOT_A_PHONE = 4;

	public static int MEM_LEVEL = MEM_LEVEL_JUST_FINE;

	/**
	 * init the genius library
	 * @param context global context
	 * @param path  setSDRoot("/work-dir/"), used be SD_ROOT
	 */
	public static void init(Application context, String path) {
		Ayo.context = context;
		Display.init(context);
		LogInner.print("genius-init: screen（{w}, {h}）".replace("{w}", Display.screenWidth + "").replace("{h}", Display.screenHeight + ""));
		
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		LogInner.print("genius-init: memory----Max memory is " + maxMemory + "KB");
		//MI 3--131072K, 130M
		//htc-M8--196608K，200M
		//MI 2A--65536KB，64M
		//HUAWEI C8816：98304KB，96M
		//
		int memMB = maxMemory / 1024;
		if(memMB < 70){
			MEM_LEVEL = MEM_LEVEL_NOT_A_PHONE;
			LogInner.print("genius-init: this is not a phone, image will be scaled by normal+3");
		}
		else if(memMB < 100){
			MEM_LEVEL = MEM_LEVEL_BAD;
			LogInner.print("genius-init: this is a bad phone, image will be scaled by normal+2");
		}else if(memMB < 190){
			MEM_LEVEL = MEM_LEVEL_JUST_FINE;
			LogInner.print("genius-init: this phone is just fine, image will be scaled by normal+1");
		}else{
			MEM_LEVEL = MEM_LEVEL_EXERLLENT;
			LogInner.print("genius-init: this phone is good, image will be scaled by normal");
		}
		
		setSDRoot(path);

		x.Ext.init(context);
		x.Ext.setDebug(false); // 是否输出debug日志
	}

	private static void setSDRoot(String path) {
		if (Lang.isEmpty(path))
			path = "genius";
		Ayo.ROOT = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ path;
		if (!Ayo.ROOT.endsWith("/")) {
			Ayo.ROOT += "/";
		}
		
		File dir = new File(Ayo.ROOT);
		if(dir.exists() && dir.isDirectory()){
			LogInner.print("genius-init: work dir-（{path}）".replace("{path}", Ayo.ROOT));
			return;
		}else{
			if(dir.mkdirs()){
				LogInner.print("genius-init: work dir-（{path}）".replace("{path}", Ayo.ROOT));
			}else{
				LogInner.print("genius-init: work dir failed");
			}
		}
	}
	
}
