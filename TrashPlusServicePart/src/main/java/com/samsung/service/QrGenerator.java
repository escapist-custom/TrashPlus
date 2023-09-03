package com.samsung.service;

// Java code to generate QR code

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QrGenerator {

	public static void createQR(String data, String path,
								String charset, Map hashMap,
								int height, int width)
		throws WriterException, IOException
	{

		BitMatrix matrix = new MultiFormatWriter().encode(
			new String(data.getBytes(charset), charset),
			BarcodeFormat.QR_CODE, width, height);

		/*MatrixToImageWriter.writeToFile(
			matrix,
			path.substring(path.lastIndexOf('.') + 1),
			new File(path));*/
	}

	public static void main(String[] args)
		throws WriterException, IOException,
			NotFoundException
	{

		String data = "4607035890024/40FE";

		String path = "Горошек зеленый консервированный, т.з. «6 Соток».png";

		String charset = "UTF-8";

		Map<EncodeHintType, ErrorCorrectionLevel> hashMap
			= new HashMap<EncodeHintType,
						ErrorCorrectionLevel>();

		hashMap.put(EncodeHintType.ERROR_CORRECTION,
					ErrorCorrectionLevel.L);


		createQR(data, path, charset, hashMap, 200, 200);
		System.out.println("QR Code Generated!!! ");
	}
}
