package com.smartloan.smtrick.samarapp;

import com.google.firebase.database.DatabaseReference;

import java.util.Map;

public class InvoiceRepositoryImpl extends FirebaseTemplateRepository implements InvoiceRepository {

//    @Override
//    public void readAllInvoices(final CallBack callBack) {
//        final Query query = Constant.INVOICE_TABLE_REF;
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<Invoice> invoiceArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            Invoice invoice = suggestionSnapshot.getValue(Invoice.class);
//                            invoiceArrayList.add(invoice);
//                        }
//                        callBack.onSuccess(invoiceArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//    @Override
//    public void readAllusers(final CallBack callBack) {
//        final Query query = Constant.USER_TABLE_REF;
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<User> invoiceArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            User invoice = suggestionSnapshot.getValue(User.class);
//                            invoiceArrayList.add(invoice);
//                        }
//                        callBack.onSuccess(invoiceArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//    @Override
//    public void readInvoicesByAgentId(String agentId, final CallBack callBack) {
//        final Query query = Constant.INVOICE_TABLE_REF.orderByChild("agentId").equalTo(agentId);
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<Invoice> invoiceArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            Invoice invoice = suggestionSnapshot.getValue(Invoice.class);
//                            invoiceArrayList.add(invoice);
//                        }
//                        callBack.onSuccess(invoiceArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//    @Override
//    public void readInvoicesByAgentId1(String agentId, final CallBack callBack) {
//        final Query query = Constant.INVOICE_TABLE_REF.orderByChild("agentId").equalTo(agentId);
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<Invoice> invoiceArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            Invoice invoice = suggestionSnapshot.getValue(Invoice.class);
//                            invoiceArrayList.add(invoice);
//                        }
//                        callBack.onSuccess(invoiceArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//    @Override
//    public void readallleadsadmin(String agentId, final CallBack callBack) {
//        final Query query = Constant.INVOICE_TABLE_REF.orderByChild("agentId").equalTo(agentId);
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<LeedsModel> invoiceArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            LeedsModel invoice = suggestionSnapshot.getValue(LeedsModel.class);
//                            invoiceArrayList.add(invoice);
//                        }
//                        callBack.onSuccess(invoiceArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//    @Override
//    public void readInvoicesByUserId(String userId, final CallBack callBack) {
//        final Query query = Constant.LEEDS_TABLE_REF.orderByChild("createdBy").equalTo(userId);
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<Invoice> invoiceArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            Invoice invoice = suggestionSnapshot.getValue(Invoice.class);
//                            invoiceArrayList.add(invoice);
//                        }
//                        callBack.onSuccess(invoiceArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//
//    public void readLeedsByUserIdReports(final Context context, String userId, final CallBack callBack) {
//        final Query query = Constant.LEEDS_TABLE_REF.orderByChild("createdBy").equalTo(userId);
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<LeedsModel> leedsModelArrayList = new ArrayList<>();
//                        int colorCount = 0;
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            LeedsModel leedsModel = suggestionSnapshot.getValue(LeedsModel.class);
//                            if (colorCount % 5 == 0)
//                                colorCount = 0;
//                           // setColor(context, leedsModel, colorCount);
//                            colorCount++;
//                            leedsModelArrayList.add(leedsModel);
//                        }
//                        callBack.onSuccess(leedsModelArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//    @Override
//    public void createInvoice(Invoice invoice, final CallBack callBack) {
//        DatabaseReference databaseReference = Constant.INVOICE_TABLE_REF.child(invoice.getLeedId());
//        fireBaseCreate(databaseReference, invoice, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                callBack.onSuccess(object);
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }

//    @Override
//    public void deleteInvoice(String invoiceId, CallBack callBack) {
//        DatabaseReference databaseReference = Constant.INVOICE_TABLE_REF.child(invoiceId);
//        fireBaseDelete(databaseReference, callBack);
//    }

//    @Override
//    public void updateInvoice(String invoiceId, Map invoiceMap, final CallBack callBack) {
//        final DatabaseReference databaseReference = Constant.INVOICE_TABLE_REF.child(invoiceId);
//        fireBaseUpdateChildren(databaseReference, invoiceMap, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                callBack.onSuccess(object);
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }

    @Override
    public void updateLeed(String leedId, Map leedMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constants.DATABASE_PATH_UPLOADS1.child(leedId);
        fireBaseUpdateChildren(databaseReference, leedMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                callBack.onSuccess(object);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }



//
//    @Override
//    public void readInvoiceByInvoiceId(String invoiceId, final CallBack callBack) {
//        final Query query = Constant.INVOICE_TABLE_REF.child(invoiceId);
//        fireBaseReadData(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        DataSnapshot child = dataSnapshot.getChildren().iterator().next();
//                        Invoice invoice = child.getValue(Invoice.class);
//                        callBack.onSuccess(invoice);
//                    } else
//                        callBack.onSuccess(null);
//                } else
//                    callBack.onSuccess(null);
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//    @Override
//    public void readInvoiceByStatus(String status, final CallBack callBack) {
//        final Query query = Constant.INVOICE_TABLE_REF.orderByChild("status").equalTo(status);
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<Invoice> invoiceArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            Invoice invoice = suggestionSnapshot.getValue(Invoice.class);
//                            invoiceArrayList.add(invoice);
//                        }
//                        callBack.onSuccess(invoiceArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
}
