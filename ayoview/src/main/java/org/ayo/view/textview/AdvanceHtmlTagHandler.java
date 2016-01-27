package org.ayo.view.textview;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;

import org.xml.sax.XMLReader;

/**
 * Created by Administrator on 2016/1/18.
 */
public class AdvanceHtmlTagHandler implements Html.TagHandler {
    private int sIndex = 0;
    private  int eIndex=0;
    private final Context mContext;

    public AdvanceHtmlTagHandler(Context context){
        mContext=context;
    }

    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.toLowerCase().equals("mxgsa")) {
            if (opening) {
                sIndex=output.length();
            }else {
                eIndex=output.length();
                output.setSpan(new MxgsaSpan(), sIndex, eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
    private class MxgsaSpan extends ClickableSpan implements View.OnClickListener {
        @Override
        public void onClick(View widget) {
            // TODO Auto-generated method stub
            //mContext.startActivity(new Intent(mContext,MainActivity.class));
        }
    }

}
