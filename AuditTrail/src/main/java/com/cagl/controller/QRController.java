package com.cagl.controller;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@RestController
@CrossOrigin(origins = "*")
public class QRController {

	@GetMapping("/test")
	public String generateQRCodeImage3(String text) throws Exception {

		return "AuditTrail server up";
	}

	@GetMapping("/generate-base64qr")
	public ResponseEntity<String> generateBase64QR(@RequestHeader("Authorization") String headerValue,
			@RequestParam String loanid, @RequestParam String memberName) {

		if (!headerValue.equals("Q2FnbCQyMDIz")) {
			return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}

		try {

			if ((loanid + " " + memberName).length() > 45) {
				return new ResponseEntity<>("Error:The Loanid&MemberName exceeds the allowed length limit.",
						HttpStatus.OK);
			}
			// Generate QR Code
			ByteArrayOutputStream byteArrayOutputStream = generateQRCodeImage(
					"upi://pay?pa=creditaccessgrameen.fingpay@icici&pn=CAGL&tr=MNOQSQ" + loanid + "S&tn=" + memberName
							+ " " + loanid + "&mc=7322");
			// Prepare response with image data
			byte[] qrCodeImage = byteArrayOutputStream.toByteArray();
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("Content-Type", "image/png");
//			return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
			return new ResponseEntity<>(Base64.getEncoder().encodeToString(qrCodeImage), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ByteArrayOutputStream generateQRCodeImage(String text) throws Exception {
		int width = 300;
		int height = 300;
		// Create the BitMatrix for QR code
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
		// Convert BitMatrix to image and save to output stream
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
		return byteArrayOutputStream;
	}

// Optional: Method to save QR Code to file
//	public void saveQRCodeToFile(String text, String filePath) throws Exception {
//		int width = 300;
//		int height = 300;
//
//		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
//
//		Path path = FileSystems.getDefault().getPath(filePath);
//		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
//	}

}
