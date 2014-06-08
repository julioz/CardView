package br.com.zynger.cardview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import br.com.zynger.cardview.CardFaceView.CardFlag;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;

public class CardView extends RelativeLayout {

	private final static int ANIMATION_DURATION = 400;
	
	private boolean isShowingFront = true;
	private boolean isAnimating = false;
	
	private CardFrontFaceView mCardFrontView;
	private CardBackFaceView mCardBackView;

	private ObjectAnimator mFrontOutAnim;
	private ObjectAnimator mFrontInAnim;
	private ObjectAnimator mBackOutAnim;
	private ObjectAnimator mBackInAnim;

	public CardView(Context context) {
		this(context, null, 0);
	}
	
	public CardView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		mCardFrontView = new CardFrontFaceView(context);
		mCardBackView = new CardBackFaceView(context);
		
		mCardBackView.setVisibility(View.INVISIBLE);
		addView(mCardBackView);
		addView(mCardFrontView);
		
		initAnimations();
	}

	private void initAnimations() {
		mFrontOutAnim = ObjectAnimator.ofFloat(mCardFrontView, "rotationY", 0f, -90f);
		mFrontOutAnim.setDuration(ANIMATION_DURATION);
		mFrontOutAnim.setInterpolator(new AccelerateInterpolator());

		mFrontInAnim = ObjectAnimator.ofFloat(mCardFrontView, "rotationY", -90f, 0f);
		mFrontInAnim.setDuration(ANIMATION_DURATION);
		mFrontInAnim.setInterpolator(new DecelerateInterpolator());
		
		mBackOutAnim = ObjectAnimator.ofFloat(mCardBackView, "rotationY", 0f, 90f);
		mBackOutAnim.setDuration(ANIMATION_DURATION);
		mBackOutAnim.setInterpolator(new AccelerateInterpolator());
		
		mBackInAnim = ObjectAnimator.ofFloat(mCardBackView, "rotationY", 90f, 0f);
		mBackInAnim.setDuration(ANIMATION_DURATION);
		mBackInAnim.setInterpolator(new DecelerateInterpolator());
		
		mFrontOutAnim.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
				isAnimating = true;
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				mCardFrontView.setVisibility(View.INVISIBLE);
				mCardBackView.setVisibility(View.VISIBLE);
				mBackInAnim.start();
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
			}
		});
		
		mFrontInAnim.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				isShowingFront = true;
				isAnimating = false;
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
			}
		});
		
		mBackOutAnim.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
				isAnimating = true;
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				mCardFrontView.setVisibility(View.VISIBLE);
				mCardBackView.setVisibility(View.INVISIBLE);
				mFrontInAnim.start();
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
			}
		});
		
		mBackInAnim.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				isShowingFront = false;
				isAnimating = false;
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
			}
		});
	}
	
	public void setCardCvv(Integer cardCvv) {
		mCardBackView.setCardCvv(cardCvv);
	}
	
	public void setCardNumber(String cardNumber) {
		mCardFrontView.setCardNumber(cardNumber);
	}
	
	public void setCardName(String cardName) {
		mCardFrontView.setCardName(cardName);
	}
	
	public void setCardValidThru(int month, int year) {
		mCardFrontView.setCardValidThru(month, year);
	}
	
	public void setValidThruText(String valid, String thru) {
		mCardFrontView.setValidThruText(valid, thru);
	}
	
	public void setMonthYearText(String text) {
		mCardFrontView.setMonthYearText(text);
	}
	
	public void setInformationText(String text) {
		mCardBackView.setInformationText(text);
	}
	
	public void setFlag(CardFlag flag) {
		mCardFrontView.setFlag(flag);
		mCardBackView.setFlag(flag);
	}
	
	public boolean isShowingFront() {
		return isShowingFront;
	}
	
	public void toggleCardFace() {
		if (isAnimating) return;

		if (isShowingFront) {
			mFrontOutAnim.start();
		} else {
			mBackOutAnim.start();
		}
	}
}
