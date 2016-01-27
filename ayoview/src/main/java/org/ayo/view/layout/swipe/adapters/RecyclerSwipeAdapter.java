package org.ayo.view.layout.swipe.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import org.ayo.view.layout.swipe.SwipeLayout;
import org.ayo.view.layout.swipe.implments.SwipeItemRecyclerMangerImpl;
import org.ayo.view.layout.swipe.interfaces.SwipeAdapterInterface;
import org.ayo.view.layout.swipe.interfaces.SwipeItemMangerInterface;
import org.ayo.view.layout.swipe.util.Attributes;

import java.util.List;

public abstract class RecyclerSwipeAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements SwipeItemMangerInterface, SwipeAdapterInterface {

    public SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH viewHolder, final int position);

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        mItemManger.setMode(mode);
    }
}
