package com.kirov.aesgcmcrypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Crypto extends Activity {
EditText inputString;
TextView outputString;
TextView decryptString;
Button Decrypt;

byte key[]="bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb".getBytes();
SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
byte[] iv = "123456789012".getBytes();
byte aad[] = "123456789012123456789012123456789012345678901234567890123456"
        .getBytes();
private byte [] inputdata;
private byte [] encodedata;
private byte [] decodedata;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crypto);
		
		inputString=(EditText)findViewById(R.id.inputLine);
		outputString=(TextView)findViewById(R.id.outputLine);
		outputString.setVisibility(View.INVISIBLE);
		decryptString=(TextView)findViewById(R.id.Decrypttext);
		decryptString.setVisibility(View.INVISIBLE);
		Decrypt= (Button) findViewById(R.id.Decrypt);
		Decrypt.setVisibility(View.INVISIBLE);
		
	}
	
	public void encrypt (View V)
	
	{
		/// hide keyboard
		InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE); 

inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                   InputMethodManager.HIDE_NOT_ALWAYS);
		/// encrypt string
		String input=inputString.getText().toString();
		inputdata= input.getBytes();
		encodedata=encryptGCM(inputdata);
		String output="";
		try {
			output = new String(encodedata, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outputString.setVisibility(View.VISIBLE);
		Decrypt.setVisibility(View.VISIBLE);
		outputString.setText(output);
	}
	
	public void decrypt (View v)
	{
		
		
		decodedata=decryptGCM(encodedata);
		
		String output="";
		try {
			output = new String(decodedata, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decryptString.setVisibility(View.VISIBLE);
		decryptString.setText(output);
		
	}
	
	public byte[] encryptGCM(byte[] data) {
		
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/GCM/NoPadding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int outputLength = cipher.getOutputSize(data.length); // Prepare output buffer
		byte[] output = new byte[outputLength];
		int outputOffset = 0;
		try {
			outputOffset = cipher.update(data, 0, data.length, output, 0);
		} catch (ShortBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// Produce cipher text
		try {
			outputOffset += cipher.doFinal(output, outputOffset);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShortBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
public byte[] decryptGCM(byte[] data) {
		
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/GCM/NoPadding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivspec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int outputLength = cipher.getOutputSize(data.length); // Prepare output buffer
		byte[] output = new byte[outputLength];
		int outputOffset = 0;
		try {
			outputOffset = cipher.update(data, 0, data.length, output, 0);
		} catch (ShortBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// Produce cipher text
		try {
			outputOffset += cipher.doFinal(output, outputOffset);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShortBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
}
