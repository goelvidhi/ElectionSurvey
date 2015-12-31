package com.example.electionsurvey.adapters;

import java.util.List;

import com.example.electionsurvey.R;
import com.example.electionsurvey.bean.PartyBean;

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
import android.widget.ImageView;
import android.widget.TextView;

public class VoteForPartyListAdapter extends ArrayAdapter<PartyBean>
{

	
	private Context mContext;
	private List<PartyBean> mList;
	private boolean [] mState;
	private Integer [] mImages;
	
	private String TAG = "VoteForPartyListAdapter";
	public VoteForPartyListAdapter(Context context, int textViewResourceId,
			List<PartyBean> list, boolean [] state, Integer [] images) {
		super(context, textViewResourceId, list);
		// TODO Auto-generated constructor stub
		
		mContext = context;
		mList = list;
		mState = state;
		mImages = images;
	}
	
	
	private class ViewHolder
	{
		TextView textView;
		CheckBox checkBox;
		ImageView imageView;
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
       
		   
        if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.party_list_row, null);
            final ViewHolder viewHolder = new ViewHolder();
            
            viewHolder.textView = (TextView) convertView.findViewById(R.id.party_row_textview);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.party_row_checkbox);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.party_row_imageview);
            
            
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
				PartyBean element =(PartyBean) holder.checkBox.getTag();
				element.setChecked(isChecked);
				
				log("button view id = " + buttonView.getId());
				mState[buttonView.getId()] = isChecked;
			}
		});
      
        holder.textView.setId(position);
        holder.checkBox.setId(position);
        
        holder.textView.setText(mList.get(position).getParty());
        holder.checkBox.setChecked(mList.get(position).isChecked());
       holder.imageView.setImageDrawable(mContext.getResources().getDrawable(mImages[position]));
        
        
        return convertView;
    }
	

	private void log(String msg)
	{
		Log.d(TAG, msg);
	}
	

}
