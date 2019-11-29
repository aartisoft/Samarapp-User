package com.smartloan.smtrick.samarapp_user;

import java.util.Map;

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
    void updateMainProduct(final String main, final Map leedMap, final CallBack callback);

    void updateSubProduct(final String sub, final Map leedMap, final CallBack callback);

    void updateMainCatalog(final String catalog, final Map leedMap, final CallBack callback);

    void updateProduct(final String property, final Map leedMap, final CallBack callback);
//
//    void readLeedByLeedId(final String leedId, final CallBack callBack);
//
//    void updateLeedDocuments(final String leedId, final Map leedMap, final CallBack callback);
//
//    void updateLeedHistory(final String leedId, final Map leedMap, final CallBack callback);

}
