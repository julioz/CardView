package br.com.zynger.cardview;

import java.util.Locale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;

import com.nineoldandroids.animation.ValueAnimator;

public class CardFrontFaceView extends CardFaceView {
	
	private TypefaceUtil typefaces;
	
	private RectF mCardBaseChip;
	private RectF mCardOverChip;
	private RectF mFlagRect;

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

	private Paint mVisaPaint;
	private Paint mFlagVisaTopPaint;
	private Paint mFlagVisaBottomPaint;
	private Paint mVisaTextPaint;
	
	private Paint mMasterCardRedCirclePaint;
	private Paint mMasterCardYellowCirclePaint;
	private Paint mMasterCardTextPaint;
	
	private Paint mDiscoverFlagPaint;
	private Paint mDiscoverWhiteFlagPaint;
	private Paint mDiscoverOrangeGradientPaint;
	private Paint mDiscoverTextPaint;
	private float mDiscoverOrangeCircleRadius;
	private RectF mDiscoverLeftTextRect;
	private RectF mDiscoverRightTextRect;

	private Paint mAmexFlagPaint;
	private Paint mAmexBlueSquarePaint;
	private RectF mAmexBlueSquareRect;
	private Paint mAmexTextPaint;

	private RectF mFlagClipRect;

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
		
		mDiscoverFlagPaint = new Paint();
		mDiscoverFlagPaint.setColor(0xFFff6600);
		
		mDiscoverWhiteFlagPaint = new Paint();
		mDiscoverWhiteFlagPaint.setColor(0xFFFFFFFF);

		mDiscoverOrangeGradientPaint = new Paint();
	    mDiscoverOrangeGradientPaint.setDither(true);
	    
	    mDiscoverTextPaint = new Paint();
		mDiscoverTextPaint.setColor(Color.BLACK);
		mDiscoverTextPaint.setTextSize(8.5f * CARD_TEXT_SIZE_MULTIPLIER);
		mDiscoverTextPaint.setTypeface(typefaces.getLiberationSans());

		mAmexFlagPaint = new Paint();
		mAmexFlagPaint.setDither(true);
		mAmexFlagPaint.setStrokeWidth(1.5f);
		
		mAmexBlueSquarePaint = new Paint();
		mAmexBlueSquarePaint.setColor(0xFF267AC3);
		
		mAmexTextPaint = new Paint();
		mAmexTextPaint.setColor(Color.WHITE);
		mAmexTextPaint.setTextSize(3.3f * CARD_TEXT_SIZE_MULTIPLIER);
		
		mFlagRect = new RectF(CARD_WIDTH - (CARD_WIDTH * 0.25f),
				CARD_CONTENT_TOP * 1.5f, CARD_CONTENT_RIGHT, CARD_CONTENT_TOP
						+ CARD_HEIGHT * 0.25f);
		
		mDiscoverOrangeGradientPaint.setShader(new RadialGradient(mFlagRect.centerX(), mFlagRect.centerY(),
	            mFlagRect.height() / 2, Color.WHITE, 0xFFe57e31, TileMode.CLAMP));
	    mDiscoverOrangeCircleRadius = mFlagRect.height() / 6.5f;
		
	    mAmexFlagPaint.setShader(new RadialGradient(mFlagRect.centerX(), mFlagRect.centerY(),
	    		mFlagRect.height() / 0.7f, Color.WHITE, 0xFF9ca8b2, TileMode.REPEAT));

	    float amexSquareRadius = mFlagRect.width() / 5f;
	    mAmexBlueSquareRect = new RectF(mFlagRect.centerX()
	    		- amexSquareRadius, mFlagRect.centerY() - amexSquareRadius,
	    		mFlagRect.centerX() + amexSquareRadius, mFlagRect.centerY()
	    		+ amexSquareRadius);

	    mCardBaseChip = new RectF(CARD_CONTENT_LEFT, CARD_CONTENT_TOP * 2f,
				CARD_WIDTH * 0.2f, CARD_CONTENT_TOP + CARD_HEIGHT * 0.25f);
		mCardOverChip = new RectF(CARD_CONTENT_LEFT, CARD_CONTENT_TOP * 2.4f,
				CARD_WIDTH * 0.15f, CARD_CONTENT_TOP + CARD_HEIGHT * 0.22f);
		int padding = 4;
		mFlagClipRect = new RectF(mFlagRect.left + padding, mFlagRect.top
				+ padding, mFlagRect.right - padding, mFlagRect.bottom - padding);
		
		mDiscoverLeftTextRect = new RectF(mFlagClipRect.left, mFlagClipRect.top,
				mFlagRect.centerX() - mDiscoverOrangeCircleRadius,
				mFlagClipRect.bottom);
		mDiscoverRightTextRect = new RectF(mFlagRect.centerX()
				+ mDiscoverOrangeCircleRadius, mFlagClipRect.top,
				mFlagClipRect.right, mFlagClipRect.bottom);
		
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
		
		canvas.drawText(mValidText , mFlagRect.left - CARD_WIDTH * 0.075f, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.46f, mCardValidTitleTextPaint);
		
		canvas.drawText(mThruText , mFlagRect.left - CARD_WIDTH * 0.075f, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.50f, mCardValidTitleTextPaint);
		
		canvas.drawText(mCardValidThru, mFlagRect.left, mCardBaseChip.bottom
				+ CARD_HEIGHT * 0.50f, mCardValidTextPaint);
	}
	
	private void drawFlag(CardFlag flag, RectF flagRect, Canvas canvas) {
		if (flag == CardFlag.VISA) {
			drawVisaFlag(flag, flagRect, canvas);
		} else if (flag == CardFlag.MASTERCARD) {
			drawMasterCardFlag(flag, flagRect, canvas);
		} else if (flag == CardFlag.DISCOVER) {
			drawDiscoverFlag(flag, flagRect, canvas);
		} else if (flag == CardFlag.AMEX) {
			drawAmexFlag(flag, flagRect, canvas);
		}
	}
	
	private void drawAmexFlag(CardFlag flag, RectF flagRect, Canvas canvas) {
		canvas.drawColor(0xFF85BDB0);
		canvas.clipRect(mFlagClipRect);

		canvas.drawRect(flagRect, mAmexFlagPaint);

		canvas.drawRect(mAmexBlueSquareRect, mAmexBlueSquarePaint);

		canvas.drawText("AMERICAN", mAmexBlueSquareRect.left,
				mAmexBlueSquareRect.centerY()
						- (mAmexBlueSquareRect.height() / 8.5f), mAmexTextPaint);
		canvas.drawText("EXPRESS", mAmexBlueSquareRect.left
				+ (mAmexBlueSquareRect.width() / 4f),
				mAmexBlueSquareRect.centerY()
						+ (mAmexBlueSquareRect.height() / 8.5f), mAmexTextPaint);
	}

	private void drawDiscoverFlag(CardFlag flag, RectF flagRect, Canvas canvas) {
		canvas.drawColor(0xFFbfd9e5);
		canvas.clipRect(mFlagClipRect);

		canvas.drawRect(flagRect, mDiscoverFlagPaint);

		canvas.drawCircle(flagRect.left + (flagRect.width() / 8f), flagRect.top
				- (flagRect.height() / 4.5f), flagRect.width() * 0.88f,
				mDiscoverWhiteFlagPaint);

		canvas.drawCircle(flagRect.centerX(), flagRect.centerY(),
				mDiscoverOrangeCircleRadius, mDiscoverOrangeGradientPaint);

		float offsetY = mFlagClipRect.height() / 13f;
		drawTextInRectF("DISC", 0, offsetY, canvas, mDiscoverLeftTextRect,
				mDiscoverTextPaint);
		drawTextInRectF("VER", 0, offsetY, canvas, mDiscoverRightTextRect,
				mDiscoverTextPaint);
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
		canvas.clipRect(mFlagClipRect);
		canvas.drawRect(0, 0, getWidth(), flagRect.bottom / 2f, mFlagVisaTopPaint);
		RectF mVisaTextRect = new RectF(0, flagRect.bottom / 2f, getWidth(), flagRect.bottom / 1.18f);
		canvas.drawRect(mVisaTextRect, mVisaPaint);
		canvas.drawRect(0, flagRect.bottom / 1.18f, getWidth(), getHeight(), mFlagVisaBottomPaint);
		
		drawTextInRectF(flag.getText(), 0, Math.round(mFlagClipRect.height()/6f), canvas, mFlagClipRect, mVisaTextPaint);
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
		if (month < 1 || year < 0) {
			this.mCardValidThru = "";
		} else {
			this.mCardValidThru = (month < 10 ? "0" : "") + month + "/" + (year < 10 ? "0" : "") + year;
		}
		invalidate();
	}
	
	public void setValidThruText(String valid, String thru) {
		this.mValidText = valid.toLowerCase(Locale.getDefault());
		this.mThruText = thru.toLowerCase(Locale.getDefault());
		
		int maxLength = Math.max(mValidText.length(), mThruText.length());
		float size = 9.3f;
		if (maxLength > 5) {
			int difference = maxLength - 5;
			size = 9.3f - (difference * 1.3f);
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
		Paint[] discoverPaints = { mDiscoverFlagPaint, mDiscoverWhiteFlagPaint,
				mDiscoverOrangeGradientPaint, mDiscoverTextPaint };
		Paint[] amexPaints = { mAmexBlueSquarePaint, mAmexFlagPaint, mAmexTextPaint };

		if (oldFlag == CardFlag.VISA) {
			setPaintsAlpha(visaPaints, fadeOutAlpha);
		} else if (oldFlag == CardFlag.MASTERCARD) {
			setPaintsAlpha(masterCardPaints, fadeOutAlpha);
		} else if (oldFlag == CardFlag.DISCOVER) {
			setPaintsAlpha(discoverPaints, fadeOutAlpha);
		} else if (oldFlag == CardFlag.AMEX) {
			setPaintsAlpha(amexPaints, fadeOutAlpha);
		}

		if (newFlag == CardFlag.VISA) {
			setPaintsAlpha(visaPaints, fadeInAlpha);
		} else if (newFlag == CardFlag.MASTERCARD) {
			setPaintsAlpha(masterCardPaints, fadeInAlpha);
		} else if (newFlag == CardFlag.DISCOVER) {
			setPaintsAlpha(discoverPaints, fadeInAlpha);
		} else if (newFlag == CardFlag.AMEX) {
			setPaintsAlpha(amexPaints, fadeInAlpha);
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
