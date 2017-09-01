package com.muzikun.lhfsyczl.view.htmltextview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.AttributeSet;

import java.io.InputStream;

/**
 * 功能描述：
 */

public class HtmlTextView extends AppCompatTextView {
    public static final String TAG = "HtmlTextView";
    public static final boolean DEBUG = false;

    public HtmlTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context) {
        super(context);
    }

    /**
     * @param is
     * @return
     */
    static private String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");

        return s.hasNext() ? s.next() : "";
    }

    /**
     * Parses String containing HTML to Android's Spannable format and displays
     * it in this TextView.
     *
     * @param html String containing HTML, for example: "<b>Hello world!</b>"
     */
    public void setHtmlFromString(String html, boolean useLocalDrawables) {
        Html.ImageGetter imgGetter;
        if (useLocalDrawables) {
            imgGetter = new LocalImageGetter(getContext());//使用glide，本地解析和网络解析一致
        } else {
            imgGetter = new UrlImageGetter(this, getContext());
        }

        // this uses Android's Html class for basic parsing, and HtmlTagHandler
        setText(Html.fromHtml(html, imgGetter, new HtmlTagHandler()));

        // make links work为草连接加上解析
//        setMovementMethod(LinkMovementMethod.getInstance());
        setTextColor(getResources().getColor(android.R.color.secondary_text_dark_nodisable));
    }
}
