package com.project.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.BookEntity;
import com.project.bookstore.model.InputData;
import com.project.bookstore.service.AdminService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.glassfish.jersey.internal.guava.UnmodifiableIterator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private AdminService adminService;

  Logger log = LoggerFactory.getLogger(BookController.class);

  @GetMapping("/generateReport")
  public String generateReport(@RequestParam String userId){
    if(StringUtils.isEmpty(userId)){
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, userId);
    }
    try{
      return adminService.generateReport(userId);
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, "admin error");
    }
  }

  @GetMapping("/getTopSold")
  public String getTop10(@RequestParam String userId){
    if(StringUtils.isEmpty(userId)){
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, userId);
    }
    try{
      return adminService.getTopSoldBooks(userId);
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, "admin error");
    }
  }


  // method for loading books from the google books api
  // categories include: fiction, biography,
//  @RequestMapping(value = "/insertBooks", method = RequestMethod.GET)
  public String insertBooks () throws Exception{
    // fetch books from google api and show them here
    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    Request request = new Request.Builder()
            .url("https://www.googleapis.com/books/v1/volumes?q=p+subject:politics&key=AIzaSyAHgQcKnaG-7NpPqnzTI1k3NBr3sFtEJjs")
            .method("GET", null)
            .build();
    Response response = client.newCall(request).execute();
    JSONObject data = new JSONObject(response.body().string());
    JSONArray books = data.getJSONArray("items");
    ArrayList<BookEntity> list = new ArrayList<>();
    for (Object book : books) {
      JSONObject bookJson = new JSONObject(book.toString());

      if(bookJson.has("volumeInfo")){
        String format = "Ebook"; double price = 0.00;
        if(bookJson.has("saleInfo")) {
          JSONObject saleInfo = bookJson.getJSONObject("saleInfo");
          format = saleInfo.getBoolean("isEbook") ? "Ebook" : "Hardcopy";
          price = 0.00;
          if (saleInfo.getString("saleability").equals("FOR_SALE")) {
            price = saleInfo.getJSONObject("retailPrice").getDouble("amount");
          }
        }

        JSONObject info = bookJson.getJSONObject("volumeInfo");
        String title = info.getString("title");
        String image = info.getJSONObject("imageLinks").getString("thumbnail");
//      String category = info.getJSONArray("categories").getString(0);
        String category = "Politics";

        StringBuilder authors = new StringBuilder();
        if(info.has("authors")){
          JSONArray authorArray = info.getJSONArray("authors");
          for(Object author: authorArray){
            authors.append(author.toString()).append(", ");
          }
          authors.delete(authors.length() - 1, authors.length());
        } else{
          authors.append("XYZ author");
        }
        double stars = Math.round((2.5 + Math.random() * 5) * 100.0) / 100.0;

        BookEntity newEntity = new BookEntity(0, title, authors.toString(), format, stars, price, category, image);
//        System.out.println(newEntity.toString());
        list.add(newEntity);
      } else{
        System.out.println("null");
      }

    }

    int res = adminService.insertBooks(list);
    return (res == WConstants.RESPONSE_SUCCESS) ? "success" : "fail";
  }
}
