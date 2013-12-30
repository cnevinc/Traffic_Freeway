package com.icareyou.highwaycal;


import com.icareyou.highwaycal.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity{

	WebView webView;
	Button btnSys;
	Button btnRule;
	Button btnBack;
//	Button btnNext;
	Button btnReload;
	Button btnEmail;
	String urlRule="";
	String urlSystem= "http://mfare.fetc.net.tw/way.html";
	private ShareActionProvider mShareActionProvider;
	
	final static String CALL = "android.intent.action.CALL";
	final static String TAG = "nevin";
	
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate menu resource file.
	    getMenuInflater().inflate(R.menu.share_menu, menu);

	    // Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.menu_item_share);

	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) item.getActionProvider();
	    Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, " https://play.google.com/store/apps/details?id=com.icareyou.highwaycal");
        shareIntent.setType("text/plain");
        if (mShareActionProvider != null) 
        	mShareActionProvider.setShareIntent(shareIntent);
	    return true;
	}

	// Call to update the share intent
	private void setShareIntent(Intent shareIntent) {
	    if (mShareActionProvider != null) {
	        mShareActionProvider.setShareIntent(shareIntent);
	    }
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.browser);
		if(webView==null)
			webView = (WebView)this.findViewById(R.id.webview);
		WebSettings webSettings = webView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new Callback());  //HERE IS THE MAIN CHANGE
		webView.loadUrl(urlSystem);
		
		btnSys = (Button)this.findViewById(R.id.system);
		btnSys.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				webView.loadUrl(urlSystem);
			}
		});
		btnRule = (Button)this.findViewById(R.id.rule);
		btnRule.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				webView.loadUrl(urlRule);
			}
		});
		btnBack = (Button)this.findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				webView.goBack();
			}
		});
//		btnNext = (Button)this.findViewById(R.id.next);
//		btnNext.setOnClickListener(new OnClickListener(){
//			public void onClick(View v) {
//				webView.goForward();
//			}
//		});
		btnReload = (Button)this.findViewById(R.id.reload);
		btnReload.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				webView.reload();
			}
		});
		btnEmail = (Button)this.findViewById(R.id.email);
		btnEmail.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				emailFiles();
			}
		});
		
	}
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		webView.saveState(outState);
		
	}
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		webView.restoreState(savedInstanceState);
		
	}
	private void emailFiles() {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		String[] recipients = new String[]{"cnevinchen@gmail.com", "",};
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "[STPGroupon Question]");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
		emailIntent.setType("text/plain");
		startActivity(Intent.createChooser(emailIntent, "Ask a Question"));

		finish();
	}
	private class Callback extends WebViewClient{  //HERE IS THE MAIN CHANGE. 
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return (false);
		}
	}

}
