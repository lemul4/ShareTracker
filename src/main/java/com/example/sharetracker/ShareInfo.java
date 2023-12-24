package com.example.sharetracker;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Класс ShareInfo представляет информацию о ценной бумаге.
 * Каждый объект этого класса содержит основные характеристики ценной бумаги, такие как figi, имя, тикер, стоимость
 * и информация о том, добавлена ли бумага в закладки.
 */
public class ShareInfo {
    private final SimpleStringProperty figi;
    private final SimpleStringProperty name;
    private final SimpleStringProperty ticker;
    private final SimpleStringProperty cost;
    private final SimpleBooleanProperty bookmarked;

    public ShareInfo(String figi, String name, String ticker, double cost) {
        this.figi = new SimpleStringProperty(figi);
        this.name = new SimpleStringProperty(name);
        this.ticker = new SimpleStringProperty(ticker);
        this.cost = new SimpleStringProperty(String.valueOf(cost));
        this.bookmarked = new SimpleBooleanProperty(false);
    }

    public String getFigi() {
        return figi.get();
    }

    public void setFigi(String figi) {
        this.figi.set(figi);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getTicker() {
        return ticker.get();
    }

    public void setTicker(String ticker) {
        this.ticker.set(ticker);
    }

    public String getCost() {
        return cost.get();
    }

    public void setCost(double cost) {
        this.cost.set(String.valueOf(cost));
    }

    public boolean isBookmarked() {
        return bookmarked.get();
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked.set(bookmarked);
    }

    public SimpleStringProperty figiProperty() {
        return figi;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty tickerProperty() {
        return ticker;
    }

    public SimpleStringProperty costProperty() {
        return cost;
    }

    public SimpleBooleanProperty bookmarkedProperty() {
        return bookmarked;
    }
}
