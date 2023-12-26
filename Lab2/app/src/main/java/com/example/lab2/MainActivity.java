package com.example.lab2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private int fragmentCount = 0;

    public void decrementFragmentCount(){
        fragmentCount--;
        updateFragmentCountText();
    }

    public void incrementFragmentCount(){
        fragmentCount++;
        updateFragmentCountText();
    }


    public void updateFragmentCountText() {
        ((TextView)findViewById(R.id.fragmentCounterText)).setText(
                getResources().getString(
                        R.string.fragment_counter_text, fragmentCount
                )
        );
    }

    public static class DetailsButtonFragment extends Fragment {

        public static final String ARG_TEXT = "text";

        public static DetailsButtonFragment create(String text) {
            DetailsButtonFragment detailsButtonFragment = new DetailsButtonFragment();
            Bundle args = new Bundle();
            args.putString(ARG_TEXT, text);
            detailsButtonFragment.setArguments(args);
            return detailsButtonFragment;
        }

        public DetailsButtonFragment() {
            super(R.layout.details_button_fragment);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            Button button = view.findViewById(R.id.button);
            String text = getArguments().getString(ARG_TEXT);
            button.setText(getArguments().getString(ARG_TEXT));

            button.setOnClickListener(v -> getParentFragmentManager().beginTransaction()
                    .replace(R.id.container_item, DetailsTextFragment.create(text))
                    .addToBackStack("TRANSACTION")
                    .commit());
            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                view.getRootView().findViewById(R.id.backButton).setVisibility(View.GONE);
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            ((MainActivity)getActivity()).incrementFragmentCount();
        }

        @Override
        public void onPause(){
            super.onPause();
            ((MainActivity)getActivity()).decrementFragmentCount();
        }
    }

    public static class DetailsTextFragment extends Fragment {

        public static final String ARG_TEXT = "text";

        public static DetailsTextFragment create(String text) {
            DetailsTextFragment detailsTextFragment = new DetailsTextFragment();
            Bundle args = new Bundle();
            args.putString(ARG_TEXT, text);
            detailsTextFragment.setArguments(args);
            return detailsTextFragment;
        }

        public DetailsTextFragment() {
            super(R.layout.details_text_fragment);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            TextView textView = view.findViewById(R.id.text);
            textView.setText(getArguments().getString(ARG_TEXT));
            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            view.getRootView().findViewById(R.id.backButton).setVisibility(View.VISIBLE);
        }

        @Override
        public void onResume() {
            super.onResume();
            ((MainActivity)getActivity()).incrementFragmentCount();
        }

        @Override
        public void onPause(){
            super.onPause();
            ((MainActivity)getActivity()).decrementFragmentCount();
        }
    }

    public static class FragmentMenu extends Fragment {

        public FragmentMenu() {
            super(R.layout.fragment_menu);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            view.findViewById(R.id.button_1).setOnClickListener(view1 -> {
                ((MainActivity) getActivity()).show("11111111");
            });
            view.findViewById(R.id.button_2).setOnClickListener(view1 -> {
                ((MainActivity) getActivity()).show("22222222");
            });
            view.findViewById(R.id.button_3).setOnClickListener(view1 -> {
                ((MainActivity) getActivity()).show("33333333");
            });

            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            ((MainActivity)getActivity()).incrementFragmentCount();
        }

        @Override
        public void onPause(){
            super.onPause();
            ((MainActivity)getActivity()).decrementFragmentCount();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() < 2) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                finish();
            }
        } else {
            super.onBackPressed();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            findViewById(R.id.backButton).setVisibility(View.GONE);
        }
    }

    public void show(String text) {
         getSupportFragmentManager().popBackStack(
                "TRANSACTION", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_item, DetailsButtonFragment.create(text))
                .addToBackStack("TRANSACTION")
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateFragmentCountText();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_list, new FragmentMenu())
                    .commit();
        }

        (findViewById(R.id.backButton)).setOnClickListener(view1 -> {
            onBackPressed();
        });
    }
}