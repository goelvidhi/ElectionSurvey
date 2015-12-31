package com.example.electionsurvey.adapters;

import java.util.List;

import com.example.electionsurvey.R;
import com.example.electionsurvey.bean.MlaBean;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class VoteForMlaListAdapter extends ArrayAdapter<MlaBean>
{

	
	private Context mContext;
	private List<MlaBean> mList;
	private boolean [] mState;
	
	
	private String TAG = "VoteForMlaListAdapter";
	
	public VoteForMlaListAdapter(Context context, int textViewResourceId,
			List<MlaBean> list, boolean [] state) {
		super(context, textViewResourceId, list);
		// TODO Auto-generated constructor stub
		
		mContext = context;
		mList = list;
		mState = state;
		
	}
	
	
	private class ViewHolder
	{
		TextView textView;
		CheckBox checkBox;
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
       
			    
		log("getView");
        if (convertView == null) {
        	log("convertview is null");
        	LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.mla_list_row, null);
            final ViewHolder viewHolder = new ViewHolder();
            
            viewHolder.textView = (TextView) convertView.findViewById(R.id.mla_row_textview);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.mla_row_checkbox);
            
            
            convertView.setTag(viewHolder);
            viewHolder.checkBox.setTag(mList.get(position));
            
        }
        else
           	{
        	log("convertview is not null");
        	((ViewHolder) convertView.getTag()).checkBox.setTag(mList.get(position));
           	}
        

        
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        
        
        holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				MlaBean element =(MlaBean) holder.checkBox.getTag();
				element.setChecked(isChecked);
				
				log("button view id = " + buttonView.getId());
				mState[buttonView.getId()] = isChecked;
			}
		});
      
        holder.textView.setId(position);
        holder.checkBox.setId(position);
        
        holder.textView.setText(mList.get(position).getMla());
        holder.checkBox.setChecked(mList.get(position).isChecked());
        
        return convertView;
    }
	

	private void log(String msg)
	{
		Log.d(TAG, msg);
	}
	

}
