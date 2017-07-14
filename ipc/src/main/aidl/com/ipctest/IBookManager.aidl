// IBookManager.aidl
package com.ipctest;
import com.ipctest.bean.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
