package br.com.zynger.cardview;

import android.content.Context;
import android.graphics.Typeface;

public class TypefaceUtil {
	private static TypefaceUtil instance;
	private Typeface mVeraMonoBold;
	private Typeface mVeraMonoBoldItalic;
	private Typeface mLiberationSans;
	
	public TypefaceUtil(Context context) {
		mVeraMonoBold = loadTypeface(context, "VeraMoBd.ttf");
		mVeraMonoBoldItalic = loadTypeface(context, "VeraMoBI.ttf");
		mLiberationSans = loadTypeface(context, "liberation-sans.ttf");
	}
	
	private Typeface loadTypeface(Context context, String name) {
		try {
			return Typeface.createFromAsset(context.getResources().getAssets(), name);
		} catch (RuntimeException e) {
			throw new MissingTypefaceException("Typeface " + name + " not found in your assets folder. Did you forget to add it?");
		}
	}
	
	public static TypefaceUtil getInstance(Context context) {
		if (instance == null) {
			instance = new TypefaceUtil(context);
		}
		return instance;
	}
	
	public Typeface getVeraMonoBold() {
		return mVeraMonoBold;
	}
	
	public Typeface getVeraMonoBoldItalic() {
		return mVeraMonoBoldItalic;
	}
	
	public Typeface getLiberationSans() {
		return mLiberationSans;
	}
}
