package com.smartloan.smtrick.samarapp;

public interface LeedRepository {

//
//    void deleteLeed(final String leedId, final CallBack callback);
//
//    void readAllLeeds(final CallBack callback);
//
//    void readLeedsByUserIdReport(final Context context, final String userId, final CallBack callBack);
//
//    void readLeedsByUserIdReport(Context context, String userId, CallBack callBack);

    void createLeed(final Upload leedsModel, final CallBack callback);

//    void readLeedsByStatus(final String status, final CallBack callBack);
//
//    void deleteLeed(final String leedId, final CallBack callback);
//
//    void updateLeed(final String leedId, final Map leedMap, final CallBack callback);
//
//    void readLeedByLeedId(final String leedId, final CallBack callBack);
//
//    void updateLeedDocuments(final String leedId, final Map leedMap, final CallBack callback);
//
//    void updateLeedHistory(final String leedId, final Map leedMap, final CallBack callback);

}
