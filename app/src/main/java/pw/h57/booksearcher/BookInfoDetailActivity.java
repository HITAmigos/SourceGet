package pw.h57.booksearcher;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import hitamigos.sourceget.R;

/**
 * 
 * @author chrishao
 * 这个类是信息显示界面
 */
public class BookInfoDetailActivity extends Activity {
	private TextView mTitle;
	private ImageView mCover;
	private TextView mAuthor;
	private TextView mPublisher;
	private TextView mPublishDate;
	private TextView mISBN;
	private TextView mSummary;
	private Button mButton;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_info_detail_activity);
		initViews();
		initData(getIntent().getParcelableExtra(BookInfo.class.getName()));



	}
	
	private void initViews(){
		mTitle = (TextView)findViewById(R.id.book_detail_title);
		mCover = (ImageView)findViewById(R.id.book_detail_cover);
		mAuthor = (TextView)findViewById(R.id.book_detail_author);
		mPublisher = (TextView)findViewById(R.id.book_detail_publisher);
		mPublishDate = (TextView)findViewById(R.id.book_detail_pubdate);
		mISBN = (TextView)findViewById(R.id.book_detail_isbn);
		mSummary = (TextView)findViewById(R.id.book_detail_summary);
		mButton = (Button) findViewById(R.id.book_search_price);
		
	}
	private class DownloadThread extends Thread {
		private String mId;
		private BookInfoDetailActivity mActivity;
		public DownloadThread(BookInfoDetailActivity activity,String id) {
			super();
			mId = id;
			mActivity = activity;
			
		}

		@Override
		public void run() {
			BookPrice[] bookPrices = (BookPrice[]) Utils.comPrice(mId);
			mActivity.mProgressDialog.dismiss();
			Intent intent = new Intent(BookInfoDetailActivity.this,BookComPriceActivity.class);
			for(int i = 0; i < bookPrices.length; i++){
				intent.putExtra("bookPrice" + i, bookPrices[i]);
				Log.v("AAAAA","2------" + bookPrices[i].getmTitle());
			}
			intent.putExtra("size", bookPrices.length);
			startActivity(intent);
		}
	}
	private void initData(Parcelable data){
		if(data == null){
			return;
		}
		final BookInfo bookInfo = (BookInfo)data;
		
		mTitle.setText(bookInfo.getmTitle());
		mCover.setImageBitmap(bookInfo.getmCover());
		mAuthor.setText(bookInfo.getmAuthor());
		mPublisher.setText(bookInfo.getmPublisher());
		mPublishDate.setText(bookInfo.getmPublishDate());
		mISBN.setText(bookInfo.getmISBN());
		mSummary.setText(bookInfo.getmSummary());
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mProgressDialog = new ProgressDialog(BookInfoDetailActivity.this);
				mProgressDialog.setMessage(getString(R.string.communicating));
				mProgressDialog.show();
				DownloadThread thread = new DownloadThread(BookInfoDetailActivity.this,bookInfo.getmId());
				thread.start();
			}
		});
	}
}
