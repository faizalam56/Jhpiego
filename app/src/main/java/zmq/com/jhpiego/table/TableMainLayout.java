package zmq.com.jhpiego.table;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zmq.com.jhpiego.activity.PathActivity;
import zmq.com.jhpiego.database.MyDatabaseAdapter;


public class TableMainLayout extends RelativeLayout {

	public final String TAG = "TableMainLayout.java";
	
	// set the header titles
//	String headers[] = {
//		"ColumnA 1 \n multi-lines",
//		"ColumnA 2",
//		"ColumnA 3",
//		"ColumnA 4",
//		"ColumnA 5",
//		"ColumnA 6",
//		"ColumnA 7",
//		"ColumnA 8",
//		"ColumnA 9"
//	};

	TableLayout tableA;
	TableLayout tableB;
	TableLayout tableC;
	TableLayout tableD;
	
	HorizontalScrollView horizontalScrollViewB;
	HorizontalScrollView horizontalScrollViewD;

	ScrollView scrollViewC;
	ScrollView scrollViewD;
	
	Context context;
	
	List<SampleObject>sampleObjects ;
	
	String headers[] = {
			"Date",
			"LaunchTime",
			"Sessions",
			"Start Time",
			"Gender",
			"End Time",
			"Relation",
			"Risk",
			"Impulse",
			"Knoledge",
			"Bonus",
			"Path",
			"Decision 1",
			"Decision 2",
			"Decision 3",
			"Decision 4",
			"Decision 5",
			"Decision 6",
			"Decision 7",
			"Decision 8",
			"Decision 9",
			"Decision 10",
			"Decision 11",
			"Decision 12",
			"Decision 13",
			"Decision 14",
			"Decision 15"};

	int  HeaderIndex1[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27};
	String headerName[];
	String tableValues[][];
	int noOfRows;
	int headerCellsWidth[] = new int[headers.length];
	
	public TableMainLayout(Context context, AttributeSet attrs) {
	    super(context, attrs);
	   
	    this.context = context;
		
		// initialize the main components (TableLayouts, HorizontalScrollView, ScrollView)
		
		sampleObjects = this.sampleObjects();
		this.initComponents();
		this.setComponentsId();
		this.setScrollViewAndHorizontalScrollViewTag();
		
		
		// no need to assemble component A, since it is just a table
		this.horizontalScrollViewB.addView(this.tableB);
		
		this.scrollViewC.addView(this.tableC);
		
		this.scrollViewD.addView(this.horizontalScrollViewD);
		this.horizontalScrollViewD.addView(this.tableD);
		
		// add the components to be part of the main layout
		this.addComponentToMainLayout();
		//this.setBackgroundColor(Color.RED);
		
		
		// add some table rows
		this.addTableRowToTableA();
		this. addTableRowToTableB();
		
		this.resizeHeaderHeight();
		
		this.getTableRowHeaderCellWidth();
		
		this.generateTableC_AndTable_B();
		
		this.resizeBodyTableRowHeight();
		
	    }
	
	public TableMainLayout(Context context) {
		
		super(context);
		
		this.context = context;
		
		// initialize the main components (TableLayouts, HorizontalScrollView, ScrollView)
		
		sampleObjects = this.sampleObjects();
		this.initComponents();
		this.setComponentsId();
		this.setScrollViewAndHorizontalScrollViewTag();
		
		
		// no need to assemble component A, since it is just a table
		this.horizontalScrollViewB.addView(this.tableB);
		
		this.scrollViewC.addView(this.tableC);
		
		this.scrollViewD.addView(this.horizontalScrollViewD);
		this.horizontalScrollViewD.addView(this.tableD);
		
		// add the components to be part of the main layout
		this.addComponentToMainLayout();
		//this.setBackgroundColor(Color.RED);
		
		
		// add some table rows
		this.addTableRowToTableA();
		this. addTableRowToTableB();
		
		this.resizeHeaderHeight();
		
		this.getTableRowHeaderCellWidth();
		
		this.generateTableC_AndTable_B();
		
		this.resizeBodyTableRowHeight();
	}

	// this is just the sample data
	List<SampleObject> sampleObjects(){
		retrieveDataFromTable();
		List<SampleObject> sampleObjects = new ArrayList<SampleObject>();
		
		for(int x=0; x < noOfRows; x++){
			
			SampleObject sampleObject = new SampleObject(

					tableValues[x][0],
					tableValues[x][1],
					tableValues[x][2],
					tableValues[x][3],
					tableValues[x][4],
					tableValues[x][5],
					tableValues[x][6],
					tableValues[x][7],
					tableValues[x][8],
					tableValues[x][9],
					tableValues[x][10],
					tableValues[x][11],
					tableValues[x][12],
					tableValues[x][13],
					tableValues[x][14],
					tableValues[x][15],
					tableValues[x][16],
					tableValues[x][17],
					tableValues[x][18],
					tableValues[x][19],
					tableValues[x][20],
					tableValues[x][21],
					tableValues[x][22],
					tableValues[x][23],
					tableValues[x][24],
					tableValues[x][25],
					tableValues[x][26]



			);			
			sampleObjects.add(sampleObject);
		}		
		return sampleObjects;	
	}
	
	private void retrieveDataFromTable() {
	// TODO Auto-generated method stub
	MyDatabaseAdapter dbAdapter = new MyDatabaseAdapter(context);
	
	dbAdapter.openToRead();
	Cursor c = dbAdapter.selectData(MyDatabaseAdapter.DATABASE_TABLE_1, null, null, null, null, null);
	
	int noOfColumns = c.getColumnCount();
	noOfRows = c.getCount();
	headerName = new String[noOfColumns];
	tableValues = new String[noOfRows][noOfColumns];
	
	for(int i = 0; i < noOfColumns; i++){
		headerName[i] = c.getColumnName(i);
	}
	int index = c.getColumnIndex(headerName[0]);		
	
	if (c != null ) {
		
           if  (c.moveToFirst()) {
        	   
        	   for(int j = 0; j < noOfRows; j++){

       			for(int colm = 0; colm < headers.length; colm++){

       				tableValues[j][colm] = c.getString(HeaderIndex1[colm]);
					System.out.println(tableValues[j][colm]+ " ---- "+ HeaderIndex1[colm]);
       			}
       			c.moveToNext();
       		}
           }
           c.close();
	}
	dbAdapter.close();
}
	
	
	
	// initalized components 
	private void initComponents(){
		this.tableA = new TableLayout(this.context); 
		this.tableB = new TableLayout(this.context); 
		this.tableC = new TableLayout(this.context); 
		this.tableD = new TableLayout(this.context);
		
		this.horizontalScrollViewB = new MyHorizontalScrollView(this.context);
		this.horizontalScrollViewD = new MyHorizontalScrollView(this.context);
		
		this.scrollViewC = new MyScrollView(this.context);
		this.scrollViewD = new MyScrollView(this.context);
		
		
		
	//	this.tableA.setBackgroundColor(Color.GREEN);
	//	this.horizontalScrollViewB.setBackgroundColor(Color.LTGRAY);
		
	}
	
	// set essential component IDs
	private void setComponentsId(){
		this.tableA.setId(1);
		this.horizontalScrollViewB.setId(2);
		this.scrollViewC.setId(3);
		this.scrollViewD.setId(4);
	}
	
	// set tags for some horizontal and vertical scroll view
	private void setScrollViewAndHorizontalScrollViewTag(){
		
		this.horizontalScrollViewB.setTag("horizontal scroll view b");
		this.horizontalScrollViewD.setTag("horizontal scroll view d");
		
		this.scrollViewC.setTag("scroll view c");
		this.scrollViewD.setTag("scroll view d");
	}
	
	// we add the components here in our TableMainLayout
	private void addComponentToMainLayout(){
		
		// RelativeLayout params were very useful here
		// the addRule method is the key to arrange the components properly
		LayoutParams componentB_Params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		componentB_Params.addRule(RelativeLayout.RIGHT_OF, this.tableA.getId());
		
		LayoutParams componentC_Params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		componentC_Params.addRule(RelativeLayout.BELOW, this.tableA.getId());
		
		LayoutParams componentD_Params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		componentD_Params.addRule(RelativeLayout.RIGHT_OF, this.scrollViewC.getId());
		componentD_Params.addRule(RelativeLayout.BELOW, this.horizontalScrollViewB.getId());
		
		// 'this' is a relative layout, 
		// we extend this table layout as relative layout as seen during the creation of this class
		
		this.addView(this.tableA);
		this.addView(this.horizontalScrollViewB, componentB_Params);
		this.addView(this.scrollViewC, componentC_Params);
		this.addView(this.scrollViewD, componentD_Params);
			
	}
	

	
	private void addTableRowToTableA(){
		this.tableA.addView(this.componentATableRow());
	}
	
	private void addTableRowToTableB(){
		this.tableB.addView(this.componentBTableRow());
	}
	
	// generate table row of table A //**asif A  first left corner
	TableRow componentATableRow(){
		
		TableRow componentATableRow = new TableRow(this.context);
		TextView textView = this.headerTextView(this.headers[0]);
		textView.setBackgroundColor(Color.rgb(138, 92, 47));
		textView.setTextColor(Color.WHITE);
		textView.setTypeface(null, Typeface.BOLD);
		textView.setWidth(150);//getResources().getDisplayMetrics().widthPixels/headers.length);
		componentATableRow.addView(textView);
		
		return componentATableRow;
	}
	
	// generate table row of table B //**asif B  Header of Columns
	TableRow componentBTableRow(){
		
		TableRow componentBTableRow = new TableRow(this.context);
		int headerFieldCount = this.headers.length;
		
		TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,60);
		params.setMargins(2, 0, 0, 0);
		
		for(int x=0; x<(headerFieldCount-1); x++){
			TextView textView = this.headerTextView(this.headers[x+1]);
			textView.setLayoutParams(params);
			textView.setBackgroundColor(Color.rgb(138, 92, 47));
			textView.setTextColor(Color.WHITE);
			textView.setTypeface(null, Typeface.BOLD);
			textView.setWidth(150);//getResources().getDisplayMetrics().widthPixels/headers.length);
			componentBTableRow.addView(textView);
		}
		
		return componentBTableRow;
	}
	
	// generate table row of table C and table D
	private void generateTableC_AndTable_B(){
		
		// just seeing some header cell width
		for(int x=0; x<this.headerCellsWidth.length; x++){
			Log.v("TableMainLayout.java", this.headerCellsWidth[x]+"");
		}

		int j=1;
		for(SampleObject sampleObject : this.sampleObjects){
			
			
			TableRow tableRowForTableC = this.tableRowForTableC(sampleObject,j);
			TableRow taleRowForTableD = this.taleRowForTableD(sampleObject,j);

			
			if(j%2 !=0){
				tableRowForTableC.setBackgroundColor(Color.parseColor("#ce9c62"));
				taleRowForTableD.setBackgroundColor(Color.parseColor("#ce9c62"));
			}
			else{
				tableRowForTableC.setBackgroundColor(Color.parseColor("#b78856"));
				taleRowForTableD.setBackgroundColor(Color.parseColor("#b78856"));

			}
			this.tableC.addView(tableRowForTableC);
			this.tableD.addView(taleRowForTableD);
            taleRowForTableD.setId(j);
		j++;


//            taleRowForTableD.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    //Toast.makeText(context,"table Row D",Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(context, PathActivity.class);
//                    MyDatabaseAdapter dbAdapter = new MyDatabaseAdapter(context);
//
//                    dbAdapter.openToRead();
//
//                    Cursor c = dbAdapter.selectData(MyDatabaseAdapter.DATABASE_TABLE_1, null, "ROWID = "+(v.getId()), null, null, null);
//                    int count = c.getCount();
//
//                    if (c != null ) {
//
//                        //	if  (c.moveToFirst()) {
//                        c.moveToFirst();
////                        int st1 = c.getColumnIndex("relation");
////                        String st21 = c.getString(2);
////                        String st = c.getString(st1);
//
//                        intent.putExtra("Relation", c.getString(c.getColumnIndex("relation")));
//                        intent.putExtra("Risk", c.getString(c.getColumnIndex("risk")));
//                        intent.putExtra("Impulse", c.getString(c.getColumnIndex("impulse")));
//                        intent.putExtra("Knoledge", c.getString(c.getColumnIndex("knoledge")));
//						intent.putExtra("Bonus", c.getString(c.getColumnIndex("bonus")));
//
//                        //	}
//                        c.close();
//                    }
//                    dbAdapter.close();
//
//                    context.startActivity(intent);
//                }
//            });
		}
	}
	
	// a TableRow for table C
	TableRow tableRowForTableC(SampleObject sampleObject, int j){
		
		TableRow.LayoutParams params = new TableRow.LayoutParams( this.headerCellsWidth[0],50);
		params.setMargins(0, 2, 0, 0);
		
		TableRow tableRowForTableC = new TableRow(this.context);
		TextView textView = this.bodyTextView(sampleObject.header1);
		if(j%2 != 0)
			textView.setBackgroundColor(Color.parseColor("#ce9c62"));
			else{
				textView.setBackgroundColor(Color.parseColor("#b78856"));
			}
		textView.setTextColor(Color.BLACK);
		
		tableRowForTableC.addView(textView,params);
		
		return tableRowForTableC;
	}
	
	TableRow taleRowForTableD(SampleObject sampleObject, int i){

		TableRow taleRowForTableD = new TableRow(this.context);
		
		
		int loopCount = ((TableRow)this.tableB.getChildAt(0)).getChildCount();
		String info[] = {


				sampleObject.header2,
				sampleObject.header3,
				sampleObject.header4,
				sampleObject.header5,
				sampleObject.header6,
				sampleObject.header7,
				sampleObject.header8,
				sampleObject.header9,
				sampleObject.header10,
				sampleObject.header11,
				sampleObject.header12,
				sampleObject.header13,
				sampleObject.header14,
				sampleObject.header15,
				sampleObject.header16,
				sampleObject.header17,
				sampleObject.header18,
				sampleObject.header19,
				sampleObject.header20,
				sampleObject.header21,
				sampleObject.header22,
				sampleObject.header23,
				sampleObject.header24,
				sampleObject.header25,
				sampleObject.header26,
				sampleObject.header27,
		};
		
		for(int x=0 ; x<loopCount; x++){
			
			// //**asif Row ForTable D
			
			TableRow.LayoutParams params = new TableRow.LayoutParams( headerCellsWidth[x+1],50);
			params.setMargins(2, 2, 0, 0);
			
			TextView textViewB = this.bodyTextView(info[x]);
			
			if(i%2 != 0)
			textViewB.setBackgroundColor(Color.parseColor("#ce9c62"));
			else{
				textViewB.setBackgroundColor(Color.parseColor("#b78856"));
			}
			textViewB.setTextColor(Color.rgb(0, 0, 0));
			taleRowForTableD.addView(textViewB,params);
		}
		
		return taleRowForTableD;
		
	}
	
	// table cell standard TextView
	TextView bodyTextView(String label){
		
		TextView bodyTextView = new TextView(this.context);
		bodyTextView.setBackgroundColor(Color.WHITE);
		bodyTextView.setText(label);
		bodyTextView.setGravity(Gravity.CENTER);
		bodyTextView.setPadding(5, 5, 5, 5);
		
		return bodyTextView;
	}
	
	// header standard TextView
	TextView headerTextView(String label){
		
		TextView headerTextView = new TextView(this.context);
		headerTextView.setBackgroundColor(Color.WHITE);
		headerTextView.setText(label);
		headerTextView.setGravity(Gravity.CENTER);
		headerTextView.setPadding(5, 5, 5, 5);
		
		return headerTextView;
	}
	
	// resizing TableRow height starts here
	void resizeHeaderHeight() {
		
		TableRow productNameHeaderTableRow = (TableRow) this.tableA.getChildAt(0);
		TableRow productInfoTableRow = (TableRow)  this.tableB.getChildAt(0);

		int rowAHeight = this.viewHeight(productNameHeaderTableRow);
		int rowBHeight = this.viewHeight(productInfoTableRow);

		TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
		int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;

		this.matchLayoutHeight(tableRow, finalHeight);
	}
	
	void getTableRowHeaderCellWidth(){
		
		int tableAChildCount = ((TableRow)this.tableA.getChildAt(0)).getChildCount();
		int tableBChildCount = ((TableRow)this.tableB.getChildAt(0)).getChildCount();;
		
		for(int x=0; x<(tableAChildCount+tableBChildCount); x++){
			
//			if(x==0){
//				this.headerCellsWidth[x] = this.viewWidth(((TableRow)this.tableA.getChildAt(0)).getChildAt(x));
//			}else{
//				this.headerCellsWidth[x] = this.viewWidth(((TableRow)this.tableB.getChildAt(0)).getChildAt(x-1));
//			}
			
			if(x==0){
				
				// ** asif width of row 
				
				this.headerCellsWidth[x] = 150; //getResources().getDisplayMetrics().widthPixels/headers.length;
			//	this.headerCellsWidth[x] = this.viewWidth(((TableRow)this.tableA.getChildAt(0)).getChildAt(x));
			}else{
				this.headerCellsWidth[x] = 150; //getResources().getDisplayMetrics().widthPixels/headers.length;
			}
			
		}
	}
	
	// resize body table row height
	void resizeBodyTableRowHeight(){
		
		int tableC_ChildCount = this.tableC.getChildCount();
		
		for(int x=0; x<tableC_ChildCount; x++){
		
			TableRow productNameHeaderTableRow = (TableRow) this.tableC.getChildAt(x);
			TableRow productInfoTableRow = (TableRow)  this.tableD.getChildAt(x);
	
			int rowAHeight = this.viewHeight(productNameHeaderTableRow);
			int rowBHeight = this.viewHeight(productInfoTableRow);
	
			TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
			int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;

			this.matchLayoutHeight(tableRow, finalHeight);		
		}
		
	}
	
	// match all height in a table row
	// to make a standard TableRow height
	private void matchLayoutHeight(TableRow tableRow, int height) {
		
		int tableRowChildCount = tableRow.getChildCount();
		
		// if a TableRow has only 1 child
		if(tableRow.getChildCount()==1){
			
			View view = tableRow.getChildAt(0);
			TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
			params.height = height - (params.bottomMargin + params.topMargin);
			
			return ;
		}
		
		// if a TableRow has more than 1 child
		for (int x = 0; x < tableRowChildCount; x++) {
			
			View view = tableRow.getChildAt(x);
			
			TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();

			if (!isTheHeighestLayout(tableRow, x)) {
				params.height = height - (params.bottomMargin + params.topMargin);
				return;
			}
		}

	}

	// check if the view has the highest height in a TableRow
	private boolean isTheHeighestLayout(TableRow tableRow, int layoutPosition) {
		
		int tableRowChildCount = tableRow.getChildCount();
		int heighestViewPosition = -1;
		int viewHeight = 0;

		for (int x = 0; x < tableRowChildCount; x++) {
			View view = tableRow.getChildAt(x);
			int height = this.viewHeight(view);

			if (viewHeight < height) {
				heighestViewPosition = x;
				viewHeight = height;
			}
		}

		return heighestViewPosition == layoutPosition;
	}

	// read a view's height
	private int viewHeight(View view) {
		view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		return view.getMeasuredHeight();
	}

	// read a view's width
	private int viewWidth(View view) {
		view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		return view.getMeasuredWidth();
	}

	// horizontal scroll view custom class
	class MyHorizontalScrollView extends HorizontalScrollView{

		public MyHorizontalScrollView(Context context) {
			super(context);
		}
		
		@Override
		protected void onScrollChanged(int l, int t, int oldl, int oldt) {
			String tag = (String) this.getTag();
			
			if(tag.equalsIgnoreCase("horizontal scroll view b")){
				horizontalScrollViewD.scrollTo(l, 0);
			}else{
				horizontalScrollViewB.scrollTo(l, 0);
			}
		}		
	}

	// scroll view custom class
	class MyScrollView extends ScrollView{

		public MyScrollView(Context context) {
			super(context);
		}
		
		@Override
		protected void onScrollChanged(int l, int t, int oldl, int oldt) {
			
			String tag = (String) this.getTag();
			
			if(tag.equalsIgnoreCase("scroll view c")){
				scrollViewD.scrollTo(0, t);
			}else{
				scrollViewC.scrollTo(0, t);
			}
		}
	}

	
}
