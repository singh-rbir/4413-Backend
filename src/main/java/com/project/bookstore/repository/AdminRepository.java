package com.project.bookstore.repository;

import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.controller.UserController;
import com.project.bookstore.model.BookEntity;
import com.project.bookstore.model.BooksSoldData;
import com.project.bookstore.model.CartItem;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepository {

  Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private EntityManager entityManager;
  private Session getSession(){
    return entityManager.unwrap(Session.class);
  }

  @Transactional
  public List<BooksSoldData> returnBooksSold(){
    try{
      Session session = getSession();
      Query<?> query = session.createNativeQuery("with summed as " +
              "    (select od.bid as bid, sum(od.quantity) as quantity from ORDER_DETAIL od " +
              "    join BOOK B on od.BID = B.BID" +
              "    left join ORDER O on O.order_id = od.order_id" +
              "    where O.status = 1" +
              "    group by od.bid) " +
              "select S.bid, B.title, B.price, S.quantity from summed S " +
              "join BOOK B on S.bid = B.bid ");
      List<Object[]> itemList = (List<Object[]>)query.getResultList();

      List<BooksSoldData> books = new ArrayList<>();
      for(Object[] bookData: itemList){
        BooksSoldData item = new BooksSoldData();
        item.setBid((int)bookData[0]);
        item.setTitle(String.valueOf(bookData[1]));
        item.setPrice(Util.roundDouble((Double) bookData[2]));
        BigInteger bg = new BigInteger(String.valueOf(bookData[3]));
        item.setQuantity(bg.intValue());
        books.add(item);
      }
      return books;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public List<BooksSoldData> topSoldBooks(){
    try{
      Session session = getSession();
      Query<?> query = session.createNativeQuery("with summed as " +
              "    (select od.bid as bid, sum(od.quantity) as quantity from ORDER_DETAIL od " +
              "    join BOOK B on od.BID = B.BID" +
              "    left join ORDER O on O.order_id = od.order_id" +
              "    where O.status = 1" +
              "    group by od.bid) " +
              "select S.bid, B.title, S.quantity from summed S " +
              "join BOOK B on S.bid = B.bid " +
              "order by S.quantity desc limit 10");
      List<Object[]> itemList = (List<Object[]>)query.getResultList();

      List<BooksSoldData> books = new ArrayList<>();
      for(Object[] bookData: itemList){
        BooksSoldData item = new BooksSoldData();
        item.setBid((int)bookData[0]);
        item.setTitle(String.valueOf(bookData[1]));
        BigInteger bg = new BigInteger(String.valueOf(bookData[2]));
        item.setQuantity(bg.intValue());
        books.add(item);
      }
      return books;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }


  @Transactional
  public int insertBooks(ArrayList<BookEntity> list){
    Session session = getSession();
    try{
      for (BookEntity bookEntity : list) {
        Query<?> query = session.createNativeQuery("insert into book (TITLE, AUTHOR, FORMAT, STARS, PRICE, CATEGORY, IMAGES) values (:t, :a, :f, :s, :p, :c, :i)");
        query.setParameter("t", bookEntity.getTitle());
        query.setParameter("a", bookEntity.getAuthor());
        query.setParameter("f", bookEntity.getFormat());
        query.setParameter("s", bookEntity.getStars());
        query.setParameter("p", bookEntity.getPrice());
        query.setParameter("c", bookEntity.getCategory());
        query.setParameter("i", bookEntity.getImages());
        int res = query.executeUpdate();
        if (res != 1) {
          log.error("something went wrong with bid: " + bookEntity.bid);
          break;
        }
      }
      return WConstants.RESPONSE_SUCCESS;
    } catch (Exception e){
      log.debug(e.getMessage(), e);
      return WConstants.RESULT_UNKNOWN_ERROR;
    }
  }

}
