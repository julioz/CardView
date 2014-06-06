package br.com.zynger.cardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

public class CardFaceView extends View {

    private static final int CARD_WIDTH = Math.round(350 * 1.5f);
    private static final int CARD_HEIGHT = Math.round(200 * 1.5f);

    private static final int CARD_BORDER_RADIUS = 10;
    private static final int CHIP_BORDER_RADIUS = 5;
    
    private static final int CARD_CONTENT_LEFT = Math.round((float)CARD_WIDTH / 17.5f);
    private static final int CARD_CONTENT_TOP = Math.round((float)CARD_HEIGHT / 15.0f);

    private Paint mCardPaint;
    private RectF mCardRect;
    private Paint mChipBasePaint;
    private Paint mChipOverPaint;
    private RectF mCardBaseChip;
    private RectF mCardOverChip;

    public CardFaceView(Context context) {
	super(context);

	mCardPaint = new Paint();
	mCardPaint.setColor(0xFFDDDDDD);
	
	mChipBasePaint = new Paint();
	mChipBasePaint.setColor(0xFFCCCCCC);

	mChipOverPaint = new Paint();
	mChipOverPaint.setColor(0xFFD9D9D9);
	
	Log.e("CARD", "W =" + CARD_WIDTH + ", H=" + CARD_HEIGHT);
	Log.e("CARD", "L =" + CARD_CONTENT_LEFT + ", T=" + CARD_CONTENT_TOP);
	
	mCardRect = new RectF(0, 0, CARD_WIDTH, CARD_HEIGHT);
	mCardBaseChip = new RectF(CARD_CONTENT_LEFT, CARD_CONTENT_TOP * 2f, CARD_WIDTH * 0.2f, CARD_CONTENT_TOP + CARD_HEIGHT * 0.25f);
	mCardOverChip = new RectF(CARD_CONTENT_LEFT, CARD_CONTENT_TOP * 2.4f, CARD_WIDTH * 0.15f, CARD_CONTENT_TOP + CARD_HEIGHT * 0.22f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
	super.onDraw(canvas);

	canvas.drawRoundRect(mCardRect, CARD_BORDER_RADIUS, CARD_BORDER_RADIUS, mCardPaint);
	canvas.drawRoundRect(mCardBaseChip, CHIP_BORDER_RADIUS, CHIP_BORDER_RADIUS, mChipBasePaint);
	canvas.drawRoundRect(mCardOverChip, CHIP_BORDER_RADIUS, CHIP_BORDER_RADIUS, mChipOverPaint);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	super.onMeasure(MeasureSpec.makeMeasureSpec(CARD_WIDTH, MeasureSpec.EXACTLY),
		MeasureSpec.makeMeasureSpec(CARD_HEIGHT, MeasureSpec.EXACTLY));
    }

}
