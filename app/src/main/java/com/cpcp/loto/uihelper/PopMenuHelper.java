package com.cpcp.loto.uihelper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 自定义PopMenu容器类，将布局view装入即可用，Activity中示例如下：
 * final PopupWindow window = PopMenuHelper.newBasicPopupWindow(this.getActivity());
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.popmenu_setting_bg,null);
			TextView tv_photo=(TextView) view.findViewById(R.id.popmenu_setting_tv_photo);
			TextView tv_camera=(TextView) view.findViewById(R.id.popmenu_setting_tv_camera);
			window.setContentView(view);
			window.showAsDropDown(v, 0, 0);
 * @author 
 */
public class PopMenuHelper {

	public static PopupWindow newBasicPopupWindow(Context context) {
		final PopupWindow window = new PopupWindow(context);
		
		// when a touch even happens outside of the window
		// make the window go away
		window.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					window.dismiss();
					return true;
				}
				return false;
			}
		});
		window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);           
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);  
		window.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		window.setTouchable(true);
		window.setFocusable(true);
		window.setOutsideTouchable(true);
		ColorDrawable colorDrawable=new ColorDrawable(Color.argb(0, 0, 0, 0));
		//window.set
		window.setBackgroundDrawable(colorDrawable);		
		
		return window;
	}
	
	/**
	 * Displays like a QuickAction from the anchor view.
	 * 
	 * @param xOffset
	 *            offset in the X direction
	 * @param yOffset
	 *            offset in the Y direction
	 */
	public static void showLikeQuickAction(PopupWindow window, View root, View anchor, WindowManager windowManager, int xOffset, int yOffset) {

		

		int[] location = new int[2];
		anchor.getLocationOnScreen(location);

		Rect anchorRect =
				new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1]
					+ anchor.getHeight());
		
		root.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		int rootWidth = root.getMeasuredWidth();
		int rootHeight = root.getMeasuredHeight();
		
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();

		int xPos = ((screenWidth - rootWidth) / 2) + xOffset;
		int yPos = anchorRect.top - rootHeight + yOffset;

		window.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
	}
	
}