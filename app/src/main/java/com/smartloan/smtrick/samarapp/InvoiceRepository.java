package com.smartloan.smtrick.samarapp;

import java.util.Map;

public interface InvoiceRepository {
//    void readAllInvoices(final CallBack callBack);
//
//    void readInvoicesByAgentId(final String agentId, final CallBack callBack);
//    void readInvoicesByAgentId1(final String agentId, final CallBack callBack);
//
//    void readInvoicesByUserId(final String userId, final CallBack callBack);
//
//    void readAllusers(final CallBack callBack);
//
//    void readallleadsadmin(final String userId, final CallBack callBack);
//
// //   void createInvoice(final Invoice invoice, final CallBack callBack);
//
//    void deleteInvoice(final String invoiceId, final CallBack callBack);
//
//    void updateInvoice(final String invoiceId, final Map invoiceMap, final CallBack callBack);
//
//    void readInvoiceByInvoiceId(final String invoiceId, final CallBack callBack);
//
//    void readInvoiceByStatus(final String status, final CallBack callBack);

    void updateLeed(final String leedId, final Map leedsMap, final CallBack callBack);

}
