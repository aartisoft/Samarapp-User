package com.smartloan.smtrick.samarapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.Map;

public class LeedRepositoryImpl extends FirebaseTemplateRepository implements LeedRepository {
//    @Override
//    public void readAllLeeds(final CallBack callBack) {
//        final Query query = Constant.LEEDS_TABLE_REF;
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<LeedsModel> leedsModelArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            LeedsModel leedsModel = suggestionSnapshot.getValue(LeedsModel.class);
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
//
//    @Override
//    public void readLeedsByUserIdReport(final Context context, String userId, final CallBack callBack) {
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



    @Override
    public void createLeed(Upload leedsModel, final CallBack callBack) {
        DatabaseReference databaseReference = Constants.DATABASE_PATH_UPLOADS1.child(leedsModel.getPoductId());
        fireBaseCreate(databaseReference, leedsModel, new CallBack() {
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




//    @Override
//    public void deleteLeed(String leedId, CallBack callback) {
//        DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedId);
//        fireBaseDelete(databaseReference, callback);
//    }
//
    @Override
    public void updateMainProduct(String mian, Map leedMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constant.MAIN_PRODUCT_TABLE.child(mian);
        final Query query = Constant.LEEDS_TABLE_REF.orderByChild("createdBy").equalTo(mian);
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

    @Override
    public void updateSubProduct(String sub, Map leedMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constant.SUB_PRODUCT_TABLE.child(sub);
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

    @Override
    public void updateMainCatalog(String catalog, Map leedMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constant.MAIN_CATALOG_TABLE.child(catalog);
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

    @Override
    public void updateProduct(String property, Map leedMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constant.NEW_IMAGES_TABLE.child(property);
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

//    public void readLeedsByStatus(String status, final CallBack callBack) {
//        final Query query = Constant.LEEDS_TABLE_REF.orderByChild("status").equalTo(status);
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<Invoice> leedsModelArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            Invoice leedsModel = suggestionSnapshot.getValue(Invoice.class);
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

//    @Override
//    public void readLeedByLeedId(String leedId, final CallBack callBack) {
//        final Query query = Constant.LEEDS_TABLE_REF.child(leedId);
//        fireBaseReadData(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        DataSnapshot child = dataSnapshot.getChildren().iterator().next();
//                        LeedsModel leedsModel = child.getValue(LeedsModel.class);
//                        callBack.onSuccess(leedsModel);
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
//
//    @Override
//    public void updateLeedDocuments(String leedId, Map leedMap, CallBack callback) {
//
//    }
//
//    @Override
//    public void updateLeedHistory(String leedId, Map leedMap, CallBack callback) {
//
//    }


}
