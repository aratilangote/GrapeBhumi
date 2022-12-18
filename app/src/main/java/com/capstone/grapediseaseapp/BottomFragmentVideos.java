package com.capstone.grapediseaseapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class BottomFragmentVideos extends Fragment {


    //Videos
    LinearLayout root;

    String[] list = {
            "https://youtu.be/Iy0E5McxE3A",
            "https://youtu.be/ejhkk0_BNns",
            "https://youtu.be/fPVGuvENhlA",
            "https://youtu.be/FSYb2q8FZE0",
            "https://youtu.be/Iwn-3xKZOhg",
            "https://youtu.be/1uVdjCcgt_0",
            "https://youtu.be/dQIzI5aTTIk",
            "https://youtu.be/hhz7boG4JNg",
            "https://youtu.be/xtbVblyT-hc",
            "https://youtu.be/1rXl_Cb6y4s",
            "https://youtu.be/A5-YiIQCFjo",
            "https://youtu.be/Np8jtzVT_WY",
            "https://youtu.be/J9eJ9vNJNIE",
            "https://youtu.be/Eu_oHN4S6tc",
            "https://youtu.be/_7SQY-q_o_I"
    };


    public BottomFragmentVideos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_videos, null);

        root = view.findViewById(R.id.root);
        for (String item : list) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.activity_video_view, null);
            YouTubePlayerView player = v.findViewById(R.id.videoPlayer);
            getLifecycle().addObserver(player);

            player.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(item, 0);
                }
            });
            root.addView(v);
        }

        return view;
    }
}