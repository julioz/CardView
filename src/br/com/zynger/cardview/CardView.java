package br.com.zynger.cardview;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import br.com.zynger.cardview.CardFaceView.CardFlag;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;

public class CardView extends RelativeLayout {

	private final static int ANIMATION_DURATION = 400;
	private static final int MAX_CARD_NUMBER_LENGTH = 16;
	private static final int MAX_CARD_DATE_LENGTH = 6;
	private static final int MAX_CARD_CVV_LENGTH = 4;
	
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
		
		if (cardNumber.matches(CardFlag.VISA.getRegex())) {
			setFlag(CardFlag.VISA);
		} else if (cardNumber.matches(CardFlag.AMEX.getRegex())) {
			setFlag(CardFlag.AMEX);
		} else if (cardNumber.matches(CardFlag.DISCOVER.getRegex())) {
			setFlag(CardFlag.DISCOVER);
		} else if (cardNumber.matches(CardFlag.MASTERCARD.getRegex())) {
			setFlag(CardFlag.MASTERCARD);
		} else {
			setFlag(null);
		}
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
		if (mCardFrontView.getFlag() != flag) {
			mCardFrontView.setFlag(flag);
			mCardBackView.setFlag(flag);
		}
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
	
	public void setNameTextInput(EditText etName) {
		etName.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				setCardName(s.toString());
			}
		});
	}
	
	public void setNumberTextInput(EditText etNumber) {
		etNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
		InputFilter[] filterArray = new InputFilter[1];
		filterArray[0] = new InputFilter.LengthFilter(MAX_CARD_NUMBER_LENGTH);
		etNumber.setFilters(filterArray);
		etNumber.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String input = s.toString();
				String cardNumber = getMaskedNumber(input);
				setCardNumber(cardNumber);
			}
		});
	}
	
	public void setExpirationTextInput(EditText etDate) {
		etDate.setInputType(InputType.TYPE_CLASS_NUMBER);
		InputFilter[] filterArray = new InputFilter[1];
		filterArray[0] = new InputFilter.LengthFilter(MAX_CARD_DATE_LENGTH);
		etDate.setFilters(filterArray);
		etDate.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String input = s.toString();
				int[] date = getMaskedDate(input);
				setCardValidThru(date[0], date[1]);
			}
		});
	}
	
	public void setCvvTextInput(EditText etCvv) {
		etCvv.setInputType(InputType.TYPE_CLASS_NUMBER);
		InputFilter[] filterArray = new InputFilter[1];
		filterArray[0] = new InputFilter.LengthFilter(MAX_CARD_CVV_LENGTH);
		etCvv.setFilters(filterArray);
		
		etCvv.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				toggleCardFace();
			}
		});
		
		etCvv.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String input = s.toString();
				Integer cardCvv = null;
				if (input.length() > 0) {
					cardCvv = Integer.valueOf(input);
				}
				setCardCvv(cardCvv);
			}
		});
	}
	
	private int[] getMaskedDate(String input) {
		int[] date = new int[2];
		date[0] = -1;
		date[1] = -1;
		
		if (input.length() > 1) {
			date[0] = Integer.valueOf(input.substring(0, 2));
			if (date[0] < 1 || date[0] > 12) {
				date[0] = -1;
			}
		}
		
		if (input.length() > 2) {
			date[1] = Integer.valueOf(input.substring(2, input.length()));
		}

		return date;
	}
	
	private String getMaskedNumber(String input) {
		StringBuilder out = new StringBuilder();
		
		for (int index = 0; index < input.length(); index++) {
			out.append(input.charAt(index));
			if ((index % 4) == 3) {
				out.append(' ');
			}
		}
		return out.toString();
	}
}
