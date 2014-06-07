package br.com.zynger.cardview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import br.com.zynger.cardview.CardFaceView.CardFlag;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		int flagCounter = 0;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			final CardView card = (CardView) rootView.findViewById(R.id.cardview);
			card.setCardNumber("5444 4444 4444 4444");
			card.setCardName("Arya Stark");
			card.setCardValidThru(8, 2015);
			card.setCardCvv(432);
			
			card.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!card.isShowingFront()) {
						CardFlag flag = null;
						if (flagCounter < CardFlag.values().length) {
							flag = CardFlag.values()[flagCounter];
						}
						card.setFlag(flag);
						Toast.makeText(v.getContext(), "Flag = " + flag,
								Toast.LENGTH_SHORT).show();
						flagCounter = (flagCounter + 1) % (CardFlag.values().length + 1);
					}
					card.toggleCardFace();
				}
			});

			return rootView;
		}
	}

}
