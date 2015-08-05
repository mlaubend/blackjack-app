package android.blackjack;

import blackjack.Game;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class KeypadDialog extends Dialog{
	
	GUI view;
	Game game;
//	StringBuffer ante;
	TextView anteAmount;

	public KeypadDialog(Context context, GUI view, Game game) {
		super(context);
		this.view = view;
		this.game = game;
		
		setContentView(R.layout.keypad);
		View keypad = findViewById(R.id.keypad);
		
		View[] keyViews = new View[]{
					findViewById(R.id.keypad_0),
					findViewById(R.id.keypad_1),
		            findViewById(R.id.keypad_2),
		            findViewById(R.id.keypad_3),
		            findViewById(R.id.keypad_4),
		            findViewById(R.id.keypad_5),
		            findViewById(R.id.keypad_6),
		            findViewById(R.id.keypad_7),
		            findViewById(R.id.keypad_8),
		            findViewById(R.id.keypad_9),
		            findViewById(R.id.back),
		            findViewById(R.id.ok),
		        };
		anteAmount = (TextView)findViewById(R.id.anteamount);
		
		for (int i = 0; i < keyViews.length; i++){
        	final int j = i;
			View.OnClickListener listener = new View.OnClickListener(){
                public void onClick(View view) {
                    keyListener(j);
                }
            };
            keyViews[i].setOnClickListener(listener);
		}
	}
	
	public void keyListener(int i){
		if (i < 10){
			anteAmount.append(Integer.toString(i));
		}
		else if (i == 10){
			if (anteAmount.getText().length() > 0){
				String text = anteAmount.getText().toString();
				anteAmount.setText(text.substring(0, text.length()-1));
			}	
		}
		else{
			game.anteUp(Integer.parseInt(anteAmount.getText().toString()));	//TODO might want to make another method in commands for this
			this.dismiss();
		}
	}

}
