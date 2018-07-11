package com.example.amazinglu.jiyve_demo.base;

import android.content.Context;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

public abstract class MSimpleOnSearchActionListener implements MaterialSearchBar.OnSearchActionListener {
    @Override
    public void onSearchStateChanged(boolean enabled) {
        String s = enabled ? "enabled" : "disabled";
        Toast.makeText(getContext(), "Search " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        startSearch(text.toString());
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    protected abstract Context getContext();
    protected abstract void startSearch(String s);
}
