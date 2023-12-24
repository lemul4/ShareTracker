package com.example.sharetracker;
/**
 * Класс ShareInfoFactory предоставляет фабричные методы для создания объектов ShareInfo.
 * Этот класс предоставляет методы для создания экземпляров ShareInfo с различными свойствами, такими как закладка или не закладка.
 */
public class ShareInfoFactory {
    public static ShareInfo createShareInfo(String figi, String name, String ticker, double cost) {
        ShareInfo shareInfo = new ShareInfo(figi, name, ticker, cost);
        shareInfo.setBookmarked(false);
        return shareInfo;
    }

    public static ShareInfo createBookmarkedShareInfo(String figi, String name, String ticker, double cost) {
        ShareInfo shareInfo = new ShareInfo(figi, name, ticker, cost);
        shareInfo.setBookmarked(true);
        return shareInfo;
    }
}

