package com.example.translateapp;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Button eBtn = (Button)findViewById(R.id.btnEng);
		eBtn.setOnClickListener(this);
		Button jBtn = (Button)findViewById(R.id.btnJap);
		jBtn.setOnClickListener(this);
	}


	@Override
	public void onClick(View v){
		//https://datamarket.azure.com/dataset/bing/microsofttranslator
		//事前にユーザIDとパスワードの作成が必要。
		
		Translate.setClientId("***");//***にユーザIDを入力
		Translate.setClientSecret("+++");//+++にパスワードを入力

		EditText originalTextET = (EditText)findViewById(R.id.original_text);
		EditText translatedTextET = (EditText)findViewById(R.id.translated_text);
		String orihinakTextStr = originalTextET.getText().toString();

		Language language1 = Language.JAPANESE;
		Language language2 = Language.ENGLISH;
		switch (v.getId()){

		case R.id.btnEng:          // From Japanese -> Into English
			translate(translatedTextET, orihinakTextStr, language1, language2, v);
			break;

		case R.id.btnJap:         // From English -> Into Japaneses
			language1 = Language.ENGLISH;
			language2 = Language.JAPANESE;
			translate(translatedTextET, orihinakTextStr, language1, language2, v);
			break;
		}

	}
	public void translate(EditText translatedTextET, String orihinakTextStr,
			Language language1, Language language2, View v){

		String translatedText = null;
		try {
			translatedText = Translate.execute(orihinakTextStr, language1, language2);
			Log.i("LOG", "Jpanese -> English :" + translatedText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		translatedTextET.setText(translatedText);
		// ソフトキーボードを非表示にする
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
