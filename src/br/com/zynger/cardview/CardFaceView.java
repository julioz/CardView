package br.com.zynger.cardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public abstract class CardFaceView extends View {
	public enum CardFlag {
		VISA("VISA", 0xFF191278, "4.*"), MASTERCARD("MasterCard", 0xFF0061A8, "5[1-5].*"), DISCOVER(
				"Discover", 0xFF86B8CF, "(6011|644|65).*"), AMEX("American Express", 0xFF108168, "(34|37).*");
		
		private String mText;
		private String mRegex;
		private int mColor;
		private CardFlag(String text, int color, String regex) {
			mText = text;
			mColor = color;
			mRegex = regex;
		}
		
		public int getColor() {
			return mColor;
		}

		public String getText() {
			return mText;
		}

		public String getRegex() {
			return mRegex;
		}
	}
	
	protected static final int CARD_BACKGROUND_COLOR_NOFLAG = 0xFFCCCCCC;

	protected static final int CARD_WIDTH = Math.round(700 * 1f); //TODO extract to variables
	protected static final int CARD_HEIGHT = Math.round(400 * 1f);

	protected static final int CARD_BORDER_RADIUS = 10;
	protected static final int CHIP_BORDER_RADIUS = 5;

	protected static final int CARD_CONTENT_LEFT = Math
			.round((float) CARD_WIDTH / 17.5f);
	protected static final int CARD_CONTENT_RIGHT = CARD_WIDTH
			- Math.round((float) CARD_WIDTH / 17.5f);
	protected static final int CARD_CONTENT_TOP = Math
			.round((float) CARD_HEIGHT / 15.0f);
	
	protected static final float CARD_TEXT_SIZE_MULTIPLIER = CARD_WIDTH / 300.0f;
	
	private CardFlag mFlag;
	
	private Paint mCardPaint;
	private RectF mCardRect;
	private Paint mChipBasePaint;
	private Paint mChipOverPaint;
	private Paint mCardBackgroundGradientPaint;
	
	public CardFaceView(Context context) {
		super(context);
		
		mCardPaint = new Paint();
		mCardPaint.setColor(0xFFDDDDDD);
		
		mCardBackgroundGradientPaint = new Paint();
		int[] colors = {Color.TRANSPARENT, Color.WHITE, Color.TRANSPARENT, Color.WHITE, Color.TRANSPARENT};
		float[] positions = {0, 0.3f, 0.5f, 0.7f, 1.0f};
		mCardBackgroundGradientPaint.setAlpha(Math.round(0.35f * 255));
	    mCardBackgroundGradientPaint.setShader(new LinearGradient(0, 0, CARD_WIDTH, CARD_HEIGHT, colors, positions, Shader.TileMode.REPEAT));

		mChipBasePaint = new Paint();
		mChipBasePaint.setColor(CARD_BACKGROUND_COLOR_NOFLAG);

		mChipOverPaint = new Paint();
		mChipOverPaint.setColor(0xFFD9D9D9);
		
		mCardRect = new RectF(0, 0, CARD_WIDTH, CARD_HEIGHT);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawRoundRect(mCardRect, CARD_BORDER_RADIUS, CARD_BORDER_RADIUS,
				mCardPaint);
	    canvas.drawRoundRect(mCardRect, CARD_BORDER_RADIUS, CARD_BORDER_RADIUS, mCardBackgroundGradientPaint);
	}
	
	protected void drawChip(Canvas canvas, RectF baseChipRect, RectF overChipRect) {
		canvas.drawRoundRect(baseChipRect, CHIP_BORDER_RADIUS,
				CHIP_BORDER_RADIUS, mChipBasePaint);
		canvas.drawRoundRect(overChipRect, CHIP_BORDER_RADIUS,
				CHIP_BORDER_RADIUS, mChipOverPaint);
	}
	
	protected void drawTextInRectF(String text, int offsetX, int offsetY, Canvas canvas, RectF r, Paint textPaint) {
		textPaint.setTextAlign(Align.CENTER);
		float width = r.width();

		int numOfChars = textPaint.breakText(text, true, width, null);
		int start = (text.length() - numOfChars) / 2;
		canvas.drawText(text, start, start + numOfChars, r.centerX() + offsetX,
				r.centerY() + offsetY, textPaint);
	}

	private void onFlagChanged(final CardFlag oldFlag, final CardFlag newFlag) {
		int flagColor = CARD_BACKGROUND_COLOR_NOFLAG;
		if (newFlag != null) {
			flagColor = newFlag.getColor();
		}
		
		ValueAnimator fade = ObjectAnimator.ofInt(mCardPaint,
				"color", mCardPaint.getColor(), flagColor);
		fade.setDuration(500);
		fade.setEvaluator(new ArgbEvaluator());
		fade.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator value) {
				onFlagChangedUpdate(oldFlag, newFlag, value);
				invalidate();
			}
		});
		
		fade.start();
	}
	
	protected abstract void onFlagChangedUpdate(CardFlag oldFlag, CardFlag newFlag, ValueAnimator value);

	protected void setPaintsAlpha(Paint[] paints, int alpha) {
		for (Paint paint : paints) {
			paint.setAlpha(alpha);
		}
	}
	
	public void setFlag(CardFlag mFlag) {
		CardFlag oldFlag = this.mFlag;
		this.mFlag = mFlag;
		
		onFlagChanged(oldFlag, mFlag);
	}
	
	public CardFlag getFlag() {
		return mFlag;
	}
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(
				MeasureSpec.makeMeasureSpec(CARD_WIDTH, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(CARD_HEIGHT, MeasureSpec.EXACTLY));
	}
}
