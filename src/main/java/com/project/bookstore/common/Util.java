package com.project.bookstore.common;

import com.project.bookstore.controller.UserController;
import com.project.bookstore.model.BookEntity;
import com.project.bookstore.repository.UserRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Util {

  static Logger log = LoggerFactory.getLogger(Util.class);


  public static String getJsonResponse(int statusCode, @Nullable String user_id){
    JSONObject json = new JSONObject();

    switch (statusCode){
      case WConstants.RESULT_INVALID_DATA:
        json.put("status", WConstants.RESPONSE_FAIL);
        json.put("message", "Invalid data provided. Please try again.");
        break;
      case WConstants.INVALID_USER_SIGNUP_DATA:
        json.put("status", WConstants.RESPONSE_FAIL);
        json.put("message", "Data was invalid, please try again.");
        break;
      case WConstants.RESULT_UNKNOWN_ERROR:
        json.put("status", WConstants.RESPONSE_FAIL);
        json.put("message", "Unknown error. Please try again.");
        break;
      case WConstants.RESULT_INVALID_CREDENTIALS:
        json.put("status", WConstants.RESPONSE_FAIL);
        json.put("message", "Invalid email or password");
        break;
      case WConstants.RESULT_USER_DOES_NOT_EXIST:
        json.put("status", WConstants.RESPONSE_FAIL);
        json.put("message", "User does not exist. Try signing up instead.");
        break;
    }

    if(user_id != null){
      json.put("user", user_id);
    }

    return json.toString(4);
  }

  /**
   * Hashing with SHA1
   *
   * @param input String to hash
   * @return String hashed
   */
  @Deprecated
  public static String sha1(String input) {
    String sha1 = null;
    try {
      MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
      msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
      sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
    } catch (Exception e) {
      log.error("Error hashing password.");
    }
    return sha1;
  }


  public static double roundDouble(double value){
    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(2, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }


}
