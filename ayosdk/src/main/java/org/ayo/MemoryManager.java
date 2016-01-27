package org.ayo;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class MemoryManager {
	private static final String TAG = "MemoryManager";
	private static final int MAXMEMORY=200*1024*1024;
	/**
	 * check if the memory is enough, compare to MAXMEMORY
	 * @return
	 */
	public static boolean hasEnoughMemory() {
		long memory = getAvailableInternalMemorySize();
		Log.i(TAG, memory+"");
		if (memory < MAXMEMORY) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 *
	 * @return
	 */
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();// bytes
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 *
	 * @return
	 */
	public static long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	/**
	 *
	 * @return
	 */
	public static long getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			throw new RuntimeException("Don't have sdcard.");
		}
	}

	/**
	 *
	 * @return
	 */
	public static long getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			throw new RuntimeException("Don't have sdcard.");
		}
	}

	/**
	 *
	 * @return
	 */
	public static boolean externalMemoryAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
}
