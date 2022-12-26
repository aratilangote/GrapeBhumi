package com.capstone.grapediseaseapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ContainerHome extends Fragment {

    //AdapterViewFlipper
    AdapterViewFlipper adaptFlipper;
    private static final int[] images = {R.drawable.farm, R.drawable.grape_farm,
            R.drawable.grapegrowth, R.drawable.grape_types};

    Button BlackRot, Downy, Esca, LeafBlight, Powdery;


    public ContainerHome() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.container_home, null);

        //AdapterViewFlipper
        //AdapterViewFlipper
        adaptFlipper = view.findViewById(R.id.adptflipper);
        ViewFlipperAdapter viewFlipper = new ViewFlipperAdapter(getContext(), images);
        adaptFlipper.setAdapter(viewFlipper);
        adaptFlipper.setFlipInterval(1000);
        adaptFlipper.setAutoStart(true);


        BlackRot = view.findViewById(R.id.info_txt1);
        Downy = view.findViewById(R.id.info_txt2);
        Esca = view.findViewById(R.id.info_txt3);
        LeafBlight = view.findViewById(R.id.info_txt4);
        Powdery = view.findViewById(R.id.info_txt5);

        BlackRot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity().getApplicationContext(), BlackRotInfoActivity.class));
            }
        });

        Downy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startActivity(new Intent(getActivity().getApplicationContext(), DownyInfoActivity.class));
            }
        });
        Esca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), EscaActivity.class));
            }
        });
        LeafBlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), LeafBlightActivity.class));
            }
        });
        Powdery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), PowderyActivity.class));
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    //AdapterViewFlipper viewFlipper custom class
    static class ViewFlipperAdapter extends BaseAdapter {
        Context context;
        int[] images;
        LayoutInflater inflater;

        public ViewFlipperAdapter(Context mycontext, int[] myimages){
            this.context = mycontext;
            this.images = myimages;
            inflater = LayoutInflater.from(mycontext);
        }

        @Override
        public int getCount() {return 4;}

        @Override
        public Object getItem(int i) {return null;}

        @Override
        public long getItemId(int i) {return 0;}

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.activity_adapter_images, null);

            ImageView imageView = view.findViewById(R.id.flip_imgs);
            imageView.setImageResource(images[position]);
            return view;
        }
    }
}