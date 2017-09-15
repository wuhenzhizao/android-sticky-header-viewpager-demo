package flier.love.utils;

import android.util.Log;


/**
 * 类描述：日志管理类
 *
 * @author Fei Liu
 * 
 * @creation 2014-4-23
 */
public class LogUtils {
	private static final String TAG = "LOVESPACE";
	// 是否打印日志标志
	private static boolean isOpenLog = true;
	
	public static void openLog(boolean flag){
		isOpenLog = flag;
	}
	
	// 打印调试信息
	public static void d(String log){
		if (isOpenLog) Log.d(TAG, log);
	}
	
	// 打印错误信息
	public static void e(String log){
		if (isOpenLog) Log.e(TAG, log);
	}
}
