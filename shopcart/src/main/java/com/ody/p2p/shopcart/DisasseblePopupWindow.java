package com.ody.p2p.shopcart;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


public class DisasseblePopupWindow extends PopupWindow {

	public DisasseblePopupWindow(Context ctx,SeparateBean sb) {
		// TODO Auto-generated constructor stub
		
		LayoutInflater layout=LayoutInflater.from(ctx);
		final View view=layout.inflate(R.layout.disasseble_view, null);
		TextView tvGoShop=(TextView)view.findViewById(R.id.layoutGoShop);
		
		ListView lvContent=(ListView)view.findViewById(R.id.lvContent);
		if(sb!=null){
			DisassebleAdapter<SeparateBean.ProductList> adapter=new DisassebleAdapter<SeparateBean.ProductList>(sb.getData().getCheckoutGroups(), ctx,this);
			lvContent.setAdapter(adapter);
		}
		
		tvGoShop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		
		this.setContentView(view);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.AnimBottom);
		ColorDrawable dw=new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		
	}
	
}
