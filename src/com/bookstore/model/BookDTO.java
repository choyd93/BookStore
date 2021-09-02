package com.bookstore.model;

public class BookDTO {
    
    private String book_id;
    private String book_title;
    private String subtitle;
    private String writer;
    private String translator;
    private String publisher;
    private String release_date;
    private int book_price;
    private int sale_price;
    private int book_sale_rate;
    
    
    
    public BookDTO(String book_title, int book_price) {
		super();
		this.book_title = book_title;
		this.book_price = book_price;
	}

	public BookDTO(String book_id, String book_title, String writer, String publisher, int sale_price) {
        super();
        this.book_id = book_id;
        this.book_title = book_title;
        this.writer = writer;
        this.publisher = publisher;
        this.sale_price = sale_price;
    }

    public BookDTO(String book_id, String book_title, String subtitle, String writer, String translator,
            String publisher, String release_date, int book_price, int sale_price, int book_sale_rate) {
        super();
        this.book_id = book_id;
        this.book_title = book_title;
        this.subtitle = subtitle;
        this.writer = writer;
        this.translator = translator;
        this.publisher = publisher;
        this.release_date = release_date;
        this.book_price = book_price;
        this.sale_price = sale_price;
        this.book_sale_rate = book_sale_rate;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getBook_price() {
        return book_price;
    }

    public void setBook_price(int book_price) {
        this.book_price = book_price;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public double getBook_sale_rate() {
        return book_sale_rate;
    }

    public void setBook_sale_rate(int book_sale_rate) {
        this.book_sale_rate = book_sale_rate;
    }

    @Override
    public String toString() {
        return "bookVO [book_id=" + book_id + ", book_title=" + book_title + ", subtitle=" + subtitle + ", writer="
                + writer + ", translator=" + translator + ", publisher=" + publisher + ", release_date=" + release_date
                + ", book_price=" + book_price + ", sale_price=" + sale_price + ", book_sale_rate=" + book_sale_rate
                + "]";
    }

}

