package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.BitmapFactory.Options;
import android.support.v4.util.LruCache;
import android.util.Log;

public class ImageFactory {
	// 图片路径 设置图片的宽度和高度
	public Bitmap GetBitmapOfLoadImage(String ImagePath, int setWidth,
			int setHeight) throws FileNotFoundException {
		// BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inJustDecodeBounds = false;
		// Bitmap image = BitmapFactory.decodeFile(ImagePath, options);
		Options options = new Options();
		options.inJustDecodeBounds = true; // 设置为true, 加载器不会返回图片,
											// 而是设置Options对象中以out开头的字段.即仅仅解码边缘区域
		BitmapFactory.decodeFile(ImagePath, options);
		int imageWidth = options.outWidth;
		int imageHeight = options.outHeight;
		int widthScale = imageWidth / setWidth;
		int heightScale = imageHeight / setHeight;
		options.inJustDecodeBounds = false;
		options.inSampleSize = 2;
		Matrix matrix = new Matrix();
		matrix.postScale(widthScale, heightScale);
		Bitmap image = BitmapFactory.decodeFile(ImagePath, options);
		if (image != null) {
			try {
				// image=Bitmap.createBitmap(image, 0, 0, imageWidth,
				// imageHeight, matrix, false);
				// Log.v("setWidth"+setWidth, "setHeight"+setHeight);
				return Bitmap.createScaledBitmap(image, setWidth, setHeight,
						true);
			} finally {
//				Log.v("finally", "work");
				if (image != null) {
					if (!image.isRecycled()) {
						image.recycle();
					}
				}

			}
		}
		return null;
	}

	@SuppressLint("NewApi")
	public void GetBitmapOfNetImage(Bitmap Image, int sWidth, int sHeight,
			ImageFactoryCallBack callBack) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		Bitmap image = Image;
		int ImageWidth = image.getWidth();
		int ImageHeight = image.getHeight();
		float ScaleWidth = (float) sWidth / ImageWidth;
		float ScaleHeight = (float) sHeight / ImageHeight;
		Matrix matrix = new Matrix();
		matrix.postScale(ScaleWidth, ScaleHeight);
		Bitmap ResetBitmap = Bitmap.createBitmap(image, 0, 0, ImageWidth,
				ImageHeight, matrix, false);
		callBack.GetBitmap(ResetBitmap);
		image.recycle();
		image = null;

	}

	public interface ImageFactoryCallBack {
		public void GetBitmap(Bitmap ResetBitmap);
	}

}
