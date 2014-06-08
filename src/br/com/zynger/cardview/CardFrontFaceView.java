package br.com.zynger.cardview;

import java.util.Locale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.nineoldandroids.animation.ValueAnimator;

public class CardFrontFaceView extends CardFaceView {
	
	private TypefaceUtil typefaces;
	
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
//	private BitmapDrawable pattern;
	
	private String mCardNumber = "•••• •••• •••• ••••";
	private String mCardName = "CARDHOLDER NAME";
	private Integer mCardValidThruMonth;
	private Integer mCardValidThruYear;
	private String mCardValidThru = "••/••";

	private Paint mMasterCardRedCirclePaint;
	private Paint mMasterCardYellowCirclePaint;
	private Paint mMasterCardTextPaint;

	private RectF mVisaClipRect;
	private Paint mVisaTextPaint;

	private String mValidText = "valid";
	private String mThruText = "thru";

	private String mMonthYearText = "MONTH/YEAR";

	public CardFrontFaceView(Context context) {
		super(context);
		
		typefaces = TypefaceUtil.getInstance(getContext());
		
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
		mCardNumberTextPaint.setTypeface(typefaces.getVeraMonoBold());
		
		mCardNameTextPaint = new Paint(mCardNumberTextPaint);
		mCardNameTextPaint.setTextSize(18 * CARD_TEXT_SIZE_MULTIPLIER);
		
		mCardValidTextPaint = new Paint(mCardNumberTextPaint);
		mCardValidTextPaint.setTextSize(13.5f * CARD_TEXT_SIZE_MULTIPLIER);

		mCardValidHeaderTextPaint = new Paint(mCardNumberTextPaint);
		mCardValidHeaderTextPaint.setTextSize(8.5f * CARD_TEXT_SIZE_MULTIPLIER);
		
		mCardValidTitleTextPaint = new Paint(mCardValidHeaderTextPaint);
		mCardValidTitleTextPaint.setTypeface(typefaces.getLiberationSans());
		mCardValidTitleTextPaint.setTextSize(9.5f * CARD_TEXT_SIZE_MULTIPLIER);
		
		mVisaTextPaint = new Paint();
		mVisaTextPaint.setColor(CardFlag.VISA.getColor());
		mVisaTextPaint.setAntiAlias(true);
		mVisaTextPaint.setDither(true);
		mVisaTextPaint.setTypeface(typefaces.getVeraMonoBold());
		mVisaTextPaint.setTextSize(13f * CARD_TEXT_SIZE_MULTIPLIER);
		
		mMasterCardTextPaint = new Paint();
		mMasterCardTextPaint.setColor(Color.WHITE);
		mMasterCardTextPaint.setAntiAlias(true);
		mMasterCardTextPaint.setDither(true);
		mMasterCardTextPaint.setTypeface(typefaces.getVeraMonoBoldItalic());
		mMasterCardTextPaint.setTextSize(8f * CARD_TEXT_SIZE_MULTIPLIER);
		
		mFlagRect = new RectF(CARD_WIDTH - (CARD_WIDTH * 0.25f),
				CARD_CONTENT_TOP * 1.5f, CARD_CONTENT_RIGHT, CARD_CONTENT_TOP
						+ CARD_HEIGHT * 0.25f);
		
		mCardBaseChip = new RectF(CARD_CONTENT_LEFT, CARD_CONTENT_TOP * 2f,
				CARD_WIDTH * 0.2f, CARD_CONTENT_TOP + CARD_HEIGHT * 0.25f);
		mCardOverChip = new RectF(CARD_CONTENT_LEFT, CARD_CONTENT_TOP * 2.4f,
				CARD_WIDTH * 0.15f, CARD_CONTENT_TOP + CARD_HEIGHT * 0.22f);
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

		drawChip(canvas, mCardBaseChip, mCardOverChip);
		
		if (getFlag() != null) {
			canvas.save();
			canvas.clipRect(mFlagRect);
			drawFlag(getFlag(), mFlagRect, canvas);
			canvas.restore();
		}
		
		canvas.drawText(mCardNumber, CARD_CONTENT_LEFT, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.25f, mCardNumberTextPaint);
		
		canvas.drawText(mCardName, CARD_CONTENT_LEFT, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.50f, mCardNameTextPaint);
		
		canvas.drawText(mMonthYearText , mFlagRect.left, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.41f, mCardValidHeaderTextPaint);
		
		canvas.drawText(mValidText , mFlagRect.left - CARD_WIDTH * 0.07f, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.46f, mCardValidTitleTextPaint);
		
		canvas.drawText(mThruText , mFlagRect.left - CARD_WIDTH * 0.07f, mCardBaseChip.bottom
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
	
	protected void setCardNumber(String cardNumber) {
		this.mCardNumber = cardNumber;
		invalidate();
	}
	
	protected void setCardName(String cardName) {
		this.mCardName = cardName.toUpperCase(Locale.getDefault());
		invalidate();
	}
	
	protected void setCardValidThru(int month, int year) {
		this.mCardValidThruMonth = month;
		this.mCardValidThruYear = year;
		this.mCardValidThru = (month < 10 ? "0" : "") + month + "/" + (year < 10 ? "0" : "") + year;
		invalidate();
	}
	
	public void setValidThruText(String valid, String thru) {
		this.mValidText = valid;
		this.mThruText = thru;
		
		int maxLength = Math.max(mValidText.length(), mThruText.length());
		float size = 9.5f;
		if (maxLength > 5) {
			int difference = maxLength - 5;
			size = 9.5f - (difference * 1.3f);
		}
		size = Math.max(size, 5.0f);
		mCardValidTitleTextPaint.setTextSize(size * CARD_TEXT_SIZE_MULTIPLIER);
		
		invalidate();
	}
	
	public void setMonthYearText(String text) {
		this.mMonthYearText = text;
		invalidate();
	}
	
	@Override
	protected void onFlagChangedUpdate(CardFlag oldFlag, CardFlag newFlag, ValueAnimator value) {
		int fadeInAlpha = Math.round(value.getAnimatedFraction() * 255f);
		int fadeOutAlpha = Math.round((1 - value.getAnimatedFraction()) * 255f);

		Paint[] visaPaints = { mVisaPaint, mVisaTextPaint };
		Paint[] masterCardPaints = { mMasterCardRedCirclePaint,
				mMasterCardYellowCirclePaint, mMasterCardTextPaint };

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
