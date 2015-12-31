package com.example.electionsurvey.adapters;

import java.util.List;

import com.example.electionsurvey.R;
import com.example.electionsurvey.bean.CMBean;

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

public class VoteForCMListAdapter extends ArrayAdapter<CMBean>
{

	
	private Context mContext;
	private List<CMBean> mList;
	private boolean [] mState;
	
	
	private String TAG = "VoteForCMListAdapter";
	
	public VoteForCMListAdapter(Context context, int textViewResourceId,
			List<CMBean> list, boolean [] state) {
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
       
			    
        if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.cm_list_row, null);
            final ViewHolder viewHolder = new ViewHolder();
            
            viewHolder.textView = (TextView) convertView.findViewById(R.id.cm_row_textview);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.cm_row_checkbox);
            
            
            convertView.setTag(viewHolder);
            viewHolder.checkBox.setTag(mList.get(position));
            
        }
        else
           	{
        	((ViewHolder) convertView.getTag()).checkBox.setTag(mList.get(position));
           	}
        

        
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        
        
        holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				CMBean element =(CMBean) holder.checkBox.getTag();
				element.setChecked(isChecked);
				
				log("button view id = " + buttonView.getId());
				mState[buttonView.getId()] = isChecked;
			}
		});
      
        holder.textView.setId(position);
        holder.checkBox.setId(position);
        
        holder.textView.setText(mList.get(position).getCm());
        holder.checkBox.setChecked(mList.get(position).isChecked());
        
        return convertView;
    }
	

	private void log(String msg)
	{
		Log.d(TAG, msg);
	}
	

}
