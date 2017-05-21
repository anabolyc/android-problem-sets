package course.labs.todomanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ToDoListAdapter extends BaseAdapter {

	// List of ToDoItems
	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {
		mContext = context;
	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed
	public void add(ToDoItem item) {
		mItems.add(item);
		notifyDataSetChanged();
	}
	
	// Clears the list adapter of all items.
	public void clear(){
		mItems.clear();
		notifyDataSetChanged();
	}

	// Returns the number of ToDoItems
	@Override
	public int getCount() {
		return mItems.size();
	}

	// Retrieve ToDoItem
	@Override
	public ToDoItem getItem(int pos) {
		return mItems.get(pos);
	}

	// Get the ID for the ToDoItem
	@Override
	public long getItemId(int pos) {
		return pos;
	}

	//Create a View to display the ToDoItem at specified position in mItems
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ToDoItem toDoItem = this.getItem(position);
		
		LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemLayout = li.inflate(R.layout.todo_item, parent, false);

		final TextView titleView = (TextView)itemLayout.findViewById(R.id.titleView);
		titleView.setText(toDoItem.getTitle());
		
		final CheckBox statusView = (CheckBox)itemLayout.findViewById(R.id.statusCheckBox);
		statusView.setChecked(toDoItem.getStatus() == ToDoItem.Status.DONE);
		
		statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				log("Entered onCheckedChanged()");
	
				ToDoItem.Status status = isChecked ? ToDoItem.Status.DONE : ToDoItem.Status.NOTDONE;
				toDoItem.setStatus(status);
			}
		});

		final TextView priorityView = (TextView)itemLayout.findViewById(R.id.PriorityLabel);
		priorityView.setText(toDoItem.getPriority().toString());

		final TextView dateView = (TextView)itemLayout.findViewById(R.id.DateLabel);
		dateView.setText(ToDoItem.FORMAT.format(toDoItem.getDate()));		

		return itemLayout;
	}
	
	private void log(String msg) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.i(TAG, msg);
	}

}
