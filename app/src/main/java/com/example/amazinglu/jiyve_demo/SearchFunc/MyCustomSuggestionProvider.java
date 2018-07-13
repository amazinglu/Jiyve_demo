package com.example.amazinglu.jiyve_demo.SearchFunc;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyCustomSuggestionProvider extends ContentProvider {

    private static String[] matrixCursorColumns = {"_id",
            SearchManager.SUGGEST_COLUMN_TEXT_1,
            SearchManager.SUGGEST_COLUMN_INTENT_DATA, // the data that will pass with the intent
            SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID}; // the data id that will pass with the intent

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (selectionArgs != null && selectionArgs.length > 0 && selectionArgs[0].length() > 0) {
            String query = selectionArgs[0];
            return getSearchResultCursor(query);
        }
        return null;
    }

    private MatrixCursor getSearchResultCursor(String query) {
        MatrixCursor searchResult = new MatrixCursor(matrixCursorColumns);
        Object[] mRow = new Object[matrixCursorColumns.length];
        int countID = 0;

        if (query != null && !query.isEmpty()) {
            for (String suggestion : StoreData.getStores()) {
                if (suggestion.toLowerCase().contains(query.toLowerCase())) {
                    mRow[0] = Integer.toString(countID);
                    mRow[1] = suggestion;
                    mRow[2] = suggestion;
                    mRow[3] = Integer.toString(countID);
                    countID++;
                    searchResult.addRow(mRow);
                }
            }
        }
        return searchResult;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
