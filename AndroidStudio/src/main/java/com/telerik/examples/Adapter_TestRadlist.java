package com.telerik.examples;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.telerik.examples.examples.listview.ListViewSlideFragment;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;

import java.util.List;

/**
 * Created by macmini on 12/05/2016.
 */

public class Adapter_TestRadlist extends ListViewAdapter {

    private int width;
    private int height;

    public Adapter_TestRadlist(List items) {
        super(items);
    }

    public void setDimens(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_view_destination_layout, parent, false);
        return new DestinationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Test_RadlistAdapter item = (Test_RadlistAdapter) this.getItem(position);

        final DestinationViewHolder typedVh = (DestinationViewHolder) holder;

        typedVh.position = position;
        typedVh.destinationInfo.setText(item.getName());
        typedVh.destinationTitle.setText(item.getCountry());

    }
    public class DestinationViewHolder extends ListViewHolder {
       // public ImageView destinationImage;
        public TextView destinationTitle;
        public TextView destinationInfo;
        public ViewGroup destinationEnquiryLayout;
       // public Button destinationEnquiry;
        public View separator;
        public ViewGroup layout;
        public int position;

        private Context context;

        public DestinationViewHolder(final View itemView) {
            super(itemView);

            this.context = itemView.getContext();
           // this.destinationImage = (ImageView) itemView.findViewById(R.id.destinationImage);
            this.destinationTitle = (TextView) itemView.findViewById(R.id.destinationTitle);
            this.destinationInfo = (TextView) itemView.findViewById(R.id.destinationInfo);
          //  this.destinationEnquiry = (Button) itemView.findViewById(R.id.destinationEnquiry);
           // this.destinationEnquiry.setOnClickListener(new View.OnClickListener() {
             //   @Override
             //   public void onClick(View v) {
             //       sendEnquiry();
             //   }
           // });
            this.layout = (ViewGroup)itemView.findViewById(R.id.scrollingLayout);
            //this.destinationEnquiryLayout = (ViewGroup) itemView.findViewById(R.id.destinationEnquiryLayout);
            this.separator = itemView.findViewById(R.id.separator);
        }

        private void sendEnquiry() {
            Toast.makeText(this.context, "Enquiry sent.", Toast.LENGTH_SHORT).show();
        }
    }
}