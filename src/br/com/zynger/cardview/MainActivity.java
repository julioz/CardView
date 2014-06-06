package br.com.zynger.cardview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	if (savedInstanceState == null) {
	    getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
	}
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

	public PlaceholderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.fragment_main, container, false);

	    RelativeLayout layout = (RelativeLayout) rootView.findViewById(R.id.relativelayout);

	    CardFaceView card = new CardFaceView(rootView.getContext());
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	    params.addRule(RelativeLayout.CENTER_IN_PARENT);
	    card.setLayoutParams(params);
	    
	    card.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            // TODO show other card face after animation
	            Toast.makeText(v.getContext(), "rotation", Toast.LENGTH_SHORT).show();
	        }
	    });
	    
	    layout.setBackgroundColor(Color.BLACK);
	    layout.addView(card);
	    return rootView;
	}
    }

}
