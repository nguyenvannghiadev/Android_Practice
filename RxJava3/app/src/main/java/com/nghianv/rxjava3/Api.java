package com.nghianv.rxjava3;

import java.util.List;

import io.reactivex.Observable;

interface Api {
	// truyen vao 1 chuoi String va tra ve 1 Observable phat ra danh sach phim duoi dang Json
	Observable<String> searchMovie(String query);

	//Truyen vao 1 chuoi Json va tra ve 1 List<Movie>
	List<Movie> parse(String json);

	// Quy tac
	/**
	 * 1 interface khong the co constructor hay khong the khoi tao 1 interface
	 * Tat ca cac phuong thuc of interface deu la abstract
	 * 1 interface co the ke duoc ke thua tu 1 interface khac
	 * 1 interfae khong the duoc ke thua tu 1 lop
	 * 1 class chi co the ke thua tu 1 class khac, nhung 1 class co the duoc trien khai tu nhieu interface
	 * Phai dung tu khoa implements de trien khai interface
	 */
}
