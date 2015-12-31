package com.example.electionsurvey.adapters;

import java.util.List;

import com.example.electionsurvey.R;
import com.example.electionsurvey.bean.CMBean;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class InputCandidateListAdapter extends ArrayAdapter
{

	
	private Context mContext;
	private List<String> mList;
	
	
	
	private String TAG = "InputCandidateListAdapter";
	
	public InputCandidateListAdapter(Context context, int textViewResourceId,
			List<String> list) {
		super(context, textViewResourceId, list);
		// TODO Auto-generated constructor stub
		
		mContext = context;
		mList = list;
		
		
	}
	
	
	private class ViewHolder
	{
		EditText editText;
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
       
			    
		ViewHolder viewHolder;
		
        if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.candidate_row, null);
            viewHolder = new ViewHolder();
            
            viewHolder.editText = (EditText) convertView.findViewById(R.id.candidate_row);
                       
            convertView.setTag(viewHolder);      
        }
        else
        {
        
        	viewHolder = (ViewHolder)convertView.getTag();
        }
        
 
        viewHolder.editText.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				if(!hasFocus)
				{
					int position = view.getId();
					
					String candidate_name = ((EditText)view).getText().toString();
					mList.set(position, candidate_name);

				}
			}
		});
      
        viewHolder.editText.setId(position);
        viewHolder.editText.setText(mList.get(position).toString());
        
        return convertView;
    }
	

	private void log(String msg)
	{
		Log.d(TAG, msg);
	}
	

}
