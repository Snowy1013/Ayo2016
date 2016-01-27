package org.ayo.view.layout;

import java.util.HashMap;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Scroller;

public class WaterView extends AdapterView<Adapter> implements OnGestureListener {
	private AbsWaterAdapter mAdapter;
	private AdapterDataSetObserver mDataSetObserver;

	/**
	 * 屏幕区最后点击的点
	 */
	private float mLastMotionY;

	private GestureDetector detector;

	/** 
	 * 内容区最后移动的点
	 */
	int finalY = 0;

	private Scroller mScroller;

	private final static int TOUCH_STATE_REST = 0;
	private final static int TOUCH_STATE_SCROLLING = 1;
	private int mTouchSlop;
	private int mTouchState = TOUCH_STATE_REST;
	private Context mContext;

	private int heightPixels = 0;// view的高度

	private int space_horizontal = 5;

	private int space_vertical = 5;

	private int columns;
	private int[] columns_height;
	// 单列宽度和高度
	private int item_width = 0;
	private int item_height = 0;

	private HashMap<Integer, View> recyleViewMap;

	public int getHorizontalSpace() {
		return space_horizontal;
	}

	public void setHorizontalSpace(int space_horizontal) {
		this.space_horizontal = space_horizontal;
	}

	public int getSpaceVertical() {
		return space_vertical;
	}

	public void setSpaceVertical(int space_vertical) {
		this.space_vertical = space_vertical;
	}

	public WaterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public WaterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public WaterView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		mScroller = new Scroller(mContext);
		detector = new GestureDetector(this);
		final ViewConfiguration configuration = ViewConfiguration.get(context);
		// 获得可以认为是滚动的距离
		mTouchSlop = configuration.getScaledTouchSlop();
		recyleViewMap = new HashMap<Integer, View>();
		
//		this.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				AdapterView.OnItemClickListener itemClick = getOnItemClickListener();
//				itemClick.onItemClick(WaterView.this, WaterView.this, 1, 2);
//			}
//		});
	}

	@Override
	public Adapter getAdapter() {
		return null;
	}

	@Override
	public View getSelectedView() {
		return null;
	}

	@Override
	public void setAdapter(Adapter adapter) {
		if (mAdapter != null) {
			mAdapter.unregisterDataSetObserver(mDataSetObserver);
		}

		if (adapter instanceof AbsWaterAdapter) {
			mAdapter = (AbsWaterAdapter) adapter;
			if (mAdapter != null) {
				mDataSetObserver = new AdapterDataSetObserver();
				mAdapter.registerDataSetObserver(mDataSetObserver);
			}
			if (mAdapter == null || mAdapter.getCount() == 0)
				return;
			resetFocus();
		} else {
			throw new IllegalArgumentException("WaterAdapter requred");
		}
	}

	private void resetFocus() {
		recyleViewMap.clear();
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View view = getChildAt(i);
			recyleViewMap.put(i, view);
		}
		removeAllViewsInLayout();
		viewTopMap.clear();
		viewBottomMap.clear();
		for (int i = 0; i < mAdapter.getCount(); i++) {
			makeAndAddView(i, true, null);
		}
		firstLoad = false;
		recyleViewMap.clear();
		requestLayout();
	}

	@Override
	public void setSelection(int position) {
		View view = getChildAt(position);
		int view_top = viewTopMap.get(view) == null ? 0 : viewTopMap.get(view);
		scrollTo(0, view_top);
		mLastMotionY = view_top;
		finalY = view_top;
		postInvalidate();
	}

	private void setupChild(View child, boolean addToEnd, boolean recycle) {
		LayoutParams p = (LayoutParams) child.getLayoutParams();
		if (p == null) {
			p = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0);
		}
		if (recycle)
			attachViewToParent(child, (addToEnd ? -1 : 0), p);
		else
			addViewInLayout(child, (addToEnd ? -1 : 0), p, true);
	}

	private void makeAndAddView(int position, boolean addToEnd, View convertView) {
		View recyleView = recyleViewMap.get(position);
		if (recyleView != null && (mAdapter.getItem(position) == recyleView.getTag())) {
			setupChild(recyleView, addToEnd, false);
			return;
		}
		View view = mAdapter.getView(position, convertView, this);
		view.setTag(mAdapter.getItem(position));
		setupChild(view, addToEnd, false);
	}

	private boolean firstLoad;

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (mAdapter != null) {
			columns = mAdapter.getColumns();
			final int counts = getChildCount();
			for (int i = 0; i < counts; i++) {
				final View child = getChildAt(i);
				int curentItem_width = child.getMeasuredWidth();// layoutParams.width
				int curentItem_height = child.getMeasuredHeight();// layoutParams.height
				int index = getMinCloumnIndex();
				int left = space_horizontal * index + getPaddingLeft() + curentItem_width * index;
				int top = columns_height[index] + space_vertical;
				int right = left + curentItem_width;
				int bottom = top + curentItem_height;
				child.layout(left, top, right, bottom);

				int view_top = top - space_vertical / 2;
				int view_bottom = bottom + space_vertical / 2;
				viewTopMap.put(child, view_top);
				viewBottomMap.put(child, view_bottom);
				columns_height[index] = bottom;

			}

			if (!firstLoad) {
				firstLoad = true;
				resetView();
			}

		}
	}

	private HashMap<View, Integer> viewTopMap = new HashMap<View, Integer>();
	private HashMap<View, Integer> viewBottomMap = new HashMap<View, Integer>();

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 计算列宽
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		if (mAdapter != null) {
			if (columns == 0) {
				columns = mAdapter.getColumns();
			}
			// 初始化列高索引
			columns_height = new int[columns];
			for (int i = 0; i < columns_height.length; i++) {
				columns_height[i] = 0;
			}
			// 计算ViewGroup的可用区域
			int parentUsableWidth = parentWidth - getPaddingLeft() - getPaddingRight() - space_horizontal * (columns - 1);
			if (parentUsableWidth < 0) {
				parentUsableWidth = 0;
			}
			int parentUsableHeight = parentHeight - getPaddingTop() - getPaddingBottom();
			if (parentUsableHeight < 0)
				parentUsableHeight = 0;
			// 单列宽度
			item_width = parentUsableWidth / columns;
			int count = getChildCount();
			for (int i = 0; i < count; i++) {
				View view = getChildAt(i);
				LayoutParams layoutParams = view.getLayoutParams();
				int measuredWidth = layoutParams.width;// view.getMeasuredWidth()
				int measuredHeight = layoutParams.height;// view.getMeasuredHeight()
				// 设置单个view的宽和高
				int makeMeasureSpec_width = MeasureSpec.makeMeasureSpec(item_width, MeasureSpec.EXACTLY);
				item_height = item_width * measuredHeight / measuredWidth;
				if(measuredHeight>0&&measuredWidth >0){
					int makeMeasureSpec_height = MeasureSpec.makeMeasureSpec(item_height, MeasureSpec.EXACTLY);
					view.measure(makeMeasureSpec_width, makeMeasureSpec_height);
				}else{
					int makeMeasureSpec_height = MeasureSpec.makeMeasureSpec(item_height, MeasureSpec.AT_MOST);
					view.measure(makeMeasureSpec_width, makeMeasureSpec_height);
				}
			}
		}
		setMeasuredDimension(parentWidth, parentHeight);// 设置整个view的高度和宽度
	}

	private int getMinCloumnIndex() {
		int index = 0;
		for (int i = 0; i < columns_height.length; i++) {
			if (columns_height[index] > columns_height[i]) {
				index = i;
			}
		}
		return index;
	}

	public int getMaxCloumnHeight() {
		int maxHeight = 0;
		for (int i = 0; i < columns_height.length; i++) {
			if (maxHeight < columns_height[i]) {
				maxHeight = columns_height[i];
			}
		}
		return maxHeight;
	}

	private class DelayRunnable implements Runnable {
		public void run() {
			resetView();
			myDelayRunnable = null;
		}
	}

	private volatile DelayRunnable myDelayRunnable;

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			// 返回当前滚动Y方向的偏移
			scrollTo(0, mScroller.getCurrY());
			postInvalidate();
			if (myDelayRunnable == null) {
				myDelayRunnable = new DelayRunnable();
			} else {
				removeCallbacks(myDelayRunnable);
				myDelayRunnable = new DelayRunnable();
			}
			postDelayed(myDelayRunnable, 600);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final float y = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionY = y;
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
			break;
		case MotionEvent.ACTION_MOVE:
			final int yDiff = (int) Math.abs(y - mLastMotionY);
			boolean yMoved = yDiff > mTouchSlop;
			// 判断是否是移动
			if (yMoved) {
				mTouchState = TOUCH_STATE_SCROLLING;
			}
			break;
		case MotionEvent.ACTION_UP:
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return mTouchState == TOUCH_STATE_SCROLLING;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		final float y = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished()) {
				mScroller.forceFinished(true);
				finalY = mScroller.getFinalY();
			}
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			if (ev.getPointerCount() == 1) {
				int deltaY = 0;
				deltaY = (int) (mLastMotionY - y);
				mLastMotionY = y;
				if (deltaY < 0) {
					if (finalY > 0) {
						int move_this = Math.max(-finalY, deltaY);
						finalY = finalY + move_this;
						scrollBy(0, move_this);
					}
				} else if (deltaY > 0) {
					heightPixels = getHeight();
					if (getMaxCloumnHeight() - finalY - heightPixels > 0) {
						int move_this = Math.min(getMaxCloumnHeight() - finalY - heightPixels, deltaY);
						finalY = finalY + move_this;
						scrollBy(0, move_this);
					}
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			mTouchState = TOUCH_STATE_REST;
			resetView();
			break;
		}
		return this.detector.onTouchEvent(ev);
	}

	public void resetView() {
		int currentHeightTop = getScrollY();
		int currentHeightBottom = getScrollY() + getHeight();
		int count = getChildCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				View view = getChildAt(i);
				int view_top = viewTopMap.get(view) == null ? -1 : viewTopMap.get(view);
				int view_bottom = viewBottomMap.get(view) == null ? -1 : viewBottomMap.get(view);
				if (view_top == -1 || view_bottom == -1) {
					return;
				}
				if ((view_bottom < currentHeightTop || view_top > currentHeightBottom)) {
					if (onResetViewDataListener != null) {
						onResetViewDataListener.onRecycleData(view);
					}
				}

				if ((view_top >= currentHeightTop && view_top <= currentHeightBottom)
						|| (view_bottom >= currentHeightTop && view_bottom <= currentHeightBottom)) {
					if (onResetViewDataListener != null) {
						onResetViewDataListener.onLoadData(view);
					}
				}
			}
		}
	}

	/**
	 * activity onPause的时候调用此方法，以释放所有占用的资源
	 */
	public void onPause() {
		postDelayed(new Runnable() {

			@Override
			public void run() {
				int count = getChildCount();
				if (count > 0) {
					for (int i = 0; i < count; i++) {
						View view = getChildAt(i);
						if (onResetViewDataListener != null) {
							onResetViewDataListener.onRecycleData(view);
						}
					}
				}

			}
		}, 500);

	}

	/**
	 * activity onPause的时候调用此方法，以加载需要加载的资源
	 */
	public void onResume() {
		postDelayed(new Runnable() {

			@Override
			public void run() {
				resetView();
			}
		}, 500);

	}

	public OnResetViewDataListener onResetViewDataListener;

	public void setOnResetViewDataListener(OnResetViewDataListener listener) {
		onResetViewDataListener = listener;
	}

	public interface OnResetViewDataListener {
		public void onLoadData(View view);

		public void onRecycleData(View view);

	}


	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		int slow = -(int) velocityY * 3 / 4;
		mScroller.fling(0, finalY, 0, slow, 0, 0, 0, getMaxCloumnHeight() - getHeight());
		postInvalidate();
		finalY = mScroller.getFinalY();
		mTouchState = TOUCH_STATE_REST;
		return false;
	}

	public boolean onDown(MotionEvent e) {
		return true;
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	public void onShowPress(MotionEvent e) {
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	public void onLongPress(MotionEvent e) {
	}

	class AdapterDataSetObserver extends DataSetObserver {

		@Override
		public void onChanged() {
			resetFocus();
		}

		@Override
		public void onInvalidated() {
		}

	}

}
