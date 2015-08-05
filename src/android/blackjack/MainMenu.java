package android.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends ActionBarActivity implements OnClickListener{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		Button newGame, rules, exit;
		newGame = (Button) findViewById(R.id.new_game);
		newGame.setOnClickListener(this);
		rules = (Button) findViewById(R.id.rules);
		rules.setOnClickListener(this);
		exit = (Button) findViewById(R.id.exit);
		exit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()){
		
		case R.id.new_game:
			Intent startGame = new Intent(MainMenu.this, GUI.class);
			startActivity(startGame);
			break;
			
		case R.id.rules:
			Intent toRules = new Intent(MainMenu.this, Rules.class);
			startActivity(toRules);
			break;
		
		case R.id.exit:
			finish();
			break;
		}
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
