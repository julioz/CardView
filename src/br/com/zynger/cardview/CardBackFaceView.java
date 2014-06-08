package br.com.zynger.cardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.nineoldandroids.animation.ValueAnimator;

public class CardBackFaceView extends CardFaceView {

	private TypefaceUtil typefaces;
	
	private String mCardCvv = "•••";
	private String mInformationText = "This card has been issued by Julio Zynger and is licensed\nfor anyone to use anywhere for free. It comes with\nno warranty. For support issues,\nplease visit: github.com/julioz/cardview";
	
	private RectF mMagnecticBarRect;
	private Paint mMagnecticBarPaint;
	private RectF mCardBaseChip;
	private RectF mCardOverChip;
	private RectF mSignatureRect;
	private Paint mSignaturePaint;
	private Paint mInformationPaint;

	private Paint mCvvPaint;

	public CardBackFaceView(Context context) {
		super(context);
		
		typefaces = TypefaceUtil.getInstance(getContext());
		
		mMagnecticBarPaint = new Paint();
		int[] colors = {0xFF444444, 0xFF333333};
		mMagnecticBarPaint.setShader(new LinearGradient(0, 0, 0, CARD_HEIGHT, colors, null, Shader.TileMode.REPEAT));
		
		mSignaturePaint = new Paint();
		mSignaturePaint.setColor(Color.WHITE);
		
		mCvvPaint = new Paint();
		mCvvPaint.setColor(Color.WHITE);
		mCvvPaint.setAntiAlias(true);
		mCvvPaint.setDither(true);
		mCvvPaint.setTypeface(typefaces.getVeraMonoBold());
		mCvvPaint.setTextSize(12.5f * CARD_TEXT_SIZE_MULTIPLIER);
		
		mInformationPaint = new Paint(mCvvPaint);
		mInformationPaint.setTextSize(6.5f * CARD_TEXT_SIZE_MULTIPLIER);
		mInformationPaint.setAlpha(Math.round(0.5f * 255));
		
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
		
		String[] splitText = mInformationText.split("\n");
		float y = mCardBaseChip.top + (CARD_HEIGHT / 35f);
		for (String line : splitText) {
			canvas.drawText(line, mCardBaseChip.right + (CARD_WIDTH / 35f), y, mInformationPaint);
			y += Math.round(mCardBaseChip.height() / splitText.length);
		}
	}

	protected void setCardCvv(Integer cardCvv) {
		this.mCardCvv = String.valueOf(cardCvv);
		invalidate();
	}
	
	@Override
	protected void onFlagChangedUpdate(CardFlag oldFlag, CardFlag newFlag,
			ValueAnimator value) {
	}

	public void setInformationText(String text) {
		this.mInformationText = text;
		invalidate();
	}

}
