package com.telerik.examples.examples.listview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.telerik.android.common.Util;
import com.telerik.examples.Adapter_TestRadlist;
import com.telerik.examples.R;
import com.telerik.examples.Test_RadlistAdapter;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.SlideLayoutManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class ListViewSlideFragment extends ExampleFragmentBase {
    private RadListView listView;
    private SlideLayoutManager slideLayoutManager;
    private Adapter_TestRadlist adapter;
    private View rootView;
    private LruCache<String, Bitmap> mMemoryCache;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_list_view_slide, container, false);
        listView = (RadListView)rootView.findViewById(R.id.listView);

        slideLayoutManager = new SlideLayoutManager(getActivity());

        adapter = new Adapter_TestRadlist(getData());
        updateListViewLayoutParams();
        listView.setAdapter(adapter);

        listView.setLayoutManager(slideLayoutManager);

        if (savedInstanceState != null) {
            int currentPosition = savedInstanceState.getInt("currentPosition", 0);
            slideLayoutManager.scrollToPosition(currentPosition);
        }

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        RetainFragment retainFragment =
                RetainFragment.findOrCreateRetainFragment(getFragmentManager());
        mMemoryCache = retainFragment.mRetainedCache;
        if (mMemoryCache == null) {
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    return bitmap.getByteCount() / 1024;
                }
            };
            retainFragment.mRetainedCache = mMemoryCache;
        }




    }

    private List getData() {
        ArrayList<Test_RadlistAdapter> destinations = new ArrayList<>();

        Test_RadlistAdapter test1 = new Test_RadlistAdapter("Costa Rican Treasures", "Whether you're a surfi");
        destinations.add(test1);

        Test_RadlistAdapter test2 = new Test_RadlistAdapter("Peru Treasures", "Whether asafddgf787453 surfi assdf");
        destinations.add(test2);

        Test_RadlistAdapter test3 = new Test_RadlistAdapter("Colombia Treasures", "Whether youasgfhgf're a surfi");
        destinations.add(test3);

        Test_RadlistAdapter test4 = new Test_RadlistAdapter("Mexico Treasures", "Whether youyuioñ535356're a surfi");
        destinations.add(test4);

        return destinations;
    }

    private void updateListViewLayoutParams() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        int height = size.y;
        TypedValue tv = new TypedValue();
        if (getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true))
        {
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
            height -= actionBarHeight;
        }
        int offset;
        int cardHeight;
        int cardWidth;
        int maxWidth = 1067;
        int maxHeight = 5 * maxWidth / 4;

        int itemSpacing = (int) Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 12);
        if (width > height) {
            cardHeight = height - 4 * itemSpacing;
            cardWidth = 4 * cardHeight / 5;
            offset = (width - cardWidth) / 2;
        } else {
            offset = width / 10;
            cardWidth = offset * 8;
            cardHeight = 5 * cardWidth / 4;
        }
        if(cardWidth > maxWidth) {
            cardWidth = maxWidth;
            cardHeight = maxHeight;
            offset = (width - cardWidth) / 2;
        }

        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, cardHeight));

        adapter.setDimens(cardWidth, cardHeight);

        slideLayoutManager.setPreviousItemPreview(offset);
        slideLayoutManager.setNextItemPreview(offset);
        slideLayoutManager.setItemSpacing(itemSpacing);
    }

    public static class RetainFragment extends Fragment {
        private static final String TAG = "RetainFragment";
        public LruCache<String, Bitmap> mRetainedCache;

        public RetainFragment() {}

        static RetainFragment findOrCreateRetainFragment(FragmentManager fm) {
            RetainFragment fragment = (RetainFragment) fm.findFragmentByTag(TAG);
            if (fragment == null) {
                fragment = new RetainFragment();
                fm.beginTransaction().add(fragment, TAG).commit();
            }
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }
    }




}

