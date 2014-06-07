package br.com.zynger.cardview;

import java.util.Locale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public class CardFaceFrontView extends View {
	public enum CardFlag {
		VISA("VISA", 0xFF191278), MASTERCARD("MasterCard", 0xFF0061A8), DISCOVER(
				"Discover", 0xFF86B8CF), AMEX("American Express", 0xFF108168);
		
		private String mText;
		private int mColor;
		private CardFlag(String text, int color) {
			mText = text;
			mColor = color;
		}
		
		public int getColor() {
			return mColor;
		}

		public String getText() {
			return mText;
		}
	}

	private static final int CARD_BACKGROUND_COLOR_NOFLAG = 0xFFCCCCCC;

	private static final int CARD_WIDTH = Math.round(700 * 1.f);
	private static final int CARD_HEIGHT = Math.round(400 * 1.f);

	private static final int CARD_BORDER_RADIUS = 10;
	private static final int CHIP_BORDER_RADIUS = 5;

	private static final int CARD_CONTENT_LEFT = Math
			.round((float) CARD_WIDTH / 17.5f);
	private static final int CARD_CONTENT_RIGHT = CARD_WIDTH
			- Math.round((float) CARD_WIDTH / 17.5f);
	private static final int CARD_CONTENT_TOP = Math
			.round((float) CARD_HEIGHT / 15.0f);
	
	private static final float CARD_TEXT_SIZE_MULTIPLIER = CARD_WIDTH / 300.0f;

	private Paint mCardPaint;
	private RectF mCardRect;
	private Paint mChipBasePaint;
	private Paint mChipOverPaint;
	private RectF mCardBaseChip;
	private RectF mCardOverChip;
	private Paint mVisaPaint;
	private RectF mFlagRect;
	private Paint mFlagVisaTopPaint;
	private Paint mFlagVisaBottomPaint;
	private Paint mCardNumberTextPaint;
	private Paint mCardNameTextPaint;
	private Paint mCardValidTextPaint;
	private Paint mCardValidHeaderTextPaint;
	private Paint mCardValidTitleTextPaint;
	private Paint mCardBackgroundGradientPaint;
	private BitmapDrawable pattern;
	
	private CardFlag mFlag;
	private String mCardNumber = "";
	private String mCardName = "";
	private Integer mCardValidThruMonth;
	private Integer mCardValidThruYear;
	private String mCardValidThru = "";

	private Paint mMasterCardRedCirclePaint;

	private Paint mMasterCardYellowCirclePaint;

	private Paint mMasterCardTextPaint;

	private RectF mVisaClipRect;

	private Paint mVisaTextPaint;

	public CardFaceFrontView(Context context) {
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

		mVisaPaint = new Paint();
		mVisaPaint.setColor(Color.WHITE);
		
		mFlagVisaTopPaint = new Paint();
		mFlagVisaTopPaint.setColor(0xFF1A1876);
		
		mFlagVisaBottomPaint = new Paint();
		mFlagVisaBottomPaint.setColor(0xFFE79800);
		
		mMasterCardRedCirclePaint = new Paint();
		mMasterCardRedCirclePaint.setColor(0xFFFF0000);
		mMasterCardYellowCirclePaint = new Paint();
		mMasterCardYellowCirclePaint.setColor(0xFFFFAB00);

		mCardNumberTextPaint = new Paint();
		mCardNumberTextPaint.setTextSize(20 * CARD_TEXT_SIZE_MULTIPLIER);
		mCardNumberTextPaint.setAntiAlias(true);
		mCardNumberTextPaint.setColor(Color.WHITE);
		mCardNumberTextPaint.setAlpha(Math.round(0.75f * 255));
		mCardNumberTextPaint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "VeraMoBd.ttf"));
		
		mCardNameTextPaint = new Paint(mCardNumberTextPaint);
		mCardNameTextPaint.setTextSize(18 * CARD_TEXT_SIZE_MULTIPLIER);
		
		mCardValidTextPaint = new Paint(mCardNumberTextPaint);
		mCardValidTextPaint.setTextSize(13.5f * CARD_TEXT_SIZE_MULTIPLIER);

		mCardValidHeaderTextPaint = new Paint(mCardNumberTextPaint);
		mCardValidHeaderTextPaint.setTextSize(8.5f * CARD_TEXT_SIZE_MULTIPLIER);
		
		mCardValidTitleTextPaint = new Paint(mCardValidHeaderTextPaint);
		mCardValidTitleTextPaint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "liberation-sans.ttf"));
		mCardValidTitleTextPaint.setTextSize(9.5f * CARD_TEXT_SIZE_MULTIPLIER);
		
		mVisaTextPaint = new Paint();
		mVisaTextPaint.setColor(CardFlag.VISA.getColor());
		mVisaTextPaint.setAntiAlias(true);
		mVisaTextPaint.setDither(true);
		mVisaTextPaint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "VeraMoBd.ttf"));
		mVisaTextPaint.setTextSize(13f * CARD_TEXT_SIZE_MULTIPLIER);
		
		mMasterCardTextPaint = new Paint();
		mMasterCardTextPaint.setColor(Color.WHITE);
		mMasterCardTextPaint.setAntiAlias(true);
		mMasterCardTextPaint.setDither(true);
		mMasterCardTextPaint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "VeraMoBI.ttf"));
		mMasterCardTextPaint.setTextSize(8f * CARD_TEXT_SIZE_MULTIPLIER);
		
		mCardRect = new RectF(0, 0, CARD_WIDTH, CARD_HEIGHT);
		mCardBaseChip = new RectF(CARD_CONTENT_LEFT, CARD_CONTENT_TOP * 2f,
				CARD_WIDTH * 0.2f, CARD_CONTENT_TOP + CARD_HEIGHT * 0.25f);
		mCardOverChip = new RectF(CARD_CONTENT_LEFT, CARD_CONTENT_TOP * 2.4f,
				CARD_WIDTH * 0.15f, CARD_CONTENT_TOP + CARD_HEIGHT * 0.22f);

		mFlagRect = new RectF(CARD_WIDTH - (CARD_WIDTH * 0.25f),
				CARD_CONTENT_TOP * 1.5f, CARD_CONTENT_RIGHT, CARD_CONTENT_TOP
						+ CARD_HEIGHT * 0.25f);
		
		int padding = 4;
		mVisaClipRect = new RectF(mFlagRect.left + padding, mFlagRect.top
				+ padding, mFlagRect.right - padding, mFlagRect.bottom - padding);
		
//		pattern = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.card_pattern));
//		pattern.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//		pattern.setBounds(Math.round(mCardRect.left), Math.round(mCardRect.top), Math.round(mCardRect.right), Math.round(mCardRect.bottom));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawRoundRect(mCardRect, CARD_BORDER_RADIUS, CARD_BORDER_RADIUS,
				mCardPaint);
//		pattern.draw(canvas);
	    canvas.drawRoundRect(mCardRect, CARD_BORDER_RADIUS, CARD_BORDER_RADIUS, mCardBackgroundGradientPaint);
		
		canvas.drawRoundRect(mCardBaseChip, CHIP_BORDER_RADIUS,
				CHIP_BORDER_RADIUS, mChipBasePaint);
		canvas.drawRoundRect(mCardOverChip, CHIP_BORDER_RADIUS,
				CHIP_BORDER_RADIUS, mChipOverPaint);
		
		if (mFlag != null) {
			canvas.save();
			canvas.clipRect(mFlagRect);
			drawFlag(mFlag, mFlagRect, canvas);
			canvas.restore();
		}
		
		canvas.drawText(mCardNumber, CARD_CONTENT_LEFT, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.25f, mCardNumberTextPaint);
		
		canvas.drawText(mCardName, CARD_CONTENT_LEFT, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.50f, mCardNameTextPaint);
		
		// TODO move to strings.xml
		canvas.drawText("MONTH/YEAR", mFlagRect.left, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.41f, mCardValidHeaderTextPaint);
		
		// TODO move to strings.xml
		canvas.drawText("valid", mFlagRect.left - CARD_WIDTH * 0.07f, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.46f, mCardValidTitleTextPaint);
		
		// TODO move to strings.xml
		canvas.drawText("thru", mFlagRect.left - CARD_WIDTH * 0.07f, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.50f, mCardValidTitleTextPaint);
		
		canvas.drawText(mCardValidThru, mFlagRect.left, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.50f, mCardValidTextPaint);
	}
	
	private void drawFlag(CardFlag flag, RectF flagRect, Canvas canvas) {
		if (flag == CardFlag.VISA) {
			drawVisaFlag(flag, flagRect, canvas);
		} else if (flag == CardFlag.MASTERCARD) {
			drawMasterCardFlag(flag, flagRect, canvas);
		}
	}

	private void drawMasterCardFlag(CardFlag flag, RectF flagRect, Canvas canvas) {
		float radius = flagRect.height() / 2.2f;
		float distanceCenters = flagRect.width() / 5;
		canvas.drawCircle(flagRect.centerX() + distanceCenters,
				flagRect.centerY(), radius, mMasterCardYellowCirclePaint);
		canvas.drawCircle(flagRect.centerX() - distanceCenters,
				flagRect.centerY(), radius, mMasterCardRedCirclePaint);

		drawTextInRectF(flag.getText(), 0, Math.round(flagRect.height() / 13f),
				canvas, flagRect, mMasterCardTextPaint);
	}

	private void drawVisaFlag(CardFlag flag, RectF flagRect, Canvas canvas) {
		canvas.drawColor(0xFF8d88bc);
		canvas.clipRect(mVisaClipRect);
		canvas.drawRect(0, 0, getWidth(), flagRect.bottom / 2f, mFlagVisaTopPaint);
		RectF mVisaTextRect = new RectF(0, flagRect.bottom / 2f, getWidth(), flagRect.bottom / 1.18f);
		canvas.drawRect(mVisaTextRect, mVisaPaint);
		canvas.drawRect(0, flagRect.bottom / 1.18f, getWidth(), getHeight(), mFlagVisaBottomPaint);
		
		drawTextInRectF(flag.getText(), 0, Math.round(mVisaClipRect.height()/6f), canvas, mVisaClipRect, mVisaTextPaint);
	}
	
	private void drawTextInRectF(String text, int offsetX, int offsetY, Canvas canvas, RectF r, Paint textPaint) {
		textPaint.setTextAlign(Align.CENTER);
		float width = r.width();

		int numOfChars = textPaint.breakText(text, true, width, null);
		int start = (text.length() - numOfChars) / 2;
		canvas.drawText(text, start, start + numOfChars, r.centerX() + offsetX,
				r.centerY() + offsetY, textPaint);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(
				MeasureSpec.makeMeasureSpec(CARD_WIDTH, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(CARD_HEIGHT, MeasureSpec.EXACTLY));
	}

	public void setCardNumber(String cardNumber) {
		this.mCardNumber = cardNumber;
		invalidate();
	}
	
	public void setCardName(String cardName) {
		this.mCardName = cardName.toUpperCase(Locale.getDefault());
		invalidate();
	}
	
	public void setCardValidThru(int month, int year) {
		this.mCardValidThruMonth = month;
		this.mCardValidThruYear = year;
		this.mCardValidThru = (month < 10 ? "0" : "") + month + "/" + (year < 10 ? "0" : "") + year;
		invalidate();
	}
	
	public void setFlag(CardFlag mFlag) {
		CardFlag oldFlag = this.mFlag;
		this.mFlag = mFlag;
		
		fadeForFlag(oldFlag, mFlag);
	}
	
	private void fadeForFlag(final CardFlag oldFlag, final CardFlag newFlag) {
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
				int fadeInAlpha = Math.round(value.getAnimatedFraction() * 255f);
				int fadeOutAlpha = Math.round((1 - value.getAnimatedFraction()) * 255f);
				Paint[] visaPaints = { mVisaPaint, mVisaTextPaint };
				Paint[] masterCardPaints = { mMasterCardRedCirclePaint, mMasterCardYellowCirclePaint, mMasterCardTextPaint };

				if (oldFlag == CardFlag.VISA) {
					setPaintsAlpha(visaPaints, fadeOutAlpha);
				} else if (oldFlag == CardFlag.MASTERCARD) {
					setPaintsAlpha(masterCardPaints, fadeOutAlpha);
				}
				
				if (newFlag == CardFlag.VISA) {
					setPaintsAlpha(visaPaints, fadeInAlpha);
				} else if (newFlag == CardFlag.MASTERCARD) {
					setPaintsAlpha(masterCardPaints, fadeInAlpha);
				}
				invalidate();
			}
		});
		
		fade.start();
	}
	
	private void setPaintsAlpha(Paint[] paints, int alpha) {
		for (Paint paint : paints) {
			paint.setAlpha(alpha);
		}
	}
	
	public Integer getCardValidThruMonth() {
		return mCardValidThruMonth;
	}
	
	public Integer getCardValidThruYear() {
		return mCardValidThruYear;
	}
	
	public String getCardName() {
		return mCardName;
	}
	
	public String getCardNumber() {
		return mCardNumber;
	}
}
