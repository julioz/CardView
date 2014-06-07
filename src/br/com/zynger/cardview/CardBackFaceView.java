package br.com.zynger.cardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;

import com.nineoldandroids.animation.ValueAnimator;

public class CardBackFaceView extends CardFaceView {

	private String mCardCvv = "•••";
	
	private RectF mMagnecticBarRect;
	private Paint mMagnecticBarPaint;
	private RectF mCardBaseChip;
	private RectF mCardOverChip;
	private RectF mSignatureRect;
	private Paint mSignaturePaint;

	private Paint mCvvPaint;

	public CardBackFaceView(Context context) {
		super(context);
		
		mMagnecticBarPaint = new Paint();
		int[] colors = {0xFF444444, 0xFF333333};
		mMagnecticBarPaint.setShader(new LinearGradient(0, 0, 0, CARD_HEIGHT, colors, null, Shader.TileMode.REPEAT));
		
		mSignaturePaint = new Paint();
		mSignaturePaint.setColor(Color.WHITE);
		
		mCvvPaint = new Paint();
		mCvvPaint.setColor(Color.WHITE);
		mCvvPaint.setAntiAlias(true);
		mCvvPaint.setDither(true);
		mCvvPaint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "VeraMoBd.ttf"));
		mCvvPaint.setTextSize(12.5f * CARD_TEXT_SIZE_MULTIPLIER);
		
		mMagnecticBarRect = new RectF(0, CARD_HEIGHT * 0.1f, CARD_WIDTH, CARD_HEIGHT * 0.3f);
		mCardBaseChip = new RectF(CARD_CONTENT_LEFT, CARD_HEIGHT * 0.7f,
				CARD_WIDTH * 0.2f, CARD_HEIGHT * 0.9f);
		mCardOverChip = new RectF(CARD_CONTENT_LEFT, CARD_HEIGHT * 0.73f,
				CARD_WIDTH * 0.15f, CARD_HEIGHT * 0.87f);
		
		mSignatureRect = new RectF(CARD_CONTENT_LEFT, CARD_HEIGHT * 0.41f, CARD_WIDTH * 0.85f, CARD_HEIGHT * 0.56f);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawRect(mMagnecticBarRect, mMagnecticBarPaint);
		
		drawChip(canvas, mCardBaseChip, mCardOverChip);
		
		canvas.drawRect(mSignatureRect, mSignaturePaint);
		
		canvas.drawText(String.valueOf(mCardCvv), mSignatureRect.right + (CARD_WIDTH / 35f), mSignatureRect.centerY(), mCvvPaint);
	}

	protected void setCardCvv(Integer cardCvv) {
		this.mCardCvv = String.valueOf(cardCvv);
		invalidate();
	}
	
	@Override
	protected void onFlagChangedUpdate(CardFlag oldFlag, CardFlag newFlag,
			ValueAnimator value) {
	}

}
